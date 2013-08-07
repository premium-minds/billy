/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.entities.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.premiumminds.billy.core.persistence.entities.jpa.JPATaxEntity;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;

@Entity
@Table(name = Config.TABLE_PREFIX + "TAX")
public class JPAPTTaxEntity extends JPATaxEntity implements PTTaxEntity {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(name = "PT_TAX_TYPE")
	private PTTaxType taxType;

	@Override
	public Context getContext() {
		return super.getContext();
	}

	@Override
	public PTTaxType getPTTaxType() {
		return taxType;
	}

	@Override
	public void setPTTaxType(PTTaxType taxType) {
		this.taxType = taxType;
	}

}
