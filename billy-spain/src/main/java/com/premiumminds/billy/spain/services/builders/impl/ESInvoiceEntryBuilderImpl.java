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
package com.premiumminds.billy.spain.services.builders.impl;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoiceEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntryEntity;
import com.premiumminds.billy.spain.services.builders.ESInvoiceEntryBuilder;
import com.premiumminds.billy.spain.services.entities.ESInvoiceEntry;
import javax.inject.Inject;

public class ESInvoiceEntryBuilderImpl<TBuilder extends ESInvoiceEntryBuilderImpl<TBuilder, TEntry>,
    TEntry extends ESInvoiceEntry>
    extends ESGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, ESInvoiceEntity, DAOESInvoiceEntry, DAOESInvoice>
    implements ESInvoiceEntryBuilder<TBuilder, TEntry, ESInvoiceEntity> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    @Inject
    public ESInvoiceEntryBuilderImpl(DAOESInvoiceEntry daoESEntry, DAOESInvoice daoESInvoice, DAOESTax daoESTax,
            DAOESProduct daoESProduct, DAOESRegionContext daoESRegionContext) {
        super(daoESEntry, daoESInvoice, daoESTax, daoESProduct, daoESRegionContext);
    }

    @Override
    protected ESInvoiceEntryEntity getTypeInstance() {
        return (ESInvoiceEntryEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.getTypeInstance().setCreditOrDebit(CreditOrDebit.CREDIT);
        super.validateInstance();
    }

}
