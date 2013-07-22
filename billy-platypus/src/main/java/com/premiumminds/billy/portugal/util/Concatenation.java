package com.premiumminds.billy.portugal.util;

public class Concatenation {

	public static String concatenatePTSourceHash(String invoiceDate,
			String entryDate, String invoiceNo, String amountWithTax,
			String previousHash) {

		String separator = ";";

		return invoiceDate + separator + entryDate + separator + invoiceNo
				+ separator + amountWithTax + separator + previousHash;

	}
}
