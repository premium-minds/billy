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

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.exceptions.DuplicateCreditNoteException;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.france.persistence.dao.DAOFRCreditReceiptEntry;
import com.premiumminds.billy.france.persistence.dao.DAOFRProduct;
import com.premiumminds.billy.france.persistence.dao.DAOFRReceipt;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.persistence.entities.FRCreditReceiptEntryEntity;
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntity;
import com.premiumminds.billy.france.services.builders.FRCreditReceiptEntryBuilder;
import com.premiumminds.billy.france.services.entities.FRCreditReceiptEntry;

public class FRCreditReceiptEntryBuilderImpl<TBuilder extends FRCreditReceiptEntryBuilderImpl<TBuilder, TEntry>, TEntry extends FRCreditReceiptEntry>
        extends FRGenericInvoiceEntryBuilderImpl<TBuilder, TEntry, DAOFRCreditReceiptEntry, DAOFRReceipt>
        implements FRCreditReceiptEntryBuilder<TBuilder, TEntry> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    public FRCreditReceiptEntryBuilderImpl(DAOFRCreditReceiptEntry daoFRCreditReceiptEntry, DAOFRReceipt daoFRReceipt,
            DAOFRTax daoFRTax, DAOFRProduct daoFRProduct, DAOFRRegionContext daoFRRegionContext) {
        super(daoFRCreditReceiptEntry, daoFRReceipt, daoFRTax, daoFRProduct, daoFRRegionContext);
    }

    @Override
    @NotOnUpdate
    public TBuilder setReferenceUID(UID referenceUID) {
        BillyValidator.notNull(referenceUID,
                FRCreditReceiptEntryBuilderImpl.LOCALIZER.getString("field.invoice_reference"));
        FRReceiptEntity i = this.daoInvoice.get(referenceUID);
        BillyValidator.found(i, FRGenericInvoiceBuilderImpl.LOCALIZER.getString("field.invoice_reference"));
        this.getTypeInstance().setReference(i);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setReason(String reason) {
        BillyValidator.notBlank(reason, FRCreditReceiptEntryBuilderImpl.LOCALIZER.getString("field.reason"));
        this.getTypeInstance().setReason(reason);
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.getTypeInstance().setCreditOrDebit(CreditOrDebit.DEBIT);

        super.validateInstance();
        FRCreditReceiptEntryEntity cn = this.getTypeInstance();
        BillyValidator.mandatory(cn.getReference(),
                FRCreditReceiptEntryBuilderImpl.LOCALIZER.getString("field.invoice_reference"));

        BillyValidator.mandatory(cn.getReason(), FRCreditReceiptEntryBuilderImpl.LOCALIZER.getString("field.reason"));

        this.ValidateFRCreditReceiptEntry(cn);
    }

    private void ValidateFRCreditReceiptEntry(FRCreditReceiptEntryEntity cn) {
        if (this.daoEntry.checkCreditReceipt(cn.getReference()) != null) {
            throw new DuplicateCreditNoteException();
        }
    }

    @Override
    protected FRCreditReceiptEntryEntity getTypeInstance() {
        return (FRCreditReceiptEntryEntity) super.getTypeInstance();
    }

}
