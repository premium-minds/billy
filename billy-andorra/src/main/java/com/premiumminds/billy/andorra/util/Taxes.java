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
import com.premiumminds.billy.andorra.Config.Key.Context.Andorra;
import com.premiumminds.billy.andorra.Config.Key.Context.Andorra.VAT;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import com.premiumminds.billy.andorra.services.entities.ADTax;
import com.premiumminds.billy.andorra.services.persistence.ADTaxPersistenceService;

/**
 * Encapsulates all tax information for Billy AD-Module.
 */
public class Taxes {

    Config configuration = new Config();

	/**
	 * @return Increased VAT value for Andorra.
	 */
	public ADTax increased() {
		DAOADTax dao = Taxes.this.getInstance(DAOADTax.class);
		return (ADTax) dao
			.get(Taxes.this.configuration.getUID(VAT.INCREASED_UUID));
	}

	/**
	 * @return Normal VAT value for Andorra.
	 */
	public ADTax normal() {
		DAOADTax dao = Taxes.this.getInstance(DAOADTax.class);
		return (ADTax) dao
			.get(Taxes.this.configuration.getUID(VAT.NORMAL_UUID));
	}

	/**
	 * @return Special VAT value for Andorra.
	 */
	public ADTax special() {
		DAOADTax dao = Taxes.this.getInstance(DAOADTax.class);
		return (ADTax) dao
			.get(Taxes.this.configuration.getUID(VAT.SPECIAL_UUID));
	}

	/**
	 * @return Intermediate VAT value for Andorra.
	 */
	public ADTax intermediate() {
		DAOADTax dao = Taxes.this.getInstance(DAOADTax.class);
		return (ADTax) dao
			.get(Taxes.this.configuration.getUID(VAT.INTERMEDIATE_UUID));
	}

	/**
	 * @return Reduced VAT value for Andorra.
	 */
	public ADTax reduced() {
		DAOADTax dao = Taxes.this.getInstance(DAOADTax.class);
		return (ADTax) dao
			.get(Taxes.this.configuration.getUID(VAT.REDUCED_UUID));
	}

    /**
     * @return Exemption tax value.
     */
    public ADTax exempt() {
        DAOADTax dao = this.getInstance(DAOADTax.class);
        return (ADTax) dao.get(this.configuration.getUID(Andorra.TAX_EXEMPT_UUID));
    }
    private final Injector injector;
    private final ADTaxPersistenceService persistenceService;

    public Taxes(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(ADTaxPersistenceService.class);
    }

    public ADTaxPersistenceService persistence() {
        return this.persistenceService;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
