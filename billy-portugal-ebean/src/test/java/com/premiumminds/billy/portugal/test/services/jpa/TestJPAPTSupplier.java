/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.jpa;

import org.junit.Before;
import org.junit.Test;

import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTSupplierEntity;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTSupplierTestUtil;

public class TestJPAPTSupplier extends PTJPAAbstractTest {

    /*private TransactionWrapper<Void> transaction;

    @Before
    public void setUp() {
        this.transaction = new TransactionWrapper<Void>(PTAbstractTest.injector.getInstance(DAOPTInvoice.class)) {

            @Override
            public Void runTransaction() throws Exception {
                final PTSupplierTestUtil supplier = new PTSupplierTestUtil(PTAbstractTest.injector);
                DAOPTSupplier daoPTSupplier = PTAbstractTest.injector.getInstance(DAOPTSupplier.class);

                PTSupplierEntity newSupplier = supplier.getSupplierEntity();

                daoPTSupplier.create(newSupplier);

                return null;
            }

        };
    }

    @Test
    public void doTest() throws Exception {
        PTJPAAbstractTest.execute(PTAbstractTest.injector, this.transaction);
    }*/

}
