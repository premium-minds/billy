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
import java.math.BigDecimal;
import java.util.Currency;

import org.apache.commons.lang3.Validate;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.andorra.persistence.dao.AbstractDAOADGenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;
import com.premiumminds.billy.andorra.services.builders.ADManualInvoiceBuilder;
import com.premiumminds.billy.andorra.services.entities.ADGenericInvoice;
import com.premiumminds.billy.andorra.services.entities.ADGenericInvoiceEntry;

public abstract class ADManualBuilderImpl<TBuilder extends ADManualBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends ADGenericInvoiceEntry, TDocument extends ADGenericInvoice>
        extends ADGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements ADManualInvoiceBuilder<TBuilder, TEntry, TDocument>
{

    public <TDAO extends AbstractDAOADGenericInvoice<? extends TDocument>> ADManualBuilderImpl(TDAO daoESGenericInvoice,
																							   DAOADBusiness daoESBusiness, DAOADCustomer daoESCustomer, DAOADSupplier daoESSupplier) {
        super(daoESGenericInvoice, daoESBusiness, daoESCustomer, daoESSupplier);
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
        BillyValidator.notNull(type, LOCALIZER.getString("field.unit_amount_type"));
        BillyValidator.notNull(amount, LOCALIZER.getString("field.unit_gross_amount"));

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
    protected void validateValues() {
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
        ADGenericInvoiceEntity i = this.getTypeInstance();
        this.validateESInstance(i);
    }

}
