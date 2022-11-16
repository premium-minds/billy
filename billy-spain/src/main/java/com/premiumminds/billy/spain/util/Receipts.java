/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.util;

import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import java.io.InputStream;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.impl.BuilderManager;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.gin.services.ExportService;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyExportTransformer;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.services.documents.ESReceiptIssuingHandler;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import com.premiumminds.billy.spain.services.entities.ESReceipt;
import com.premiumminds.billy.spain.services.entities.ESReceiptEntry;
import com.premiumminds.billy.spain.services.export.ESReceiptData;
import com.premiumminds.billy.spain.services.export.ESReceiptDataExtractor;
import com.premiumminds.billy.spain.services.export.pdf.receipt.ESReceiptPDFExportRequest;
import com.premiumminds.billy.spain.services.export.pdf.receipt.ESReceiptPDFFOPTransformer;
import com.premiumminds.billy.spain.services.persistence.ESReceiptPersistenceService;

public class Receipts {

    private final Injector injector;
    private final ESReceiptPersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public Receipts(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(ESReceiptPersistenceService.class);
        this.issuingService = this.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(ESReceiptEntity.class, this.getInstance(ESReceiptIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(ESReceiptData.class, this.getInstance(ESReceiptDataExtractor.class));
        this.exportService.addTransformerMapper(ESReceiptPDFExportRequest.class, ESReceiptPDFFOPTransformer.class);
    }

    public ESReceipt.Builder builder() {
        return this.getInstance(ESReceipt.Builder.class);
    }

    public ESReceipt.Builder builder(ESReceipt receipt) {
        ESReceipt.Builder builder = this.getInstance(ESReceipt.Builder.class);
        BuilderManager.setTypeInstance(builder, receipt);
        return builder;
    }

    public ESReceiptEntry.Builder entryBuilder() {
        return this.getInstance(ESReceiptEntry.Builder.class);
    }

    public ESReceiptEntry.Builder entryBuilder(ESReceiptEntry entry) {
        ESReceiptEntry.Builder builder = this.getInstance(ESReceiptEntry.Builder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public ESReceiptPersistenceService persistence() {
        return this.persistenceService;
    }

    public ESReceipt issue(ESReceipt.Builder builder, ESIssuingParams params)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(ESReceiptPDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(
        StringID<GenericInvoice> uidDoc,
        BillyExportTransformer<ESReceiptData, O> dataTransformer,
        O output) throws ExportServiceException
    {
        this.exportService.export(uidDoc, dataTransformer, output);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
