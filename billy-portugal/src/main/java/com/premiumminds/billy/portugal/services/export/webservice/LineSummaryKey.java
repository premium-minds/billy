/*
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
package com.premiumminds.billy.portugal.services.export.webservice;

import java.util.Objects;
import javax.xml.datatype.XMLGregorianCalendar;

import com.premiumminds.billy.portugal.ws.client.DebitCreditIndicator;
import com.premiumminds.billy.portugal.ws.client.TaxType;

class LineSummaryKey {

    TaxType taxType;
    String taxCountryRegion;
    String taxCode;
    String taxExemptionCode;
    XMLGregorianCalendar taxPointDate;
    String reference;
    DebitCreditIndicator creditOrDebit;

    public LineSummaryKey(TaxType taxType, String taxCountryRegion, String taxCode, String taxExemptionCode,
                          XMLGregorianCalendar taxPointDate, String reference, DebitCreditIndicator creditOrDebit) {
        this.taxType = taxType;
        this.taxCountryRegion = taxCountryRegion;
        this.taxCode = taxCode;
        this.taxExemptionCode = taxExemptionCode;
        this.taxPointDate = taxPointDate;
        this.reference = reference;
        this.creditOrDebit = creditOrDebit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LineSummaryKey)) {
            return false;
        }
        LineSummaryKey that = (LineSummaryKey) o;
        return taxType == that.taxType &&
                Objects.equals(taxCountryRegion, that.taxCountryRegion) &&
                Objects.equals(taxCode, that.taxCode) &&
                Objects.equals(taxExemptionCode, that.taxExemptionCode) &&
                Objects.equals(taxPointDate, that.taxPointDate) &&
                Objects.equals(reference, that.reference) &&
                Objects.equals(creditOrDebit, that.creditOrDebit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taxType, taxCountryRegion, taxCode, taxExemptionCode, taxPointDate, reference, creditOrDebit);
    }

    @Override
    public String toString() {
        return "LineSummaryKey{" + "taxType=" + taxType + ", taxCountryRegion='" + taxCountryRegion + '\'' +
                ", taxCode='" + taxCode + '\'' + ", taxExemptionCode='" + taxExemptionCode + '\'' + ", taxPointDate=" +
                taxPointDate + ", reference='" + reference + '\'' + ", creditOrDebit=" + creditOrDebit + '}';
    }
}
