/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.persistence.entities.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.spain.Config;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntryEntity;
import com.premiumminds.billy.spain.services.entities.ESInvoice;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "CREDIT_NOTE_ENTRY")
public class JPAESCreditNoteEntryEntity extends JPAESGenericInvoiceEntryEntity implements ESCreditNoteEntryEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = JPAESInvoiceEntity.class,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_ESINVOICE", referencedColumnName = "ID")
    protected ESInvoice invoiceReference;

    @Column(name = "REASON")
    protected String reason;

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public ESInvoice getReference() {
        return this.invoiceReference;
    }

    @Override
    public void setReference(ESInvoice reference) {
        this.invoiceReference = reference;
    }

    @Override
    public void setReason(String reason) {
        this.reason = reason;
    }

}
