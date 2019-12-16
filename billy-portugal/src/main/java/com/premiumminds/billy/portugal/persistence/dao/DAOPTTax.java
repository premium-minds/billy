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
package com.premiumminds.billy.portugal.persistence.dao;

import java.util.Date;
import java.util.List;

import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTTaxEntity;

public interface DAOPTTax extends DAOTax {

    @Override
    public PTTaxEntity getEntityInstance();

    public List<JPAPTTaxEntity> getTaxes(PTRegionContextEntity context, Date validFrom, Date validTo);

    public List<JPAPTTaxEntity> getTaxesForSAFTPT(PTRegionContextEntity context, Date validFrom, Date validTo);
}
