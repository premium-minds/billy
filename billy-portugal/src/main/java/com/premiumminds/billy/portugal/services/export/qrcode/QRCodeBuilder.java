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

import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.export.exceptions.RequiredFieldNotFoundException;
import com.premiumminds.billy.portugal.services.export.qrcode.QRCodeData.Field;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	public static String generateQRCodeString(
		final PTGenericInvoice document,
		final Customer customer,
		final String customerFinancialID,
		final InvoiceSeriesEntity invoiceSeries,
		final PTContexts ptContexts) throws RequiredFieldNotFoundException
	{
		final String atcud = invoiceSeries
			.getSeriesUniqueCode()
			.map(s -> new StringBuilder()
				.append(s)
				.append("-")
				.append(document.getSeriesNumber()))
			.orElse(new StringBuilder().append("0")).toString();

		final StringBuilder result = new StringBuilder()
			.append(QRCodeData.emitterFinancialId.getName()).append(DATA_SEPARATOR)
			.append(validateString(QRCodeData.emitterFinancialId, document.getBusiness().getFinancialID(), true))
			.append(FIELD_SEPARATOR)
			.append(QRCodeData.buyerFinancialId.getName()).append(DATA_SEPARATOR)
			.append(validateString(QRCodeData.buyerFinancialId, customerFinancialID, true))
			.append(FIELD_SEPARATOR)
			.append(QRCodeData.buyerCountry.getName()).append(DATA_SEPARATOR)
			.append(validateString(QRCodeData.buyerCountry, customer.getBillingAddress().getISOCountry(), true))
			.append(FIELD_SEPARATOR)
			.append(QRCodeData.documentType.getName()).append(DATA_SEPARATOR)
			.append(validateString(QRCodeData.documentType, document.getType().name(), true))
			.append(FIELD_SEPARATOR)
			.append(QRCodeData.documentStatus.getName()).append(DATA_SEPARATOR)
			.append(validateString(QRCodeData.documentStatus, getDocumentStatus(document), true))
			.append(FIELD_SEPARATOR)
			.append(QRCodeData.documentDate.getName()).append(DATA_SEPARATOR)
			.append(validateString(QRCodeData.documentDate, formatDate(document.getDate()), true))
			.append(FIELD_SEPARATOR)
			.append(QRCodeData.documentUniqueID.getName()).append(DATA_SEPARATOR)
			.append(validateString(QRCodeData.documentUniqueID, document.getNumber() , true))
			;

		if(!atcud.isEmpty()) {
			result
				.append(FIELD_SEPARATOR)
				.append(QRCodeData.ATCUD.getName()).append(DATA_SEPARATOR)
				.append(validateString(QRCodeData.ATCUD, atcud, true));
		}

		final Map<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>> amountAndContextAndType =
			getTaxAmountByContextAndType(document);

		BigDecimal exemptAmount = BigDecimal.ZERO;
		for(Entry<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>> entry : amountAndContextAndType.entrySet()){
			if(entry.getKey().getUID().equals(ptContexts.getPortugalUID())){
				TupleTaxAmountAndAmountWithoutTaxes ise = entry.getValue().get("ISE");
				if(ise != null) {
					exemptAmount = ise.amountWithoutTaxes;
				}
			}
		}

		result.append(buildContextSpecificParameters(amountAndContextAndType, exemptAmount, ptContexts));

		if(!exemptAmount.equals(BigDecimal.ZERO)) {
			result.append(FIELD_SEPARATOR)
				  .append(QRCodeData.NSOrNTAmount.getName()).append(DATA_SEPARATOR)
				  .append(validateBigDecimal(exemptAmount));
		}

		//QRCodeData.stampDuty not implemented

		if(!document.getTaxAmount().equals(BigDecimal.ZERO)) {
			result.append(FIELD_SEPARATOR)
				  .append(QRCodeData.taxPayable.getName()).append(DATA_SEPARATOR)
				  .append(validateBigDecimal(document.getTaxAmount()));
		}

		if(!document.getAmountWithTax().equals(BigDecimal.ZERO)) {
			result.append(FIELD_SEPARATOR)
				  .append(QRCodeData.grossTotal.getName()).append(DATA_SEPARATOR)
				  .append(validateBigDecimal(document.getAmountWithTax()));
		}

		//RQRCodeData.withholdingTaxAmount not implemented

		final String hash = String.valueOf(document.getHash().charAt(0)) + document.getHash().charAt(10) +
			document.getHash().charAt(20) + document.getHash().charAt(30);
		result.append(FIELD_SEPARATOR)
			  .append(QRCodeData.hash.getName()).append(DATA_SEPARATOR)
			  .append(validateString(
				  QRCodeData.hash,
				  hash,
				  true));
		// 1.ª, 11.ª, 21.ª e 31.ª
		final Optional<Application> applicationOptional = document.getBusiness().getApplications().stream().findAny();

		if(applicationOptional.isPresent()) {
			result.append(FIELD_SEPARATOR)
				  .append(QRCodeData.certificateNumber.getName()).append(DATA_SEPARATOR)
				  .append(validateInteger(
					  QRCodeData.certificateNumber,
					  ((PTApplicationEntity)applicationOptional.get()).getSoftwareCertificationNumber(),
					  true));
		} else {
			throw new RequiredFieldNotFoundException(QRCodeData.certificateNumber.getName());
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
					QRCodeData.taxCountryRegion,
					QRCodeData.exemptAmount,
					QRCodeData.reducedTaxableAmount,
					QRCodeData.reducedTaxAmount,
					QRCodeData.intermediateTaxableAmount,
					QRCodeData.intermediateTaxAmount,
					QRCodeData.regularTaxableAmount,
					QRCodeData.regularTaxAmount);

			} else if (context.getUID().equals(ptContexts.getAzoresUID())) {
				printedASection = true;
				buildContextParameters(
					result,
					exemptAmount,
					(PTRegionContext) context,
					value,
					QRCodeData.taxCountryRegionAzores,
					QRCodeData.exemptAmountAzores,
					QRCodeData.reducedTaxableAmountAzores,
					QRCodeData.reducedTaxAmountAzores,
					QRCodeData.intermediateTaxableAmountAzores,
					QRCodeData.intermediateTaxAmountAzores,
					QRCodeData.regularTaxableAmountAzores,
					QRCodeData.regularTaxAmountAzores);

			} else if (context.getUID().equals(ptContexts.getMadeiraUID())) {
				printedASection = true;
				buildContextParameters(
					result,
					exemptAmount,
					(PTRegionContext) context,
					value,
					QRCodeData.taxCountryRegionMadeira,
					QRCodeData.exemptAmountMadeira,
					QRCodeData.reducedTaxableAmountMadeira,
					QRCodeData.reducedTaxAmountMadeira,
					QRCodeData.intermediateTaxableAmountMadeira,
					QRCodeData.intermediateTaxAmountMadeira,
					QRCodeData.regularTaxableAmountMadeira,
					QRCodeData.regularTaxAmountMadeira);
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
				QRCodeData.taxCountryRegion,
				QRCodeData.exemptAmount,
				QRCodeData.reducedTaxableAmount,
				QRCodeData.reducedTaxAmount,
				QRCodeData.intermediateTaxableAmount,
				QRCodeData.intermediateTaxAmount,
				QRCodeData.regularTaxableAmount,
				QRCodeData.regularTaxAmount);
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
		final PTGenericInvoice document)
	{
		return document.getEntries()
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

	private static String getDocumentStatus(PTGenericInvoice document)
	{
		if (document.isCancelled()) {
			return "A";
		} else if (document.isBilled()) {
			return "F";
		} else if (document.isSelfBilled()) {
			return "S";
		}

		return "N";
	}

	private static String validateString(
		final QRCodeData.Field field,
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
		final QRCodeData.Field field,
		final Integer integer,
		final boolean isRequired) throws RequiredFieldNotFoundException {

		return Integer.parseInt(validateString(field, String.valueOf(integer), isRequired));
	}

	private static BigDecimal validateBigDecimal(BigDecimal bd) {
		return bd.setScale(2, MATH_CONTEXT.getRoundingMode());
	}
}
