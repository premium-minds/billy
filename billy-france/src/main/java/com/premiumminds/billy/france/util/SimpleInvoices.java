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

import java.io.InputStream;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.impl.BuilderManager;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.gin.services.ExportService;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyExportTransformer;
import com.premiumminds.billy.france.persistence.entities.FRSimpleInvoiceEntity;
import com.premiumminds.billy.france.services.documents.FRSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParams;
import com.premiumminds.billy.france.services.entities.FRSimpleInvoice;
import com.premiumminds.billy.france.services.export.FRSimpleInvoiceData;
import com.premiumminds.billy.france.services.export.FRSimpleInvoiceDataExtractor;
import com.premiumminds.billy.france.services.export.pdf.simpleinvoice.FRSimpleInvoicePDFExportRequest;
import com.premiumminds.billy.france.services.export.pdf.simpleinvoice.impl.FRSimpleInvoicePDFFOPTransformer;
import com.premiumminds.billy.france.services.persistence.FRSimpleInvoicePersistenceService;

public class SimpleInvoices {

    private final Injector injector;
    private final FRSimpleInvoicePersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public SimpleInvoices(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(FRSimpleInvoicePersistenceService.class);
        this.issuingService = injector.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(FRSimpleInvoiceEntity.class,
                this.injector.getInstance(FRSimpleInvoiceIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(FRSimpleInvoiceData.class,
                this.getInstance(FRSimpleInvoiceDataExtractor.class));
        this.exportService.addTransformerMapper(FRSimpleInvoicePDFExportRequest.class,
                FRSimpleInvoicePDFFOPTransformer.class);
    }

    public FRSimpleInvoice.Builder builder() {
        return this.getInstance(FRSimpleInvoice.Builder.class);
    }

    public FRSimpleInvoice.Builder builder(FRSimpleInvoice customer) {
        FRSimpleInvoice.Builder builder = this.getInstance(FRSimpleInvoice.Builder.class);
        BuilderManager.setTypeInstance(builder, customer);
        return builder;
    }

    public FRSimpleInvoicePersistenceService persistence() {
        return this.persistenceService;
    }

    public FRSimpleInvoice issue(FRSimpleInvoice.Builder builder, FRIssuingParams params)
            throws DocumentIssuingException {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(FRSimpleInvoicePDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(UID uidDoc, BillyExportTransformer<FRSimpleInvoiceData, O> dataTransformer, O output)
            throws ExportServiceException {

        this.exportService.export(uidDoc, dataTransformer, output);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

}
