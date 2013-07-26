/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTAddress;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTContact;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTAddressEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTContactEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTAddress.Builder;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTProduct;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.entities.PTTax;

public class PlatypusBootstrap {

	public static final String CODE_PT = "PT";
	public static final String CODE_PT_AVEIRO = "PT-01";
	protected static final String CODE_PT_BEJA = "PT-02";
	protected static final String CODE_PT_BRAGA = "PT-03";
	protected static final String CODE_PT_BRAGANCA = "PT-04";
	protected static final String CODE_PT_CASTELO_BRANCO = "PT-05";
	protected static final String CODE_PT_COIMBRA = "PT-06";
	protected static final String CODE_PT_EVORA = "PT-07";
	protected static final String CODE_PT_FARO = "PT-08";
	protected static final String CODE_PT_GUARDA = "PT-09";
	protected static final String CODE_PT_LEIRIA = "PT-10";
	protected static final String CODE_PT_LISBOA = "PT-11";
	protected static final String CODE_PT_PORTALEGRE = "PT-12";
	protected static final String CODE_PT_PORTO = "PT-13";
	protected static final String CODE_PT_SANTAREM = "PT-14";
	protected static final String CODE_PT_SETUBAL = "PT-15";
	protected static final String CODE_PT_VIANA = "PT-16";
	protected static final String CODE_PT_VILA_REAL = "PT-17";
	protected static final String CODE_PT_VISEU = "PT-18";
	protected static final String CODE_PT_AZORES = "PT-20";
	protected static final String CODE_PT_MADEIRA = "PT-30";

	public static void main(String[] args) {
		PlatypusBootstrap.execute();
	}

	private static void execute() {
		// Load dependency injector
		Injector injector = Guice.createInjector(
				new PlatypusDependencyModule(),
				new PlatypusPersistenceDependencyModule());
		injector.getInstance(PlatypusDependencyModule.Initializer.class);
		injector.getInstance(PlatypusPersistenceDependencyModule.Initializer.class);
		PlatypusBootstrap.execute(injector);

	}

