/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.services.export.pdf;

import java.io.InputStream;
import java.math.MathContext;

import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.export.GenericInvoiceData;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractFOPPDFTransformer;

public abstract class ADAbstractFOPPDFTransformer<T extends GenericInvoiceData> extends AbstractFOPPDFTransformer<T> {

    static final String BANK_TRANSFER_TEXT = "Transferencia Bancaria";
    static final String CASH_TEXT = "Metálico";
    static final String CREDIT_CARD_TEXT = "Tarjeta Crédito";
    static final String CHECK_TEXT = "Cheque";
    static final String DEBIT_CARD_TEXT = "Tarjeta Débito";
    static final String COMPENSATION_TEXT = "Compensación de saldos en cuenta corriente";
    static final String COMMERCIAL_LETTER_TEXT = "Letra Comercial";
    static final String RESTAURANT_TICKET_TEXT = "Ticket Restaurante";
    static final String ATM_TEXT = "Datáfono";
    static final String EXCHANGE_TEXT = "Permuta";
    static final String ELECTRONIC_MONEY_TEXT = "Dinero Electrónico";

    private final ADTemplateBundle externalTranslator;

    public ADAbstractFOPPDFTransformer(Class<T> transformableClass, MathContext mc, String logoImagePath,
									   InputStream xsltFileStream) {

        super(transformableClass, mc, logoImagePath, xsltFileStream);
        this.externalTranslator = null;
    }

    public ADAbstractFOPPDFTransformer(Class<T> transformableClass, MathContext mc, ADTemplateBundle bundle) {

        super(transformableClass, mc, bundle.getLogoImagePath(), bundle.getXSLTFileStream());
        this.externalTranslator = bundle;
    }

    @Override
    protected String getPaymentMechanismTranslation(Enum<?> pmc) {
        if (null == pmc) {
            return null;
        }

        if (this.externalTranslator != null) {
            return this.externalTranslator.getPaymentMechanismTranslation(pmc);
        } else {
            PaymentMechanism payment = (PaymentMechanism) pmc;
            switch (payment) {
                case BANK_TRANSFER:
                    return ADAbstractFOPPDFTransformer.BANK_TRANSFER_TEXT;
                case CASH:
                    return ADAbstractFOPPDFTransformer.CASH_TEXT;
                case CREDIT_CARD:
                    return ADAbstractFOPPDFTransformer.CREDIT_CARD_TEXT;
                case CHECK:
                    return ADAbstractFOPPDFTransformer.CHECK_TEXT;
                case DEBIT_CARD:
                    return ADAbstractFOPPDFTransformer.DEBIT_CARD_TEXT;
                case COMPENSATION:
                    return ADAbstractFOPPDFTransformer.COMPENSATION_TEXT;
                case COMMERCIAL_LETTER:
                    return ADAbstractFOPPDFTransformer.COMMERCIAL_LETTER_TEXT;
                case ATM:
                    return ADAbstractFOPPDFTransformer.ATM_TEXT;
                case RESTAURANT_TICKET:
                    return ADAbstractFOPPDFTransformer.RESTAURANT_TICKET_TEXT;
                case EXCHANGE:
                    return ADAbstractFOPPDFTransformer.EXCHANGE_TEXT;
                case ELECTRONIC_MONEY:
                    return ADAbstractFOPPDFTransformer.ELECTRONIC_MONEY_TEXT;
                default:
                    return null;
            }
        }
    }

}
