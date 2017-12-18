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
package com.premiumminds.billy.france.test.services.documents;

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.documents.impl.DocumentIssuingServiceImpl;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.services.documents.FRInvoiceIssuingHandler;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.util.FRBusinessTestUtil;
import com.premiumminds.billy.france.test.util.FRInvoiceTestUtil;

public class TestDocumentIssuingService extends FRDocumentAbstractTest {

    private DocumentIssuingService service;

    @Before
    public void setUp() {

        this.service = FRAbstractTest.injector.getInstance(DocumentIssuingServiceImpl.class);
        this.service.addHandler(FRInvoiceEntity.class,
                FRAbstractTest.injector.getInstance(FRInvoiceIssuingHandler.class));

        this.parameters.setInvoiceSeries("A");
    }

    @Test
    public void testIssuingService() throws DocumentIssuingException {

        this.service.issue(new FRInvoiceTestUtil(FRAbstractTest.injector).getInvoiceBuilder(
                new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity()), this.parameters);
    }
}
