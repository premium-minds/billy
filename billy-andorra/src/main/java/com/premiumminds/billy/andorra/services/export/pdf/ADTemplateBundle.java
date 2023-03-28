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

import com.premiumminds.billy.gin.services.export.BillyTemplateBundle;

public interface ADTemplateBundle extends BillyTemplateBundle {

    static final String BANK_TRANSFER_TEXT = "Transferència Bancària";
    static final String CASH_TEXT = "Efectiu";
    static final String CREDIT_CARD_TEXT = "Targeta de Crèdit";
    static final String CHECK_TEXT = "Xec";
    static final String DEBIT_CARD_TEXT = "Targeta Dèbit";
    static final String COMPENSATION_TEXT = "Compensació de saldos en compte corrent";
    static final String COMMERCIAL_LETTER_TEXT = "Lletra Comercial";
    static final String RESTAURANT_TICKET_TEXT = "Tiquet Restaurant";
    static final String ATM_TEXT = "Datàfon";
    static final String EXCHANGE_TEXT = "Permuta";
    static final String ELECTRONIC_MONEY_TEXT = "Diners Electrònic";
}
