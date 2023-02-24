/*
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
package com.premiumminds.billy.france.services.entities;

import javax.inject.Inject;

import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceiptEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntity;
import com.premiumminds.billy.france.services.builders.impl.FRReceiptEntryBuilderImpl;

public interface FRReceiptEntry extends FRGenericInvoiceEntry {

    class Builder extends FRReceiptEntryBuilderImpl<Builder, FRReceiptEntry, FRReceiptEntity> {

        @Inject
        public Builder(DAOFRReceiptEntry daoFRReceiptEntry, DAOFRReceipt daoFRReceipt, DAOFRTax daoFRTax,
                DAOFRProduct daoFRProduct, DAOFRRegionContext daoFRRegionContext) {
            super(daoFRReceiptEntry, daoFRReceipt, daoFRTax, daoFRProduct, daoFRRegionContext);
        }
    }

}
