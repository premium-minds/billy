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
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntity;
import com.premiumminds.billy.spain.services.documents.ESCreditReceiptIssuingHandler;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import com.premiumminds.billy.spain.services.entities.ESCreditReceipt;
import com.premiumminds.billy.spain.services.entities.ESCreditReceiptEntry;
import com.premiumminds.billy.spain.services.export.ESCreditReceiptData;
import com.premiumminds.billy.spain.services.export.ESCreditReceiptDataExtractor;
import com.premiumminds.billy.spain.services.export.pdf.creditreceipt.ESCreditReceiptPDFExportRequest;
import com.premiumminds.billy.spain.services.export.pdf.creditreceipt.ESCreditReceiptPDFFOPTransformer;
import com.premiumminds.billy.spain.services.persistence.ESCreditReceiptPersistenceService;

public class CreditReceipts {

    private final Injector injector;
    private final ESCreditReceiptPersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public CreditReceipts(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(ESCreditReceiptPersistenceService.class);
        this.issuingService = injector.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(ESCreditReceiptEntity.class,
                this.injector.getInstance(ESCreditReceiptIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(ESCreditReceiptData.class,
                this.getInstance(ESCreditReceiptDataExtractor.class));
        this.exportService.addTransformerMapper(ESCreditReceiptPDFExportRequest.class,
                ESCreditReceiptPDFFOPTransformer.class);
    }

    public ESCreditReceipt.Builder builder() {
        return this.getInstance(ESCreditReceipt.Builder.class);
    }

    public ESCreditReceipt.Builder builder(ESCreditReceipt invoice) {
        ESCreditReceipt.Builder builder = this.getInstance(ESCreditReceipt.Builder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public ESCreditReceiptEntry.Builder entryBuilder() {
        return this.getInstance(ESCreditReceiptEntry.Builder.class);
    }

    public ESCreditReceiptEntry.Builder entryBuilder(ESCreditReceiptEntry entry) {
        ESCreditReceiptEntry.Builder builder = this.getInstance(ESCreditReceiptEntry.Builder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public ESCreditReceiptPersistenceService persistence() {
        return this.persistenceService;
    }

    public ESCreditReceipt issue(ESCreditReceipt.Builder builder, ESIssuingParams params)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(ESCreditReceiptPDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(
        StringID<GenericInvoice> uidDoc,
        BillyExportTransformer<ESCreditReceiptData, O> dataTransformer,
        O output) throws ExportServiceException
    {
        this.exportService.export(uidDoc, dataTransformer, output);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

    public ESCreditReceipt.ManualBuilder manualBuilder() {
        return this.getInstance(ESCreditReceipt.ManualBuilder.class);
    }

    public ESCreditReceipt.ManualBuilder manualbuilder(ESCreditReceipt invoice) {
        ESCreditReceipt.ManualBuilder builder = this.getInstance(ESCreditReceipt.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public ESCreditReceiptEntry.ManualBuilder manualEntryBuilder() {
        return this.getInstance(ESCreditReceiptEntry.ManualBuilder.class);
    }

    public ESCreditReceiptEntry.ManualBuilder manualEntryBuilder(ESCreditReceiptEntry entry) {
        ESCreditReceiptEntry.ManualBuilder builder = this.getInstance(ESCreditReceiptEntry.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public ESCreditReceipt issue(ESCreditReceipt.ManualBuilder builder, ESIssuingParams params)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        return this.issuingService.issue(builder, params);
    }
}
