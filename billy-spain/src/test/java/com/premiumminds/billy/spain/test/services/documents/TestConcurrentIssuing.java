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
package com.premiumminds.billy.spain.test.services.documents;

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
import com.premiumminds.billy.core.services.documents.impl.DocumentIssuingServiceImpl;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESBusinessEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.documents.ESInvoiceIssuingHandler;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoice.SourceBilling;
import com.premiumminds.billy.spain.services.entities.ESInvoice;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.util.ConcurrentTestUtil;
import com.premiumminds.billy.spain.test.util.ESBusinessTestUtil;
import com.premiumminds.billy.spain.test.util.ESInvoiceTestUtil;

public class TestConcurrentIssuing extends ESDocumentAbstractTest {

	private DocumentIssuingService	service;

	@Before
	public void setUp() {

		this.service = injector.getInstance(DocumentIssuingServiceImpl.class);
		this.service.addHandler(ESInvoiceEntity.class, ESAbstractTest.injector
				.getInstance(ESInvoiceIssuingHandler.class));
		this.parameters.setInvoiceSeries("A");
	}

	class TestRunner implements Callable<ESInvoice> {

		private Injector			injector;
		private String				series;
		private ESBusinessEntity	business;

		public TestRunner(Injector inject, String series,
							ESBusinessEntity business) {
			this.injector = inject;
			this.series = series;
			this.business = business;
		}

