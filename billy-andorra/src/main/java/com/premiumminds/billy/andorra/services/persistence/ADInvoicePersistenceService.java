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

import jakarta.inject.Inject;

import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADInvoice;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;

public class ADInvoicePersistenceService {

    protected final DAOADInvoice daoInvoice;

    @Inject
    public ADInvoicePersistenceService(DAOADInvoice daoInvoice) {
        this.daoInvoice = daoInvoice;
    }

    public ADInvoice update(final Builder<ADInvoice> builder) {
        try {
            return new TransactionWrapper<ADInvoice>(this.daoInvoice) {

                @Override
                public ADInvoice runTransaction() throws Exception {
                    ADInvoiceEntity entity = (ADInvoiceEntity) builder.build();
                    return ADInvoicePersistenceService.this.daoInvoice.update(entity);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ADInvoice get(final StringID<GenericInvoice> uid) {
        try {
            return new TransactionWrapper<ADInvoice>(this.daoInvoice) {

                @Override
                public ADInvoice runTransaction() throws Exception {
                    return ADInvoicePersistenceService.this.daoInvoice.get(uid);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

    public ADInvoice findByNumber(final StringID<Business> uidBusiness, final String number) {
        try {
            return new TransactionWrapper<ADInvoice>(this.daoInvoice) {

                @Override
                public ADInvoice runTransaction() throws Exception {
                    return ADInvoicePersistenceService.this.daoInvoice.findByNumber(uidBusiness, number);
                }

            }.execute();
        } catch (Exception e) {
            throw new BillyRuntimeException(e);
        }
    }

}
