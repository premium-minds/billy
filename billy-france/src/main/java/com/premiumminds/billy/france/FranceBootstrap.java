/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france;

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
import com.premiumminds.billy.france.persistence.dao.DAOFRAddress;
import com.premiumminds.billy.france.persistence.dao.DAOFRContact;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.persistence.entities.FRRegionContextEntity;
import com.premiumminds.billy.france.persistence.entities.FRTaxEntity;
import com.premiumminds.billy.france.services.entities.FRAddress;
import com.premiumminds.billy.france.services.entities.FRContact;
import com.premiumminds.billy.france.services.entities.FRCustomer;
import com.premiumminds.billy.france.services.entities.FRRegionContext;
import com.premiumminds.billy.france.services.entities.FRTax;
import com.premiumminds.billy.france.services.entities.FRTax.FRVATCode;

public class FranceBootstrap {

    private static final Logger log = LoggerFactory.getLogger(FranceBootstrap.class);

    protected static final String CODE_FR = "FR";

    public static void main(String[] args) {
        if (args.length > 0 && !args[0].isEmpty()) {
            FranceBootstrap.execute(args[0]);
        } else {
            FranceBootstrap.execute(BillyFrance.DEFAULT_PERSISTENCE_UNIT); // backward compatibility
        }
    }

    private static void execute(String persistenceUnitId) {
        // Load dependency injector
        Injector injector = Guice.createInjector(new FranceDependencyModule(),
                new FrancePersistenceDependencyModule(persistenceUnitId));
        injector.getInstance(FranceDependencyModule.Initializer.class);
        injector.getInstance(FrancePersistenceDependencyModule.Initializer.class);
        FranceBootstrap.execute(injector);

    }

    public static void execute(final Injector dependencyInjector) {
        DAO<?> dao = dependencyInjector.getInstance(DAOFRInvoice.class);
        final Config configuration = new Config();

        try {
            new TransactionWrapper<Void>(dao) {

                @SuppressWarnings("unused")
                @Override
                public Void runTransaction() throws Exception {
                    // Dao creation
                    DAOFRCustomer daoFRCustomer = dependencyInjector.getInstance(DAOFRCustomer.class);
                    DAOFRRegionContext daoFRRegionContext = dependencyInjector.getInstance(DAOFRRegionContext.class);
                    DAOFRTax daoFRTax = dependencyInjector.getInstance(DAOFRTax.class);
                    DAOFRContact daoFRContact = dependencyInjector.getInstance(DAOFRContact.class);
                    DAOFRAddress daoFRAddress = dependencyInjector.getInstance(DAOFRAddress.class);

                    // Builders
                    FRCustomer.Builder customerBuilder = dependencyInjector.getInstance(FRCustomer.Builder.class);
                    FRRegionContext.Builder contextBuilder =
                            dependencyInjector.getInstance(FRRegionContext.Builder.class);
                    FRTax.Builder taxBuilder = dependencyInjector.getInstance(FRTax.Builder.class);
                    FRAddress.Builder addressBuilder = dependencyInjector.getInstance(FRAddress.Builder.class);
                    FRContact.Builder contactBuilder = dependencyInjector.getInstance(FRContact.Builder.class);

                    // France Contexts
                    final FRRegionContextEntity CONTEXT_FRANCE =
                            this.buildContextEntity(daoFRRegionContext, contextBuilder, "France",
                                    "The Context for the country France", null, Config.Key.Context.France.UUID);

                    final FRRegionContextEntity CONTEXT_CONTINENTAL_FRANCE = this.buildContextEntity(daoFRRegionContext,
                            contextBuilder, "France Continental", "The Context for mainland France",
                            CONTEXT_FRANCE.getUID(), Config.Key.Context.France.Continental.UUID);

                    final FRRegionContextEntity CONTEXT_ALSACE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                    		contextBuilder, "Alsace", "The Context for the French Alsace region",
                    		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Alsace.UUID);

                	final FRRegionContextEntity CONTEXT_AQUITAINE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Aquitaine", "The Context for the French Aquitaine region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Aquitaine.UUID);

                	final FRRegionContextEntity CONTEXT_AUVERGNE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Auvergne", "The Context for the French Auvergne region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Auvergne.UUID);

                	final FRRegionContextEntity CONTEXT_BASSENORMANDIE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "BasseNormandie", "The Context for the French BasseNormandie region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.BasseNormandie.UUID);

                	final FRRegionContextEntity CONTEXT_BOURGOGNE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Bourgogne", "The Context for the French Bourgogne region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Bourgogne.UUID);

