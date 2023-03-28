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

import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoiceEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntryEntity;
import com.premiumminds.billy.andorra.services.builders.ADManualInvoiceEntryBuilder;
import com.premiumminds.billy.andorra.services.entities.ADGenericInvoiceEntry;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceEntryBuilderImpl;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;

public class ADManualInvoiceEntryBuilderImpl<TBuilder extends ADManualInvoiceEntryBuilderImpl<TBuilder, TEntry>,
    TEntry extends ADGenericInvoiceEntry>
    extends ADManualEntryBuilderImpl<TBuilder, TEntry, ADInvoiceEntity, DAOADInvoiceEntry, DAOADInvoice>
    implements ADManualInvoiceEntryBuilder<TBuilder, TEntry, ADInvoiceEntity>
{

    public ADManualInvoiceEntryBuilderImpl(
        DAOADInvoiceEntry daoADEntry,
        DAOADInvoice daoADInvoice,
        DAOADTax daoADTax,
        DAOADProduct daoADProduct,
        DAOADRegionContext daoADRegionContext)
    {
        super(daoADEntry, daoADInvoice, daoADTax, daoADProduct, daoADRegionContext);
    }

    @Override
    protected ADInvoiceEntryEntity getTypeInstance() {
        return (ADInvoiceEntryEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.getTypeInstance().setCreditOrDebit(CreditOrDebit.CREDIT);
        this.validateValues();
        super.validateInstance();
    }

    @Override
    protected void validateValues() {
        GenericInvoiceEntryEntity e = this.getTypeInstance();
        for (Tax t : e.getProduct().getTaxes()) {
            if (this.daoContext.isSameOrSubContext(this.context, t.getContext())) {
                Date taxDate = e.getTaxPointDate() == null ? new Date() : e.getTaxPointDate();
                if (DateUtils.isSameDay(t.getValidTo(), taxDate) || t.getValidTo().after(taxDate)) {
                    e.getTaxes().add(t);
                }
            }
        }
        if (e.getTaxes().isEmpty()) {
            throw new BillyValidationException(
                    GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("exception.invalid_taxes"));
        }
    }
}
