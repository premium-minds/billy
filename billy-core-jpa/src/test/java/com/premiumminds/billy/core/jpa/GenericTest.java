/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-core-jpa.
 * 
 * billy-core-jpa is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-core-jpa is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core-jpa.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.jpa;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.CoreJPADependencyModule;
import com.premiumminds.billy.core.CoreJPAPersistenceDependencyModule;
import com.premiumminds.billy.core.persistence.dao.DAOBusiness;
import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;

public class GenericTest {

	static Injector injector;
	
	@BeforeClass 
	public static void setUpClass() {      
		injector = Guice.createInjector(new CoreJPADependencyModule(), new CoreJPAPersistenceDependencyModule());
		injector.getInstance(CoreJPAPersistenceDependencyModule.Initializer.class);
	}

	@Test
	public void test1() {
		Business.Builder builder = injector.getInstance(Business.Builder.class);
		GenericInvoiceEntry.Builder entryBuilder = injector.getInstance(GenericInvoiceEntry.Builder.class);
		Contact.Builder contactBuilder = injector.getInstance(Contact.Builder.class);
		Contact.Builder contactBuilder2 = injector.getInstance(Contact.Builder.class);
		Context.Builder contextBuilder = injector.getInstance(Context.Builder.class);
		Address.Builder addressBuilder = injector.getInstance(Address.Builder.class);
		Address.Builder addressBuilder2 = injector.getInstance(Address.Builder.class);
		Application.Builder applicationBuilder = injector.getInstance(Application.Builder.class);
		
		DAOContext daoContext = injector.getInstance(DAOContext.class);

		daoContext.beginTransaction();
		
		Context parent = contextBuilder
			.setName("parent name")
			.setDescription("the parent description")
			.setParentContextUID(null)
			.build();
		parent = daoContext.create((ContextEntity) parent);
		
		contextBuilder.clear();
		Context  child = contextBuilder
				.setName("child name")
				.setDescription("the child description")
				.setParentContextUID(parent.getUID())
				.build();
		child = daoContext.create((ContextEntity) child);
		
		builder
			.setOperationalContextUID(child.getUID())
			.setName("name")
			.setCommercialName("commercial name")
			.addContact(contactBuilder
							.setName("name")
							.setEmail("email")
							.setFax("fax")
							.setMobile("mobile")
							.setTelephone("phone")
							.setWebsite("website"))
			.setAddress(addressBuilder
							.setBuilding("building")
							.setCity("city")
							.setDetails("details")
							.setISOCountry("PT")
							.setNumber("number")
							.setPostalCode("2345")
							.setRegion("region")
							.setStreetName("street"))
			.setBillingAddress(addressBuilder2
							.setBuilding("building")
							.setCity("city")
							.setDetails("details")
							.setISOCountry("PT")
							.setNumber("number")
							.setPostalCode("2345")
							.setRegion("region")
							.setStreetName("street"))
			.addApplication(applicationBuilder
							.setDeveloperCompanyName("name")
							.setDeveloperCompanyTaxIdentifier("taxid")
							.setName("application name")
							.setVersion("1.0")
							.addContact(contactBuilder2
									.setName("name")
									.setEmail("email")
									.setFax("fax")
									.setMobile("mobile")
									.setTelephone("phone")
									.setWebsite("website")))
			.setFinancialID("financial id");
		injector.getInstance(DAOBusiness.class).create((BusinessEntity) builder.build());
		
		
//		GenericInvoice.Builder invoiceBuilder = injector.getInstance(GenericInvoice.Builder.class);
//		invoiceBuilder.set
//		
//		
//		
//		
//		
//		daoContext.commit();
		
		return;
//		builder
//			.setName("business name")
//			.setCommercialName("commercial name")
//			.setOperationalContextUID(uidContext);
//		
//		injector.getInstance(DAOBusiness.class).create((BusinessEntity) builder.build());
//		
	}
}
