/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.test.services.persistence;

import org.junit.Before;

import com.premiumminds.billy.france.BillyFrance;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParams;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParamsImpl;
import com.premiumminds.billy.france.test.FRPersistencyAbstractTest;

public class FRPersistenceServiceAbstractTest extends FRPersistencyAbstractTest {

    protected FRIssuingParams parameters;
    protected BillyFrance billy;

    @Before
    public void setUpParamenters() {
        this.parameters = new FRIssuingParamsImpl();
        this.parameters.setEACCode("31400");

        this.billy = this.getInstance(BillyFrance.class);
    }
}
