package com.premiumminds.billy.portugal.test.services.documents;

import java.util.Arrays;
import java.util.Date;
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
					.getInvoiceBuilder(SourceBilling.P), parameters);

		}
	}

	@Test
	public void testConcurrentIssuing() {
		ConcurrentTestUtil test = new ConcurrentTestUtil(2);

		List<Future<?>> results = test.runThreads(new TestRunner(injector, "A",
				"Business 1"));
		List<Future<?>> results2 = test.runThreads(new TestRunner(injector,
				"A", "Business 2"));

		int i = 1;
		for (Future<?> future : results) {
			System.out.println("############################################");
			System.out.println("Thread " + i);
			try {
				test.testFuture(future);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}

		i = 1;
		for (Future<?> future : results2) {
			System.out.println("############################################");
			System.out.println("Thread " + i);
			try {
				test.testFuture(future);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
	}

}
