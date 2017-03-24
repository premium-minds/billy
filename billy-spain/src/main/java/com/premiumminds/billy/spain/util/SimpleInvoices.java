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

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.impl.BuilderManager;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.gin.services.ExportService;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.spain.persistence.entities.ESSimpleInvoiceEntity;
import com.premiumminds.billy.spain.services.documents.ESSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import com.premiumminds.billy.spain.services.entities.ESSimpleInvoice;
import com.premiumminds.billy.spain.services.export.pdf.simpleinvoice.ESSimpleInvoicePDFExportHandler;
import com.premiumminds.billy.spain.services.export.pdf.simpleinvoice.ESSimpleInvoicePDFExportRequest;
import com.premiumminds.billy.spain.services.persistence.ESSimpleInvoicePersistenceService;

public class SimpleInvoices {

	private final Injector	injector;
	private final ESSimpleInvoicePersistenceService persistenceService;
	private final DocumentIssuingService issuingService;
	private final ExportService exportService;

	public SimpleInvoices(Injector injector) {
		this.injector = injector;
		this.persistenceService = getInstance(ESSimpleInvoicePersistenceService.class);
		this.issuingService = injector
				.getInstance(DocumentIssuingService.class);
		this.issuingService.addHandler(ESSimpleInvoiceEntity.class,
				this.injector.getInstance(ESSimpleInvoiceIssuingHandler.class));
		this.exportService = getInstance(ExportService.class);
		this.exportService.addHandler(ESSimpleInvoicePDFExportRequest.class, getInstance(ESSimpleInvoicePDFExportHandler.class));
	}

	public ESSimpleInvoice.Builder builder() {
		return getInstance(ESSimpleInvoice.Builder.class);
	}

	public ESSimpleInvoice.Builder builder(ESSimpleInvoice customer) {
		ESSimpleInvoice.Builder builder = getInstance(ESSimpleInvoice.Builder.class);
		BuilderManager.setTypeInstance(builder, customer);
		return builder;
	}

	public ESSimpleInvoicePersistenceService persistence() {
		return this.persistenceService;
	}

	public ESSimpleInvoice issue(ESSimpleInvoice.Builder builder, ESIssuingParams params) throws DocumentIssuingException {
		return issuingService.issue(builder, params);
	}
	
	public InputStream pdfExport(ESSimpleInvoicePDFExportRequest  request) throws ExportServiceException {
		return exportService.exportToStream(request);
	}

	private <T> T getInstance(Class<T> clazz) {
		return this.injector.getInstance(clazz);
	}

}
