/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.export.pdf;

import com.premiumminds.billy.portugal.util.PaymentMechanism;

public abstract class AbstractPTTemplateBundle implements
		IBillyPTTemplateBundle {

	private static final String BANK_TRANSFER = "TB";
	private static final String CASH = "NU";
	private static final String CREDIT_CARD = "CC";
	private static final String CHECK = "CH";
	private static final String DEBIT_CARD = "CD";
	private static final String COMPENSATION = "CS";
	private static final String COMMERCIAL_LETTER = "LT";
	private static final String RESTAURANT_TICKET = "TR";
	private static final String ATM = "MB";

	private static final String GENERIC_CUSTOMER = "Consumidor Final";

	@Override
	public String getPaymentMechanismTranslation(PaymentMechanism pmc) {
		if (null == pmc) {
			return null;
		}

		switch (pmc) {
			case BANK_TRANSFER:
				return BANK_TRANSFER;
			case CASH:
				return CASH;
			case CREDIT_CARD:
				return CREDIT_CARD;
			case CHECK:
				return CHECK;
			case DEBIT_CARD:
				return DEBIT_CARD;
			case COMPENSATION:
				return COMPENSATION;
			case COMMERCIAL_LETTER:
				return COMMERCIAL_LETTER;
			case ATM:
				return ATM;
			case RESTAURANT_TICKET:
				return RESTAURANT_TICKET;
			default:
				return null;
		}
	}

	@Override
	public String getGenericCustomer() {
		return GENERIC_CUSTOMER;
	}

}
