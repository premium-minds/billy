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

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceEntryBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.france.persistence.dao.AbstractDAOFRGenericInvoice;
import com.premiumminds.billy.france.persistence.dao.AbstractDAOFRGenericInvoiceEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.persistence.entities.FRGenericInvoiceEntryEntity;
import com.premiumminds.billy.france.services.builders.FRGenericInvoiceEntryBuilder;
import com.premiumminds.billy.france.services.entities.FRGenericInvoiceEntry;

public class FRGenericInvoiceEntryBuilderImpl<TBuilder extends FRGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, TDAOEntry, TDAOInvoice>, TEntry extends FRGenericInvoiceEntry, TDAOEntry extends AbstractDAOFRGenericInvoiceEntry<?>, TDAOInvoice extends AbstractDAOFRGenericInvoice<?>>
        extends GenericInvoiceEntryBuilderImpl<TBuilder, TEntry, TDAOEntry, TDAOInvoice>
        implements FRGenericInvoiceEntryBuilder<TBuilder, TEntry> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public FRGenericInvoiceEntryBuilderImpl(TDAOEntry daoFRGenericInvoiceEntry, TDAOInvoice daoFRGenericInvoice,
            DAOFRTax daoFRTax, DAOFRProduct daoFRProduct, DAOFRRegionContext daoFRRegionContext) {
        super(daoFRGenericInvoiceEntry, daoFRGenericInvoice, daoFRTax, daoFRProduct, daoFRRegionContext);
    }

    @Override
    protected FRGenericInvoiceEntryEntity getTypeInstance() {
        return (FRGenericInvoiceEntryEntity) super.getTypeInstance();
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxPointDate(Date date) {
        BillyValidator.mandatory(date, FRGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
        this.getTypeInstance().setTaxPointDate(date);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
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
        if (i.getTaxAmount().compareTo(BigDecimal.ZERO) == 0) {
            BillyValidator.mandatory(i.getTaxExemptionReason(),
                    FRGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_exemption_reason"));
        }
    }

}
