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

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.exceptions.DuplicateCreditNoteException;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.builders.PTManualCreditNoteEntryBuilder;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;

public class PTManualCreditNoteEntryBuilderImpl<TBuilder extends PTManualCreditNoteEntryBuilderImpl<TBuilder, TEntry>, TEntry extends PTCreditNoteEntry>
        extends PTManualEntryBuilderImpl<TBuilder, TEntry> implements PTManualCreditNoteEntryBuilder<TBuilder, TEntry> {

    public PTManualCreditNoteEntryBuilderImpl(DAOPTCreditNoteEntry daoPTCreditNoteEntry, DAOPTInvoice daoPTInvoice,
            DAOPTTax daoPTTax, DAOPTProduct daoPTProduct, DAOPTRegionContext daoPTRegionContext) {
        super(daoPTCreditNoteEntry, daoPTInvoice, daoPTTax, daoPTProduct, daoPTRegionContext);
    }

    @Override
    @NotOnUpdate
    public TBuilder setReferenceUID(UID referenceUID) {
        BillyValidator.notNull(referenceUID,
                PTCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.invoice_reference"));
        PTInvoiceEntity i = (PTInvoiceEntity) this.daoGenericInvoice.get(referenceUID);
        BillyValidator.found(i, PTGenericInvoiceBuilderImpl.LOCALIZER.getString("field.invoice_reference"));
        this.getTypeInstance().setReference(i);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setReason(String reason) {
        BillyValidator.notBlank(reason, PTCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.reason"));
        this.getTypeInstance().setReason(reason);
        return this.getBuilder();
    }

    @Override
    protected PTCreditNoteEntryEntity getTypeInstance() {
        return (PTCreditNoteEntryEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.getTypeInstance().setCreditOrDebit(CreditOrDebit.DEBIT);

        super.validateInstance();
        PTCreditNoteEntryEntity cn = this.getTypeInstance();
        BillyValidator.mandatory(cn.getQuantity(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.quantity"));
        BillyValidator.mandatory(cn.getUnitOfMeasure(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit"));
        BillyValidator.mandatory(cn.getProduct(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.product"));
        BillyValidator.notEmpty(cn.getTaxes(), PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(cn.getTaxAmount(), PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(cn.getTaxPointDate(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
        BillyValidator.mandatory(cn.getReference(),
                PTCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.invoice_reference"));

        BillyValidator.mandatory(cn.getReason(), PTCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.reason"));

        this.ValidatePTCreditNoteEntry(cn);
    }

    private void ValidatePTCreditNoteEntry(PTCreditNoteEntryEntity cn) {
        DAOPTCreditNoteEntry daoPTCreditNoteEntry = (DAOPTCreditNoteEntry) this.daoEntry;

        if (daoPTCreditNoteEntry.checkCreditNote(cn.getReference()) != null) {
            throw new DuplicateCreditNoteException();
        }
    }

}
