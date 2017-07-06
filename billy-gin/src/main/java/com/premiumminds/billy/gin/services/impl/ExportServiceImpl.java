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
import com.premiumminds.billy.gin.services.export.BillyPDFTransformer;
import com.premiumminds.billy.gin.services.export.GenericInvoiceData;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractExportRequest;

public class ExportServiceImpl implements ExportService {
	
	private static final Logger log = LoggerFactory.getLogger(ExportServiceImpl.class);

	private final Map<Class<? extends GenericInvoiceData>, BillyDataExtractor<? extends GenericInvoiceData>> dataExtractors;
	private final Map<Class<? extends ExportServiceRequest>, Class<? extends BillyPDFTransformer<? extends GenericInvoiceData>>> requestMapper;
	
	public ExportServiceImpl() {
		this.dataExtractors = new HashMap<Class<? extends GenericInvoiceData>, BillyDataExtractor<? extends GenericInvoiceData>>();
		this.requestMapper = new HashMap<Class<? extends ExportServiceRequest>, Class<? extends BillyPDFTransformer<? extends GenericInvoiceData>>>();
	}

	@Override
	public <T extends ExportServiceRequest> InputStream exportToStream(T request)
		throws ExportServiceException {
		try {
			return new FileInputStream(this.exportToFile(request));
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new ExportServiceException(e);
		}
	}

	@Override
	public <T extends ExportServiceRequest> File exportToFile(T request)
		throws ExportServiceException {
		if (!this.requestMapper.containsKey(request.getClass())) {
		    RuntimeException e = new RuntimeException(
					"Could not find a handler for export request : "
							+ request.getClass().getCanonicalName());
		    log.error(e.getMessage(), e);
		    throw e;
		}
		File outputFile = new File(request.getResultPath());
		if ( outputFile.exists() ) {
			throw new ExportServiceException("file exists");
		}
		
		try (OutputStream outputStream = new FileOutputStream(outputFile)) {
			exportWithTransformer(request, outputStream);
		} catch (IOException | ExportServiceException e) {
			log.error(e.getMessage(), e);
			throw new ExportServiceException(e);
		}
		return outputFile;
	}
	
	protected <T extends ExportServiceRequest> void exportWithTransformer(T request, OutputStream outputStream) throws ExportServiceException {
		Class<? extends BillyPDFTransformer<? extends GenericInvoiceData>> transformerClazz = requestMapper.get(request.getClass());
		
		//TODO: This logic should be part of the interface instead of depending of an instance type
		if (!(request instanceof AbstractExportRequest)) {
		    RuntimeException e = new RuntimeException(
                    "Could not find a handler for export request : "
                            + request.getClass().getCanonicalName());
            log.error(e.getMessage(), e);
            throw e;
		}
		AbstractExportRequest exportRequest = (AbstractExportRequest) request;
		UID uidDoc = exportRequest.getDocumentUID();
		
		try {
			Constructor<? extends BillyPDFTransformer<?>> transformerConstructor = 
					transformerClazz.getDeclaredConstructor(request.getBundle().getClass());
			
			BillyPDFTransformer<?> dataTransformer = transformerConstructor.newInstance(request.getBundle());
			doExport(uidDoc, dataTransformer, outputStream);
			
		} catch (NoSuchMethodException | SecurityException | InstantiationException | 
				IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Override
	public <T extends GenericInvoiceData> void export(UID uidDoc, BillyPDFTransformer<T> dataTransformer, OutputStream outputStream) 
			throws ExportServiceException {
		doExport(uidDoc, dataTransformer, outputStream);
	}
	
	private <T extends GenericInvoiceData> void doExport(UID uidDoc, BillyPDFTransformer<T> dataTransformer, OutputStream outputStream) 
			throws ExportServiceException {
		Class<T> clazz = dataTransformer.getTransformableClass();
		if (!dataExtractors.containsKey(clazz)) {
		    RuntimeException e = new RuntimeException(
                    "Could not find a handler for export request : "
                            + clazz.getCanonicalName());
            log.error(e.getMessage(), e);
            throw e;
		}
		
		T document = clazz.cast(dataExtractors.get(clazz).extract(uidDoc));
		dataTransformer.transform(document, outputStream);
	}
	
	@Override
	public void addHandler(Class<? extends ExportServiceRequest> requestClass,
			ExportServiceHandler handler) {
		log.warn("This method is deprecated and no longer has side effects on service execution");
	}
	
	@Override
	public <T extends GenericInvoiceData> void addDataExtractor(Class<T> dataClass, BillyDataExtractor<T> dataExtractor) {
		dataExtractors.put(dataClass, dataExtractor);
	}
	
	@Override
	public void addTransformerMapper(
			Class<? extends ExportServiceRequest> requestClazz, 
			Class<? extends BillyPDFTransformer<? extends GenericInvoiceData>> transformerClazz) {
		
		requestMapper.put(requestClazz, transformerClazz);
	}

}
