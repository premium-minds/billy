/**
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
package com.premiumminds.billy.portugal.services.documents;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.documents.IssuingParams;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;

public class PTReceiptInvoiceIssuingHandler extends PTGenericInvoiceIssuingHandler
    implements DocumentIssuingHandler {

  public final static TYPE INVOICE_TYPE = TYPE.FR;
  private final DAOPTReceiptInvoice daoReceiptInvoice;

  @Inject
  public PTReceiptInvoiceIssuingHandler(DAOInvoiceSeries daoInvoiceSeries,
      DAOPTReceiptInvoice daoRecepit) {
    super(daoInvoiceSeries);
    this.daoReceiptInvoice = daoRecepit;
  }

  @Override
  public <T extends GenericInvoice, P extends IssuingParams> T issue(T document, P parameters)
      throws DocumentIssuingException {

    final PTIssuingParams parametersPT = (PTIssuingParams) parameters;

    return this.issue(document, parametersPT, daoReceiptInvoice,
        PTReceiptInvoiceIssuingHandler.INVOICE_TYPE);
  }

}
