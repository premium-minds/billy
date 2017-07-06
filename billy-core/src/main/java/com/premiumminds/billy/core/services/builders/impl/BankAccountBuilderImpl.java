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
package com.premiumminds.billy.core.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOBankAccount;
import com.premiumminds.billy.core.persistence.entities.BankAccountEntity;
import com.premiumminds.billy.core.services.builders.BankAccountBuilder;
import com.premiumminds.billy.core.services.entities.BankAccount;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;

public class BankAccountBuilderImpl<TBuilder extends BankAccountBuilderImpl<TBuilder, TBankAccount>, TBankAccount extends BankAccount>
    extends AbstractBuilder<TBuilder, TBankAccount>
    implements BankAccountBuilder<TBuilder, TBankAccount> {

  protected static final Localizer LOCALIZER = new Localizer(
      "com/premiumminds/billy/core/i18n/FieldNames");

  protected DAOBankAccount daoBankAccount;

  @Inject
  public BankAccountBuilderImpl(DAOBankAccount daoBankAccount) {
    super(daoBankAccount);
    this.daoBankAccount = daoBankAccount;
  }

  @Override
  public TBuilder setIBANNumber(String iban) {
    BillyValidator.notBlank(iban, BankAccountBuilderImpl.LOCALIZER.getString("field.iban"));
    this.getTypeInstance().setIBANNumber(iban);
    return this.getBuilder();
  }

  @Override
  public TBuilder setBankIdentifier(String bankId) {
    BillyValidator.notBlank(bankId, BankAccountBuilderImpl.LOCALIZER.getString("field.bank_id"));
    this.getTypeInstance().setBankIdentifier(bankId);
    return this.getBuilder();
  }

  @Override
  public TBuilder setBankAccountNumber(String accountNumber) {
    BillyValidator.notBlank(accountNumber,
        BankAccountBuilderImpl.LOCALIZER.getString("field.bank_account_number"));
    this.getTypeInstance().setBankAccountNumber(accountNumber);
    return this.getBuilder();
  }

  @Override
  public TBuilder setOwnerName(String ownerName) {
    BillyValidator.notBlank(ownerName,
        BankAccountBuilderImpl.LOCALIZER.getString("field.bank_owner_name"));
    this.getTypeInstance().setOwnerName(ownerName);
    return this.getBuilder();
  }

  @Override
  protected void validateInstance() throws javax.validation.ValidationException {
    BankAccountEntity b = this.getTypeInstance();
    BillyValidator.mandatory(b.getIBANNumber(), "field.iban");
    BillyValidator.mandatory(b.getBankAccountNumber(), "field.bank_account_number");
  }

  @SuppressWarnings("unchecked")
  @Override
  protected BankAccountEntity getTypeInstance() {
    return (BankAccountEntity) super.getTypeInstance();
  }

}
