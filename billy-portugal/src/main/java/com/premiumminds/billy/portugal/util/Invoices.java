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
import java.io.OutputStream;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.builders.impl.BuilderManager;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.gin.services.ExportService;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyPDFTransformer;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.services.entities.PTInvoiceEntry;
import com.premiumminds.billy.portugal.services.export.PTInvoiceData;
import com.premiumminds.billy.portugal.services.export.PTInvoiceDataExtractor;
import com.premiumminds.billy.portugal.services.export.pdf.invoice.PTInvoicePDFExportRequest;
import com.premiumminds.billy.portugal.services.export.pdf.invoice.PTInvoicePDFFOPTransformer;
import com.premiumminds.billy.portugal.services.persistence.PTInvoicePersistenceService;

public class Invoices {

  private final Injector injector;
  private final PTInvoicePersistenceService persistenceService;
  private final DocumentIssuingService issuingService;
  private final ExportService exportService;

  public Invoices(Injector injector) {
    this.injector = injector;
    this.persistenceService = getInstance(PTInvoicePersistenceService.class);
    this.issuingService = injector.getInstance(DocumentIssuingService.class);
    this.issuingService.addHandler(PTInvoiceEntity.class,
        this.injector.getInstance(PTInvoiceIssuingHandler.class));
    this.exportService = getInstance(ExportService.class);

    this.exportService.addDataExtractor(PTInvoiceData.class,
        getInstance(PTInvoiceDataExtractor.class));
    this.exportService.addTransformerMapper(PTInvoicePDFExportRequest.class,
        PTInvoicePDFFOPTransformer.class);

  }

  public PTInvoice.Builder builder() {
    return getInstance(PTInvoice.Builder.class);
  }

  public PTInvoice.Builder builder(PTInvoice invoice) {
    PTInvoice.Builder builder = getInstance(PTInvoice.Builder.class);
    BuilderManager.setTypeInstance(builder, invoice);
    return builder;
  }

  public PTInvoiceEntry.Builder entryBuilder() {
    return getInstance(PTInvoiceEntry.Builder.class);
  }

  public PTInvoiceEntry.Builder entrybuilder(PTInvoiceEntry entry) {
    PTInvoiceEntry.Builder builder = getInstance(PTInvoiceEntry.Builder.class);
    BuilderManager.setTypeInstance(builder, entry);
    return builder;
  }

  public PTInvoicePersistenceService persistence() {
    return this.persistenceService;
  }

  public PTInvoice issue(PTInvoice.Builder builder, PTIssuingParams params)
      throws DocumentIssuingException {
    return issuingService.issue(builder, params);
  }

  public InputStream pdfExport(PTInvoicePDFExportRequest request) throws ExportServiceException {
    return exportService.exportToStream(request);
  }

  public void pdfExport(UID uidDoc, BillyPDFTransformer<PTInvoiceData> dataTransformer,
      OutputStream outputStream) throws ExportServiceException {

    exportService.export(uidDoc, dataTransformer, outputStream);
  }

  private <T> T getInstance(Class<T> clazz) {
    return this.injector.getInstance(clazz);
  }

  public PTInvoice.ManualBuilder manualBuilder() {
    return getInstance(PTInvoice.ManualBuilder.class);
  }

  public PTInvoice.ManualBuilder manualBuilder(PTInvoice invoice) {
    PTInvoice.ManualBuilder builder = getInstance(PTInvoice.ManualBuilder.class);
    BuilderManager.setTypeInstance(builder, invoice);
    return builder;
  }

  public PTInvoiceEntry.ManualBuilder manualEntryBuilder() {
    return getInstance(PTInvoiceEntry.ManualBuilder.class);
  }

  public PTInvoiceEntry.ManualBuilder manualEntrybuilder(PTInvoiceEntry entry) {
    PTInvoiceEntry.ManualBuilder builder = getInstance(PTInvoiceEntry.ManualBuilder.class);
    BuilderManager.setTypeInstance(builder, entry);
    return builder;
  }

  public PTInvoice issue(PTInvoice.ManualBuilder builder, PTIssuingParams params)
      throws DocumentIssuingException {
    return issuingService.issue(builder, params);
  }

}
