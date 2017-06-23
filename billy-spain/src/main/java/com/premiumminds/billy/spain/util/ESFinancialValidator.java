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
package com.premiumminds.billy.spain.util;

import com.premiumminds.billy.core.util.FinancialValidator;

public class ESFinancialValidator extends FinancialValidator {

  public static final String ES_COUNTRY_CODE = "ES";

  public ESFinancialValidator(String financialID) {
    super(financialID);
  }

  @Override
  public boolean isValid() {
    {
      if (financialID.length() != 9)
        return false;
      String cif = financialID.toUpperCase();
      int[] numbers = new int[9];
      for (int i = 0; i < 9; i++)
        numbers[i] = Character.getNumericValue(cif.charAt(i));

      if (!cif.matches("((^[A-Z]{1}[0-9]{7}[A-Z0-9]{1}$|^[T]{1}[A-Z0-9]{8}$)|^[0-9]{8}[A-Z]{1}$)"))
        return false;

      String controlDigits = "TRWAGMYFPDXBNJZSQVHLCKE";
      char lastDigit = cif.charAt(8);
      if (cif.matches("(^[0-9]{8}[A-Z]{1}$)")) {
        // TODO: check if this do what is pretended
        if (lastDigit == controlDigits.charAt(Integer.parseInt(cif.substring(0, 8)) % 23))
          return true;
        else
          return false;
      }

      int sum = numbers[2] + numbers[4] + numbers[6];
      for (int i = 1; i < 8; i += 2) {
        sum += (2 * numbers[i] / 10) + (2 * numbers[i] % 10);
      }
      int n = 10 - (sum % 10);

      if (cif.matches("^[KLM]{1}.*")) {
        // TODO: check if this do what is pretended
        if (lastDigit == (char) (64 + n)
            || lastDigit == controlDigits.charAt(Integer.parseInt(cif.substring(1, 8)) % 23))
          return true;
        else
          return false;
      }

      if (cif.matches("^[ABCDEFGHJNPQRSUVW]{1}.*")) {
        if (lastDigit == (char) (64 + n) || numbers[8] == n % 10)
          return true;
        else
          return false;
      }

      if (cif.matches("^[T]{1}.*")) {
        int value = cif.matches("^[T]{1}[A-Z0-9]{8}$") ? 1 : 0;
        if (numbers[8] == value)
          return true;
        else
          return false;
      }

      if (cif.matches("^[XYZ]{1}.*")) {
        String cif_aux = cif.replace('X', '0').replace('Y', '1').replace('Z', '2');
        if (lastDigit == controlDigits.charAt(Integer.parseInt(cif_aux.substring(0, 8)) % 23))
          return true;
      }

      return false;
    }
  }

}
