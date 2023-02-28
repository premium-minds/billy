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
import com.premiumminds.billy.andorra.services.entities.ADTax.ESVATCode;

public class SpainBootstrap {

    private static final Logger log = LoggerFactory.getLogger(SpainBootstrap.class);

    protected static final String CODE_ES = "AD";

    public static void main(String[] args) {
        if (args.length > 0 && !args[0].isEmpty()) {
            SpainBootstrap.execute(args[0]);
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
        SpainBootstrap.execute(injector);

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
                    DAOADCustomer daoESCustomer = dependencyInjector.getInstance(DAOADCustomer.class);
                    DAOADRegionContext daoESRegionContext = dependencyInjector.getInstance(DAOADRegionContext.class);
                    DAOADTax daoESTax = dependencyInjector.getInstance(DAOADTax.class);
                    DAOADContact daoESContact = dependencyInjector.getInstance(DAOADContact.class);
                    DAOADAddress daoESAddress = dependencyInjector.getInstance(DAOADAddress.class);

                    // Builders
                    ADCustomer.Builder customerBuilder = dependencyInjector.getInstance(ADCustomer.Builder.class);
                    ADRegionContext.Builder contextBuilder =
                            dependencyInjector.getInstance(ADRegionContext.Builder.class);
                    ADTax.Builder taxBuilder = dependencyInjector.getInstance(ADTax.Builder.class);
                    ADAddress.Builder addressBuilder = dependencyInjector.getInstance(ADAddress.Builder.class);
                    ADContact.Builder contactBuilder = dependencyInjector.getInstance(ADContact.Builder.class);

                    // Spain Contexts
                    final ADRegionContextEntity CONTEXT_SPAIN =
                            this.buildContextEntity(daoESRegionContext, contextBuilder, "Spain",
                                    "The Context for the country Spain", null, Config.Key.Context.Spain.UUID);

                    final ADRegionContextEntity CONTEXT_CONTINENTAL_SPAIN = this.buildContextEntity(daoESRegionContext,
																									contextBuilder, "Spain Continental", "The Context for mainland Spain",
																									CONTEXT_SPAIN.getUID(), Config.Key.Context.Spain.Continental.UUID);

                    final ADRegionContextEntity CONTEXT_ALAVA_SPAIN = this.buildContextEntity(daoESRegionContext,
																							  contextBuilder, "Álava", "The Context for the Spanish Àlava region",
																							  CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Alava.UUID);

                    final ADRegionContextEntity CONTEXT_ALBACETE_SPAIN = this.buildContextEntity(daoESRegionContext,
																								 contextBuilder, "Albacete", "The Context for the Spanish Albacete region",
																								 CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Albacete.UUID);

                    final ADRegionContextEntity CONTEXT_ALICANTE_SPAIN = this.buildContextEntity(daoESRegionContext,
																								 contextBuilder, "Alicante", "The Context for the Spanish Alicante region",
																								 CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Alicante.UUID);

                    final ADRegionContextEntity CONTEXT_ALMERIA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								contextBuilder, "Almería", "The Context for the Spanish Almería region",
																								CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Almeria.UUID);

                    final ADRegionContextEntity CONTEXT_ASTURIAS_SPAIN = this.buildContextEntity(daoESRegionContext,
																								 contextBuilder, "Asturias", "The Context for the Spanish Asturias region",
																								 CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Asturias.UUID);

                    final ADRegionContextEntity CONTEXT_AVILA_SPAIN = this.buildContextEntity(daoESRegionContext,
																							  contextBuilder, "Ávila", "The Context for the Spanish Ávila region",
																							  CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Avila.UUID);

                    final ADRegionContextEntity CONTEXT_BADAJOZ_SPAIN = this.buildContextEntity(daoESRegionContext,
																								contextBuilder, "Badajoz", "The Context for the Spanish Badajoz region",
																								CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Badajoz.UUID);

                    final ADRegionContextEntity CONTEXT_BALEARES_SPAIN = this.buildContextEntity(daoESRegionContext,
																								 contextBuilder, "Illes Balears", "The Context for the Spanish Balearic Islands region",
																								 CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Baleares.UUID);

                    final ADRegionContextEntity CONTEXT_BARCELONA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								  contextBuilder, "Barcelona", "The Context for the Spanish Barcelona region",
																								  CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Barcelona.UUID);

