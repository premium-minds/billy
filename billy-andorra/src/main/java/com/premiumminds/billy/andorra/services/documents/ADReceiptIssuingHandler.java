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
package com.premiumminds.billy.andorra.services.documents;

import com.premiumminds.billy.andorra.persistence.dao.DAOADReceipt;
import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntity;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;

public class ADReceiptIssuingHandler extends ADGenericInvoiceIssuingHandler<ADReceiptEntity, ADIssuingParams> {

    private final DAOADReceipt daoReceipt;

    @Inject
    public ADReceiptIssuingHandler(DAOInvoiceSeries daoInvoiceSeries, DAOADReceipt daoReceipt) {
        super(daoInvoiceSeries);
        this.daoReceipt = daoReceipt;
    }

    @Override
    public ADReceiptEntity issue(ADReceiptEntity document, ADIssuingParams parameters)
        throws DocumentIssuingException, DocumentSeriesDoesNotExistException
    {

        return this.issue(document, parameters, this.daoReceipt);
    }

}
