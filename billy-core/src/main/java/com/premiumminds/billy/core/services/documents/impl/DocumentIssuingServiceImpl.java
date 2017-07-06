/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.services.documents.impl;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.premiumminds.billy.core.exceptions.InvalidTicketException;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.TicketManager;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.documents.IssuingParams;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;

public class DocumentIssuingServiceImpl implements DocumentIssuingService {

  private static final Logger log = LoggerFactory.getLogger(DocumentIssuingServiceImpl.class);

  protected Map<Class<? extends GenericInvoiceEntity>, DocumentIssuingHandler> handlers;
  protected DAOGenericInvoice daoInvoice;
  protected TicketManager ticketManager;

  @Inject
  public DocumentIssuingServiceImpl(DAOGenericInvoice daoInvoice, TicketManager ticketManager) {
    this.handlers = new HashMap<Class<? extends GenericInvoiceEntity>, DocumentIssuingHandler>();
    this.daoInvoice = daoInvoice;
    this.ticketManager = ticketManager;
  }

  @Override
  public void addHandler(Class<? extends GenericInvoiceEntity> handledClass,
      DocumentIssuingHandler handler) {
    this.handlers.put(handledClass, handler);
  }

  @Override
  public synchronized <T extends GenericInvoice> T issue(final Builder<T> documentBuilder,
      final IssuingParams parameters) throws DocumentIssuingException {

    try {
      return new TransactionWrapper<T>(daoInvoice) {

        @Override
        public T runTransaction() throws Exception {
          return issueDocument(documentBuilder, parameters);
        }
      }.execute();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new DocumentIssuingException(e);
    }
  }

  @Override
  public synchronized <T extends GenericInvoice> T issue(final Builder<T> documentBuilder,
      final IssuingParams parameters, final String ticketUID) throws DocumentIssuingException {

    try {
      return new TransactionWrapper<T>(daoInvoice) {

        @Override
        public T runTransaction() throws Exception {

          if (!ticketManager.ticketIssued(ticketUID))
            throw new InvalidTicketException();

          T result = issueDocument(documentBuilder, parameters);

          ticketManager.updateTicket(new UID(ticketUID), result.getUID(), result.getDate(),
              result.getCreateTimestamp());

          return result;
        }
      }.execute();
    } catch (InvalidTicketException e) {
      throw e;
    } catch (RuntimeException e) {
      throw new DocumentIssuingException(e);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new DocumentIssuingException(e);
    }
  }

  private <T extends GenericInvoice> T issueDocument(Builder<T> documentBuilder,
      final IssuingParams parameters) throws DocumentIssuingException {

    final T document = documentBuilder.build();
    final Type[] types = document.getClass().getGenericInterfaces();
    for (Type type : types) {
      if (handlers.containsKey(type)) {
        return handlers.get(type).issue(document, parameters);
      }
    }

    throw new RuntimeException(
        "Cannot handle document : " + document.getClass().getCanonicalName());
  }
}