                	final FRRegionContextEntity CONTEXT_BRETAGNE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Bretagne", "The Context for the French Bretagne region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Bretagne.UUID);

                	final FRRegionContextEntity CONTEXT_CENTRE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Centre", "The Context for the French Centre region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Centre.UUID);

                	final FRRegionContextEntity CONTEXT_CHAMPAGNE_ARDENNE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Champagne-Ardenne", "The Context for the French Champagne-Ardenne region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Champagne_Ardenne.UUID);

                	final FRRegionContextEntity CONTEXT_CORSE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Corse", "The Context for the French Corse region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Corse.UUID);

                	final FRRegionContextEntity CONTEXT_FRANCHE_COMTE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Franche-Comté", "The Context for the French Franche-Comté region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Franche_Comte.UUID);

                	final FRRegionContextEntity CONTEXT_HAUTENORMANDIE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "HauteNormandie", "The Context for the French HauteNormandie region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.HauteNormandie.UUID);

                	final FRRegionContextEntity CONTEXT_ILE_DE_FRANCE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Ile-de-France", "The Context for the French Ile-de-France region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Ile_de_France.UUID);

                	final FRRegionContextEntity CONTEXT_LANGUEDOC_ROUSSILLON_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Languedoc-Roussillon", "The Context for the French Languedoc-Roussillon region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Languedoc_Roussillon.UUID);

                	final FRRegionContextEntity CONTEXT_LIMOUSIN_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Limousin", "The Context for the French Limousin region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Limousin.UUID);

                	final FRRegionContextEntity CONTEXT_LORRAINE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Lorraine", "The Context for the French Lorraine region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Lorraine.UUID);

                	final FRRegionContextEntity CONTEXT_MIDIPYRENEES_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "MidiPyrénées", "The Context for the French MidiPyrénées region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.MidiPyrenees.UUID);

                	final FRRegionContextEntity CONTEXT_NORD_PAS_DE_CALAIS_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Nord-Pas-de-Calais", "The Context for the French Nord-Pas-de-Calais region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Nord_Pas_de_Calais.UUID);

                	final FRRegionContextEntity CONTEXT_PAYS_DE_LA_LOIRE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Pays de la Loire", "The Context for the French Pays de la Loire region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Pays_de_la_Loire.UUID);

                	final FRRegionContextEntity CONTEXT_PICARDIE_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Picardie", "The Context for the French Picardie region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Picardie.UUID);

                	final FRRegionContextEntity CONTEXT_POITOU_CHARENTES_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Poitou-Charentes", "The Context for the French Poitou-Charentes region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Poitou_Charentes.UUID);

                	final FRRegionContextEntity CONTEXT_PROVENCE_ALPES_COTE_D_AZUR_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "Provence-Alpes-Côte-d'Azur", "The Context for the French Provence-Alpes-Côte-d'Azur region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.Provence_Alpes_Cote_d_Azur.UUID);

                	final FRRegionContextEntity CONTEXT_RHONEALPES_FRANCE = this.buildContextEntity(daoFRRegionContext,
                		contextBuilder, "RhôneAlpes", "The Context for the French RhôneAlpes region",
                		CONTEXT_CONTINENTAL_FRANCE.getUID(), Config.Key.Context.France.Continental.RhoneAlpes.UUID);

                    // Taxes
                    Date from = new DateTime(2017, 1, 1, 0, 0).toDateMidnight().toDateTime().toDate();
                    Date to = new DateTime(2018, 1, 1, 0, 0).toDateMidnight().toDateTime().toDate();
                    final FRTaxEntity VAT_NORMAL_CONTINENTAL_FRANCE = this.buildTaxEntity(daoFRTax, taxBuilder,
                            FRVATCode.NORMAL, CONTEXT_CONTINENTAL_FRANCE, Currency.getInstance("EUR"),
                            "IVA General Continente", "IVA", Tax.TaxRateType.PERCENTAGE, from, to,
                            Config.Key.Context.France.Continental.VAT.NORMAL_PERCENT,
                            Config.Key.Context.France.Continental.VAT.NORMAL_UUID);

                    final FRTaxEntity VAT_INTERMEDIATE_CONTINENTAL_FRANCE =
                            this.buildTaxEntity(daoFRTax, taxBuilder, FRVATCode.INTERMEDIATE, CONTEXT_CONTINENTAL_FRANCE,
                                    Currency.getInstance("EUR"), "IVA Reducido", "IVA", Tax.TaxRateType.PERCENTAGE,
                                    from, to, Config.Key.Context.France.Continental.VAT.INTERMEDIATE_PERCENT,
                                    Config.Key.Context.France.Continental.VAT.INTERMEDIATE_UUID);

                    final FRTaxEntity VAT_REDUCED_CONTINENTAL_FRANCE =
                            this.buildTaxEntity(daoFRTax, taxBuilder, FRVATCode.REDUCED, CONTEXT_CONTINENTAL_FRANCE,
                                    Currency.getInstance("EUR"), "IVA Superreducido", "IVA", Tax.TaxRateType.PERCENTAGE,
                                    from, to, Config.Key.Context.France.Continental.VAT.REDUCED_PERCENT,
                                    Config.Key.Context.France.Continental.VAT.REDUCED_UUID);
                    
                    final FRTaxEntity VAT_SUPER_REDUCED_CONTINENTAL_FRANCE =
                            this.buildTaxEntity(daoFRTax, taxBuilder, FRVATCode.SUPER_REDUCED, CONTEXT_CONTINENTAL_FRANCE,
                                    Currency.getInstance("EUR"), "IVA Superreducido", "IVA", Tax.TaxRateType.PERCENTAGE,
                                    from, to, Config.Key.Context.France.Continental.VAT.SUPER_REDUCED_PERCENT,
                                    Config.Key.Context.France.Continental.VAT.SUPER_REDUCED_UUID);

                    final FRTaxEntity TAX_EXEMPT_FRANCE = this.buildTaxEntity(daoFRTax, taxBuilder, FRVATCode.EXEMPT,
                            CONTEXT_FRANCE, Currency.getInstance("EUR"), "Isento de IVA", "IVA", Tax.TaxRateType.NONE,
                            from, to, Config.Key.Context.France.TAX_EXEMPT_VALUE,
                            Config.Key.Context.France.TAX_EXEMPT_UUID);

                    return null;
                }

                private FRTaxEntity buildTaxEntity(DAOFRTax daoFRTax, FRTax.Builder taxBuilder, String taxCode,
                        FRRegionContextEntity context, Currency currency, String description, String designation,
                        Tax.TaxRateType type, Date validFrom, Date validTo, String valueKey, String key) {

                    BigDecimal amount = new BigDecimal(configuration.get(valueKey));

                    taxBuilder.clear();

                    taxBuilder.setCode(taxCode).setContextUID(context.getUID()).setCurrency(currency)
                            .setDescription(description).setDesignation(designation).setTaxRate(type, amount)
                            .setValidFrom(validFrom).setValidTo(validTo).setValue(amount);
                    final FRTaxEntity tax = (FRTaxEntity) taxBuilder.build();

                    tax.setUID(configuration.getUID(key));

                    daoFRTax.create(tax);

                    return tax;
                }

                private FRRegionContextEntity buildContextEntity(DAOFRRegionContext daoFRRegionContext,
                        FRRegionContext.Builder contextBuilder, String name, String description, UID parentUID,
                        String key) {

                    contextBuilder.clear();

                    contextBuilder.setName(name).setDescription(description).setParentContextUID(parentUID);

                    final FRRegionContextEntity context = (FRRegionContextEntity) contextBuilder.build();

                    context.setUID(configuration.getUID(key));

                    daoFRRegionContext.create(context);

                    return context;
                }

            }.execute();
        } catch (Exception e) {
            FranceBootstrap.log.error(e.getMessage(), e);
        }

    }
}
