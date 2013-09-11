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
package com.premiumminds.billy.portugal.test.services.dao;

import java.rmi.server.UID;

import org.junit.Assert;
import org.junit.Test;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
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
		this.getNewIssuedInvoice(B1);
		this.getNewIssuedInvoice(B2);
		PTInvoiceEntity resultInvoice1 = this.getNewIssuedInvoice(B1);
		PTInvoiceEntity resultInvoice2 = this.getNewIssuedInvoice(B2);
		Assert.assertEquals(new Integer(2), resultInvoice1.getSeriesNumber());
		Assert.assertEquals(new Integer(2), resultInvoice2.getSeriesNumber());
	}

	@Test(expected = BillyRuntimeException.class)
	public void testWithNoInvoice() {
		this.getInstance(DAOPTInvoice.class).getLatestInvoiceFromSeries(
				"NON EXISTING SERIES", (new UID().toString()));
	}
}
