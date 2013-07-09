/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-gin.
 * 
 * billy-gin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-gin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-gin.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.gin.services.export;

import java.io.InputStream;


public interface IBillyTemplateBundle {
	
	/**
	 * returns the path to the image file to be used as a logo
	 * @return The path of the logo image file
	 */
	public String getLogoImagePath();

	/**
	 * returns the path to the xslt file to be used as the pdf template generator
	 * @return The path of the xslt template file.
	 */
	public InputStream getXSLTFileStream();
	
	/**
	 * returns the path for the resulting pdf file, if applicable.
	 * @return The path of the destination file.
	 */
	public String getResultingPdfFilePath();
	
	public String getBusinessEmailContact();
	public String getBusinessPhoneContact();
	public String getBusinessFaxContact();
	
	public String getSoftwareCertificationId();
}