		@Override
		public ESInvoice call() throws Exception {
			TestConcurrentIssuing.this.parameters.setInvoiceSeries(this.series);
			ESInvoice invoice = TestConcurrentIssuing.this.service.issue(
					new ESInvoiceTestUtil(this.injector).getInvoiceBuilder(
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
		ESBusinessEntity businessEntity1 = new ESBusinessTestUtil(injector)
				.getBusinessEntity(B1);
		String B2 = "Business 2";
		ESBusinessEntity businessEntity2 = new ESBusinessTestUtil(injector)
				.getBusinessEntity(B2);

		List<Future<?>> results1 = test.runThreads(new TestRunner(
				ESAbstractTest.injector, "A", businessEntity1));

		List<ESInvoice> invoices1 = this.executeThreads(results1);

		List<Future<?>> results2 = test.runThreads(new TestRunner(
				ESAbstractTest.injector, "A", businessEntity2));

		List<ESInvoice> invoices2 = this.executeThreads(results2);

		if (invoices1.isEmpty() || invoices2.isEmpty()) {
			Assert.fail(((invoices1.isEmpty()) ? "Invoice1" : "Invoice2")
					+ " is empty!");
		}

		ESInvoiceEntity entity1 = this.getLatestInvoice(invoices1);
		ESInvoiceEntity latestInvoice1 = this.getInstance(DAOESInvoice.class)
				.getLatestInvoiceFromSeries("A", B1);
		Assert.assertEquals(entity1.getSeriesNumber(),
				latestInvoice1.getSeriesNumber());
		Assert.assertEquals(entity1.getBusiness().getUID().toString(), B1);

		ESInvoiceEntity entity2 = this.getLatestInvoice(invoices2);
		ESInvoiceEntity latestInvoice2 = this.getInstance(DAOESInvoice.class)
				.getLatestInvoiceFromSeries("A", B2);
		Assert.assertEquals(entity2.getSeriesNumber(),
				latestInvoice2.getSeriesNumber());
		Assert.assertEquals(entity2.getBusiness().getUID().toString(), B2);

	}

	@Test
	public void testConcurrentIssuing2() throws InterruptedException,
		ExecutionException {
		String B1 = "Business 1";
		ESBusinessEntity businessEntity1 = new ESBusinessTestUtil(injector)
				.getBusinessEntity(B1);
		ConcurrentTestUtil test = new ConcurrentTestUtil(10);
		List<Future<?>> results1 = test.runThreads(new TestRunner(
				ESAbstractTest.injector, "A", businessEntity1));
		List<Future<?>> results2 = test.runThreads(new TestRunner(
				ESAbstractTest.injector, "A", businessEntity1));

		List<ESInvoice> invoices1 = this.executeThreads(results1);
		List<ESInvoice> invoices2 = this.executeThreads(results2);

		ESInvoiceEntity entity1 = this.getLatestInvoice(invoices1);
		ESInvoiceEntity entity2 = this.getLatestInvoice(invoices2);

		Integer latestInvoiceNumber = (entity1.getSeriesNumber() < entity2
				.getSeriesNumber()) ? entity2.getSeriesNumber() : entity1
				.getSeriesNumber();

		ESInvoiceEntity latestInvocie = this.getInstance(DAOESInvoice.class)
				.getLatestInvoiceFromSeries("A", B1);
		Assert.assertEquals(latestInvoiceNumber,
				latestInvocie.getSeriesNumber());
	}

	@Test
	public void testDifferenteBusinessAndSeries() throws InterruptedException,
		ExecutionException {
		ConcurrentTestUtil test = new ConcurrentTestUtil(30);
		String B1 = "Business 1";
		ESBusinessEntity businessEntity1 = new ESBusinessTestUtil(injector)
				.getBusinessEntity(B1);
		String B2 = "Business 2";
		ESBusinessEntity businessEntity2 = new ESBusinessTestUtil(injector)
				.getBusinessEntity(B2);

		List<Future<?>> results1 = test.runThreads(new TestRunner(
				ESAbstractTest.injector, "A", businessEntity1));

		List<ESInvoice> invoices1 = this.executeThreads(results1);

		List<Future<?>> results2 = test.runThreads(new TestRunner(
				ESAbstractTest.injector, "B", businessEntity2));

		List<ESInvoice> invoices2 = this.executeThreads(results2);

		ESInvoiceEntity entity1 = this.getLatestInvoice(invoices1);
		ESInvoiceEntity latestInvoice1 = this.getInstance(DAOESInvoice.class)
				.getLatestInvoiceFromSeries("A", B1);
		Assert.assertEquals(entity1.getSeriesNumber(),
				latestInvoice1.getSeriesNumber());
		Assert.assertEquals(entity1.getBusiness().getUID().toString(), B1);

		ESInvoiceEntity entity2 = this.getLatestInvoice(invoices2);
		ESInvoiceEntity latestInvoice2 = this.getInstance(DAOESInvoice.class)
				.getLatestInvoiceFromSeries("B", B2);
		Assert.assertEquals(entity2.getSeriesNumber(),
				latestInvoice2.getSeriesNumber());
		Assert.assertEquals(entity2.getBusiness().getUID().toString(), B2);

	}

	@Test
	public void testMultipleSeriesIssuing() throws InterruptedException,
		ExecutionException {
		String B1 = "Business 1";
		ESBusinessEntity businessEntity1 = new ESBusinessTestUtil(injector)
				.getBusinessEntity(B1);
		Integer totalThreads = 10;
		ConcurrentTestUtil test = new ConcurrentTestUtil(totalThreads);

		List<Future<?>> results1 = test.runThreads(new TestRunner(
				ESAbstractTest.injector, "A", businessEntity1));
		List<Future<?>> results2 = test.runThreads(new TestRunner(
				ESAbstractTest.injector, "B", businessEntity1));
		List<Future<?>> results3 = test.runThreads(new TestRunner(
				ESAbstractTest.injector, "C", businessEntity1));

		List<ESInvoice> invoices1 = this.executeThreads(results1);
		List<ESInvoice> invoices2 = this.executeThreads(results2);
		List<ESInvoice> invoices3 = this.executeThreads(results3);

		ESInvoiceEntity latestInvoice1 = this.getInstance(DAOESInvoice.class)
				.getLatestInvoiceFromSeries("A", B1);
		Assert.assertEquals(totalThreads, latestInvoice1.getSeriesNumber());
		ESInvoiceEntity latestInvoice2 = this.getInstance(DAOESInvoice.class)
				.getLatestInvoiceFromSeries("B", B1);
		Assert.assertEquals(totalThreads, latestInvoice2.getSeriesNumber());
		ESInvoiceEntity latestInvoice3 = this.getInstance(DAOESInvoice.class)
				.getLatestInvoiceFromSeries("C", B1);
		Assert.assertEquals(totalThreads, latestInvoice3.getSeriesNumber());

	}

	private ESInvoiceEntity getLatestInvoice(List<ESInvoice> invoices) {
		int lastSeriesNumber = 0;
		ESInvoiceEntity entity = null;
		for (ESInvoice invoice : invoices) {
			if (lastSeriesNumber < invoice.getSeriesNumber()) {
				lastSeriesNumber = invoice.getSeriesNumber();
				entity = (ESInvoiceEntity) invoice;
			}
		}
		return entity;
	}

	private List<ESInvoice> executeThreads(List<Future<?>> results)
		throws InterruptedException, ExecutionException {
		List<ESInvoice> invoices = new ArrayList<ESInvoice>();

		for (int i = 0; i < results.size(); i++) {
			ESInvoice invoice = (ESInvoice) results.get(i).get();
			if (invoice != null) {
				invoices.add(invoice);
			}
		}
		return invoices;
	}

}
