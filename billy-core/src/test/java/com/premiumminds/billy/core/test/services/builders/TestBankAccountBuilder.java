/**
 * Copyright (C) 2013 Premium Minds.
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

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOBankAccount;
import com.premiumminds.billy.core.persistence.entities.BankAccountEntity;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockBankAccountEntity;

public class TestBankAccountBuilder extends AbstractTest {
	
	private static final String IBAN = "iban";
	private static final String BANKID = "bank_id";
	private static final String ACCOUNT_NUMBER = "account_number";
	private static final String OWNER_NAME = "owner_name";
	
	@Test
	public void doTest() {
		MockBankAccountEntity mockBankAccount = this.loadFixture(BankAccountEntity.class);
		
		DAOBankAccount mockDaoBankAccount = this.getMock(DAOBankAccount.class);
		
		Mockito.when(mockDaoBankAccount.getEntityInstance()).thenReturn(new MockBankAccountEntity());
		
		BankAccount.Builder builder = new BankAccount.Builder(mockDaoBankAccount);
		
		builder.setBankAccountNumber(mockBankAccount.getBankAccountNumber()).setBankIdentifier(mockBankAccount.getBankIdentifier()).setIBANNumber(mockBankAccount.getIBANNumber()).setOwnerName(mockBankAccount.getOwnerName());
		
		BankAccount bankAccount = builder.build();
		
		assert(bankAccount != null);
		assertEquals(IBAN, bankAccount.getIBANNumber());
		assertEquals(BANKID, bankAccount.getBankIdentifier());
		assertEquals(ACCOUNT_NUMBER, bankAccount.getBankAccountNumber());
		assertEquals(OWNER_NAME, bankAccount.getOwnerName());
	}
	
	public MockBankAccountEntity loadFixture(Class<BankAccountEntity> clazz) {
		MockBankAccountEntity result = new MockBankAccountEntity();
		
		result.iban = IBAN;
		result.bankId = BANKID;
		result.accountNumber = ACCOUNT_NUMBER;
		result.ownerName = OWNER_NAME;
		
		return result;
	}

}
