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

public class TaxExemption {

    private final String exemptionCode;
    private final String exemptionReason;

    private TaxExemption(String exemptionCode, String exemptionReason) {
        this.exemptionCode = exemptionCode;
        this.exemptionReason = exemptionReason;
    }

    public static TaxExemption setExemption(String exemptionCode, String exemptionReason) {
        if(exemptionCode == null || exemptionReason == null) {
            return null;
        }
        return new TaxExemption(exemptionCode, exemptionReason);
    }

    public String getExemptionCode() {
        return exemptionCode;
    }

    public String getExemptionReason() {
        return exemptionReason;
    }
}
