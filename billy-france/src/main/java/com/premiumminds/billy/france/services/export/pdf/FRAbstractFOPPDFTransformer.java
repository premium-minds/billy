/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.services.export.pdf;

import java.io.InputStream;
import java.math.MathContext;

import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.export.GenericInvoiceData;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractFOPPDFTransformer;

public abstract class FRAbstractFOPPDFTransformer<T extends GenericInvoiceData> extends AbstractFOPPDFTransformer<T> {

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

    private final FRTemplateBundle externalTranslator;

    public FRAbstractFOPPDFTransformer(Class<T> transformableClass, MathContext mc, String logoImagePath,
            InputStream xsltFileStream) {

        super(transformableClass, mc, logoImagePath, xsltFileStream);
        this.externalTranslator = null;
    }

    public FRAbstractFOPPDFTransformer(Class<T> transformableClass, MathContext mc, FRTemplateBundle bundle) {

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
                    return FRAbstractFOPPDFTransformer.BANK_TRANSFER_TEXT;
                case CASH:
                    return FRAbstractFOPPDFTransformer.CASH_TEXT;
                case CREDIT_CARD:
                    return FRAbstractFOPPDFTransformer.CREDIT_CARD_TEXT;
                case CHECK:
                    return FRAbstractFOPPDFTransformer.CHECK_TEXT;
                case DEBIT_CARD:
                    return FRAbstractFOPPDFTransformer.DEBIT_CARD_TEXT;
                case COMPENSATION:
                    return FRAbstractFOPPDFTransformer.COMPENSATION_TEXT;
                case COMMERCIAL_LETTER:
                    return FRAbstractFOPPDFTransformer.COMMERCIAL_LETTER_TEXT;
                case ATM:
                    return FRAbstractFOPPDFTransformer.ATM_TEXT;
                case RESTAURANT_TICKET:
                    return FRAbstractFOPPDFTransformer.RESTAURANT_TICKET_TEXT;
                case EXCHANGE:
                    return FRAbstractFOPPDFTransformer.EXCHANGE_TEXT;
                case ELECTRONIC_MONEY:
                    return FRAbstractFOPPDFTransformer.ELECTRONIC_MONEY_TEXT;
                default:
                    return null;
            }
        }
    }

}
