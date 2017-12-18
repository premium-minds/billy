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
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.services.documents.FRInvoiceIssuingHandler;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParams;
import com.premiumminds.billy.france.services.entities.FRInvoice;
import com.premiumminds.billy.france.services.entities.FRInvoiceEntry;
import com.premiumminds.billy.france.services.export.FRInvoiceData;
import com.premiumminds.billy.france.services.export.FRInvoiceDataExtractor;
import com.premiumminds.billy.france.services.export.pdf.invoice.FRInvoicePDFExportRequest;
import com.premiumminds.billy.france.services.export.pdf.invoice.FRInvoicePDFFOPTransformer;
import com.premiumminds.billy.france.services.persistence.FRInvoicePersistenceService;

public class Invoices {

    private final Injector injector;
    private final FRInvoicePersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public Invoices(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(FRInvoicePersistenceService.class);
        this.issuingService = injector.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(FRInvoiceEntity.class, this.injector.getInstance(FRInvoiceIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(FRInvoiceData.class, this.getInstance(FRInvoiceDataExtractor.class));
        this.exportService.addTransformerMapper(FRInvoicePDFExportRequest.class, FRInvoicePDFFOPTransformer.class);
    }

    public FRInvoice.Builder builder() {
        return this.getInstance(FRInvoice.Builder.class);
    }

    public FRInvoice.Builder builder(FRInvoice invoice) {
        FRInvoice.Builder builder = this.getInstance(FRInvoice.Builder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public FRInvoiceEntry.Builder entryBuilder() {
        return this.getInstance(FRInvoiceEntry.Builder.class);
    }

    public FRInvoiceEntry.Builder entrybuilder(FRInvoiceEntry entry) {
        FRInvoiceEntry.Builder builder = this.getInstance(FRInvoiceEntry.Builder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public FRInvoicePersistenceService persistence() {
        return this.persistenceService;
    }

    public FRInvoice issue(FRInvoice.Builder builder, FRIssuingParams params) throws DocumentIssuingException {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(FRInvoicePDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(UID uidDoc, BillyExportTransformer<FRInvoiceData, O> dataTransformer, O output)
            throws ExportServiceException {

        this.exportService.export(uidDoc, dataTransformer, output);
    }

    public FRInvoice.ManualBuilder manualBuilder() {
        return this.getInstance(FRInvoice.ManualBuilder.class);
    }

    public FRInvoice.ManualBuilder manualBuilder(FRInvoice invoice) {
        FRInvoice.ManualBuilder builder = this.getInstance(FRInvoice.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public FRInvoiceEntry.ManualBuilder manualEntryBuilder() {
        return this.getInstance(FRInvoiceEntry.ManualBuilder.class);
    }

    public FRInvoiceEntry.ManualBuilder manualEntrybuilder(FRInvoiceEntry entry) {
        FRInvoiceEntry.ManualBuilder builder = this.getInstance(FRInvoiceEntry.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public FRInvoice issue(FRInvoice.ManualBuilder builder, FRIssuingParams params) throws DocumentIssuingException {
        return this.issuingService.issue(builder, params);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
