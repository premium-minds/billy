/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.util;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.spain.services.certification.CertificationManager;

public class GenerateHash {

	public static String generateHash(@NotNull
	PrivateKey privateKey, @NotNull
	PublicKey publicKey, @NotNull
	Date invoiceDate, @NotNull
	Date systemEntryDate, @NotNull
	String invoiceNumber, @NotNull
	BigDecimal grossTotal, String previousInvoiceHash)
		throws DocumentIssuingException {

		try {
			String sourceString = GenerateHash.generateSourceHash(invoiceDate,
					systemEntryDate, invoiceNumber, grossTotal,
					previousInvoiceHash);

			CertificationManager certificationManager = new CertificationManager();
			certificationManager.setAutoVerifyHash(true);
			certificationManager.setPrivateKey(privateKey);
			certificationManager.setPublicKey(publicKey);

			return certificationManager.getHashBase64(sourceString);
		} catch (Throwable e) {
			throw new DocumentIssuingException(e);
		}
	}

	public static String generateSourceHash(Date invoiceDate,
			Date systemEntryDate, String invoiceNumber, BigDecimal grossTotal,
			String previousInvoiceHash) {

		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateTime = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");

		StringBuilder builder = new StringBuilder();
		builder.append(date.format(invoiceDate)).append(';')
				.append(dateTime.format(systemEntryDate)).append(';')
				.append(invoiceNumber).append(';')
				.append(grossTotal.setScale(BillyMathContext.SCALE, BillyMathContext.get().getRoundingMode())).append(';')
				.append(previousInvoiceHash == null ? "" : previousInvoiceHash);

		String sourceString = builder.toString();
		return sourceString;
	}

}
