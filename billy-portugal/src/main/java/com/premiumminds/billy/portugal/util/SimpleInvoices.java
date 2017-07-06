/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
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
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.PTSimpleInvoice;
import com.premiumminds.billy.portugal.services.export.PTSimpleInvoiceData;
import com.premiumminds.billy.portugal.services.export.PTSimpleInvoiceDataExtractor;
import com.premiumminds.billy.portugal.services.export.pdf.simpleinvoice.PTSimpleInvoicePDFExportRequest;
import com.premiumminds.billy.portugal.services.export.pdf.simpleinvoice.PTSimpleInvoicePDFFOPTransformer;
import com.premiumminds.billy.portugal.services.persistence.PTSimpleInvoicePersistenceService;

public class SimpleInvoices {

	private final Injector	injector;
	private final PTSimpleInvoicePersistenceService persistenceService;
	private final DocumentIssuingService issuingService;
	private final ExportService exportService;

	public SimpleInvoices(Injector injector) {
		this.injector = injector;
		this.persistenceService = getInstance(PTSimpleInvoicePersistenceService.class);
		this.issuingService = injector
				.getInstance(DocumentIssuingService.class);
		this.issuingService.addHandler(PTSimpleInvoiceEntity.class,
				this.injector.getInstance(PTSimpleInvoiceIssuingHandler.class));
		this.exportService = getInstance(ExportService.class);
		
		this.exportService.addDataExtractor(PTSimpleInvoiceData.class, getInstance(PTSimpleInvoiceDataExtractor.class));
        this.exportService.addTransformerMapper(PTSimpleInvoicePDFExportRequest.class, PTSimpleInvoicePDFFOPTransformer.class);
	}

	public PTSimpleInvoice.Builder builder() {
		return getInstance(PTSimpleInvoice.Builder.class);
	}

	public PTSimpleInvoice.Builder builder(PTSimpleInvoice customer) {
		PTSimpleInvoice.Builder builder = getInstance(PTSimpleInvoice.Builder.class);
		BuilderManager.setTypeInstance(builder, customer);
		return builder;
	}

	public PTSimpleInvoicePersistenceService persistence() {
		return this.persistenceService;
	}

	public PTSimpleInvoice issue(PTSimpleInvoice.Builder builder, PTIssuingParams params) throws DocumentIssuingException {
		return issuingService.issue(builder, params);
	}
	
	public InputStream pdfExport(PTSimpleInvoicePDFExportRequest  request) throws ExportServiceException {
		return exportService.exportToStream(request);
	}
	
	public <O> void pdfExport(UID uidDoc, BillyExportTransformer<PTSimpleInvoiceData, O> dataTransformer, O output) 
            throws ExportServiceException {
        
        exportService.export(uidDoc, dataTransformer, output);
    }

	private <T> T getInstance(Class<T> clazz) {
		return this.injector.getInstance(clazz);
	}

}
