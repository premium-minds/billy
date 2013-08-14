/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy portugal (PT Pack).
 * 
 * billy portugal (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy portugal (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.documents;

import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;

public class TestDocumentIssuingService extends PTDocumentAbstractTest {

	private DocumentIssuingService service;

	@Before
	public void setUp() {
		service = this.getInstance(DocumentIssuingService.class);
		service.addHandler(PTInvoiceEntity.class, PTAbstractTest.injector
				.getInstance(PTInvoiceIssuingHandler.class));

		parameters.setInvoiceSeries("A");
	}

	@Test
	public void testIssuingService() throws DocumentIssuingException {

		service.issue(
				new PTInvoiceTestUtil(injector).getInvoiceBuilder("b1", "b2",
						SourceBilling.P, Arrays.asList("prod")).setDate(
						new Date(new Date().getTime() + 100000)), parameters);

	}

}
