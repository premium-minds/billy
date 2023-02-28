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

import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntryEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADCreditNoteEntry;
import com.premiumminds.billy.andorra.services.entities.ADInvoice;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.exceptions.DuplicateCreditNoteException;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNoteEntry;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADProduct;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import com.premiumminds.billy.andorra.services.builders.ADManualCreditNoteEntryBuilder;

public class ADManualCreditNoteEntryBuilderImpl<TBuilder extends ADManualCreditNoteEntryBuilderImpl<TBuilder, TEntry>
    , TEntry extends ADCreditNoteEntry>
    extends ADManualEntryBuilderImpl<TBuilder, TEntry, ADInvoiceEntity, DAOADCreditNoteEntry, DAOADInvoice>
    implements ADManualCreditNoteEntryBuilder<TBuilder, TEntry, ADInvoiceEntity>
{

    public ADManualCreditNoteEntryBuilderImpl(
		DAOADCreditNoteEntry daoESCreditNoteEntry, DAOADInvoice daoESInvoice,
		DAOADTax daoESTax, DAOADProduct daoESProduct, DAOADRegionContext daoESRegionContext) {
        super(daoESCreditNoteEntry, daoESInvoice, daoESTax, daoESProduct, daoESRegionContext);
    }

    @Override
    @NotOnUpdate
    public TBuilder setReferenceUID(StringID<GenericInvoice> referenceUID) {
        BillyValidator.notNull(referenceUID,
							   ADCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.invoice_reference"));
        ADInvoice i = this.daoInvoice.get(referenceUID);
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
    protected ADCreditNoteEntryEntity getTypeInstance() {
        return (ADCreditNoteEntryEntity) super.getTypeInstance();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        this.getTypeInstance().setCreditOrDebit(CreditOrDebit.DEBIT);

        super.validateInstance();
        ADCreditNoteEntryEntity cn = this.getTypeInstance();
        BillyValidator.mandatory(cn.getQuantity(),
								 LOCALIZER.getString("field.quantity"));
        BillyValidator.mandatory(cn.getUnitOfMeasure(),
								 LOCALIZER.getString("field.unit"));
        BillyValidator.<Object>mandatory(cn.getProduct(),
										 LOCALIZER.getString("field.product"));
        BillyValidator.notEmpty(cn.getTaxes(), LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(cn.getTaxAmount(), LOCALIZER.getString("field.tax"));
        BillyValidator.mandatory(cn.getTaxPointDate(),
								 LOCALIZER.getString("field.tax_point_date"));
        BillyValidator.mandatory(cn.getReference(),
								 ADCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.invoice_reference"));

        BillyValidator.mandatory(cn.getReason(), ADCreditNoteEntryBuilderImpl.LOCALIZER.getString("field.reason"));

        this.ValidateESCreditNoteEntry(cn);
    }

    private void ValidateESCreditNoteEntry(ADCreditNoteEntryEntity cn) {
        if (this.daoEntry.checkCreditNote(cn.getReference()) != null) {
            throw new DuplicateCreditNoteException();
        }
    }
}
