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

import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntity;
import com.premiumminds.billy.andorra.services.export.ADReceiptDataExtractor;
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
import com.premiumminds.billy.andorra.services.documents.ADReceiptIssuingHandler;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;
import com.premiumminds.billy.andorra.services.entities.ADReceipt;
import com.premiumminds.billy.andorra.services.entities.ADReceiptEntry;
import com.premiumminds.billy.andorra.services.export.ADReceiptData;
import com.premiumminds.billy.andorra.services.export.pdf.receipt.ADReceiptPDFExportRequest;
import com.premiumminds.billy.andorra.services.export.pdf.receipt.ADReceiptPDFFOPTransformer;
import com.premiumminds.billy.andorra.services.persistence.ADReceiptPersistenceService;

public class Receipts {

    private final Injector injector;
    private final ADReceiptPersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public Receipts(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(ADReceiptPersistenceService.class);
        this.issuingService = this.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(ADReceiptEntity.class, this.getInstance(ADReceiptIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(ADReceiptData.class, this.getInstance(ADReceiptDataExtractor.class));
        this.exportService.addTransformerMapper(ADReceiptPDFExportRequest.class, ADReceiptPDFFOPTransformer.class);
    }

    public ADReceipt.Builder builder() {
        return this.getInstance(ADReceipt.Builder.class);
    }

    public ADReceipt.Builder builder(ADReceipt receipt) {
        ADReceipt.Builder builder = this.getInstance(ADReceipt.Builder.class);
        BuilderManager.setTypeInstance(builder, receipt);
        return builder;
    }

    public ADReceiptEntry.Builder entryBuilder() {
        return this.getInstance(ADReceiptEntry.Builder.class);
    }

    public ADReceiptEntry.Builder entryBuilder(ADReceiptEntry entry) {
        ADReceiptEntry.Builder builder = this.getInstance(ADReceiptEntry.Builder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public ADReceiptPersistenceService persistence() {
        return this.persistenceService;
    }

    public ADReceipt issue(ADReceipt.Builder builder, ADIssuingParams params)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(ADReceiptPDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(
        StringID<GenericInvoice> uidDoc,
        BillyExportTransformer<ADReceiptData, O> dataTransformer,
        O output) throws ExportServiceException
    {
        this.exportService.export(uidDoc, dataTransformer, output);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
