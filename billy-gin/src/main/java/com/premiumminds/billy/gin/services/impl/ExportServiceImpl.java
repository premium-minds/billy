/**
 * Copyright (C) 2013 Premium Minds.
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
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.premiumminds.billy.gin.services.ExportService;
import com.premiumminds.billy.gin.services.ExportServiceHandler;
import com.premiumminds.billy.gin.services.ExportServiceRequest;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;

public class ExportServiceImpl implements ExportService {

	protected Map<Class<? extends ExportServiceRequest>, ExportServiceHandler>	handlers;

	public ExportServiceImpl() {
		this.handlers = new HashMap<Class<? extends ExportServiceRequest>, ExportServiceHandler>();
	}

	@Override
	public <T extends ExportServiceRequest> InputStream exportToStream(T request)
		throws ExportServiceException {
		try {
			return new FileInputStream(this.exportToFile(request));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ExportServiceException(e);
		}
	}

	@Override
	public <T extends ExportServiceRequest> File exportToFile(T request)
		throws ExportServiceException {
		if (!this.handlers.containsKey(request.getClass())) {
			throw new RuntimeException(
					"Could not find a handler for export request : "
							+ request.getClass().getCanonicalName());
		}
		File outputFile = new File(request.getResultPath());
		if ( outputFile.exists() ) {
			throw new ExportServiceException("file exists");
		}
		OutputStream outputStream=null;
		try {
			
			outputStream = new FileOutputStream(outputFile);
			this.handlers.get(request.getClass()).export(request, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExportServiceException(e);
		} catch (ExportServiceException e) {
			e.printStackTrace();
			throw new ExportServiceException(e);
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
		return outputFile;
	}

	@Override
	public void addHandler(Class<? extends ExportServiceRequest> requestClass,
			ExportServiceHandler handler) {
		this.handlers.put(requestClass, handler);
	}

}
