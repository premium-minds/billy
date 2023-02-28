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
package com.premiumminds.billy.andorra.persistence.entities.jpa;

import com.premiumminds.billy.andorra.Config;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditReceiptEntryEntity;
import com.premiumminds.billy.andorra.services.entities.ADReceipt;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "CREDIT_RECEIPT_ENTRY")
public class JPAADCreditReceiptEntryEntity extends JPAADGenericInvoiceEntryEntity
        implements ADCreditReceiptEntryEntity
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = JPAADReceiptEntity.class,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_ESRECEIPT", referencedColumnName = "ID")
    protected ADReceipt receiptReference;

    @Column(name = "REASON")
    protected String reason;

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public ADReceipt getReference() {
        return this.receiptReference;
    }

    @Override
    public void setReference(ADReceipt reference) {
        this.receiptReference = reference;
    }

    @Override
    public void setReason(String reason) {
        this.reason = reason;
    }
}
