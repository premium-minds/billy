/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.util;

import com.premiumminds.billy.core.util.FinancialValidator;

public class ADFinancialValidator extends FinancialValidator {

    public static final String AD_COUNTRY_CODE = "AD";

    public ADFinancialValidator(String financialID) {
        super(financialID);
    }

    @Override
    public boolean isValid() {
        {
            if (this.financialID.length() != 8) {
                return false;
            }

            final String tin = this.financialID.toUpperCase();
            if (!tin.matches("((^[ACDEFGLOPU]{1}[0-9]{6}[A-Z]{1}))")) {
                return false;
            }

            final char firstLetter = tin.charAt(0);
            final int numeroIdentificacionAdministrativo;
            try {
                numeroIdentificacionAdministrativo = Integer.parseInt(tin.substring(1, tin.length() - 1));
            } catch (NumberFormatException e) {
                return false;
            }

            if((firstLetter == 'A' || firstLetter == 'L')
                && !(numeroIdentificacionAdministrativo >= 700_000 && numeroIdentificacionAdministrativo < 800_000)){
                return false;
            } else if (firstLetter == 'F' && !(numeroIdentificacionAdministrativo >= 0 && numeroIdentificacionAdministrativo < 700_000)) {
                return false;
            } else {
                return true;
            }
        }
    }

}
