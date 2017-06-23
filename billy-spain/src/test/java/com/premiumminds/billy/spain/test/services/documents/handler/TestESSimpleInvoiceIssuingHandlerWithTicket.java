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
      setUpParamenters();
      this.parameters.setInvoiceSeries(DEFAULT_SERIES);

      ESBusinessEntity business = new ESBusinessTestUtil(injector).getBusinessEntity("business");
      ESSimpleInvoice.Builder simpleInvoiceBuilder = new ESSimpleInvoiceTestUtil(injector)
          .getSimpleInvoiceBuilder(business, CLIENTTYPE.CUSTOMER);

      ticketManager = getInstance(TicketManager.class);

      String ticketValue = ticketManager.generateTicket(getInstance(Ticket.Builder.class));
      ticketUID = new UID(ticketValue);

      Services services = new Services(injector);
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
    ESSimpleInvoice issuedInvoice = (ESSimpleInvoice) this.getInstance(DAOESSimpleInvoice.class)
        .get(this.issuedInvoiceUID);

    ESSimpleInvoicePersistenceService service = injector
        .getInstance(ESSimpleInvoicePersistenceService.class);

    ESSimpleInvoiceEntity ticketEntity = (ESSimpleInvoiceEntity) service.getWithTicket(ticketUID);

    Assert.assertTrue(issuedInvoice != null);
    Assert.assertEquals(DEFAULT_SERIES, issuedInvoice.getSeries());
    Assert.assertTrue(1 == issuedInvoice.getSeriesNumber());
    String formatedNumber = DEFAULT_SERIES + "/1";
    Assert.assertEquals(formatedNumber, issuedInvoice.getNumber());

    Assert.assertTrue(ticketManager.ticketExists(ticketUID.getValue()) == true);
    Assert.assertTrue(ticketEntity != null);
    Assert.assertTrue(ticketEntity.getUID().getValue().equals(issuedInvoice.getUID().getValue()));
    Assert.assertTrue(ticketEntity.getNumber().equals(issuedInvoice.getNumber()));
    Assert.assertTrue(ticketEntity.getSeries().equals(issuedInvoice.getSeries()));

  }

  @Test
  public void testTicketAssociateEntity() throws DocumentIssuingException {

    ESSimpleInvoicePersistenceService service = injector
        .getInstance(ESSimpleInvoicePersistenceService.class);
    ESSimpleInvoiceEntity ticketEntity = null;
    UID noResultUID = new UID("noresult");
    String notIssuedUID = ticketManager.generateTicket(getInstance(Ticket.Builder.class));

    try {
      ticketEntity = (ESSimpleInvoiceEntity) service.getWithTicket(noResultUID);
    } catch (NoResultException e) {

    }

    Assert.assertTrue(ticketManager.ticketExists(noResultUID.getValue()) == false);
    Assert.assertTrue(ticketEntity == null);

    try {
      ticketEntity = (ESSimpleInvoiceEntity) service.getWithTicket(new UID(notIssuedUID));
    } catch (NoResultException e) {
    }

    Assert.assertTrue(ticketManager.ticketExists(new UID(notIssuedUID).getValue()) == true);
    Assert.assertFalse(ticketManager.ticketIssued(notIssuedUID) == false);
    Assert.assertTrue(ticketEntity == null);

  }

  @Test
  public void testIssueWithUsedTicket() {
    Services services = new Services(injector);
    ESSimpleInvoiceEntity entity = null;
    this.parameters.setInvoiceSeries(DEFAULT_SERIES);

    ESBusinessEntity business = new ESBusinessTestUtil(injector).getBusinessEntity("business");
    ESSimpleInvoice.Builder builder = new ESSimpleInvoiceTestUtil(injector)
        .getSimpleInvoiceBuilder(business, CLIENTTYPE.CUSTOMER);

    try {

      entity = (ESSimpleInvoiceEntity) services.issueDocument(builder, this.parameters,
          issuedInvoiceUID.getValue());
    } catch (InvalidTicketException e) {

    } catch (DocumentIssuingException e) {
      e.printStackTrace();
    }
    Assert.assertTrue(entity == null);
  }

  @Test
  public void testOpenCloseConnections() {

    Services services = new Services(injector);
    ESSimpleInvoicePersistenceService persistenceService = injector
        .getInstance(ESSimpleInvoicePersistenceService.class);
    ESBusinessEntity business = new ESBusinessTestUtil(injector).getBusinessEntity("business");
    ESSimpleInvoice.Builder testinvoice = new ESSimpleInvoiceTestUtil(injector)
        .getSimpleInvoiceBuilder(business, CLIENTTYPE.CUSTOMER);

    EntityManager em = injector.getInstance(EntityManager.class);
    em.getTransaction().begin();

    TicketManager newTicketManager = injector.getInstance(TicketManager.class);
    String testValue = newTicketManager.generateTicket(getInstance(Ticket.Builder.class));
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