	private static void execute(final Injector dependencyInjector) {
		DAO<?> dao = dependencyInjector.getInstance(DAOPTInvoice.class);
		final Config configuration = new Config();

		try {
			new TransactionWrapper<Void>(dao) {

				@SuppressWarnings("unused")
				@Override
				public Void runTransaction() throws Exception {
					// Dao creation
					DAOPTCustomer daoPTCustomer = dependencyInjector
							.getInstance(DAOPTCustomer.class);
					DAOPTRegionContext daoPTRegionContext = dependencyInjector
							.getInstance(DAOPTRegionContext.class);
					DAOPTTax daoPTTax = dependencyInjector
							.getInstance(DAOPTTax.class);
					DAOPTContact daoPTContact = dependencyInjector
							.getInstance(DAOPTContact.class);
					DAOPTAddress daoPTAddress = dependencyInjector
							.getInstance(DAOPTAddress.class);
					DAOPTProduct daoPTProduct = dependencyInjector
							.getInstance(DAOPTProduct.class);
					DAOPTInvoiceEntry daoPTInvoiceEntry = dependencyInjector
							.getInstance(DAOPTInvoiceEntry.class);
					DAOPTInvoice daoPTInvoice = dependencyInjector
							.getInstance(DAOPTInvoice.class);
					DAOPTCreditNote daoPTCreditNote = dependencyInjector
							.getInstance(DAOPTCreditNote.class);
					DAOPTCreditNoteEntry daoPTCreditNoteEntry = dependencyInjector
							.getInstance(DAOPTCreditNoteEntry.class);

					// Builders
					PTCustomer.Builder customerBuilder = dependencyInjector
							.getInstance(PTCustomer.Builder.class);
					PTRegionContext.Builder contextBuilder = dependencyInjector
							.getInstance(PTRegionContext.Builder.class);
					PTTax.Builder taxBuilder = dependencyInjector
							.getInstance(PTTax.Builder.class);
					PTAddress.Builder addressBuilder = dependencyInjector
							.getInstance(PTAddress.Builder.class);
					PTContact.Builder contactBuilder = dependencyInjector
							.getInstance(PTContact.Builder.class);
					PTProduct.Builder productBuilder = dependencyInjector
							.getInstance(PTProduct.Builder.class);
					PTInvoiceEntry.Builder invoiceEntryBuilder = dependencyInjector
							.getInstance(PTInvoiceEntry.Builder.class);
					PTInvoice.Builder invoiceBuilder = dependencyInjector
							.getInstance(PTInvoice.Builder.class);
					PTCreditNote.Builder creditNoteBuilder = dependencyInjector
							.getInstance(PTCreditNote.Builder.class);
					PTCreditNoteEntry.Builder creditNoteEntryBuilder = dependencyInjector
							.getInstance(PTCreditNoteEntry.Builder.class);

					// Generic Address
					final PTAddressEntity GENERIC_ADDRESS = this
							.buildAddressEntity(daoPTAddress, addressBuilder,
									"1", "Street", "Building", "City",
									"region", "PT", "details", "1000-001",
									Config.Key.Address.Generic.UUID);

					// Generic contact
					final PTContactEntity GENERIC_CONTACT = this
							.buildContactEntity(daoPTContact, contactBuilder,
									"John Doe", "111222333", "123123123",
									"doe@example.com", "321321321",
									"www.doe.io",
									Config.Key.Contact.Generic.UUID);

					// Generic Customer
					final PTCustomerEntity GENERIC_CUSTOMER = this
							.buildCustomerEntity(daoPTCustomer,
									customerBuilder, "Final Consumer",
									"997971477", addressBuilder,
									contactBuilder, false,
									Config.Key.Customer.Generic.UUID);

					// Portugal Contexts
					final PTRegionContextEntity CONTEXT_PORTUGAL = this
							.buildContextEntity(daoPTRegionContext,
									contextBuilder, "Portugal",
									"The Context for the country Portugal",
									null, PlatypusBootstrap.CODE_PT,
									Config.Key.Context.Portugal.UUID);

					final PTRegionContextEntity CONTEXT_CONTINENTAL_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Portugal Continental",
									"The Context for the country Portugal",
									CONTEXT_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT,
									Config.Key.Context.Portugal.Continental.UUID);

					final PTRegionContextEntity CONTEXT_AVEIRO_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Aveiro",
									"The Context for the Portuguese Aveiro region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_AVEIRO,
									Config.Key.Context.Portugal.Continental.Aveiro.UUID);

					final PTRegionContextEntity CONTEXT_BEJA_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Beja",
									"The Context for the Portuguese Beja region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_BEJA,
									Config.Key.Context.Portugal.Continental.Beja.UUID);

					final PTRegionContextEntity CONTEXT_BRAGA_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Braga",
									"The Context for the Portuguese Braga region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_BRAGA,
									Config.Key.Context.Portugal.Continental.Braga.UUID);

