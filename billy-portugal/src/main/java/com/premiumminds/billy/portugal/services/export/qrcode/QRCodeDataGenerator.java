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

import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.Config.Key;
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
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QRCodeDataGenerator {
    private static final Logger log = LoggerFactory.getLogger(QRCodeDataGenerator.class);
    private static final String DATA_SEPARATOR = ":";
    private static final String FIELD_SEPARATOR = "*";

    private static final MathContext MATH_CONTEXT = BillyMathContext.get();

    private final Config config;
    private final DAOInvoiceSeries daoInvoiceSeries;
    private final UID portugalUID;
    private final UID continentalUID;
    private final UID azoresUID;
    private final UID madeiraUID;

    @Inject
    public QRCodeDataGenerator(
        final DAOInvoiceSeries daoInvoiceSeries) {
        this.daoInvoiceSeries = daoInvoiceSeries;
        this.config = new Config();

        this.portugalUID = this.config.getUID(Config.Key.Context.Portugal.UUID);
        this.continentalUID = this.config.getUID(Config.Key.Context.Portugal.Continental.UUID);
        this.azoresUID = this.config.getUID(Config.Key.Context.Portugal.Azores.UUID);
        this.madeiraUID = this.config.getUID(Config.Key.Context.Portugal.Madeira.UUID);
    }

    public String generateQRCodeData(PTGenericInvoice document) throws RequiredFieldNotFoundException {
        final StringBuilder result = new StringBuilder();

        final Customer customer = document.getCustomer();
        final String customerFinancialID;
        if (this.config.getUID(Key.Customer.Generic.UUID).equals(customer.getUID())) {
            customerFinancialID = "999999990";
        } else {
            customerFinancialID = customer.getTaxRegistrationNumber();
        }

        final InvoiceSeriesEntity invoiceSeries =
            this.daoInvoiceSeries.getSeries(
                document.getSeries(),
                document.getBusiness().getUID().toString(),
                LockModeType.NONE);

        result
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

        final String atcud = invoiceSeries
            .getSeriesUniqueCode()
            .map(s -> new StringBuilder()
                .append(s)
                .append("-")
                .append(document.getSeriesNumber()))
            .orElse(new StringBuilder().append("0")).toString();

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
            if(entry.getKey().getUID().equals(portugalUID)){
                TupleTaxAmountAndAmountWithoutTaxes ise = entry.getValue().get("ISE");
                if(ise != null) {
                    exemptAmount = ise.amountWithoutTaxes;
                }
            }
        }

        result.append(buildContextSpecificParameters(amountAndContextAndType, exemptAmount));

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

        result.append(FIELD_SEPARATOR)
              .append(QRCodeData.hash.getName()).append(DATA_SEPARATOR)
              .append(validateString(
                  QRCodeData.hash,
                  document.getHash(),
                  true));

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

    private StringBuilder buildContextSpecificParameters(
        final Map<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>> amountAndContextAndType,
        final BigDecimal exemptAmount) throws RequiredFieldNotFoundException
    {
        final StringBuilder result = new StringBuilder();
        for(Entry<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>> entry : amountAndContextAndType.entrySet())  {
            Context context = entry.getKey();
            Map<String, TupleTaxAmountAndAmountWithoutTaxes> value = entry.getValue();
            if (context.getUID().equals(continentalUID)) {
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

            } else if (context.getUID().equals(azoresUID)) {
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

            } else if (context.getUID().equals(madeiraUID)) {
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
        }
        return result;
    }

    private void buildContextParameters(
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

    private Map<Context, Map<String, TupleTaxAmountAndAmountWithoutTaxes>> getTaxAmountByContextAndType(
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

    static class TupleTaxAmountAndAmountWithoutTaxes{
        public final BigDecimal taxAmount;
        public final BigDecimal amountWithoutTaxes;

        TupleTaxAmountAndAmountWithoutTaxes(final BigDecimal taxAmount, final BigDecimal amountWithoutTaxes) {
            this.taxAmount = taxAmount;
            this.amountWithoutTaxes = amountWithoutTaxes;
        }
    }

    static class TupleContextTypeAmount {
        public final Context context;
        public final String type;
        public final BigDecimal taxAmount;
        public final BigDecimal amountWithoutTaxes;

        TupleContextTypeAmount(
            final Context context, final String type, final BigDecimal taxAmount, final BigDecimal amountWithoutTaxes) {
            this.context = context;
            this.type = type;
            this.taxAmount = taxAmount;
            this.amountWithoutTaxes = amountWithoutTaxes;
        }

        public static Function<Tax, TupleContextTypeAmount> taxToTupleContextTypeAmount(final BigDecimal amountWithoutTaxes) {
            return tax -> {
                Context context = tax.getContext();
                String code = tax.getCode();
                BigDecimal taxValue = tax.getValue();
                final BigDecimal taxAmount;
                switch (tax.getTaxRateType()) {
                    case FLAT:
                        taxAmount = taxValue;
                        break;
                    case PERCENTAGE:
                        taxAmount = amountWithoutTaxes.multiply(taxValue.divide(new BigDecimal(100)));
                        break;
                    case NONE:
                    default:
                        taxAmount = BigDecimal.ZERO;
                }
                return new TupleContextTypeAmount(context, code, taxAmount, amountWithoutTaxes);
            };
        }

        public static Function<GenericInvoiceEntry, Stream<TupleContextTypeAmount>> getTupleContextTypeAmountStreamFunction() {
            return entry -> {
                BigDecimal amountWithoutTax = entry.getAmountWithoutTax();
                return entry.getTaxes().stream().map(TupleContextTypeAmount.taxToTupleContextTypeAmount(amountWithoutTax));
            };
        }
    }

	private String formatDate(final Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(date);
	}

    private String getDocumentStatus(PTGenericInvoice document)
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
                log.warn("the field {} has been truncated.", field);
            } else {
                result = str;
            }
        }
        return result != null ? result.trim() : "";
    }

    private String getRegionCodeFromISOCode(String regionCode) {
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
