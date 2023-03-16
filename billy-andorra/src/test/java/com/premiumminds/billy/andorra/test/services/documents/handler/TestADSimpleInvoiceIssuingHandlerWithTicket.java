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
package com.premiumminds.billy.andorra.test.services.documents.handler;

import com.google.inject.Guice;

import com.premiumminds.billy.andorra.persistence.dao.DAOADSimpleInvoice;
import com.premiumminds.billy.andorra.persistence.entities.ADBusinessEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADSimpleInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADSimpleInvoice;
import com.premiumminds.billy.andorra.services.entities.ADSimpleInvoice.Builder;
import com.premiumminds.billy.andorra.services.entities.ADSimpleInvoice.CLIENTTYPE;
import com.premiumminds.billy.andorra.services.persistence.ADSimpleInvoicePersistenceService;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.ADMockDependencyModule;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;
import com.premiumminds.billy.andorra.test.util.ADBusinessTestUtil;
import com.premiumminds.billy.andorra.test.util.ADSimpleInvoiceTestUtil;
import com.premiumminds.billy.andorra.util.Services;
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
import com.premiumminds.billy.andorra.test.services.documents.ADDocumentAbstractTest;

public class TestADSimpleInvoiceIssuingHandlerWithTicket extends ADDocumentAbstractTest {

    private StringID<GenericInvoice> issuedInvoiceUID;
    private StringID<Ticket> ticketUID;
    private TicketManager ticketManager;

    private String DEFAULT_SERIES = INVOICE_TYPE.FS + " " + ADPersistencyAbstractTest.DEFAULT_SERIES;

    @BeforeEach
    public void setUpNewSimpleInvoice() {

        try {
            this.setUpParamenters();
            this.parameters.setInvoiceSeries(this.DEFAULT_SERIES);

            ADBusinessEntity
                business = new ADBusinessTestUtil(ADAbstractTest.injector).getBusinessEntity(StringID.fromValue("business"));
            Builder simpleInvoiceBuilder = new ADSimpleInvoiceTestUtil(ADAbstractTest.injector)
                    .getSimpleInvoiceBuilder(business, CLIENTTYPE.CUSTOMER);

            this.ticketManager = this.getInstance(TicketManager.class);

            this.ticketUID = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));

            this.createSeries(simpleInvoiceBuilder.build(), this.DEFAULT_SERIES);

            Services services = new Services(ADAbstractTest.injector);
            services.issueDocument(simpleInvoiceBuilder, this.parameters, ticketUID);

            ADSimpleInvoice simpleInvoice = simpleInvoiceBuilder.build();
            this.issuedInvoiceUID = simpleInvoice.getUID();

        } catch (InvalidTicketException e) {
            e.printStackTrace();
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIssuedInvoiceSimpleWithTicket() throws DocumentIssuingException {
        ADSimpleInvoice issuedInvoice = this.getInstance(DAOADSimpleInvoice.class).get(this.issuedInvoiceUID);

        ADSimpleInvoicePersistenceService service =
                ADAbstractTest.injector.getInstance(ADSimpleInvoicePersistenceService.class);

        ADSimpleInvoiceEntity ticketEntity = (ADSimpleInvoiceEntity) service.getWithTicket(this.ticketUID);

        Assertions.assertTrue(issuedInvoice != null);
        Assertions.assertEquals(this.DEFAULT_SERIES, issuedInvoice.getSeries());
        Assertions.assertTrue(1 == issuedInvoice.getSeriesNumber());
        String formatedNumber = this.DEFAULT_SERIES + "/1";
        Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());

        Assertions.assertTrue(this.ticketManager.ticketExists(this.ticketUID));
        Assertions.assertTrue(ticketEntity != null);
        Assertions.assertTrue(ticketEntity.getUID().getIdentifier().equals(issuedInvoice.getUID().getIdentifier()));
        Assertions.assertTrue(ticketEntity.getNumber().equals(issuedInvoice.getNumber()));
        Assertions.assertTrue(ticketEntity.getSeries().equals(issuedInvoice.getSeries()));

    }

    @Test
    public void testTicketAssociateEntity() throws DocumentIssuingException {

        ADSimpleInvoicePersistenceService service =
                ADAbstractTest.injector.getInstance(ADSimpleInvoicePersistenceService.class);
        ADSimpleInvoiceEntity ticketEntity = null;
        StringID<Ticket> noResultUID = StringID.fromValue("noresult");
        StringID<Ticket> notIssuedUID = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));

        try {
            ticketEntity = (ADSimpleInvoiceEntity) service.getWithTicket(noResultUID);
        } catch (NoResultException e) {

        }

        Assertions.assertTrue(!this.ticketManager.ticketExists(noResultUID));
        Assertions.assertTrue(ticketEntity == null);

        try {
            ticketEntity = (ADSimpleInvoiceEntity) service.getWithTicket(notIssuedUID);
        } catch (NoResultException e) {
        }

        Assertions.assertTrue(this.ticketManager.ticketExists(notIssuedUID) == true);
        Assertions.assertFalse(this.ticketManager.ticketIssued(notIssuedUID) == false);
        Assertions.assertTrue(ticketEntity == null);

    }

    @Test
    public void testIssueWithUsedTicket() {
        Services services = new Services(ADAbstractTest.injector);
        ADSimpleInvoiceEntity entity = null;
        this.parameters.setInvoiceSeries(this.DEFAULT_SERIES);

        ADBusinessEntity
            business = new ADBusinessTestUtil(ADAbstractTest.injector).getBusinessEntity(StringID.fromValue("business"));
        ADSimpleInvoice.Builder builder = new ADSimpleInvoiceTestUtil(ADAbstractTest.injector)
                .getSimpleInvoiceBuilder(business, CLIENTTYPE.CUSTOMER);

        try {

            entity = (ADSimpleInvoiceEntity) services.issueDocument(builder, this.parameters,
                                                                    StringID.fromValue(this.issuedInvoiceUID.getIdentifier()));
        } catch (InvalidTicketException e) {

        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(entity == null);
    }

    @Test
    public void testOpenCloseConnections() {

        Services services = new Services(ADAbstractTest.injector);
        ADSimpleInvoicePersistenceService persistenceService =
                ADAbstractTest.injector.getInstance(ADSimpleInvoicePersistenceService.class);
        ADBusinessEntity
            business = new ADBusinessTestUtil(ADAbstractTest.injector).getBusinessEntity(StringID.fromValue("business"));
        ADSimpleInvoice.Builder testinvoice = new ADSimpleInvoiceTestUtil(ADAbstractTest.injector)
                .getSimpleInvoiceBuilder(business, CLIENTTYPE.CUSTOMER);

        EntityManager em = ADAbstractTest.injector.getInstance(EntityManager.class);
        em.getTransaction().begin();

        TicketManager newTicketManager = ADAbstractTest.injector.getInstance(TicketManager.class);
        StringID<Ticket> testValue = newTicketManager.generateTicket(this.getInstance(Ticket.Builder.class));
        em.getTransaction().commit();

        em.clear();

        services = new Services(Guice.createInjector(new ADMockDependencyModule()));

        try {
            services.issueDocument(testinvoice, this.parameters, testValue);
        } catch (Exception e) {
        }

        ADSimpleInvoiceEntity ticketEntity = null;
        try {
            ticketEntity = (ADSimpleInvoiceEntity) persistenceService.getWithTicket(testValue);
        } catch (Exception e) {
        }
        Assertions.assertTrue(ticketEntity == null);
    }

}
