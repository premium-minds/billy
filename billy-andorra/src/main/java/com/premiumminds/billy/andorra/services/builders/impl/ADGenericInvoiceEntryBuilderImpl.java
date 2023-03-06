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

import com.premiumminds.billy.andorra.persistence.entities.ADGenericInvoiceEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADGenericInvoiceEntryEntity;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceEntryBuilderImpl;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.andorra.persistence.dao.AbstractDAOADGenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.AbstractDAOADGenericInvoiceEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import com.premiumminds.billy.andorra.services.builders.ADGenericInvoiceEntryBuilder;
import com.premiumminds.billy.andorra.services.entities.ADGenericInvoiceEntry;
import java.math.BigDecimal;
import java.util.Date;

public class ADGenericInvoiceEntryBuilderImpl<TBuilder extends ADGenericInvoiceEntryBuilderImpl<TBuilder, TEntry,
    TInvoice, TDAOEntry, TDAOInvoice>, TEntry extends ADGenericInvoiceEntry, TInvoice extends ADGenericInvoiceEntity,
    TDAOEntry extends AbstractDAOADGenericInvoiceEntry<?>, TDAOInvoice extends AbstractDAOADGenericInvoice<TInvoice>>
    extends GenericInvoiceEntryBuilderImpl<TBuilder, TEntry, TInvoice, TDAOEntry, TDAOInvoice>
    implements ADGenericInvoiceEntryBuilder<TBuilder, TEntry, TInvoice>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public ADGenericInvoiceEntryBuilderImpl(
		TDAOEntry daoADGenericInvoiceEntry,
		TDAOInvoice daoADGenericInvoice,
		DAOADTax daoADTax,
		DAOADProduct daoADProduct,
		DAOADRegionContext daoADRegionContext)
	{
        super(daoADGenericInvoiceEntry, daoADGenericInvoice, daoADTax, daoADProduct, daoADRegionContext);
    }

    @Override
    protected ADGenericInvoiceEntryEntity getTypeInstance() {
        return (ADGenericInvoiceEntryEntity) super.getTypeInstance();
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxPointDate(Date date) {
        BillyValidator.mandatory(date, ADGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
        this.getTypeInstance().setTaxPointDate(date);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        super.validateInstance();
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
        if (i.getTaxAmount().compareTo(BigDecimal.ZERO) == 0) {
            BillyValidator.mandatory(i.getTaxExemptionReason(),
									 ADGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_exemption_reason"));
        }
    }

}
