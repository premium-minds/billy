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

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.ValidationException;

import org.apache.commons.lang3.time.DateUtils;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.spain.persistence.dao.AbstractDAOESGenericInvoice;
import com.premiumminds.billy.spain.persistence.dao.AbstractDAOESGenericInvoiceEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntryEntity;
import com.premiumminds.billy.spain.services.builders.ESManualInvoiceEntryBuilder;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoiceEntry;

public class ESManualEntryBuilderImpl<TBuilder extends ESManualEntryBuilderImpl<TBuilder, TEntry, TDAOEntry, TDAOInvoice>, TEntry extends ESGenericInvoiceEntry, TDAOEntry extends AbstractDAOESGenericInvoiceEntry<?>, TDAOInvoice extends AbstractDAOESGenericInvoice<?>>
        extends ESGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, TDAOEntry, TDAOInvoice>
        implements ESManualInvoiceEntryBuilder<TBuilder, TEntry> {

    public ESManualEntryBuilderImpl(TDAOEntry daoESEntry, TDAOInvoice daoESInvoice, DAOESTax daoESTax,
            DAOESProduct daoESProduct, DAOESRegionContext daoESRegionContext) {
        super(daoESEntry, daoESInvoice, daoESTax, daoESProduct, daoESRegionContext);
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.validateValues();

        ESGenericInvoiceEntryEntity i = this.getTypeInstance();
        BillyValidator.mandatory(i.getQuantity(),
                ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.quantity"));
        BillyValidator.mandatory(i.getUnitOfMeasure(),
                ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit"));
        BillyValidator.<Object>mandatory(i.getProduct(), ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.product"));
        BillyValidator.notEmpty(i.getTaxes(), ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(i.getTaxAmount(), ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(i.getTaxPointDate(),
                ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
    }

    @Override
    protected void validateValues() throws ValidationException {
        GenericInvoiceEntryEntity e = this.getTypeInstance();

        for (Tax t : e.getProduct().getTaxes()) {
            if (this.daoContext.isSameOrSubContext(t.getContext(), this.context)) {
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
        BillyValidator.notNull(type, ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit_amount_type"));
        BillyValidator.notNull(amount, ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit_gross_amount"));

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
        BillyValidator.notNull(type, ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.amount_type"));
        BillyValidator.notNull(amount, ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.gross_amount"));

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
