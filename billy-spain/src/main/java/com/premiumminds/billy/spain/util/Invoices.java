/**
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
import java.io.OutputStream;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.impl.BuilderManager;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.gin.services.ExportService;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyPDFTransformer;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.documents.ESInvoiceIssuingHandler;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import com.premiumminds.billy.spain.services.entities.ESInvoice;
import com.premiumminds.billy.spain.services.entities.ESInvoiceEntry;
import com.premiumminds.billy.spain.services.export.ESInvoiceData;
import com.premiumminds.billy.spain.services.export.ESInvoiceDataExtractor;
import com.premiumminds.billy.spain.services.export.pdf.invoice.ESInvoicePDFExportRequest;
import com.premiumminds.billy.spain.services.export.pdf.invoice.ESInvoicePDFFOPTransformer;
import com.premiumminds.billy.spain.services.persistence.ESInvoicePersistenceService;

public class Invoices {

	private final Injector	injector;
	private final ESInvoicePersistenceService persistenceService;
	private final DocumentIssuingService issuingService;
	private final ExportService exportService;
	
	public Invoices(Injector injector) {
		this.injector = injector;
		this.persistenceService = getInstance(ESInvoicePersistenceService.class);
		this.issuingService = injector
				.getInstance(DocumentIssuingService.class);
		this.issuingService.addHandler(ESInvoiceEntity.class,
				this.injector.getInstance(ESInvoiceIssuingHandler.class));
		this.exportService = getInstance(ExportService.class);
		
		this.exportService.addDataExtractor(ESInvoiceData.class, getInstance(ESInvoiceDataExtractor.class));
		this.exportService.addTransformerMapper(ESInvoicePDFExportRequest.class, ESInvoicePDFFOPTransformer.class);
	}

	public ESInvoice.Builder builder() {
		return getInstance(ESInvoice.Builder.class);
	}

	public ESInvoice.Builder builder(ESInvoice invoice) {
		ESInvoice.Builder builder = getInstance(ESInvoice.Builder.class);
		BuilderManager.setTypeInstance(builder, invoice);
		return builder;
	}
	
	public ESInvoiceEntry.Builder entryBuilder() {
		return getInstance(ESInvoiceEntry.Builder.class);
	}

	public ESInvoiceEntry.Builder entrybuilder(ESInvoiceEntry entry) {
		ESInvoiceEntry.Builder builder = getInstance(ESInvoiceEntry.Builder.class);
		BuilderManager.setTypeInstance(builder, entry);
		return builder;
	}

	public ESInvoicePersistenceService persistence() {
		return this.persistenceService;
	}

	public ESInvoice issue(ESInvoice.Builder builder, ESIssuingParams params) throws DocumentIssuingException {
		return issuingService.issue(builder, params);
	}

	public InputStream pdfExport(ESInvoicePDFExportRequest  request) throws ExportServiceException {
		return exportService.exportToStream(request);
	}
	
	public void pdfExport(UID uidDoc, BillyPDFTransformer<ESInvoiceData> dataTransformer, OutputStream outputStream) 
            throws ExportServiceException {
        
        exportService.export(uidDoc, dataTransformer, outputStream);
    }
	
	public ESInvoice.ManualBuilder manualBuilder() {
		return getInstance(ESInvoice.ManualBuilder.class);
	}
	
	public ESInvoice.ManualBuilder manualBuilder(ESInvoice invoice) {
		ESInvoice.ManualBuilder builder = getInstance(ESInvoice.ManualBuilder.class);
		BuilderManager.setTypeInstance(builder, invoice);
		return builder;
	}
	
	public ESInvoiceEntry.ManualBuilder manualEntryBuilder() {
		return getInstance(ESInvoiceEntry.ManualBuilder.class);
	}
	
	public ESInvoiceEntry.ManualBuilder manualEntrybuilder(ESInvoiceEntry entry) {
		ESInvoiceEntry.ManualBuilder builder = getInstance(ESInvoiceEntry.ManualBuilder.class);
		BuilderManager.setTypeInstance(builder, entry);
		return builder;
	}
	
	public ESInvoice issue(ESInvoice.ManualBuilder builder, ESIssuingParams params) throws DocumentIssuingException {
		return issuingService.issue(builder, params);
	}
	
	private <T> T getInstance(Class<T> clazz) {
		return this.injector.getInstance(clazz);
	}
}
