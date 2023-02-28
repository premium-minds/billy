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

import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractTemplateBundle;

public abstract class ADAbstractTemplateBundle extends AbstractTemplateBundle implements ADTemplateBundle {

    public ADAbstractTemplateBundle(String logoImagePath, InputStream xsltFileStream) {
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
                return BANK_TRANSFER_TEXT;
            case CASH:
                return CASH_TEXT;
            case CREDIT_CARD:
                return CREDIT_CARD_TEXT;
            case CHECK:
                return CHECK_TEXT;
            case DEBIT_CARD:
                return DEBIT_CARD_TEXT;
            case COMPENSATION:
                return COMPENSATION_TEXT;
            case COMMERCIAL_LETTER:
                return COMMERCIAL_LETTER_TEXT;
            case ATM:
                return ATM_TEXT;
            case RESTAURANT_TICKET:
                return RESTAURANT_TICKET_TEXT;
            case EXCHANGE:
                return EXCHANGE_TEXT;
            case ELECTRONIC_MONEY:
                return ELECTRONIC_MONEY_TEXT;
            default:
                return null;
        }
    }

}
