/**
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

import com.premiumminds.billy.gin.services.export.BillyTemplateBundle;

public interface ESTemplateBundle extends BillyTemplateBundle {
	static final String	BANK_TRANSFER_TEXT		= "Transferencia Bancaria";
	static final String	CASH_TEXT				= "Metálico";
	static final String	CREDIT_CARD_TEXT		= "Tarjeta Crédito";
	static final String	CHECK_TEXT				= "Cheque";
	static final String	DEBIT_CARD_TEXT			= "Tarjeta Débito";
	static final String	COMPENSATION_TEXT		= "Compensación de saldos en cuenta corriente";
	static final String	COMMERCIAL_LETTER_TEXT	= "Letra Comercial";
	static final String	RESTAURANT_TICKET_TEXT	= "Ticket Restaurante";
	static final String	ATM_TEXT				= "Datáfono";
	static final String	EXCHANGE_TEXT			= "Permuta";
	static final String ELECTRONIC_MONEY_TEXT	= "Dinero Electrónico";
}
