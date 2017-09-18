/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.services.entities;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.persistence.dao.DAOFRTax;
import com.premiumminds.billy.france.services.builders.impl.FRTaxBuilderImpl;

public interface FRTax extends Tax {

    public static class Builder extends FRTaxBuilderImpl<Builder, FRTax> {

        @Inject
        public Builder(DAOFRTax daoFRTax, DAOFRRegionContext daoFRRegionContext) {
            super(daoFRTax, daoFRRegionContext);
        }

    }

    public static class FRVATCode {

        public static final String REDUCED = "RED";
        public static final String SUPER_REDUCED = "SPR";
        public static final String INTERMEDIATE = "INT";
        public static final String NORMAL = "NOR";
        public static final String EXEMPT = "ISE";
        public static final String OTHER = "OUT";
    }

}
