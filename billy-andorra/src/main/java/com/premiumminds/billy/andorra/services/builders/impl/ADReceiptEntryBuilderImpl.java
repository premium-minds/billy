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
package com.premiumminds.billy.andorra.services.builders.impl;

import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntryEntity;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceipt;
import com.premiumminds.billy.andorra.persistence.dao.DAOADReceiptEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import com.premiumminds.billy.andorra.services.builders.ADReceiptEntryBuilder;
import com.premiumminds.billy.andorra.services.entities.ADReceiptEntry;

public class ADReceiptEntryBuilderImpl<TBuilder extends ADReceiptEntryBuilderImpl<TBuilder, TEntry>,
    TEntry extends ADReceiptEntry>
    extends ADGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, ADReceiptEntity, DAOADReceiptEntry, DAOADReceipt>
    implements ADReceiptEntryBuilder<TBuilder, TEntry, ADReceiptEntity>
{

    public ADReceiptEntryBuilderImpl(
		DAOADReceiptEntry daoADReceiptEntry,
		DAOADReceipt daoADReceipt,
		DAOADTax daoADTax,
		DAOADProduct daoADProduct,
		DAOADRegionContext daoADRegionContext)
	{
        super(daoADReceiptEntry, daoADReceipt, daoADTax, daoADProduct, daoADRegionContext);
    }

    @Override
    protected ADReceiptEntryEntity getTypeInstance() {
        return (ADReceiptEntryEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.getTypeInstance().setCreditOrDebit(CreditOrDebit.CREDIT);
        super.validateInstance();
    }

}
