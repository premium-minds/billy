/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.export.qrcode;

import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.export.exceptions.RequiredFieldNotFoundException;
import com.premiumminds.billy.portugal.services.export.qrcode.QRCodeConstants.Field;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QRCodeBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(QRCodeBuilder.class);
	private static final String DATA_SEPARATOR = ":";
	private static final String FIELD_SEPARATOR = "*";
	private static final MathContext MATH_CONTEXT = BillyMathContext.get();

	private QRCodeBuilder() {
		//Silence is golden
	}

	public static String generateQRCodeString(final QRCodeData qrCodeData) throws RequiredFieldNotFoundException
	{
		final String atcud = qrCodeData.getInvoiceSeries()
			.getSeriesUniqueCode()
			.map(s -> new StringBuilder()
				.append(s)
				.append("-")
				.append(qrCodeData.getSeriesNumber()))
			.orElse(new StringBuilder().append("0")).toString();

		final String customerFinancialID;
		if (qrCodeData.getGenericCustomerUID().equals(qrCodeData.getCustomer().getUID())) {
			customerFinancialID = "999999990";
		} else {
			customerFinancialID = qrCodeData.getCustomer().getTaxRegistrationNumber();
		}

		final StringBuilder result = new StringBuilder()
			.append(QRCodeConstants.emitterFinancialId.getName()).append(DATA_SEPARATOR)
			.append(validateString(QRCodeConstants.emitterFinancialId, qrCodeData.getFinancialID(), true))
			.append(FIELD_SEPARATOR)
			.append(QRCodeConstants.buyerFinancialId.getName()).append(DATA_SEPARATOR)
			.append(validateString(QRCodeConstants.buyerFinancialId, customerFinancialID, true))
			.append(FIELD_SEPARATOR)
			.append(QRCodeConstants.buyerCountry.getName()).append(DATA_SEPARATOR)
			.append(validateString(QRCodeConstants.buyerCountry, qrCodeData.getCustomer().getBillingAddress().getISOCountry(), true))
			.append(FIELD_SEPARATOR)
			.append(QRCodeConstants.documentType.getName()).append(DATA_SEPARATOR)
			.append(validateString(QRCodeConstants.documentType, qrCodeData.getType().name(), true))
			.append(FIELD_SEPARATOR)
			.append(QRCodeConstants.documentStatus.getName()).append(DATA_SEPARATOR)
			.append(validateString(QRCodeConstants.documentStatus, getDocumentStatus(qrCodeData), true))
			.append(FIELD_SEPARATOR)
			.append(QRCodeConstants.documentDate.getName()).append(DATA_SEPARATOR)
			.append(validateString(QRCodeConstants.documentDate, formatDate(qrCodeData.getDate()), true))
			.append(FIELD_SEPARATOR)
			.append(QRCodeConstants.documentUniqueID.getName()).append(DATA_SEPARATOR)
			.append(validateString(QRCodeConstants.documentUniqueID, qrCodeData.getNumber() , true))
			;

		if(!atcud.isEmpty()) {
			result
				.append(FIELD_SEPARATOR)
				.append(QRCodeConstants.ATCUD.getName()).append(DATA_SEPARATOR)
				.append(validateString(QRCodeConstants.ATCUD, atcud, true));
		}

		final Map<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>> amountAndContextAndType =
			getTaxAmountByContextAndType(qrCodeData.getEntries());

		BigDecimal exemptAmount = BigDecimal.ZERO;
		for(Entry<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>> entry : amountAndContextAndType.entrySet()){
			if(entry.getKey().getUID().equals(qrCodeData.getPtContexts().getPortugalUID())){
				TupleTaxAmountAndAmountWithoutTaxes ise = entry.getValue().get("ISE");
				if(ise != null) {
					exemptAmount = ise.amountWithoutTaxes;
				}
			}
		}

		result.append(buildContextSpecificParameters(amountAndContextAndType, exemptAmount, qrCodeData.getPtContexts()));

		if(!exemptAmount.equals(BigDecimal.ZERO)) {
			result.append(FIELD_SEPARATOR)
				  .append(QRCodeConstants.NSOrNTAmount.getName()).append(DATA_SEPARATOR)
				  .append(validateBigDecimal(exemptAmount));
		}

		//QRCodeData.stampDuty not implemented

		if(!qrCodeData.getTaxAmount().equals(BigDecimal.ZERO)) {
			result.append(FIELD_SEPARATOR)
				  .append(QRCodeConstants.taxPayable.getName()).append(DATA_SEPARATOR)
				  .append(validateBigDecimal(qrCodeData.getTaxAmount()));
		}

		if(!qrCodeData.getAmountWithTax().equals(BigDecimal.ZERO)) {
			result.append(FIELD_SEPARATOR)
				  .append(QRCodeConstants.grossTotal.getName()).append(DATA_SEPARATOR)
				  .append(validateBigDecimal(qrCodeData.getAmountWithTax()));
		}

		//RQRCodeData.withholdingTaxAmount not implemented

		final String hash = String.valueOf(qrCodeData.getHash().charAt(0)) + qrCodeData.getHash().charAt(10) +
			qrCodeData.getHash().charAt(20) + qrCodeData.getHash().charAt(30);
		result.append(FIELD_SEPARATOR)
			  .append(QRCodeConstants.hash.getName()).append(DATA_SEPARATOR)
			  .append(validateString(
				  QRCodeConstants.hash,
				  hash,
				  true));
		final Optional<Application> applicationOptional = qrCodeData.getApplication().stream().findAny();

		if(applicationOptional.isPresent()) {
			result.append(FIELD_SEPARATOR)
				  .append(QRCodeConstants.certificateNumber.getName()).append(DATA_SEPARATOR)
				  .append(validateInteger(
					  QRCodeConstants.certificateNumber,
					  ((PTApplicationEntity)applicationOptional.get()).getSoftwareCertificationNumber(),
					  true));
		} else {
			throw new RequiredFieldNotFoundException(QRCodeConstants.certificateNumber.getName());
		}

		return result.toString();
	}

	private static StringBuilder buildContextSpecificParameters(
		final Map<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>> amountAndContextAndType,
		final BigDecimal exemptAmount,
		final PTContexts ptContexts) throws RequiredFieldNotFoundException
	{
		final StringBuilder result = new StringBuilder();
		Entry<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>> exemptPTEntry = null;
		boolean printedASection = false;
		for(Entry<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>> entry : amountAndContextAndType.entrySet())  {
			Context context = entry.getKey();
			Map<String, TupleTaxAmountAndAmountWithoutTaxes> value = entry.getValue();
			if (context.getUID().equals(ptContexts.getContinentalUID())) {
				printedASection = true;
				buildContextParameters(
					result,
					exemptAmount,
					(PTRegionContext) context,
					value,
					QRCodeConstants.taxCountryRegion,
					QRCodeConstants.exemptAmount,
					QRCodeConstants.reducedTaxableAmount,
					QRCodeConstants.reducedTaxAmount,
					QRCodeConstants.intermediateTaxableAmount,
					QRCodeConstants.intermediateTaxAmount,
					QRCodeConstants.regularTaxableAmount,
					QRCodeConstants.regularTaxAmount);

			} else if (context.getUID().equals(ptContexts.getAzoresUID())) {
				printedASection = true;
				buildContextParameters(
					result,
					exemptAmount,
					(PTRegionContext) context,
					value,
					QRCodeConstants.taxCountryRegionAzores,
					QRCodeConstants.exemptAmountAzores,
					QRCodeConstants.reducedTaxableAmountAzores,
					QRCodeConstants.reducedTaxAmountAzores,
					QRCodeConstants.intermediateTaxableAmountAzores,
					QRCodeConstants.intermediateTaxAmountAzores,
					QRCodeConstants.regularTaxableAmountAzores,
					QRCodeConstants.regularTaxAmountAzores);

			} else if (context.getUID().equals(ptContexts.getMadeiraUID())) {
				printedASection = true;
				buildContextParameters(
					result,
					exemptAmount,
					(PTRegionContext) context,
					value,
					QRCodeConstants.taxCountryRegionMadeira,
					QRCodeConstants.exemptAmountMadeira,
					QRCodeConstants.reducedTaxableAmountMadeira,
					QRCodeConstants.reducedTaxAmountMadeira,
					QRCodeConstants.intermediateTaxableAmountMadeira,
					QRCodeConstants.intermediateTaxAmountMadeira,
					QRCodeConstants.regularTaxableAmountMadeira,
					QRCodeConstants.regularTaxAmountMadeira);
			}
			if (context.getUID().equals(ptContexts.getPortugalUID())){
				exemptPTEntry = entry;
			}
		}
		if (!printedASection && exemptPTEntry != null) {
			Context context = exemptPTEntry.getKey();
			Map<String, TupleTaxAmountAndAmountWithoutTaxes> value = exemptPTEntry.getValue();
			buildContextParameters(
				result,
				exemptAmount,
				(PTRegionContext) context,
				value,
				QRCodeConstants.taxCountryRegion,
				QRCodeConstants.exemptAmount,
				QRCodeConstants.reducedTaxableAmount,
				QRCodeConstants.reducedTaxAmount,
				QRCodeConstants.intermediateTaxableAmount,
				QRCodeConstants.intermediateTaxAmount,
				QRCodeConstants.regularTaxableAmount,
				QRCodeConstants.regularTaxAmount);
		}

		return result;
	}

	private static void buildContextParameters(
		final StringBuilder result,
		final BigDecimal exemptAmount,
		final PTRegionContext context,
		final Map<String, TupleTaxAmountAndAmountWithoutTaxes> item,
		final Field taxCountryRegionFiled,
		final Field exemptAmountField,
		final Field reducedTaxableAmount,
		final Field reducedTaxAmount,
		final Field intermediateTaxableAmount,
		final Field intermediateTaxAmount,
		final Field regularTaxableAmount,
		final Field regularTaxAmount) throws RequiredFieldNotFoundException
	{
		result
			.append(FIELD_SEPARATOR)
			.append(taxCountryRegionFiled.getName()).append(DATA_SEPARATOR)
			.append(validateString(taxCountryRegionFiled, getRegionCodeFromISOCode(context.getRegionCode()), true));
		if (!exemptAmount.equals(BigDecimal.ZERO)) {
			result.append(FIELD_SEPARATOR).append(exemptAmountField.getName()).append(DATA_SEPARATOR).append(
				validateBigDecimal(exemptAmount));
		}
		if (item.get("RED") != null) {
			result.append(FIELD_SEPARATOR).append(reducedTaxableAmount.getName()).append(DATA_SEPARATOR).append(
				validateBigDecimal(item.get("RED").amountWithoutTaxes));
			result.append(FIELD_SEPARATOR).append(reducedTaxAmount.getName()).append(DATA_SEPARATOR).append(
				validateBigDecimal(item.get("RED").taxAmount));
		}
		if (item.get("INT") != null) {
			result.append(FIELD_SEPARATOR).append(intermediateTaxableAmount.getName()).append(DATA_SEPARATOR).append(
				validateBigDecimal(item.get("INT").amountWithoutTaxes));
			result.append(FIELD_SEPARATOR).append(intermediateTaxAmount.getName()).append(DATA_SEPARATOR).append(
				validateBigDecimal(item.get("INT").taxAmount));
		}
		if (item.get("NOR") != null) {
			result.append(FIELD_SEPARATOR).append(regularTaxableAmount.getName()).append(DATA_SEPARATOR).append(
				validateBigDecimal(item.get("NOR").amountWithoutTaxes));
			result.append(FIELD_SEPARATOR).append(regularTaxAmount.getName()).append(DATA_SEPARATOR).append(
				validateBigDecimal(item.get("NOR").taxAmount));
		}
	}

	private static Map<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>> getTaxAmountByContextAndType(
		final List<? extends GenericInvoiceEntry> entries)
	{
		return entries
			.stream()
			.flatMap(TupleContextTypeAmount.getTupleContextTypeAmountStreamFunction())
			.collect(
				Collectors.groupingBy(
					tupleContextTypeAmount -> tupleContextTypeAmount.context,
					Collectors.groupingBy(
						tupleContextTypeAmount -> tupleContextTypeAmount.type,
						Collectors.mapping(
							t -> new TupleTaxAmountAndAmountWithoutTaxes(t.taxAmount, t.amountWithoutTaxes),
							Collectors.reducing(
								new TupleTaxAmountAndAmountWithoutTaxes(BigDecimal.ZERO, BigDecimal.ZERO),
								(o, o2) ->  new TupleTaxAmountAndAmountWithoutTaxes(
									o.taxAmount.add(o2.taxAmount),
									o.amountWithoutTaxes.add(o2.amountWithoutTaxes)))))));
	}

	private static String formatDate(final Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(date);
	}

	private static String getDocumentStatus(QRCodeData qrCodeData)
	{
		if (qrCodeData.isCancelled()) {
			return "A";
		} else if (qrCodeData.isBilled()) {
			return "F";
		} else if (qrCodeData.isSelfBilled()) {
			return "S";
		}

		return "N";
	}

	private static String validateString(
		final Field field,
		final String str,
		final boolean isRequired) throws RequiredFieldNotFoundException {

		final String result;
		if (isRequired && (str == null || str.length() == 0)) {
			throw new RequiredFieldNotFoundException(field.getName());
		} else {
			if (str != null && str.length() > field.getLength()) {
				result = str.substring(0, field.getLength());
				LOG.warn("the field {} has been truncated.", field);
			} else {
				result = str;
			}
		}
		return result != null ? result.trim() : "";
	}

	private static String getRegionCodeFromISOCode(String regionCode) {
		if (regionCode.equals("PT-20")) {
			return "PT-AC";
		}
		if (regionCode.equals("PT-30")) {
			return "PT-MA";
		}
		return "PT";
	}

	private static int validateInteger(
		final Field field,
		final Integer integer,
		final boolean isRequired) throws RequiredFieldNotFoundException {

		return Integer.parseInt(validateString(field, String.valueOf(integer), isRequired));
	}

	private static BigDecimal validateBigDecimal(BigDecimal bd) {
		return bd.setScale(2, MATH_CONTEXT.getRoundingMode());
	}
}
