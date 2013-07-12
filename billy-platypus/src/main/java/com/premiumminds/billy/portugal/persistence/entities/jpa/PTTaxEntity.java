/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
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

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.premiumminds.billy.core.util.TaxType;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.entities.IPTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTTaxEntity;

@Entity
@Table(name = Config.TABLE_PREFIX + "TAX")
public class PTTaxEntity extends TaxEntity
implements IPTTaxEntity {
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(name = "VAT_CODE", nullable = true, insertable = true, updatable = true)
	private PTVatCode vatCode;
	
	
	public PTTaxEntity(){}
	
	public PTTaxEntity(
			PTRegionContextEntity context,
			String designation,
			TaxRateType type,
			BigDecimal value,
			Unit unit,
			Currency currency,
			Date validFrom,
			Date validTo,
			PTVatCode vatCode){
		
		super(context, designation, type, value, unit, currency, validFrom, validTo);
		
		setVatType(vatCode);
	}

	
	//GETTERS
	
	@Override
	public PTVatCode getVatCode() {
		return vatCode;
	}
	
	@Override
	public IPTRegionContextEntity getRegionContext() {
		return (IPTRegionContextEntity) getContext();
	}

	//SETTERS
	private void setVatType(PTVatCode vatCode) {
		this.vatCode = vatCode;
	}
	
}
