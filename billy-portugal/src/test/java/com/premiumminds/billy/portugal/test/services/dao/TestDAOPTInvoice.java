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

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;

public class TestDAOPTInvoice extends PTPersistencyAbstractTest {

	private PTInvoiceTestUtil invoiceUtil;
	private PTInvoiceEntity invoiceEntity;
	private DAOPTInvoice dao;
	private final static Integer LAST_NUMBER = 20;
	private final static TYPE INVOICE_TYPE = TYPE.FT;

	@Before
	public void setUp() {
		this.invoiceUtil = new PTInvoiceTestUtil(PTAbstractTest.injector);
		this.invoiceEntity = this.invoiceUtil.getInvoiceEntity();
		this.dao = this.getInstance(DAOPTInvoice.class);
		this.dao.create(this.invoiceEntity);
		this.dao.create(this.invoiceUtil.getInvoiceEntity(
				TestDAOPTInvoice.INVOICE_TYPE, this.invoiceEntity.getSeries(),
				"test uid", TestDAOPTInvoice.LAST_NUMBER, "entry uid", "buid",
				"cuid", Arrays.asList("limoes")));
	}

	@Test
	public void testLastInvoiceNumber() {
		PTInvoiceEntity resultInvoice = this.dao
				.getLatestInvoiceFromSeries(this.invoiceEntity.getSeries());
		Assert.assertEquals(resultInvoice.getSeriesNumber(),
				TestDAOPTInvoice.LAST_NUMBER);
	}

	@Test(expected = BillyRuntimeException.class)
	public void testWithNoInvoice() {
		System.out.println(this.dao
				.getLatestInvoiceFromSeries("NON EXISTING SERIES"));
	}
}
