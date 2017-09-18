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
package com.premiumminds.billy.france.test.services.builders;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.france.persistence.dao.DAOFRPayment;
import com.premiumminds.billy.france.services.entities.FRPayment;
import com.premiumminds.billy.france.test.FRAbstractTest;
import com.premiumminds.billy.france.test.fixtures.MockFRPaymentEntity;

public class TestFRPaymentBuilder extends FRAbstractTest {

    private static final String FR_PAYMENT_YML = AbstractTest.YML_CONFIGS_DIR + "FRPayment.yml";

    @Test
    public void doTest() {
        MockFRPaymentEntity mock =
                this.createMockEntity(MockFRPaymentEntity.class, TestFRPaymentBuilder.FR_PAYMENT_YML);

        Mockito.when(this.getInstance(DAOFRPayment.class).getEntityInstance()).thenReturn(new MockFRPaymentEntity());

        FRPayment.Builder builder = this.getInstance(FRPayment.Builder.class);

        builder.setPaymentAmount(mock.getPaymentAmount()).setPaymentDate(mock.getPaymentDate())
                .setPaymentMethod(mock.getPaymentMethod());

        FRPayment payment = builder.build();

        Assert.assertTrue(payment != null);
        Assert.assertEquals(payment.getPaymentMethod(), mock.getPaymentMethod());
        Assert.assertEquals(payment.getPaymentAmount(), mock.getPaymentAmount());
        Assert.assertEquals(payment.getPaymentDate(), mock.getPaymentDate());
    }

}
