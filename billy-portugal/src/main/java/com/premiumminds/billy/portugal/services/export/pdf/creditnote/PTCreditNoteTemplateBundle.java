/**
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
package com.premiumminds.billy.portugal.services.export.pdf.creditnote;

import java.io.InputStream;

import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractTemplateBundle;
import com.premiumminds.billy.portugal.services.export.pdf.PTTemplateBundle;

public class PTCreditNoteTemplateBundle extends AbstractTemplateBundle
	implements PTTemplateBundle {

	private static final String	GENERIC_CUSTOMER_TEXT	= "Consumidor Final";
	private static final String	BANK_TRANSFER_TEXT		= "Transferência bancária";
	private static final String	CASH_TEXT				= "Numerário";
	private static final String	CREDIT_CARD_TEXT		= "Cartão crédito";
	private static final String	CHECK_TEXT				= "Cheque";
	private static final String	DEBIT_CARD_TEXT			= "Cartão débito";
	private static final String	COMPENSATION_TEXT		= "Compensação de saldos em conta corrente";
	private static final String	COMMERCIAL_LETTER_TEXT	= " Letra comercial";
	private static final String	RESTAURANT_TICKET_TEXT	= "Ticket restaurante";
	private static final String	ATM_TEXT				= "Multibanco";
	private static final String	EXCHANGE_TEXT			= "Permuta";
	private static final String ELECTRONIC_MONEY_TEXT	= "Dinheiro eletrónico";
	private final String		softwareCertificationId;

	public PTCreditNoteTemplateBundle(String logoImagePath,
										InputStream xsltFileStream,
										String softwareCertificationId) {

		super(logoImagePath, xsltFileStream);
		this.softwareCertificationId = softwareCertificationId;
	}

	@Override
	public String getGenericCustomer() {
		return PTCreditNoteTemplateBundle.GENERIC_CUSTOMER_TEXT;
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
				return PTCreditNoteTemplateBundle.BANK_TRANSFER_TEXT;
			case CASH:
				return PTCreditNoteTemplateBundle.CASH_TEXT;
			case CREDIT_CARD:
				return PTCreditNoteTemplateBundle.CREDIT_CARD_TEXT;
			case CHECK:
				return PTCreditNoteTemplateBundle.CHECK_TEXT;
			case DEBIT_CARD:
				return PTCreditNoteTemplateBundle.DEBIT_CARD_TEXT;
			case COMPENSATION:
				return PTCreditNoteTemplateBundle.COMPENSATION_TEXT;
			case COMMERCIAL_LETTER:
				return PTCreditNoteTemplateBundle.COMMERCIAL_LETTER_TEXT;
			case ATM:
				return PTCreditNoteTemplateBundle.ATM_TEXT;
			case RESTAURANT_TICKET:
				return PTCreditNoteTemplateBundle.RESTAURANT_TICKET_TEXT;
			case EXCHANGE:
				return PTCreditNoteTemplateBundle.EXCHANGE_TEXT;
			case ELECTRONIC_MONEY:
				return PTCreditNoteTemplateBundle.ELECTRONIC_MONEY_TEXT;
			default:
				return null;
		}
	}

}
