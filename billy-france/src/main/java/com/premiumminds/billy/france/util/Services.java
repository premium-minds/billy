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
package com.premiumminds.billy.france.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.documents.IssuingParams;
import com.premiumminds.billy.core.services.documents.impl.DocumentIssuingServiceImpl;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.france.persistence.entities.FRCreditNoteEntity;
import com.premiumminds.billy.france.persistence.entities.FRCreditReceiptEntity;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntity;
import com.premiumminds.billy.france.persistence.entities.FRSimpleInvoiceEntity;
import com.premiumminds.billy.france.services.documents.FRCreditNoteIssuingHandler;
import com.premiumminds.billy.france.services.documents.FRCreditReceiptIssuingHandler;
import com.premiumminds.billy.france.services.documents.FRInvoiceIssuingHandler;
import com.premiumminds.billy.france.services.documents.FRReceiptIssuingHandler;
import com.premiumminds.billy.france.services.documents.FRSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParams;
import com.premiumminds.billy.france.services.entities.FRGenericInvoice;

public class Services {

    private final Injector injector;
    private DocumentIssuingService issuingService;
    private PersistenceServices persistenceService;

    public Services(Injector injector) {
        this.injector = injector;
        this.issuingService = injector.getInstance(DocumentIssuingServiceImpl.class);
        this.persistenceService = new PersistenceServices(injector);
        this.setupServices();
    }

    private void setupServices() {
        this.issuingService.addHandler(FRInvoiceEntity.class, this.injector.getInstance(FRInvoiceIssuingHandler.class));
        this.issuingService.addHandler(FRCreditNoteEntity.class,
                this.injector.getInstance(FRCreditNoteIssuingHandler.class));
        this.issuingService.addHandler(FRSimpleInvoiceEntity.class,
                this.injector.getInstance(FRSimpleInvoiceIssuingHandler.class));
        this.issuingService.addHandler(FRReceiptEntity.class, this.injector.getInstance(FRReceiptIssuingHandler.class));
        this.issuingService.addHandler(FRCreditReceiptEntity.class,
                this.injector.getInstance(FRCreditReceiptIssuingHandler.class));
    }

    /**
     * @return {@link PersistenceServices}
     */
    public PersistenceServices entities() {
        return this.persistenceService;
    }

    /**
     * Issue a new document and store it in the database.
     *
     * @param <T> the invoice generic type
     *
     * @param builder of the document to issue.
     * @param issuingParameters required to issue the document.
     * @return The newly issued document
     * @throws DocumentIssuingException when an exception occurs during the issue
     */
    public <T extends FRGenericInvoice> T issueDocument(Builder<T> builder, FRIssuingParams issuingParameters)
            throws DocumentIssuingException {
        return this.issuingService.issue(builder, issuingParameters);
    }

    public <T extends FRGenericInvoice> T issueDocument(Builder<T> builder, FRIssuingParams issuingParameters,
            String ticketUID) throws DocumentIssuingException {
        return this.issuingService.issue(builder, issuingParameters, ticketUID);
    }

}
