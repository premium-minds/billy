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

import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntryEntity;
import com.premiumminds.billy.andorra.services.entities.ADInvoice;
import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;

public class MockADCreditNoteEntryEntity extends MockGenericInvoiceEntryEntity implements ADCreditNoteEntryEntity {

    private static final long serialVersionUID = 1L;
    private ADInvoice reference;
    private String reason;

    public MockADCreditNoteEntryEntity() {

    }

    @Override
    public ADInvoice getReference() {
        return this.reference;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public void setReference(ADInvoice reference) {
        this.reference = reference;
    }

    @Override
    public void setReason(String reason) {
        this.reason = reason;
    }

}
