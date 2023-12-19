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

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.persistence.entities.jpa.JPAGenericInvoiceEntity;
import com.premiumminds.billy.andorra.Config;
import com.premiumminds.billy.andorra.persistence.entities.ADGenericInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADGenericInvoiceEntry;
import com.premiumminds.billy.andorra.services.entities.ADPayment;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "GENERIC_INVOICE")
public class JPAADGenericInvoiceEntity extends JPAGenericInvoiceEntity implements ADGenericInvoiceEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "CANCELLED")
    protected boolean cancelled;

    @Column(name = "EAC_CODE")
    protected String eacCode;

    @Column(name = "BILLED")
    protected boolean billed;

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public boolean isBilled() {
        return this.billed;
    }

    @Override
    public String getEACCode() {
        return this.eacCode;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public void setBilled(boolean billed) {
        this.billed = billed;
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public List<? extends ADGenericInvoiceEntry> getEntries() {
        return (List<ADGenericInvoiceEntry>) super.getEntries();
    }

    @Override
    public void setEACCode(String eacCode) {
        this.eacCode = eacCode;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ADPayment> getPayments() {
        return (List<ADPayment>) super.getPayments();
    }
}
