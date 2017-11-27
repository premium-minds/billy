/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.entities.ebean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;

@Entity
@DiscriminatorValue("JPAPTCreditNoteEntryEntity")
public class JPAPTCreditNoteEntryEntity extends JPAPTGenericInvoiceEntryEntity implements PTCreditNoteEntryEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = JPAPTInvoiceEntity.class,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_PTINVOICE", referencedColumnName = "ID")
    protected JPAPTInvoiceEntity reference;

    @Column(name = "REASON")
    protected String reason;

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public JPAPTInvoiceEntity getReference() {
        return this.reference;
    }

    @Override
    public void setReference(PTInvoice reference) {
        this.reference = (JPAPTInvoiceEntity) reference;
    }

    @Override
    public void setReason(String reason) {
        this.reason = reason;
    }

}
