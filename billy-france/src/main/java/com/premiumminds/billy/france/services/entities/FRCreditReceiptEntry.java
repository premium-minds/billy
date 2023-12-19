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

import jakarta.inject.Inject;

import com.premiumminds.billy.france.persistence.dao.DAOFRCreditReceiptEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.services.builders.impl.FRCreditReceiptEntryBuilderImpl;
import com.premiumminds.billy.france.services.builders.impl.FRManualCreditReceiptEntryBuilderImpl;

public interface FRCreditReceiptEntry extends FRGenericInvoiceEntry {

    public static class Builder extends FRCreditReceiptEntryBuilderImpl<Builder, FRCreditReceiptEntry> {

        @Inject
        public Builder(DAOFRCreditReceiptEntry daoFRCreditReceiptEntry, DAOFRReceipt daoFRReceipt, DAOFRTax daoFRTax,
                DAOFRProduct daoFRProduct, DAOFRRegionContext daoFRRegionContext) {
            super(daoFRCreditReceiptEntry, daoFRReceipt, daoFRTax, daoFRProduct, daoFRRegionContext);
        }
    }

    public static class ManualBuilder
            extends FRManualCreditReceiptEntryBuilderImpl<ManualBuilder, FRCreditReceiptEntry> {

        @Inject
        public ManualBuilder(DAOFRCreditReceiptEntry daoFRCreditReceiptEntry, DAOFRReceipt daoFRReceipt,
                DAOFRTax daoFRTax, DAOFRProduct daoFRProduct, DAOFRRegionContext daoFRRegionContext) {
            super(daoFRCreditReceiptEntry, daoFRReceipt, daoFRTax, daoFRProduct, daoFRRegionContext);
        }
    }

    public FRReceipt getReference();

    public String getReason();
}
