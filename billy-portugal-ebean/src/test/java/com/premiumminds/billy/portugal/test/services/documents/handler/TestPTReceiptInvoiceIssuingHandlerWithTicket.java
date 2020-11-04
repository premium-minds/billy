/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.documents.handler;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.exceptions.InvalidTicketException;
import com.premiumminds.billy.core.services.TicketManager;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Ticket;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTReceiptInvoice;
import com.premiumminds.billy.portugal.services.persistence.PTReceiptInvoicePersistenceService;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.services.documents.PTDocumentAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTBusinessTestUtil;
import com.premiumminds.billy.portugal.test.util.PTReceiptInvoiceTestUtil;
import com.premiumminds.billy.portugal.util.Services;

public class TestPTReceiptInvoiceIssuingHandlerWithTicket extends PTDocumentAbstractTest {

    /*private static final TYPE DEFAULT_TYPE = TYPE.FR;
    private static final SourceBilling SOURCE_BILLING = SourceBilling.P;

    private UID issuedInvoiceUID;
    private UID ticketUID;
    private TicketManager ticketManager;

    @BeforeEach
    public void setUpNewReceiptInvoice() {

        try {
            this.setUpParamenters();
            this.parameters.setInvoiceSeries(PTPersistencyAbstractTest.DEFAULT_SERIES);

            PTBusinessEntity business = new PTBusinessTestUtil(PTAbstractTest.injector).getBusinessEntity("business");
            PTReceiptInvoice.Builder receiptInvoiceBuilder = new PTReceiptInvoiceTestUtil(PTAbstractTest.injector)
                    .getReceiptInvoiceBuilder(business, TestPTReceiptInvoiceIssuingHandlerWithTicket.SOURCE_BILLING);

            this.ticketManager = this.getInstance(TicketManager.class);

            String ticketValue = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));
            this.ticketUID = new UID(ticketValue);

            Services services = PTAbstractTest.injector.getInstance(Services.class);
            services.issueDocument(receiptInvoiceBuilder, this.parameters, ticketValue);

            PTReceiptInvoice receiptInvoice = receiptInvoiceBuilder.build();
            this.issuedInvoiceUID = receiptInvoice.getUID();

        } catch (InvalidTicketException e) {
            e.printStackTrace();
        } catch (DocumentIssuingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIssuedInvoiceReceiptWithTicket() throws DocumentIssuingException {
        PTReceiptInvoice issuedInvoice = this.getInstance(DAOPTReceiptInvoice.class).get(this.issuedInvoiceUID);

        PTReceiptInvoicePersistenceService service =
                PTAbstractTest.injector.getInstance(PTReceiptInvoicePersistenceService.class);

        PTReceiptInvoiceEntity ticketEntity = (PTReceiptInvoiceEntity) service.getWithTicket(this.ticketUID);

        Assertions.assertTrue(issuedInvoice != null);
        Assertions.assertEquals(PTPersistencyAbstractTest.DEFAULT_SERIES, issuedInvoice.getSeries());
        Assertions.assertTrue(1 == issuedInvoice.getSeriesNumber());
        String formatedNumber = TestPTReceiptInvoiceIssuingHandlerWithTicket.DEFAULT_TYPE + " " +
                PTPersistencyAbstractTest.DEFAULT_SERIES + "/1";
        Assertions.assertEquals(formatedNumber, issuedInvoice.getNumber());
        Assertions.assertEquals(TestPTReceiptInvoiceIssuingHandlerWithTicket.SOURCE_BILLING,
                issuedInvoice.getSourceBilling());

        Assertions.assertTrue(this.ticketManager.ticketExists(this.ticketUID.getValue()) == true);
        Assertions.assertTrue(ticketEntity != null);
        Assertions.assertTrue(ticketEntity.getUID().getValue().equals(issuedInvoice.getUID().getValue()));
        Assertions.assertTrue(ticketEntity.getHash().equals(issuedInvoice.getHash()));
        Assertions.assertTrue(ticketEntity.getNumber().equals(issuedInvoice.getNumber()));
        Assertions.assertTrue(ticketEntity.getSeries().equals(issuedInvoice.getSeries()));

    }

    @Test
    public void testTicketAssociateEntity() throws DocumentIssuingException {

        PTReceiptInvoicePersistenceService service =
                PTAbstractTest.injector.getInstance(PTReceiptInvoicePersistenceService.class);
        PTReceiptInvoiceEntity ticketEntity = null;
        UID noResultUID = new UID("noresult");
        String notIssuedUID = this.ticketManager.generateTicket(this.getInstance(Ticket.Builder.class));

        try {
            ticketEntity = (PTReceiptInvoiceEntity) service.getWithTicket(noResultUID);
        } catch (NoResultException e) {

        }

        Assertions.assertTrue(this.ticketManager.ticketExists(noResultUID.getValue()) == false);
        Assertions.assertTrue(ticketEntity == null);

        try {
            ticketEntity = (PTReceiptInvoiceEntity) service.getWithTicket(new UID(notIssuedUID));
        } catch (NoResultException e) {
        }

        Assertions.assertTrue(this.ticketManager.ticketExists(new UID(notIssuedUID).getValue()) == true);
        Assertions.assertFalse(this.ticketManager.ticketIssued(notIssuedUID) == false);
        Assertions.assertTrue(ticketEntity == null);

    }

    @Test
    public void testIssueWithUsedTicket() {
        Services services = PTAbstractTest.injector.getInstance(Services.class);
        PTReceiptInvoiceEntity entity = null;
        this.parameters.setInvoiceSeries(PTPersistencyAbstractTest.DEFAULT_SERIES);

        PTBusinessEntity business = new PTBusinessTestUtil(PTAbstractTest.injector).getBusinessEntity("business");
        PTReceiptInvoice.Builder builder = new PTReceiptInvoiceTestUtil(PTAbstractTest.injector)
                .getReceiptInvoiceBuilder(business, TestPTReceiptInvoiceIssuingHandlerWithTicket.SOURCE_BILLING);

        try {

            entity = (PTReceiptInvoiceEntity) services.issueDocument(builder, this.parameters,
                    this.issuedInvoiceUID.getValue());
        } catch (InvalidTicketException e) {

        } catch (DocumentIssuingException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(entity == null);
    }

    @Test
    public void testOpenCloseConnections() {

        Services services = PTAbstractTest.injector.getInstance(Services.class);
        PTReceiptInvoicePersistenceService persistenceService =
                PTAbstractTest.injector.getInstance(PTReceiptInvoicePersistenceService.class);
        PTBusinessEntity business = new PTBusinessTestUtil(PTAbstractTest.injector).getBusinessEntity("business");
        PTReceiptInvoice.Builder testinvoice = new PTReceiptInvoiceTestUtil(PTAbstractTest.injector)
                .getReceiptInvoiceBuilder(business, TestPTReceiptInvoiceIssuingHandlerWithTicket.SOURCE_BILLING);

        //EntityManager em = PTAbstractTest.injector.getInstance(EntityManager.class);
        //em.getTransaction().begin();

        TicketManager newTicketManager = PTAbstractTest.injector.getInstance(TicketManager.class);
        String testValue = newTicketManager.generateTicket(this.getInstance(Ticket.Builder.class));
        UID testUID = new UID(testValue);
        // em.getTransaction().commit();

        // em.clear();

        services = PTAbstractTest.injector.getInstance(Services.class);

        try {
            services.issueDocument(testinvoice, this.parameters, testValue);
        } catch (Exception e) {
        }

        PTReceiptInvoiceEntity ticketEntity = null;
        try {
            ticketEntity = (PTReceiptInvoiceEntity) persistenceService.getWithTicket(testUID);
        } catch (Exception e) {
        }
        Assertions.assertTrue(ticketEntity == null);
    }*/

}
