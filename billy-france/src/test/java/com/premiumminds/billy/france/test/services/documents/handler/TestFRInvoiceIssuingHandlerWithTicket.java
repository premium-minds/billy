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
package com.premiumminds.billy.france.test.services.documents.handler;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.inject.Guice;
import com.premiumminds.billy.core.exceptions.InvalidTicketException;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.TicketManager;
import com.premiumminds.billy.core.services.entities.Ticket;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.entities.FRBusinessEntity;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.services.entities.FRInvoice;
import com.premiumminds.billy.france.services.persistence.FRInvoicePersistenceService;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.FRMockDependencyModule;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.services.documents.FRDocumentAbstractTest;
import com.premiumminds.billy.france.test.util.FRBusinessTestUtil;
import com.premiumminds.billy.france.test.util.FRInvoiceTestUtil;
import com.premiumminds.billy.france.util.Services;

public class TestFRInvoiceIssuingHandlerWithTicket extends FRDocumentAbstractTest {

    private StringID<GenericInvoice> issuedInvoiceUID;
    private StringID<Ticket> ticketUID;
    private TicketManager ticketManager;

    private String DEFAULT_SERIES = INVOICE_TYPE.FT + " " + FRPersistencyAbstractTest.DEFAULT_SERIES;

    @BeforeEach
    public void setUpNewInvoice() {

        try {
            this.setUpParamenters();
            this.parameters.setInvoiceSeries(this.DEFAULT_SERIES);

            final String uid = "business";
            this.createSeries(uid, this.DEFAULT_SERIES);
            FRBusinessEntity business = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity(uid);
            FRInvoice.Builder invoiceBuilder =
                    new FRInvoiceTestUtil(FRAbstractTest.injector).getInvoiceBuilder(business);

            this.ticketManager = this.getInstance(TicketManager.class);

            this.ticketUID = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));

            Services services = new Services(FRAbstractTest.injector);
            services.issueDocument(invoiceBuilder, this.parameters, ticketUID);

            FRInvoice invoice = invoiceBuilder.build();
            this.issuedInvoiceUID = invoice.getUID();

        } catch (InvalidTicketException e) {
            e.printStackTrace();
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIssuedInvoiceSimpleWithTicket() throws DocumentIssuingException {
        FRInvoice issuedInvoice = this.getInstance(DAOFRInvoice.class).get(this.issuedInvoiceUID);

        FRInvoicePersistenceService service = FRAbstractTest.injector.getInstance(FRInvoicePersistenceService.class);

        FRInvoiceEntity ticketEntity = (FRInvoiceEntity) service.getWithTicket(this.ticketUID);

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

        FRInvoicePersistenceService service = FRAbstractTest.injector.getInstance(FRInvoicePersistenceService.class);
        FRInvoiceEntity ticketEntity = null;
        StringID<Ticket> noResultUID = StringID.fromValue("noresult");
        StringID<Ticket> notIssuedUID = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));

        try {
            ticketEntity = (FRInvoiceEntity) service.getWithTicket(noResultUID);
        } catch (NoResultException e) {

        }

        Assertions.assertTrue(this.ticketManager.ticketExists(noResultUID) == false);
        Assertions.assertTrue(ticketEntity == null);

        try {
            ticketEntity = (FRInvoiceEntity) service.getWithTicket(notIssuedUID);
        } catch (NoResultException e) {
        }

        Assertions.assertTrue(this.ticketManager.ticketExists(notIssuedUID) == true);
        Assertions.assertFalse(this.ticketManager.ticketIssued(notIssuedUID) == false);
        Assertions.assertTrue(ticketEntity == null);

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
                    StringID.fromValue(this.issuedInvoiceUID.getIdentifier()));
        } catch (InvalidTicketException e) {

        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(entity == null);
    }

    @Test
    public void testOpenCloseConnections() {
        FRInvoicePersistenceService persistenceService =
                FRAbstractTest.injector.getInstance(FRInvoicePersistenceService.class);
        FRBusinessEntity business = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity("business");
        FRInvoice.Builder testinvoice = new FRInvoiceTestUtil(FRAbstractTest.injector).getInvoiceBuilder(business);

        EntityManager em = FRAbstractTest.injector.getInstance(EntityManager.class);
        em.getTransaction().begin();

        TicketManager newTicketManager = FRAbstractTest.injector.getInstance(TicketManager.class);
        StringID<Ticket> testValue = newTicketManager.generateTicket(this.getInstance(Ticket.Builder.class));
        em.getTransaction().commit();

        em.clear();

        Services services = new Services(Guice.createInjector(new FRMockDependencyModule()));

        try {
            services.issueDocument(testinvoice, this.parameters, testValue);
        } catch (Exception e) {
        }

        FRInvoiceEntity ticketEntity = null;
        try {
            ticketEntity = (FRInvoiceEntity) persistenceService.getWithTicket(testValue);
        } catch (Exception e) {
        }
        Assertions.assertTrue(ticketEntity == null);
    }

}