                    final ADRegionContextEntity CONTEXT_BIZKAIA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								contextBuilder, "Bizkaia", "The Context for the Spanish Biscay region",
																								CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Bizkaia.UUID);

                    final ADRegionContextEntity CONTEXT_BURGOS_SPAIN = this.buildContextEntity(daoESRegionContext,
																							   contextBuilder, "Burgos", "The Context for the Spanish Burgos region",
																							   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Burgos.UUID);

                    final ADRegionContextEntity CONTEXT_CACERES_SPAIN = this.buildContextEntity(daoESRegionContext,
																								contextBuilder, "Cáceres", "The Context for the Spanish Cáceres region",
																								CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Caceres.UUID);

                    final ADRegionContextEntity CONTEXT_CADIZ_SPAIN = this.buildContextEntity(daoESRegionContext,
																							  contextBuilder, "Cádiz", "The Context for the Spanish Cádiz region",
																							  CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Cadiz.UUID);

                    final ADRegionContextEntity CONTEXT_CANTABRIA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								  contextBuilder, "Cantabria", "The Context for the Spanish Cantabria region",
																								  CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Cantabria.UUID);

                    final ADRegionContextEntity CONTEXT_CASTELLON_SPAIN = this.buildContextEntity(daoESRegionContext,
																								  contextBuilder, "Castelló", "The Context for the Spanish Castellón region",
																								  CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Castellon.UUID);

                    final ADRegionContextEntity CONTEXT_CIUDADREAL_SPAIN = this.buildContextEntity(daoESRegionContext,
																								   contextBuilder, "Ciudad Real", "The Context for the Spanish Ciudad Real region",
																								   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.CiudadReal.UUID);

                    final ADRegionContextEntity CONTEXT_CORDOBA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								contextBuilder, "Córdoba", "The Context for the Spanish Córdoba region",
																								CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Cordoba.UUID);

                    final ADRegionContextEntity CONTEXT_CUENCA_SPAIN = this.buildContextEntity(daoESRegionContext,
																							   contextBuilder, "Cuenca", "The Context for the Spanish Cuenca region",
																							   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Cuenca.UUID);

                    final ADRegionContextEntity CONTEXT_GERONA_SPAIN = this.buildContextEntity(daoESRegionContext,
																							   contextBuilder, "Girona", "The Context for the Spanish Gerona region",
																							   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Gerona.UUID);

                    final ADRegionContextEntity CONTEXT_GIPUZKOA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								 contextBuilder, "Gipuzkoa", "The Context for the Spanish Guipuscoa region",
																								 CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Gipuzkoa.UUID);

                    final ADRegionContextEntity CONTEXT_GRANADA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								contextBuilder, "Granada", "The Context for the Spanish Granada region",
																								CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Granada.UUID);

                    final ADRegionContextEntity CONTEXT_GUADALAJARA_SPAIN = this.buildContextEntity(daoESRegionContext,
																									contextBuilder, "Guadalajara", "The Context for the Spanish Guadalajara region",
																									CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Guadalajara.UUID);

                    final ADRegionContextEntity CONTEXT_HUELVA_SPAIN = this.buildContextEntity(daoESRegionContext,
																							   contextBuilder, "Huelva", "The Context for the Spanish Huelva region",
																							   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Huelva.UUID);

                    final ADRegionContextEntity CONTEXT_HUESCA_SPAIN = this.buildContextEntity(daoESRegionContext,
																							   contextBuilder, "Huesca", "The Context for the Spanish Huesca region",
																							   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Huesca.UUID);

                    final ADRegionContextEntity CONTEXT_JAEN_SPAIN = this.buildContextEntity(daoESRegionContext,
																							 contextBuilder, "Jaén",
																							 "The Context for the Spanish Jaén region",
																							 CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Jaen.UUID);

                    final ADRegionContextEntity CONTEXT_LACORUNA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								 contextBuilder, "A Coruña", "The Context for the Spanish La Corunna region",
																								 CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.LaCoruna.UUID);

                    final ADRegionContextEntity CONTEXT_LARIOJA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								contextBuilder, "La Rioja", "The Context for the Spanish La Rioja region",
																								CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.LaRioja.UUID);

                    final ADRegionContextEntity CONTEXT_LEON_SPAIN = this.buildContextEntity(daoESRegionContext,
																							 contextBuilder, "León",
																							 "The Context for the Spanish León region",
																							 CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Leon.UUID);

                    final ADRegionContextEntity CONTEXT_LERIDA_SPAIN = this.buildContextEntity(daoESRegionContext,
																							   contextBuilder, "Lleida", "The Context for the Spanish Lérida region",
																							   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Lerida.UUID);

                    final ADRegionContextEntity CONTEXT_LUGO_SPAIN = this.buildContextEntity(daoESRegionContext,
																							 contextBuilder, "Lugo",
																							 "The Context for the Spanish Lugo region",
																							 CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Lugo.UUID);

                    final ADRegionContextEntity CONTEXT_MADRID_SPAIN = this.buildContextEntity(daoESRegionContext,
																							   contextBuilder, "Madrid", "The Context for the Spanish Madrid region",
																							   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Madrid.UUID);

                    final ADRegionContextEntity CONTEXT_MALAGA_SPAIN = this.buildContextEntity(daoESRegionContext,
																							   contextBuilder, "Málaga", "The Context for the Spanish Málaga region",
																							   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Malaga.UUID);

                    final ADRegionContextEntity CONTEXT_MURCIA_SPAIN = this.buildContextEntity(daoESRegionContext,
																							   contextBuilder, "Murcia", "The Context for the Spanish Murcia region",
																							   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Murcia.UUID);

                    final ADRegionContextEntity CONTEXT_NAVARRA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								contextBuilder, "Navarra", "The Context for the Spanish Navarra region",
																								CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Navarra.UUID);

                    final ADRegionContextEntity CONTEXT_ORENSE_SPAIN = this.buildContextEntity(daoESRegionContext,
																							   contextBuilder, "Ourense", "The Context for the Spanish Orense region",
																							   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Orense.UUID);

                    final ADRegionContextEntity CONTEXT_PALENCIA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								 contextBuilder, "Palencia", "The Context for the Spanish Palencia region",
																								 CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Palencia.UUID);

                    final ADRegionContextEntity CONTEXT_PONTEVEDRA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								   contextBuilder, "Pontevedra", "The Context for the Spanish Pontevedra region",
																								   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Pontevedra.UUID);

                    final ADRegionContextEntity CONTEXT_SALAMANCA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								  contextBuilder,
																								  "Salamanca", "The Context for the Spanish Salamanca region",
																								  CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Salamanca.UUID);

                    final ADRegionContextEntity CONTEXT_SEGOVIA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								contextBuilder, "Segovia", "The Context for the Spanish Segovia region",
																								CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Segovia.UUID);

                    final ADRegionContextEntity CONTEXT_SEVILLA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								contextBuilder, "Sevilla", "The Context for the Spanish Seville region",
																								CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Sevilla.UUID);

                    final ADRegionContextEntity CONTEXT_SORIA_SPAIN = this.buildContextEntity(daoESRegionContext,
																							  contextBuilder, "Soria", "The Context for the Spanish Soria region",
																							  CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Soria.UUID);

                    final ADRegionContextEntity CONTEXT_TARRAGONA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								  contextBuilder,
																								  "Tarragona", "The Context for the Spanish Tarragona region",
																								  CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Tarragona.UUID);

                    final ADRegionContextEntity CONTEXT_TERUEL_SPAIN = this.buildContextEntity(daoESRegionContext,
																							   contextBuilder, "Teruel", "The Context for the Spanish Teruel region",
																							   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Teruel.UUID);

                    final ADRegionContextEntity CONTEXT_TOLEDO_SPAIN = this.buildContextEntity(daoESRegionContext,
																							   contextBuilder, "Toledo", "The Context for the Spanish Toledo region",
																							   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Toledo.UUID);

                    final ADRegionContextEntity CONTEXT_VALENCIA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								 contextBuilder, "Valencia", "The Context for the Spanish Valencia region",
																								 CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Valencia.UUID);

                    final ADRegionContextEntity CONTEXT_VALLADOLID_SPAIN = this.buildContextEntity(daoESRegionContext,
																								   contextBuilder, "Valladolid", "The Context for the Spanish Valladolid region",
																								   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Valladolid.UUID);

                    final ADRegionContextEntity CONTEXT_ZAMORA_SPAIN = this.buildContextEntity(daoESRegionContext,
																							   contextBuilder, "Zamora", "The Context for the Spanish Zamora region",
																							   CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Zamora.UUID);

                    final ADRegionContextEntity CONTEXT_ZARAGOZA_SPAIN = this.buildContextEntity(daoESRegionContext,
																								 contextBuilder, "Zaragoza", "The Context for the Spanish Saragossa region",
																								 CONTEXT_CONTINENTAL_SPAIN.getUID(), Config.Key.Context.Spain.Continental.Zaragoza.UUID);

                    final ADRegionContextEntity CONTEXT_CANARYISLANDS_SPAIN = this.buildContextEntity(
                            daoESRegionContext, contextBuilder, "Islas Canarias", "The Context for the Canary Islands",
                            CONTEXT_SPAIN.getUID(), Config.Key.Context.Spain.CanaryIslands.UUID);

                    final ADRegionContextEntity CONTEXT_LASPALMAS_SPAIN = this.buildContextEntity(daoESRegionContext,
																								  contextBuilder,
																								  "Las Palmas", "The Context for the Spanish Las Palmas region",
																								  CONTEXT_CANARYISLANDS_SPAIN.getUID(),
																								  Config.Key.Context.Spain.CanaryIslands.LasPalmas.UUID);

                    final ADRegionContextEntity CONTEXT_SCRUZTENERIFE_SPAIN =
                            this.buildContextEntity(daoESRegionContext, contextBuilder, "Santa Cruz de Tenerife",
                                    "The Context for the Spanish Santa Cruz de Tenerife region",
                                    CONTEXT_CANARYISLANDS_SPAIN.getUID(),
                                    Config.Key.Context.Spain.CanaryIslands.StaCruzDeTenerife.UUID);

                    // Taxes
                    ZoneId madrid = ZoneId.of("Europe/Madrid");
                    Date from = Date.from(LocalDate.of(2020,1,1).atStartOfDay().atZone(madrid).toInstant());
                    Date to = Date.from(LocalDate.of(2114,1,1).atStartOfDay().atZone(madrid).toInstant());
                    final ADTaxEntity VAT_NORMAL_CONTINENTAL_SPAIN = this.buildTaxEntity(daoESTax, taxBuilder,
																						 ESVATCode.NORMAL, CONTEXT_CONTINENTAL_SPAIN, Currency.getInstance("EUR"),
																						 "IVA General Continente", "IVA", Tax.TaxRateType.PERCENTAGE, from, to,
																						 Config.Key.Context.Spain.Continental.VAT.NORMAL_PERCENT,
																						 Config.Key.Context.Spain.Continental.VAT.NORMAL_UUID);

                    final ADTaxEntity VAT_INTERMEDIATE_CONTINENTAL_SPAIN =
                            this.buildTaxEntity(daoESTax, taxBuilder, ESVATCode.INTERMEDIATE, CONTEXT_CONTINENTAL_SPAIN,
                                    Currency.getInstance("EUR"), "IVA Reducido", "IVA", Tax.TaxRateType.PERCENTAGE,
                                    from, to, Config.Key.Context.Spain.Continental.VAT.INTERMEDIATE_PERCENT,
                                    Config.Key.Context.Spain.Continental.VAT.INTERMEDIATE_UUID);

                    final ADTaxEntity VAT_REDUCED_CONTINENTAL_SPAIN =
                            this.buildTaxEntity(daoESTax, taxBuilder, ESVATCode.REDUCED, CONTEXT_CONTINENTAL_SPAIN,
                                    Currency.getInstance("EUR"), "IVA Superreducido", "IVA", Tax.TaxRateType.PERCENTAGE,
                                    from, to, Config.Key.Context.Spain.Continental.VAT.REDUCED_PERCENT,
                                    Config.Key.Context.Spain.Continental.VAT.REDUCED_UUID);

                    final ADTaxEntity IGIC_NORMAL_CANARYISLANDS_SPAIN =
                            this.buildTaxEntity(daoESTax, taxBuilder, ESVATCode.NORMAL, CONTEXT_CANARYISLANDS_SPAIN,
                                    Currency.getInstance("EUR"), "IGIC General", "IGIC", Tax.TaxRateType.PERCENTAGE,
                                    from, to, Config.Key.Context.Spain.CanaryIslands.IGIC.NORMAL_PERCENT,
                                    Config.Key.Context.Spain.CanaryIslands.IGIC.NORMAL_UUID);

                    final ADTaxEntity IGIC_INTERMEDIATE_CANARYISLANDS_SPAIN = this.buildTaxEntity(daoESTax, taxBuilder,
																								  ESVATCode.INTERMEDIATE, CONTEXT_CANARYISLANDS_SPAIN, Currency.getInstance("EUR"),
																								  "IGIC Reducido", "IGIC", Tax.TaxRateType.PERCENTAGE, from, to,
																								  Config.Key.Context.Spain.CanaryIslands.IGIC.INTERMEDIATE_PERCENT,
																								  Config.Key.Context.Spain.CanaryIslands.IGIC.INTERMEDIATE_UUID);

                    final ADTaxEntity IGIC_REDUCED_CANARYISLANDS_SPAIN = this.buildTaxEntity(daoESTax, taxBuilder,
																							 ESVATCode.REDUCED, CONTEXT_CANARYISLANDS_SPAIN, Currency.getInstance("EUR"),
																							 "IGIC Superreducido", "IGIC", Tax.TaxRateType.PERCENTAGE, from, to,
																							 Config.Key.Context.Spain.CanaryIslands.IGIC.REDUCED_PERCENT,
																							 Config.Key.Context.Spain.CanaryIslands.IGIC.REDUCED_UUID);

                    final ADTaxEntity TAX_EXEMPT_SPAIN = this.buildTaxEntity(daoESTax, taxBuilder, ESVATCode.EXEMPT,
																			 CONTEXT_SPAIN, Currency.getInstance("EUR"), "Isento de IVA", "IVA", Tax.TaxRateType.NONE,
																			 from, to, Config.Key.Context.Spain.TAX_EXEMPT_VALUE,
																			 Config.Key.Context.Spain.TAX_EXEMPT_UUID);

                    return null;
                }

                private ADTaxEntity buildTaxEntity(
					DAOADTax daoESTax, ADTax.Builder taxBuilder, String taxCode,
					ADRegionContextEntity context, Currency currency, String description, String designation,
					Tax.TaxRateType type, Date validFrom, Date validTo, String valueKey, String key) {

                    BigDecimal amount = new BigDecimal(configuration.get(valueKey));

                    taxBuilder.clear();

                    taxBuilder.setCode(taxCode).setContextUID(context.getUID()).setCurrency(currency)
                            .setDescription(description).setDesignation(designation).setTaxRate(type, amount)
                            .setValidFrom(validFrom).setValidTo(validTo).setValue(amount);
                    final ADTaxEntity tax = (ADTaxEntity) taxBuilder.build();

                    tax.setUID(configuration.getUID(key));

                    daoESTax.create(tax);

                    return tax;
                }

                private ADRegionContextEntity buildContextEntity(
					DAOADRegionContext daoESRegionContext,
					Builder contextBuilder, String name, String description, StringID<Context> parentUID,
					String key) {

                    contextBuilder.clear();

                    contextBuilder.setName(name).setDescription(description).setParentContextUID(parentUID);

                    final ADRegionContextEntity context = (ADRegionContextEntity) contextBuilder.build();

                    context.setUID(configuration.getUID(key));

                    daoESRegionContext.create(context);

                    return context;
                }

            }.execute();
        } catch (Exception e) {
            SpainBootstrap.log.error(e.getMessage(), e);
        }

    }
}
