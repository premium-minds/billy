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
package com.premiumminds.billy.spain.services.builders.impl;

import javax.inject.Inject;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.services.builders.impl.ShippingPointBuilderImpl;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.spain.persistence.dao.DAOESShippingPoint;
import com.premiumminds.billy.spain.persistence.entities.ESShippingPointEntity;
import com.premiumminds.billy.spain.services.builders.ESShippingPointBuilder;
import com.premiumminds.billy.spain.services.entities.ESShippingPoint;

public class ESShippingPointBuilderImpl<TBuilder extends ESShippingPointBuilderImpl<TBuilder, TShippingPoint>, TShippingPoint extends ESShippingPoint>
    extends ShippingPointBuilderImpl<TBuilder, TShippingPoint>
    implements ESShippingPointBuilder<TBuilder, TShippingPoint> {

  protected static final Localizer LOCALIZER = new Localizer(
      "com/premiumminds/billy/core/i18n/FieldNames");

  @Inject
  public ESShippingPointBuilderImpl(DAOESShippingPoint daoESShippingPoint) {
    super(daoESShippingPoint);
  }

  @Override
  protected ESShippingPointEntity getTypeInstance() {
    return (ESShippingPointEntity) super.getTypeInstance();
  }

  @Override
  protected void validateInstance() throws BillyValidationException {
    super.validateInstance();
  }
}
