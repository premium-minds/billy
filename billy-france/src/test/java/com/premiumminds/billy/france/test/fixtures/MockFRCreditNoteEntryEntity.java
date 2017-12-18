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
package com.premiumminds.billy.france.test.fixtures;

import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;
import com.premiumminds.billy.france.persistence.entities.FRCreditNoteEntryEntity;
import com.premiumminds.billy.france.services.entities.FRInvoice;

public class MockFRCreditNoteEntryEntity extends MockGenericInvoiceEntryEntity implements FRCreditNoteEntryEntity {

    private static final long serialVersionUID = 1L;
    private FRInvoice reference;
    private String reason;

    public MockFRCreditNoteEntryEntity() {

    }

    @Override
    public FRInvoice getReference() {
        return this.reference;
    }

    @Override
    public String getReason() {
        return this.reason;
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
