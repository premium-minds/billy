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
package com.premiumminds.billy.portugal.test.services.documents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.util.ConcurrentTestUtil;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;

public class TestConcurrentIssuing extends PTDocumentAbstractTest {

	private DocumentIssuingService service;

	@Before
	public void setUp() {
		service = this.getInstance(DocumentIssuingService.class);
		service.addHandler(PTInvoiceEntity.class, PTAbstractTest.injector
				.getInstance(PTInvoiceIssuingHandler.class));

		parameters.setInvoiceSeries("A");
	}

	class TestRunner implements Callable<PTInvoice> {

		private Injector injector;
		private String series;
		private String business;

		public TestRunner(Injector inject, String series, String business) {
			this.injector = inject;
			this.series = series;
			this.business = business;

		}

		@Override
		public PTInvoice call() throws Exception {
			parameters.setInvoiceSeries(series);
			return service.issue(new PTInvoiceTestUtil(injector)
					.getInvoiceBuilder(business, SourceBilling.P), parameters);

		}
	}

	@Test
	public void testConcurrentIssuing() {
		ConcurrentTestUtil test = new ConcurrentTestUtil(5);

		String B1 = "Business 1";
		String B2 = "Business 2";

		List<Future<?>> results1 = test.runThreads(new TestRunner(injector,
				"A", B1));
		List<Future<?>> results2 = test.runThreads(new TestRunner(injector,
				"A", B2));

		List<PTInvoice> invoices1 = new ArrayList<PTInvoice>();
		List<PTInvoice> invoices2 = new ArrayList<PTInvoice>();

		for (int i = 0; i < results1.size(); i++) {
			try {
				PTInvoice invoice = (PTInvoice) results1.get(i).get();
				if (invoice != null)
					invoices1.add(invoice);
			} catch (Exception e) {
			}
		}

		for (int i = 0; i < results2.size(); i++) {
			try {
				PTInvoice invoice = (PTInvoice) results2.get(i).get();
				if (invoice != null)
					invoices2.add(invoice);
			} catch (Exception e) {
			}
		}

		if (invoices1.isEmpty() || invoices2.isEmpty()) {
			fail(((invoices1.isEmpty()) ? "Invoice1" : "Invoice2")
					+ " is empty!");
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int lastSeriesNumber = 0;
		PTInvoiceEntity entity1 = null;
		for (PTInvoice invoice : invoices1) {
			if (lastSeriesNumber < invoice.getSeriesNumber()) {
				lastSeriesNumber = invoice.getSeriesNumber();
				entity1 = (PTInvoiceEntity) invoice;
			}
		}
		assertEquals(entity1.getSeriesNumber(), new Integer(invoices1.size()));
		assertEquals(entity1.getBusiness().getUID().toString(), B1);

		lastSeriesNumber = 0;
		PTInvoiceEntity entity2 = null;
		for (PTInvoice invoice : invoices2) {
			if (lastSeriesNumber < invoice.getSeriesNumber()) {
				lastSeriesNumber = invoice.getSeriesNumber();
				entity2 = (PTInvoiceEntity) invoice;
			}
		}
		assertEquals(entity2.getSeriesNumber(), new Integer(invoices2.size()));
		assertEquals(entity2.getBusiness().getUID().toString(), B2);

	}
}
