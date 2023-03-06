/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra;

import com.premiumminds.billy.andorra.Config.Key.Context.Andorra.VAT;
import com.premiumminds.billy.andorra.persistence.entities.ADRegionContextEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADTaxEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Currency;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.andorra.persistence.dao.DAOADAddress;
import com.premiumminds.billy.andorra.persistence.dao.DAOADContact;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import com.premiumminds.billy.andorra.services.entities.ADAddress;
import com.premiumminds.billy.andorra.services.entities.ADContact;
import com.premiumminds.billy.andorra.services.entities.ADCustomer;
import com.premiumminds.billy.andorra.services.entities.ADRegionContext;
import com.premiumminds.billy.andorra.services.entities.ADRegionContext.Builder;
import com.premiumminds.billy.andorra.services.entities.ADTax;
import com.premiumminds.billy.andorra.services.entities.ADTax.ADVATCode;

public class AndorraBootstrap {

    private static final Logger log = LoggerFactory.getLogger(AndorraBootstrap.class);

    protected static final String CODE_AD = "AD";

    public static void main(String[] args) {
        if (args.length > 0 && !args[0].isEmpty()) {
            AndorraBootstrap.execute(args[0]);
        } else {
            execute(BillyAndorra.DEFAULT_PERSISTENCE_UNIT); // backward compatibility
        }
    }

    private static void execute(String persistenceUnitId) {
        // Load dependency injector
        Injector injector = Guice.createInjector(new AndorraDependencyModule(),
                new AndorraPersistenceDependencyModule(persistenceUnitId));
        injector.getInstance(AndorraDependencyModule.Initializer.class);
        injector.getInstance(AndorraPersistenceDependencyModule.Initializer.class);
        AndorraBootstrap.execute(injector);

    }

