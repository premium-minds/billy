/*
 * Copyright (C) 2017 Premium Minds.
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
package com.premiumminds.billy.spain.test.services.documents;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.documents.impl.DocumentIssuingServiceImpl;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.services.documents.ESInvoiceIssuingHandler;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.util.ESBusinessTestUtil;
import com.premiumminds.billy.spain.test.util.ESInvoiceTestUtil;

public class TestDocumentIssuingService extends ESDocumentAbstractTest {

    private DocumentIssuingService service;

    @BeforeEach
    public void setUp() {

        this.service = ESAbstractTest.injector.getInstance(DocumentIssuingServiceImpl.class);
        this.service.addHandler(ESInvoiceEntity.class,
                ESAbstractTest.injector.getInstance(ESInvoiceIssuingHandler.class));

        this.parameters.setInvoiceSeries("A");
    }

    @Test
    public void testIssuingService() throws DocumentIssuingException {

        this.service.issue(new ESInvoiceTestUtil(ESAbstractTest.injector).getInvoiceBuilder(
                new ESBusinessTestUtil(ESAbstractTest.injector).getBusinessEntity()), this.parameters);
    }
}
