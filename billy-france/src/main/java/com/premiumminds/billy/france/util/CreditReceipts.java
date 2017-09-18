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
import com.premiumminds.billy.france.persistence.entities.FRCreditReceiptEntity;
import com.premiumminds.billy.france.services.documents.FRCreditReceiptIssuingHandler;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParams;
import com.premiumminds.billy.france.services.entities.FRCreditReceipt;
import com.premiumminds.billy.france.services.entities.FRCreditReceiptEntry;
import com.premiumminds.billy.france.services.export.FRCreditReceiptData;
import com.premiumminds.billy.france.services.export.FRCreditReceiptDataExtractor;
import com.premiumminds.billy.france.services.export.pdf.creditreceipt.FRCreditReceiptPDFExportRequest;
import com.premiumminds.billy.france.services.export.pdf.creditreceipt.FRCreditReceiptPDFFOPTransformer;
import com.premiumminds.billy.france.services.persistence.FRCreditReceiptPersistenceService;

public class CreditReceipts {

    private final Injector injector;
    private final FRCreditReceiptPersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public CreditReceipts(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(FRCreditReceiptPersistenceService.class);
        this.issuingService = injector.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(FRCreditReceiptEntity.class,
                this.injector.getInstance(FRCreditReceiptIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(FRCreditReceiptData.class,
                this.getInstance(FRCreditReceiptDataExtractor.class));
        this.exportService.addTransformerMapper(FRCreditReceiptPDFExportRequest.class,
                FRCreditReceiptPDFFOPTransformer.class);
    }

    public FRCreditReceipt.Builder builder() {
        return this.getInstance(FRCreditReceipt.Builder.class);
    }

    public FRCreditReceipt.Builder builder(FRCreditReceipt invoice) {
        FRCreditReceipt.Builder builder = this.getInstance(FRCreditReceipt.Builder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public FRCreditReceiptEntry.Builder entryBuilder() {
        return this.getInstance(FRCreditReceiptEntry.Builder.class);
    }

    public FRCreditReceiptEntry.Builder entryBuilder(FRCreditReceiptEntry entry) {
        FRCreditReceiptEntry.Builder builder = this.getInstance(FRCreditReceiptEntry.Builder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public FRCreditReceiptPersistenceService persistence() {
        return this.persistenceService;
    }

    public FRCreditReceipt issue(FRCreditReceipt.Builder builder, FRIssuingParams params)
            throws DocumentIssuingException {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(FRCreditReceiptPDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(UID uidDoc, BillyExportTransformer<FRCreditReceiptData, O> dataTransformer, O output)
            throws ExportServiceException {

        this.exportService.export(uidDoc, dataTransformer, output);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

    public FRCreditReceipt.ManualBuilder manualBuilder() {
        return this.getInstance(FRCreditReceipt.ManualBuilder.class);
    }

    public FRCreditReceipt.ManualBuilder manualbuilder(FRCreditReceipt invoice) {
        FRCreditReceipt.ManualBuilder builder = this.getInstance(FRCreditReceipt.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public FRCreditReceiptEntry.ManualBuilder manualEntryBuilder() {
        return this.getInstance(FRCreditReceiptEntry.ManualBuilder.class);
    }

    public FRCreditReceiptEntry.ManualBuilder manualEntryBuilder(FRCreditReceiptEntry entry) {
        FRCreditReceiptEntry.ManualBuilder builder = this.getInstance(FRCreditReceiptEntry.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public FRCreditReceipt issue(FRCreditReceipt.ManualBuilder builder, FRIssuingParams params)
            throws DocumentIssuingException {
        return this.issuingService.issue(builder, params);
    }
}
