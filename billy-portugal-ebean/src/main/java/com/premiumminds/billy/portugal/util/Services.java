/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.util;

import com.google.inject.Inject;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.documents.impl.DocumentIssuingServiceImpl;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTCreditNoteIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.PTReceiptInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.PTSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice;

public class Services {

    private DocumentIssuingService issuingService;

    private PersistenceServices persistenceService;

    private PTInvoiceIssuingHandler invoiceIssuingHandler;

    private PTCreditNoteIssuingHandler creditNoteIssuingHandler;

    private PTSimpleInvoiceIssuingHandler simpleInvoiceIssuingHandler;

    private PTReceiptInvoiceIssuingHandler receiptInvoiceIssuingHandler;

    @Inject
    public Services(DocumentIssuingServiceImpl issuingService, PersistenceServices persistenceService,
            PTInvoiceIssuingHandler invoiceIssuingHandler, PTCreditNoteIssuingHandler creditNoteIssuingHandler,
            PTSimpleInvoiceIssuingHandler simpleInvoiceIssuingHandler,
            PTReceiptInvoiceIssuingHandler receiptInvoiceIssuingHandler) {
        this.issuingService = issuingService;
        this.persistenceService = persistenceService;
        this.invoiceIssuingHandler = invoiceIssuingHandler;
        this.creditNoteIssuingHandler = creditNoteIssuingHandler;
        this.simpleInvoiceIssuingHandler = simpleInvoiceIssuingHandler;
        this.receiptInvoiceIssuingHandler = receiptInvoiceIssuingHandler;

        this.setupServices();
    }

    private void setupServices() {
        this.issuingService.addHandler(PTInvoiceEntity.class, this.invoiceIssuingHandler);
        this.issuingService.addHandler(PTCreditNoteEntity.class, this.creditNoteIssuingHandler);
        this.issuingService.addHandler(PTSimpleInvoiceEntity.class, this.simpleInvoiceIssuingHandler);
        this.issuingService.addHandler(PTReceiptInvoiceEntity.class, this.receiptInvoiceIssuingHandler);
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
     * @param <T>
     *        document type
     * @param builder
     *        of the document to issue.
     * @param issuingParameters
     *        required to issue the document.
     * @return The newly issued document
     * @throws DocumentIssuingException
     *         exception when document is not issued
     */
    public <T extends PTGenericInvoice> T issueDocument(Builder<T> builder, PTIssuingParams issuingParameters)
            throws DocumentIssuingException {
        return this.issuingService.issue(builder, issuingParameters);
    }

    public <T extends PTGenericInvoice> T issueDocument(Builder<T> builder, PTIssuingParams issuingParameters,
            String ticketUID) throws DocumentIssuingException {
        return this.issuingService.issue(builder, issuingParameters, ticketUID);
    }

}
