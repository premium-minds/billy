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
package com.premiumminds.billy.andorra.util;

import com.google.inject.Injector;
import com.premiumminds.billy.andorra.Config;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import com.premiumminds.billy.andorra.services.entities.ADTax;
import com.premiumminds.billy.andorra.services.persistence.ADTaxPersistenceService;

/**
 * Encapsulates all tax information for Billy ES-Module.
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
        public ADTax normal() {
            DAOADTax dao = Taxes.this.getInstance(DAOADTax.class);
            return (ADTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.Spain.Continental.VAT.NORMAL_UUID));
        }

        /**
         * @return Intermediate VAT value for Continent.
         */
        public ADTax intermediate() {
            DAOADTax dao = Taxes.this.getInstance(DAOADTax.class);
            return (ADTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.Spain.Continental.VAT.INTERMEDIATE_UUID));
        }

        /**
         * @return Reduced VAT value for Continent.
         */
        public ADTax reduced() {
            DAOADTax dao = Taxes.this.getInstance(DAOADTax.class);
            return (ADTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.Spain.Continental.VAT.REDUCED_UUID));
        }

    }

    public class CanaryIslands {

        /**
         * @return Normal IGIC value for the Canary Islands.
         */
        public ADTax normal() {
            DAOADTax dao = Taxes.this.getInstance(DAOADTax.class);
            return (ADTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.Spain.CanaryIslands.IGIC.NORMAL_UUID));
        }

        /**
         * @return Intermediate IGIC value for the Canary Islands.
         */
        public ADTax intermediate() {
            DAOADTax dao = Taxes.this.getInstance(DAOADTax.class);
            return (ADTax) dao.get(
                    Taxes.this.configuration.getUID(Config.Key.Context.Spain.CanaryIslands.IGIC.INTERMEDIATE_UUID));
        }

        /**
         * @return Reduced IGIC value for the Canary Islands.
         */
        public ADTax reduced() {
            DAOADTax dao = Taxes.this.getInstance(DAOADTax.class);
            return (ADTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.Spain.CanaryIslands.IGIC.REDUCED_UUID));
        }
    }

    /**
     * @return Exemption tax value.
     */
    public ADTax exempt() {
        DAOADTax dao = this.getInstance(DAOADTax.class);
        return (ADTax) dao.get(this.configuration.getUID(Config.Key.Context.Spain.TAX_EXEMPT_UUID));
    }

    private final Continent continent;
    private final CanaryIslands canaryIslands;
    private final Injector injector;
    private final ADTaxPersistenceService persistenceService;

    public Taxes(Injector injector) {
        this.continent = new Continent();
        this.canaryIslands = new CanaryIslands();
        this.injector = injector;
        this.persistenceService = this.getInstance(ADTaxPersistenceService.class);
    }

    /**
     * @return Spanish tax information from {@link Continent} region.
     */
    public Continent continent() {
        return this.continent;
    }

    /**
     * @return Spanish tax information from {@link CanaryIslands} region.
     */
    public CanaryIslands canaryIslands() {
        return this.canaryIslands;
    }

    public ADTaxPersistenceService persistence() {
        return this.persistenceService;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
