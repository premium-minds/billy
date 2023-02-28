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
import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntity;
import com.premiumminds.billy.andorra.services.documents.ADCreditNoteIssuingHandler;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;
import com.premiumminds.billy.andorra.services.entities.ADCreditNote;
import com.premiumminds.billy.andorra.services.entities.ADCreditNoteEntry;
import com.premiumminds.billy.andorra.services.export.ADCreditNoteData;
import com.premiumminds.billy.andorra.services.export.ADCreditNoteDataExtractor;
import com.premiumminds.billy.andorra.services.export.pdf.creditnote.ADCreditNotePDFExportRequest;
import com.premiumminds.billy.andorra.services.export.pdf.creditnote.ADCreditNotePDFFOPTransformer;
import com.premiumminds.billy.andorra.services.persistence.ADCreditNotePersistenceService;

public class CreditNotes {

    private final Injector injector;
    private final ADCreditNotePersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public CreditNotes(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(ADCreditNotePersistenceService.class);
        this.issuingService = injector.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(
			ADCreditNoteEntity.class,
			this.injector.getInstance(ADCreditNoteIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(ADCreditNoteData.class, this.getInstance(ADCreditNoteDataExtractor.class));
        this.exportService.addTransformerMapper(
			ADCreditNotePDFExportRequest.class,
			ADCreditNotePDFFOPTransformer.class);
    }

    public ADCreditNote.Builder builder() {
        return this.getInstance(ADCreditNote.Builder.class);
    }

    public ADCreditNote.Builder builder(ADCreditNote invoice) {
        ADCreditNote.Builder builder = this.getInstance(ADCreditNote.Builder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public ADCreditNoteEntry.Builder entryBuilder() {
        return this.getInstance(ADCreditNoteEntry.Builder.class);
    }

    public ADCreditNoteEntry.Builder entryBuilder(ADCreditNoteEntry entry) {
        ADCreditNoteEntry.Builder builder = this.getInstance(ADCreditNoteEntry.Builder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public ADCreditNotePersistenceService persistence() {
        return this.persistenceService;
    }

    public ADCreditNote issue(ADCreditNote.Builder builder, ADIssuingParams params)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(ADCreditNotePDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(StringID<GenericInvoice> uidDoc, BillyExportTransformer<ADCreditNoteData, O> dataTransformer, O output)
            throws ExportServiceException {

        this.exportService.export(uidDoc, dataTransformer, output);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

    public ADCreditNote.ManualBuilder manualBuilder() {
        return this.getInstance(ADCreditNote.ManualBuilder.class);
    }

    public ADCreditNote.ManualBuilder manualbuilder(ADCreditNote invoice) {
        ADCreditNote.ManualBuilder builder = this.getInstance(ADCreditNote.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public ADCreditNoteEntry.ManualBuilder manualEntryBuilder() {
        return this.getInstance(ADCreditNoteEntry.ManualBuilder.class);
    }

    public ADCreditNoteEntry.ManualBuilder manualEntryBuilder(ADCreditNoteEntry entry) {
        ADCreditNoteEntry.ManualBuilder builder = this.getInstance(ADCreditNoteEntry.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public ADCreditNote issue(ADCreditNote.ManualBuilder builder, ADIssuingParams params)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        return this.issuingService.issue(builder, params);
    }
}
