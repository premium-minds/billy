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

import javax.inject.Inject;
import javax.validation.ValidationException;

import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.spain.persistence.dao.DAOESBusiness;
import com.premiumminds.billy.spain.persistence.dao.DAOESCustomer;
import com.premiumminds.billy.spain.persistence.dao.DAOESReceipt;
import com.premiumminds.billy.spain.persistence.dao.DAOESSupplier;
import com.premiumminds.billy.spain.persistence.entities.ESGenericInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.services.builders.ESReceiptBuilder;
import com.premiumminds.billy.spain.services.entities.ESReceipt;
import com.premiumminds.billy.spain.services.entities.ESReceiptEntry;

public class ESReceiptBuilderImpl<TBuilder extends ESReceiptBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends ESReceiptEntry, TDocument extends ESReceipt>
        extends ESGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements ESReceiptBuilder<TBuilder, TEntry, TDocument> {

    @Inject
    public ESReceiptBuilderImpl(DAOESReceipt daoESReceipt, DAOESBusiness daoESBusiness, DAOESCustomer daoESCustomer,
            DAOESSupplier daoESSupplier) {
        super(daoESReceipt, daoESBusiness, daoESCustomer, daoESSupplier);
    }

    @Override
    protected ESReceiptEntity getTypeInstance() {
        return (ESReceiptEntity) super.getTypeInstance();
    }

    @Override
    protected void validateValues() throws ValidationException {
        ESReceiptEntity i = this.getTypeInstance();
        i.setCreditOrDebit(CreditOrDebit.CREDIT);
        super.validateValues();
    }

    @Override
    protected void validateESInstance(ESGenericInvoiceEntity i) {
        super.validateDate();

        BillyValidator.mandatory(i.getSourceId(), ESGenericInvoiceBuilderImpl.LOCALIZER.getString("field.source"));
        BillyValidator.mandatory(i.getDate(), ESGenericInvoiceBuilderImpl.LOCALIZER.getString("field.date"));
        if (i.isSelfBilled() != null) {
            BillyValidator.mandatory(i.isSelfBilled(),
                    ESGenericInvoiceBuilderImpl.LOCALIZER.getString("field.self_billed"));
        } else {
            i.setSelfBilled(false);
        }
        BillyValidator.mandatory(i.isCancelled(), ESGenericInvoiceBuilderImpl.LOCALIZER.getString("field.cancelled"));
        BillyValidator.mandatory(i.isBilled(), ESGenericInvoiceBuilderImpl.LOCALIZER.getString("field.billed"));
        BillyValidator.notEmpty(i.getPayments(),
                ESGenericInvoiceBuilderImpl.LOCALIZER.getString("field.payment_mechanism"));
    }

}
