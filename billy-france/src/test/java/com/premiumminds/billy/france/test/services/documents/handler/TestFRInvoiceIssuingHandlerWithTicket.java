/**
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
package com.premiumminds.billy.france.test.services.documents.handler;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.premiumminds.billy.core.exceptions.InvalidTicketException;
import com.premiumminds.billy.core.services.TicketManager;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Ticket;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.entities.FRBusinessEntity;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.services.entities.FRInvoice;
import com.premiumminds.billy.france.services.persistence.FRInvoicePersistenceService;
import com.premiumminds.billy.france.util.Services;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.FRMockDependencyModule;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.services.documents.FRDocumentAbstractTest;
import com.premiumminds.billy.france.test.util.FRBusinessTestUtil;
import com.premiumminds.billy.france.test.util.FRInvoiceTestUtil;

public class TestFRInvoiceIssuingHandlerWithTicket extends FRDocumentAbstractTest {

    private UID issuedInvoiceUID;
    private UID ticketUID;
    private TicketManager ticketManager;

    private String DEFAULT_SERIES = INVOICE_TYPE.FT + " " + FRPersistencyAbstractTest.DEFAULT_SERIES;

    @Before
    public void setUpNewInvoice() {

        try {
            this.setUpParamenters();
            this.parameters.setInvoiceSeries(this.DEFAULT_SERIES);

            FRBusinessEntity business = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity("business");
            FRInvoice.Builder invoiceBuilder =
                    new FRInvoiceTestUtil(FRAbstractTest.injector).getInvoiceBuilder(business);

            this.ticketManager = this.getInstance(TicketManager.class);

            String ticketValue = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));
            this.ticketUID = new UID(ticketValue);

            Services services = new Services(FRAbstractTest.injector);
            services.issueDocument(invoiceBuilder, this.parameters, ticketValue);

            FRInvoice invoice = invoiceBuilder.build();
            this.issuedInvoiceUID = invoice.getUID();

        } catch (InvalidTicketException e) {
            e.printStackTrace();
        } catch (DocumentIssuingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIssuedInvoiceSimpleWithTicket() throws DocumentIssuingException {
        FRInvoice issuedInvoice = this.getInstance(DAOFRInvoice.class).get(this.issuedInvoiceUID);

        FRInvoicePersistenceService service = FRAbstractTest.injector.getInstance(FRInvoicePersistenceService.class);

        FRInvoiceEntity ticketEntity = (FRInvoiceEntity) service.getWithTicket(this.ticketUID);

        Assert.assertTrue(issuedInvoice != null);
        Assert.assertEquals(this.DEFAULT_SERIES, issuedInvoice.getSeries());
        Assert.assertTrue(1 == issuedInvoice.getSeriesNumber());
        String formatedNumber = this.DEFAULT_SERIES + "/1";
        Assert.assertEquals(formatedNumber, issuedInvoice.getNumber());

        Assert.assertTrue(this.ticketManager.ticketExists(this.ticketUID.getValue()) == true);
        Assert.assertTrue(ticketEntity != null);
        Assert.assertTrue(ticketEntity.getUID().getValue().equals(issuedInvoice.getUID().getValue()));
        Assert.assertTrue(ticketEntity.getNumber().equals(issuedInvoice.getNumber()));
        Assert.assertTrue(ticketEntity.getSeries().equals(issuedInvoice.getSeries()));

    }

    @Test
    public void testTicketAssociateEntity() throws DocumentIssuingException {

        FRInvoicePersistenceService service = FRAbstractTest.injector.getInstance(FRInvoicePersistenceService.class);
        FRInvoiceEntity ticketEntity = null;
        UID noResultUID = new UID("noresult");
        String notIssuedUID = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));

        try {
            ticketEntity = (FRInvoiceEntity) service.getWithTicket(noResultUID);
        } catch (NoResultException e) {

        }

        Assert.assertTrue(this.ticketManager.ticketExists(noResultUID.getValue()) == false);
        Assert.assertTrue(ticketEntity == null);

        try {
            ticketEntity = (FRInvoiceEntity) service.getWithTicket(new UID(notIssuedUID));
        } catch (NoResultException e) {
        }

        Assert.assertTrue(this.ticketManager.ticketExists(new UID(notIssuedUID).getValue()) == true);
        Assert.assertFalse(this.ticketManager.ticketIssued(notIssuedUID) == false);
        Assert.assertTrue(ticketEntity == null);

    }

    @Test
    public void testIssueWithUsedTicket() {
        Services services = new Services(FRAbstractTest.injector);
        FRInvoiceEntity entity = null;
        this.parameters.setInvoiceSeries(this.DEFAULT_SERIES);

        FRBusinessEntity business = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity("business");
        FRInvoice.Builder builder = new FRInvoiceTestUtil(FRAbstractTest.injector).getInvoiceBuilder(business);

        try {

            entity = (FRInvoiceEntity) services.issueDocument(builder, this.parameters,
                    this.issuedInvoiceUID.getValue());
        } catch (InvalidTicketException e) {

        } catch (DocumentIssuingException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(entity == null);
    }

    @Test
    public void testOpenCloseConnections() {

        Services services = new Services(FRAbstractTest.injector);
        FRInvoicePersistenceService persistenceService =
                FRAbstractTest.injector.getInstance(FRInvoicePersistenceService.class);
        FRBusinessEntity business = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity("business");
        FRInvoice.Builder testinvoice = new FRInvoiceTestUtil(FRAbstractTest.injector).getInvoiceBuilder(business);

        EntityManager em = FRAbstractTest.injector.getInstance(EntityManager.class);
        em.getTransaction().begin();

        TicketManager newTicketManager = FRAbstractTest.injector.getInstance(TicketManager.class);
        String testValue = newTicketManager.generateTicket(this.getInstance(Ticket.Builder.class));
        UID testUID = new UID(testValue);
        em.getTransaction().commit();

        em.clear();

        services = new Services(Guice.createInjector(new FRMockDependencyModule()));

        try {
            services.issueDocument(testinvoice, this.parameters, testValue);
        } catch (Exception e) {
        }

        FRInvoiceEntity ticketEntity = null;
        try {
            ticketEntity = (FRInvoiceEntity) persistenceService.getWithTicket(testUID);
        } catch (Exception e) {
        }
        Assert.assertTrue(ticketEntity == null);
    }

}
