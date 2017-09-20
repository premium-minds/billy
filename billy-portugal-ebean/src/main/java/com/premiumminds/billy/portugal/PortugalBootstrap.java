/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTAddress;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTContact;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTAddressEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTContactEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTAddress.Builder;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.entities.PTTax;
import com.premiumminds.billy.portugal.services.entities.PTTax.PTVATCode;

public class PortugalBootstrap {

    private static final Logger log = LoggerFactory.getLogger(PortugalBootstrap.class);

    protected static final String CODE_PT = "PT";
    protected static final String CODE_PT_AVEIRO = "PT-01";
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
        if (args.length > 0 && !args[0].isEmpty()) {
            PortugalBootstrap.execute(args[0]);
        } else {
            PortugalBootstrap.execute(BillyPortugal.DEFAULT_PERSISTENCE_UNIT); // backward compatibility
        }
    }

    private static void execute(String persistenceUnitId) {
        // Load dependency injector
        Injector injector = Guice.createInjector(new PortugalDependencyModule(),
                new PortugalPersistenceDependencyModule(persistenceUnitId));
        injector.getInstance(PortugalDependencyModule.Initializer.class);
        injector.getInstance(PortugalPersistenceDependencyModule.Initializer.class);
        PortugalBootstrap.execute(injector);

    }

    public static void execute(final Injector dependencyInjector) {
        DAO<?> dao = dependencyInjector.getInstance(DAOPTInvoice.class);
        final Config configuration = new Config();

        try {
            new TransactionWrapper<Void>(dao) {

                @SuppressWarnings("unused")
                @Override
                public Void runTransaction() throws Exception {
                    // Dao creation
                    DAOPTCustomer daoPTCustomer = dependencyInjector.getInstance(DAOPTCustomer.class);
                    DAOPTRegionContext daoPTRegionContext = dependencyInjector.getInstance(DAOPTRegionContext.class);
                    DAOPTTax daoPTTax = dependencyInjector.getInstance(DAOPTTax.class);
                    DAOPTContact daoPTContact = dependencyInjector.getInstance(DAOPTContact.class);
                    DAOPTAddress daoPTAddress = dependencyInjector.getInstance(DAOPTAddress.class);

                    // Builders
                    PTCustomer.Builder customerBuilder = dependencyInjector.getInstance(PTCustomer.Builder.class);
                    PTRegionContext.Builder contextBuilder =
                            dependencyInjector.getInstance(PTRegionContext.Builder.class);
                    PTTax.Builder taxBuilder = dependencyInjector.getInstance(PTTax.Builder.class);
                    PTAddress.Builder addressBuilder = dependencyInjector.getInstance(PTAddress.Builder.class);
                    PTContact.Builder contactBuilder = dependencyInjector.getInstance(PTContact.Builder.class);

                    // Generic Address
                    final PTAddressEntity GENERIC_ADDRESS = this.buildAddressEntity(daoPTAddress, addressBuilder, null,
                            null, null, "Desconhecido", null, "Desconhecido", "Desconhecido", "Desconhecido",
                            Config.Key.Address.Generic.UUID);

                    // Generic contact
                    final PTContactEntity GENERIC_CONTACT = this.buildContactEntity(daoPTContact, contactBuilder, null,
                            null, null, null, null, null, Config.Key.Contact.Generic.UUID);

                    // Generic Customer
                    final PTCustomerEntity GENERIC_CUSTOMER =
                            this.buildCustomerEntity(daoPTCustomer, customerBuilder, "Consumidor final", "999999990",
                                    addressBuilder, contactBuilder, false, Config.Key.Customer.Generic.UUID);

                    // Portugal Contexts
                    final PTRegionContextEntity CONTEXT_PORTUGAL = this.buildContextEntity(daoPTRegionContext,
                            contextBuilder, "Portugal", "The Context for the country Portugal", null,
                            PortugalBootstrap.CODE_PT, Config.Key.Context.Portugal.UUID);

                    final PTRegionContextEntity CONTEXT_CONTINENTAL_PORTUGAL =
                            this.buildContextEntity(daoPTRegionContext, contextBuilder, "Portugal Continental",
                                    "The Context for mainland Portugal", CONTEXT_PORTUGAL.getUID(),
                                    PortugalBootstrap.CODE_PT, Config.Key.Context.Portugal.Continental.UUID);

                    final PTRegionContextEntity CONTEXT_AVEIRO_PORTUGAL = this.buildContextEntity(daoPTRegionContext,
                            contextBuilder, "Aveiro", "The Context for the Portuguese Aveiro region",
                            CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_AVEIRO,
                            Config.Key.Context.Portugal.Continental.Aveiro.UUID);

                    final PTRegionContextEntity CONTEXT_BEJA_PORTUGAL =
                            this.buildContextEntity(daoPTRegionContext, contextBuilder, "Beja",
                                    "The Context for the Portuguese Beja region", CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
                                    PortugalBootstrap.CODE_PT_BEJA, Config.Key.Context.Portugal.Continental.Beja.UUID);

                    final PTRegionContextEntity CONTEXT_BRAGA_PORTUGAL = this.buildContextEntity(daoPTRegionContext,
                            contextBuilder, "Braga", "The Context for the Portuguese Braga region",
                            CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_BRAGA,
                            Config.Key.Context.Portugal.Continental.Braga.UUID);

                    final PTRegionContextEntity CONTEXT_BRAGANÇA_PORTUGAL = this.buildContextEntity(daoPTRegionContext,
                            contextBuilder, "Bragança", "The Context for the Portuguese Bragança region",
                            CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_BRAGANCA,
                            Config.Key.Context.Portugal.Continental.Braganca.UUID);

                    final PTRegionContextEntity CONTEXT_CASTELO_BRANCO_PORTUGAL =
                            this.buildContextEntity(daoPTRegionContext, contextBuilder, "Castelo Branco",
                                    "The Context for the Portuguese Castelo Branco region",
                                    CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_CASTELO_BRANCO,
                                    Config.Key.Context.Portugal.Continental.CasteloBranco.UUID);

                    final PTRegionContextEntity CONTEXT_COIMBRA_PORTUGAL = this.buildContextEntity(daoPTRegionContext,
                            contextBuilder, "Coimbra", "The Context for the Portuguese Coimbra region",
                            CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_COIMBRA,
                            Config.Key.Context.Portugal.Continental.Coimbra.UUID);

                    final PTRegionContextEntity CONTEXT_EVORA_PORTUGAL = this.buildContextEntity(daoPTRegionContext,
                            contextBuilder, "Evora", "The Context for the Portuguese Evora region",
                            CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_EVORA,
                            Config.Key.Context.Portugal.Continental.Evora.UUID);

                    final PTRegionContextEntity CONTEXT_FARO_PORTUGAL =
                            this.buildContextEntity(daoPTRegionContext, contextBuilder, "Faro",
                                    "The Context for the Portuguese Faro region", CONTEXT_CONTINENTAL_PORTUGAL.getUID(),
                                    PortugalBootstrap.CODE_PT_FARO, Config.Key.Context.Portugal.Continental.Faro.UUID);

                    final PTRegionContextEntity CONTEXT_GUARDA_PORTUGAL = this.buildContextEntity(daoPTRegionContext,
                            contextBuilder, "Guarda", "The Context for the Portuguese Guarda region",
                            CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_GUARDA,
                            Config.Key.Context.Portugal.Continental.Guarda.UUID);

                    final PTRegionContextEntity CONTEXT_LEIRIA_PORTUGAL = this.buildContextEntity(daoPTRegionContext,
                            contextBuilder, "Leiria", "The Context for the Portuguese Leiria region",
                            CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_LEIRIA,
                            Config.Key.Context.Portugal.Continental.Leiria.UUID);

                    final PTRegionContextEntity CONTEXT_LISBOA_PORTUGAL = this.buildContextEntity(daoPTRegionContext,
                            contextBuilder, "Lisboa", "The Context for the Portuguese Lisboa region",
                            CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_LISBOA,
                            Config.Key.Context.Portugal.Continental.Lisboa.UUID);

                    final PTRegionContextEntity CONTEXT_PORTALEGRE_PORTUGAL =
                            this.buildContextEntity(daoPTRegionContext, contextBuilder, "Portalegre",
                                    "The Context for the Portuguese Portalegre region",
                                    CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_PORTALEGRE,
                                    Config.Key.Context.Portugal.Continental.Portalegre.UUID);

                    final PTRegionContextEntity CONTEXT_PORTO_PORTUGAL = this.buildContextEntity(daoPTRegionContext,
                            contextBuilder, "Porto", "The Context for the Portuguese Porto region",
                            CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_PORTO,
                            Config.Key.Context.Portugal.Continental.Porto.UUID);

                    final PTRegionContextEntity CONTEXT_SANTAREM_PORTUGAL = this.buildContextEntity(daoPTRegionContext,
                            contextBuilder, "Santarem", "The Context for the Portuguese Santarem region",
                            CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_SANTAREM,
                            Config.Key.Context.Portugal.Continental.Santarem.UUID);

                    final PTRegionContextEntity CONTEXT_SETUBAL_PORTUGAL = this.buildContextEntity(daoPTRegionContext,
                            contextBuilder, "Setubal", "The Context for the Portuguese Setubal region",
                            CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_SETUBAL,
                            Config.Key.Context.Portugal.Continental.Setubal.UUID);

                    final PTRegionContextEntity CONTEXT_VIANA_PORTUGAL =
                            this.buildContextEntity(daoPTRegionContext, contextBuilder, "Viana do Castelo",
                                    "The Context for the Portuguese Viana do Castelo region",
                                    CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_VIANA,
                                    Config.Key.Context.Portugal.Continental.Viana.UUID);

                    final PTRegionContextEntity CONTEXT_VILA_REAL_PORTUGAL = this.buildContextEntity(daoPTRegionContext,
                            contextBuilder, "Vila Real", "The Context for the Portuguese Vila Real region",
                            CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_VILA_REAL,
                            Config.Key.Context.Portugal.Continental.VilaReal.UUID);

                    final PTRegionContextEntity CONTEXT_VISEU_PORTUGAL = this.buildContextEntity(daoPTRegionContext,
                            contextBuilder, "Viseu", "The Context for the Portuguese Viseu region",
                            CONTEXT_CONTINENTAL_PORTUGAL.getUID(), PortugalBootstrap.CODE_PT_VISEU,
                            Config.Key.Context.Portugal.Continental.Viseu.UUID);

                    final PTRegionContextEntity CONTEXT_AZORES_PORTUGAL =
                            this.buildContextEntity(daoPTRegionContext, contextBuilder, "Azores",
                                    "The Context for the Portuguese Azores", CONTEXT_PORTUGAL.getUID(),
                                    PortugalBootstrap.CODE_PT_AZORES, Config.Key.Context.Portugal.Azores.UUID);

                    final PTRegionContextEntity CONTEXT_MADEIRA_PORTUGAL =
                            this.buildContextEntity(daoPTRegionContext, contextBuilder, "Madeira Autonomous Region",
                                    "The Context for the Portuguese Madeira island", CONTEXT_PORTUGAL.getUID(),
                                    PortugalBootstrap.CODE_PT_MADEIRA, Config.Key.Context.Portugal.Madeira.UUID);

                    // Taxes
                    Date from = new DateTime(2013, 1, 1, 0, 0).toDateMidnight().toDateTime().toDate();
                    Date to = new DateTime(2014, 1, 1, 0, 0).toDateMidnight().toDateTime().toDate();
                    final PTTaxEntity VAT_NORMAL_CONTINENTAL_PORTUGAL = this.buildTaxEntity(daoPTTax, taxBuilder,
                            PTVATCode.NORMAL, CONTEXT_CONTINENTAL_PORTUGAL, Currency.getInstance("EUR"),
                            "IVA Normal Continente", "IVA", Tax.TaxRateType.PERCENTAGE, from, to,
                            Config.Key.Context.Portugal.Continental.VAT.NORMAL_PERCENT,
                            Config.Key.Context.Portugal.Continental.VAT.NORMAL_UUID);

                    final PTTaxEntity VAT_INTERMEDIATE_CONTINENTAL_PORTUGAL = this.buildTaxEntity(daoPTTax, taxBuilder,
                            PTVATCode.INTERMEDIATE, CONTEXT_CONTINENTAL_PORTUGAL, Currency.getInstance("EUR"),
                            "IVA Intermedio Continente", "IVA", Tax.TaxRateType.PERCENTAGE, from, to,
                            Config.Key.Context.Portugal.Continental.VAT.INTERMEDIATE_PERCENT,
                            Config.Key.Context.Portugal.Continental.VAT.INTERMEDIATE_UUID);

                    final PTTaxEntity VAT_REDUCED_CONTINENTAL_PORTUGAL = this.buildTaxEntity(daoPTTax, taxBuilder,
                            PTVATCode.REDUCED, CONTEXT_CONTINENTAL_PORTUGAL, Currency.getInstance("EUR"),
                            "IVA Reduzido Continente", "IVA", Tax.TaxRateType.PERCENTAGE, from, to,
                            Config.Key.Context.Portugal.Continental.VAT.REDUCED_PERCENT,
                            Config.Key.Context.Portugal.Continental.VAT.REDUCED_UUID);

                    // Madeira
                    final PTTaxEntity VAT_NORMAL_MADEIRA_PORTUGAL = this.buildTaxEntity(daoPTTax, taxBuilder,
                            PTVATCode.NORMAL, CONTEXT_MADEIRA_PORTUGAL, Currency.getInstance("EUR"),
                            "IVA Normal Madeira", "IVA", Tax.TaxRateType.PERCENTAGE, from, to,
                            Config.Key.Context.Portugal.Madeira.VAT.NORMAL_PERCENT,
                            Config.Key.Context.Portugal.Madeira.VAT.NORMAL_UUID);

                    final PTTaxEntity VAT_INTERMEDIATE_MADEIRA_PORTUGAL = this.buildTaxEntity(daoPTTax, taxBuilder,
                            PTVATCode.INTERMEDIATE, CONTEXT_MADEIRA_PORTUGAL, Currency.getInstance("EUR"),
                            "IVA Intermedio Madeira", "IVA", Tax.TaxRateType.PERCENTAGE, from, to,
                            Config.Key.Context.Portugal.Madeira.VAT.INTERMEDIATE_PERCENT,
                            Config.Key.Context.Portugal.Madeira.VAT.INTERMEDIATE_UUID);

                    final PTTaxEntity VAT_REDUCED_MADEIRA_PORTUGAL = this.buildTaxEntity(daoPTTax, taxBuilder,
                            PTVATCode.REDUCED, CONTEXT_MADEIRA_PORTUGAL, Currency.getInstance("EUR"),
                            "IVA Reduzido Madeira", "IVA", Tax.TaxRateType.PERCENTAGE, from, to,
                            Config.Key.Context.Portugal.Madeira.VAT.REDUCED_PERCENT,
                            Config.Key.Context.Portugal.Madeira.VAT.REDUCED_UUID);

                    final PTTaxEntity VAT_NORMAL_AZORES_PORTUGAL =
                            this.buildTaxEntity(daoPTTax, taxBuilder, PTVATCode.NORMAL, CONTEXT_AZORES_PORTUGAL,
                                    Currency.getInstance("EUR"), "IVA Normal Açores", "IVA", Tax.TaxRateType.PERCENTAGE,
                                    from, to, Config.Key.Context.Portugal.Azores.VAT.NORMAL_PERCENT,
                                    Config.Key.Context.Portugal.Azores.VAT.NORMAL_UUID);

                    // Azores
                    final PTTaxEntity VAT_INTERMEDIATE_AZORES_PORTUGAL = this.buildTaxEntity(daoPTTax, taxBuilder,
                            PTVATCode.INTERMEDIATE, CONTEXT_AZORES_PORTUGAL, Currency.getInstance("EUR"),
                            "IVA Intermedio Açores", "IVA", Tax.TaxRateType.PERCENTAGE, from, to,
                            Config.Key.Context.Portugal.Azores.VAT.INTERMEDIATE_PERCENT,
                            Config.Key.Context.Portugal.Azores.VAT.INTERMEDIATE_UUID);

                    final PTTaxEntity VAT_REDUCED_AZORES_PORTUGAL = this.buildTaxEntity(daoPTTax, taxBuilder,
                            PTVATCode.REDUCED, CONTEXT_AZORES_PORTUGAL, Currency.getInstance("EUR"),
                            "IVA Reduzido Açores", "IVA", Tax.TaxRateType.PERCENTAGE, from, to,
                            Config.Key.Context.Portugal.Azores.VAT.REDUCED_PERCENT,
                            Config.Key.Context.Portugal.Azores.VAT.REDUCED_UUID);

                    final PTTaxEntity TAX_EXEMPT_PORTUGAL = this.buildTaxEntity(daoPTTax, taxBuilder, PTVATCode.EXEMPT,
                            CONTEXT_PORTUGAL, Currency.getInstance("EUR"), "Isento de IVA", "IVA", Tax.TaxRateType.NONE,
                            from, to, Config.Key.Context.Portugal.TAX_EXEMPT_VALUE,
                            Config.Key.Context.Portugal.TAX_EXEMPT_UUID);

                    return null;
                }

                private PTAddressEntity buildAddressEntity(DAOPTAddress daoPTAddress, Builder addressBuilder,
                        String number, String street, String building, String city, String region, String isoCode,
                        String details, String postalCode, String key) {

                    addressBuilder.setCity(city).setDetails(details).setISOCountry(isoCode).setNumber(number)
                            .setRegion(region).setStreetName(street).setPostalCode(postalCode).setBuilding(building);

                    PTAddressEntity address = (PTAddressEntity) addressBuilder.build();

                    address.setUID(configuration.getUID(key));

                    daoPTAddress.create(address);

                    return address;
                }

                private PTContactEntity buildContactEntity(DAOPTContact daoPTContact, PTContact.Builder contactBuilder,
                        String name, String telephone, String mobile, String email, String fax, String website,
                        String key) {

                    contactBuilder.clear();

                    contactBuilder.setName(name).setEmail(email).setMobile(mobile).setFax(fax).setTelephone(telephone)
                            .setWebsite(website);

                    final PTContactEntity contact = (PTContactEntity) contactBuilder.build();

                    contact.setUID(configuration.getUID(key));

                    daoPTContact.create(contact);

                    return contact;
                }

                private PTTaxEntity buildTaxEntity(DAOPTTax daoPTTax, PTTax.Builder taxBuilder, String taxCode,
                        PTRegionContextEntity context, Currency currency, String description, String designation,
                        Tax.TaxRateType type, Date validFrom, Date validTo, String valueKey, String key) {

                    BigDecimal amount = new BigDecimal(configuration.get(valueKey));

                    taxBuilder.clear();

                    taxBuilder.setCode(taxCode).setContextUID(context.getUID()).setCurrency(currency)
                            .setDescription(description).setDesignation(designation).setTaxRate(type, amount)
                            .setValidFrom(validFrom).setValidTo(validTo).setValue(amount);
                    final PTTaxEntity tax = (PTTaxEntity) taxBuilder.build();

                    tax.setUID(configuration.getUID(key));

                    daoPTTax.create(tax);

                    return tax;
                }

                private PTRegionContextEntity buildContextEntity(DAOPTRegionContext daoPTRegionContext,
                        PTRegionContext.Builder contextBuilder, String name, String description, UID parentUID,
                        String regionCode, String key) {

                    contextBuilder.clear();

                    contextBuilder.setName(name).setDescription(description).setRegionCode(regionCode)
                            .setParentContextUID(parentUID);

                    final PTRegionContextEntity context = (PTRegionContextEntity) contextBuilder.build();

                    context.setUID(configuration.getUID(key));

                    daoPTRegionContext.create(context);

                    return context;
                }

                private PTCustomerEntity buildCustomerEntity(DAOPTCustomer daoPTCustomer,
                        PTCustomer.Builder customerBuilder, String name, String taxRegistrationID,
                        PTAddress.Builder addressBuilder, PTContact.Builder contactBuilder, boolean hasSelfAgreement,
                        String key) {

                    customerBuilder.clear();

                    customerBuilder.setName(name).addContact(contactBuilder)
                            .setMainContactUID(contactBuilder.build().getUID())
                            .setHasSelfBillingAgreement(hasSelfAgreement)
                            .setTaxRegistrationNumber(taxRegistrationID, PortugalBootstrap.CODE_PT)
                            .setBillingAddress(addressBuilder).setShippingAddress(addressBuilder)
                            .addAddress(addressBuilder, true);

                    PTCustomerEntity customer = (PTCustomerEntity) customerBuilder.build();

                    customer.setUID(configuration.getUID(key));
                    customer.setTaxRegistrationNumber(null);

                    daoPTCustomer.create(customer);

                    return customer;
                }

            }.execute();
        } catch (Exception e) {
            PortugalBootstrap.log.error(e.getMessage(), e);
        }

    }
}
