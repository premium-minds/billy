/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy GIN.
 *
 * billy GIN is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy GIN is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy GIN. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.gin.services.export;

import java.math.BigDecimal;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.Tax.TaxRateType;

public class TaxData {

    private final StringID<Tax> uid;
    private final BigDecimal value;
    private final TaxRateType taxRateType;
    private final String description;
    private final String designation;

    public TaxData(StringID<Tax> uid, BigDecimal value, TaxRateType taxRateType, String description, String designation) {
        this.uid = uid;
        this.value = value;
        this.taxRateType = taxRateType;
        this.description = description;
        this.designation = designation;
    }

    public StringID<Tax> getUID() {
        return this.uid;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public TaxRateType getTaxRateType() {
        return this.taxRateType;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDesignation() {
        return this.designation;
    }

}
