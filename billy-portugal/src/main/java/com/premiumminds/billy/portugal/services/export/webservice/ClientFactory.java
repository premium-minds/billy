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
package com.premiumminds.billy.portugal.services.export.webservice;

import java.net.URL;

import javax.inject.Inject;

import com.premiumminds.billy.portugal.Config;

public class ClientFactory {

    private final Config config;

    @Inject
    public ClientFactory(Config config) {
        this.config = config;
    }

    public Client newClient(URL url, WebserviceCredentials webserviceCredentials, WebserviceKeys webserviceKeys,
            SslClientCertificate sslClientCertificate){
        return new Client(config, url, webserviceCredentials, webserviceKeys, sslClientCertificate);
    }

}
