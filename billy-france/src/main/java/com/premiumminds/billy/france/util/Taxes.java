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
package com.premiumminds.billy.france.util;

import com.google.inject.Injector;
import com.premiumminds.billy.france.Config;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.services.entities.FRTax;
import com.premiumminds.billy.france.services.persistence.FRTaxPersistenceService;

/**
 * Encapsulates all tax information for Billy FR-Module.
 */
public class Taxes {

    Config configuration = new Config();

    /**
     * Provides Continent tax information
     */
    public class Continent {

        /**
         * @return Normal VAT value for Continent.
         */
        public FRTax normal() {
            DAOFRTax dao = Taxes.this.getInstance(DAOFRTax.class);
            return (FRTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.France.Continental.VAT.NORMAL_UUID));
        }

        /**
         * @return Intermediate VAT value for Continent.
         */
        public FRTax intermediate() {
            DAOFRTax dao = Taxes.this.getInstance(DAOFRTax.class);
            return (FRTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.France.Continental.VAT.INTERMEDIATE_UUID));
        }

        /**
         * @return Reduced VAT value for Continent.
         */
        public FRTax reduced() {
        	DAOFRTax dao = Taxes.this.getInstance(DAOFRTax.class);
            return (FRTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.France.Continental.VAT.REDUCED_UUID));
        }
        
        /**
         * @return Super Reduced VAT value for Continent.
         */
        public FRTax superreduced() {
        	DAOFRTax dao = Taxes.this.getInstance(DAOFRTax.class);
            return (FRTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.France.Continental.VAT.SUPER_REDUCED_UUID));
        }
        

    }

     /**
     * @return Exemption tax value.
     */
    public FRTax exempt() {
        DAOFRTax dao = this.getInstance(DAOFRTax.class);
        return (FRTax) dao.get(this.configuration.getUID(Config.Key.Context.France.TAX_EXEMPT_UUID));
    }

    private final Continent continent;
    private final Injector injector;
    private final FRTaxPersistenceService persistenceService;

    public Taxes(Injector injector) {
        this.continent = new Continent();
        this.injector = injector;
        this.persistenceService = this.getInstance(FRTaxPersistenceService.class);
    }

    /**
     * @return Spanish tax information from {@link Continent} region.
     */
    public Continent continent() {
        return this.continent;
    }

    public FRTaxPersistenceService persistence() {
        return this.persistenceService;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
