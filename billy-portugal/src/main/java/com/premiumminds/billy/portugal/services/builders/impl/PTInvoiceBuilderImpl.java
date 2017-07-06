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

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.builders.PTInvoiceBuilder;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;

public class PTInvoiceBuilderImpl<TBuilder extends PTInvoiceBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends PTInvoiceEntry, TDocument extends PTInvoice>
    extends PTGenericInvoiceBuilderImpl<TBuilder, TEntry, TDocument>
    implements PTInvoiceBuilder<TBuilder, TEntry, TDocument> {

  protected static final Localizer LOCALIZER = new Localizer(
      "com/premiumminds/billy/core/i18n/FieldNames");

  @Inject
  public PTInvoiceBuilderImpl(DAOPTInvoice daoPTInvoice, DAOPTBusiness daoPTBusiness,
      DAOPTCustomer daoPTCustomer, DAOPTSupplier daoPTSupplier) {
    super(daoPTInvoice, daoPTBusiness, daoPTCustomer, daoPTSupplier);
    this.setSourceBilling(SourceBilling.P);
  }

  @Override
  protected PTInvoiceEntity getTypeInstance() {
    return (PTInvoiceEntity) super.getTypeInstance();
  }

  @Override
  protected void validateInstance() throws BillyValidationException {
    PTInvoiceEntity i = getTypeInstance();
    i.setSourceBilling(SourceBilling.P);
    i.setCreditOrDebit(CreditOrDebit.CREDIT);
    super.validateInstance();
  }

  @Override
  @NotOnUpdate
  public TBuilder setSourceBilling(SourceBilling sourceBilling) {
    switch (sourceBilling) {
    case P:
      return super.setSourceBilling(sourceBilling);
    case M:
    default:
      throw new BillyValidationException();
    }
  }
}
