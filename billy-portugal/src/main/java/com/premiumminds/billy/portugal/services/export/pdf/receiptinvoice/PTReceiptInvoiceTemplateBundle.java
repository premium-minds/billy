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
package com.premiumminds.billy.portugal.services.export.pdf.receiptinvoice;

import java.io.InputStream;

import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractTemplateBundle;
import com.premiumminds.billy.portugal.services.export.pdf.PTTemplateBundle;

public class PTReceiptInvoiceTemplateBundle extends AbstractTemplateBundle implements PTTemplateBundle {

    private static final String GENERIC_CUSTOMER_TEXT = "Consumidor Final";
    private static final String BANK_TRANSFER_TEXT = "Transferência Bancária";
    private static final String CASH_TEXT = "Numerário";
    private static final String CREDIT_CARD_TEXT = "Cartão Crédito";
    private static final String CHECK_TEXT = "Cheque";
    private static final String DEBIT_CARD_TEXT = "Cartão Débito";
    private static final String COMPENSATION_TEXT = "Compensação de saldos em conta corrente";
    private static final String COMMERCIAL_LETTER_TEXT = "Letra Comercial";
    private static final String RESTAURANT_TICKET_TEXT = "Ticket Restaurante";
    private static final String ATM_TEXT = "Multibanco";
    private static final String EXCHANGE_TEXT = "Permuta";
    private static final String ELECTRONIC_MONEY_TEXT = "Dinheiro Eletrónico";
    private final String softwareCertificationId;

    public PTReceiptInvoiceTemplateBundle(String logoImagePath, InputStream xsltFileStream,
            String softwareCertificationId) {

        super(logoImagePath, xsltFileStream);
        this.softwareCertificationId = softwareCertificationId;
    }

    @Override
    public String getGenericCustomer() {
        return PTReceiptInvoiceTemplateBundle.GENERIC_CUSTOMER_TEXT;
    }

    @Override
    public String getSoftwareCertificationId() {
        return this.softwareCertificationId;
    }

    @Override
    public String getPaymentMechanismTranslation(Enum<?> pmc) {
        if (null == pmc) {
            return null;
        }
        PaymentMechanism payment = (PaymentMechanism) pmc;
        switch (payment) {
            case BANK_TRANSFER:
                return PTReceiptInvoiceTemplateBundle.BANK_TRANSFER_TEXT;
            case CASH:
                return PTReceiptInvoiceTemplateBundle.CASH_TEXT;
            case CREDIT_CARD:
                return PTReceiptInvoiceTemplateBundle.CREDIT_CARD_TEXT;
            case CHECK:
                return PTReceiptInvoiceTemplateBundle.CHECK_TEXT;
            case DEBIT_CARD:
                return PTReceiptInvoiceTemplateBundle.DEBIT_CARD_TEXT;
            case COMPENSATION:
                return PTReceiptInvoiceTemplateBundle.COMPENSATION_TEXT;
            case COMMERCIAL_LETTER:
                return PTReceiptInvoiceTemplateBundle.COMMERCIAL_LETTER_TEXT;
            case ATM:
                return PTReceiptInvoiceTemplateBundle.ATM_TEXT;
            case RESTAURANT_TICKET:
                return PTReceiptInvoiceTemplateBundle.RESTAURANT_TICKET_TEXT;
            case EXCHANGE:
                return PTReceiptInvoiceTemplateBundle.EXCHANGE_TEXT;
            case ELECTRONIC_MONEY:
                return PTReceiptInvoiceTemplateBundle.ELECTRONIC_MONEY_TEXT;
            default:
                return null;
        }
    }
}
