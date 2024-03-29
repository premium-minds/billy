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

import com.premiumminds.billy.core.services.documents.IssuingParams;
import com.premiumminds.billy.core.services.exceptions.ParameterNotFoundException;

public interface ADIssuingParams extends IssuingParams {

    public static class Util {

        public static ADIssuingParams newInstance() {
            return new ADIssuingParamsImpl();
        }
    }

    public static class Keys {

        public static final String INVOICE_SERIES = "invoice_series";
        public static final String EAC_CODE = "eac_code";
    }

    public String getInvoiceSeries() throws ParameterNotFoundException;

    public String getEACCode() throws ParameterNotFoundException;

    public void setInvoiceSeries(String series);

    public void setEACCode(String eacCode);

}
