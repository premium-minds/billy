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
package com.premiumminds.billy.andorra.services.persistence;

import javax.inject.Inject;

import com.premiumminds.billy.andorra.persistence.dao.DAOADSimpleInvoice;
import com.premiumminds.billy.andorra.persistence.entities.ADSimpleInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADSimpleInvoice;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;

public class ADSimpleInvoicePersistenceService {

    protected final DAOADSimpleInvoice daoInvoice;

    @Inject
    public ADSimpleInvoicePersistenceService(DAOADSimpleInvoice daoInvoice) {
        this.daoInvoice = daoInvoice;
    }

    public ADSimpleInvoice update(final Builder<ADSimpleInvoice> builder) {
        try {
            return new TransactionWrapper<ADSimpleInvoice>(this.daoInvoice) {

                @Override
                public ADSimpleInvoice runTransaction() throws Exception {
                    ADSimpleInvoiceEntity entity = (ADSimpleInvoiceEntity) builder.build();
                    return ADSimpleInvoicePersistenceService.this.daoInvoice.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ADSimpleInvoice get(final StringID<GenericInvoice> uid) {
        try {
            return new TransactionWrapper<ADSimpleInvoice>(this.daoInvoice) {

                @Override
                public ADSimpleInvoice runTransaction() throws Exception {
                    return ADSimpleInvoicePersistenceService.this.daoInvoice.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
