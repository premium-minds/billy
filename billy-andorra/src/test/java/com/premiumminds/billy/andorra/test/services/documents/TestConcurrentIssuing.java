/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.test.services.documents;

import com.google.inject.Injector;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.entities.ADBusinessEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADInvoice;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.util.ConcurrentTestUtil;
import com.premiumminds.billy.andorra.test.util.ADBusinessTestUtil;
import com.premiumminds.billy.andorra.test.util.ADInvoiceTestUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.documents.impl.DocumentIssuingServiceImpl;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.andorra.services.documents.ADInvoiceIssuingHandler;

public class TestConcurrentIssuing extends ADDocumentAbstractTest {

    private DocumentIssuingService service;

    @BeforeEach
    public void setUp() {

        this.service = ADAbstractTest.injector.getInstance(DocumentIssuingServiceImpl.class);
        this.service.addHandler(
            ADInvoiceEntity.class,
            injector.getInstance(ADInvoiceIssuingHandler.class));
        this.parameters.setInvoiceSeries("A");
    }

    class TestRunner implements Callable<ADInvoice> {

        private Injector injector;
        private String series;
        private ADBusinessEntity business;

        public TestRunner(Injector inject, String series, ADBusinessEntity business) {
            this.injector = inject;
            this.series = series;
            this.business = business;
        }

