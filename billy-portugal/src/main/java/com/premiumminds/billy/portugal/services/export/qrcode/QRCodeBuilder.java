/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.export.qrcode;

import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.export.exceptions.RequiredFieldNotFoundException;
import com.premiumminds.billy.portugal.services.export.qrcode.QRCodeConstants.TaxBreakdown;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
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

		result.append(buildContextSpecificParameters(amountAndContextAndType, qrCodeData.getPtContexts()));

		if(!exemptAmount.equals(BigDecimal.ZERO)) {
			result.append(FIELD_SEPARATOR)
				  .append(QRCodeConstants.NSOrNTAmount.getName()).append(DATA_SEPARATOR)
				  .append(validateBigDecimal(exemptAmount));
		}

		//QRCodeData.stampDuty not implemented

		result.append(FIELD_SEPARATOR)
			  .append(QRCodeConstants.taxPayable.getName()).append(DATA_SEPARATOR)
			  .append(validateBigDecimal(qrCodeData.getTaxAmount()));

		result.append(FIELD_SEPARATOR)
			  .append(QRCodeConstants.grossTotal.getName()).append(DATA_SEPARATOR)
			  .append(validateBigDecimal(qrCodeData.getAmountWithTax()));

		//RQRCodeData.withholdingTaxAmount not implemented

		String hashString = qrCodeData.getHash();
		final String hash = String.valueOf(hashString.charAt(0)) + hashString.charAt(10) +
			hashString.charAt(20) + hashString.charAt(30);
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
		final Map<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>> amountAndContextAndType, final PTContexts ptContexts) throws RequiredFieldNotFoundException
	{
		final StringBuilder result = new StringBuilder();
		boolean printedASection = false;

		final Optional<Entry<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>>> continentEntry =
			amountAndContextAndType
				.entrySet()
				.stream()
				.filter(contextMapEntry -> contextMapEntry.getKey().getUID().equals(ptContexts.getContinentalUID()))
				.findAny();
		Queue<TaxBreakdown> taxBreakdowns = new LinkedList<TaxBreakdown>() {};
		taxBreakdowns.add(QRCodeConstants.I);
		taxBreakdowns.add(QRCodeConstants.J);
		taxBreakdowns.add(QRCodeConstants.K);


		if (continentEntry.isPresent()) {
			printedASection = true;
			buildContextParameters(
				result,
				(PTRegionContext) continentEntry.get().getKey(),
				continentEntry.get().getValue(),
				Objects.requireNonNull(taxBreakdowns.poll()));
		}

		final Optional<Entry<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>>> azoresEntry =
			amountAndContextAndType
				.entrySet()
				.stream()
				.filter(contextMapEntry -> contextMapEntry.getKey().getUID().equals(ptContexts.getAzoresUID()))
				.findAny();
		if (azoresEntry.isPresent()) {
			printedASection = true;
			buildContextParameters(
				result,
				(PTRegionContext) azoresEntry.get().getKey(),
				azoresEntry.get().getValue(),
				Objects.requireNonNull(taxBreakdowns.poll()));
		}

		final Optional<Entry<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>>> madeiraEntry =
			amountAndContextAndType
				.entrySet()
				.stream()
				.filter(contextMapEntry -> contextMapEntry.getKey().getUID().equals(ptContexts.getMadeiraUID()))
				.findAny();
		if (madeiraEntry.isPresent()) {
			printedASection = true;
			buildContextParameters(
				result,
				(PTRegionContext) madeiraEntry.get().getKey(),
				madeiraEntry.get().getValue(),
				Objects.requireNonNull(taxBreakdowns.poll()));
		}

		final Optional<Entry<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>>> portugalEntry =
			amountAndContextAndType
				.entrySet()
				.stream()
				.filter(contextMapEntry -> contextMapEntry.getKey().getUID().equals(ptContexts.getPortugalUID()))
				.findAny();
		if (!printedASection && portugalEntry.isPresent()) {
			buildContextParameters(
				result,
				(PTRegionContext) portugalEntry.get().getKey(),
				portugalEntry.get().getValue(),
				Objects.requireNonNull(taxBreakdowns.poll()));
		}

		return result;
	}

	private static void buildContextParameters(
		final StringBuilder result,
		final PTRegionContext context,
		final Map<String, TupleTaxAmountAndAmountWithoutTaxes> item,
		final TaxBreakdown taxBreakdown) throws RequiredFieldNotFoundException
	{
		result
			.append(FIELD_SEPARATOR)
			.append(taxBreakdown.taxCountryRegion.getName()).append(DATA_SEPARATOR)
			.append(validateString(taxBreakdown.taxCountryRegion, getRegionCodeFromISOCode(context.getRegionCode()), true));

		//Not done QRCodeConstants.exemptAmount
		if (item.get("RED") != null) {
			result.append(FIELD_SEPARATOR).append(taxBreakdown.reducedTaxableAmount.getName()).append(DATA_SEPARATOR).append(
				validateBigDecimal(item.get("RED").amountWithoutTaxes));
			result.append(FIELD_SEPARATOR).append(taxBreakdown.reducedTaxAmount.getName()).append(DATA_SEPARATOR).append(
				validateBigDecimal(item.get("RED").taxAmount));
		}
		if (item.get("INT") != null) {
			result.append(FIELD_SEPARATOR).append(taxBreakdown.intermediateTaxableAmount.getName()).append(DATA_SEPARATOR).append(
				validateBigDecimal(item.get("INT").amountWithoutTaxes));
			result.append(FIELD_SEPARATOR).append(taxBreakdown.intermediateTaxAmount.getName()).append(DATA_SEPARATOR).append(
				validateBigDecimal(item.get("INT").taxAmount));
		}
		if (item.get("NOR") != null) {
			result.append(FIELD_SEPARATOR).append(taxBreakdown.regularTaxableAmount.getName()).append(DATA_SEPARATOR).append(
				validateBigDecimal(item.get("NOR").amountWithoutTaxes));
			result.append(FIELD_SEPARATOR).append(taxBreakdown.regularTaxAmount.getName()).append(DATA_SEPARATOR).append(
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
		DateFormat dateFormat = new SimpleDateFormat(QRCodeConstants.DATE_FORMAT);
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
		final QRCodeConstants.Field field,
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
		final QRCodeConstants.Field field,
		final Integer integer,
		final boolean isRequired) throws RequiredFieldNotFoundException {

		return Integer.parseInt(validateString(field, String.valueOf(integer), isRequired));
	}

	private static BigDecimal validateBigDecimal(BigDecimal bd) {
		return bd.setScale(2, MATH_CONTEXT.getRoundingMode());
	}
}
