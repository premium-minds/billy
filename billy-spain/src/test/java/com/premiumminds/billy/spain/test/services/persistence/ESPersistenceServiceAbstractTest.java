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
package com.premiumminds.billy.spain.test.services.persistence;

import org.junit.Before;

import com.premiumminds.billy.spain.BillySpain;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParamsImpl;
import com.premiumminds.billy.spain.test.ESPersistencyAbstractTest;
import com.premiumminds.billy.spain.util.KeyGenerator;

public class ESPersistenceServiceAbstractTest extends ESPersistencyAbstractTest {

	protected ESIssuingParams	parameters;
	protected BillySpain billy;

	@Before
	public void setUpParamenters() {
		KeyGenerator generator = new KeyGenerator(
				ESPersistencyAbstractTest.PRIVATE_KEY_DIR);

		this.parameters = new ESIssuingParamsImpl();
		this.parameters.setPrivateKey(generator.getPrivateKey());
		this.parameters.setPublicKey(generator.getPublicKey());
		this.parameters.setPrivateKeyVersion("1");
		this.parameters.setEACCode("31400");

		billy = getInstance(BillySpain.class);
	}
}
