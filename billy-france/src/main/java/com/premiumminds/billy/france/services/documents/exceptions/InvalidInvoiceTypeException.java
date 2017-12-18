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
package com.premiumminds.billy.france.services.documents.exceptions;

public class InvalidInvoiceTypeException extends FRDocumentIssuingException {

    private static final long serialVersionUID = 1L;

    public InvalidInvoiceTypeException(String series, String type, String expectedType) {
        super("Invalid invoice type " + type + "in series " + series + " when expected " + expectedType);
    }

    public InvalidInvoiceTypeException(String series, String type) {
        super("Invalid invoice type " + type + "in series " + series);
    }
}
