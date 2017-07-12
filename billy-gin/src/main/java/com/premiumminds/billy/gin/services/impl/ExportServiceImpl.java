/**
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
package com.premiumminds.billy.gin.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.gin.services.ExportService;
import com.premiumminds.billy.gin.services.ExportServiceHandler;
import com.premiumminds.billy.gin.services.ExportServiceRequest;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyDataExtractor;
import com.premiumminds.billy.gin.services.export.BillyExportTransformer;
import com.premiumminds.billy.gin.services.export.GenericInvoiceData;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractExportRequest;

public class ExportServiceImpl implements ExportService {

    private static final Logger log = LoggerFactory.getLogger(ExportServiceImpl.class);

    private final Map<Class<? extends GenericInvoiceData>, BillyDataExtractor<? extends GenericInvoiceData>> dataExtractors;
    private final Map<Class<? extends ExportServiceRequest>, Class<? extends BillyExportTransformer<? extends GenericInvoiceData, OutputStream>>> requestMapper;

    public ExportServiceImpl() {
        this.dataExtractors =
                new HashMap<>();
        this.requestMapper =
                new HashMap<>();
    }

    @Override
    public <T extends ExportServiceRequest> InputStream exportToStream(T request) throws ExportServiceException {
        try {
            return new FileInputStream(this.exportToFile(request));
        } catch (FileNotFoundException e) {
            ExportServiceImpl.log.error(e.getMessage(), e);
            throw new ExportServiceException(e);
        }
    }

    @Override
    public <T extends ExportServiceRequest> File exportToFile(T request) throws ExportServiceException {
        if (!this.requestMapper.containsKey(request.getClass())) {
            RuntimeException e = new RuntimeException(
                    "Could not find a handler for export request : " + request.getClass().getCanonicalName());
            ExportServiceImpl.log.error(e.getMessage(), e);
            throw e;
        }
        File outputFile = new File(request.getResultPath());
        if (outputFile.exists()) {
            throw new ExportServiceException("file exists");
        }

        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
            this.exportWithTransformer(request, outputStream);
        } catch (IOException | ExportServiceException e) {
            ExportServiceImpl.log.error(e.getMessage(), e);
            throw new ExportServiceException(e);
        }
        return outputFile;
    }

    protected <T extends ExportServiceRequest> void exportWithTransformer(T request, OutputStream outputStream)
            throws ExportServiceException {
        Class<? extends BillyExportTransformer<? extends GenericInvoiceData, OutputStream>> transformerClazz =
                this.requestMapper.get(request.getClass());

        // TODO: This logic should be part of the interface instead of depending of an instance type
        if (!(request instanceof AbstractExportRequest)) {
            RuntimeException e = new RuntimeException(
                    "Could not find a handler for export request : " + request.getClass().getCanonicalName());
            ExportServiceImpl.log.error(e.getMessage(), e);
            throw e;
        }
        AbstractExportRequest exportRequest = (AbstractExportRequest) request;
        UID uidDoc = exportRequest.getDocumentUID();

        try {
            Constructor<? extends BillyExportTransformer<?, OutputStream>> transformerConstructor =
                    transformerClazz.getDeclaredConstructor(request.getBundle().getClass());

            BillyExportTransformer<?, OutputStream> dataTransformer =
                    transformerConstructor.newInstance(request.getBundle());
            this.doExport(uidDoc, dataTransformer, outputStream);

        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e) {
            ExportServiceImpl.log.error(e.getMessage(), e);
        }
    }

    @Override
    public <T extends GenericInvoiceData, O> void export(UID uidDoc, BillyExportTransformer<T, O> dataTransformer,
            O output) throws ExportServiceException {
        this.doExport(uidDoc, dataTransformer, output);
    }

    private <T extends GenericInvoiceData, O> void doExport(UID uidDoc, BillyExportTransformer<T, O> dataTransformer,
            O output) throws ExportServiceException {
        Class<T> clazz = dataTransformer.getTransformableClass();
        if (!this.dataExtractors.containsKey(clazz)) {
            RuntimeException e =
                    new RuntimeException("Could not find a handler for export request : " + clazz.getCanonicalName());
            ExportServiceImpl.log.error(e.getMessage(), e);
            throw e;
        }

        T document = clazz.cast(this.dataExtractors.get(clazz).extract(uidDoc));
        dataTransformer.transform(document, output);
    }

    @Override
    public void addHandler(Class<? extends ExportServiceRequest> requestClass, ExportServiceHandler handler) {
        ExportServiceImpl.log.warn("This method is deprecated and no longer has side effects on service execution");
    }

    @Override
    public <T extends GenericInvoiceData> void addDataExtractor(Class<T> dataClass,
            BillyDataExtractor<T> dataExtractor) {
        this.dataExtractors.put(dataClass, dataExtractor);
    }

    @Override
    public void addTransformerMapper(Class<? extends ExportServiceRequest> requestClazz,
            Class<? extends BillyExportTransformer<? extends GenericInvoiceData, OutputStream>> transformerClazz) {

        this.requestMapper.put(requestClazz, transformerClazz);
    }

}
