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
package com.premiumminds.billy.spain.test.services.documents.handler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.spain.exceptions.BillySimpleInvoiceException;
import com.premiumminds.billy.spain.persistence.dao.DAOESSimpleInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESSimpleInvoiceEntity;
import com.premiumminds.billy.spain.services.documents.ESSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.spain.services.entities.ESSimpleInvoice;
import com.premiumminds.billy.spain.services.entities.ESSimpleInvoice.CLIENTTYPE;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.services.documents.ESDocumentAbstractTest;
import com.premiumminds.billy.spain.test.util.ESSimpleInvoiceTestUtil;

public class TestESSimpleInvoiceIssuingHandler extends ESDocumentAbstractTest {

  private String DEFAULT_SERIES = INVOICE_TYPE.FS + " " + ESPersistencyAbstractTest.DEFAULT_SERIES;

  private ESSimpleInvoiceIssuingHandler handler;
  private UID issuedInvoiceUID;

  @Before
  public void setUpNewSimpleInvoice() {
    this.handler = this.getInstance(ESSimpleInvoiceIssuingHandler.class);

    try {
      ESSimpleInvoiceEntity invoice = this.newInvoice(INVOICE_TYPE.FS, SOURCE_BILLING.APPLICATION);

      this.issueNewInvoice(this.handler, invoice, DEFAULT_SERIES);
      this.issuedInvoiceUID = invoice.getUID();
    } catch (DocumentIssuingException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testIssuedInvoiceSimple() throws DocumentIssuingException {
    ESSimpleInvoice issuedInvoice = (ESSimpleInvoice) this.getInstance(DAOESSimpleInvoice.class)
        .get(this.issuedInvoiceUID);

    Assert.assertEquals(DEFAULT_SERIES, issuedInvoice.getSeries());
    Assert.assertTrue(1 == issuedInvoice.getSeriesNumber());
    String formatedNumber = DEFAULT_SERIES + "/1";
    Assert.assertEquals(formatedNumber, issuedInvoice.getNumber());
  }

  @Test(expected = BillySimpleInvoiceException.class)
  public void testBusinessSimpleInvoice() {
    ESSimpleInvoiceEntity invoice = new ESSimpleInvoiceTestUtil(ESAbstractTest.injector)
        .getSimpleInvoiceEntity(CLIENTTYPE.BUSINESS);
  }

}
