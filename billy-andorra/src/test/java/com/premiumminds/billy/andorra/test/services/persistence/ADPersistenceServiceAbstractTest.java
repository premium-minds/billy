/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.test.services.persistence;

import com.premiumminds.billy.andorra.BillyAndorra;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParamsImpl;
import com.premiumminds.billy.andorra.test.ADPersistencyAbstractTest;
import org.junit.jupiter.api.BeforeEach;

public class ADPersistenceServiceAbstractTest extends ADPersistencyAbstractTest {

    protected ADIssuingParams parameters;
    protected BillyAndorra billy;

    @BeforeEach
    public void setUpParamenters() {
        this.parameters = new ADIssuingParamsImpl();
        this.parameters.setEACCode("31400");

        this.billy = this.getInstance(BillyAndorra.class);
    }
}
