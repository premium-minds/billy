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
package com.premiumminds.billy.portugal.services.export.pdf;

import java.io.InputStream;
import java.math.MathContext;

import org.apache.commons.codec.binary.Base64;

import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.gin.services.export.GenericInvoiceData;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractFOPPDFTransformer;
import com.premiumminds.billy.portugal.Config;

public abstract class PTAbstractFOPPDFTransformer<T extends GenericInvoiceData>
    extends AbstractFOPPDFTransformer<T> {

  protected static class PTParamKeys {
    public static final String ROOT = "invoice";
    public static final String INVOICE_HASH = "hash";
    public static final String SOFTWARE_CERTIFICATE_NUMBER = "certificateNumber";
    public static final String INVOICE_PAYSETTLEMENT = "paymentSettlement";
  }

  protected static final String GENERIC_CUSTOMER_TEXT = "Consumidor Final";
  protected static final String BANK_TRANSFER_TEXT = "Transferência Bancária";
  protected static final String CASH_TEXT = "Numerário";
  protected static final String CREDIT_CARD_TEXT = "Cartão Crédito";
  protected static final String CHECK_TEXT = "Cheque";
  protected static final String DEBIT_CARD_TEXT = "Cartão Débito";
  protected static final String COMPENSATION_TEXT = "Compensação de saldos em conta corrente";
  protected static final String COMMERCIAL_LETTER_TEXT = "Letra Comercial";
  protected static final String RESTAURANT_TICKET_TEXT = "Ticket Restaurante";
  protected static final String ATM_TEXT = "Multibanco";
  protected static final String EXCHANGE_TEXT = "Permuta";
  protected static final String ELECTRONIC_MONEY_TEXT = "Dinheiro Eletrónico";

  protected final Config config;
  protected final String softwareCertificationId;
  private final PTTemplateBundle externalBundle;

  public PTAbstractFOPPDFTransformer(Class<T> transformableClass, MathContext mc,
      String logoImagePath, InputStream xsltFileStream, String softwareCertificationId,
      Config config) {

    super(transformableClass, mc, logoImagePath, xsltFileStream);

    this.config = config;
    this.softwareCertificationId = softwareCertificationId;
    this.externalBundle = null;
  }

  public PTAbstractFOPPDFTransformer(Class<T> transformableClass, MathContext mc,
      PTTemplateBundle bundle, Config config) {

    super(transformableClass, mc, bundle.getLogoImagePath(), bundle.getXSLTFileStream());
    this.config = config;
    this.softwareCertificationId = bundle.getSoftwareCertificationId();
    this.externalBundle = bundle;
  }

  @Override
  protected ParamsTree<String, String> getNewParamsTree() {
    return new ParamsTree<String, String>(PTParamKeys.ROOT);
  }

  @Override
  protected String getPaymentMechanismTranslation(Enum<?> pmc) {
    if (null == pmc) {
      return null;
    }

    if (externalBundle != null) {
      return externalBundle.getPaymentMechanismTranslation(pmc);
    } else {
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

  protected String getSoftwareCertificationId() {
    return softwareCertificationId;
  }

  @Override
  protected String getCustomerFinancialId(T entity) {
    return (entity.getCustomer().getUID()
        .equals(this.config.getUUID(Config.Key.Customer.Generic.UUID)) ? getGenericCustomer()
            : entity.getCustomer().getTaxRegistrationNumber());
  }

  private String getGenericCustomer() {
    if (externalBundle == null) {
      return GENERIC_CUSTOMER_TEXT;
    } else {
      return externalBundle.getGenericCustomer();
    }
  }

  protected String getVerificationHashString(byte[] hash) {
    String hashString = Base64.encodeBase64String(hash);
    String rval = hashString.substring(0, 1) + hashString.substring(10, 11)
        + hashString.substring(20, 21) + hashString.substring(30, 31);

    return rval;
  }

}
