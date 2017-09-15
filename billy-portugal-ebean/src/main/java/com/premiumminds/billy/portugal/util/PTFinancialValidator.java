/**
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
package com.premiumminds.billy.portugal.util;

import com.premiumminds.billy.core.util.FinancialValidator;

public class PTFinancialValidator extends FinancialValidator {

    public static final String PT_COUNTRY_CODE = "PT";

    public PTFinancialValidator(String financialID) {
        super(financialID);
    }

    @Override
    public boolean isValid() {
        // Must have exactly 9 numeric digits
        if (this.financialID.length() != 9 || !this.financialID.matches("\\d+")) {
            return false;
        }

        // Fist digit must be 1, 2, 5, 6, 8 or 9
        char firstDigit = this.financialID.charAt(0);
        String validChars = "125689";
        if (validChars.indexOf(firstDigit) == -1) {
            return false;
        }

        int checkSum = 0;
        for (int i = 1; i < this.financialID.length(); i++) {
            int digit = Character.getNumericValue(this.financialID.charAt(i - 1));
            checkSum += (10 - i) * digit;
        }

        int lastDigit = Character.getNumericValue(this.financialID.charAt(8));
        int val = (checkSum / 11) * 11;
        checkSum -= val;
        if (checkSum == 0 || checkSum == 1) {
            checkSum = 0;
        } else {
            checkSum = 11 - checkSum;
        }

        if (checkSum == lastDigit) {
            return true;
        } else {
            return false;
        }
    }

}
