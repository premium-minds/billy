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

import com.premiumminds.billy.andorra.persistence.entities.ADSimpleInvoiceEntity;
import com.premiumminds.billy.andorra.services.export.ADSimpleInvoiceData;
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
import com.premiumminds.billy.andorra.services.documents.ADSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;
import com.premiumminds.billy.andorra.services.entities.ADSimpleInvoice;
import com.premiumminds.billy.andorra.services.export.ADSimpleInvoiceDataExtractor;
import com.premiumminds.billy.andorra.services.export.pdf.simpleinvoice.ADSimpleInvoicePDFExportRequest;
import com.premiumminds.billy.andorra.services.export.pdf.simpleinvoice.impl.ADSimpleInvoicePDFFOPTransformer;
import com.premiumminds.billy.andorra.services.persistence.ADSimpleInvoicePersistenceService;

public class SimpleInvoices {

    private final Injector injector;
    private final ADSimpleInvoicePersistenceService persistenceService;
    private final DocumentIssuingService issuingService;
    private final ExportService exportService;

    public SimpleInvoices(Injector injector) {
        this.injector = injector;
        this.persistenceService = this.getInstance(ADSimpleInvoicePersistenceService.class);
        this.issuingService = injector.getInstance(DocumentIssuingService.class);
        this.issuingService.addHandler(
			ADSimpleInvoiceEntity.class,
			this.injector.getInstance(ADSimpleInvoiceIssuingHandler.class));
        this.exportService = this.getInstance(ExportService.class);

        this.exportService.addDataExtractor(
			ADSimpleInvoiceData.class,
			this.getInstance(ADSimpleInvoiceDataExtractor.class));
        this.exportService.addTransformerMapper(
			ADSimpleInvoicePDFExportRequest.class,
			ADSimpleInvoicePDFFOPTransformer.class);
    }

    public ADSimpleInvoice.Builder builder() {
        return this.getInstance(ADSimpleInvoice.Builder.class);
    }

    public ADSimpleInvoice.Builder builder(ADSimpleInvoice customer) {
        ADSimpleInvoice.Builder builder = this.getInstance(ADSimpleInvoice.Builder.class);
        BuilderManager.setTypeInstance(builder, customer);
        return builder;
    }

    public ADSimpleInvoicePersistenceService persistence() {
        return this.persistenceService;
    }

    public ADSimpleInvoice issue(ADSimpleInvoice.Builder builder, ADIssuingParams params)
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        return this.issuingService.issue(builder, params);
    }

    public InputStream pdfExport(ADSimpleInvoicePDFExportRequest request) throws ExportServiceException {
        return this.exportService.exportToStream(request);
    }

    public <O> void pdfExport(StringID<GenericInvoice> uidDoc, BillyExportTransformer<ADSimpleInvoiceData, O> dataTransformer, O output)
            throws ExportServiceException {

        this.exportService.export(uidDoc, dataTransformer, output);
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }

}
