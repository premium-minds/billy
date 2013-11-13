/**
 * Copyright (C) 2013 Premium Minds.
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
import com.premiumminds.billy.core.services.builders.impl.BuilderManager;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.gin.services.ExportService;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntity;
import com.premiumminds.billy.spain.services.documents.ESCreditNoteIssuingHandler;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import com.premiumminds.billy.spain.services.entities.ESCreditNote;
import com.premiumminds.billy.spain.services.entities.ESCreditNoteEntry;
import com.premiumminds.billy.spain.services.export.pdf.creditnote.ESCreditNotePDFExportHandler;
import com.premiumminds.billy.spain.services.export.pdf.creditnote.ESCreditNotePDFExportRequest;
import com.premiumminds.billy.spain.services.persistence.ESCreditNotePersistenceService;

public class CreditNotes {

	private final Injector	injector;
	private final ESCreditNotePersistenceService persistenceService;
	private final DocumentIssuingService issuingService;
	private final ExportService exportService;

	public CreditNotes(Injector injector) {
		this.injector = injector;
		this.persistenceService = getInstance(ESCreditNotePersistenceService.class);
		this.issuingService = injector
				.getInstance(DocumentIssuingService.class);
		this.issuingService.addHandler(ESCreditNoteEntity.class,
				this.injector.getInstance(ESCreditNoteIssuingHandler.class));
		this.exportService = getInstance(ExportService.class);
		this.exportService.addHandler(ESCreditNotePDFExportRequest.class, getInstance(ESCreditNotePDFExportHandler.class));
	}

	public ESCreditNote.Builder builder() {
		return getInstance(ESCreditNote.Builder.class);
	}

	public ESCreditNote.Builder builder(ESCreditNote invoice) {
		ESCreditNote.Builder builder = getInstance(ESCreditNote.Builder.class);
		BuilderManager.setTypeInstance(builder, invoice);
		return builder;
	}
	
	public ESCreditNoteEntry.Builder entryBuilder() {
		return getInstance(ESCreditNoteEntry.Builder.class);
	}

	public ESCreditNoteEntry.Builder entryBuilder(ESCreditNoteEntry entry) {
		ESCreditNoteEntry.Builder builder = getInstance(ESCreditNoteEntry.Builder.class);
		BuilderManager.setTypeInstance(builder, entry);
		return builder;
	}

	public ESCreditNotePersistenceService persistence() {
		return this.persistenceService;
	}

	public ESCreditNote issue(ESCreditNote.Builder builder, ESIssuingParams params) throws DocumentIssuingException {
		return issuingService.issue(builder, params);
	}

	public InputStream pdfExport(ESCreditNotePDFExportRequest request) throws ExportServiceException {
		return exportService.exportToStream(request);
	}
	
	private <T> T getInstance(Class<T> clazz) {
		return this.injector.getInstance(clazz);
	}

}
