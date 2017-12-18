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

import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractTemplateBundle;

public abstract class FRAbstractTemplateBundle extends AbstractTemplateBundle implements FRTemplateBundle {

    public FRAbstractTemplateBundle(String logoImagePath, InputStream xsltFileStream) {
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
                return FRTemplateBundle.BANK_TRANSFER_TEXT;
            case CASH:
                return FRTemplateBundle.CASH_TEXT;
            case CREDIT_CARD:
                return FRTemplateBundle.CREDIT_CARD_TEXT;
            case CHECK:
                return FRTemplateBundle.CHECK_TEXT;
            case DEBIT_CARD:
                return FRTemplateBundle.DEBIT_CARD_TEXT;
            case COMPENSATION:
                return FRTemplateBundle.COMPENSATION_TEXT;
            case COMMERCIAL_LETTER:
                return FRTemplateBundle.COMMERCIAL_LETTER_TEXT;
            case ATM:
                return FRTemplateBundle.ATM_TEXT;
            case RESTAURANT_TICKET:
                return FRTemplateBundle.RESTAURANT_TICKET_TEXT;
            case EXCHANGE:
                return FRTemplateBundle.EXCHANGE_TEXT;
            case ELECTRONIC_MONEY:
                return FRTemplateBundle.ELECTRONIC_MONEY_TEXT;
            default:
                return null;
        }
    }

}
