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

import java.util.Date;

import javax.validation.ValidationException;

import org.apache.commons.lang3.time.DateUtils;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.builders.impl.GenericInvoiceEntryBuilderImpl;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.spain.persistence.dao.DAOESGenericInvoiceEntry;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.persistence.dao.DAOESProduct;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntryEntity;
import com.premiumminds.billy.spain.services.builders.ESManualInvoiceEntryBuilder;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoiceEntry;

public class ESManualInvoiceEntryBuilderImpl<TBuilder extends ESManualInvoiceEntryBuilderImpl<TBuilder, TEntry>, TEntry extends ESGenericInvoiceEntry>
    extends ESManualEntryBuilderImpl<TBuilder, TEntry>
    implements ESManualInvoiceEntryBuilder<TBuilder, TEntry> {

  public ESManualInvoiceEntryBuilderImpl(DAOESGenericInvoiceEntry daoESEntry,
      DAOESInvoice daoESInvoice, DAOESTax daoESTax, DAOESProduct daoESProduct,
      DAOESRegionContext daoESRegionContext) {
    super(daoESEntry, daoESInvoice, daoESTax, daoESProduct, daoESRegionContext);
  }

  @Override
  protected ESInvoiceEntryEntity getTypeInstance() {
    return (ESInvoiceEntryEntity) super.getTypeInstance();
  }

  @Override
  protected void validateInstance() throws BillyValidationException {
    getTypeInstance().setCreditOrDebit(CreditOrDebit.CREDIT);
    this.validateValues();
    super.validateInstance();
  }

  @Override
  protected void validateValues() throws BillyValidationException {
    GenericInvoiceEntryEntity e = this.getTypeInstance();
    for (Tax t : e.getProduct().getTaxes()) {
      if (this.daoContext.isSubContext(t.getContext(), this.context)) {
        Date taxDate = e.getTaxPointDate() == null ? new Date() : e.getTaxPointDate();
        if (DateUtils.isSameDay(t.getValidTo(), taxDate) || t.getValidTo().after(taxDate)) {
          e.getTaxes().add(t);
        }
      }
    }
    if (e.getTaxes().isEmpty()) {
      throw new ValidationException(
          GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("exception.invalid_taxes"));
    }
  }
}
