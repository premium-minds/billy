/**
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

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceEntryBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.spain.persistence.dao.AbstractDAOESGenericInvoice;
import com.premiumminds.billy.spain.persistence.dao.AbstractDAOESGenericInvoiceEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntryEntity;
import com.premiumminds.billy.spain.services.builders.ESGenericInvoiceEntryBuilder;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoiceEntry;

public class ESGenericInvoiceEntryBuilderImpl<TBuilder extends ESGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, TDAOEntry, TDAOInvoice>, TEntry extends ESGenericInvoiceEntry, TDAOEntry extends AbstractDAOESGenericInvoiceEntry<?>, TDAOInvoice extends AbstractDAOESGenericInvoice<?>>
        extends GenericInvoiceEntryBuilderImpl<TBuilder, TEntry, TDAOEntry, TDAOInvoice>
        implements ESGenericInvoiceEntryBuilder<TBuilder, TEntry> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public ESGenericInvoiceEntryBuilderImpl(TDAOEntry daoESGenericInvoiceEntry, TDAOInvoice daoESGenericInvoice,
            DAOESTax daoESTax, DAOESProduct daoESProduct, DAOESRegionContext daoESRegionContext) {
        super(daoESGenericInvoiceEntry, daoESGenericInvoice, daoESTax, daoESProduct, daoESRegionContext);
    }

    @Override
    protected ESGenericInvoiceEntryEntity getTypeInstance() {
        return (ESGenericInvoiceEntryEntity) super.getTypeInstance();
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxPointDate(Date date) {
        BillyValidator.mandatory(date, ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
        this.getTypeInstance().setTaxPointDate(date);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
        ESGenericInvoiceEntryEntity i = this.getTypeInstance();
        BillyValidator.mandatory(i.getQuantity(),
                ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.quantity"));
        BillyValidator.mandatory(i.getUnitOfMeasure(),
                ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit"));
        BillyValidator.mandatory(i.getProduct(), ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.product"));
        BillyValidator.notEmpty(i.getTaxes(), ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(i.getTaxAmount(), ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(i.getTaxPointDate(),
                ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
        if (i.getTaxAmount().compareTo(BigDecimal.ZERO) == 0) {
            BillyValidator.mandatory(i.getTaxExemptionReason(),
                    ESGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_exemption_reason"));
        }
    }

}
