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
package com.premiumminds.billy.portugal.services.export.pdf.invoice;

import java.io.InputStream;

import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractTemplateBundle;
import com.premiumminds.billy.portugal.services.export.pdf.PTTemplateBundle;

public class PTInvoiceTemplateBundle extends AbstractTemplateBundle implements PTTemplateBundle {

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

  public PTInvoiceTemplateBundle(String logoImagePath, InputStream xsltFileStream,
      String softwareCertificationId) {

    super(logoImagePath, xsltFileStream);
    this.softwareCertificationId = softwareCertificationId;
  }

  @Override
  public String getGenericCustomer() {
    return PTInvoiceTemplateBundle.GENERIC_CUSTOMER_TEXT;
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
      return PTInvoiceTemplateBundle.BANK_TRANSFER_TEXT;
    case CASH:
      return PTInvoiceTemplateBundle.CASH_TEXT;
    case CREDIT_CARD:
      return PTInvoiceTemplateBundle.CREDIT_CARD_TEXT;
    case CHECK:
      return PTInvoiceTemplateBundle.CHECK_TEXT;
    case DEBIT_CARD:
      return PTInvoiceTemplateBundle.DEBIT_CARD_TEXT;
    case COMPENSATION:
      return PTInvoiceTemplateBundle.COMPENSATION_TEXT;
    case COMMERCIAL_LETTER:
      return PTInvoiceTemplateBundle.COMMERCIAL_LETTER_TEXT;
    case ATM:
      return PTInvoiceTemplateBundle.ATM_TEXT;
    case RESTAURANT_TICKET:
      return PTInvoiceTemplateBundle.RESTAURANT_TICKET_TEXT;
    case EXCHANGE:
      return PTInvoiceTemplateBundle.EXCHANGE_TEXT;
    case ELECTRONIC_MONEY:
      return PTInvoiceTemplateBundle.ELECTRONIC_MONEY_TEXT;
    default:
      return null;
    }
  }
}
