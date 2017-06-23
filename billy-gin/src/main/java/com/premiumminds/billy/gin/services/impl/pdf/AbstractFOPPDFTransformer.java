/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy GIN.
 *
 * billy GIN is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy GIN is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy GIN. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.gin.services.impl.pdf;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyExportTransformer;
import com.premiumminds.billy.gin.services.export.GenericInvoiceData;
import com.premiumminds.billy.gin.services.export.InvoiceEntryData;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import com.premiumminds.billy.gin.services.export.PaymentData;
import com.premiumminds.billy.gin.services.export.TaxData;

public abstract class AbstractFOPPDFTransformer<T extends GenericInvoiceData>
    extends FOPPDFTransformer implements BillyExportTransformer<T, OutputStream> {

  protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  protected static class ParamKeys {

    public static final String INVOICE_PAYMETHOD = "paymentMechanism";
    public static final String ID = "id";
    public static final String EMISSION_DATE = "emissionDate";
    public static final String DUE_DATE = "dueDate";
    public static final String TOTAL_BEFORE_TAX = "totalBeforeTax";
    public static final String TOTAL_TAX = "totalTax";
    public static final String TOTAL = "totalPrice";

    public static final String BUSINESS = "business";
    public static final String BUSINESS_LOGO = "logoPath";
    public static final String BUSINESS_NAME = "name";
    public static final String BUSINESS_FINANCIAL_ID = "financialId";
    public static final String BUSINESS_ADDRESS = "address";
    public static final String BUSINESS_ADDRESS_COUNTRY = "country";
    public static final String BUSINESS_ADDRESS_DETAILS = "details";
    public static final String BUSINESS_ADDRESS_CITY = "city";
    public static final String BUSINESS_ADDRESS_REGION = "region";
    public static final String BUSINESS_ADDRESS_POSTAL_CODE = "postalcode";

    public static final String BUSINESS_CONTACTS = "contacts";
    public static final String BUSINESS_PHONE = "phNo";
    public static final String BUSINESS_FAX = "faxNo";
    public static final String BUSINESS_EMAIL = "email";

    public static final String CUSTOMER = "customer";
    public static final String CUSTOMER_NAME = "name";
    public static final String CUSTOMER_FINANCIAL_ID = "financialId";

    public static final String CUSTOMER_BILLING_ADDRESS = "address";
    public static final String CUSTOMER_BILLING_ADDRESS_COUNTRY = "country";
    public static final String CUSTOMER_BILLING_ADDRESS_DETAILS = "details";
    public static final String CUSTOMER_BILLING_ADDRESS_CITY = "city";
    public static final String CUSTOMER_BILLING_ADDRESS_REGION = "region";
    public static final String CUSTOMER_BILLING_ADDRESS_POSTAL_CODE = "postalcode";

    public static final String ENTRIES = "entries";
    public static final String ENTRY = "entry";
    public static final String ENTRY_ID = "id";
    public static final String ENTRY_DESCRIPTION = "description";
    public static final String ENTRY_QUANTITY = "qty";
    public static final String ENTRY_UNIT_PRICE = "unitPrice";
    public static final String ENTRY_TOTAL = "total";
    public static final String ENTRY_TAX = "tax";

    public static final String TAX_DETAILS = "taxDetails";
    public static final String TAX_DETAIL = "detail";
    public static final String TAX_DETAIL_TAX = "tax";
    public static final String TAX_DETAIL_NET_VALUE = "baseValue";
    public static final String TAX_DETAIL_VALUE = "taxValue";
    public static final String TAX_DETAIL_DESIGNATION = "designation";
    public static final String TAX_DETAIL_DESCRIPTION = "description";
  }

  private final Class<T> transformableClass;

  protected final MathContext mc;
  protected final String logoImagePath;
  protected final InputStream xsltFileStream;

  public AbstractFOPPDFTransformer(Class<T> transformableClass, MathContext mc,
      String logoImagePath, InputStream xsltFileStream) {

    this.transformableClass = transformableClass;
    this.mc = mc;
    this.logoImagePath = logoImagePath;
    this.xsltFileStream = xsltFileStream;
  }

  protected abstract String getPaymentMechanismTranslation(Enum<?> pmc);

  protected abstract String getCustomerFinancialId(T document);

  protected abstract ParamsTree<String, String> getNewParamsTree();

  @Override
  public Class<T> getTransformableClass() {
    return transformableClass;
  }

  @Override
  public void transform(T document, OutputStream targetStream) throws ExportServiceException {
    transformToStream(xsltFileStream, mapDocumentToParamsTree(document), targetStream);
  }

  protected ParamsTree<String, String> mapDocumentToParamsTree(T document) {

    ParamsTree<String, String> params = getNewParamsTree();

    TaxTotals taxTotals = new TaxTotals();

    setHeader(params, document);
    setBusiness(params, document);
    setCustomer(params, document);
    setEntries(taxTotals, params, document);
    setTaxDetails(taxTotals, params);
    setTaxValues(params, document);

    return params;
  }

  protected void setHeader(ParamsTree<String, String> params, T document) {
    params.getRoot().addChild(ParamKeys.ID, document.getNumber());

    if (document.getPayments() != null) {
      for (PaymentData payment : document.getPayments()) {
        params.getRoot().addChild(ParamKeys.INVOICE_PAYMETHOD,
            getPaymentMechanismTranslation(payment.getPaymentMethod()));
      }
    }

    params.getRoot().addChild(ParamKeys.EMISSION_DATE, DATE_FORMAT.format(document.getDate()));

    if (null != document.getSettlementDate()) {
      params.getRoot().addChild(ParamKeys.DUE_DATE,
          DATE_FORMAT.format(document.getSettlementDate()));
    }
  }

  protected void setTaxDetails(TaxTotals taxTotals, ParamsTree<String, String> params) {

    Node<String, String> taxDetails = params.getRoot().addChild(ParamKeys.TAX_DETAILS);

    for (TaxTotals.TaxTotalEntry taxDetail : taxTotals.getEntries()) {

      Node<String, String> taxDetailNode = taxDetails.addChild(ParamKeys.TAX_DETAIL);

      taxDetailNode.addChild(ParamKeys.TAX_DETAIL_TAX,
          taxDetail.getTaxValue().setScale(BillyMathContext.SCALE, this.mc.getRoundingMode())
              .toPlainString() + (taxDetail.isPercentage() ? "%" : "&#8364;"));

      taxDetailNode.addChild(ParamKeys.TAX_DETAIL_NET_VALUE, taxDetail.getNetValue()
          .setScale(BillyMathContext.SCALE, this.mc.getRoundingMode()).toPlainString());

      taxDetailNode.addChild(ParamKeys.TAX_DETAIL_VALUE, taxDetail.getAppliedTaxValue()
          .setScale(BillyMathContext.SCALE, this.mc.getRoundingMode()).toPlainString());

      taxDetailNode.addChild(ParamKeys.TAX_DETAIL_DESIGNATION, taxDetail.getTaxDesignation());

      taxDetailNode.addChild(ParamKeys.TAX_DETAIL_DESCRIPTION, taxDetail.getTaxDescription());
    }
  }

  protected void setEntries(TaxTotals taxTotals, ParamsTree<String, String> params, T document) {

    Node<String, String> entries = params.getRoot().addChild(ParamKeys.ENTRIES);
    Collection<? extends InvoiceEntryData> genericInvoiceList = document.getEntries();

    for (InvoiceEntryData entry : genericInvoiceList) {

      Node<String, String> entryNode = entries.addChild(ParamKeys.ENTRY);

      entryNode.addChild(ParamKeys.ENTRY_ID, entry.getProduct().getProductCode());

      entryNode.addChild(ParamKeys.ENTRY_DESCRIPTION, entry.getDescription());

      entryNode.addChild(ParamKeys.ENTRY_QUANTITY, entry.getQuantity()
          .setScale(BillyMathContext.SCALE, this.mc.getRoundingMode()).toPlainString());

      entryNode.addChild(ParamKeys.ENTRY_UNIT_PRICE, entry.getUnitAmountWithTax()
          .setScale(BillyMathContext.SCALE, this.mc.getRoundingMode()).toPlainString());

      entryNode.addChild(ParamKeys.ENTRY_TOTAL, entry.getAmountWithTax()
          .setScale(BillyMathContext.SCALE, this.mc.getRoundingMode()).toPlainString());

      Collection<TaxData> taxList = entry.getTaxes();
      for (TaxData tax : taxList) {
        entryNode.addChild(ParamKeys.ENTRY_TAX,
            tax.getValue().setScale(BillyMathContext.SCALE, this.mc.getRoundingMode())
                .toPlainString()
                + (tax.getTaxRateType() == TaxRateType.PERCENTAGE ? "%" : "&#8364;"));
        taxTotals.add((tax.getTaxRateType() == TaxRateType.PERCENTAGE ? true : false),
            tax.getValue(), entry.getAmountWithoutTax(), entry.getTaxAmount(),
            tax.getUID().getValue(), tax.getDesignation(), tax.getDescription());
      }
    }
  }

  protected void setBusiness(ParamsTree<String, String> params, T document) {
    Node<String, String> businessInfo = params.getRoot().addChild(ParamKeys.BUSINESS);

    businessInfo.addChild(ParamKeys.BUSINESS_LOGO, logoImagePath);

    businessInfo.addChild(ParamKeys.BUSINESS_NAME, document.getBusiness().getName());

    businessInfo.addChild(ParamKeys.BUSINESS_FINANCIAL_ID, document.getBusiness().getFinancialID());

    Node<String, String> businessAddress = businessInfo.addChild(ParamKeys.BUSINESS_ADDRESS);

    businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_COUNTRY,
        document.getBusiness().getAddress().getISOCountry());

    businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_DETAILS,
        document.getBusiness().getAddress().getDetails());

    businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_CITY,
        document.getBusiness().getAddress().getCity());

    businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_REGION,
        document.getBusiness().getAddress().getRegion());

    businessAddress.addChild(ParamKeys.BUSINESS_ADDRESS_POSTAL_CODE,
        document.getBusiness().getAddress().getPostalCode());

    Node<String, String> businessContacts = businessInfo.addChild(ParamKeys.BUSINESS_CONTACTS);

    businessContacts.addChild(ParamKeys.BUSINESS_PHONE,
        document.getBusiness().getMainContact().getTelephone());

    businessContacts.addChild(ParamKeys.BUSINESS_FAX,
        document.getBusiness().getMainContact().getFax());

    businessContacts.addChild(ParamKeys.BUSINESS_EMAIL,
        document.getBusiness().getMainContact().getEmail());

    return;
  }

  protected void setCustomer(ParamsTree<String, String> params, T document) {

    Node<String, String> customer = params.getRoot().addChild(ParamKeys.CUSTOMER);

    customer.addChild(ParamKeys.CUSTOMER_NAME, document.getCustomer().getName());

    customer.addChild(ParamKeys.CUSTOMER_FINANCIAL_ID, getCustomerFinancialId(document));

    if (document.getCustomer().getBillingAddress() != null) {
      Node<String, String> customerAddress = customer.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS);

      customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_COUNTRY,
          document.getCustomer().getBillingAddress().getISOCountry());

      customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_DETAILS,
          document.getCustomer().getBillingAddress().getDetails());

      customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_CITY,
          document.getCustomer().getBillingAddress().getCity());

      customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_REGION,
          document.getCustomer().getBillingAddress().getRegion());

      customerAddress.addChild(ParamKeys.CUSTOMER_BILLING_ADDRESS_POSTAL_CODE,
          document.getCustomer().getBillingAddress().getPostalCode());
    }
    return;
  }

  protected void setTaxValues(ParamsTree<String, String> params, T document) {

    params.getRoot().addChild(ParamKeys.TOTAL_BEFORE_TAX, document.getAmountWithoutTax()
        .setScale(BillyMathContext.SCALE, this.mc.getRoundingMode()).toPlainString());
    params.getRoot().addChild(ParamKeys.TOTAL_TAX, document.getTaxAmount()
        .setScale(BillyMathContext.SCALE, mc.getRoundingMode()).toPlainString());
    params.getRoot().addChild(ParamKeys.TOTAL, document.getAmountWithTax()
        .setScale(BillyMathContext.SCALE, mc.getRoundingMode()).toPlainString());

    BillyValidator.isTrue(
        document.getAmountWithoutTax().setScale(BillyMathContext.SCALE, mc.getRoundingMode())
            .add(document.getTaxAmount().setScale(BillyMathContext.SCALE, mc.getRoundingMode()))
            .compareTo(document.getAmountWithTax().setScale(BillyMathContext.SCALE,
                mc.getRoundingMode())) == 0);
  }

  protected class TaxTotals {

    Map<String, TaxTotalEntry> entries;

    private class TaxTotalEntry {

      BigDecimal baseValue;
      BigDecimal taxValue;
      BigDecimal appliedTaxValue;
      Boolean percentageValued;
      String designation;
      String description;

      public TaxTotalEntry(boolean perc, BigDecimal taxValue, BigDecimal baseValue,
          BigDecimal appliedTaxValue, String designation, String description) {
        this.baseValue = baseValue;
        this.taxValue = taxValue;
        this.percentageValued = perc;
        this.appliedTaxValue = appliedTaxValue;
        this.designation = designation;
        this.description = description;
      }

      public String getTaxDescription() {
        return this.description;
      }

      public String getTaxDesignation() {
        return this.designation;
      }

      public BigDecimal getNetValue() {
        return this.baseValue;
      }

      public BigDecimal getTaxValue() {
        return this.taxValue;
      }

      public boolean isPercentage() {
        return this.percentageValued;
      }

      public void addBaseValue(BigDecimal val) {
        this.baseValue = this.baseValue.add(val, mc);
      }

      public void addAppliedTaxValue(BigDecimal val) {
        this.appliedTaxValue = this.appliedTaxValue.add(val, mc);
      }

      public BigDecimal getAppliedTaxValue() {
        return appliedTaxValue;
      }
    }

    public TaxTotals() {
      this.entries = new HashMap<String, TaxTotalEntry>();
    }

    public void add(boolean isPercentage, BigDecimal taxValue, BigDecimal baseValue,
        BigDecimal taxAmount, String taxUid, String designation, String description) {
      TaxTotalEntry currentEntry = new TaxTotalEntry(isPercentage, taxValue, baseValue, taxAmount,
          designation, description);
      if (this.entries.containsKey(taxUid)) {
        this.entries.get(taxUid).addBaseValue(baseValue);
        this.entries.get(taxUid).addAppliedTaxValue(taxAmount);
      } else {
        this.entries.put(taxUid, currentEntry);
      }
    }

    public Collection<TaxTotalEntry> getEntries() {
      return this.entries.values();
    }
  }

}
