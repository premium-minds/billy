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
import com.premiumminds.billy.france.persistence.dao.DAOFRSimpleInvoice;
import com.premiumminds.billy.france.persistence.entities.FRBusinessEntity;
import com.premiumminds.billy.france.persistence.entities.FRSimpleInvoiceEntity;
import com.premiumminds.billy.france.services.entities.FRSimpleInvoice;
import com.premiumminds.billy.france.services.entities.FRSimpleInvoice.CLIENTTYPE;
import com.premiumminds.billy.france.services.persistence.FRSimpleInvoicePersistenceService;
import com.premiumminds.billy.france.util.Services;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.FRMockDependencyModule;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.services.documents.FRDocumentAbstractTest;
import com.premiumminds.billy.france.test.util.FRBusinessTestUtil;
import com.premiumminds.billy.france.test.util.FRSimpleInvoiceTestUtil;

public class TestFRSimpleInvoiceIssuingHandlerWithTicket extends FRDocumentAbstractTest {

    private UID issuedInvoiceUID;
    private UID ticketUID;
    private TicketManager ticketManager;

    private String DEFAULT_SERIES = INVOICE_TYPE.FS + " " + FRPersistencyAbstractTest.DEFAULT_SERIES;

    @Before
    public void setUpNewSimpleInvoice() {

        try {
            this.setUpParamenters();
            this.parameters.setInvoiceSeries(this.DEFAULT_SERIES);

            FRBusinessEntity business = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity("business");
            FRSimpleInvoice.Builder simpleInvoiceBuilder = new FRSimpleInvoiceTestUtil(FRAbstractTest.injector)
                    .getSimpleInvoiceBuilder(business, CLIENTTYPE.CUSTOMER);

            this.ticketManager = this.getInstance(TicketManager.class);

            String ticketValue = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));
            this.ticketUID = new UID(ticketValue);

            Services services = new Services(FRAbstractTest.injector);
            services.issueDocument(simpleInvoiceBuilder, this.parameters, ticketValue);

            FRSimpleInvoice simpleInvoice = simpleInvoiceBuilder.build();
            this.issuedInvoiceUID = simpleInvoice.getUID();

        } catch (InvalidTicketException e) {
            e.printStackTrace();
        } catch (DocumentIssuingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIssuedInvoiceSimpleWithTicket() throws DocumentIssuingException {
        FRSimpleInvoice issuedInvoice = this.getInstance(DAOFRSimpleInvoice.class).get(this.issuedInvoiceUID);

        FRSimpleInvoicePersistenceService service =
                FRAbstractTest.injector.getInstance(FRSimpleInvoicePersistenceService.class);

        FRSimpleInvoiceEntity ticketEntity = (FRSimpleInvoiceEntity) service.getWithTicket(this.ticketUID);

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

        FRSimpleInvoicePersistenceService service =
                FRAbstractTest.injector.getInstance(FRSimpleInvoicePersistenceService.class);
        FRSimpleInvoiceEntity ticketEntity = null;
        UID noResultUID = new UID("noresult");
        String notIssuedUID = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));

        try {
            ticketEntity = (FRSimpleInvoiceEntity) service.getWithTicket(noResultUID);
        } catch (NoResultException e) {

        }

        Assert.assertTrue(this.ticketManager.ticketExists(noResultUID.getValue()) == false);
        Assert.assertTrue(ticketEntity == null);

        try {
            ticketEntity = (FRSimpleInvoiceEntity) service.getWithTicket(new UID(notIssuedUID));
        } catch (NoResultException e) {
        }

        Assert.assertTrue(this.ticketManager.ticketExists(new UID(notIssuedUID).getValue()) == true);
        Assert.assertFalse(this.ticketManager.ticketIssued(notIssuedUID) == false);
        Assert.assertTrue(ticketEntity == null);

    }

    @Test
    public void testIssueWithUsedTicket() {
        Services services = new Services(FRAbstractTest.injector);
        FRSimpleInvoiceEntity entity = null;
        this.parameters.setInvoiceSeries(this.DEFAULT_SERIES);

        FRBusinessEntity business = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity("business");
        FRSimpleInvoice.Builder builder = new FRSimpleInvoiceTestUtil(FRAbstractTest.injector)
                .getSimpleInvoiceBuilder(business, CLIENTTYPE.CUSTOMER);

        try {

            entity = (FRSimpleInvoiceEntity) services.issueDocument(builder, this.parameters,
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
        FRSimpleInvoicePersistenceService persistenceService =
                FRAbstractTest.injector.getInstance(FRSimpleInvoicePersistenceService.class);
        FRBusinessEntity business = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity("business");
        FRSimpleInvoice.Builder testinvoice = new FRSimpleInvoiceTestUtil(FRAbstractTest.injector)
                .getSimpleInvoiceBuilder(business, CLIENTTYPE.CUSTOMER);

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

        FRSimpleInvoiceEntity ticketEntity = null;
        try {
            ticketEntity = (FRSimpleInvoiceEntity) persistenceService.getWithTicket(testUID);
        } catch (Exception e) {
        }
        Assert.assertTrue(ticketEntity == null);
    }

}
