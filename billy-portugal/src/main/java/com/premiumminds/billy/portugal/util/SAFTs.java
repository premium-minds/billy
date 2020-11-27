/*
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTApplication;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.services.export.exceptions.SAFTPTExportException;
import com.premiumminds.billy.portugal.services.export.saftpt.PTSAFTFileGenerator;
import com.premiumminds.billy.portugal.services.export.saftpt.PTSAFTFileGenerator.SAFTVersion;

public class SAFTs {

	private static final String TMP_SAFT = System.getProperty("java.io.tmpdir") + "/saft.xml";
	private final Injector injector;
	private final PTSAFTFileGenerator generator;

	public SAFTs(Injector injector) {
		this.injector = injector;
		this.generator = this.getInstance(PTSAFTFileGenerator.class);
	}

	public InputStream export(UID uidApplication, UID uidBusiness, Date from, Date to,
							  SAFTVersion version) throws SAFTPTExportException, IOException {
		return this.export(uidApplication, uidBusiness, from, to, SAFTs.TMP_SAFT, version);
	}

	public InputStream export(UID uidApplication, UID uidBusiness, Date from, Date to,
							  SAFTVersion version, boolean validate) throws SAFTPTExportException, IOException {
		return this.export(uidApplication, uidBusiness, from, to, SAFTs.TMP_SAFT, version, validate);
	}

	public InputStream export(UID uidApplication, UID uidBusiness, Date from, Date to,
							  String resultPath, SAFTVersion version) throws SAFTPTExportException, IOException {
		return this.export(uidApplication, uidBusiness, from, to, resultPath, version, false);
	}

	public InputStream export(UID uidApplication, UID uidBusiness, Date from, Date to,
							  String resultPath, SAFTVersion version, boolean validate) throws SAFTPTExportException, IOException {
		File outputFile = new File(resultPath);
		OutputStream oStream = new FileOutputStream(outputFile);

		this.generator.generateSAFTFile(oStream, this.getInstance(DAOPTBusiness.class).get(uidBusiness),
				this.getInstance(DAOPTApplication.class).get(uidApplication), from, to, version, validate);
		IOUtils.closeQuietly(oStream);
		return new FileInputStream(outputFile);
	}

	@Deprecated
	public InputStream export(UID uidApplication, UID uidBusiness, String certificateNumber, Date from, Date to,
							  SAFTVersion version) throws SAFTPTExportException, IOException {
		return this.export(uidApplication, uidBusiness, from, to, SAFTs.TMP_SAFT, version, false);
	}

	@Deprecated
	public InputStream export(UID uidApplication, UID uidBusiness, String certificateNumber, Date from, Date to,
							  String resultPath, SAFTVersion version) throws SAFTPTExportException, IOException {
		return this.export(uidApplication, uidBusiness, from, to, resultPath, version, false);
	}

	private <T> T getInstance(Class<T> clazz) {
		return this.injector.getInstance(clazz);
	}

}
