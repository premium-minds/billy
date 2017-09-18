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

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.ValidationException;

import org.apache.commons.lang3.time.DateUtils;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.france.persistence.dao.AbstractDAOFRGenericInvoice;
import com.premiumminds.billy.france.persistence.dao.AbstractDAOFRGenericInvoiceEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.persistence.entities.FRGenericInvoiceEntryEntity;
import com.premiumminds.billy.france.services.builders.FRManualInvoiceEntryBuilder;
import com.premiumminds.billy.france.services.entities.FRGenericInvoiceEntry;

public class FRManualEntryBuilderImpl<TBuilder extends FRManualEntryBuilderImpl<TBuilder, TEntry, TDAOEntry, TDAOInvoice>, TEntry extends FRGenericInvoiceEntry, TDAOEntry extends AbstractDAOFRGenericInvoiceEntry<?>, TDAOInvoice extends AbstractDAOFRGenericInvoice<?>>
        extends FRGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, TDAOEntry, TDAOInvoice>
        implements FRManualInvoiceEntryBuilder<TBuilder, TEntry> {

    public FRManualEntryBuilderImpl(TDAOEntry daoFREntry, TDAOInvoice daoFRInvoice, DAOFRTax daoFRTax,
            DAOFRProduct daoFRProduct, DAOFRRegionContext daoFRRegionContext) {
        super(daoFREntry, daoFRInvoice, daoFRTax, daoFRProduct, daoFRRegionContext);
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.validateValues();

        FRGenericInvoiceEntryEntity i = this.getTypeInstance();
        BillyValidator.mandatory(i.getQuantity(),
                FRGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.quantity"));
        BillyValidator.mandatory(i.getUnitOfMeasure(),
                FRGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit"));
        BillyValidator.mandatory(i.getProduct(), FRGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.product"));
        BillyValidator.notEmpty(i.getTaxes(), FRGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(i.getTaxAmount(), FRGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(i.getTaxPointDate(),
                FRGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
    }

    @Override
    protected void validateValues() throws ValidationException {
        GenericInvoiceEntryEntity e = this.getTypeInstance();

        for (Tax t : e.getProduct().getTaxes()) {
            if (this.daoContext.isSubContext(t.getContext(), this.context)) {
                Date taxDate = e.getTaxPointDate() == null ? new Date() : e.getTaxPointDate();
                if (DateUtils.isSameDay(t.getValidTo(), taxDate) || t.getValidTo().after(taxDate)) {
                    e.getTaxes().add(t);
                }
            }
        }
    }

    @Override
    @NotOnUpdate
    public TBuilder setUnitTaxAmount(BigDecimal taxAmount) {
        this.getTypeInstance().setTaxAmount(taxAmount);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setUnitAmount(AmountType type, BigDecimal amount) {
        BillyValidator.notNull(type, FRGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit_amount_type"));
        BillyValidator.notNull(amount, FRGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit_gross_amount"));

        switch (type) {
            case WITH_TAX:
                this.getTypeInstance().setUnitAmountWithTax(amount);
                break;
            case WITHOUT_TAX:
                this.getTypeInstance().setUnitAmountWithoutTax(amount);
                break;
        }
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setAmount(AmountType type, BigDecimal amount) {
        BillyValidator.notNull(type, FRGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.amount_type"));
        BillyValidator.notNull(amount, FRGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.gross_amount"));

        switch (type) {
            case WITH_TAX:
                this.getTypeInstance().setAmountWithTax(amount);
                break;
            case WITHOUT_TAX:
                this.getTypeInstance().setAmountWithoutTax(amount);
                break;
        }
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxAmount(BigDecimal taxAmount) {
        this.getTypeInstance().setTaxAmount(taxAmount);
        return this.getBuilder();
    }
}
