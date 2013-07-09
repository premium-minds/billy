/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-platypus.
 * 
 * billy-platypus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-platypus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-platypus.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.portugal;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.entities.TaxEntity.Unit;
import com.premiumminds.billy.core.persistence.entities.jpa.CustomerEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.EntityBootstrapper;
import com.premiumminds.billy.core.util.TaxType;
import com.premiumminds.billy.platypus.PlatypusTestPersistenceDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTFinancialDocument;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.IPTTaxEntity.PTVatCode;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTTaxEntity;

public class PlatypusBootstrap {

	public static void main(String[] args) {
		execute();
	}

	public static void execute() {
		//Load dependency injector
		Injector injector = Guice.createInjector(new PlatypusTestPersistenceDependencyModule(), new PlatypusDependencyModule());
		injector.getInstance(PlatypusTestPersistenceDependencyModule.Initializer.class);
		injector.getInstance(PlatypusDependencyModule.Initializer.class);
		execute(injector);
	}

	public static void execute(final Injector dependencyInjector) {
		DAO<?> dao = dependencyInjector.getInstance(DAOPTFinancialDocument.class); //any dao will do
		
		try {
			new TransactionWrapper<Void>(dao) {

				@Override
				public Void runTransaction() throws Exception {
					//Load configuration
					Config configuration = new Config();
					
					DAOCustomer daoCustomer = dependencyInjector.getInstance(DAOCustomer.class);
					DAOPTRegionContext daoPTRegionContext = dependencyInjector.getInstance(DAOPTRegionContext.class);
					DAOPTTax daoPTTax = dependencyInjector.getInstance(DAOPTTax.class);
					
					
					//Generic customer
					final CustomerEntity GENERIC_CUSTOMER = 
							EntityBootstrapper.bootstrap(
									new CustomerEntity("Consumidor final", "", "", null, null, null, null, null, false),
									configuration.getUUID(Config.Key.Customer.Generic.UUID));
					daoCustomer.create(GENERIC_CUSTOMER);

					//Portugal Contexts
					final PTRegionContextEntity CONTEXT_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Portugal", "The context for the country Portugal", null, "PT"),
							configuration.getUUID(Config.Key.Context.Portugal.UUID));
					daoPTRegionContext.create(CONTEXT_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_CONTINENTAL_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Portugal Continental", "The context for Continental Portugal", CONTEXT_PORTUGAL, "PT"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.UUID));
					daoPTRegionContext.create(CONTEXT_CONTINENTAL_PORTUGAL);

					final PTRegionContextEntity CONTEXT_AVEIRO_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Aveiro", "The context for the Portuguese Aveiro region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-01"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Aveiro.UUID));
					daoPTRegionContext.create(CONTEXT_AVEIRO_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_BEJA_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Beja", "The context for the Portuguese Beja region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-02"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Beja.UUID));
					daoPTRegionContext.create(CONTEXT_BEJA_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_BRAGA_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Braga", "The context for the Portuguese Braga region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-03"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Braga.UUID));
					daoPTRegionContext.create(CONTEXT_BRAGA_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_BRAGANCA_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Bragança", "The context for the Portuguese Bragança region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-04"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Braganca.UUID));
					daoPTRegionContext.create(CONTEXT_BRAGANCA_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_CASTELO_BRANCO_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Castelo Branco", "The context for the Portuguese Castelo Branco region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-05"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.CasteloBranco.UUID));
					daoPTRegionContext.create(CONTEXT_CASTELO_BRANCO_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_COIMBRA_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Coimbra", "The context for the Portuguese Coimbra region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-06"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Coimbra.UUID));
					daoPTRegionContext.create(CONTEXT_COIMBRA_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_EVORA_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Évora", "The context for the Portuguese Évora region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-07"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Evora.UUID));
					daoPTRegionContext.create(CONTEXT_EVORA_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_FARO_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Faro", "The context for the Portuguese Faro region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-08"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Faro.UUID));
					daoPTRegionContext.create(CONTEXT_FARO_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_GUARDA_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Guarda", "The context for the Portuguese Guarda region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-09"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Guarda.UUID));
					daoPTRegionContext.create(CONTEXT_GUARDA_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_LEIRIA_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Leiria", "The context for the Portuguese Leiria region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-10"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Leiria.UUID));
					daoPTRegionContext.create(CONTEXT_LEIRIA_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_LISBOA_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Lisboa", "The context for the Portuguese Lisboa region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-11"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Lisboa.UUID));
					daoPTRegionContext.create(CONTEXT_LISBOA_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_PORTALEGRE_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Portalegre", "The context for the Portuguese Portalegre region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-12"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Portalegre.UUID));
					daoPTRegionContext.create(CONTEXT_PORTALEGRE_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_PORTO_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Porto", "The context for the Portuguese Porto region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-13"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Porto.UUID));
					daoPTRegionContext.create(CONTEXT_PORTO_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_SANTAREM_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Santarém", "The context for the Portuguese Santarém region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-14"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Santarem.UUID));
					daoPTRegionContext.create(CONTEXT_SANTAREM_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_SETUBAL_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Setúbal", "The context for the Portuguese Setúbal region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-15"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Setubal.UUID));
					daoPTRegionContext.create(CONTEXT_SETUBAL_PORTUGAL);

					final PTRegionContextEntity CONTEXT_VIANA_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Viana do Castelo", "The context for the Portuguese Viana do Castelo region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-16"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Viana.UUID));
					daoPTRegionContext.create(CONTEXT_VIANA_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_VILA_REAL_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Vila Real", "The context for the Portuguese Vila Real region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-17"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.VilaReal.UUID));
					daoPTRegionContext.create(CONTEXT_VILA_REAL_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_VISEU_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Viseu", "The context for the Portuguese Viseu region", CONTEXT_CONTINENTAL_PORTUGAL, "PT-18"),
							configuration.getUUID(Config.Key.Context.Portugal.Continental.Viseu.UUID));
					daoPTRegionContext.create(CONTEXT_VISEU_PORTUGAL);

					final PTRegionContextEntity CONTEXT_AZORES_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Azores Autonomous Region", "The context for the Portuguese Azores", CONTEXT_CONTINENTAL_PORTUGAL, "PT-20"),
							configuration.getUUID(Config.Key.Context.Portugal.Azores.UUID));
					daoPTRegionContext.create(CONTEXT_AZORES_PORTUGAL);
					
					final PTRegionContextEntity CONTEXT_MADEIRA_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTRegionContextEntity("Madeira Autonomous Region", "The context for the Portuguese Madeira island", CONTEXT_CONTINENTAL_PORTUGAL, "PT-30"),
							configuration.getUUID(Config.Key.Context.Portugal.Madeira.UUID));
					daoPTRegionContext.create(CONTEXT_MADEIRA_PORTUGAL);
					
					//Taxes
					//Portugal

					//Portugal Continent
					final PTTaxEntity VAT_NORMAL_CONTINENTAL_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTTaxEntity(
									CONTEXT_CONTINENTAL_PORTUGAL,
									"IVA Normal Continente",
									TaxType.VAT,
									new BigDecimal(configuration.get(Config.Key.Context.Portugal.Continental.VAT.NORMAL_PERCENT)),
									Unit.PERCENTAGE,
									Currency.getInstance("EUR"),
									new Date(),
									null,
									PTVatCode.NORMAL),
									configuration.getUUID(Config.Key.Context.Portugal.Continental.VAT.NORMAL_UUID));
					daoPTTax.create(VAT_NORMAL_CONTINENTAL_PORTUGAL);

					final PTTaxEntity VAT_INTERMEDIATE_CONTINENTAL_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTTaxEntity(
									CONTEXT_CONTINENTAL_PORTUGAL,
									"IVA Intermédio Continente",
									TaxType.VAT,
									new BigDecimal(configuration.get(Config.Key.Context.Portugal.Continental.VAT.INTERMEDIATE_PERCENT)),
									Unit.PERCENTAGE,
									Currency.getInstance("EUR"),
									new Date(),
									null,
									PTVatCode.NORMAL
									),
									configuration.getUUID(Config.Key.Context.Portugal.Continental.VAT.INTERMEDIATE_UUID));
					daoPTTax.create(VAT_INTERMEDIATE_CONTINENTAL_PORTUGAL);

					final PTTaxEntity VAT_REDUCED_CONTINENTAL_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTTaxEntity(
									CONTEXT_CONTINENTAL_PORTUGAL,
									"IVA Reduzido Continente",
									TaxType.VAT,
									new BigDecimal(configuration.get(Config.Key.Context.Portugal.Continental.VAT.REDUCED_PERCENT)),
									Unit.PERCENTAGE,
									Currency.getInstance("EUR"),
									new Date(),
									null,
									PTVatCode.NORMAL
									),
									configuration.getUUID(Config.Key.Context.Portugal.Continental.VAT.REDUCED_UUID));
					daoPTTax.create(VAT_REDUCED_CONTINENTAL_PORTUGAL);

					//Portugal Madeira
					final PTTaxEntity VAT_NORMAL_MADEIRA_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTTaxEntity(
									CONTEXT_MADEIRA_PORTUGAL,
									"IVA Normal Madeira",
									TaxType.VAT,
									new BigDecimal(configuration.get(Config.Key.Context.Portugal.Madeira.VAT.NORMAL_PERCENT)),
									Unit.PERCENTAGE,
									Currency.getInstance("EUR"),
									new Date(),
									null,
									PTVatCode.NORMAL
									),
									configuration.getUUID(Config.Key.Context.Portugal.Madeira.VAT.NORMAL_UUID));
					daoPTTax.create(VAT_NORMAL_MADEIRA_PORTUGAL);

					final PTTaxEntity VAT_INTERMEDIATE_MADEIRA_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTTaxEntity(
									CONTEXT_MADEIRA_PORTUGAL,
									"IVA Intermédio Madeira",
									TaxType.VAT,
									new BigDecimal(configuration.get(Config.Key.Context.Portugal.Madeira.VAT.INTERMEDIATE_PERCENT)),
									Unit.PERCENTAGE,
									Currency.getInstance("EUR"),
									new Date(),
									null,
									PTVatCode.NORMAL
									),
									configuration.getUUID(Config.Key.Context.Portugal.Madeira.VAT.INTERMEDIATE_UUID));
					daoPTTax.create(VAT_INTERMEDIATE_MADEIRA_PORTUGAL);

					final PTTaxEntity VAT_REDUCED_MADEIRA_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTTaxEntity(
									CONTEXT_MADEIRA_PORTUGAL,
									"IVA Reduzido Madeira",
									TaxType.VAT,
									new BigDecimal(configuration.get(Config.Key.Context.Portugal.Madeira.VAT.REDUCED_PERCENT)),
									Unit.PERCENTAGE,
									Currency.getInstance("EUR"),
									new Date(),
									null,
									PTVatCode.NORMAL
									),
									configuration.getUUID(Config.Key.Context.Portugal.Madeira.VAT.REDUCED_UUID));
					daoPTTax.create(VAT_REDUCED_MADEIRA_PORTUGAL);

					//Portugal Azores
					final PTTaxEntity VAT_NORMAL_AZORES_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTTaxEntity(
									CONTEXT_AZORES_PORTUGAL,
									"IVA Normal Açores",
									TaxType.VAT,
									new BigDecimal(configuration.get(Config.Key.Context.Portugal.Azores.VAT.NORMAL_PERCENT)),
									Unit.PERCENTAGE,
									Currency.getInstance("EUR"),
									new Date(),
									null,
									PTVatCode.NORMAL
									),
									configuration.getUUID(Config.Key.Context.Portugal.Azores.VAT.NORMAL_UUID));
					daoPTTax.create(VAT_NORMAL_AZORES_PORTUGAL);

					final PTTaxEntity VAT_INTERMEDIATE_AZORES_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTTaxEntity(
									CONTEXT_AZORES_PORTUGAL,
									"IVA Intermédio Açores",
									TaxType.VAT,
									new BigDecimal(configuration.get(Config.Key.Context.Portugal.Azores.VAT.INTERMEDIATE_PERCENT)),
									Unit.PERCENTAGE,
									Currency.getInstance("EUR"),
									new Date(),
									null,
									PTVatCode.NORMAL
									),
									configuration.getUUID(Config.Key.Context.Portugal.Azores.VAT.INTERMEDIATE_UUID));
					daoPTTax.create(VAT_INTERMEDIATE_AZORES_PORTUGAL);

					final PTTaxEntity VAT_REDUCED_AZORES_PORTUGAL = EntityBootstrapper.bootstrap(
							new PTTaxEntity(
									CONTEXT_AZORES_PORTUGAL,
									"IVA Reduzido Açores",
									TaxType.VAT,
									new BigDecimal(configuration.get(Config.Key.Context.Portugal.Azores.VAT.REDUCED_PERCENT)),
									Unit.PERCENTAGE,
									Currency.getInstance("EUR"),
									new Date(),
									null,
									PTVatCode.NORMAL
									),
									configuration.getUUID(Config.Key.Context.Portugal.Azores.VAT.REDUCED_UUID));
					daoPTTax.create(VAT_REDUCED_AZORES_PORTUGAL);

					return null;
				}
			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
