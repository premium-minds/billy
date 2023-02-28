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

import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntity;
import com.premiumminds.billy.andorra.services.entities.ADCreditNote;
import com.premiumminds.billy.andorra.services.entities.ADCreditNoteEntry;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.andorra.persistence.dao.AbstractDAOADGenericInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADBusiness;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCustomer;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;
import com.premiumminds.billy.andorra.services.builders.ADCreditNoteBuilder;

public class ADCreditNoteBuilderImpl<TBuilder extends ADCreditNoteBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends ADCreditNoteEntry, TDocument extends ADCreditNote>
        extends ADGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
        implements ADCreditNoteBuilder<TBuilder, TEntry, TDocument>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public <TDAO extends AbstractDAOADGenericInvoice<? extends TDocument>> ADCreditNoteBuilderImpl(TDAO daoESCreditNote,
																								   DAOADBusiness daoESBusiness, DAOADCustomer daoESCustomer, DAOADSupplier daoESSupplier) {
        super(daoESCreditNote, daoESBusiness, daoESCustomer, daoESSupplier);
    }

    @Override
    protected ADCreditNoteEntity getTypeInstance() {
        return (ADCreditNoteEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        ADCreditNoteEntity i = this.getTypeInstance();
        i.setCreditOrDebit(CreditOrDebit.DEBIT);
        super.validateInstance();
    }
}
