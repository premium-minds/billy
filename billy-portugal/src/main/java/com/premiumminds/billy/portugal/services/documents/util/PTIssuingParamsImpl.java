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

import com.premiumminds.billy.core.services.documents.impl.IssuingParamsImpl;
import com.premiumminds.billy.core.services.exceptions.ParameterNotFoundException;

public class PTIssuingParamsImpl extends IssuingParamsImpl implements PTIssuingParams {

    public PTIssuingParamsImpl() {
        super();
    }

    @Override
    public String getInvoiceSeries() throws ParameterNotFoundException {
        return (String) this.getParameter(Keys.INVOICE_SERIES);
    }

    @Override
    public PrivateKey getPrivateKey() throws ParameterNotFoundException {
        return (PrivateKey) this.getParameter(Keys.PRIVATE_KEY);
    }

    @Override
    public PublicKey getPublicKey() throws ParameterNotFoundException {
        return (PublicKey) this.getParameter(Keys.PUBLIC_KEY);
    }

    @Override
    public String getPrivateKeyVersion() throws ParameterNotFoundException {
        return (String) this.getParameter(Keys.PRIVATE_KEY_VERSION);
    }

    @Override
    public String getEACCode() throws ParameterNotFoundException {
        return (String) this.getParameter(Keys.EAC_CODE);
    }

    @Override
    public void setPublicKey(PublicKey publicKey) {
        this.setParameter(Keys.PUBLIC_KEY, publicKey);
    }

    @Override
    public void setPrivateKey(PrivateKey privateKey) {
        this.setParameter(Keys.PRIVATE_KEY, privateKey);
    }

    @Override
    public void setInvoiceSeries(String series) {
        this.setParameter(Keys.INVOICE_SERIES, series);
    }

    @Override
    public void setPrivateKeyVersion(String version) {
        this.setParameter(Keys.PRIVATE_KEY_VERSION, version);
    }

    @Override
    public void setEACCode(String eacCode) {
        this.setParameter(Keys.EAC_CODE, eacCode);
    }

}
