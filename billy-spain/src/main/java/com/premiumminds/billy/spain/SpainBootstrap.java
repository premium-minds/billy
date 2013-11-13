/**
 * Copyright (C) 2013 Premium Minds.
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
package com.premiumminds.billy.spain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import org.joda.time.DateTime;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.spain.persistence.dao.DAOESAddress;
import com.premiumminds.billy.spain.persistence.dao.DAOESContact;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.entities.ESAddressEntity;
import com.premiumminds.billy.spain.persistence.entities.ESContactEntity;
import com.premiumminds.billy.spain.persistence.entities.ESCustomerEntity;
import com.premiumminds.billy.spain.persistence.entities.ESRegionContextEntity;
import com.premiumminds.billy.spain.persistence.entities.ESTaxEntity;
import com.premiumminds.billy.spain.services.entities.ESAddress;
import com.premiumminds.billy.spain.services.entities.ESAddress.Builder;
import com.premiumminds.billy.spain.services.entities.ESContact;
import com.premiumminds.billy.spain.services.entities.ESCustomer;
import com.premiumminds.billy.spain.services.entities.ESRegionContext;
import com.premiumminds.billy.spain.services.entities.ESTax;
import com.premiumminds.billy.spain.services.entities.ESTax.ESVATCode;

public class SpainBootstrap {

	protected static final String	CODE_ES					= "ES";
	protected static final String	CODE_ES_MADRID			= "ES-01";

	public static void main(String[] args) {
		SpainBootstrap.execute();
	}

	private static void execute() {
		// Load dependency injector
		Injector injector = Guice.createInjector(
				new SpainDependencyModule(),
				new SpainPersistenceDependencyModule());
		injector.getInstance(SpainDependencyModule.Initializer.class);
		injector.getInstance(SpainPersistenceDependencyModule.Initializer.class);
		SpainBootstrap.execute(injector);

	}

	public static void execute(final Injector dependencyInjector) {
		DAO<?> dao = dependencyInjector.getInstance(DAOESInvoice.class);
		final Config configuration = new Config();

		try {
			new TransactionWrapper<Void>(dao) {

				@SuppressWarnings("unused")
				@Override
				public Void runTransaction() throws Exception {
					// Dao creation
					DAOESCustomer daoESCustomer = dependencyInjector
							.getInstance(DAOESCustomer.class);
					DAOESRegionContext daoESRegionContext = dependencyInjector
							.getInstance(DAOESRegionContext.class);
					DAOESTax daoESTax = dependencyInjector
							.getInstance(DAOESTax.class);
					DAOESContact daoESContact = dependencyInjector
							.getInstance(DAOESContact.class);
					DAOESAddress daoESAddress = dependencyInjector
							.getInstance(DAOESAddress.class);

					// Builders
					ESCustomer.Builder customerBuilder = dependencyInjector
							.getInstance(ESCustomer.Builder.class);
					ESRegionContext.Builder contextBuilder = dependencyInjector
							.getInstance(ESRegionContext.Builder.class);
					ESTax.Builder taxBuilder = dependencyInjector
							.getInstance(ESTax.Builder.class);
					ESAddress.Builder addressBuilder = dependencyInjector
							.getInstance(ESAddress.Builder.class);
					ESContact.Builder contactBuilder = dependencyInjector
							.getInstance(ESContact.Builder.class);

					// Generic Address
					final ESAddressEntity GENERIC_ADDRESS = this
							.buildAddressEntity(daoESAddress, addressBuilder,
									null, null, null,
									"Desconhecido", null,
									"Desconhecido", "Desconhecido",
									"Desconhecido",
									Config.Key.Address.Generic.UUID);

					// Generic contact
					final ESContactEntity GENERIC_CONTACT = this
							.buildContactEntity(daoESContact, contactBuilder,
									null, null, null,
									null, null,
									null,
									Config.Key.Contact.Generic.UUID);

					// Generic Customer
					final ESCustomerEntity GENERIC_CUSTOMER = this
							.buildCustomerEntity(daoESCustomer,
									customerBuilder, "Consumidor final",
									"11111111H", addressBuilder,
									contactBuilder, false,
									Config.Key.Customer.Generic.UUID);

					// Spain Contexts
					final ESRegionContextEntity CONTEXT_SPAIN = this
							.buildContextEntity(daoESRegionContext,
									contextBuilder, "Spain",
									"The Context for the country Spain",
									null, SpainBootstrap.CODE_ES,
									Config.Key.Context.Spain.UUID);

					final ESRegionContextEntity CONTEXT_CONTINENTAL_SPAIN = this
							.buildContextEntity(
									daoESRegionContext,
									contextBuilder,
									"Spain Continental",
									"The Context for mainland Spain",
									CONTEXT_SPAIN.getUID(),
									SpainBootstrap.CODE_ES,
									Config.Key.Context.Spain.Continental.UUID);

					final ESRegionContextEntity CONTEXT_MADRID_SPAIN = this
							.buildContextEntity(
									daoESRegionContext,
									contextBuilder,
									"Madrid",
									"The Context for the Spanish Madrid region",
									CONTEXT_CONTINENTAL_SPAIN.getUID(),
									SpainBootstrap.CODE_ES_MADRID,
									Config.Key.Context.Spain.Continental.Madrid.UUID);


					// Taxes
					Date from = new DateTime(2013, 1, 1, 0, 0).toDateMidnight()
							.toDateTime().toDate();
					Date to = new DateTime(2014, 1, 1, 0, 0).toDateMidnight()
							.toDateTime().toDate();
					final ESTaxEntity VAT_NORMAL_CONTINENTAL_SPAIN = this
							.buildTaxEntity(
									daoESTax,
									taxBuilder,
									ESVATCode.NORMAL,
									CONTEXT_CONTINENTAL_SPAIN,
									Currency.getInstance("EUR"),
									"IVA Normal Continente",
									"IVA",
									Tax.TaxRateType.PERCENTAGE,
									from,
									to,
									Config.Key.Context.Spain.Continental.VAT.NORMAL_PERCENT,
									Config.Key.Context.Spain.Continental.VAT.NORMAL_UUID);

					final ESTaxEntity VAT_INTERMEDIATE_CONTINENTAL_SPAIN = this
							.buildTaxEntity(
									daoESTax,
									taxBuilder,
									ESVATCode.INTERMEDIATE,
									CONTEXT_CONTINENTAL_SPAIN,
									Currency.getInstance("EUR"),
									"IVA Intermedio Continente",
									"IVA",
									Tax.TaxRateType.PERCENTAGE,
									from,
									to,
									Config.Key.Context.Spain.Continental.VAT.INTERMEDIATE_PERCENT,
									Config.Key.Context.Spain.Continental.VAT.INTERMEDIATE_UUID);

					final ESTaxEntity VAT_REDUCED_CONTINENTAL_SPAIN = this
							.buildTaxEntity(
									daoESTax,
									taxBuilder,
									ESVATCode.REDUCED,
									CONTEXT_CONTINENTAL_SPAIN,
									Currency.getInstance("EUR"),
									"IVA Reduzido Continente",
									"IVA",
									Tax.TaxRateType.PERCENTAGE,
									from,
									to,
									Config.Key.Context.Spain.Continental.VAT.REDUCED_PERCENT,
									Config.Key.Context.Spain.Continental.VAT.REDUCED_UUID);

					
					final ESTaxEntity TAX_EXEMPT_SPAIN = this
							.buildTaxEntity(
									daoESTax,
									taxBuilder,
									ESVATCode.EXEMPT,
									CONTEXT_SPAIN,
									Currency.getInstance("EUR"),
									"Isento de IVA",
									"IVA",
									Tax.TaxRateType.NONE,
									from,
									to,
									Config.Key.Context.Spain.TAX_EXEMPT_VALUE,
									Config.Key.Context.Spain.TAX_EXEMPT_UUID);

					return null;
				}

				private ESAddressEntity buildAddressEntity(
						DAOESAddress daoESAddress, Builder addressBuilder,
						String number, String street, String building,
						String city, String region, String isoCode,
						String details, String postalCode, String key) {

					addressBuilder.setCity(city).setDetails(details)
							.setISOCountry(isoCode).setNumber(number)
							.setRegion(region).setStreetName(street)
							.setPostalCode(postalCode).setBuilding(building);

					ESAddressEntity address = (ESAddressEntity) addressBuilder
							.build();

					address.setUID(configuration.getUID(key));

					daoESAddress.create(address);

					return address;
				}

				private ESContactEntity buildContactEntity(
						DAOESContact daoESContact,
						ESContact.Builder contactBuilder, String name,
						String telephone, String mobile, String email,
						String fax, String website, String key) {

					contactBuilder.clear();

					contactBuilder.setName(name).setEmail(email)
							.setMobile(mobile).setFax(fax)
							.setTelephone(telephone).setWebsite(website);

					final ESContactEntity contact = (ESContactEntity) contactBuilder
							.build();

					contact.setUID(configuration.getUID(key));

					daoESContact.create(contact);

					return contact;
				}

				private ESTaxEntity buildTaxEntity(DAOESTax daoESTax,
						ESTax.Builder taxBuilder, String taxCode,
						ESRegionContextEntity context, Currency currency,
						String description, String designation,
						Tax.TaxRateType type, Date validFrom, Date validTo,
						String valueKey, String key) {

					BigDecimal amount = new BigDecimal(
							configuration.get(valueKey));

					taxBuilder.clear();

					taxBuilder.setCode(taxCode).setContextUID(context.getUID())
							.setCurrency(currency).setDescription(description)
							.setDesignation(designation)
							.setTaxRate(type, amount).setValidFrom(validFrom)
							.setValidTo(validTo).setValue(amount);
					final ESTaxEntity tax = (ESTaxEntity) taxBuilder.build();

					tax.setUID(configuration.getUID(key));

					daoESTax.create(tax);

					return tax;
				}

				private ESRegionContextEntity buildContextEntity(
						DAOESRegionContext daoESRegionContext,
						ESRegionContext.Builder contextBuilder, String name,
						String description, UID parentUID, String regionCode,
						String key) {

					contextBuilder.clear();

					contextBuilder.setName(name).setDescription(description)
							.setRegionCode(regionCode)
							.setParentContextUID(parentUID);

					final ESRegionContextEntity context = (ESRegionContextEntity) contextBuilder
							.build();

					context.setUID(configuration.getUID(key));

					daoESRegionContext.create(context);

					return context;
				}

				private ESCustomerEntity buildCustomerEntity(
						DAOESCustomer daoESCustomer,
						ESCustomer.Builder customerBuilder, String name,
						String taxRegistrationID,
						ESAddress.Builder addressBuilder,
						ESContact.Builder contactBuilder,
						boolean hasSelfAgreement, String key) {

					customerBuilder.clear();

					customerBuilder
							.setName(name)
							.addContact(contactBuilder)
							.setMainContactUID(contactBuilder.build().getUID())
							.setHasSelfBillingAgreement(hasSelfAgreement)
							.setTaxRegistrationNumber(taxRegistrationID,
									CODE_ES)
							.setBillingAddress(addressBuilder)
							.setShippingAddress(addressBuilder)
							.addAddress(addressBuilder, true);

					ESCustomerEntity customer = (ESCustomerEntity) customerBuilder
							.build();

					customer.setUID(configuration.getUID(key));
					customer.setTaxRegistrationNumber(null);

					daoESCustomer.create(customer);

					return customer;
				}

			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
