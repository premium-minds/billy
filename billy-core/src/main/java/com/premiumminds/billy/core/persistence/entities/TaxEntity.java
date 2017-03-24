/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.entities;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import com.premiumminds.billy.core.services.entities.Tax;

public interface TaxEntity extends Tax, BaseEntity {

	public <T extends ContextEntity> void setContext(T context);

	public void setDesignation(String designation);

	public void setDescription(String description);

	public void setCode(String code);

	public void setValidFrom(Date from);

	public void setValidTo(Date to);

	public void setTaxRateType(TaxRateType type);

	public void setPercentageRateValue(BigDecimal value);

	public void setFlatRateAmount(BigDecimal amount);

	public void setCurrency(Currency currency);

	public void setValue(BigDecimal value);

}
