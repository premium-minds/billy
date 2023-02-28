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

import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntryEntity;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoiceEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import com.premiumminds.billy.andorra.services.builders.ADInvoiceEntryBuilder;
import com.premiumminds.billy.andorra.services.entities.ADInvoiceEntry;
import javax.inject.Inject;

public class ADInvoiceEntryBuilderImpl<TBuilder extends ADInvoiceEntryBuilderImpl<TBuilder, TEntry>,
    TEntry extends ADInvoiceEntry>
    extends ADGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, ADInvoiceEntity, DAOADInvoiceEntry, DAOADInvoice>
    implements ADInvoiceEntryBuilder<TBuilder, TEntry, ADInvoiceEntity>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public ADInvoiceEntryBuilderImpl(
		DAOADInvoiceEntry daoESEntry, DAOADInvoice daoESInvoice, DAOADTax daoESTax,
		DAOADProduct daoESProduct, DAOADRegionContext daoESRegionContext) {
        super(daoESEntry, daoESInvoice, daoESTax, daoESProduct, daoESRegionContext);
    }

    @Override
    protected ADInvoiceEntryEntity getTypeInstance() {
        return (ADInvoiceEntryEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.getTypeInstance().setCreditOrDebit(CreditOrDebit.CREDIT);
        super.validateInstance();
    }

}
