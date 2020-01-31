/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import java.util.Date;

import javax.validation.ValidationException;

import org.apache.commons.lang3.time.DateUtils;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceEntryBuilderImpl;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoiceEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntryEntity;
import com.premiumminds.billy.portugal.services.builders.PTManualInvoiceEntryBuilder;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoiceEntry;

public class PTManualInvoiceEntryBuilderImpl<TBuilder extends PTManualInvoiceEntryBuilderImpl<TBuilder, TEntry>, TEntry extends PTGenericInvoiceEntry>
        extends PTManualEntryBuilderImpl<TBuilder, TEntry, DAOPTInvoiceEntry, DAOPTInvoice>
        implements PTManualInvoiceEntryBuilder<TBuilder, TEntry> {

    public PTManualInvoiceEntryBuilderImpl(DAOPTInvoiceEntry daoPTEntry, DAOPTInvoice daoPTInvoice, DAOPTTax daoPTTax,
            DAOPTProduct daoPTProduct, DAOPTRegionContext daoPTRegionContext) {
        super(daoPTEntry, daoPTInvoice, daoPTTax, daoPTProduct, daoPTRegionContext);
    }

    @Override
    protected PTInvoiceEntryEntity getTypeInstance() {
        return (PTInvoiceEntryEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.getTypeInstance().setCreditOrDebit(CreditOrDebit.CREDIT);
        this.validateValues();
        super.validateInstance();
    }

    @Override
    protected void validateValues() throws BillyValidationException {
        GenericInvoiceEntryEntity e = this.getTypeInstance();
        for (Tax t : e.getProduct().getTaxes()) {
            if (this.daoContext.isSameOrSubContext(t.getContext(), this.context)) {
                Date taxDate = e.getTaxPointDate() == null ? new Date() : e.getTaxPointDate();
                if (DateUtils.isSameDay(t.getValidTo(), taxDate) || t.getValidTo().after(taxDate)) {
                    e.getTaxes().add(t);
                }
            }
        }
        if (e.getTaxes().isEmpty()) {
            throw new ValidationException(
                    GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("exception.invalid_taxes"));
        }
    }

}
