/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.services.export.pdf;

import java.io.InputStream;
import java.math.MathContext;

import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.export.GenericInvoiceData;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractFOPPDFTransformer;

public abstract class ESAbstractFOPPDFTransformer<T extends GenericInvoiceData> extends AbstractFOPPDFTransformer<T> {

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

    private final ESTemplateBundle externalTranslator;

    public ESAbstractFOPPDFTransformer(Class<T> transformableClass, MathContext mc, String logoImagePath,
            InputStream xsltFileStream) {

        super(transformableClass, mc, logoImagePath, xsltFileStream);
        this.externalTranslator = null;
    }

    public ESAbstractFOPPDFTransformer(Class<T> transformableClass, MathContext mc, ESTemplateBundle bundle) {

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
                    return ESAbstractFOPPDFTransformer.BANK_TRANSFER_TEXT;
                case CASH:
                    return ESAbstractFOPPDFTransformer.CASH_TEXT;
                case CREDIT_CARD:
                    return ESAbstractFOPPDFTransformer.CREDIT_CARD_TEXT;
                case CHECK:
                    return ESAbstractFOPPDFTransformer.CHECK_TEXT;
                case DEBIT_CARD:
                    return ESAbstractFOPPDFTransformer.DEBIT_CARD_TEXT;
                case COMPENSATION:
                    return ESAbstractFOPPDFTransformer.COMPENSATION_TEXT;
                case COMMERCIAL_LETTER:
                    return ESAbstractFOPPDFTransformer.COMMERCIAL_LETTER_TEXT;
                case ATM:
                    return ESAbstractFOPPDFTransformer.ATM_TEXT;
                case RESTAURANT_TICKET:
                    return ESAbstractFOPPDFTransformer.RESTAURANT_TICKET_TEXT;
                case EXCHANGE:
                    return ESAbstractFOPPDFTransformer.EXCHANGE_TEXT;
                case ELECTRONIC_MONEY:
                    return ESAbstractFOPPDFTransformer.ELECTRONIC_MONEY_TEXT;
                default:
                    return null;
            }
        }
    }

}
