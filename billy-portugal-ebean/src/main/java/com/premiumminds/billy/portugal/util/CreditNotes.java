/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.util;

import java.io.InputStream;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.impl.BuilderManager;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.gin.services.ExportService;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyExportTransformer;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.services.documents.PTCreditNoteIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.services.export.PTCreditNoteData;
import com.premiumminds.billy.portugal.services.export.PTCreditNoteDataExtractor;
import com.premiumminds.billy.portugal.services.export.pdf.creditnote.PTCreditNotePDFExportRequest;
import com.premiumminds.billy.portugal.services.export.pdf.creditnote.PTCreditNotePDFFOPTransformer;
import com.premiumminds.billy.portugal.services.persistence.PTCreditNotePersistenceService;

public class CreditNotes {

    private final Injector injector;
    private final PTCreditNotePersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public CreditNotes(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(PTCreditNotePersistenceService.class);
        this.issuingService = injector.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(PTCreditNoteEntity.class,
                this.injector.getInstance(PTCreditNoteIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(PTCreditNoteData.class, this.getInstance(PTCreditNoteDataExtractor.class));
        this.exportService.addTransformerMapper(PTCreditNotePDFExportRequest.class,
                PTCreditNotePDFFOPTransformer.class);
    }

    public PTCreditNote.Builder builder() {
        return this.getInstance(PTCreditNote.Builder.class);
    }

    public PTCreditNote.Builder builder(PTCreditNote invoice) {
        PTCreditNote.Builder builder = this.getInstance(PTCreditNote.Builder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public PTCreditNoteEntry.Builder entryBuilder() {
        return this.getInstance(PTCreditNoteEntry.Builder.class);
    }

    public PTCreditNoteEntry.Builder entryBuilder(PTCreditNoteEntry entry) {
        PTCreditNoteEntry.Builder builder = this.getInstance(PTCreditNoteEntry.Builder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public PTCreditNotePersistenceService persistence() {
        return this.persistenceService;
    }

    public PTCreditNote issue(PTCreditNote.Builder builder, PTIssuingParams params) throws DocumentIssuingException {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(PTCreditNotePDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(UID uidDoc, BillyExportTransformer<PTCreditNoteData, O> dataTransformer, O output)
            throws ExportServiceException {

        this.exportService.export(uidDoc, dataTransformer, output);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

    public PTCreditNote.ManualBuilder manualBuilder() {
        return this.getInstance(PTCreditNote.ManualBuilder.class);
    }

    public PTCreditNote.ManualBuilder manualbuilder(PTCreditNote invoice) {
        PTCreditNote.ManualBuilder builder = this.getInstance(PTCreditNote.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public PTCreditNoteEntry.ManualBuilder manualEntryBuilder() {
        return this.getInstance(PTCreditNoteEntry.ManualBuilder.class);
    }

    public PTCreditNoteEntry.ManualBuilder manualEntryBuilder(PTCreditNoteEntry entry) {
        PTCreditNoteEntry.ManualBuilder builder = this.getInstance(PTCreditNoteEntry.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public PTCreditNote issue(PTCreditNote.ManualBuilder builder, PTIssuingParams params)
            throws DocumentIssuingException {
        return this.issuingService.issue(builder, params);
    }
}
