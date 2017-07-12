/**
 * Copyright (C) 2017 Premium Minds.
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
package com.premiumminds.billy.spain.test.services.documents.handler;

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
import com.premiumminds.billy.spain.persistence.dao.DAOESSimpleInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESBusinessEntity;
import com.premiumminds.billy.spain.persistence.entities.ESSimpleInvoiceEntity;
import com.premiumminds.billy.spain.services.entities.ESSimpleInvoice;
import com.premiumminds.billy.spain.services.entities.ESSimpleInvoice.CLIENTTYPE;
import com.premiumminds.billy.spain.services.persistence.ESSimpleInvoicePersistenceService;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.ESMockDependencyModule;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.services.documents.ESDocumentAbstractTest;
import com.premiumminds.billy.spain.test.util.ESBusinessTestUtil;
import com.premiumminds.billy.spain.test.util.ESSimpleInvoiceTestUtil;
import com.premiumminds.billy.spain.util.Services;

public class TestESSimpleInvoiceIssuingHandlerWithTicket extends ESDocumentAbstractTest {

    private UID issuedInvoiceUID;
    private UID ticketUID;
    private TicketManager ticketManager;

    private String DEFAULT_SERIES = INVOICE_TYPE.FS + " " + ESPersistencyAbstractTest.DEFAULT_SERIES;

    @Before
    public void setUpNewSimpleInvoice() {

        try {
            this.setUpParamenters();
            this.parameters.setInvoiceSeries(this.DEFAULT_SERIES);

            ESBusinessEntity business = new ESBusinessTestUtil(ESAbstractTest.injector).getBusinessEntity("business");
            ESSimpleInvoice.Builder simpleInvoiceBuilder = new ESSimpleInvoiceTestUtil(ESAbstractTest.injector)
                    .getSimpleInvoiceBuilder(business, CLIENTTYPE.CUSTOMER);

            this.ticketManager = this.getInstance(TicketManager.class);

            String ticketValue = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));
            this.ticketUID = new UID(ticketValue);

            Services services = new Services(ESAbstractTest.injector);
            services.issueDocument(simpleInvoiceBuilder, this.parameters, ticketValue);

            ESSimpleInvoice simpleInvoice = simpleInvoiceBuilder.build();
            this.issuedInvoiceUID = simpleInvoice.getUID();

        } catch (InvalidTicketException e) {
            e.printStackTrace();
        } catch (DocumentIssuingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIssuedInvoiceSimpleWithTicket() throws DocumentIssuingException {
        ESSimpleInvoice issuedInvoice = this.getInstance(DAOESSimpleInvoice.class).get(this.issuedInvoiceUID);

        ESSimpleInvoicePersistenceService service =
                ESAbstractTest.injector.getInstance(ESSimpleInvoicePersistenceService.class);

        ESSimpleInvoiceEntity ticketEntity = (ESSimpleInvoiceEntity) service.getWithTicket(this.ticketUID);

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

        ESSimpleInvoicePersistenceService service =
                ESAbstractTest.injector.getInstance(ESSimpleInvoicePersistenceService.class);
        ESSimpleInvoiceEntity ticketEntity = null;
        UID noResultUID = new UID("noresult");
        String notIssuedUID = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));

        try {
            ticketEntity = (ESSimpleInvoiceEntity) service.getWithTicket(noResultUID);
        } catch (NoResultException e) {

        }

        Assert.assertTrue(this.ticketManager.ticketExists(noResultUID.getValue()) == false);
        Assert.assertTrue(ticketEntity == null);

        try {
            ticketEntity = (ESSimpleInvoiceEntity) service.getWithTicket(new UID(notIssuedUID));
        } catch (NoResultException e) {
        }

        Assert.assertTrue(this.ticketManager.ticketExists(new UID(notIssuedUID).getValue()) == true);
        Assert.assertFalse(this.ticketManager.ticketIssued(notIssuedUID) == false);
        Assert.assertTrue(ticketEntity == null);

    }

    @Test
    public void testIssueWithUsedTicket() {
        Services services = new Services(ESAbstractTest.injector);
        ESSimpleInvoiceEntity entity = null;
        this.parameters.setInvoiceSeries(this.DEFAULT_SERIES);

        ESBusinessEntity business = new ESBusinessTestUtil(ESAbstractTest.injector).getBusinessEntity("business");
        ESSimpleInvoice.Builder builder = new ESSimpleInvoiceTestUtil(ESAbstractTest.injector)
                .getSimpleInvoiceBuilder(business, CLIENTTYPE.CUSTOMER);

        try {

            entity = (ESSimpleInvoiceEntity) services.issueDocument(builder, this.parameters,
                    this.issuedInvoiceUID.getValue());
        } catch (InvalidTicketException e) {

        } catch (DocumentIssuingException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(entity == null);
    }

    @Test
    public void testOpenCloseConnections() {

        Services services = new Services(ESAbstractTest.injector);
        ESSimpleInvoicePersistenceService persistenceService =
                ESAbstractTest.injector.getInstance(ESSimpleInvoicePersistenceService.class);
        ESBusinessEntity business = new ESBusinessTestUtil(ESAbstractTest.injector).getBusinessEntity("business");
        ESSimpleInvoice.Builder testinvoice = new ESSimpleInvoiceTestUtil(ESAbstractTest.injector)
                .getSimpleInvoiceBuilder(business, CLIENTTYPE.CUSTOMER);

        EntityManager em = ESAbstractTest.injector.getInstance(EntityManager.class);
        em.getTransaction().begin();

        TicketManager newTicketManager = ESAbstractTest.injector.getInstance(TicketManager.class);
        String testValue = newTicketManager.generateTicket(this.getInstance(Ticket.Builder.class));
        UID testUID = new UID(testValue);
        em.getTransaction().commit();

        em.clear();

        services = new Services(Guice.createInjector(new ESMockDependencyModule()));

        try {
            services.issueDocument(testinvoice, this.parameters, testValue);
        } catch (Exception e) {
        }

        ESSimpleInvoiceEntity ticketEntity = null;
        try {
            ticketEntity = (ESSimpleInvoiceEntity) persistenceService.getWithTicket(testUID);
        } catch (Exception e) {
        }
        Assert.assertTrue(ticketEntity == null);
    }

}
