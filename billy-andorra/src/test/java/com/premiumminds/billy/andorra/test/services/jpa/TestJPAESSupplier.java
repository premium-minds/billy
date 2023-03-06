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
package com.premiumminds.billy.andorra.test.services.jpa;

import com.premiumminds.billy.andorra.persistence.entities.ADSupplierEntity;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.util.ADSupplierTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.dao.DAOADSupplier;

public class TestJPAESSupplier extends ESJPAAbstractTest {

    private TransactionWrapper<Void> transaction;

    @BeforeEach
    public void setUp() {
        this.transaction = new TransactionWrapper<Void>(ADAbstractTest.injector.getInstance(DAOADInvoice.class)) {

            @Override
            public Void runTransaction() throws Exception {
                final ADSupplierTestUtil supplier = new ADSupplierTestUtil(ADAbstractTest.injector);
                DAOADSupplier daoADSupplier = ADAbstractTest.injector.getInstance(DAOADSupplier.class);

                ADSupplierEntity newSupplier = supplier.getSupplierEntity();

                daoADSupplier.create(newSupplier);

                return null;
            }

        };
    }

    @Test
    public void doTest() throws Exception {
        ESJPAAbstractTest.execute(ADAbstractTest.injector, this.transaction);
    }

}
