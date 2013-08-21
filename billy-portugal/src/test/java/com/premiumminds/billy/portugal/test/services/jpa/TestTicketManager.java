
package com.premiumminds.billy.portugal.test.services.jpa;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.services.TicketManager;
import com.premiumminds.billy.core.services.UID;

public class TestTicketManager extends PTJPAAbstractTest{
	private static final String OBJECT_UID = "object_uid";
	private static final Date CREATION_DATE = new Date();
	private static final Date PROCESS_DATE = new Date();
	private TicketManager manager = null;
	String ticket = null;
	
	@Before
	public void setUp(){
		manager = new TicketManager(injector);
	}
	
	@Test
	public void generateTicketTest(){
		ticket = manager.generateTicket();
		Assert.assertTrue(ticket != null);
	}
	
	@Test
	public void ticketExistsTest(){
		ticket = manager.generateTicket();
		Assert.assertTrue(manager.ticketExists(ticket));
	}
	
	@Test 
	public void updateTicketTest(){
		ticket = manager.generateTicket();
		manager.updateTicket(new UID(ticket), new UID(OBJECT_UID), CREATION_DATE, PROCESS_DATE);
		Assert.assertTrue(manager.ticketExists(ticket));
	}

	
}