/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy GIN.
 *
 * billy GIN is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy GIN is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy GIN. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.gin.services;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyDataExtractor;
import com.premiumminds.billy.gin.services.export.BillyExportTransformer;
import com.premiumminds.billy.gin.services.export.GenericInvoiceData;

public interface ExportService {

    <T extends ExportServiceRequest> InputStream exportToStream(T request) throws ExportServiceException;

    <T extends ExportServiceRequest> File exportToFile(T request) throws ExportServiceException;

    <T extends GenericInvoiceData> void addDataExtractor(Class<T> dataClass,
            BillyDataExtractor<T> dataExtractor);

    void addTransformerMapper(Class<? extends ExportServiceRequest> requestClazz,
            Class<? extends BillyExportTransformer<? extends GenericInvoiceData, OutputStream>> transformerClazz);

    <T extends GenericInvoiceData, O> void export(StringID<GenericInvoice> uidDoc,
            BillyExportTransformer<T, O> dataTransformer, O output) throws ExportServiceException;

}