    public static void execute(final Injector dependencyInjector) {
        DAOADInvoice dao = dependencyInjector.getInstance(DAOADInvoice.class);
        final Config configuration = new Config();

        try {
            new TransactionWrapper<Void>(dao) {

                @SuppressWarnings("unused")
                @Override
                public Void runTransaction() throws Exception {
                    // Dao creation
                    DAOADCustomer daoADCustomer = dependencyInjector.getInstance(DAOADCustomer.class);
                    DAOADRegionContext daoADRegionContext = dependencyInjector.getInstance(DAOADRegionContext.class);
                    DAOADTax daoADTax = dependencyInjector.getInstance(DAOADTax.class);
                    DAOADContact daoADContact = dependencyInjector.getInstance(DAOADContact.class);
                    DAOADAddress daoADAddress = dependencyInjector.getInstance(DAOADAddress.class);

                    // Builders
                    ADCustomer.Builder customerBuilder = dependencyInjector.getInstance(ADCustomer.Builder.class);
                    ADRegionContext.Builder contextBuilder =
                            dependencyInjector.getInstance(ADRegionContext.Builder.class);
                    ADTax.Builder taxBuilder = dependencyInjector.getInstance(ADTax.Builder.class);
                    ADAddress.Builder addressBuilder = dependencyInjector.getInstance(ADAddress.Builder.class);
                    ADContact.Builder contactBuilder = dependencyInjector.getInstance(ADContact.Builder.class);

                    // Andorra Contexts
                    final ADRegionContextEntity CONTEXT_ANDORRA = this.buildContextEntity(
						daoADRegionContext, contextBuilder, "Andorra", "The Context for the country Andorra",
						null, Config.Key.Context.Andorra.UUID);

                    final ADRegionContextEntity CONTEXT_ANDORRALAVIEJA_ANDORRA = this.buildContextEntity(
						daoADRegionContext, contextBuilder, "Álava", "The Context for the Andorra La Vieja region",
						CONTEXT_ANDORRA.getUID(), Config.Key.Context.Andorra.AndorraLaVieja.UUID);

					final ADRegionContextEntity CONTEXT_CANILLO_ANDORRA = this.buildContextEntity(
						daoADRegionContext, contextBuilder, "Canillo", "The Context for the Andorra Canillo region",
						CONTEXT_ANDORRA.getUID(), Config.Key.Context.Andorra.Canillo.UUID);

					final ADRegionContextEntity CONTEXT_ENCAMP_ANDORRA = this.buildContextEntity(
						daoADRegionContext, contextBuilder, "Encamp", "The Context for the Andorra Encamp region",
						CONTEXT_ANDORRA.getUID(), Config.Key.Context.Andorra.Encamp.UUID);

					final ADRegionContextEntity CONTEXT_LASESCALDAS_ANDORRA = this.buildContextEntity(
						daoADRegionContext, contextBuilder, "Las Escaldas Engordany", "The Context for the Andorra Las Escaldas Engordany region",
						CONTEXT_ANDORRA.getUID(), Config.Key.Context.Andorra.LasEscaldasEngordany.UUID);

					final ADRegionContextEntity CONTEXT_LAMASSANA_ANDORRA = this.buildContextEntity(
						daoADRegionContext, contextBuilder, "La Massana", "The Context for the Andorra La Massana region",
						CONTEXT_ANDORRA.getUID(), Config.Key.Context.Andorra.LaMassana.UUID);

					final ADRegionContextEntity CONTEXT_ORDINO_ANDORRA = this.buildContextEntity(
						daoADRegionContext, contextBuilder, "Ordino", "The Context for the Andorra Ordino region",
						CONTEXT_ANDORRA.getUID(), Config.Key.Context.Andorra.Ordino.UUID);

					final ADRegionContextEntity CONTEXT_SANJULIANDELORIA_ANDORRA = this.buildContextEntity(
						daoADRegionContext, contextBuilder, "San Julian De Loria", "The Context for the Andorra San Julian De Loria region",
						CONTEXT_ANDORRA.getUID(), Config.Key.Context.Andorra.SanJulianDeLoria.UUID);

                    // Taxes
                    final ZoneId madrid = ZoneId.of("Europe/Madrid");
                    final Date from = Date.from(LocalDate.of(2020,1,1).atStartOfDay().atZone(madrid).toInstant());
                    final Date to = Date.from(LocalDate.of(2114,1,1).atStartOfDay().atZone(madrid).toInstant());

					final ADTaxEntity VAT_INCREASED_ANDORRA = this.buildTaxEntity(
						daoADTax, taxBuilder, ADVATCode.OTHER, CONTEXT_ANDORRA, Currency.getInstance("EUR"),
						"IGI Incrementado", "IGI", Tax.TaxRateType.PERCENTAGE, from, to,
						Config.Key.Context.Andorra.VAT.INCREASED_PERCENT, Config.Key.Context.Andorra.VAT.INCREASED_UUID);

                    final ADTaxEntity VAT_NORMAL_CONTINENTAL_SPAIN = this.buildTaxEntity(
						daoADTax, taxBuilder, ADVATCode.NORMAL, CONTEXT_ANDORRA, Currency.getInstance("EUR"),
						"IVA General Continente", "IVA", Tax.TaxRateType.PERCENTAGE, from, to,
						Config.Key.Context.Andorra.VAT.NORMAL_PERCENT, Config.Key.Context.Andorra.VAT.NORMAL_UUID);

					final ADTaxEntity VAT_SPECIAL_ANDORRA = this.buildTaxEntity(
						daoADTax, taxBuilder, ADVATCode.OTHER, CONTEXT_ANDORRA, Currency.getInstance("EUR"),
						"IGI Special", "IGI", Tax.TaxRateType.PERCENTAGE, from, to,
					Config.Key.Context.Andorra.VAT.SPECIAL_PERCENT, Config.Key.Context.Andorra.VAT.SPECIAL_UUID);

					final ADTaxEntity VAT_REDUCED_ANDORRA = this.buildTaxEntity(
						daoADTax, taxBuilder, ADVATCode.INTERMEDIATE, CONTEXT_ANDORRA, Currency.getInstance("EUR"),
						"IGI Reducido", "IGI", Tax.TaxRateType.PERCENTAGE, from, to,
						Config.Key.Context.Andorra.VAT.INTERMEDIATE_PERCENT, Config.Key.Context.Andorra.VAT.INTERMEDIATE_UUID);

                    final ADTaxEntity VAT_SUPER_REDUCED_ANDORRA = this.buildTaxEntity(
						daoADTax, taxBuilder, ADVATCode.REDUCED, CONTEXT_ANDORRA, Currency.getInstance("EUR"),
						"IGI Superreducido", "IGI", Tax.TaxRateType.PERCENTAGE, from, to,
						Config.Key.Context.Andorra.VAT.REDUCED_PERCENT, Config.Key.Context.Andorra.VAT.REDUCED_UUID);

                    final ADTaxEntity TAX_EXEMPT_ANDORRA = this.buildTaxEntity(
						daoADTax, taxBuilder, ADVATCode.EXEMPT, CONTEXT_ANDORRA, Currency.getInstance("EUR"),
						"Isento de IGI", "IGI", Tax.TaxRateType.NONE, from, to,
						Config.Key.Context.Andorra.TAX_EXEMPT_VALUE, Config.Key.Context.Andorra.TAX_EXEMPT_UUID);

                    return null;
                }

                private ADTaxEntity buildTaxEntity(
					DAOADTax daoADTax,
					ADTax.Builder taxBuilder,
					String taxCode,
					ADRegionContextEntity context,
					Currency currency,
					String description,
					String designation,
					Tax.TaxRateType type,
					Date validFrom,
					Date validTo,
					String valueKey,
					String key)
				{

                    BigDecimal amount = new BigDecimal(configuration.get(valueKey));

                    taxBuilder.clear();

                    taxBuilder.setCode(taxCode).setContextUID(context.getUID()).setCurrency(currency)
                            .setDescription(description).setDesignation(designation).setTaxRate(type, amount)
                            .setValidFrom(validFrom).setValidTo(validTo).setValue(amount);
                    final ADTaxEntity tax = (ADTaxEntity) taxBuilder.build();

                    tax.setUID(configuration.getUID(key));

                    daoADTax.create(tax);

                    return tax;
                }

                private ADRegionContextEntity buildContextEntity(
					DAOADRegionContext daoADRegionContext,
					Builder contextBuilder,
					String name,
					String description,
					StringID<Context> parentUID,
					String key)
				{

                    contextBuilder.clear();

                    contextBuilder.setName(name).setDescription(description).setParentContextUID(parentUID);

                    final ADRegionContextEntity context = (ADRegionContextEntity) contextBuilder.build();

                    context.setUID(configuration.getUID(key));

                    daoADRegionContext.create(context);

                    return context;
                }

            }.execute();
        } catch (Exception e) {
            AndorraBootstrap.log.error(e.getMessage(), e);
        }

    }
}
