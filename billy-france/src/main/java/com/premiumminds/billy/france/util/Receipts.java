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
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntity;
import com.premiumminds.billy.france.services.documents.FRReceiptIssuingHandler;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParams;
import com.premiumminds.billy.france.services.entities.FRReceipt;
import com.premiumminds.billy.france.services.entities.FRReceiptEntry;
import com.premiumminds.billy.france.services.export.FRReceiptData;
import com.premiumminds.billy.france.services.export.FRReceiptDataExtractor;
import com.premiumminds.billy.france.services.export.pdf.receipt.FRReceiptPDFExportRequest;
import com.premiumminds.billy.france.services.export.pdf.receipt.FRReceiptPDFFOPTransformer;
import com.premiumminds.billy.france.services.persistence.FRReceiptPersistenceService;

public class Receipts {

    private final Injector injector;
    private final FRReceiptPersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public Receipts(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(FRReceiptPersistenceService.class);
        this.issuingService = this.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(FRReceiptEntity.class, this.getInstance(FRReceiptIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(FRReceiptData.class, this.getInstance(FRReceiptDataExtractor.class));
        this.exportService.addTransformerMapper(FRReceiptPDFExportRequest.class, FRReceiptPDFFOPTransformer.class);
    }

    public FRReceipt.Builder builder() {
        return this.getInstance(FRReceipt.Builder.class);
    }

    public FRReceipt.Builder builder(FRReceipt receipt) {
        FRReceipt.Builder builder = this.getInstance(FRReceipt.Builder.class);
        BuilderManager.setTypeInstance(builder, receipt);
        return builder;
    }

    public FRReceiptEntry.Builder entryBuilder() {
        return this.getInstance(FRReceiptEntry.Builder.class);
    }

    public FRReceiptEntry.Builder entryBuilder(FRReceiptEntry entry) {
        FRReceiptEntry.Builder builder = this.getInstance(FRReceiptEntry.Builder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public FRReceiptPersistenceService persistence() {
        return this.persistenceService;
    }

    public FRReceipt issue(FRReceipt.Builder builder, FRIssuingParams params) throws DocumentIssuingException {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(FRReceiptPDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(UID uidDoc, BillyExportTransformer<FRReceiptData, O> dataTransformer, O output)
            throws ExportServiceException {

        this.exportService.export(uidDoc, dataTransformer, output);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
