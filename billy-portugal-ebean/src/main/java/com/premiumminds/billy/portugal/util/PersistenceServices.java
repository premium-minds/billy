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

import com.google.inject.Inject;
import com.premiumminds.billy.portugal.services.persistence.PTBusinessPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTCreditNotePersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTCustomerPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTInvoicePersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTProductPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTRegionContextPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTSimpleInvoicePersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTSupplierPersistenceService;
import com.premiumminds.billy.portugal.services.persistence.PTTaxPersistenceService;

/**
 * {@link PersistenceServices} provides persistence of Billy's entities.
 */
public class PersistenceServices {

    private PTBusinessPersistenceService businessPersistService;

    private PTCustomerPersistenceService customerPersistService;

    private PTProductPersistenceService productPersistService;

    private PTRegionContextPersistenceService contextPersistService;

    private PTSupplierPersistenceService supplierPersistService;

    private PTTaxPersistenceService taxPersistService;

    private PTInvoicePersistenceService invoicePersistService;

    private PTSimpleInvoicePersistenceService simpleInvoicePersistService;

    private PTCreditNotePersistenceService creditNotePersistService;

    @Inject
    public PersistenceServices(PTBusinessPersistenceService businessPersistService,
            PTCustomerPersistenceService customerPersistService, PTProductPersistenceService productPersistService,
            PTRegionContextPersistenceService contextPersistService,
            PTSupplierPersistenceService supplierPersistService, PTTaxPersistenceService taxPersistService,
            PTInvoicePersistenceService invoicePersistService,
            PTSimpleInvoicePersistenceService simpleInvoicePersistService,
            PTCreditNotePersistenceService creditNotePersistService) {
        this.businessPersistService = businessPersistService;
        this.customerPersistService = customerPersistService;
        this.productPersistService = productPersistService;
        this.contextPersistService = contextPersistService;
        this.supplierPersistService = supplierPersistService;
        this.taxPersistService = taxPersistService;
        this.invoicePersistService = invoicePersistService;
        this.simpleInvoicePersistService = simpleInvoicePersistService;
        this.creditNotePersistService = creditNotePersistService;
    }

    /**
     * @return {@link PTBusinessPersistenceService}.
     */
    public PTBusinessPersistenceService business() {
        return this.businessPersistService;
    }

    /**
     * @return {@link PTCustomerPersistenceService}.
     */
    public PTCustomerPersistenceService customer() {
        return this.customerPersistService;
    }

    /**
     * @return {@link PTProductPersistenceService}.
     */
    public PTProductPersistenceService product() {
        return this.productPersistService;
    }

    /**
     * @return {@link PTRegionContextPersistenceService}.
     */
    public PTRegionContextPersistenceService context() {
        return this.contextPersistService;
    }

    /**
     * @return {@link PTSupplierPersistenceService}.
     */
    public PTSupplierPersistenceService supplier() {
        return this.supplierPersistService;
    }

    /**
     * @return {@link PTTaxPersistenceService}.
     */
    public PTTaxPersistenceService tax() {
        return this.taxPersistService;
    }

    /**
     * @return {@link PTSimpleInvoicePersistenceService}.
     */
    public PTInvoicePersistenceService invoice() {
        return this.invoicePersistService;
    }

    /**
     * @return {@link PTSimpleInvoicePersistenceService}.
     */
    public PTSimpleInvoicePersistenceService simpleInvoice() {
        return this.simpleInvoicePersistService;
    }

    /**
     * @return {@link PTCreditNotePersistenceService}.
     */
    public PTCreditNotePersistenceService creditNote() {
        return this.creditNotePersistService;
    }
}
