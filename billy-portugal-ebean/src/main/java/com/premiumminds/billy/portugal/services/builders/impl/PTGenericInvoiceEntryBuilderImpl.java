/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import java.math.BigDecimal;
import java.util.Date;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.dao.AbstractDAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.AbstractDAOGenericInvoiceEntry;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceEntryBuilderImpl;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntryEntity;
import com.premiumminds.billy.portugal.services.builders.PTGenericInvoiceEntryBuilder;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoiceEntry;

public class PTGenericInvoiceEntryBuilderImpl<TBuilder extends PTGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, TDAOEntry, TDAOInvoice>, TEntry extends PTGenericInvoiceEntry, TDAOEntry extends AbstractDAOGenericInvoiceEntry<?>, TDAOInvoice extends AbstractDAOGenericInvoice<?>>
        extends GenericInvoiceEntryBuilderImpl<TBuilder, TEntry, TDAOEntry, TDAOInvoice>
        implements PTGenericInvoiceEntryBuilder<TBuilder, TEntry> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public PTGenericInvoiceEntryBuilderImpl(TDAOEntry daoPTGenericInvoiceEntry, TDAOInvoice daoPTGenericInvoice,
            DAOPTTax daoPTTax, DAOPTProduct daoPTProduct, DAOPTRegionContext daoPTRegionContext) {
        super(daoPTGenericInvoiceEntry, daoPTGenericInvoice, daoPTTax, daoPTProduct, daoPTRegionContext);
    }

    @Override
    protected PTGenericInvoiceEntryEntity getTypeInstance() {
        return (PTGenericInvoiceEntryEntity) super.getTypeInstance();
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxPointDate(Date date) {
        // The <generic> specs below are necessary because type inference fails here for unknown reasons
        // If removed, these lines will fail in runtime with a linkage error (ClassCastException)
        BillyValidator.<Date>mandatory(date,
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
        this.getTypeInstance().setTaxPointDate(date);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
        PTGenericInvoiceEntryEntity i = this.getTypeInstance();
        // The <generic> specs below are necessary because type inference fails here for unknown reasons
        // If removed, these lines will fail in runtime with a linkage error (ClassCastException)
        BillyValidator.<BigDecimal>mandatory(i.getQuantity(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.quantity"));
        BillyValidator.<String>mandatory(i.getUnitOfMeasure(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit"));
        BillyValidator.<Product>mandatory(i.getProduct(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.product"));
        BillyValidator.notEmpty(i.getTaxes(), PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.<BigDecimal>mandatory(i.getTaxAmount(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.<Date>mandatory(i.getTaxPointDate(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
        if (i.getTaxAmount().compareTo(BigDecimal.ZERO) == 0) {
            BillyValidator.<String>mandatory(i.getTaxExemptionReason(),
                    PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_exemption_reason"));
        }
    }

}
