/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core JPA.
 *
 * billy core JPA is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core JPA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core JPA. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.persistence.entities.jpa;

import com.premiumminds.billy.core.services.entities.BankAccount;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.BankAccountEntity;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "BANK_ACCOUNT")
public class JPABankAccountEntity extends JPABaseEntity<BankAccount> implements BankAccountEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "IBAN")
    protected String iban;

    @Column(name = "BANK_IDENTIFIER")
    protected String bankIdentifier;

    @Column(name = "BANK_ACCOUNT_NUMBER")
    protected String bankAccountNumber;

    @Column(name = "OWNER_NAME")
    protected String ownerName;

    public JPABankAccountEntity() {
    }

    @Override
    public String getIBANNumber() {
        return this.iban;
    }

    @Override
    public String getBankIdentifier() {
        return this.bankIdentifier;
    }

    @Override
    public String getBankAccountNumber() {
        return this.bankAccountNumber;
    }

    @Override
    public String getOwnerName() {
        return this.ownerName;
    }

    @Override
    public void setIBANNumber(String iban) {
        this.iban = iban;
    }

    @Override
    public void setBankIdentifier(String bankId) {
        this.bankIdentifier = bankId;
    }

    @Override
    public void setBankAccountNumber(String accountNumber) {
        this.bankAccountNumber = accountNumber;
    }

    @Override
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

}
