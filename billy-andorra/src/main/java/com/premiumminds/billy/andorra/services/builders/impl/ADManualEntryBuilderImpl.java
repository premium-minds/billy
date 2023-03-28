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

import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.entities.ADGenericInvoiceEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADGenericInvoiceEntryEntity;
import com.premiumminds.billy.andorra.services.builders.ADManualInvoiceEntryBuilder;
import com.premiumminds.billy.andorra.services.entities.ADGenericInvoiceEntry;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.andorra.persistence.dao.AbstractDAOADGenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.AbstractDAOADGenericInvoiceEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;

public class ADManualEntryBuilderImpl<TBuilder extends ADManualEntryBuilderImpl<TBuilder, TEntry, TInvoice, TDAOEntry,
    TDAOInvoice>, TEntry extends ADGenericInvoiceEntry, TInvoice extends ADGenericInvoiceEntity, TDAOEntry extends AbstractDAOADGenericInvoiceEntry<?>,
    TDAOInvoice extends AbstractDAOADGenericInvoice<TInvoice>>
    extends ADGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, TInvoice, TDAOEntry, TDAOInvoice>
    implements ADManualInvoiceEntryBuilder<TBuilder, TEntry, TInvoice>
{

    public ADManualEntryBuilderImpl(
        TDAOEntry daoADEntry,
        TDAOInvoice daoADInvoice,
        DAOADTax daoADTax,
        DAOADProduct daoADProduct,
        DAOADRegionContext daoADRegionContext)
    {
        super(daoADEntry, daoADInvoice, daoADTax, daoADProduct, daoADRegionContext);
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.validateValues();

        ADGenericInvoiceEntryEntity i = this.getTypeInstance();
        BillyValidator.mandatory(i.getQuantity(),
                                 ADGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.quantity"));
        BillyValidator.mandatory(i.getUnitOfMeasure(),
                                 ADGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit"));
        BillyValidator.<Object>mandatory(i.getProduct(), ADGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.product"));
        BillyValidator.notEmpty(i.getTaxes(), ADGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(i.getTaxAmount(), ADGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(i.getTaxPointDate(),
                                 ADGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
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
        BillyValidator.notNull(type, ADGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit_amount_type"));
        BillyValidator.notNull(amount, ADGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit_gross_amount"));

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
        BillyValidator.notNull(type, ADGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.amount_type"));
        BillyValidator.notNull(amount, ADGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.gross_amount"));

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
