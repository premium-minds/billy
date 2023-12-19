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
package com.premiumminds.billy.spain.services.entities;

import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntity;
import jakarta.inject.Inject;

import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESGenericInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESGenericInvoiceEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.services.builders.impl.ESGenericInvoiceEntryBuilderImpl;

public interface ESGenericInvoiceEntry extends GenericInvoiceEntry {

    class Builder extends
        ESGenericInvoiceEntryBuilderImpl<Builder, ESGenericInvoiceEntry, ESGenericInvoiceEntity,
            DAOESGenericInvoiceEntry, DAOESGenericInvoice> {

        @Inject
        public Builder(DAOESGenericInvoiceEntry daoESGenericInvoiceEntry, DAOESGenericInvoice daoESGenericInvoice,
                DAOESTax daoESTax, DAOESProduct daoESProduct, DAOESRegionContext daoESRegionContext) {
            super(daoESGenericInvoiceEntry, daoESGenericInvoice, daoESTax, daoESProduct, daoESRegionContext);
        }
    }
}
