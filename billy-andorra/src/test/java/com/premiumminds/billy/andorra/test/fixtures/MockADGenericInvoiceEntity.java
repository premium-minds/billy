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
package com.premiumminds.billy.andorra.test.fixtures;

import com.premiumminds.billy.andorra.persistence.entities.ADGenericInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADGenericInvoiceEntry;
import com.premiumminds.billy.andorra.services.entities.ADPayment;
import java.util.ArrayList;
import java.util.List;

import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;

public class MockADGenericInvoiceEntity extends MockGenericInvoiceEntity implements ADGenericInvoiceEntity {

    private static final long serialVersionUID = 1L;
    protected boolean cancelled;
    protected boolean billed;
    protected String eacCode;
    protected List<ADPayment> payments;

    public MockADGenericInvoiceEntity() {
        this.payments = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ADGenericInvoiceEntry> getEntries() {
        return (List<ADGenericInvoiceEntry>) (List<?>) super.getEntries();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public void setBilled(boolean billed) {
        this.billed = billed;
    }

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
    public void setEACCode(String eacCode) {
        this.eacCode = eacCode;
    }

    @Override
    public List<ADPayment> getPayments() {
        return this.payments;
    }
}
