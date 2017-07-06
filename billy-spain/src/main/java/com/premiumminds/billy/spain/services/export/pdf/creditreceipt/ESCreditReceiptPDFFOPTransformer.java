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
package com.premiumminds.billy.spain.services.export.pdf.creditreceipt;

import java.io.InputStream;
import java.math.MathContext;
import java.util.Collection;
import java.util.List;

import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import com.premiumminds.billy.gin.services.export.TaxData;
import com.premiumminds.billy.spain.services.export.ESCreditReceiptData;
import com.premiumminds.billy.spain.services.export.ESCreditReceiptEntryData;
import com.premiumminds.billy.spain.services.export.pdf.ESAbstractFOPPDFTransformer;
import com.premiumminds.billy.spain.services.export.pdf.ESCreditReceiptPDFTransformer;

public class ESCreditReceiptPDFFOPTransformer extends
    ESAbstractFOPPDFTransformer<ESCreditReceiptData> implements ESCreditReceiptPDFTransformer {

  public static final String PARAM_KEYS_ROOT = "creditreceipt";
  public static final String PARAM_KEYS_RECEIPT = "receipt";

  public ESCreditReceiptPDFFOPTransformer(MathContext mathContext, String logoImagePath,
      InputStream xsltFileStream) {

    super(ESCreditReceiptData.class, mathContext, logoImagePath, xsltFileStream);
  }

  public ESCreditReceiptPDFFOPTransformer(String logoImagePath, InputStream xsltFileStream) {

    this(BillyMathContext.get(), logoImagePath, xsltFileStream);
  }

  public ESCreditReceiptPDFFOPTransformer(ESCreditReceiptTemplateBundle bundle) {
    super(ESCreditReceiptData.class, BillyMathContext.get(), bundle);
  }

  @Override
  protected ParamsTree<String, String> getNewParamsTree() {
    return new ParamsTree<String, String>(PARAM_KEYS_ROOT);
  }

  @Override
  protected ParamsTree<String, String> mapDocumentToParamsTree(ESCreditReceiptData creditReceipt) {

    ParamsTree<String, String> params = getNewParamsTree();

    TaxTotals taxTotals = new TaxTotals();

    setHeader(params, creditReceipt);
    setBusiness(params, creditReceipt);
    setEntries(taxTotals, params, creditReceipt);
    setTaxDetails(taxTotals, params);
    setTaxValues(params, creditReceipt);

    return params;
  }

  @Override
  protected void setEntries(TaxTotals taxTotals, ParamsTree<String, String> params,
      ESCreditReceiptData document) {

    Node<String, String> entries = params.getRoot().addChild(ParamKeys.ENTRIES);

    List<ESCreditReceiptEntryData> creditReceiptList = document.getEntries();
    for (ESCreditReceiptEntryData entry : creditReceiptList) {

      Node<String, String> entryNode = entries.addChild(ParamKeys.ENTRY);
      entryNode.addChild(ParamKeys.ENTRY_ID, entry.getProduct().getProductCode());
      entryNode.addChild(ParamKeys.ENTRY_DESCRIPTION, entry.getProduct().getDescription());
      entryNode.addChild(ParamKeys.ENTRY_QUANTITY,
          entry.getQuantity().setScale(2, this.mc.getRoundingMode()).toPlainString());
      entryNode.addChild(ParamKeys.ENTRY_UNIT_PRICE,
          entry.getUnitAmountWithTax().setScale(2, this.mc.getRoundingMode()).toPlainString());
      entryNode.addChild(ParamKeys.ENTRY_TOTAL,
          entry.getAmountWithTax().setScale(2, this.mc.getRoundingMode()).toPlainString());

      Collection<TaxData> list = entry.getTaxes();
      for (TaxData tax : list) {
        entryNode.addChild(ParamKeys.ENTRY_TAX, tax.getValue().setScale(2, mc.getRoundingMode())
            + (tax.getTaxRateType() == Tax.TaxRateType.PERCENTAGE ? "%" : "&#8364;"));
        taxTotals.add((tax.getTaxRateType() == Tax.TaxRateType.PERCENTAGE ? true : false),
            tax.getValue(), entry.getAmountWithoutTax(), entry.getTaxAmount(),
            tax.getUID().toString(), tax.getDesignation(), tax.getDescription());
      }
      entryNode.addChild(PARAM_KEYS_RECEIPT).addChild(ParamKeys.ID,
          entry.getReference().getNumber());
    }
  }

  @Override
  protected String getCustomerFinancialId(ESCreditReceiptData document) {
    return "";
  }
}
