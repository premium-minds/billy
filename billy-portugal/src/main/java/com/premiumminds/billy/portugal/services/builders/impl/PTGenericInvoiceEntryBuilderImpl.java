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

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.dao.AbstractDAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.AbstractDAOGenericInvoiceEntry;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceEntryBuilderImpl;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntryEntity;
import com.premiumminds.billy.portugal.services.builders.PTGenericInvoiceEntryBuilder;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoiceEntry;
import java.math.BigDecimal;
import java.util.Date;

public class PTGenericInvoiceEntryBuilderImpl<TBuilder extends PTGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, TInvoice,
    TDAOEntry, TDAOInvoice>, TEntry extends PTGenericInvoiceEntry, TInvoice extends GenericInvoice, TDAOEntry extends AbstractDAOGenericInvoiceEntry<?>
    , TDAOInvoice extends AbstractDAOGenericInvoice<?>>
    extends GenericInvoiceEntryBuilderImpl<TBuilder, TEntry, TInvoice, TDAOEntry, TDAOInvoice>
    implements PTGenericInvoiceEntryBuilder<TBuilder, TEntry, TInvoice> {

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
        BillyValidator.mandatory(date, PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
        this.getTypeInstance().setTaxPointDate(date);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
        PTGenericInvoiceEntryEntity i = this.getTypeInstance();
        BillyValidator.mandatory(i.getQuantity(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.quantity"));
        BillyValidator.mandatory(i.getUnitOfMeasure(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit"));
        BillyValidator.<Object>mandatory(i.getProduct(), PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.product"));
        BillyValidator.notEmpty(i.getTaxes(), PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(i.getTaxAmount(), PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(i.getTaxPointDate(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
        if(i.getTaxAmount().compareTo(BigDecimal.ZERO) == 0) {

            BillyValidator.mandatory(
                i.getTaxExemptionCode(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_exemption_code"));
            BillyValidator.mandatory(
                i.getTaxExemptionReason(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_exemption_reason"));

        }
        if(i.getTaxExemptionCode() != null) {
            BillyValidator.matchesPattern(
                i.getTaxExemptionCode(),
                "(M[0-9]{2})+",
                GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_exemption_code"));
        }

    }

}
