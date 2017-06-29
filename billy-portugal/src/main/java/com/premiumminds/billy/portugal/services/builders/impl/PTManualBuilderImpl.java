/**
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
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTGenericInvoiceEntity;
import com.premiumminds.billy.portugal.services.builders.PTManualInvoiceBuilder;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoiceEntry;

public abstract class PTManualBuilderImpl<TBuilder extends PTManualBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends PTGenericInvoiceEntry, TDocument extends PTGenericInvoice>
        extends PTGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements PTManualInvoiceBuilder<TBuilder, TEntry, TDocument> {

    public PTManualBuilderImpl(DAOPTGenericInvoice daoPTGenericInvoice, DAOPTBusiness daoPTBusiness,
            DAOPTCustomer daoPTCustomer, DAOPTSupplier daoPTSupplier) {
        super(daoPTGenericInvoice, daoPTBusiness, daoPTCustomer, daoPTSupplier);
        this.setSourceBilling(SourceBilling.M);
    }

    @Override
    @NotOnUpdate
    public TBuilder setSourceBilling(SourceBilling sourceBilling) {
        switch (sourceBilling) {
            case M:
                return super.setSourceBilling(sourceBilling);
            case P:
            default:
                throw new BillyValidationException();
        }
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
        BillyValidator.notNull(type, PTGenericInvoiceBuilderImpl.LOCALIZER.getString("field.unit_amount_type"));
        BillyValidator.notNull(amount, PTGenericInvoiceBuilderImpl.LOCALIZER.getString("field.unit_gross_amount"));

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
        super.validateInstance();
        PTGenericInvoiceEntity i = this.getTypeInstance();
        i.setSourceBilling(SourceBilling.M);
    }

}
