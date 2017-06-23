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
package com.premiumminds.billy.portugal.test.services.dao;

import java.rmi.server.UID;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;

public class TestDAOPTInvoice extends PTPersistencyAbstractTest {

  @Test
  public void testLastInvoiceNumber() {
    String B1 = "B1";
    this.getNewIssuedInvoice(B1);
    this.getNewIssuedInvoice(B1);
    PTInvoiceEntity resultInvoice2 = this.getNewIssuedInvoice(B1);
    Assert.assertEquals(new Integer(3), resultInvoice2.getSeriesNumber());
  }

  @Test
  public void testLastInvoiceNumberWithDifferentBusiness() {
    String B1 = "B1";
    String B2 = "B2";
    PTInvoiceEntity inv1 = this.getNewIssuedInvoice(B1);
    PTInvoiceEntity inv2 = this.getNewIssuedInvoice(B2);

    PTInvoiceEntity resultInvoice1 = (PTInvoiceEntity) this.getInstance(DAOPTInvoice.class)
        .getLatestInvoiceFromSeries(inv1.getSeries(), inv1.getBusiness().getUID().toString());
    PTInvoiceEntity resultInvoice2 = (PTInvoiceEntity) this.getInstance(DAOPTInvoice.class)
        .getLatestInvoiceFromSeries(inv2.getSeries(), inv2.getBusiness().getUID().toString());

    PTInvoiceEntity inv3 = this.getNewIssuedInvoice(B1);
    PTInvoiceEntity inv4 = this.getNewIssuedInvoice(B2);

    Assert.assertEquals(inv1.getSeriesNumber(), resultInvoice1.getSeriesNumber());
    Assert.assertEquals(inv2.getSeriesNumber(), resultInvoice2.getSeriesNumber());
    Assert.assertEquals(new Integer(2), inv3.getSeriesNumber());
    Assert.assertEquals(new Integer(2), inv4.getSeriesNumber());
  }

  @Test(expected = BillyRuntimeException.class)
  public void testWithNoInvoice() {
    this.getInstance(DAOPTInvoice.class).getLatestInvoiceFromSeries("NON EXISTING SERIES",
        (new UID().toString()));
  }

  @Test
  public void testInvoiceFromBusiness() {
    PTInvoiceEntity inv1 = this.getNewIssuedInvoice("B1");

    PTGenericInvoiceEntity res = this.getInstance(DAOPTInvoice.class)
        .findByNumber(inv1.getBusiness().getUID(), inv1.getNumber());

    Assert.assertEquals(inv1.getUID(), res.getUID());
    Assert.assertNull(this.getInstance(DAOPTInvoice.class).findByNumber(
        com.premiumminds.billy.core.services.UID.fromString("INEXISTENT_BUSINESS"),
        inv1.getNumber()));
  }

  @Test
  public void testFindCreditNote() {
    PTInvoiceEntity inv1 = this.getNewIssuedInvoice("B1");
    PTCreditNote cc1 = this.getNewIssuedCreditnote(inv1);

    List<PTCreditNote> cn1 = this.getInstance(DAOPTCreditNote.class)
        .findByReferencedDocument(cc1.getBusiness().getUID(), inv1.getUID());

    List<PTCreditNote> cn0 = this.getInstance(DAOPTCreditNote.class).findByReferencedDocument(
        com.premiumminds.billy.core.services.UID.fromString("B1"),
        com.premiumminds.billy.core.services.UID.fromString("leRandomNumbér"));

    Assert.assertEquals(0, cn0.size());
    Assert.assertEquals(1, cn1.size());
  }
}
