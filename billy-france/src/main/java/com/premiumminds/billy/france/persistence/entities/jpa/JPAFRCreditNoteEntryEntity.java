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
package com.premiumminds.billy.france.persistence.entities.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.france.Config;
import com.premiumminds.billy.france.persistence.entities.FRCreditNoteEntryEntity;
import com.premiumminds.billy.france.services.entities.FRInvoice;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "CREDIT_NOTE_ENTRY")
public class JPAFRCreditNoteEntryEntity extends JPAFRGenericInvoiceEntryEntity implements FRCreditNoteEntryEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = JPAFRInvoiceEntity.class,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_FRINVOICE", referencedColumnName = "ID")
    protected FRInvoice reference;

    @Column(name = "REASON")
    protected String reason;

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public FRInvoice getReference() {
        return this.reference;
    }

    @Override
    public void setReference(FRInvoice reference) {
        this.reference = reference;
    }

    @Override
    public void setReason(String reason) {
        this.reason = reason;
    }

}
