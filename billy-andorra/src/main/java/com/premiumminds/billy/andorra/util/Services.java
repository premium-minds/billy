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
package com.premiumminds.billy.andorra.util;

import com.google.inject.Injector;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditReceiptEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADSimpleInvoiceEntity;
import com.premiumminds.billy.andorra.services.documents.ADCreditNoteIssuingHandler;
import com.premiumminds.billy.andorra.services.documents.ADCreditReceiptIssuingHandler;
import com.premiumminds.billy.andorra.services.documents.ADInvoiceIssuingHandler;
import com.premiumminds.billy.andorra.services.documents.ADReceiptIssuingHandler;
import com.premiumminds.billy.andorra.services.documents.ADSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;
import com.premiumminds.billy.andorra.services.entities.ADGenericInvoice;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.documents.impl.DocumentIssuingServiceImpl;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;

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
        this.issuingService.addHandler(ADInvoiceEntity.class, this.injector.getInstance(ADInvoiceIssuingHandler.class));
        this.issuingService.addHandler(
            ADCreditNoteEntity.class,
            this.injector.getInstance(ADCreditNoteIssuingHandler.class));
        this.issuingService.addHandler(
            ADSimpleInvoiceEntity.class,
            this.injector.getInstance(ADSimpleInvoiceIssuingHandler.class));
        this.issuingService.addHandler(ADReceiptEntity.class, this.injector.getInstance(ADReceiptIssuingHandler.class));
        this.issuingService.addHandler(
            ADCreditReceiptEntity.class,
            this.injector.getInstance(ADCreditReceiptIssuingHandler.class));
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
    public <T extends ADGenericInvoice> T issueDocument(Builder<T> builder, ADIssuingParams issuingParameters)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        return this.issuingService.issue(builder, issuingParameters);
    }

}
