/**
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
package com.premiumminds.billy.portugal.test.services.persistence;

import org.junit.Before;

import com.premiumminds.billy.portugal.BillyPortugal;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParamsImpl;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.util.KeyGenerator;

public class PTPersistenceServiceAbstractTest extends PTPersistencyAbstractTest {

	protected PTIssuingParams	parameters;
	protected BillyPortugal billy;

	@Before
	public void setUpParamenters() {
		KeyGenerator generator = new KeyGenerator(
				PTPersistencyAbstractTest.PRIVATE_KEY_DIR);

		this.parameters = new PTIssuingParamsImpl();
		this.parameters.setPrivateKey(generator.getPrivateKey());
		this.parameters.setPublicKey(generator.getPublicKey());
		this.parameters.setPrivateKeyVersion("1");
		this.parameters.setEACCode("31400");

		billy = getInstance(BillyPortugal.class);
	}
}
