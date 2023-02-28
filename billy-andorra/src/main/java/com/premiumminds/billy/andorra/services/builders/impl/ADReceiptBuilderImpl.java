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

import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntity;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.andorra.persistence.dao.AbstractDAOADGenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;
import com.premiumminds.billy.andorra.persistence.entities.ADGenericInvoiceEntity;
import com.premiumminds.billy.andorra.services.builders.ADReceiptBuilder;
import com.premiumminds.billy.andorra.services.entities.ADReceipt;
import com.premiumminds.billy.andorra.services.entities.ADReceiptEntry;

public class ADReceiptBuilderImpl<TBuilder extends ADReceiptBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends ADReceiptEntry, TDocument extends ADReceipt>
        extends ADGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements ADReceiptBuilder<TBuilder, TEntry, TDocument>
{

    public <TDAO extends AbstractDAOADGenericInvoice<? extends TDocument>> ADReceiptBuilderImpl(TDAO daoESReceipt,
																								DAOADBusiness daoESBusiness, DAOADCustomer daoESCustomer, DAOADSupplier daoESSupplier) {
        super(daoESReceipt, daoESBusiness, daoESCustomer, daoESSupplier);
    }

    @Override
    protected ADReceiptEntity getTypeInstance() {
        return (ADReceiptEntity) super.getTypeInstance();
    }

    @Override
    protected void validateValues() {
        ADReceiptEntity i = this.getTypeInstance();
        i.setCreditOrDebit(CreditOrDebit.CREDIT);
        super.validateValues();
    }

    @Override
    protected void validateESInstance(ADGenericInvoiceEntity i) {
        super.validateDate();

        BillyValidator.mandatory(i.getSourceId(), LOCALIZER.getString("field.source"));
        BillyValidator.mandatory(i.getDate(), LOCALIZER.getString("field.date"));
        if (i.isSelfBilled() != null) {
            BillyValidator.mandatory(i.isSelfBilled(),
									 LOCALIZER.getString("field.self_billed"));
        } else {
            i.setSelfBilled(false);
        }
        BillyValidator.mandatory(i.isCancelled(), LOCALIZER.getString("field.cancelled"));
        BillyValidator.mandatory(i.isBilled(), LOCALIZER.getString("field.billed"));
        BillyValidator.notEmpty(i.getPayments(),
								LOCALIZER.getString("field.payment_mechanism"));
    }

}
