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
package com.premiumminds.billy.portugal.services.documents.util;

import java.security.PrivateKey;
import java.security.PublicKey;

import com.premiumminds.billy.core.services.documents.IssuingParams;
import com.premiumminds.billy.core.services.exceptions.ParameterNotFoundException;

public interface PTIssuingParams extends IssuingParams {

    public static class Util {

        public static PTIssuingParams newInstance() {
            return new PTIssuingParamsImpl();
        }
    }

    public static class Keys {

        public static final String PRIVATE_KEY = "private_key";
        public static final String PUBLIC_KEY = "public_key";
        public static final String INVOICE_SERIES = "invoice_series";
        public static final String PRIVATE_KEY_VERSION = "private_key_version";
        public static final String EAC_CODE = "eac_code";
    }

    public String getInvoiceSeries() throws ParameterNotFoundException;

    public PrivateKey getPrivateKey() throws ParameterNotFoundException;

    public PublicKey getPublicKey() throws ParameterNotFoundException;

    public String getPrivateKeyVersion() throws ParameterNotFoundException;

    public String getEACCode() throws ParameterNotFoundException;

    public void setPublicKey(PublicKey privateKey);

    public void setPrivateKey(PrivateKey privateKey);

    public void setInvoiceSeries(String series);

    public void setPrivateKeyVersion(String version);

    public void setEACCode(String eacCode);

}
