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
import com.premiumminds.billy.andorra.services.persistence.ADBusinessPersistenceService;
import com.premiumminds.billy.andorra.services.persistence.ADCreditNotePersistenceService;
import com.premiumminds.billy.andorra.services.persistence.ADCustomerPersistenceService;
import com.premiumminds.billy.andorra.services.persistence.ADInvoicePersistenceService;
import com.premiumminds.billy.andorra.services.persistence.ADProductPersistenceService;
import com.premiumminds.billy.andorra.services.persistence.ADRegionContextPersistenceService;
import com.premiumminds.billy.andorra.services.persistence.ADSimpleInvoicePersistenceService;
import com.premiumminds.billy.andorra.services.persistence.ADSupplierPersistenceService;
import com.premiumminds.billy.andorra.services.persistence.ADTaxPersistenceService;

/**
 * {@link PersistenceServices} provides persistence of Billy's entities.
 */
public class PersistenceServices {

    private Injector injector;

    public PersistenceServices(Injector injector) {
        this.injector = injector;
    }

    /**
     * @return {@link ADBusinessPersistenceService}.
     */
    public ADBusinessPersistenceService business() {
        return this.injector.getInstance(ADBusinessPersistenceService.class);
    }

    /**
     * @return {@link ADCustomerPersistenceService}.
     */
    public ADCustomerPersistenceService customer() {
        return this.injector.getInstance(ADCustomerPersistenceService.class);
    }

    /**
     * @return {@link ADProductPersistenceService}.
     */
    public ADProductPersistenceService product() {
        return this.injector.getInstance(ADProductPersistenceService.class);
    }

    /**
     * @return {@link ADRegionContextPersistenceService}.
     */
    public ADRegionContextPersistenceService context() {
        return this.injector.getInstance(ADRegionContextPersistenceService.class);
    }

    /**
     * @return {@link ADSupplierPersistenceService}.
     */
    public ADSupplierPersistenceService supplier() {
        return this.injector.getInstance(ADSupplierPersistenceService.class);
    }

    /**
     * @return {@link ADTaxPersistenceService}.
     */
    public ADTaxPersistenceService tax() {
        return this.injector.getInstance(ADTaxPersistenceService.class);
    }

    /**
     * @return {@link ADSimpleInvoicePersistenceService}.
     */
    public ADInvoicePersistenceService invoice() {
        return this.injector.getInstance(ADInvoicePersistenceService.class);
    }

    /**
     * @return {@link ADSimpleInvoicePersistenceService}.
     */
    public ADSimpleInvoicePersistenceService simpleInvoice() {
        return this.injector.getInstance(ADSimpleInvoicePersistenceService.class);
    }

    /**
     * @return {@link ADCreditNotePersistenceService}.
     */
    public ADCreditNotePersistenceService creditNote() {
        return this.injector.getInstance(ADCreditNotePersistenceService.class);
    }
}
