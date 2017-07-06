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
package com.premiumminds.billy.portugal.persistence.dao.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.mysema.query.jpa.impl.JPAQuery;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNoteEntry;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.QJPAPTCreditNoteEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;

public class DAOPTCreditNoteEntryImpl extends DAOPTGenericInvoiceEntryImpl
    implements DAOPTCreditNoteEntry {

  @Inject
  public DAOPTCreditNoteEntryImpl(Provider<EntityManager> emProvider) {
    super(emProvider);
  }

  @Override
  public PTCreditNoteEntryEntity getEntityInstance() {
    return new JPAPTCreditNoteEntryEntity();
  }

  @Override
  protected Class<JPAPTCreditNoteEntryEntity> getEntityClass() {
    return JPAPTCreditNoteEntryEntity.class;
  }

  @Override
  public PTCreditNoteEntity checkCreditNote(PTInvoice invoice) {

    QJPAPTCreditNoteEntity creditNoteEntity = QJPAPTCreditNoteEntity.jPAPTCreditNoteEntity;

    JPAQuery query = new JPAQuery(this.getEntityManager());

    query.from(creditNoteEntity);

    List<JPAPTCreditNoteEntity> allCns = query.list(creditNoteEntity);

    // TODO make a query to do this
    for (JPAPTCreditNoteEntity cne : allCns) {
      for (PTCreditNoteEntry cnee : cne.getEntries()) {
        if (cnee.getReference().getNumber().compareTo(invoice.getNumber()) == 0) {
          return cne;
        }
      }
    }
    return null;
  }
}
