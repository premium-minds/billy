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

import java.io.InputStream;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.impl.BuilderManager;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.gin.services.ExportService;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyExportTransformer;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntity;
import com.premiumminds.billy.spain.services.documents.ESCreditNoteIssuingHandler;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import com.premiumminds.billy.spain.services.entities.ESCreditNote;
import com.premiumminds.billy.spain.services.entities.ESCreditNoteEntry;
import com.premiumminds.billy.spain.services.export.ESCreditNoteData;
import com.premiumminds.billy.spain.services.export.ESCreditNoteDataExtractor;
import com.premiumminds.billy.spain.services.export.pdf.creditnote.ESCreditNotePDFExportRequest;
import com.premiumminds.billy.spain.services.export.pdf.creditnote.ESCreditNotePDFFOPTransformer;
import com.premiumminds.billy.spain.services.persistence.ESCreditNotePersistenceService;

public class CreditNotes {

    private final Injector injector;
    private final ESCreditNotePersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public CreditNotes(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(ESCreditNotePersistenceService.class);
        this.issuingService = injector.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(ESCreditNoteEntity.class,
                this.injector.getInstance(ESCreditNoteIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(ESCreditNoteData.class, this.getInstance(ESCreditNoteDataExtractor.class));
        this.exportService.addTransformerMapper(ESCreditNotePDFExportRequest.class,
                ESCreditNotePDFFOPTransformer.class);
    }

    public ESCreditNote.Builder builder() {
        return this.getInstance(ESCreditNote.Builder.class);
    }

    public ESCreditNote.Builder builder(ESCreditNote invoice) {
        ESCreditNote.Builder builder = this.getInstance(ESCreditNote.Builder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public ESCreditNoteEntry.Builder entryBuilder() {
        return this.getInstance(ESCreditNoteEntry.Builder.class);
    }

    public ESCreditNoteEntry.Builder entryBuilder(ESCreditNoteEntry entry) {
        ESCreditNoteEntry.Builder builder = this.getInstance(ESCreditNoteEntry.Builder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public ESCreditNotePersistenceService persistence() {
        return this.persistenceService;
    }

    public ESCreditNote issue(ESCreditNote.Builder builder, ESIssuingParams params) throws DocumentIssuingException {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(ESCreditNotePDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(UID uidDoc, BillyExportTransformer<ESCreditNoteData, O> dataTransformer, O output)
            throws ExportServiceException {

        this.exportService.export(uidDoc, dataTransformer, output);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

    public ESCreditNote.ManualBuilder manualBuilder() {
        return this.getInstance(ESCreditNote.ManualBuilder.class);
    }

    public ESCreditNote.ManualBuilder manualbuilder(ESCreditNote invoice) {
        ESCreditNote.ManualBuilder builder = this.getInstance(ESCreditNote.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public ESCreditNoteEntry.ManualBuilder manualEntryBuilder() {
        return this.getInstance(ESCreditNoteEntry.ManualBuilder.class);
    }

    public ESCreditNoteEntry.ManualBuilder manualEntryBuilder(ESCreditNoteEntry entry) {
        ESCreditNoteEntry.ManualBuilder builder = this.getInstance(ESCreditNoteEntry.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public ESCreditNote issue(ESCreditNote.ManualBuilder builder, ESIssuingParams params)
            throws DocumentIssuingException {
        return this.issuingService.issue(builder, params);
    }
}
