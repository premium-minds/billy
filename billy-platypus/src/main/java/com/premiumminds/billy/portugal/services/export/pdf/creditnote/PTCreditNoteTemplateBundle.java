/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.export.pdf.creditnote;

import java.io.InputStream;

import com.premiumminds.billy.portugal.services.export.pdf.AbstractPTTemplateBundle;
import com.premiumminds.billy.portugal.services.export.pdf.IBillyPTTemplateBundle;

public class PTCreditNoteTemplateBundle extends AbstractPTTemplateBundle implements IBillyPTTemplateBundle{

	private final String logoImagePath;
	private final InputStream xsltFileStream;
	private final String pdfFilePath;
	private final String businessEmail;
	private final String businessPhone;
	private final String businessFax;
	private final String softwareCertificationId;
	
	public PTCreditNoteTemplateBundle(
			String logoImagePath
			, InputStream xsltFileStream
			, String pdfFilePath
			, String businessEmail
			, String businessPhone
			, String businessFax
			, String softwareCertificationId) {
		this.logoImagePath =logoImagePath;
		this.xsltFileStream = xsltFileStream;
		this.pdfFilePath = pdfFilePath;
		this.businessEmail = businessEmail;
		this.businessPhone = businessPhone;
		this.businessFax = businessFax;
		this.softwareCertificationId = softwareCertificationId;
		
	}

	@Override
	public String getLogoImagePath() {
		return this.logoImagePath;
	}

	@Override
	public InputStream getXSLTFileStream() {
		return this.xsltFileStream;
	}

	@Override
	public String getResultingPdfFilePath() {
		return this.pdfFilePath;
	}

	@Override
	public String getBusinessEmailContact() {
		return this.businessEmail;
	}

	@Override
	public String getBusinessPhoneContact() {
		return this.businessPhone;
	}

	@Override
	public String getBusinessFaxContact() {
		return this.businessFax;
	}

	@Override
	public String getSoftwareCertificationId() {
		return this.softwareCertificationId;
	}
}
