/*
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
package com.premiumminds.billy.france.services.documents;

import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.france.persistence.dao.DAOFRSimpleInvoice;
import com.premiumminds.billy.france.persistence.entities.FRSimpleInvoiceEntity;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParams;

public class FRSimpleInvoiceIssuingHandler
        extends FRGenericInvoiceIssuingHandler<FRSimpleInvoiceEntity, FRIssuingParams> {

    private final DAOFRSimpleInvoice daoSimpleInvoice;

    @Inject
    public FRSimpleInvoiceIssuingHandler(DAOInvoiceSeries daoInvoiceSeries, DAOFRSimpleInvoice daoSimpleInvoice) {
        super(daoInvoiceSeries);
        this.daoSimpleInvoice = daoSimpleInvoice;
    }

    @Override
    public FRSimpleInvoiceEntity issue(FRSimpleInvoiceEntity document, FRIssuingParams parameters)
        throws DocumentIssuingException, DocumentSeriesDoesNotExistException
    {

        return this.issue(document, parameters, this.daoSimpleInvoice);
    }

}
