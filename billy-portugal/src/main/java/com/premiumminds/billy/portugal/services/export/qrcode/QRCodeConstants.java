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
package com.premiumminds.billy.portugal.services.export.qrcode;

public interface QRCodeConstants {

	String dateFormat = "YYYYMMDD";

	class Field{
		private final String name;
		private final int length;

		Field(String name, int length) {
			this.name = name;
			this.length = length;
		}

		public String getName() {
			return name;
		}

		public int getLength() {
			return length;
		}
	}

	Field emitterFinancialId =
		new Field("A", 9);
	
	Field buyerFinancialId =
		new Field("B", 30);

	Field buyerCountry =
		new Field("C", 12);

	Field documentType =
		new Field("D", 2);

	Field documentStatus =
		new Field("E", 1);

	Field documentDate =
		new Field("F", 8);

	Field documentUniqueID =
		new Field("G", 60);

	Field ATCUD =
		new Field("H", 70);

	Field taxCountryRegion =
		new Field("I1", 5);

	Field exemptAmount =
		new Field("I2", 16);

	Field reducedTaxableAmount =
		new Field("I3", 16);

	Field reducedTaxAmount =
		new Field("I4", 16);

	Field intermediateTaxableAmount =
		new Field("I5", 16);

	Field intermediateTaxAmount =
		new Field("I6", 16);

	Field regularTaxableAmount =
		new Field("I7", 16);

	Field regularTaxAmount =
		new Field("I8", 16);


	Field taxCountryRegionAzores =
		new Field("J1", 5);

	Field exemptAmountAzores =
		new Field("J2", 16);

	Field reducedTaxableAmountAzores =
		new Field("J3", 16);

	Field reducedTaxAmountAzores =
		new Field("J4", 16);

	Field intermediateTaxableAmountAzores =
		new Field("J5", 16);

	Field intermediateTaxAmountAzores =
		new Field("J6", 16);

	Field regularTaxableAmountAzores =
		new Field("J7", 16);

	Field regularTaxAmountAzores =
		new Field("J8", 16);


	Field taxCountryRegionMadeira =
		new Field("K1", 5);

	Field exemptAmountMadeira =
		new Field("K2", 16);

	Field reducedTaxableAmountMadeira =
		new Field("K3", 16);

	Field reducedTaxAmountMadeira =
		new Field("K4", 16);

	Field intermediateTaxableAmountMadeira =
		new Field("K5", 16);

	Field intermediateTaxAmountMadeira =
		new Field("K6", 16);

	Field regularTaxableAmountMadeira =
		new Field("K7", 16);

	Field regularTaxAmountMadeira =
		new Field("K8", 16);

	Field NSOrNTAmount =
		new Field("L", 16);

	Field stampDuty =
		new Field("M", 16);

	Field taxPayable =
		new Field("N", 16);

	Field grossTotal =
		new Field("O", 16);

	Field withholdingTaxAmount =
		new Field("P", 16);

	Field hash =
		new Field("Q", 4);

	Field certificateNumber =
		new Field("R", 4);

	Field otherInfo =
		new Field("S", 16);


}
