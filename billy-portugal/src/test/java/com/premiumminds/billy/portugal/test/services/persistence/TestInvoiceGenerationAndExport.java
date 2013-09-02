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
package com.premiumminds.billy.portugal.test.services.persistence;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.export.exceptions.SAFTPTExportException;
import com.premiumminds.billy.portugal.test.util.PTApplicationTestUtil;


public class TestInvoiceGenerationAndExport extends
		PTPersistenceServiceAbstractTest {
	
	private PTInvoice invoice;
	private PTCreditNote creditNote;
	private UID appUID;
	
	@Before
	public void setUp() throws DocumentIssuingException, MalformedURLException {
		this.invoice = getNewIssuedInvoice();
		this.creditNote = getNewIssuedCreditnote(invoice);
		
		PTApplication.Builder appBuilder = new PTApplicationTestUtil(injector).getApplicationBuilder();
		appUID = new UID();
		appBuilder.setUID(appUID);
		billy.applications().persistence().create(appBuilder);
		
	}
	
	@Test
	public void testExportInvoice() throws SAFTPTExportException, IOException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013, 1, 1);
		Date tau = calendar.getTime();
		billy.saft().export(appUID, invoice.getBusiness().getUID(), "1", tau, new Date());
	}

}
