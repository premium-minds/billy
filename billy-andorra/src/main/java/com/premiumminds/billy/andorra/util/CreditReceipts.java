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
import com.premiumminds.billy.andorra.persistence.entities.ADCreditReceiptEntity;
import com.premiumminds.billy.andorra.services.documents.ADCreditReceiptIssuingHandler;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;
import com.premiumminds.billy.andorra.services.entities.ADCreditReceipt;
import com.premiumminds.billy.andorra.services.entities.ADCreditReceiptEntry;
import com.premiumminds.billy.andorra.services.export.ADCreditReceiptData;
import com.premiumminds.billy.andorra.services.export.ADCreditReceiptDataExtractor;
import com.premiumminds.billy.andorra.services.export.pdf.creditreceipt.ADCreditReceiptPDFExportRequest;
import com.premiumminds.billy.andorra.services.export.pdf.creditreceipt.ADCreditReceiptPDFFOPTransformer;
import com.premiumminds.billy.andorra.services.persistence.ADCreditReceiptPersistenceService;

public class CreditReceipts {

    private final Injector injector;
    private final ADCreditReceiptPersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public CreditReceipts(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(ADCreditReceiptPersistenceService.class);
        this.issuingService = injector.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(
			ADCreditReceiptEntity.class,
			this.injector.getInstance(ADCreditReceiptIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(
			ADCreditReceiptData.class,
			this.getInstance(ADCreditReceiptDataExtractor.class));
        this.exportService.addTransformerMapper(
			ADCreditReceiptPDFExportRequest.class,
			ADCreditReceiptPDFFOPTransformer.class);
    }

    public ADCreditReceipt.Builder builder() {
        return this.getInstance(ADCreditReceipt.Builder.class);
    }

    public ADCreditReceipt.Builder builder(ADCreditReceipt invoice) {
        ADCreditReceipt.Builder builder = this.getInstance(ADCreditReceipt.Builder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public ADCreditReceiptEntry.Builder entryBuilder() {
        return this.getInstance(ADCreditReceiptEntry.Builder.class);
    }

    public ADCreditReceiptEntry.Builder entryBuilder(ADCreditReceiptEntry entry) {
        ADCreditReceiptEntry.Builder builder = this.getInstance(ADCreditReceiptEntry.Builder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public ADCreditReceiptPersistenceService persistence() {
        return this.persistenceService;
    }

    public ADCreditReceipt issue(ADCreditReceipt.Builder builder, ADIssuingParams params)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(ADCreditReceiptPDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(
        StringID<GenericInvoice> uidDoc,
        BillyExportTransformer<ADCreditReceiptData, O> dataTransformer,
        O output) throws ExportServiceException
    {
        this.exportService.export(uidDoc, dataTransformer, output);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

    public ADCreditReceipt.ManualBuilder manualBuilder() {
        return this.getInstance(ADCreditReceipt.ManualBuilder.class);
    }

    public ADCreditReceipt.ManualBuilder manualbuilder(ADCreditReceipt invoice) {
        ADCreditReceipt.ManualBuilder builder = this.getInstance(ADCreditReceipt.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public ADCreditReceiptEntry.ManualBuilder manualEntryBuilder() {
        return this.getInstance(ADCreditReceiptEntry.ManualBuilder.class);
    }

    public ADCreditReceiptEntry.ManualBuilder manualEntryBuilder(ADCreditReceiptEntry entry) {
        ADCreditReceiptEntry.ManualBuilder builder = this.getInstance(ADCreditReceiptEntry.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public ADCreditReceipt issue(ADCreditReceipt.ManualBuilder builder, ADIssuingParams params)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        return this.issuingService.issue(builder, params);
    }
}
