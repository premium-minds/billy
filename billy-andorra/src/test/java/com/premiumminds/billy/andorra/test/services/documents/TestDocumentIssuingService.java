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
package com.premiumminds.billy.andorra.test.services.documents;

import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADInvoice.Builder;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.util.ESBusinessTestUtil;
import com.premiumminds.billy.andorra.test.util.ESInvoiceTestUtil;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.documents.impl.DocumentIssuingServiceImpl;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.andorra.services.documents.ADInvoiceIssuingHandler;

public class TestDocumentIssuingService extends ADDocumentAbstractTest {

    private DocumentIssuingService service;

    @BeforeEach
    public void setUp() {

        this.service = ADAbstractTest.injector.getInstance(DocumentIssuingServiceImpl.class);
        this.service.addHandler(
			ADInvoiceEntity.class,
			injector.getInstance(ADInvoiceIssuingHandler.class));

        this.parameters.setInvoiceSeries("A");
    }

    @Test
    public void testIssuingService()
        throws DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {

        final Builder invoiceBuilder =
            new ESInvoiceTestUtil(injector).getInvoiceBuilder(new ESBusinessTestUtil(injector).getBusinessEntity());
        this.createSeries(invoiceBuilder.build(), "A");
        this.service.issue(invoiceBuilder, this.parameters);
    }
}
