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

import java.util.Date;

import javax.validation.ValidationException;

import org.apache.commons.lang3.time.DateUtils;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceEntryBuilderImpl;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoiceEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntryEntity;
import com.premiumminds.billy.france.services.builders.FRManualInvoiceEntryBuilder;
import com.premiumminds.billy.france.services.entities.FRGenericInvoiceEntry;

public class FRManualInvoiceEntryBuilderImpl<TBuilder extends FRManualInvoiceEntryBuilderImpl<TBuilder, TEntry>, TEntry extends FRGenericInvoiceEntry>
        extends FRManualEntryBuilderImpl<TBuilder, TEntry, DAOFRInvoiceEntry, DAOFRInvoice>
        implements FRManualInvoiceEntryBuilder<TBuilder, TEntry> {

    public FRManualInvoiceEntryBuilderImpl(DAOFRInvoiceEntry daoFREntry, DAOFRInvoice daoFRInvoice, DAOFRTax daoFRTax,
            DAOFRProduct daoFRProduct, DAOFRRegionContext daoFRRegionContext) {
        super(daoFREntry, daoFRInvoice, daoFRTax, daoFRProduct, daoFRRegionContext);
    }

    @Override
    protected FRInvoiceEntryEntity getTypeInstance() {
        return (FRInvoiceEntryEntity) super.getTypeInstance();
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
            if (this.daoContext.isSubContext(t.getContext(), this.context)) {
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
