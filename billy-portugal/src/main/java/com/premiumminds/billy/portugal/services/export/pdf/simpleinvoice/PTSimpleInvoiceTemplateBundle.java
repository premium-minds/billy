/**
 * Copyright (C) 2013 Premium Minds.
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
package com.premiumminds.billy.portugal.services.export.pdf.simpleinvoice;

import java.io.InputStream;

import com.premiumminds.billy.gin.services.impl.pdf.AbstractTemplateBundle;
import com.premiumminds.billy.portugal.services.export.pdf.PTTemplateBundle;
import com.premiumminds.billy.portugal.util.PaymentMechanism;

public class PTSimpleInvoiceTemplateBundle extends AbstractTemplateBundle
		implements PTTemplateBundle {

	private static final String GENERIC_CUSTOMER_TEXT = "Consumidor Final";
	private static final String BANK_TRANSFER_TEXT = "Transferência bancária";
	private static final String CASH_TEXT = "Numerário";
	private static final String CREDIT_CARD_TEXT = "Cartão crédito";
	private static final String CHECK_TEXT = "Cheque";
	private static final String DEBIT_CARD_TEXT = "Cartão débito";
	private static final String COMPENSATION_TEXT = "Compensação de saldos em conta corrente";
	private static final String COMMERCIAL_LETTER_TEXT = " Letra comercial";
	private static final String RESTAURANT_TICKET_TEXT = "Ticket restaurante";
	private static final String ATM_TEXT = "Multibanco";
	private static final String EXCHANGE_TEXT = "Permuta";
	private final String softwareCertificationId;

	public PTSimpleInvoiceTemplateBundle(String logoImagePath,
			InputStream xsltFileStream, String resultFilePath,
			String softwareCertificationId) {

		super(logoImagePath, xsltFileStream, resultFilePath);
		this.softwareCertificationId = softwareCertificationId;
	}

	@Override
	public String getGenericCustomer() {
		return PTSimpleInvoiceTemplateBundle.GENERIC_CUSTOMER_TEXT;
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
			return PTSimpleInvoiceTemplateBundle.BANK_TRANSFER_TEXT;
		case CASH:
			return PTSimpleInvoiceTemplateBundle.CASH_TEXT;
		case CREDIT_CARD:
			return PTSimpleInvoiceTemplateBundle.CREDIT_CARD_TEXT;
		case CHECK:
			return PTSimpleInvoiceTemplateBundle.CHECK_TEXT;
		case DEBIT_CARD:
			return PTSimpleInvoiceTemplateBundle.DEBIT_CARD_TEXT;
		case COMPENSATION:
			return PTSimpleInvoiceTemplateBundle.COMPENSATION_TEXT;
		case COMMERCIAL_LETTER:
			return PTSimpleInvoiceTemplateBundle.COMMERCIAL_LETTER_TEXT;
		case ATM:
			return PTSimpleInvoiceTemplateBundle.ATM_TEXT;
		case RESTAURANT_TICKET:
			return PTSimpleInvoiceTemplateBundle.RESTAURANT_TICKET_TEXT;
		case EXCHANGE:
			return PTSimpleInvoiceTemplateBundle.EXCHANGE_TEXT;
		default:
			return null;
		}
	}
}
