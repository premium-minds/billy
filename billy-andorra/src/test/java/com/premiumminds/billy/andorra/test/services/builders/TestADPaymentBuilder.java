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
package com.premiumminds.billy.andorra.test.services.builders;

import com.premiumminds.billy.andorra.persistence.dao.DAOADPayment;
import com.premiumminds.billy.andorra.services.entities.ADPayment;
import com.premiumminds.billy.andorra.services.entities.ADPayment.Builder;
import com.premiumminds.billy.andorra.test.ADAbstractTest;
import com.premiumminds.billy.andorra.test.fixtures.MockADPaymentEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;

public class TestADPaymentBuilder extends ADAbstractTest {

    private static final String AD_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "ADPayment.yml";

    @Test
    public void doTest() {
        MockADPaymentEntity mock =
                this.createMockEntity(MockADPaymentEntity.class, TestADPaymentBuilder.AD_PAYMENT_YML);

        Mockito.when(this.getInstance(DAOADPayment.class).getEntityInstance()).thenReturn(new MockADPaymentEntity());

        Builder builder = this.getInstance(Builder.class);

        builder.setPaymentAmount(mock.getPaymentAmount()).setPaymentDate(mock.getPaymentDate())
                .setPaymentMethod(mock.getPaymentMethod());

        ADPayment payment = builder.build();

        Assertions.assertTrue(payment != null);
        Assertions.assertEquals(payment.getPaymentMethod(), mock.getPaymentMethod());
        Assertions.assertEquals(payment.getPaymentAmount(), mock.getPaymentAmount());
        Assertions.assertEquals(payment.getPaymentDate(), mock.getPaymentDate());
    }

}
