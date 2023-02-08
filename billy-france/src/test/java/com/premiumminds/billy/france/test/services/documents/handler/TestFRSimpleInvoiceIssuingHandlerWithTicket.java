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
import com.premiumminds.billy.france.persistence.dao.DAOFRSimpleInvoice;
import com.premiumminds.billy.france.persistence.entities.FRBusinessEntity;
import com.premiumminds.billy.france.persistence.entities.FRSimpleInvoiceEntity;
import com.premiumminds.billy.france.services.entities.FRSimpleInvoice;
import com.premiumminds.billy.france.services.entities.FRSimpleInvoice.CLIENTTYPE;
import com.premiumminds.billy.france.services.persistence.FRSimpleInvoicePersistenceService;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.FRMockDependencyModule;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;
import com.premiumminds.billy.france.test.services.documents.FRDocumentAbstractTest;
import com.premiumminds.billy.france.test.util.FRBusinessTestUtil;
import com.premiumminds.billy.france.test.util.FRSimpleInvoiceTestUtil;
import com.premiumminds.billy.france.util.Services;

public class TestFRSimpleInvoiceIssuingHandlerWithTicket extends FRDocumentAbstractTest {

    private StringID<GenericInvoice> issuedInvoiceUID;
    private StringID<Ticket> ticketUID;
    private TicketManager ticketManager;

    private String DEFAULT_SERIES = INVOICE_TYPE.FS + " " + FRPersistencyAbstractTest.DEFAULT_SERIES;

    @BeforeEach
    public void setUpNewSimpleInvoice() {

        try {
            this.setUpParamenters();
            this.parameters.setInvoiceSeries(this.DEFAULT_SERIES);

            final String uid = "business";
            this.createSeries(uid, this.DEFAULT_SERIES);
            FRBusinessEntity business = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity(uid);
            FRSimpleInvoice.Builder simpleInvoiceBuilder = new FRSimpleInvoiceTestUtil(FRAbstractTest.injector)
                    .getSimpleInvoiceBuilder(business, CLIENTTYPE.CUSTOMER);

            this.ticketManager = this.getInstance(TicketManager.class);

            this.ticketUID = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));

            Services services = new Services(FRAbstractTest.injector);
            services.issueDocument(simpleInvoiceBuilder, this.parameters, ticketUID);

            FRSimpleInvoice simpleInvoice = simpleInvoiceBuilder.build();
            this.issuedInvoiceUID = simpleInvoice.getUID();

        } catch (InvalidTicketException e) {
            e.printStackTrace();
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIssuedInvoiceSimpleWithTicket() throws DocumentIssuingException {
        FRSimpleInvoice issuedInvoice = this.getInstance(DAOFRSimpleInvoice.class).get(this.issuedInvoiceUID);

        FRSimpleInvoicePersistenceService service =
                FRAbstractTest.injector.getInstance(FRSimpleInvoicePersistenceService.class);

        FRSimpleInvoiceEntity ticketEntity = (FRSimpleInvoiceEntity) service.getWithTicket(this.ticketUID);

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

        FRSimpleInvoicePersistenceService service =
                FRAbstractTest.injector.getInstance(FRSimpleInvoicePersistenceService.class);
        FRSimpleInvoiceEntity ticketEntity = null;
        StringID<Ticket> noResultUID = StringID.fromValue("noresult");
        StringID<Ticket> notIssuedUID = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));

        try {
            ticketEntity = (FRSimpleInvoiceEntity) service.getWithTicket(noResultUID);
        } catch (NoResultException e) {

        }

        Assertions.assertTrue(this.ticketManager.ticketExists(noResultUID) == false);
        Assertions.assertTrue(ticketEntity == null);

        try {
            ticketEntity = (FRSimpleInvoiceEntity) service.getWithTicket(notIssuedUID);
        } catch (NoResultException e) {
        }

        Assertions.assertTrue(this.ticketManager.ticketExists(notIssuedUID) == true);
        Assertions.assertFalse(this.ticketManager.ticketIssued(notIssuedUID) == false);
        Assertions.assertTrue(ticketEntity == null);

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
                    StringID.fromValue(this.issuedInvoiceUID.getIdentifier()));
        } catch (InvalidTicketException e) {

        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(entity == null);
    }

    @Test
    public void testOpenCloseConnections() {
        FRSimpleInvoicePersistenceService persistenceService =
                FRAbstractTest.injector.getInstance(FRSimpleInvoicePersistenceService.class);
        FRBusinessEntity business = new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity("business");
        FRSimpleInvoice.Builder testinvoice = new FRSimpleInvoiceTestUtil(FRAbstractTest.injector)
                .getSimpleInvoiceBuilder(business, CLIENTTYPE.CUSTOMER);

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

        FRSimpleInvoiceEntity ticketEntity = null;
        try {
            ticketEntity = (FRSimpleInvoiceEntity) persistenceService.getWithTicket(testValue);
        } catch (Exception e) {
        }
        Assertions.assertTrue(ticketEntity == null);
    }

}
