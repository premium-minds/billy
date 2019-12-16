/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.entities;

import javax.inject.Inject;

import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.services.builders.impl.PTRegionContextBuilderImpl;

public interface PTRegionContext extends Context {

    public static class Builder extends PTRegionContextBuilderImpl<Builder, PTRegionContext> {

        @Inject
        public Builder(DAOPTRegionContext daoPTRegionContext) {
            super(daoPTRegionContext);
        }
    }

    /**
     * @return ISO 3166-1-alpha-2
     */
    public String getRegionCode();

    @Override
    public <T extends Context> T getParentContext();

}
