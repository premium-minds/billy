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
package com.premiumminds.billy.gin.services;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyDataExtractor;
import com.premiumminds.billy.gin.services.export.BillyPDFTransformer;
import com.premiumminds.billy.gin.services.export.GenericInvoiceData;

public interface ExportService {

	public <T extends ExportServiceRequest> InputStream exportToStream(T request)
		throws ExportServiceException;

	public <T extends ExportServiceRequest> File exportToFile(T request)
		throws ExportServiceException;

	public void addHandler(Class<? extends ExportServiceRequest> requestClass,
			ExportServiceHandler handler);

	public <T extends GenericInvoiceData> void addHandler(Class<T> dataClass, 
			BillyDataExtractor<T> dataExtractor);

	public <T extends GenericInvoiceData> void export(UID uidDoc, BillyPDFTransformer<T> dataTransformer, OutputStream outputStream)
			throws ExportServiceException;

}
