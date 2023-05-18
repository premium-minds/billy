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

import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntryEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.services.builders.ADCreditNoteEntryBuilder;
import com.premiumminds.billy.andorra.services.entities.ADCreditNoteEntry;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.exceptions.DuplicateCreditNoteException;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNoteEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;

public class ADCreditNoteEntryBuilderImpl<TBuilder extends ADCreditNoteEntryBuilderImpl<TBuilder, TEntry>,
    TEntry extends ADCreditNoteEntry>
    extends ADGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, ADInvoiceEntity, DAOADCreditNoteEntry, DAOADInvoice>
    implements ADCreditNoteEntryBuilder<TBuilder, TEntry, ADInvoiceEntity>
{

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public ADCreditNoteEntryBuilderImpl(
        DAOADCreditNoteEntry daoADCreditNoteEntry,
        DAOADInvoice daoADInvoice,
        DAOADTax daoADTax,
        DAOADProduct daoADProduct,
        DAOADRegionContext daoADRegionContext)
    {
        super(daoADCreditNoteEntry, daoADInvoice, daoADTax, daoADProduct, daoADRegionContext);
    }

    @Override
    @NotOnUpdate
    public TBuilder setReferenceUID(StringID<GenericInvoice> referenceUID) {
        BillyValidator.notNull(referenceUID,
                               ADCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.invoice_reference"));
        ADInvoiceEntity i = this.daoInvoice.get(referenceUID);
        BillyValidator.found(i, ADGenericInvoiceBuilderImpl.LOCALIZER.getString("field.invoice_reference"));
        this.getTypeInstance().setReference(i);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setReason(String reason) {
        BillyValidator.notBlank(reason, ADCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.reason"));
        this.getTypeInstance().setReason(reason);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.getTypeInstance().setCreditOrDebit(CreditOrDebit.DEBIT);

        super.validateInstance();
        ADCreditNoteEntryEntity cn = this.getTypeInstance();
        BillyValidator.mandatory(cn.getReference(),
                                 ADCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.invoice_reference"));

        BillyValidator.mandatory(cn.getReason(), ADCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.reason"));

        this.ValidateADCreditNoteEntry(cn);
    }

    private void ValidateADCreditNoteEntry(ADCreditNoteEntryEntity cn) {
        if (this.daoEntry.existsCreditNote(cn.getReference())) {
            throw new DuplicateCreditNoteException();
        }
    }

    @Override
    protected ADCreditNoteEntryEntity getTypeInstance() {
        return (ADCreditNoteEntryEntity) super.getTypeInstance();
    }

}
