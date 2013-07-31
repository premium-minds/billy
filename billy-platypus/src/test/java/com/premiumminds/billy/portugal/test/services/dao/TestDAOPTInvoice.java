/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;

public class TestDAOPTInvoice extends PTPersistencyAbstractTest {

	private PTInvoiceTestUtil invoiceUtil;
	private PTInvoiceEntity invoiceEntity;
	private DAOPTInvoice dao;
	private final Integer LAST_NUMBER = 20;

	@Before
	public void setUp() {
		this.invoiceUtil = new PTInvoiceTestUtil(PTAbstractTest.injector);
		this.invoiceEntity = invoiceUtil.getInvoiceEntity();
		this.dao = getInstance(DAOPTInvoice.class);
		dao.create(invoiceEntity);
		dao.create(invoiceUtil.getInvoiceEntity(invoiceEntity.getSeries(),
				"test uid", LAST_NUMBER, "entry uid", "limoes"));
		// dao.create(invoiceUtil.getInvoiceEntity(invoiceEntity.getSeries(),
		// "test uid", LAST_NUMBER, "entry uid",
		// Arrays.asList("Limoes", "Cenouras", "Carapaus")));
	}

	@Test
	public void testLastInvoiceNumber() {
		PTInvoiceEntity resultInvoice = dao
				.getLatestInvoiceFromSeries(invoiceEntity.getSeries());
		assertEquals(resultInvoice.getSeriesNumber(), LAST_NUMBER);
	}

	@Test(expected = BillyRuntimeException.class)
	public void testWithNoInvoice() {
		dao.getLatestInvoiceFromSeries("NON EXISTING SERIES");
	}
}
