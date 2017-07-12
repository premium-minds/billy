/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.services.entities;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.persistence.dao.DAOESTax;
import com.premiumminds.billy.spain.services.builders.impl.ESTaxBuilderImpl;

public interface ESTax extends Tax {

    public static class Builder extends ESTaxBuilderImpl<Builder, ESTax> {

        @Inject
        public Builder(DAOESTax daoESTax, DAOESRegionContext daoESRegionContext) {
            super(daoESTax, daoESRegionContext);
        }

    }

    public static class ESVATCode {

        public static final String REDUCED = "RED";
        public static final String INTERMEDIATE = "INT";
        public static final String NORMAL = "NOR";
        public static final String EXEMPT = "ISE";
        public static final String OTHER = "OUT";
    }

}
