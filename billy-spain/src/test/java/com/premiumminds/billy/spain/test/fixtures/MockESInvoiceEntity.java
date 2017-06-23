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
package com.premiumminds.billy.spain.test.fixtures;

import java.util.ArrayList;
import java.util.List;

import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.entities.ESInvoiceEntry;
import com.premiumminds.billy.spain.services.entities.ESPayment;

public class MockESInvoiceEntity extends MockGenericInvoiceEntity implements ESInvoiceEntity {

  private static final long serialVersionUID = 1L;

  protected boolean cancelled;
  protected boolean billed;
  protected String eacCode;
  protected List<ESPayment> payments;

  public MockESInvoiceEntity() {
    this.payments = new ArrayList<ESPayment>();
  }

  @Override
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  @Override
  public void setBilled(boolean billed) {
    this.billed = billed;
  }

  @Override
  public boolean isCancelled() {
    return this.cancelled;
  }

  @Override
  public boolean isBilled() {
    return this.billed;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ESInvoiceEntry> getEntries() {
    return (List<ESInvoiceEntry>) (List<?>) super.getEntries();
  }

  @Override
  public void setEACCode(String eacCode) {
    this.eacCode = eacCode;
  }

  @Override
  public String getEACCode() {
    return eacCode;
  }

  @Override
  public List<ESPayment> getPayments() {
    return payments;
  }
}
