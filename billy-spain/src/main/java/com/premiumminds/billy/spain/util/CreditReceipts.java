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

	private final Injector	injector;
	private final ESCreditReceiptPersistenceService persistenceService;
	private final DocumentIssuingService issuingService;
	private final ExportService exportService;

	public CreditReceipts(Injector injector) {
		this.injector = injector;
		this.persistenceService = getInstance(ESCreditReceiptPersistenceService.class);
		this.issuingService = injector
				.getInstance(DocumentIssuingService.class);
		this.issuingService.addHandler(ESCreditReceiptEntity.class,
				this.injector.getInstance(ESCreditReceiptIssuingHandler.class));
		this.exportService = getInstance(ExportService.class);
		
        this.exportService.addDataExtractor(ESCreditReceiptData.class, getInstance(ESCreditReceiptDataExtractor.class));
        this.exportService.addTransformerMapper(ESCreditReceiptPDFExportRequest.class, ESCreditReceiptPDFFOPTransformer.class);
	}

	public ESCreditReceipt.Builder builder() {
		return getInstance(ESCreditReceipt.Builder.class);
	}

	public ESCreditReceipt.Builder builder(ESCreditReceipt invoice) {
		ESCreditReceipt.Builder builder = getInstance(ESCreditReceipt.Builder.class);
		BuilderManager.setTypeInstance(builder, invoice);
		return builder;
	}
	
	public ESCreditReceiptEntry.Builder entryBuilder() {
		return getInstance(ESCreditReceiptEntry.Builder.class);
	}

	public ESCreditReceiptEntry.Builder entryBuilder(ESCreditReceiptEntry entry) {
		ESCreditReceiptEntry.Builder builder = getInstance(ESCreditReceiptEntry.Builder.class);
		BuilderManager.setTypeInstance(builder, entry);
		return builder;
	}

	public ESCreditReceiptPersistenceService persistence() {
		return this.persistenceService;
	}

	public ESCreditReceipt issue(ESCreditReceipt.Builder builder, ESIssuingParams params) throws DocumentIssuingException {
		return issuingService.issue(builder, params);
	}

	public InputStream pdfExport(ESCreditReceiptPDFExportRequest request) throws ExportServiceException {
		return exportService.exportToStream(request);
	}
	
	public void pdfExport(UID uidDoc, BillyPDFTransformer<ESCreditReceiptData> dataTransformer, OutputStream outputStream) 
            throws ExportServiceException {

        exportService.export(uidDoc, dataTransformer, outputStream);
    }
	
	private <T> T getInstance(Class<T> clazz) {
		return this.injector.getInstance(clazz);
	}
	

	public ESCreditReceipt.ManualBuilder manualBuilder() {
		return getInstance(ESCreditReceipt.ManualBuilder.class);
	}
	
	public ESCreditReceipt.ManualBuilder manualbuilder(ESCreditReceipt invoice) {
		ESCreditReceipt.ManualBuilder builder = getInstance(ESCreditReceipt.ManualBuilder.class);
		BuilderManager.setTypeInstance(builder, invoice);
		return builder;
	}
	
	public ESCreditReceiptEntry.ManualBuilder manualEntryBuilder() {
		return getInstance(ESCreditReceiptEntry.ManualBuilder.class);
	}
	
	public ESCreditReceiptEntry.ManualBuilder manualEntryBuilder(ESCreditReceiptEntry entry) {
		ESCreditReceiptEntry.ManualBuilder builder = getInstance(ESCreditReceiptEntry.ManualBuilder.class);
		BuilderManager.setTypeInstance(builder, entry);
		return builder;
	}
	
	public ESCreditReceipt issue(ESCreditReceipt.ManualBuilder builder, ESIssuingParams params) throws DocumentIssuingException {
		return issuingService.issue(builder, params);
	}
}
