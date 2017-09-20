/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.portugal.persistence.dao.AbstractDAOPTGenericInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.services.builders.PTManualCreditNoteBuilder;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;

public class PTManualCreditNoteBuilderImpl<TBuilder extends PTManualCreditNoteBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends PTCreditNoteEntry, TDocument extends PTCreditNote>
        extends PTManualBuilderImpl<TBuilder, TEntry, TDocument>
        implements PTManualCreditNoteBuilder<TBuilder, TEntry, TDocument> {

    public <TDAO extends AbstractDAOPTGenericInvoice<? extends TDocument>> PTManualCreditNoteBuilderImpl(
            TDAO daoPTCreditNote, DAOPTBusiness daoPTBusiness, DAOPTCustomer daoPTCustomer,
            DAOPTSupplier daoPTSupplier) {
        super(daoPTCreditNote, daoPTBusiness, daoPTCustomer, daoPTSupplier);
    }

    @Override
    protected PTCreditNoteEntity getTypeInstance() {
        return (PTCreditNoteEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        PTCreditNoteEntity i = this.getTypeInstance();
        i.setCreditOrDebit(CreditOrDebit.DEBIT);
        super.validateInstance();
    }

}
