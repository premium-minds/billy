/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.util;

import com.premiumminds.billy.core.util.FinancialValidator;

public class FRFinancialValidator extends FinancialValidator {

    public static final String FR_COUNTRY_CODE = "FR";
    private static final int TIN_CITIZEN_LEN = 13;
	private static final int[] TIN_FIRST_DIGITS = { 0, 1, 2, 3 };

    public FRFinancialValidator(String financialID) {
        super(financialID);
    }

    @Override
    public boolean isValid() {
    	if(financialID.length() != TIN_CITIZEN_LEN) {
			return false;
		}
		
		int[] tin = new int[TIN_CITIZEN_LEN];
		String aux = "";
		String checkDigit = "";
		for (int i = 0; i < TIN_CITIZEN_LEN; i++) {
			char c = financialID.charAt(i);
			tin[i] = Integer.parseInt("" + financialID.charAt(i));
			if (i < 10) {
				aux += c;
			}
			else {
				checkDigit += c;
			}
		}
		
		// first digit must be either 0, 1, 2, or 3
		boolean firstDigitValid = false;
		for (int f : TIN_FIRST_DIGITS) {
			if (tin[0] == f) {
				firstDigitValid = true;
				break;
			}
		}
		if (!firstDigitValid) {
			return false;
		}
		
		long auxAux = Long.valueOf(aux);
		long remainder = auxAux % 511;

		// remainder must match the checkdigit
		if (Integer.valueOf(checkDigit) != remainder) {
			return false;
		}
		// remainder < 100 requires tin[10] = 0
		if (remainder < 100 && tin[10] != 0) {
			return false;
		}
		// remainder < 10 requires tin[10] = 0 and tin[11] = 0
		if (remainder < 10 && tin[10] != 0 && tin[11] != 0) {
			return false;
		}
		
		return true;
    }

}
