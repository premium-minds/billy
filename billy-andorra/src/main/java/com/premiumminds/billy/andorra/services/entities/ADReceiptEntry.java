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
package com.premiumminds.billy.andorra.services.entities;

import com.premiumminds.billy.andorra.services.builders.impl.ADReceiptEntryBuilderImpl;
import javax.inject.Inject;

import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceipt;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceiptEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;

public interface ADReceiptEntry extends ADGenericInvoiceEntry {

    public static class Builder extends ADReceiptEntryBuilderImpl<Builder, ADReceiptEntry> {

        @Inject
        public Builder(
			DAOADReceiptEntry daoESReceiptEntry, DAOADReceipt daoESReceipt, DAOADTax daoESTax,
			DAOADProduct daoESProduct, DAOADRegionContext daoESRegionContext) {
            super(daoESReceiptEntry, daoESReceipt, daoESTax, daoESProduct, daoESRegionContext);
        }
    }

}
