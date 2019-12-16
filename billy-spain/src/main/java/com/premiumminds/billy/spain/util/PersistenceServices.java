/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.util;

import com.google.inject.Injector;
import com.premiumminds.billy.spain.services.persistence.ESBusinessPersistenceService;
import com.premiumminds.billy.spain.services.persistence.ESCreditNotePersistenceService;
import com.premiumminds.billy.spain.services.persistence.ESCustomerPersistenceService;
import com.premiumminds.billy.spain.services.persistence.ESInvoicePersistenceService;
import com.premiumminds.billy.spain.services.persistence.ESProductPersistenceService;
import com.premiumminds.billy.spain.services.persistence.ESRegionContextPersistenceService;
import com.premiumminds.billy.spain.services.persistence.ESSimpleInvoicePersistenceService;
import com.premiumminds.billy.spain.services.persistence.ESSupplierPersistenceService;
import com.premiumminds.billy.spain.services.persistence.ESTaxPersistenceService;

/**
 * {@link PersistenceServices} provides persistence of Billy's entities.
 */
public class PersistenceServices {

    private Injector injector;

    public PersistenceServices(Injector injector) {
        this.injector = injector;
    }

    /**
     * @return {@link ESBusinessPersistenceService}.
     */
    public ESBusinessPersistenceService business() {
        return this.injector.getInstance(ESBusinessPersistenceService.class);
    }

    /**
     * @return {@link ESCustomerPersistenceService}.
     */
    public ESCustomerPersistenceService customer() {
        return this.injector.getInstance(ESCustomerPersistenceService.class);
    }

    /**
     * @return {@link ESProductPersistenceService}.
     */
    public ESProductPersistenceService product() {
        return this.injector.getInstance(ESProductPersistenceService.class);
    }

    /**
     * @return {@link ESRegionContextPersistenceService}.
     */
    public ESRegionContextPersistenceService context() {
        return this.injector.getInstance(ESRegionContextPersistenceService.class);
    }

    /**
     * @return {@link ESSupplierPersistenceService}.
     */
    public ESSupplierPersistenceService supplier() {
        return this.injector.getInstance(ESSupplierPersistenceService.class);
    }

    /**
     * @return {@link ESTaxPersistenceService}.
     */
    public ESTaxPersistenceService tax() {
        return this.injector.getInstance(ESTaxPersistenceService.class);
    }

    /**
     * @return {@link ESSimpleInvoicePersistenceService}.
     */
    public ESInvoicePersistenceService invoice() {
        return this.injector.getInstance(ESInvoicePersistenceService.class);
    }

    /**
     * @return {@link ESSimpleInvoicePersistenceService}.
     */
    public ESSimpleInvoicePersistenceService simpleInvoice() {
        return this.injector.getInstance(ESSimpleInvoicePersistenceService.class);
    }

    /**
     * @return {@link ESCreditNotePersistenceService}.
     */
    public ESCreditNotePersistenceService creditNote() {
        return this.injector.getInstance(ESCreditNotePersistenceService.class);
    }
}
