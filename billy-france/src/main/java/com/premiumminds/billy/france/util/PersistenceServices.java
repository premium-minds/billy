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
import com.premiumminds.billy.france.services.persistence.FRBusinessPersistenceService;
import com.premiumminds.billy.france.services.persistence.FRCreditNotePersistenceService;
import com.premiumminds.billy.france.services.persistence.FRCustomerPersistenceService;
import com.premiumminds.billy.france.services.persistence.FRInvoicePersistenceService;
import com.premiumminds.billy.france.services.persistence.FRProductPersistenceService;
import com.premiumminds.billy.france.services.persistence.FRRegionContextPersistenceService;
import com.premiumminds.billy.france.services.persistence.FRSimpleInvoicePersistenceService;
import com.premiumminds.billy.france.services.persistence.FRSupplierPersistenceService;
import com.premiumminds.billy.france.services.persistence.FRTaxPersistenceService;

/**
 * {@link PersistenceServices} provides persistence of Billy's entities.
 */
public class PersistenceServices {

    private Injector injector;

    public PersistenceServices(Injector injector) {
        this.injector = injector;
    }

    /**
     * @return {@link FRBusinessPersistenceService}.
     */
    public FRBusinessPersistenceService business() {
        return this.injector.getInstance(FRBusinessPersistenceService.class);
    }

    /**
     * @return {@link FRCustomerPersistenceService}.
     */
    public FRCustomerPersistenceService customer() {
        return this.injector.getInstance(FRCustomerPersistenceService.class);
    }

    /**
     * @return {@link FRProductPersistenceService}.
     */
    public FRProductPersistenceService product() {
        return this.injector.getInstance(FRProductPersistenceService.class);
    }

    /**
     * @return {@link FRRegionContextPersistenceService}.
     */
    public FRRegionContextPersistenceService context() {
        return this.injector.getInstance(FRRegionContextPersistenceService.class);
    }

    /**
     * @return {@link FRSupplierPersistenceService}.
     */
    public FRSupplierPersistenceService supplier() {
        return this.injector.getInstance(FRSupplierPersistenceService.class);
    }

    /**
     * @return {@link FRTaxPersistenceService}.
     */
    public FRTaxPersistenceService tax() {
        return this.injector.getInstance(FRTaxPersistenceService.class);
    }

    /**
     * @return {@link FRSimpleInvoicePersistenceService}.
     */
    public FRInvoicePersistenceService invoice() {
        return this.injector.getInstance(FRInvoicePersistenceService.class);
    }

    /**
     * @return {@link FRSimpleInvoicePersistenceService}.
     */
    public FRSimpleInvoicePersistenceService simpleInvoice() {
        return this.injector.getInstance(FRSimpleInvoicePersistenceService.class);
    }

    /**
     * @return {@link FRCreditNotePersistenceService}.
     */
    public FRCreditNotePersistenceService creditNote() {
        return this.injector.getInstance(FRCreditNotePersistenceService.class);
    }
}
