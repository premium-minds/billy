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

import com.premiumminds.billy.andorra.services.entities.ADCreditNote;
import com.premiumminds.billy.andorra.services.entities.ADInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import java.io.IOException;
import java.net.MalformedURLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestInvoiceGenerationAndExport extends ADPersistenceServiceAbstractTest {

    private ADInvoice invoice;
    private ADCreditNote creditNote;

    @BeforeEach
    public void setUp() throws DocumentIssuingException, MalformedURLException {
        // this.invoice = getNewIssuedInvoice();
        // this.creditNote = getNewIssuedCreditnote(invoice);
        //
        // ESApplication.Builder appBuilder = new ESApplicationTestUtil(injector).getApplicationBuilder();
        // appUID = new UID();
        // appBuilder.setUID(appUID);
        // billy.applications().persistence().create(appBuilder);

    }

    @Test
    public void testExportInvoice() throws IOException {
        // TO USE with EOS DB
        // Calendar calendar = Calendar.getInstance();
        // calendar.set(2013, 1, 1);
        // Date tau = calendar.getTime();
        // billy.saft().export(new UID("EOS"), new UID("MAKSU_ES"), "1", tau, new Date());
    }

}
