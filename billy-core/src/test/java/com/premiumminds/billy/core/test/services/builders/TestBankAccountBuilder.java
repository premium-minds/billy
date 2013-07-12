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
	
	private static final String BANK_ACCOUNT_YML = "src/test/resources/BankAccount.yml";
	
	@Test
	public void doTest() {
		MockBankAccountEntity mockBankAccount = (MockBankAccountEntity) createMockEntityFromYaml(MockBankAccountEntity.class, BANK_ACCOUNT_YML);
		
		Mockito.when(getInstance(DAOBankAccount.class).getEntityInstance()).thenReturn(new MockBankAccountEntity());
		
		BankAccount.Builder builder = getInstance(BankAccount.Builder.class);
		
		builder.setBankAccountNumber(mockBankAccount.getBankAccountNumber()).setBankIdentifier(mockBankAccount.getBankIdentifier()).setIBANNumber(mockBankAccount.getIBANNumber()).setOwnerName(mockBankAccount.getOwnerName());
		
		BankAccount bankAccount = builder.build();
		
		assert(bankAccount != null);
		assertEquals(mockBankAccount.getIBANNumber(), bankAccount.getIBANNumber());
		assertEquals(mockBankAccount.getBankIdentifier(), bankAccount.getBankIdentifier());
		assertEquals(mockBankAccount.getBankAccountNumber(), bankAccount.getBankAccountNumber());
		assertEquals(mockBankAccount.getOwnerName(), bankAccount.getOwnerName());
	}
}
