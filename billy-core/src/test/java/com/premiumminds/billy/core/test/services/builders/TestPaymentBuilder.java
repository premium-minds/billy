/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.test.services.builders;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOPayment;
import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockPaymentEntity;

public class TestPaymentBuilder extends AbstractTest {

    private static final String PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "Payment.yml";

    @Test
    public void doTest() {
        MockPaymentEntity mock = this.createMockEntity(MockPaymentEntity.class, TestPaymentBuilder.PAYMENT_YML);

        Mockito.when(this.getInstance(DAOPayment.class).getEntityInstance()).thenReturn(new MockPaymentEntity());

        Payment.Builder builder = this.getInstance(Payment.Builder.class);

        builder.setPaymentDate(mock.getPaymentDate());

        Payment payment = builder.build();

        Assert.assertTrue(payment != null);
        Assert.assertEquals(payment.getPaymentDate(), mock.getPaymentDate());
    }

}
