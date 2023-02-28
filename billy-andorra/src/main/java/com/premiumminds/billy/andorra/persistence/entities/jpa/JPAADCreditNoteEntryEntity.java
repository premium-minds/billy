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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.andorra.Config;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntryEntity;
import com.premiumminds.billy.andorra.services.entities.ADInvoice;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "CREDIT_NOTE_ENTRY")
public class JPAADCreditNoteEntryEntity extends JPAADGenericInvoiceEntryEntity implements ADCreditNoteEntryEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = JPAADInvoiceEntity.class,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_ESINVOICE", referencedColumnName = "ID")
    protected ADInvoice invoiceReference;

    @Column(name = "REASON")
    protected String reason;

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public ADInvoice getReference() {
        return this.invoiceReference;
    }

    @Override
    public void setReference(ADInvoice reference) {
        this.invoiceReference = reference;
    }

    @Override
    public void setReason(String reason) {
        this.reason = reason;
    }

}
