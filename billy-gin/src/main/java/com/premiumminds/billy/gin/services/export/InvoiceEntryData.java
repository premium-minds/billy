/**
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

import java.math.BigDecimal;
import java.util.List;

public class InvoiceEntryData {

    private final ProductData product;
    private final String description;
    private final BigDecimal quantity;
    private final BigDecimal taxAmount;
    private final BigDecimal unitAmountWithTax;
    private final BigDecimal amountWithTax;
    private final BigDecimal amountWithoutTax;
    private final List<TaxData> taxes;

    public InvoiceEntryData(ProductData product, String description, BigDecimal quantity, BigDecimal taxAmount,
            BigDecimal unitAmountWithTax, BigDecimal amountWithTax, BigDecimal amountWithoutTax, List<TaxData> taxes) {

        this.product = product;
        this.description = description;
        this.quantity = quantity;
        this.taxAmount = taxAmount;
        this.unitAmountWithTax = unitAmountWithTax;
        this.amountWithTax = amountWithTax;
        this.amountWithoutTax = amountWithoutTax;
        this.taxes = taxes;
    }

    public ProductData getProduct() {
        return this.product;
    }

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public BigDecimal getTaxAmount() {
        return this.taxAmount;
    }

    public BigDecimal getUnitAmountWithTax() {
        return this.unitAmountWithTax;
    }

    public BigDecimal getAmountWithTax() {
        return this.amountWithTax;
    }

    public BigDecimal getAmountWithoutTax() {
        return this.amountWithoutTax;
    }

    public List<TaxData> getTaxes() {
        return this.taxes;
    }

}
