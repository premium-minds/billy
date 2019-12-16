/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.entities;

import javax.inject.Inject;

import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.services.builders.impl.PTCreditNoteBuilderImpl;
import com.premiumminds.billy.portugal.services.builders.impl.PTManualCreditNoteBuilderImpl;

public interface PTCreditNote extends PTGenericInvoice {

    public static class Builder extends PTCreditNoteBuilderImpl<Builder, PTCreditNoteEntry, PTCreditNote> {

        @Inject
        public Builder(DAOPTCreditNote daoPTCreditNote, DAOPTBusiness daoPTBusiness, DAOPTCustomer daoPTCustomer,
                DAOPTSupplier daoPTSupplier) {
            super(daoPTCreditNote, daoPTBusiness, daoPTCustomer, daoPTSupplier);
        }
    }

    public static class ManualBuilder
            extends PTManualCreditNoteBuilderImpl<ManualBuilder, PTCreditNoteEntry, PTCreditNote> {

        @Inject
        public ManualBuilder(DAOPTCreditNote daoPTCreditNote, DAOPTBusiness daoPTBusiness, DAOPTCustomer daoPTCustomer,
                DAOPTSupplier daoPTSupplier) {
            super(daoPTCreditNote, daoPTBusiness, daoPTCustomer, daoPTSupplier);
        }
    }

}
