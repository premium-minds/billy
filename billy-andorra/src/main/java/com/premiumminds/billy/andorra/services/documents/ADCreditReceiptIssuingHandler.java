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

import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditReceipt;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditReceiptEntity;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;

public class ADCreditReceiptIssuingHandler
        extends ADGenericInvoiceIssuingHandler<ADCreditReceiptEntity, ADIssuingParams>
{

    private final DAOADCreditReceipt daoCreditReceipt;

    @Inject
    public ADCreditReceiptIssuingHandler(DAOInvoiceSeries invoiceSeries, DAOADCreditReceipt daoCreditReceipt) {
        super(invoiceSeries);
        this.daoCreditReceipt = daoCreditReceipt;
    }

    @Override
    public ADCreditReceiptEntity issue(ADCreditReceiptEntity document, ADIssuingParams parameters)
        throws DocumentIssuingException, DocumentSeriesDoesNotExistException
    {

        return this.issue(document, parameters, this.daoCreditReceipt);
    }
}