					final PTRegionContextEntity CONTEXT_BRAGANÇA_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Bragança",
									"The Context for the Portuguese Bragança region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_BRAGANCA,
									Config.Key.Context.Portugal.Continental.Braganca.UUID);

					final PTRegionContextEntity CONTEXT_CASTELO_BRANCO_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Castelo Branco",
									"The Context for the Portuguese Castelo Branco region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_CASTELO_BRANCO,
									Config.Key.Context.Portugal.Continental.CasteloBranco.UUID);

					final PTRegionContextEntity CONTEXT_COIMBRA_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Coimbra",
									"The Context for the Portuguese Coimbra region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_COIMBRA,
									Config.Key.Context.Portugal.Continental.Coimbra.UUID);

					final PTRegionContextEntity CONTEXT_EVORA_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Evora",
									"The Context for the Portuguese Evora region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_EVORA,
									Config.Key.Context.Portugal.Continental.Evora.UUID);

					final PTRegionContextEntity CONTEXT_FARO_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Faro",
									"The Context for the Portuguese Faro region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_FARO,
									Config.Key.Context.Portugal.Continental.Faro.UUID);

					final PTRegionContextEntity CONTEXT_GUARDA_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Guarda",
									"The Context for the Portuguese Guarda region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_GUARDA,
									Config.Key.Context.Portugal.Continental.Guarda.UUID);

					final PTRegionContextEntity CONTEXT_LEIRIA_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Leiria",
									"The Context for the Portuguese Leiria region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_LEIRIA,
									Config.Key.Context.Portugal.Continental.Leiria.UUID);

					final PTRegionContextEntity CONTEXT_LISBOA_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Lisboa",
									"The Context for the Portuguese Lisboa region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_LISBOA,
									Config.Key.Context.Portugal.Continental.Lisboa.UUID);

					final PTRegionContextEntity CONTEXT_PORTALEGRE_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Portalegre",
									"The Context for the Portuguese Portalegre region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_PORTALEGRE,
									Config.Key.Context.Portugal.Continental.Portalegre.UUID);

					final PTRegionContextEntity CONTEXT_PORTO_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Porto",
									"The Context for the Portuguese Porto region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_PORTO,
									Config.Key.Context.Portugal.Continental.Porto.UUID);

					final PTRegionContextEntity CONTEXT_SANTAREM_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Santarem",
									"The Context for the Portuguese Santarem region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_SANTAREM,
									Config.Key.Context.Portugal.Continental.Santarem.UUID);

					final PTRegionContextEntity CONTEXT_SETUBAL_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Setubal",
									"The Context for the Portuguese Setubal region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_SETUBAL,
									Config.Key.Context.Portugal.Continental.Setubal.UUID);

					final PTRegionContextEntity CONTEXT_VIANA_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Viana do Castelo",
									"The Context for the Portuguese Viana do Castelo region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_VIANA,
									Config.Key.Context.Portugal.Continental.Viana.UUID);

					final PTRegionContextEntity CONTEXT_VILA_REAL_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Vila Real",
									"The Context for the Portuguese Vila Real region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_VILA_REAL,
									Config.Key.Context.Portugal.Continental.VilaReal.UUID);

					final PTRegionContextEntity CONTEXT_VISEU_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Viseu",
									"The Context for the Portuguese Viseu region",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_VISEU,
									Config.Key.Context.Portugal.Continental.Viseu.UUID);

					final PTRegionContextEntity CONTEXT_AZORES_PORTUGAL = this
							.buildContextEntity(daoPTRegionContext,
									contextBuilder, "Azores",
									"The Context for the Portuguese Azores",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_AZORES,
									Config.Key.Context.Portugal.Azores.UUID);

					final PTRegionContextEntity CONTEXT_MADEIRA_PORTUGAL = this
							.buildContextEntity(
									daoPTRegionContext,
									contextBuilder,
									"Madeira Autonomous Region",
									"The Context for the Portuguese Madeira island",
									CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
									PlatypusBootstrap.CODE_PT_MADEIRA,
									Config.Key.Context.Portugal.Madeira.UUID);

					// Taxes
					final PTTaxEntity VAT_NORMAL_CONTINENTAL_PORTUGAL = this
							.buildTaxEntity(
									daoPTTax,
									taxBuilder,
									"IVA",
									CONTEXT_CONTINENTAL_PORTUGAL,
									Currency.getInstance("EUR"),
									"IVA Normal Continente",
									"IVA",
									Tax.TaxRateType.PERCENTAGE,
									new Date(),
									new Date(),
									Config.Key.Context.Portugal.Continental.VAT.NORMAL_PERCENT,
									Config.Key.Context.Portugal.Continental.VAT.NORMAL_UUID);

					final PTTaxEntity VAT_INTERMEDIATE_CONTINENTAL_PORTUGAL = this
							.buildTaxEntity(
									daoPTTax,
									taxBuilder,
									"IVA",
									CONTEXT_CONTINENTAL_PORTUGAL,
									Currency.getInstance("EUR"),
									"IVA Intermedio Continente",
									"IVA",
									Tax.TaxRateType.PERCENTAGE,
									new Date(),
									new Date(),
									Config.Key.Context.Portugal.Continental.VAT.INTERMEDIATE_PERCENT,
									Config.Key.Context.Portugal.Continental.VAT.INTERMEDIATE_UUID);

					final PTTaxEntity VAT_REDUCED_CONTINENTAL_PORTUGAL = this
							.buildTaxEntity(
									daoPTTax,
									taxBuilder,
									"IVA",
									CONTEXT_CONTINENTAL_PORTUGAL,
									Currency.getInstance("EUR"),
									"IVA Reduzido Continente",
									"IVA",
									Tax.TaxRateType.PERCENTAGE,
									new Date(),
									new Date(),
									Config.Key.Context.Portugal.Continental.VAT.REDUCED_PERCENT,
									Config.Key.Context.Portugal.Continental.VAT.REDUCED_UUID);

					// Madeira
					final PTTaxEntity VAT_NORMAL_MADEIRA_PORTUGAL = this
							.buildTaxEntity(
									daoPTTax,
									taxBuilder,
									"IVA",
									CONTEXT_MADEIRA_PORTUGAL,
									Currency.getInstance("EUR"),
									"IVA Normal Madeira",
									"IVA",
									Tax.TaxRateType.PERCENTAGE,
									new Date(),
									new Date(),
									Config.Key.Context.Portugal.Madeira.VAT.NORMAL_PERCENT,
									Config.Key.Context.Portugal.Madeira.VAT.NORMAL_UUID);

					final PTTaxEntity VAT_INTERMEDIATE_MADEIRA_PORTUGAL = this
							.buildTaxEntity(
									daoPTTax,
									taxBuilder,
									"IVA",
									CONTEXT_MADEIRA_PORTUGAL,
									Currency.getInstance("EUR"),
									"IVA Intermedio Madeira",
									"IVA",
									Tax.TaxRateType.PERCENTAGE,
									new Date(),
									new Date(),
									Config.Key.Context.Portugal.Madeira.VAT.INTERMEDIATE_PERCENT,
									Config.Key.Context.Portugal.Madeira.VAT.INTERMEDIATE_UUID);

					final PTTaxEntity VAT_REDUCED_MADEIRA_PORTUGAL = this
							.buildTaxEntity(
									daoPTTax,
									taxBuilder,
									"IVA",
									CONTEXT_MADEIRA_PORTUGAL,
									Currency.getInstance("EUR"),
									"IVA Reduzido Madeira",
									"IVA",
									Tax.TaxRateType.PERCENTAGE,
									new Date(),
									new Date(),
									Config.Key.Context.Portugal.Madeira.VAT.REDUCED_PERCENT,
									Config.Key.Context.Portugal.Madeira.VAT.REDUCED_UUID);

					final PTTaxEntity VAT_NORMAL_AZORES_PORTUGAL = this
							.buildTaxEntity(
									daoPTTax,
									taxBuilder,
									"IVA",
									CONTEXT_AZORES_PORTUGAL,
									Currency.getInstance("EUR"),
									"IVA Normal Azores",
									"IVA",
									Tax.TaxRateType.PERCENTAGE,
									new Date(),
									new Date(),
									Config.Key.Context.Portugal.Azores.VAT.NORMAL_PERCENT,
									Config.Key.Context.Portugal.Azores.VAT.NORMAL_UUID);

					// Azores
					final PTTaxEntity VAT_INTERMEDIATE_AZORES_PORTUGAL = this
							.buildTaxEntity(
									daoPTTax,
									taxBuilder,
									"IVA",
									CONTEXT_AZORES_PORTUGAL,
									Currency.getInstance("EUR"),
									"IVA Intermedio Azores",
									"IVA",
									Tax.TaxRateType.PERCENTAGE,
									new Date(),
									new Date(),
									Config.Key.Context.Portugal.Azores.VAT.INTERMEDIATE_PERCENT,
									Config.Key.Context.Portugal.Azores.VAT.INTERMEDIATE_UUID);

					final PTTaxEntity VAT_REDUCED_AZORES_PORTUGAL = this
							.buildTaxEntity(
									daoPTTax,
									taxBuilder,
									"IVA",
									CONTEXT_AZORES_PORTUGAL,
									Currency.getInstance("EUR"),
									"IVA Reduzido Azores",
									"IVA",
									Tax.TaxRateType.PERCENTAGE,
									new Date(),
									new Date(),
									Config.Key.Context.Portugal.Azores.VAT.REDUCED_PERCENT,
									Config.Key.Context.Portugal.Azores.VAT.REDUCED_UUID);

					final PTTaxEntity TAX_EXEMPT_PORTUGAL = this
							.buildTaxEntity(
									daoPTTax,
									taxBuilder,
									"IVA",
									CONTEXT_PORTUGAL,
									Currency.getInstance("EUR"),
									"Isento de IVA",
									"IVA",
									Tax.TaxRateType.FLAT,
									new Date(),
									new Date(),
									Config.Key.Context.Portugal.TAX_EXEMPT_VALUE,
									Config.Key.Context.Portugal.TAX_EXEMPT_UUID);

					// Dumbest side
					final PTProductEntity PRODUCT_PORTUGAL = this.buildProduct(
							daoPTProduct, productBuilder,
							VAT_NORMAL_CONTINENTAL_PORTUGAL.getUID());

					final PTInvoiceEntity INVOICE_ENTITY = this.buildInvoice(
							daoPTInvoice, invoiceBuilder, invoiceEntryBuilder,
							PRODUCT_PORTUGAL.getUID(), daoPTInvoiceEntry,
							CONTEXT_PORTUGAL.getUID());

					final PTCreditNoteEntity CREDIT_NOTE_ENTITY = this
							.buildCreditNote(daoPTCreditNote,
									creditNoteBuilder, creditNoteEntryBuilder,
									PRODUCT_PORTUGAL.getUID(),
									daoPTCreditNoteEntry,
									CONTEXT_PORTUGAL.getUID(), INVOICE_ENTITY);

					return null;
				}

				private PTAddressEntity buildAddressEntity(
						DAOPTAddress daoPTAddress, Builder addressBuilder,
						String number, String street, String building,
						String city, String region, String isoCode,
						String details, String postalCode, String key) {

					addressBuilder.setBuilding(building).setCity(city)
							.setDetails(details).setISOCountry(isoCode)
							.setNumber(number).setRegion(region)
							.setStreetName(street).setPostalCode(postalCode);

					PTAddressEntity address = (PTAddressEntity) addressBuilder
							.build();

					address.setUID(configuration.getUID(key));

					daoPTAddress.create(address);

					return address;
				}

				private PTContactEntity buildContactEntity(
						DAOPTContact daoPTContact,
						PTContact.Builder contactBuilder, String name,
						String telephone, String mobile, String email,
						String fax, String website, String key) {

					contactBuilder.clear();

					contactBuilder.setName(name).setEmail(email)
							.setMobile(mobile).setFax(fax)
							.setTelephone(telephone).setWebsite(website);

					final PTContactEntity contact = (PTContactEntity) contactBuilder
							.build();

					contact.setUID(configuration.getUID(key));

					daoPTContact.create(contact);

					return contact;
				}

				private PTTaxEntity buildTaxEntity(DAOPTTax daoPTTax,
						PTTax.Builder taxBuilder, String taxCode,
						PTRegionContextEntity context, Currency currency,
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

					final PTTaxEntity tax = (PTTaxEntity) taxBuilder.build();

					tax.setUID(configuration.getUID(key));

					daoPTTax.create(tax);

					return tax;
				}

				private PTRegionContextEntity buildContextEntity(
						DAOPTRegionContext daoPTRegionContext,
						PTRegionContext.Builder contextBuilder, String name,
						String description, UID parentUID, String regionCode,
						String key) {

					contextBuilder.clear();

					contextBuilder.setName(name).setDescription(description)
							.setRegionCode(regionCode)
							.setParentContextUID(parentUID);

					final PTRegionContextEntity context = (PTRegionContextEntity) contextBuilder
							.build();

					context.setUID(configuration.getUID(key));

					daoPTRegionContext.create(context);

					return context;
				}

				private PTCustomerEntity buildCustomerEntity(
						DAOPTCustomer daoPTCustomer,
						PTCustomer.Builder customerBuilder, String name,
						String taxRegistrationID,
						PTAddress.Builder addressBuilder,
						PTContact.Builder contactBuilder,
						boolean hasSelfAgreement, String key) {

					customerBuilder.clear();

					customerBuilder.setName(name).addContact(contactBuilder)
							.setMainContactUID(contactBuilder.build().getUID())
							.setHasSelfBillingAgreement(hasSelfAgreement)
							.setTaxRegistrationNumber(taxRegistrationID)
							.setBillingAddress(addressBuilder)
							.setShippingAddress(addressBuilder)
							.addAddress(addressBuilder, true);

					PTCustomerEntity customer = (PTCustomerEntity) customerBuilder
							.build();

					customer.setUID(configuration.getUID(key));

					daoPTCustomer.create(customer);

					return customer;
				}

				// Dummy side

				private PTProductEntity buildProduct(DAOPTProduct daoPTProduct,
						PTProduct.Builder productBuilder, UID taxUID) {
					productBuilder.clear();
					productBuilder.addTaxUID(taxUID).setNumberCode("123")
							.setUnitOfMeasure("Kg").setProductCode("123")
							.setDescription("description")
							.setType(ProductType.GOODS);

					PTProductEntity product = (PTProductEntity) productBuilder
							.build();

					product.setUID(new UID("POTATOES"));

					daoPTProduct.create(product);

					return product;
				}

				private PTInvoiceEntity buildInvoice(DAOPTInvoice daoPTInvoice,
						PTInvoice.Builder invoiceBuilder,
						PTInvoiceEntry.Builder invoiceEntryBuilder,
						UID productUID, DAOPTInvoiceEntry daoPTInvoiceEntry,
						UID contextUID) {

					buidInvoiceEntry(invoiceEntryBuilder, productUID,
							contextUID);

					invoiceBuilder.clear();

					invoiceBuilder.setBilled(false).setCancelled(false)
							.setSelfBilled(false).setHash("HASH")
							.setDate(new Date()).setSourceId("EU")
							.addEntry(invoiceEntryBuilder);

					PTInvoiceEntity invoice = (PTInvoiceEntity) invoiceBuilder
							.build();

					invoice.setUID(new UID("INVOICE"));
					invoice.setSeries("A");
					invoice.setSeriesNumber(1);

					PTInvoiceEntryEntity invoiceEntry = (PTInvoiceEntryEntity) invoice
							.getEntries().get(0);
					invoiceEntry.setUID(new UID("INVOICE_ENTRY"));
					invoiceEntry.getDocumentReferences().add(invoice);
					daoPTInvoice.create(invoice);

					return invoice;
				}

				private void buidInvoiceEntry(
						PTInvoiceEntry.Builder invoiceEntryBuilder,
						UID productUID, UID contextUID) {
					invoiceEntryBuilder.clear();

					invoiceEntryBuilder
							.setUnitAmount(AmountType.WITH_TAX,
									new BigDecimal(20),
									Currency.getInstance("EUR"))
							.setTaxPointDate(new Date())
							.setCreditOrDebit(CreditOrDebit.DEBIT)
							.setDescription("Description")
							.setQuantity(new BigDecimal(1))
							.setUnitOfMeasure("Kg").setProductUID(productUID)
							.setContextUID(contextUID);
				}

				private PTCreditNoteEntity buildCreditNote(
						DAOPTCreditNote daoPTCreditNote,
						PTCreditNote.Builder creditNoteBuilder,
						PTCreditNoteEntry.Builder creditNoteEntryBuilder,
						UID productUID,
						DAOPTCreditNoteEntry daoPTCreditNoteEntry,
						UID contextUID, PTInvoice reference) {

					buidCreditNoteEntry(creditNoteEntryBuilder, productUID,
							contextUID, reference);

					creditNoteBuilder.clear();

					creditNoteBuilder.setBilled(false).setCancelled(false)
							.setSelfBilled(false).setHash("HASH")
							.setDate(new Date()).setSourceId("EU")
							.addEntry(creditNoteEntryBuilder);

					PTCreditNoteEntity creditNote = (PTCreditNoteEntity) creditNoteBuilder
							.build();

					creditNote.setUID(new UID("CREDIT_NOTE"));

					PTCreditNoteEntryEntity creditNoteEntry = (PTCreditNoteEntryEntity) creditNote
							.getEntries().get(0);
					creditNoteEntry.setUID(new UID("CREDIT_NOTE_ENTRY"));
					creditNoteEntry.getDocumentReferences().add(creditNote);
					daoPTCreditNote.create(creditNote);

					return creditNote;
				}

				private void buidCreditNoteEntry(
						PTCreditNoteEntry.Builder creditNoteEntryBuilder,
						UID productUID, UID contextUID, PTInvoice reference) {
					creditNoteEntryBuilder.clear();

					creditNoteEntryBuilder
							.setUnitAmount(AmountType.WITH_TAX,
									new BigDecimal(20),
									Currency.getInstance("EUR"))
							.setTaxPointDate(new Date())
							.setCreditOrDebit(CreditOrDebit.DEBIT)
							.setDescription("Description")
							.setQuantity(new BigDecimal(1))
							.setUnitOfMeasure("Kg").setProductUID(productUID)
							.setContextUID(contextUID)
							.setReason("Rotten potatoes")
							.setReference(reference);
				}

			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
