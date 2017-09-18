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
package com.premiumminds.billy.france.services.builders.impl;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceiptEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntryEntity;
import com.premiumminds.billy.france.services.builders.FRReceiptEntryBuilder;
import com.premiumminds.billy.france.services.entities.FRReceiptEntry;

public class FRReceiptEntryBuilderImpl<TBuilder extends FRReceiptEntryBuilderImpl<TBuilder, TEntry>, TEntry extends FRReceiptEntry>
        extends FRGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, DAOFRReceiptEntry, DAOFRReceipt>
        implements FRReceiptEntryBuilder<TBuilder, TEntry> {

    public FRReceiptEntryBuilderImpl(DAOFRReceiptEntry daoFRReceiptEntry, DAOFRReceipt daoFRReceipt, DAOFRTax daoFRTax,
            DAOFRProduct daoFRProduct, DAOFRRegionContext daoFRRegionContext) {
        super(daoFRReceiptEntry, daoFRReceipt, daoFRTax, daoFRProduct, daoFRRegionContext);
    }

    @Override
    protected FRReceiptEntryEntity getTypeInstance() {
        return (FRReceiptEntryEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.getTypeInstance().setCreditOrDebit(CreditOrDebit.CREDIT);
        super.validateInstance();
    }

}
