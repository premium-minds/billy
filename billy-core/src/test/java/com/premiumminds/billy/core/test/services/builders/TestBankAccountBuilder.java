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

import com.premiumminds.billy.core.persistence.dao.DAOBankAccount;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockBankAccountEntity;

public class TestBankAccountBuilder extends AbstractTest {

	private static final String	BANK_ACCOUNT_YML	= AbstractTest.YML_CONFIGS_DIR
															+ "BankAccount.yml";

	@Test
	public void doTest() {
		MockBankAccountEntity mockBankAccount = this.createMockEntity(
				MockBankAccountEntity.class,
				TestBankAccountBuilder.BANK_ACCOUNT_YML);

		Mockito.when(this.getInstance(DAOBankAccount.class).getEntityInstance())
				.thenReturn(new MockBankAccountEntity());

		BankAccount.Builder builder = this
				.getInstance(BankAccount.Builder.class);

		builder.setBankAccountNumber(mockBankAccount.getBankAccountNumber())
				.setBankIdentifier(mockBankAccount.getBankIdentifier())
				.setIBANNumber(mockBankAccount.getIBANNumber())
				.setOwnerName(mockBankAccount.getOwnerName());

		BankAccount bankAccount = builder.build();

		assert (bankAccount != null);
		Assert.assertEquals(mockBankAccount.getIBANNumber(),
				bankAccount.getIBANNumber());
		Assert.assertEquals(mockBankAccount.getBankIdentifier(),
				bankAccount.getBankIdentifier());
		Assert.assertEquals(mockBankAccount.getBankAccountNumber(),
				bankAccount.getBankAccountNumber());
		Assert.assertEquals(mockBankAccount.getOwnerName(),
				bankAccount.getOwnerName());
	}
}
