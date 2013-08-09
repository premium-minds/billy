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
package com.premiumminds.billy.gin.services.impl.pdf;

import java.io.InputStream;

import com.premiumminds.billy.gin.services.export.IBillyTemplateBundle;

public abstract class AbstractTemplateBundle implements IBillyTemplateBundle {

	protected final String logoImagePath;
	protected final InputStream xsltFileStream;

	public AbstractTemplateBundle(String logoImagePath,
			InputStream xsltFileStream) {

		this.logoImagePath = logoImagePath;
		this.xsltFileStream = xsltFileStream;
	}

	public String getLogoImagePath() {
		return this.logoImagePath;
	}

	public InputStream getXSLTFileStream() {
		return this.xsltFileStream;
	}

	public abstract String getPaymentMechanismTranslation(Enum<?> pmc);
}
