/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.test.services.documents;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.documents.impl.DocumentIssuingServiceImpl;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.entities.FRBusinessEntity;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.services.documents.FRInvoiceIssuingHandler;
import com.premiumminds.billy.france.services.entities.FRInvoice;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.util.ConcurrentTestUtil;
import com.premiumminds.billy.france.test.util.FRBusinessTestUtil;
import com.premiumminds.billy.france.test.util.FRInvoiceTestUtil;

public class TestConcurrentIssuing extends FRDocumentAbstractTest {

    private DocumentIssuingService service;

    @BeforeEach
    public void setUp() {

        this.service = FRAbstractTest.injector.getInstance(DocumentIssuingServiceImpl.class);
        this.service.addHandler(FRInvoiceEntity.class,
                FRAbstractTest.injector.getInstance(FRInvoiceIssuingHandler.class));
        this.parameters.setInvoiceSeries("A");
    }

    class TestRunner implements Callable<FRInvoice> {

        private Injector injector;
        private String series;
        private FRBusinessEntity business;

        public TestRunner(Injector inject, String series, FRBusinessEntity business) {
            this.injector = inject;
            this.series = series;
            this.business = business;
        }

        @Override
        public FRInvoice call() throws Exception {
            TestConcurrentIssuing.this.parameters.setInvoiceSeries(this.series);
            try {
                FRInvoice invoice = TestConcurrentIssuing.this.service.issue(
                        new FRInvoiceTestUtil(this.injector).getInvoiceBuilder(this.business),
                        TestConcurrentIssuing.this.parameters);
                return invoice;
            } catch (DocumentIssuingException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
    }

    @Test
    public void testConcurrentIssuing() throws InterruptedException, ExecutionException {
        ConcurrentTestUtil test = new ConcurrentTestUtil(10);
        String B1 = "Business 1";
		this.createSeries(B1, "A");
		this.createSeries(B1, "B");
		this.createSeries(B1, "C");
        FRBusinessEntity businessEntity1 = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity(B1);
        String B2 = "Business 2";
		this.createSeries(B2, "A");
		this.createSeries(B2, "B");
		this.createSeries(B2, "C");
        FRBusinessEntity businessEntity2 = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity(B2);

        List<Future<?>> results1 = test.runThreads(new TestRunner(FRAbstractTest.injector, "A", businessEntity1));

        List<FRInvoice> invoices1 = this.executeThreads(results1);

        List<Future<?>> results2 = test.runThreads(new TestRunner(FRAbstractTest.injector, "A", businessEntity2));

        List<FRInvoice> invoices2 = this.executeThreads(results2);

        if (invoices1.isEmpty() || invoices2.isEmpty()) {
            Assertions.fail(((invoices1.isEmpty()) ? "Invoice1" : "Invoice2") + " is empty!");
        }

        FRInvoiceEntity entity1 = this.getLatestInvoice(invoices1);
        FRInvoiceEntity latestInvoice1 = this.getInstance(DAOFRInvoice.class).getLatestInvoiceFromSeries("A", B1);
        Assertions.assertNotNull(entity1);
        Assertions.assertEquals(entity1.getSeriesNumber(), latestInvoice1.getSeriesNumber());
        Assertions.assertEquals(entity1.getBusiness().getUID().toString(), B1);

        FRInvoiceEntity entity2 = this.getLatestInvoice(invoices2);
        FRInvoiceEntity latestInvoice2 = this.getInstance(DAOFRInvoice.class).getLatestInvoiceFromSeries("A", B2);
        Assertions.assertNotNull(entity2);
        Assertions.assertEquals(entity2.getSeriesNumber(), latestInvoice2.getSeriesNumber());
        Assertions.assertEquals(entity2.getBusiness().getUID().toString(), B2);

    }

    @Test
    public void testConcurrentIssuing2() throws InterruptedException, ExecutionException {
        String B1 = "Business 1";
		this.createSeries(B1, "A");
		this.createSeries(B1, "B");
		this.createSeries(B1, "C");
        FRBusinessEntity businessEntity1 = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity(B1);
        ConcurrentTestUtil test = new ConcurrentTestUtil(10);
        List<Future<?>> results1 = test.runThreads(new TestRunner(FRAbstractTest.injector, "A", businessEntity1));
        List<Future<?>> results2 = test.runThreads(new TestRunner(FRAbstractTest.injector, "A", businessEntity1));

        List<FRInvoice> invoices1 = this.executeThreads(results1);
        List<FRInvoice> invoices2 = this.executeThreads(results2);

        FRInvoiceEntity entity1 = this.getLatestInvoice(invoices1);
        FRInvoiceEntity entity2 = this.getLatestInvoice(invoices2);

        Integer latestInvoiceNumber = (entity1.getSeriesNumber() < entity2.getSeriesNumber())
                ? entity2.getSeriesNumber() : entity1.getSeriesNumber();

        FRInvoiceEntity latestInvocie = this.getInstance(DAOFRInvoice.class).getLatestInvoiceFromSeries("A", B1);
        Assertions.assertEquals(latestInvoiceNumber, latestInvocie.getSeriesNumber());
    }

    @Test
    public void testDifferenteBusinessAndSeries() throws InterruptedException, ExecutionException {
        ConcurrentTestUtil test = new ConcurrentTestUtil(20);
        String B1 = "Business 1";
		this.createSeries(B1, "A");
		this.createSeries(B1, "B");
		this.createSeries(B1, "C");
        FRBusinessEntity businessEntity1 = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity(B1);
        String B2 = "Business 2";
		this.createSeries(B2, "A");
		this.createSeries(B2, "B");
		this.createSeries(B2, "C");
        FRBusinessEntity businessEntity2 = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity(B2);

        List<Future<?>> results1 = test.runThreads(new TestRunner(FRAbstractTest.injector, "A", businessEntity1));

        List<FRInvoice> invoices1 = this.executeThreads(results1);

        List<Future<?>> results2 = test.runThreads(new TestRunner(FRAbstractTest.injector, "B", businessEntity2));

        List<FRInvoice> invoices2 = this.executeThreads(results2);

        FRInvoiceEntity entity1 = this.getLatestInvoice(invoices1);
        FRInvoiceEntity latestInvoice1 = this.getInstance(DAOFRInvoice.class).getLatestInvoiceFromSeries("A", B1);
        Assertions.assertEquals(entity1.getSeriesNumber(), latestInvoice1.getSeriesNumber());
        Assertions.assertEquals(entity1.getBusiness().getUID().toString(), B1);

        FRInvoiceEntity entity2 = this.getLatestInvoice(invoices2);
        FRInvoiceEntity latestInvoice2 = this.getInstance(DAOFRInvoice.class).getLatestInvoiceFromSeries("B", B2);
        Assertions.assertEquals(entity2.getSeriesNumber(), latestInvoice2.getSeriesNumber());
        Assertions.assertEquals(entity2.getBusiness().getUID().toString(), B2);

    }

    @Test
    public void testMultipleSeriesIssuing() throws InterruptedException, ExecutionException {
        String B1 = "Business 1";
		this.createSeries(B1, "A");
		this.createSeries(B1, "B");
		this.createSeries(B1, "C");
        FRBusinessEntity businessEntity1 = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity(B1);
        Integer totalThreads = 10;
        ConcurrentTestUtil test = new ConcurrentTestUtil(totalThreads);

        List<Future<?>> results1 = test.runThreads(new TestRunner(FRAbstractTest.injector, "A", businessEntity1));
        List<Future<?>> results2 = test.runThreads(new TestRunner(FRAbstractTest.injector, "B", businessEntity1));
        List<Future<?>> results3 = test.runThreads(new TestRunner(FRAbstractTest.injector, "C", businessEntity1));

        List<FRInvoice> invoices1 = this.executeThreads(results1);
        List<FRInvoice> invoices2 = this.executeThreads(results2);
        List<FRInvoice> invoices3 = this.executeThreads(results3);

        FRInvoiceEntity latestInvoice1 = this.getInstance(DAOFRInvoice.class).getLatestInvoiceFromSeries("A", B1);
        Assertions.assertEquals(this.filterNotNull(invoices1).size(), latestInvoice1.getSeriesNumber().intValue());
        FRInvoiceEntity latestInvoice2 = this.getInstance(DAOFRInvoice.class).getLatestInvoiceFromSeries("B", B1);
        Assertions.assertEquals(this.filterNotNull(invoices2).size(), latestInvoice2.getSeriesNumber().intValue());
        FRInvoiceEntity latestInvoice3 = this.getInstance(DAOFRInvoice.class).getLatestInvoiceFromSeries("C", B1);
        Assertions.assertEquals(this.filterNotNull(invoices3).size(), latestInvoice3.getSeriesNumber().intValue());

    }

    private FRInvoiceEntity getLatestInvoice(List<FRInvoice> invoices) {
        int lastSeriesNumber = 0;
        FRInvoiceEntity entity = null;
        for (FRInvoice invoice : invoices) {
            if (lastSeriesNumber < invoice.getSeriesNumber()) {
                lastSeriesNumber = invoice.getSeriesNumber();
                entity = (FRInvoiceEntity) invoice;
            }
        }
        return entity;
    }

    private List<FRInvoice> executeThreads(List<Future<?>> results) throws InterruptedException, ExecutionException {
        List<FRInvoice> invoices = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            FRInvoice invoice = (FRInvoice) results.get(i).get();
            if (invoice != null) {
                invoices.add(invoice);
            }
        }
        return invoices;
    }

    private <T> List<T> filterNotNull(List<T> in) {
        List<T> out = new ArrayList<>();
        for (T e : in) {
            if (null != e) {
                out.add(e);
            }
        }
        return out;
    }

}
