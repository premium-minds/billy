/*
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
package com.premiumminds.billy.portugal.util;

import com.google.inject.Injector;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.services.entities.PTTax;
import com.premiumminds.billy.portugal.services.persistence.PTTaxPersistenceService;

/**
 * Encapsulates all tax information for Billy PT-Module.
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
        public PTTax normal() {
            DAOPTTax dao = Taxes.this.getInstance(DAOPTTax.class);
            return (PTTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.Portugal.Continental.VAT.NORMAL_UUID));
        }

        /**
         * @return Intermediate VAT value for Continent.
         */
        public PTTax intermediate() {
            DAOPTTax dao = Taxes.this.getInstance(DAOPTTax.class);
            return (PTTax) dao.get(
                    Taxes.this.configuration.getUID(Config.Key.Context.Portugal.Continental.VAT.INTERMEDIATE_UUID));
        }

        /**
         * @return Reduced VAT value for Continent.
         */
        public PTTax reduced() {
            DAOPTTax dao = Taxes.this.getInstance(DAOPTTax.class);
            return (PTTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.Portugal.Continental.VAT.REDUCED_UUID));
        }

    }

    /**
     * Provides Madeira tax information
     */
    public class Madeira {

        /**
         * @return Normal VAT value for Madeira.
         */
        public PTTax normal() {
            DAOPTTax dao = Taxes.this.getInstance(DAOPTTax.class);
            return (PTTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.Portugal.Madeira.VAT.NORMAL_UUID));
        }

        /**
         * @return Intermediate VAT value for Madeira.
         */
        public PTTax intermediate() {
            DAOPTTax dao = Taxes.this.getInstance(DAOPTTax.class);
            return (PTTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.Portugal.Madeira.VAT.INTERMEDIATE_UUID));
        }

        /**
         * @return Reduced VAT value for Madeira.
         */
        public PTTax reduced() {
            DAOPTTax dao = Taxes.this.getInstance(DAOPTTax.class);
            return (PTTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.Portugal.Madeira.VAT.REDUCED_UUID));
        }

    }

    /**
     * Provides Azores tax information
     */
    public class Azores {

        /**
         * @return Normal VAT value for Azores.
         */
        public PTTax normal() {
            DAOPTTax dao = Taxes.this.getInstance(DAOPTTax.class);
            return (PTTax) dao.get(Taxes.this.configuration.getUID(Config.Key.Context.Portugal.Azores.VAT.NORMAL_UUID));
        }

        /**
         * @return Intermediate VAT value for Azores.
         */
        public PTTax intermediate() {
            DAOPTTax dao = Taxes.this.getInstance(DAOPTTax.class);
            return (PTTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.Portugal.Azores.VAT.INTERMEDIATE_UUID));
        }

        /**
         * @return Reduced VAT value for Azores.
         */
        public PTTax reduced() {
            DAOPTTax dao = Taxes.this.getInstance(DAOPTTax.class);
            return (PTTax) dao
                    .get(Taxes.this.configuration.getUID(Config.Key.Context.Portugal.Azores.VAT.REDUCED_UUID));
        }

    }

    /**
     * @return Exemption tax value.
     */
    public PTTax exempt() {
        DAOPTTax dao = this.getInstance(DAOPTTax.class);
        return (PTTax) dao.get(this.configuration.getUID(Config.Key.Context.Portugal.TAX_EXEMPT_UUID));
    }

    private final Continent continent;
    private final Madeira madeira;
    private final Azores azores;
    private final Injector injector;
    private final PTTaxPersistenceService persistenceService;

    public Taxes(Injector injector) {
        this.continent = new Continent();
        this.madeira = new Madeira();
        this.azores = new Azores();
        this.injector = injector;
        this.persistenceService = this.getInstance(PTTaxPersistenceService.class);
    }

    /**
     * @return Portuguese tax information from {@link Continent} region.
     */
    public Continent continent() {
        return this.continent;
    }

    /**
     * @return Portuguese tax information from {@link Madeira} island.
     */
    public Madeira madeira() {
        return this.madeira;
    }

    /**
     * @return Portuguese tax information from {@link Azores} island.
     */
    public Azores azores() {
        return this.azores;
    }

    public PTTaxPersistenceService persistence() {
        return this.persistenceService;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