        @Override
        public ADInvoice call() throws Exception {
            TestConcurrentIssuing.this.parameters.setInvoiceSeries(this.series);
            try {
                ADInvoice invoice = TestConcurrentIssuing.this.service.issue(
                        new ADInvoiceTestUtil(this.injector).getInvoiceBuilder(this.business),
                        TestConcurrentIssuing.this.parameters);
                return invoice;
            } catch (DocumentIssuingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void testConcurrentIssuing() throws InterruptedException, ExecutionException {
        ConcurrentTestUtil test = new ConcurrentTestUtil(10);
        StringID<Business> B1 = StringID.fromValue("Business 1");
        this.createSeries(B1, "A");
        ADBusinessEntity businessEntity1 = new ADBusinessTestUtil(injector).getBusinessEntity(B1);
        StringID<Business> B2 = StringID.fromValue("Business 2");
        this.createSeries(B2, "A");
        ADBusinessEntity businessEntity2 = new ADBusinessTestUtil(injector).getBusinessEntity(B2);

        List<Future<?>> results1 = test.runThreads(new TestRunner(injector, "A", businessEntity1));

        List<ADInvoice> invoices1 = this.executeThreads(results1);

        List<Future<?>> results2 = test.runThreads(new TestRunner(injector, "A", businessEntity2));

        List<ADInvoice> invoices2 = this.executeThreads(results2);

        if (invoices1.isEmpty() || invoices2.isEmpty()) {
            Assertions.fail(((invoices1.isEmpty()) ? "Invoice1" : "Invoice2") + " is empty!");
        }

        ADInvoiceEntity entity1 = this.getLatestInvoice(invoices1);
        ADInvoiceEntity latestInvoice1 = this.getInstance(DAOADInvoice.class).getLatestInvoiceFromSeries("A", B1);
        Assertions.assertNotNull(entity1);
        Assertions.assertEquals(entity1.getSeriesNumber(), latestInvoice1.getSeriesNumber());
        Assertions.assertEquals(entity1.getLocalDate(), latestInvoice1.getLocalDate());
        Assertions.assertEquals(entity1.getBusiness().getUID(), B1);

        ADInvoiceEntity entity2 = this.getLatestInvoice(invoices2);
        ADInvoiceEntity latestInvoice2 = this.getInstance(DAOADInvoice.class).getLatestInvoiceFromSeries("A", B2);
        Assertions.assertNotNull(entity2);
        Assertions.assertEquals(entity2.getSeriesNumber(), latestInvoice2.getSeriesNumber());
        Assertions.assertEquals(entity2.getLocalDate(), latestInvoice2.getLocalDate());
        Assertions.assertEquals(entity2.getBusiness().getUID(), B2);

    }

    @Test
    public void testConcurrentIssuing2() throws InterruptedException, ExecutionException {
        StringID<Business> B1 = StringID.fromValue("Business 1");
        this.createSeries(B1, "A");
        ADBusinessEntity businessEntity1 = new ADBusinessTestUtil(injector).getBusinessEntity(B1);
        ConcurrentTestUtil test = new ConcurrentTestUtil(10);
        List<Future<?>> results1 = test.runThreads(new TestRunner(injector, "A", businessEntity1));
        List<Future<?>> results2 = test.runThreads(new TestRunner(injector, "A", businessEntity1));

        List<ADInvoice> invoices1 = this.executeThreads(results1);
        List<ADInvoice> invoices2 = this.executeThreads(results2);

        ADInvoiceEntity entity1 = this.getLatestInvoice(invoices1);
        ADInvoiceEntity entity2 = this.getLatestInvoice(invoices2);

        Integer latestInvoiceNumber = (entity1.getSeriesNumber() < entity2.getSeriesNumber())
                ? entity2.getSeriesNumber() : entity1.getSeriesNumber();

        ADInvoiceEntity latestInvocie = this.getInstance(DAOADInvoice.class).getLatestInvoiceFromSeries("A", B1);
        Assertions.assertEquals(latestInvoiceNumber, latestInvocie.getSeriesNumber());
    }

    @Test
    public void testDifferenteBusinessAndSeries() throws InterruptedException, ExecutionException {
        ConcurrentTestUtil test = new ConcurrentTestUtil(20);
        StringID<Business> B1 = StringID.fromValue("Business 1");
        this.createSeries(B1, "A");
        this.createSeries(B1, "B");
        this.createSeries(B1, "C");
        ADBusinessEntity businessEntity1 = new ADBusinessTestUtil(injector).getBusinessEntity(B1);
        StringID<Business> B2 = StringID.fromValue("Business 2");
        this.createSeries(B2, "A");
        this.createSeries(B2, "B");
        this.createSeries(B2, "C");
        ADBusinessEntity businessEntity2 = new ADBusinessTestUtil(injector).getBusinessEntity(B2);

        List<Future<?>> results1 = test.runThreads(new TestRunner(injector, "A", businessEntity1));

        List<ADInvoice> invoices1 = this.executeThreads(results1);

        List<Future<?>> results2 = test.runThreads(new TestRunner(injector, "B", businessEntity2));

        List<ADInvoice> invoices2 = this.executeThreads(results2);

        ADInvoiceEntity entity1 = this.getLatestInvoice(invoices1);
        ADInvoiceEntity latestInvoice1 = this.getInstance(DAOADInvoice.class).getLatestInvoiceFromSeries("A", B1);
        Assertions.assertEquals(entity1.getSeriesNumber(), latestInvoice1.getSeriesNumber());
        Assertions.assertEquals(entity1.getBusiness().getUID(), B1);

        ADInvoiceEntity entity2 = this.getLatestInvoice(invoices2);
        ADInvoiceEntity latestInvoice2 = this.getInstance(DAOADInvoice.class).getLatestInvoiceFromSeries("B", B2);
        Assertions.assertEquals(entity2.getSeriesNumber(), latestInvoice2.getSeriesNumber());
        Assertions.assertEquals(entity2.getBusiness().getUID(), B2);

    }

    @Test
    public void testMultipleSeriesIssuing() throws InterruptedException, ExecutionException {
        StringID<Business> B1 = StringID.fromValue("Business 1");
        this.createSeries(B1, "A");
        this.createSeries(B1, "B");
        this.createSeries(B1, "C");
        ADBusinessEntity businessEntity1 = new ADBusinessTestUtil(injector).getBusinessEntity(B1);
        Integer totalThreads = 10;
        ConcurrentTestUtil test = new ConcurrentTestUtil(totalThreads);

        List<Future<?>> results1 = test.runThreads(new TestRunner(injector, "A", businessEntity1));
        List<Future<?>> results2 = test.runThreads(new TestRunner(injector, "B", businessEntity1));
        List<Future<?>> results3 = test.runThreads(new TestRunner(injector, "C", businessEntity1));

        List<ADInvoice> invoices1 = this.executeThreads(results1);
        List<ADInvoice> invoices2 = this.executeThreads(results2);
        List<ADInvoice> invoices3 = this.executeThreads(results3);

        ADInvoiceEntity latestInvoice1 = this.getInstance(DAOADInvoice.class).getLatestInvoiceFromSeries("A", B1);
        Assertions.assertEquals(this.filterNotNull(invoices1).size(), latestInvoice1.getSeriesNumber().intValue());
        ADInvoiceEntity latestInvoice2 = this.getInstance(DAOADInvoice.class).getLatestInvoiceFromSeries("B", B1);
        Assertions.assertEquals(this.filterNotNull(invoices2).size(), latestInvoice2.getSeriesNumber().intValue());
        ADInvoiceEntity latestInvoice3 = this.getInstance(DAOADInvoice.class).getLatestInvoiceFromSeries("C", B1);
        Assertions.assertEquals(this.filterNotNull(invoices3).size(), latestInvoice3.getSeriesNumber().intValue());

    }

    private ADInvoiceEntity getLatestInvoice(List<ADInvoice> invoices) {
        int lastSeriesNumber = 0;
        ADInvoiceEntity entity = null;
        for (ADInvoice invoice : invoices) {
            if (lastSeriesNumber < invoice.getSeriesNumber()) {
                lastSeriesNumber = invoice.getSeriesNumber();
                entity = (ADInvoiceEntity) invoice;
            }
        }
        return entity;
    }

    private List<ADInvoice> executeThreads(List<Future<?>> results) throws InterruptedException, ExecutionException {
        List<ADInvoice> invoices = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            ADInvoice invoice = (ADInvoice) results.get(i).get();
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
