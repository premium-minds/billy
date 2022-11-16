/*
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

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.exceptions.DuplicateCreditNoteException;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNoteEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntryEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.builders.ESCreditNoteEntryBuilder;
import com.premiumminds.billy.spain.services.entities.ESCreditNoteEntry;

public class ESCreditNoteEntryBuilderImpl<TBuilder extends ESCreditNoteEntryBuilderImpl<TBuilder, TEntry>,
    TEntry extends ESCreditNoteEntry>
    extends ESGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, ESInvoiceEntity, DAOESCreditNoteEntry, DAOESInvoice>
    implements ESCreditNoteEntryBuilder<TBuilder, TEntry, ESInvoiceEntity> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public ESCreditNoteEntryBuilderImpl(DAOESCreditNoteEntry daoESCreditNoteEntry, DAOESInvoice daoESInvoice,
            DAOESTax daoESTax, DAOESProduct daoESProduct, DAOESRegionContext daoESRegionContext) {
        super(daoESCreditNoteEntry, daoESInvoice, daoESTax, daoESProduct, daoESRegionContext);
    }

    @Override
    @NotOnUpdate
    public TBuilder setReferenceUID(StringID<GenericInvoice> referenceUID) {
        BillyValidator.notNull(referenceUID,
                ESCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.invoice_reference"));
        ESInvoiceEntity i = this.daoInvoice.get(referenceUID);
        BillyValidator.found(i, ESGenericInvoiceBuilderImpl.LOCALIZER.getString("field.invoice_reference"));
        this.getTypeInstance().setReference(i);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setReason(String reason) {
        BillyValidator.notBlank(reason, ESCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.reason"));
        this.getTypeInstance().setReason(reason);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.getTypeInstance().setCreditOrDebit(CreditOrDebit.DEBIT);

        super.validateInstance();
        ESCreditNoteEntryEntity cn = this.getTypeInstance();
        BillyValidator.mandatory(cn.getReference(),
                ESCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.invoice_reference"));

        BillyValidator.mandatory(cn.getReason(), ESCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.reason"));

        this.ValidateESCreditNoteEntry(cn);
    }

    private void ValidateESCreditNoteEntry(ESCreditNoteEntryEntity cn) {
        if (this.daoEntry.checkCreditNote(cn.getReference()) != null) {
            throw new DuplicateCreditNoteException();
        }
    }

    @Override
    protected ESCreditNoteEntryEntity getTypeInstance() {
        return (ESCreditNoteEntryEntity) super.getTypeInstance();
    }

}
