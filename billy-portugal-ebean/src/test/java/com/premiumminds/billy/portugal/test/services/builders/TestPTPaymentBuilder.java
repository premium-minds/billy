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
package com.premiumminds.billy.portugal.test.services.builders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTPayment;
import com.premiumminds.billy.portugal.services.entities.PTPayment;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.fixtures.MockPTPaymentEntity;

public class TestPTPaymentBuilder extends PTAbstractTest {

    /*private static final String PT_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "PTPayment.yml";

    @Test
    public void doTest() {
        MockPTPaymentEntity mock =
                this.createMockEntity(MockPTPaymentEntity.class, TestPTPaymentBuilder.PT_PAYMENT_YML);

        Mockito.when(this.getInstance(DAOPTPayment.class).getEntityInstance()).thenReturn(new MockPTPaymentEntity());

        PTPayment.Builder builder = this.getInstance(PTPayment.Builder.class);

        builder.setPaymentAmount(mock.getPaymentAmount()).setPaymentDate(mock.getPaymentDate())
                .setPaymentMethod(mock.getPaymentMethod());

        PTPayment payment = builder.build();

        Assertions.assertTrue(payment != null);
        Assertions.assertEquals(payment.getPaymentMethod(), mock.getPaymentMethod());
        Assertions.assertEquals(payment.getPaymentAmount(), mock.getPaymentAmount());
        Assertions.assertEquals(payment.getPaymentDate(), mock.getPaymentDate());
    }*/

}
