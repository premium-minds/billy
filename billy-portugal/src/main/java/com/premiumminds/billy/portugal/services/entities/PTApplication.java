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
package com.premiumminds.billy.portugal.services.entities;

import java.net.MalformedURLException;
import java.net.URL;

import jakarta.inject.Inject;

import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTApplication;
import com.premiumminds.billy.portugal.services.builders.impl.PTApplicationBuilderImpl;

public interface PTApplication extends Application {

    public static class Builder extends PTApplicationBuilderImpl<Builder, PTApplication> {

        @Inject
        public Builder(DAOPTApplication daoPTApplication) {
            super(daoPTApplication);
        }

    }

    public Integer getSoftwareCertificationNumber();

    public URL getApplicationKeysPath() throws MalformedURLException;
}
