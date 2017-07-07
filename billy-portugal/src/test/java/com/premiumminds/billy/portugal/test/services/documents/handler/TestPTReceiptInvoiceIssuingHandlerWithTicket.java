/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.documents.handler;

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
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTReceiptInvoice;
import com.premiumminds.billy.portugal.services.persistence.PTReceiptInvoicePersistenceService;
import com.premiumminds.billy.portugal.test.PTMockDependencyModule;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.services.documents.PTDocumentAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTBusinessTestUtil;
import com.premiumminds.billy.portugal.test.util.PTReceiptInvoiceTestUtil;
import com.premiumminds.billy.portugal.util.Services;

public class TestPTReceiptInvoiceIssuingHandlerWithTicket extends
	PTDocumentAbstractTest {

	private static final TYPE			DEFAULT_TYPE	= TYPE.FR;
	private static final SourceBilling	SOURCE_BILLING	= SourceBilling.P;

	private UID							issuedInvoiceUID;
	private UID							ticketUID;
	private TicketManager				ticketManager;

	@Before
	public void setUpNewReceiptInvoice() {

		try {
			setUpParamenters();
			this.parameters
					.setInvoiceSeries(PTPersistencyAbstractTest.DEFAULT_SERIES);

			PTBusinessEntity business = new PTBusinessTestUtil(injector)
					.getBusinessEntity("business");
			PTReceiptInvoice.Builder receiptInvoiceBuilder = new PTReceiptInvoiceTestUtil(
					injector)
					.getReceiptInvoiceBuilder(
							business,
							TestPTReceiptInvoiceIssuingHandlerWithTicket.SOURCE_BILLING);

			ticketManager = getInstance(TicketManager.class);

			String ticketValue = ticketManager
					.generateTicket(getInstance(Ticket.Builder.class));
			ticketUID = new UID(ticketValue);

			Services services = new Services(injector);
			services.issueDocument(receiptInvoiceBuilder, this.parameters,
					ticketValue);

			PTReceiptInvoice receiptInvoice = receiptInvoiceBuilder.build();
			this.issuedInvoiceUID = receiptInvoice.getUID();

		} catch (InvalidTicketException e) {
			e.printStackTrace();
		} catch (DocumentIssuingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIssuedInvoiceReceiptWithTicket()
		throws DocumentIssuingException {
		PTReceiptInvoice issuedInvoice = (PTReceiptInvoice) this.getInstance(
				DAOPTReceiptInvoice.class).get(this.issuedInvoiceUID);

		PTReceiptInvoicePersistenceService service = injector
				.getInstance(PTReceiptInvoicePersistenceService.class);

		PTReceiptInvoiceEntity ticketEntity = (PTReceiptInvoiceEntity) service
				.getWithTicket(ticketUID);

		Assert.assertTrue(issuedInvoice != null);
		Assert.assertEquals(PTPersistencyAbstractTest.DEFAULT_SERIES,
				issuedInvoice.getSeries());
		Assert.assertTrue(1 == issuedInvoice.getSeriesNumber());
		String formatedNumber = TestPTReceiptInvoiceIssuingHandlerWithTicket.DEFAULT_TYPE
				+ " "
				+ TestPTReceiptInvoiceIssuingHandlerWithTicket.DEFAULT_SERIES
				+ "/1";
		Assert.assertEquals(formatedNumber, issuedInvoice.getNumber());
		Assert.assertEquals(
				TestPTReceiptInvoiceIssuingHandlerWithTicket.SOURCE_BILLING,
				issuedInvoice.getSourceBilling());

		Assert.assertTrue(ticketManager.ticketExists(ticketUID.getValue()) == true);
		Assert.assertTrue(ticketEntity != null);
		Assert.assertTrue(ticketEntity.getUID().getValue()
				.equals(issuedInvoice.getUID().getValue()));
		Assert.assertTrue(ticketEntity.getHash()
				.equals(issuedInvoice.getHash()));
		Assert.assertTrue(ticketEntity.getNumber().equals(
				issuedInvoice.getNumber()));
		Assert.assertTrue(ticketEntity.getSeries().equals(
				issuedInvoice.getSeries()));

	}

	@Test
	public void testTicketAssociateEntity() throws DocumentIssuingException {

		PTReceiptInvoicePersistenceService service = injector
				.getInstance(PTReceiptInvoicePersistenceService.class);
		PTReceiptInvoiceEntity ticketEntity = null;
		UID noResultUID = new UID("noresult");
		String notIssuedUID = ticketManager
				.generateTicket(getInstance(Ticket.Builder.class));

		try {
			ticketEntity = (PTReceiptInvoiceEntity) service
					.getWithTicket(noResultUID);
		} catch (NoResultException e) {

		}

		Assert.assertTrue(ticketManager.ticketExists(noResultUID.getValue()) == false);
		Assert.assertTrue(ticketEntity == null);

		try {
			ticketEntity = (PTReceiptInvoiceEntity) service
					.getWithTicket(new UID(notIssuedUID));
		} catch (NoResultException e) {}

		Assert.assertTrue(ticketManager.ticketExists(new UID(notIssuedUID)
				.getValue()) == true);
		Assert.assertFalse(ticketManager.ticketIssued(notIssuedUID) == false);
		Assert.assertTrue(ticketEntity == null);

	}

	@Test
	public void testIssueWithUsedTicket() {
		Services services = new Services(injector);
		PTReceiptInvoiceEntity entity = null;
		this.parameters
				.setInvoiceSeries(PTPersistencyAbstractTest.DEFAULT_SERIES);

		PTBusinessEntity business = new PTBusinessTestUtil(injector)
				.getBusinessEntity("business");
		PTReceiptInvoice.Builder builder = new PTReceiptInvoiceTestUtil(
				injector).getReceiptInvoiceBuilder(business,
				TestPTReceiptInvoiceIssuingHandlerWithTicket.SOURCE_BILLING);

		try {

			entity = (PTReceiptInvoiceEntity) services.issueDocument(builder,
					this.parameters, issuedInvoiceUID.getValue());
		} catch (InvalidTicketException e) {

		} catch (DocumentIssuingException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(entity == null);
	}

	@Test
	public void testOpenCloseConnections() {

		Services services = new Services(injector);
		PTReceiptInvoicePersistenceService persistenceService = injector
				.getInstance(PTReceiptInvoicePersistenceService.class);
		PTBusinessEntity business = new PTBusinessTestUtil(injector)
				.getBusinessEntity("business");
		PTReceiptInvoice.Builder testinvoice = new PTReceiptInvoiceTestUtil(
				injector).getReceiptInvoiceBuilder(business,
				TestPTReceiptInvoiceIssuingHandlerWithTicket.SOURCE_BILLING);

		EntityManager em = injector.getInstance(EntityManager.class);
		em.getTransaction().begin();

		TicketManager newTicketManager = injector
				.getInstance(TicketManager.class);
		String testValue = newTicketManager
				.generateTicket(getInstance(Ticket.Builder.class));
		UID testUID = new UID(testValue);
		em.getTransaction().commit();

		em.clear();

		services = new Services(
				Guice.createInjector(new PTMockDependencyModule()));

		try {
			services.issueDocument(testinvoice, this.parameters, testValue);
		} catch (Exception e) {}

		PTReceiptInvoiceEntity ticketEntity = null;
		try {
			ticketEntity = (PTReceiptInvoiceEntity) persistenceService
					.getWithTicket(testUID);
		} catch (Exception e) {}
		Assert.assertTrue(ticketEntity == null);
	}

}
