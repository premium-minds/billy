/**
 * Copyright (C) 2013 Premium Minds.
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
package com.premiumminds.billy.spain.test.services.dao;

import java.rmi.server.UID;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNote;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.entities.ESCreditNote;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;

public class TestDAOESInvoice extends ESPersistencyAbstractTest {

	@Test
	public void testLastInvoiceNumber() {
		String B1 = "B1";
		this.getNewIssuedInvoice(B1);
		this.getNewIssuedInvoice(B1);
		ESInvoiceEntity resultInvoice2 = this.getNewIssuedInvoice(B1);
		Assert.assertEquals(new Integer(3), resultInvoice2.getSeriesNumber());
	}

	@Test
	public void testLastInvoiceNumberWithDifferentBusiness() {
		String B1 = "B1";
		String B2 = "B2";
		ESInvoiceEntity inv1 = this.getNewIssuedInvoice(B1);
		ESInvoiceEntity inv2 = this.getNewIssuedInvoice(B2);
		
		ESInvoiceEntity resultInvoice1 = (ESInvoiceEntity)this.getInstance(DAOESInvoice.class)
				.getLatestInvoiceFromSeries(inv1.getSeries(), inv1.getBusiness().getUID().toString());
		ESInvoiceEntity resultInvoice2 = (ESInvoiceEntity)this.getInstance(DAOESInvoice.class)
				.getLatestInvoiceFromSeries(inv2.getSeries(), inv2.getBusiness().getUID().toString());
		
		ESInvoiceEntity inv3 = this.getNewIssuedInvoice(B1);
		ESInvoiceEntity inv4 = this.getNewIssuedInvoice(B2);
		
		Assert.assertEquals(inv1.getSeriesNumber(), resultInvoice1.getSeriesNumber());
		Assert.assertEquals(inv2.getSeriesNumber(), resultInvoice2.getSeriesNumber());
		Assert.assertEquals(new Integer(2), inv3.getSeriesNumber());
		Assert.assertEquals(new Integer(2), inv4.getSeriesNumber());
	}

	@Test(expected = BillyRuntimeException.class)
	public void testWithNoInvoice() {
		this.getInstance(DAOESInvoice.class).getLatestInvoiceFromSeries(
				"NON EXISTING SERIES", (new UID().toString()));
	}
	
	@Test
	public void testInvoiceFromBusiness() {
		ESInvoiceEntity inv1 = this.getNewIssuedInvoice("B1");
		
		ESGenericInvoiceEntity res = this.getInstance(DAOESInvoice.class).findByNumber(
				inv1.getBusiness().getUID(),
				inv1.getNumber());
		
		Assert.assertEquals(inv1.getUID(), res.getUID());
		Assert.assertNull(this.getInstance(DAOESInvoice.class).findByNumber(
				com.premiumminds.billy.core.services.UID.fromString("INEXISTENT_BUSINESS"),
				inv1.getNumber()));
	}
	
	@Test
	public void testFindCreditNote() {
		ESInvoiceEntity inv1 = this.getNewIssuedInvoice("B1");
		ESCreditNote cc1 = this.getNewIssuedCreditnote(inv1);
		
		List<ESCreditNote> cn1 = this.getInstance(DAOESCreditNote.class).findByReferencedDocument(
				cc1.getBusiness().getUID(), 
				inv1.getUID());
		
		List<ESCreditNote> cn0 = this.getInstance(DAOESCreditNote.class).findByReferencedDocument(
				com.premiumminds.billy.core.services.UID.fromString("B1"), 
				com.premiumminds.billy.core.services.UID.fromString("leRandomNumbér"));
		
		Assert.assertEquals(0, cn0.size());
		Assert.assertEquals(1, cn1.size());
	}
}
