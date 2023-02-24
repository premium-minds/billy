/*
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

import com.google.inject.Guice;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.exceptions.InvalidTicketException;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.TicketManager;
import com.premiumminds.billy.core.services.entities.Ticket;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.entities.ESBusinessEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.entities.ESInvoice;
import com.premiumminds.billy.spain.services.persistence.ESInvoicePersistenceService;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.ESMockDependencyModule;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.test.services.documents.ESDocumentAbstractTest;
import com.premiumminds.billy.spain.test.util.ESBusinessTestUtil;
import com.premiumminds.billy.spain.test.util.ESInvoiceTestUtil;
import com.premiumminds.billy.spain.util.Services;

public class TestESInvoiceIssuingHandlerWithTicket extends ESDocumentAbstractTest {

    private StringID<GenericInvoice> issuedInvoiceUID;
    private StringID<Ticket> ticketUID;
    private TicketManager ticketManager;

    private String DEFAULT_SERIES = INVOICE_TYPE.FT + " " + ESPersistencyAbstractTest.DEFAULT_SERIES;

    @BeforeEach
    public void setUpNewInvoice() {

        try {
            this.setUpParamenters();
            this.parameters.setInvoiceSeries(this.DEFAULT_SERIES);

            ESBusinessEntity business = new ESBusinessTestUtil(ESAbstractTest.injector).getBusinessEntity(StringID.fromValue("business"));
            ESInvoice.Builder invoiceBuilder =
                    new ESInvoiceTestUtil(ESAbstractTest.injector).getInvoiceBuilder(business);

            this.ticketManager = this.getInstance(TicketManager.class);
            this.ticketUID = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));

            this.createSeries(invoiceBuilder.build(), this.DEFAULT_SERIES);

            Services services = new Services(ESAbstractTest.injector);
            services.issueDocument(invoiceBuilder, this.parameters, ticketUID);

            ESInvoice invoice = invoiceBuilder.build();
            this.issuedInvoiceUID = invoice.getUID();

        } catch (InvalidTicketException e) {
            e.printStackTrace();
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIssuedInvoiceSimpleWithTicket() throws DocumentIssuingException {
        ESInvoice issuedInvoice = this.getInstance(DAOESInvoice.class).get(this.issuedInvoiceUID);

        ESInvoicePersistenceService service = ESAbstractTest.injector.getInstance(ESInvoicePersistenceService.class);

        ESInvoiceEntity ticketEntity = (ESInvoiceEntity) service.getWithTicket(this.ticketUID);

        Assertions.assertTrue(issuedInvoice != null);
        Assertions.assertEquals(this.DEFAULT_SERIES, issuedInvoice.getSeries());
        Assertions.assertTrue(1 == issuedInvoice.getSeriesNumber());
        String formatedNumber = this.DEFAULT_SERIES + "/1";
        Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());

        Assertions.assertTrue(this.ticketManager.ticketExists(this.ticketUID) == true);
        Assertions.assertTrue(ticketEntity != null);
        Assertions.assertTrue(ticketEntity.getUID().getIdentifier().equals(issuedInvoice.getUID().getIdentifier()));
        Assertions.assertTrue(ticketEntity.getNumber().equals(issuedInvoice.getNumber()));
        Assertions.assertTrue(ticketEntity.getSeries().equals(issuedInvoice.getSeries()));

    }

    @Test
    public void testTicketAssociateEntity() throws DocumentIssuingException {

        ESInvoicePersistenceService service = ESAbstractTest.injector.getInstance(ESInvoicePersistenceService.class);
        ESInvoiceEntity ticketEntity = null;
        StringID<Ticket> noResultUID = StringID.fromValue("noresult");
        StringID<Ticket> notIssuedUID = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));

        try {
            ticketEntity = (ESInvoiceEntity) service.getWithTicket(noResultUID);
        } catch (NoResultException e) {

        }

        Assertions.assertTrue(this.ticketManager.ticketExists(noResultUID) == false);
        Assertions.assertTrue(ticketEntity == null);

        try {
            ticketEntity = (ESInvoiceEntity) service.getWithTicket(notIssuedUID);
        } catch (NoResultException e) {
        }

        Assertions.assertTrue(this.ticketManager.ticketExists(notIssuedUID) == true);
        Assertions.assertFalse(this.ticketManager.ticketIssued(notIssuedUID) == false);
        Assertions.assertTrue(ticketEntity == null);

    }

    @Test
    public void testIssueWithUsedTicket() {
        Services services = new Services(ESAbstractTest.injector);
        ESInvoiceEntity entity = null;
        this.parameters.setInvoiceSeries(this.DEFAULT_SERIES);

        ESBusinessEntity business = new ESBusinessTestUtil(ESAbstractTest.injector).getBusinessEntity(StringID.fromValue("business"));
        ESInvoice.Builder builder = new ESInvoiceTestUtil(ESAbstractTest.injector).getInvoiceBuilder(business);

        try {

            entity = (ESInvoiceEntity) services.issueDocument(builder, this.parameters,
                    StringID.fromValue(this.issuedInvoiceUID.getIdentifier()));
        } catch (InvalidTicketException e) {

        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(entity == null);
    }

    @Test
    public void testOpenCloseConnections() {
        ESInvoicePersistenceService persistenceService =
                ESAbstractTest.injector.getInstance(ESInvoicePersistenceService.class);
        ESBusinessEntity business = new ESBusinessTestUtil(ESAbstractTest.injector).getBusinessEntity(StringID.fromValue("business"));
        ESInvoice.Builder testinvoice = new ESInvoiceTestUtil(ESAbstractTest.injector).getInvoiceBuilder(business);

        EntityManager em = ESAbstractTest.injector.getInstance(EntityManager.class);
        em.getTransaction().begin();

        TicketManager newTicketManager = ESAbstractTest.injector.getInstance(TicketManager.class);
        StringID<Ticket> testValue = newTicketManager.generateTicket(this.getInstance(Ticket.Builder.class));
        em.getTransaction().commit();

        em.clear();

        Services services = new Services(Guice.createInjector(new ESMockDependencyModule()));

        try {
            services.issueDocument(testinvoice, this.parameters, testValue);
        } catch (Exception e) {
        }

        ESInvoiceEntity ticketEntity = null;
        try {
            ticketEntity = (ESInvoiceEntity) persistenceService.getWithTicket(testValue);
        } catch (Exception e) {
        }
        Assertions.assertTrue(ticketEntity == null);
    }

}
