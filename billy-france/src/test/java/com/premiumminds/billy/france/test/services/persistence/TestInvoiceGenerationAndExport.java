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

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.france.services.entities.FRCreditNote;
import com.premiumminds.billy.france.services.entities.FRInvoice;

public class TestInvoiceGenerationAndExport extends FRPersistenceServiceAbstractTest {

    private FRInvoice invoice;
    private FRCreditNote creditNote;
    private UID appUID;

    @Before
    public void setUp() throws DocumentIssuingException, MalformedURLException {
        // this.invoice = getNewIssuedInvoice();
        // this.creditNote = getNewIssuedCreditnote(invoice);
        //
        // FRApplication.Builder appBuilder = new FRApplicationTestUtil(injector).getApplicationBuilder();
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
