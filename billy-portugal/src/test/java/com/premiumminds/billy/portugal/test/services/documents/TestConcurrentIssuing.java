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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.util.ConcurrentTestUtil;
import com.premiumminds.billy.portugal.test.util.PTBusinessTestUtil;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;

public class TestConcurrentIssuing extends PTDocumentAbstractTest {

	private DocumentIssuingService	service;

	@Before
	public void setUp() {
		this.service = this.getInstance(DocumentIssuingService.class);
		this.service.addHandler(PTInvoiceEntity.class, PTAbstractTest.injector
				.getInstance(PTInvoiceIssuingHandler.class));

		this.parameters.setInvoiceSeries("A");
	}

	class TestRunner implements Callable<PTInvoice> {

		private Injector			injector;
		private String				series;
		private PTBusinessEntity	business;

		public TestRunner(Injector inject, String series,
							PTBusinessEntity business) {
			this.injector = inject;
			this.series = series;
			this.business = business;
		}

		@Override
		public PTInvoice call() throws Exception {
			TestConcurrentIssuing.this.parameters.setInvoiceSeries(this.series);

			PTInvoice invoice = TestConcurrentIssuing.this.service.issue(
					new PTInvoiceTestUtil(this.injector).getInvoiceBuilder(
							this.business, SourceBilling.P),
					TestConcurrentIssuing.this.parameters);

			return invoice;
		}
	}

	@Test
	public void testConcurrentIssuing() throws InterruptedException,
		ExecutionException {
		ConcurrentTestUtil test = new ConcurrentTestUtil(10);
		String B1 = "Business 1";
		PTBusinessEntity businessEntity1 = new PTBusinessTestUtil(injector)
				.getBusinessEntity(B1);
		String B2 = "Business 2";
		PTBusinessEntity businessEntity2 = new PTBusinessTestUtil(injector)
				.getBusinessEntity(B2);

		List<Future<?>> results1 = test.runThreads(new TestRunner(
				PTAbstractTest.injector, "A", businessEntity1));

		List<PTInvoice> invoices1 = this.executeThreads(results1);

		List<Future<?>> results2 = test.runThreads(new TestRunner(
				PTAbstractTest.injector, "A", businessEntity2));

		List<PTInvoice> invoices2 = this.executeThreads(results2);

		if (invoices1.isEmpty() || invoices2.isEmpty()) {
			Assert.fail(((invoices1.isEmpty()) ? "Invoice1" : "Invoice2")
					+ " is empty!");
		}

		PTInvoiceEntity entity1 = this.getLatestInvoice(invoices1);
		PTInvoiceEntity latestInvoice1 = this.getInstance(DAOPTInvoice.class)
				.getLatestInvoiceFromSeries("A", B1);
		Assert.assertEquals(entity1.getSeriesNumber(),
				latestInvoice1.getSeriesNumber());
		Assert.assertEquals(entity1.getBusiness().getUID().toString(), B1);

		PTInvoiceEntity entity2 = this.getLatestInvoice(invoices2);
		PTInvoiceEntity latestInvoice2 = this.getInstance(DAOPTInvoice.class)
				.getLatestInvoiceFromSeries("A", B2);
		Assert.assertEquals(entity2.getSeriesNumber(),
				latestInvoice2.getSeriesNumber());
		Assert.assertEquals(entity2.getBusiness().getUID().toString(), B2);

	}

	@Test
	public void testConcurrentIssuing2() throws InterruptedException,
		ExecutionException {
		String B1 = "Business 1";
		PTBusinessEntity businessEntity1 = new PTBusinessTestUtil(injector)
				.getBusinessEntity(B1);
		ConcurrentTestUtil test = new ConcurrentTestUtil(10);
		List<Future<?>> results1 = test.runThreads(new TestRunner(
				PTAbstractTest.injector, "A", businessEntity1));
		List<Future<?>> results2 = test.runThreads(new TestRunner(
				PTAbstractTest.injector, "A", businessEntity1));

		List<PTInvoice> invoices1 = this.executeThreads(results1);
		List<PTInvoice> invoices2 = this.executeThreads(results2);

		PTInvoiceEntity entity1 = this.getLatestInvoice(invoices1);
		PTInvoiceEntity entity2 = this.getLatestInvoice(invoices2);

		Integer latestInvoiceNumber = (entity1.getSeriesNumber() < entity2
				.getSeriesNumber()) ? entity2.getSeriesNumber() : entity1
				.getSeriesNumber();

		PTInvoiceEntity latestInvocie = this.getInstance(DAOPTInvoice.class)
				.getLatestInvoiceFromSeries("A", B1);
		Assert.assertEquals(latestInvoiceNumber,
				latestInvocie.getSeriesNumber());
	}

	private PTInvoiceEntity getLatestInvoice(List<PTInvoice> invoices) {
		int lastSeriesNumber = 0;
		PTInvoiceEntity entity = null;
		for (PTInvoice invoice : invoices) {
			if (lastSeriesNumber < invoice.getSeriesNumber()) {
				lastSeriesNumber = invoice.getSeriesNumber();
				entity = (PTInvoiceEntity) invoice;
			}
		}
		return entity;
	}

	private List<PTInvoice> executeThreads(List<Future<?>> results)
		throws InterruptedException, ExecutionException {
		List<PTInvoice> invoices = new ArrayList<PTInvoice>();

		for (int i = 0; i < results.size(); i++) {
			PTInvoice invoice = (PTInvoice) results.get(i).get();
			if (invoice != null) {
				invoices.add(invoice);
			}
		}
		return invoices;
	}

}
