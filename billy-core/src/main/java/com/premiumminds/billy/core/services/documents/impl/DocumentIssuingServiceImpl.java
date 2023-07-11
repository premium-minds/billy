/*
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

import com.premiumminds.billy.core.exceptions.InvalidAmountForDocumentTypeException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.documents.IssuingParams;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentIssuingServiceImpl implements DocumentIssuingService {

    private static final Logger log = LoggerFactory.getLogger(DocumentIssuingServiceImpl.class);

    protected Map<Class<? extends GenericInvoice>, DocumentIssuingHandler<? extends GenericInvoice, ?
            extends IssuingParams>>
            handlers;
    protected DAOGenericInvoice daoInvoice;

    @Inject
    public DocumentIssuingServiceImpl(DAOGenericInvoice daoInvoice) {

        this.handlers = new HashMap<>();
        this.daoInvoice = daoInvoice;
    }

    @Override
    public <T extends GenericInvoice, P extends IssuingParams> void addHandler(Class<T> handledClass,
            DocumentIssuingHandler<T, P> handler) {

        this.handlers.put(handledClass, handler);
    }

    @Override
    public synchronized <T extends GenericInvoice> T issue(final Builder<T> documentBuilder,
            final IssuingParams parameters)
            throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException {

        try {
            return new TransactionWrapper<T>(this.daoInvoice) {

                @Override
                public T runTransaction() throws Exception {
                    return DocumentIssuingServiceImpl.this.issueDocument(documentBuilder, parameters);
                }
            }.execute();
        } catch (SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException |
                 InvalidAmountForDocumentTypeException e) {
            DocumentIssuingServiceImpl.log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            DocumentIssuingServiceImpl.log.error(e.getMessage(), e);
            throw new DocumentIssuingException(e);
        }
    }

    private <T extends GenericInvoice> T issueDocument(Builder<T> documentBuilder, final IssuingParams parameters)
            throws DocumentIssuingException, DocumentSeriesDoesNotExistException, SeriesUniqueCodeNotFilled {

        final T document = documentBuilder.build();
        final Type[] types = document.getClass().getGenericInterfaces();
        for (Type type : types) {
            if (this.handlers.containsKey(type)) {
                @SuppressWarnings("unchecked") DocumentIssuingHandler<T, IssuingParams> handler =
                        (DocumentIssuingHandler<T, IssuingParams>) this.handlers.get(type);
                return handler.issue(document, parameters);
            }
        }

        throw new RuntimeException("Cannot handle document : " + document.getClass().getCanonicalName());
    }
}
