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

	String DATE_FORMAT = "yyyyMMdd";

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

	TaxBreakdown I = new TaxBreakdown(
		new Field("I1", 5),
		new Field("I2", 16),
		new Field("I3", 16),
		new Field("I4", 16),
		new Field("I5", 16),
		new Field("I6", 16),
		new Field("I7", 16),
		new Field("I8", 16)
	);

	TaxBreakdown J = new TaxBreakdown(
		new Field("J1", 5),
		new Field("J2", 16),
		new Field("J3", 16),
		new Field("J4", 16),
		new Field("J5", 16),
		new Field("J6", 16),
		new Field("J7", 16),
		new Field("J8", 16)
	);

	TaxBreakdown K = new TaxBreakdown(
		new Field("K1", 5),
		new Field("K2", 16),
		new Field("K3", 16),
		new Field("K4", 16),
		new Field("K5", 16),
		new Field("K6", 16),
		new Field("K7", 16),
		new Field("K8", 16)
	);

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

	class TaxBreakdown {
		final Field taxCountryRegion;
		final Field exemptAmount;
		final Field reducedTaxableAmount;
		final Field reducedTaxAmount;
		final Field intermediateTaxableAmount;
		final Field intermediateTaxAmount;
		final Field regularTaxableAmount;
		final Field regularTaxAmount;

		public TaxBreakdown(
			final Field taxCountryRegion,
			final Field exemptAmount,
			final Field reducedTaxableAmount,
			final Field reducedTaxAmount,
			final Field intermediateTaxableAmount,
			final Field intermediateTaxAmount,
			final Field regularTaxableAmount,
			final Field regularTaxAmount)
		{
			this.taxCountryRegion = taxCountryRegion;
			this.exemptAmount = exemptAmount;
			this.reducedTaxableAmount = reducedTaxableAmount;
			this.reducedTaxAmount = reducedTaxAmount;
			this.intermediateTaxableAmount = intermediateTaxableAmount;
			this.intermediateTaxAmount = intermediateTaxAmount;
			this.regularTaxableAmount = regularTaxableAmount;
			this.regularTaxAmount = regularTaxAmount;
		}
	}


}
