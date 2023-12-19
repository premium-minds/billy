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
package com.premiumminds.billy.andorra.persistence.entities.jpa;

import com.premiumminds.billy.andorra.Config;
import com.premiumminds.billy.andorra.persistence.entities.ADTaxEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.persistence.entities.jpa.JPATaxEntity;
import com.premiumminds.billy.core.services.entities.Context;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "TAX")
public class JPAADTaxEntity extends JPATaxEntity implements ADTaxEntity {

    private static final long serialVersionUID = 1L;

    @Override
    public Context getContext() {
        return super.getContext();
    }
}
