/*
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
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
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
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;

public class PTManualCreditNoteEntryBuilderImpl<TBuilder extends PTManualCreditNoteEntryBuilderImpl<TBuilder, TEntry, TInvoice>
    , TEntry extends PTCreditNoteEntry, TInvoice extends PTCreditNote>
    extends PTManualEntryBuilderImpl<TBuilder, TEntry, TInvoice, DAOPTCreditNoteEntry, DAOPTInvoice>
    implements PTManualCreditNoteEntryBuilder<TBuilder, TEntry, TInvoice> {

    public PTManualCreditNoteEntryBuilderImpl(DAOPTCreditNoteEntry daoPTCreditNoteEntry, DAOPTInvoice daoPTInvoice,
            DAOPTTax daoPTTax, DAOPTProduct daoPTProduct, DAOPTRegionContext daoPTRegionContext) {
        super(daoPTCreditNoteEntry, daoPTInvoice, daoPTTax, daoPTProduct, daoPTRegionContext);
    }

    @Override
    @NotOnUpdate
    public TBuilder setReferenceUID(StringID<GenericInvoice> referenceUID) {
        BillyValidator.notNull(referenceUID,
                PTCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.invoice_reference"));
        PTInvoiceEntity i = this.daoInvoice.get(referenceUID);
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
        BillyValidator.<Object>mandatory(cn.getProduct(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.product"));
        BillyValidator.notEmpty(cn.getTaxes(), PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(cn.getTaxAmount(), PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(cn.getTaxPointDate(),
                PTGenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
        BillyValidator.mandatory(cn.getReference(),
                PTCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.invoice_reference"));

        BillyValidator.mandatory(cn.getReason(), PTCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.reason"));
    }
}
