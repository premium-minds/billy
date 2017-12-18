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

import javax.validation.ValidationException;

import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.france.persistence.dao.AbstractDAOFRGenericInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRBusiness;
import com.premiumminds.billy.france.persistence.dao.DAOFRCustomer;
import com.premiumminds.billy.france.persistence.dao.DAOFRSupplier;
import com.premiumminds.billy.france.persistence.entities.FRGenericInvoiceEntity;
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntity;
import com.premiumminds.billy.france.services.builders.FRReceiptBuilder;
import com.premiumminds.billy.france.services.entities.FRReceipt;
import com.premiumminds.billy.france.services.entities.FRReceiptEntry;

public class FRReceiptBuilderImpl<TBuilder extends FRReceiptBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends FRReceiptEntry, TDocument extends FRReceipt>
        extends FRGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements FRReceiptBuilder<TBuilder, TEntry, TDocument> {

    public <TDAO extends AbstractDAOFRGenericInvoice<? extends TDocument>> FRReceiptBuilderImpl(TDAO daoFRReceipt,
            DAOFRBusiness daoFRBusiness, DAOFRCustomer daoFRCustomer, DAOFRSupplier daoFRSupplier) {
        super(daoFRReceipt, daoFRBusiness, daoFRCustomer, daoFRSupplier);
    }

    @Override
    protected FRReceiptEntity getTypeInstance() {
        return (FRReceiptEntity) super.getTypeInstance();
    }

    @Override
    protected void validateValues() throws ValidationException {
        FRReceiptEntity i = this.getTypeInstance();
        i.setCreditOrDebit(CreditOrDebit.CREDIT);
        super.validateValues();
    }

    @Override
    protected void validateFRInstance(FRGenericInvoiceEntity i) {
        super.validateDate();

        BillyValidator.mandatory(i.getSourceId(), FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.source"));
        BillyValidator.mandatory(i.getDate(), FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.date"));
        if (i.isSelfBilled() != null) {
            BillyValidator.mandatory(i.isSelfBilled(),
                    FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.self_billed"));
        } else {
            i.setSelfBilled(false);
        }
        BillyValidator.mandatory(i.isCancelled(), FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.cancelled"));
        BillyValidator.mandatory(i.isBilled(), FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.billed"));
        BillyValidator.notEmpty(i.getPayments(),
                FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.payment_mechanism"));
    }

}
