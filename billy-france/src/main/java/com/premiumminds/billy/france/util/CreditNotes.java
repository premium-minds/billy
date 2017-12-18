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
import com.premiumminds.billy.france.persistence.entities.FRCreditNoteEntity;
import com.premiumminds.billy.france.services.documents.FRCreditNoteIssuingHandler;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParams;
import com.premiumminds.billy.france.services.entities.FRCreditNote;
import com.premiumminds.billy.france.services.entities.FRCreditNoteEntry;
import com.premiumminds.billy.france.services.export.FRCreditNoteData;
import com.premiumminds.billy.france.services.export.FRCreditNoteDataExtractor;
import com.premiumminds.billy.france.services.export.pdf.creditnote.FRCreditNotePDFExportRequest;
import com.premiumminds.billy.france.services.export.pdf.creditnote.FRCreditNotePDFFOPTransformer;
import com.premiumminds.billy.france.services.persistence.FRCreditNotePersistenceService;

public class CreditNotes {

    private final Injector injector;
    private final FRCreditNotePersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public CreditNotes(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(FRCreditNotePersistenceService.class);
        this.issuingService = injector.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(FRCreditNoteEntity.class,
                this.injector.getInstance(FRCreditNoteIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(FRCreditNoteData.class, this.getInstance(FRCreditNoteDataExtractor.class));
        this.exportService.addTransformerMapper(FRCreditNotePDFExportRequest.class,
                FRCreditNotePDFFOPTransformer.class);
    }

    public FRCreditNote.Builder builder() {
        return this.getInstance(FRCreditNote.Builder.class);
    }

    public FRCreditNote.Builder builder(FRCreditNote invoice) {
        FRCreditNote.Builder builder = this.getInstance(FRCreditNote.Builder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public FRCreditNoteEntry.Builder entryBuilder() {
        return this.getInstance(FRCreditNoteEntry.Builder.class);
    }

    public FRCreditNoteEntry.Builder entryBuilder(FRCreditNoteEntry entry) {
        FRCreditNoteEntry.Builder builder = this.getInstance(FRCreditNoteEntry.Builder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public FRCreditNotePersistenceService persistence() {
        return this.persistenceService;
    }

    public FRCreditNote issue(FRCreditNote.Builder builder, FRIssuingParams params) throws DocumentIssuingException {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(FRCreditNotePDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(UID uidDoc, BillyExportTransformer<FRCreditNoteData, O> dataTransformer, O output)
            throws ExportServiceException {

        this.exportService.export(uidDoc, dataTransformer, output);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

    public FRCreditNote.ManualBuilder manualBuilder() {
        return this.getInstance(FRCreditNote.ManualBuilder.class);
    }

    public FRCreditNote.ManualBuilder manualbuilder(FRCreditNote invoice) {
        FRCreditNote.ManualBuilder builder = this.getInstance(FRCreditNote.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, invoice);
        return builder;
    }

    public FRCreditNoteEntry.ManualBuilder manualEntryBuilder() {
        return this.getInstance(FRCreditNoteEntry.ManualBuilder.class);
    }

    public FRCreditNoteEntry.ManualBuilder manualEntryBuilder(FRCreditNoteEntry entry) {
        FRCreditNoteEntry.ManualBuilder builder = this.getInstance(FRCreditNoteEntry.ManualBuilder.class);
        BuilderManager.setTypeInstance(builder, entry);
        return builder;
    }

    public FRCreditNote issue(FRCreditNote.ManualBuilder builder, FRIssuingParams params)
            throws DocumentIssuingException {
        return this.issuingService.issue(builder, params);
    }
}
