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
package com.premiumminds.billy.andorra.services.entities;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.persistence.dao.DAOADTax;
import com.premiumminds.billy.andorra.services.builders.impl.ADTaxBuilderImpl;

public interface ADTax extends Tax {

    public static class Builder extends ADTaxBuilderImpl<Builder, ADTax> {

        @Inject
        public Builder(DAOADTax daoADTax, DAOADRegionContext daoADRegionContext) {
            super(daoADTax, daoADRegionContext);
        }

    }

    public static class ADVATCode {

        public static final String INCREASED = "INC";
        public static final String NORMAL = "NOR";
        public static final String SPECIAL = "SPC";
        public static final String REDUCED = "RED";
        public static final String SUPER_REDUCED = "SUP";
        public static final String EXEMPT = "ISE";
    }

}
