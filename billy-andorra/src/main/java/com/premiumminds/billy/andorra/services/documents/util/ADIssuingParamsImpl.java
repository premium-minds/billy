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
package com.premiumminds.billy.andorra.services.documents.util;

import com.premiumminds.billy.core.services.documents.impl.IssuingParamsImpl;
import com.premiumminds.billy.core.services.exceptions.ParameterNotFoundException;

public class ADIssuingParamsImpl extends IssuingParamsImpl implements ADIssuingParams {

    public ADIssuingParamsImpl() {
        super();
    }

    @Override
    public String getInvoiceSeries() throws ParameterNotFoundException {
        return (String) this.getParameter(Keys.INVOICE_SERIES);
    }

    @Override
    public String getEACCode() throws ParameterNotFoundException {
        return (String) this.getParameter(Keys.EAC_CODE);
    }

    @Override
    public void setInvoiceSeries(String series) {
        this.setParameter(Keys.INVOICE_SERIES, series);
    }

    @Override
    public void setEACCode(String eacCode) {
        this.setParameter(Keys.EAC_CODE, eacCode);
    }

}
