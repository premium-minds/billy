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
package com.premiumminds.billy.spain.test.services.builders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.spain.persistence.dao.DAOESPayment;
import com.premiumminds.billy.spain.services.entities.ESPayment;
import com.premiumminds.billy.spain.test.ESAbstractTest;
import com.premiumminds.billy.spain.test.fixtures.MockESPaymentEntity;

public class TestESPaymentBuilder extends ESAbstractTest {

    private static final String ES_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "ESPayment.yml";

    @Test
    public void doTest() {
        MockESPaymentEntity mock =
                this.createMockEntity(MockESPaymentEntity.class, TestESPaymentBuilder.ES_PAYMENT_YML);

        Mockito.when(this.getInstance(DAOESPayment.class).getEntityInstance()).thenReturn(new MockESPaymentEntity());

        ESPayment.Builder builder = this.getInstance(ESPayment.Builder.class);

        builder.setPaymentAmount(mock.getPaymentAmount()).setPaymentDate(mock.getPaymentDate())
                .setPaymentMethod(mock.getPaymentMethod());

        ESPayment payment = builder.build();

        Assertions.assertTrue(payment != null);
        Assertions.assertEquals(payment.getPaymentMethod(), mock.getPaymentMethod());
        Assertions.assertEquals(payment.getPaymentAmount(), mock.getPaymentAmount());
        Assertions.assertEquals(payment.getPaymentDate(), mock.getPaymentDate());
    }

}
