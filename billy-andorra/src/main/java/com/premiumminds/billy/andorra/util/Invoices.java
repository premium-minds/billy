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

import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import java.io.InputStream;

import com.google.inject.Injector;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.builders.impl.BuilderManager;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.gin.services.ExportService;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyExportTransformer;
import com.premiumminds.billy.andorra.services.documents.ADInvoiceIssuingHandler;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;
import com.premiumminds.billy.andorra.services.entities.ADInvoice;
import com.premiumminds.billy.andorra.services.entities.ADInvoiceEntry;
import com.premiumminds.billy.andorra.services.export.ADInvoiceData;
import com.premiumminds.billy.andorra.services.export.ADInvoiceDataExtractor;
import com.premiumminds.billy.andorra.services.export.pdf.invoice.ADInvoicePDFExportRequest;
import com.premiumminds.billy.andorra.services.export.pdf.invoice.ADInvoicePDFFOPTransformer;
import com.premiumminds.billy.andorra.services.persistence.ADInvoicePersistenceService;

public class Invoices {

    private final Injector injector;
    private final ADInvoicePersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public Invoices(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(ADInvoicePersistenceService.class);
        this.issuingService = injector.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(ADInvoiceEntity.class, this.injector.getInstance(ADInvoiceIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(ADInvoiceData.class, this.getInstance(ADInvoiceDataExtractor.class));
        this.exportService.addTransformerMapper(ADInvoicePDFExportRequest.class, ADInvoicePDFFOPTransformer.class);
    }

    public ADInvoice.Builder builder() {
        return this.getInstance(ADInvoice.Builder.class);
    }

    public ADInvoice.Builder builder(ADInvoice invoice) {
        ADInvoice.Builder builder = this.getInstance(ADInvoice.Builder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public ADInvoiceEntry.Builder entryBuilder() {
        return this.getInstance(ADInvoiceEntry.Builder.class);
    }

    public ADInvoiceEntry.Builder entrybuilder(ADInvoiceEntry entry) {
        ADInvoiceEntry.Builder builder = this.getInstance(ADInvoiceEntry.Builder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public ADInvoicePersistenceService persistence() {
        return this.persistenceService;
    }

    public ADInvoice issue(ADInvoice.Builder builder, ADIssuingParams params)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(ADInvoicePDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(StringID<GenericInvoice> uidDoc, BillyExportTransformer<ADInvoiceData, O> dataTransformer, O output)
            throws ExportServiceException {

        this.exportService.export(uidDoc, dataTransformer, output);
    }

    public ADInvoice.ManualBuilder manualBuilder() {
        return this.getInstance(ADInvoice.ManualBuilder.class);
    }

    public ADInvoice.ManualBuilder manualBuilder(ADInvoice invoice) {
        ADInvoice.ManualBuilder builder = this.getInstance(ADInvoice.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public ADInvoiceEntry.ManualBuilder manualEntryBuilder() {
        return this.getInstance(ADInvoiceEntry.ManualBuilder.class);
    }

    public ADInvoiceEntry.ManualBuilder manualEntrybuilder(ADInvoiceEntry entry) {
        ADInvoiceEntry.ManualBuilder builder = this.getInstance(ADInvoiceEntry.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public ADInvoice issue(ADInvoice.ManualBuilder builder, ADIssuingParams params)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        return this.issuingService.issue(builder, params);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
