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

import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractTemplateBundle;

public abstract class ESAbstractTemplateBundle extends AbstractTemplateBundle implements ESTemplateBundle {

    public ESAbstractTemplateBundle(String logoImagePath, InputStream xsltFileStream) {
        super(logoImagePath, xsltFileStream);
    }

    @Override
    public String getPaymentMechanismTranslation(Enum<?> pmc) {
        if (null == pmc) {
            return null;
        }
        PaymentMechanism payment = (PaymentMechanism) pmc;
        switch (payment) {
            case BANK_TRANSFER:
                return ESTemplateBundle.BANK_TRANSFER_TEXT;
            case CASH:
                return ESTemplateBundle.CASH_TEXT;
            case CREDIT_CARD:
                return ESTemplateBundle.CREDIT_CARD_TEXT;
            case CHECK:
                return ESTemplateBundle.CHECK_TEXT;
            case DEBIT_CARD:
                return ESTemplateBundle.DEBIT_CARD_TEXT;
            case COMPENSATION:
                return ESTemplateBundle.COMPENSATION_TEXT;
            case COMMERCIAL_LETTER:
                return ESTemplateBundle.COMMERCIAL_LETTER_TEXT;
            case ATM:
                return ESTemplateBundle.ATM_TEXT;
            case RESTAURANT_TICKET:
                return ESTemplateBundle.RESTAURANT_TICKET_TEXT;
            case EXCHANGE:
                return ESTemplateBundle.EXCHANGE_TEXT;
            case ELECTRONIC_MONEY:
                return ESTemplateBundle.ELECTRONIC_MONEY_TEXT;
            default:
                return null;
        }
    }

}
