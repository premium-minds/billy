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
package com.premiumminds.billy.spain.persistence.entities.jpa;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.spain.Config;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.services.entities.ESPayment;
import com.premiumminds.billy.spain.services.entities.ESReceiptEntry;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "RECEIPT")
@Inheritance(strategy = InheritanceType.JOINED)
public class JPAESReceiptEntity extends JPAESGenericInvoiceEntity implements ESReceiptEntity {

  private static final long serialVersionUID = 1L;

  @SuppressWarnings("unchecked")
  @Override
  public List<ESReceiptEntry> getEntries() {
    return (List<ESReceiptEntry>) super.getEntries();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ESPayment> getPayments() {
    return (List<ESPayment>) super.getPayments();
  }
}
