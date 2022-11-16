/*
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
package com.premiumminds.billy.portugal.util;

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
import com.premiumminds.billy.portugal.persistence.entities.PTReceiptInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTReceiptInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTReceiptInvoice;
import com.premiumminds.billy.portugal.services.export.PTInvoiceData;
import com.premiumminds.billy.portugal.services.export.PTInvoiceDataExtractor;
import com.premiumminds.billy.portugal.services.export.pdf.invoice.PTInvoicePDFExportRequest;
import com.premiumminds.billy.portugal.services.export.pdf.invoice.PTInvoicePDFFOPTransformer;
import com.premiumminds.billy.portugal.services.persistence.PTInvoicePersistenceService;
import java.io.InputStream;

public class ReceiptInvoices {

    private final Injector injector;
    private final PTInvoicePersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public ReceiptInvoices(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(PTInvoicePersistenceService.class);
        this.issuingService = injector.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(PTReceiptInvoiceEntity.class,
                                       this.injector.getInstance(PTReceiptInvoiceIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(PTInvoiceData.class, this.getInstance(PTInvoiceDataExtractor.class));
        this.exportService.addTransformerMapper(PTInvoicePDFExportRequest.class, PTInvoicePDFFOPTransformer.class);

    }

    public PTReceiptInvoice.Builder builder() {
        return this.getInstance(PTReceiptInvoice.Builder.class);
    }

    public PTReceiptInvoice.Builder builder(PTReceiptInvoice invoice) {
        PTReceiptInvoice.Builder builder = this.getInstance(PTReceiptInvoice.Builder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public PTInvoiceEntry.Builder entryBuilder() {
        return this.getInstance(PTInvoiceEntry.Builder.class);
    }

    public PTInvoiceEntry.Builder entryBuilder(PTInvoiceEntry entry) {
        PTInvoiceEntry.Builder builder = this.getInstance(PTInvoiceEntry.Builder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public PTInvoicePersistenceService persistence() {
        return this.persistenceService;
    }

    public PTReceiptInvoice issue(PTReceiptInvoice.Builder builder, PTIssuingParams params)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(PTInvoicePDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(StringID<GenericInvoice> uidDoc, BillyExportTransformer<PTInvoiceData, O> dataTransformer, O output)
            throws ExportServiceException {

        this.exportService.export(uidDoc, dataTransformer, output);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

}
