/**
 * Copyright (C) 2013 Premium Minds.
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
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.premiumminds.billy.core.exceptions.InvalidTicketException;
import com.premiumminds.billy.core.services.TicketManager;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.PortugalBootstrap;
import com.premiumminds.billy.portugal.PortugalDependencyModule;
import com.premiumminds.billy.portugal.Config.Key.Context.Portugal;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice;
import com.premiumminds.billy.portugal.services.persistence.PTSimpleInvoicePersistenceService;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.PortugalTestPersistenceDependencyModule;
import com.premiumminds.billy.portugal.test.PortugalTestPersistenceDependencyModule.Initializer;
import com.premiumminds.billy.portugal.test.services.documents.PTDocumentAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTBusinessTestUtil;
import com.premiumminds.billy.portugal.test.util.PTSimpleInvoiceTestUtil;
import com.premiumminds.billy.portugal.util.Services;

public class TestPTSimpleInvoiceIssuingHandlerWithTicket extends
		PTDocumentAbstractTest {

	private static final TYPE DEFAULT_TYPE = TYPE.FS;
	private static final SourceBilling SOURCE_BILLING = SourceBilling.P;

	private UID issuedInvoiceUID;
	private UID ticketUID;
	private TicketManager ticketManager;

	@Before
	public void setUpNewSimpleInvoice() {

		try {
			setUpParamenters();
			this.parameters
					.setInvoiceSeries(PTPersistencyAbstractTest.DEFAULT_SERIES);

			PTBusinessEntity business = new PTBusinessTestUtil(injector).getBusinessEntity("business");
			PTSimpleInvoice.Builder simpleInvoiceBuilder = new PTSimpleInvoiceTestUtil(
					injector)
					.getSimpleInvoiceBuilder(business,TestPTSimpleInvoiceIssuingHandlerWithTicket.SOURCE_BILLING);

			ticketManager = new TicketManager(injector);

			String ticketValue = ticketManager.generateTicket();
			ticketUID = new UID(ticketValue);

			Services services = new Services(injector);
			services.issueDocument(simpleInvoiceBuilder, this.parameters,
					ticketValue);

			PTSimpleInvoice simpleInvoice = simpleInvoiceBuilder.build();
			this.issuedInvoiceUID = simpleInvoice.getUID();

		} catch (InvalidTicketException e) {
			e.printStackTrace();
		} catch (DocumentIssuingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIssuedInvoiceSimpleWithTicket()
			throws DocumentIssuingException {
		PTSimpleInvoice issuedInvoice = (PTSimpleInvoice) this.getInstance(
				DAOPTSimpleInvoice.class).get(this.issuedInvoiceUID);

		PTSimpleInvoicePersistenceService service = injector
				.getInstance(PTSimpleInvoicePersistenceService.class);

		PTSimpleInvoiceEntity ticketEntity = (PTSimpleInvoiceEntity) service
				.getWithTicket(ticketUID);

		Assert.assertTrue(issuedInvoice != null);
		Assert.assertEquals(PTPersistencyAbstractTest.DEFAULT_SERIES,
				issuedInvoice.getSeries());
		Assert.assertTrue(1 == issuedInvoice.getSeriesNumber());
		String formatedNumber = TestPTSimpleInvoiceIssuingHandlerWithTicket.DEFAULT_TYPE
				+ " "
				+ TestPTSimpleInvoiceIssuingHandlerWithTicket.DEFAULT_SERIES
				+ "/1";
		Assert.assertEquals(formatedNumber, issuedInvoice.getNumber());
		Assert.assertEquals(
				TestPTSimpleInvoiceIssuingHandlerWithTicket.SOURCE_BILLING,
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

		PTSimpleInvoicePersistenceService service = injector
				.getInstance(PTSimpleInvoicePersistenceService.class);
		PTSimpleInvoiceEntity ticketEntity = null;
		UID noResultUID = new UID("noresult");
		String notIssuedUID = ticketManager.generateTicket();

		try {
			ticketEntity = (PTSimpleInvoiceEntity) service
					.getWithTicket(noResultUID);
		} catch (NoResultException e) {
		}

		Assert.assertTrue(ticketManager.ticketExists(noResultUID.getValue()) == false);
		Assert.assertTrue(ticketEntity == null);

		try {
			ticketEntity = (PTSimpleInvoiceEntity) service
					.getWithTicket(new UID(notIssuedUID));
		} catch (NoResultException e) {
		}

		Assert.assertTrue(ticketManager.ticketExists(new UID(notIssuedUID)
				.getValue()) == true);
		Assert.assertFalse(ticketManager.ticketIssued(notIssuedUID) == false);
		Assert.assertTrue(ticketEntity == null);

	}

	@Test
	public void testIssueWithUsedTicket() {
		Services services = new Services(injector);
		PTSimpleInvoiceEntity entity = null;
		this.parameters
		.setInvoiceSeries(PTPersistencyAbstractTest.DEFAULT_SERIES);

		PTBusinessEntity business = new PTBusinessTestUtil(injector).getBusinessEntity("business");
		PTSimpleInvoice.Builder builder = new PTSimpleInvoiceTestUtil(injector)
				.getSimpleInvoiceBuilder(business ,TestPTSimpleInvoiceIssuingHandlerWithTicket.SOURCE_BILLING);

		try {
			
			entity = (PTSimpleInvoiceEntity) services.issueDocument(builder,
					this.parameters, issuedInvoiceUID.getValue());
		} catch (InvalidTicketException e) {

		} catch (DocumentIssuingException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(entity == null);
	}
	
	@Test
	public void testOpenCloseConnections() {
		/*Services services = new Services(injector);
		PTSimpleInvoicePersistenceService service = injector
				.getInstance(PTSimpleInvoicePersistenceService.class);
		
		
		PTSimpleInvoice.Builder testinvoice = new PTSimpleInvoiceTestUtil(
				injector)
				.getSimpleInvoiceBuilder(TestPTSimpleInvoiceIssuingHandlerWithTicket.SOURCE_BILLING);
		
		TicketManager newTicketManager = injector.getInstance(TicketManager.class);
		String testValue = newTicketManager.generateTicket();
		UID testUID = new UID(testValue);

		openConnection();
		try{
		services.issueDocument(testinvoice, this.parameters,
				testValue);
		}
		catch(Exception e){
			System.out.println(e);
		}
		closeConnection();
		PTSimpleInvoiceEntity ticketEntity = null;
		try {
			
			if(ticketManager.ticketExists(testUID.getValue()))
				System.out.println("existo sim senhor!");
			ticketEntity = (PTSimpleInvoiceEntity) service
					.getEntityForTicket(testUID);
		} catch (Exception e) {
		}
		Assert.assertTrue(ticketEntity == null);*/
	}
	
	public void openConnection(){
		PTAbstractTest.injector = Guice.createInjector(
				new PortugalDependencyModule(),
				new PortugalTestPersistenceDependencyModule());
		PTAbstractTest.injector
				.getInstance(PortugalDependencyModule.Initializer.class);
		PTAbstractTest.injector
				.getInstance(PortugalTestPersistenceDependencyModule.Initializer.class);
	}
	
	public void closeConnection(){
		PTAbstractTest.injector
		.getInstance(PortugalTestPersistenceDependencyModule.Finalizer.class);
	}

}
