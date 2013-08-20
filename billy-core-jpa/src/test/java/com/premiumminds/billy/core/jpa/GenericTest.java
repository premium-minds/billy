/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy core JPA.
 *
 * billy core JPA is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core JPA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core JPA. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.jpa;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.CoreJPADependencyModule;
import com.premiumminds.billy.core.CoreJPAPersistenceDependencyModule;
import com.premiumminds.billy.core.persistence.dao.DAOBusiness;
import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;

public class GenericTest {

	static Injector	injector;

	@BeforeClass
	public static void setUpClass() {
		GenericTest.injector = Guice.createInjector(
				new CoreJPADependencyModule(),
				new CoreJPAPersistenceDependencyModule());
		GenericTest.injector
				.getInstance(CoreJPAPersistenceDependencyModule.Initializer.class);
	}

	@Test
	public void test1() {
		Business.Builder builder = GenericTest.injector
				.getInstance(Business.Builder.class);
		Contact.Builder contactBuilder = GenericTest.injector
				.getInstance(Contact.Builder.class);
		Contact.Builder contactBuilder2 = GenericTest.injector
				.getInstance(Contact.Builder.class);
		Context.Builder contextBuilder = GenericTest.injector
				.getInstance(Context.Builder.class);
		Address.Builder addressBuilder = GenericTest.injector
				.getInstance(Address.Builder.class);
		Address.Builder addressBuilder2 = GenericTest.injector
				.getInstance(Address.Builder.class);
		Application.Builder applicationBuilder = GenericTest.injector
				.getInstance(Application.Builder.class);
		Tax.Builder taxBuilder = GenericTest.injector
				.getInstance(Tax.Builder.class);

		DAOContext daoContext = GenericTest.injector
				.getInstance(DAOContext.class);
		DAOTax daoTax = GenericTest.injector.getInstance(DAOTax.class);

		daoContext.beginTransaction();

		Context parent = contextBuilder.setName("parent name")
				.setDescription("the parent description")
				.setParentContextUID(null).build();
		parent = daoContext.create((ContextEntity) parent);

		contextBuilder.clear();
		Context child = contextBuilder.setName("child name")
				.setDescription("the child description")
				.setParentContextUID(parent.getUID()).build();
		child = daoContext.create((ContextEntity) child);

		builder.setOperationalContextUID(child.getUID())
				.setName("name")
				.setCommercialName("commercial name")
				.addContact(
						contactBuilder.setName("name").setEmail("email")
								.setFax("fax").setMobile("mobile")
								.setTelephone("phone").setWebsite("website"),
						true)
				.setAddress(
						addressBuilder.setBuilding("building").setCity("city")
								.setDetails("details").setISOCountry("PT")
								.setNumber("number").setPostalCode("2345")
								.setRegion("region").setStreetName("street"))
				.setBillingAddress(
						addressBuilder2.setBuilding("building").setCity("city")
								.setDetails("details").setISOCountry("PT")
								.setNumber("number").setPostalCode("2345")
								.setRegion("region").setStreetName("street"))
				.addApplication(
						applicationBuilder
								.setDeveloperCompanyName("name")
								.setDeveloperCompanyTaxIdentifier("taxid")
								.setName("application name")
								.setVersion("1.0")
								.addContact(
										contactBuilder2.setName("name")
												.setEmail("email")
												.setFax("fax")
												.setMobile("mobile")
												.setTelephone("phone")
												.setWebsite("website")))
				.setFinancialID("financial id");

		GenericTest.injector.getInstance(DAOBusiness.class).create(
				(BusinessEntity) builder.build());

		taxBuilder.setCode("PT").setContextUID(child.getUID())
				.setCurrency(Currency.getInstance("EUR"))
				.setDescription("description").setDesignation("designation")
				.setTaxRate(TaxRateType.PERCENTAGE, BigDecimal.TEN)
				.setValidFrom(new Date()).setValidTo(new Date())
				.setValue(BigDecimal.TEN);

		daoTax.create((TaxEntity) taxBuilder.build());
	}

}
