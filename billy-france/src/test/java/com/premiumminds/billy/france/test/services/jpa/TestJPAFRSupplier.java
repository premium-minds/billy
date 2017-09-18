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
package com.premiumminds.billy.france.test.services.jpa;

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.france.persistence.dao.DAOFRInvoice;
import com.premiumminds.billy.france.persistence.dao.DAOFRSupplier;
import com.premiumminds.billy.france.persistence.entities.FRSupplierEntity;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.util.FRSupplierTestUtil;

public class TestJPAFRSupplier extends FRJPAAbstractTest {

    private TransactionWrapper<Void> transaction;

    @Before
    public void setUp() {
        this.transaction = new TransactionWrapper<Void>(FRAbstractTest.injector.getInstance(DAOFRInvoice.class)) {

            @Override
            public Void runTransaction() throws Exception {
                final FRSupplierTestUtil supplier = new FRSupplierTestUtil(FRAbstractTest.injector);
                DAOFRSupplier daoFRSupplier = FRAbstractTest.injector.getInstance(DAOFRSupplier.class);

                FRSupplierEntity newSupplier = supplier.getSupplierEntity();

                daoFRSupplier.create(newSupplier);

                return null;
            }

        };
    }

    @Test
    public void doTest() throws Exception {
        FRJPAAbstractTest.execute(FRAbstractTest.injector, this.transaction);
    }

}
