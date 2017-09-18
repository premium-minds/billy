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
import java.util.Currency;

import javax.validation.ValidationException;

import org.apache.commons.lang3.Validate;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.france.persistence.dao.AbstractDAOFRGenericInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRBusiness;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.dao.DAOFRSupplier;
import com.premiumminds.billy.france.persistence.entities.FRGenericInvoiceEntity;
import com.premiumminds.billy.france.services.builders.FRManualInvoiceBuilder;
import com.premiumminds.billy.france.services.entities.FRGenericInvoice;
import com.premiumminds.billy.france.services.entities.FRGenericInvoiceEntry;

public abstract class FRManualBuilderImpl<TBuilder extends FRManualBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends FRGenericInvoiceEntry, TDocument extends FRGenericInvoice>
        extends FRGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements FRManualInvoiceBuilder<TBuilder, TEntry, TDocument> {

    public <TDAO extends AbstractDAOFRGenericInvoice<? extends TDocument>> FRManualBuilderImpl(TDAO daoFRGenericInvoice,
            DAOFRBusiness daoFRBusiness, DAOFRCustomer daoFRCustomer, DAOFRSupplier daoFRSupplier) {
        super(daoFRGenericInvoice, daoFRBusiness, daoFRCustomer, daoFRSupplier);
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxAmount(BigDecimal taxAmount) {
        this.getTypeInstance().setTaxAmount(taxAmount);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setAmount(AmountType type, BigDecimal amount) {
        BillyValidator.notNull(type, FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.unit_amount_type"));
        BillyValidator.notNull(amount, FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.unit_gross_amount"));

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
    protected void validateValues() throws ValidationException {
        GenericInvoiceEntity i = this.getTypeInstance();
        i.setCurrency(Currency.getInstance("EUR"));

        for (GenericInvoiceEntry e : i.getEntries()) {
            if (e.getCurrency() == null) {
                GenericInvoiceEntryEntity entry = (GenericInvoiceEntryEntity) e;
                entry.setCurrency(i.getCurrency());
                e = entry;
            } else {
                Validate.isTrue(i.getCurrency().getCurrencyCode().equals(e.getCurrency().getCurrencyCode()));
            }
        }
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        FRGenericInvoiceEntity i = this.getTypeInstance();
        this.validateFRInstance(i);
    }

}
