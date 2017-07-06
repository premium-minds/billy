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
package com.premiumminds.billy.core.services.entities;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import javax.inject.Inject;

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.services.builders.impl.TaxBuilderImpl;

public interface Tax extends Entity {

  public static class Builder extends TaxBuilderImpl<Builder, Tax> {

    @Inject
    public Builder(DAOTax daoTax, DAOContext daoContext) {
      super(daoTax, daoContext);
    }
  }

  public static enum TaxRateType {
    PERCENTAGE, FLAT, NONE
  }

  public <T extends Context> T getContext();

  public String getDesignation();

  public String getDescription();

  public String getCode();

  public BigDecimal getValue();

  public Date getValidFrom();

  public Date getValidTo();

  public TaxRateType getTaxRateType();

  public BigDecimal getPercentageRateValue();

  public BigDecimal getFlatRateAmount();

  public Currency getCurrency();

}
