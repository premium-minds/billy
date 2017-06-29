/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core JPA.
 *
 * billy core JPA is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core JPA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core JPA. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.entities.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "GENERIC_INVOICE_ENTRY")
public class JPAGenericInvoiceEntryEntity extends JPABaseEntity implements GenericInvoiceEntryEntity {

    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    @Column(name = "CREDIT_OR_DEBIT")
    protected CreditOrDebit creditOrDebit;

    @Column(name = "CURRENCY")
    protected Currency currency;

    @Column(name = "DESCRIPTION")
    protected String description;

    @ManyToMany(targetEntity = JPAGenericInvoiceEntity.class)
    @JoinTable(name = Config.TABLE_PREFIX + "ENTRY_REFERENCE",
            joinColumns = { @JoinColumn(name = "ID_ENTRY", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "ID_REFERENCE", referencedColumnName = "ID") })
    protected List<GenericInvoice> references;

    @Column(name = "NUMBER")
    protected Integer number;

    @Column(name = "EXCHANGE_RATE_TO_DOCUMENT_CURRENCY", precision = 19, scale = 7)
    protected BigDecimal exchangeRateToDocumentCurrency;

    @Column(name = "AMOUNT_WITHOUT_TAX", precision = 19, scale = 7)
    protected BigDecimal amountWithoutTax;

    @Column(name = "AMOUNT_WITH_TAX", precision = 19, scale = 7)
    protected BigDecimal amountWithTax;

    @Column(name = "TAX_AMOUNT", precision = 19, scale = 7)
    protected BigDecimal taxAmount;

    @Column(name = "DISCOUNT_AMOUNT", precision = 19, scale = 7)
    protected BigDecimal discountAmount;

    @ManyToOne(targetEntity = JPAProductEntity.class)
    @JoinColumn(name = "ID_PRODUCT", referencedColumnName = "ID")
    protected Product product;

    @Column(name = "QUANTITY", precision = 19, scale = 7)
    protected BigDecimal quantity;

    @Column(name = "SHIPPING_COSTS_AMOUNT", precision = 19, scale = 7)
    protected BigDecimal shippingCostsAmount;

    @OneToOne(targetEntity = JPAShippingPointEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_SHIPPING_DESTINATION")
    protected ShippingPoint shippingDestination;

    @OneToOne(targetEntity = JPAShippingPointEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_SHIPPING_ORIGIN")
    protected ShippingPoint shippingOrigin;

    @ManyToMany(targetEntity = JPATaxEntity.class)
    @JoinTable(name = Config.TABLE_PREFIX + "ENTRY_TAX",
            joinColumns = { @JoinColumn(name = "ID_ENTRY", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "ID_TAX", referencedColumnName = "ID") })
    protected List<Tax> taxes;

    @Column(name = "TAX_EXEMPTION_REASON")
    protected String taxExemptionReason;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TAX_POINT_DATE")
    protected Date taxPointDate;

    @Column(name = "UNIT_AMOUNT_WITHOUT_TAX", precision = 19, scale = 7)
    protected BigDecimal unitAmountWithoutTax;

    @Column(name = "UNIT_AMOUNT_WITH_TAX", precision = 19, scale = 7)
    protected BigDecimal unitAmountWithTax;

    @Column(name = "UNIT_TAX_AMOUNT", precision = 19, scale = 7)
    protected BigDecimal unitTaxAmount;

    @Column(name = "UNIT_DISCOUNT_AMOUNT", precision = 19, scale = 7)
    protected BigDecimal unitDiscountAmount;

    @Column(name = "UNIT_OF_MEASURE")
    protected String unitOfMeasure;

    @Column(name = "AMOUNT_TYPE")
    protected AmountType type;

    public JPAGenericInvoiceEntryEntity() {
        this.references = new ArrayList<>();
        this.taxes = new ArrayList<>();
    }

    @Override
    public Integer getEntryNumber() {
        return this.number;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ShippingPoint getShippingOrigin() {
        return this.shippingOrigin;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ShippingPoint getShippingDestination() {
        return this.shippingDestination;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Product getProduct() {
        return this.product;
    }

    @Override
    public BigDecimal getQuantity() {
        return this.quantity;
    }

    @Override
    public String getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    @Override
    public BigDecimal getUnitAmountWithTax() {
        return this.unitAmountWithTax;
    }

    @Override
    public BigDecimal getUnitAmountWithoutTax() {
        return this.unitAmountWithoutTax;
    }

    @Override
    public BigDecimal getUnitDiscountAmount() {
        return this.unitDiscountAmount;
    }

    @Override
    public BigDecimal getUnitTaxAmount() {
        return this.unitTaxAmount;
    }

    @Override
    public BigDecimal getAmountWithTax() {
        return this.amountWithTax;
    }

    @Override
    public BigDecimal getAmountWithoutTax() {
        return this.amountWithoutTax;
    }

    @Override
    public BigDecimal getDiscountAmount() {
        return this.discountAmount;
    }

    @Override
    public BigDecimal getTaxAmount() {
        return this.taxAmount;
    }

    @Override
    public Date getTaxPointDate() {
        return this.taxPointDate;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public CreditOrDebit getCreditOrDebit() {
        return this.creditOrDebit;
    }

    @Override
    public BigDecimal getShippingCostsAmount() {
        return this.shippingCostsAmount;
    }

    @Override
    public Currency getCurrency() {
        return this.currency;
    }

    @Override
    public BigDecimal getExchangeRateToDocumentCurrency() {
        return this.exchangeRateToDocumentCurrency;
    }

    @Override
    public String getTaxExemptionReason() {
        return this.taxExemptionReason;
    }

    @Override
    public AmountType getAmountType() {
        return this.type;
    }

    @Override
    public void setEntryNumber(Integer number) {
        this.number = number;
    }

    @Override
    public <T extends ShippingPoint> void setShippingOrigin(T origin) {
        this.shippingOrigin = origin;
    }

    @Override
    public <T extends ShippingPoint> void setShippingDestination(T destination) {
        this.shippingDestination = destination;
    }

    @Override
    public <T extends Product> void setProduct(T product) {
        this.product = product;
    }

    @Override
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public void setUnitOfMeasure(String unit) {
        this.unitOfMeasure = unit;
    }

    @Override
    public void setUnitAmountWithTax(BigDecimal amount) {
        this.unitAmountWithTax = amount;
    }

    @Override
    public void setUnitAmountWithoutTax(BigDecimal amount) {
        this.unitAmountWithoutTax = amount;
    }

    @Override
    public void setUnitDiscountAmount(BigDecimal amount) {
        this.unitDiscountAmount = amount;
    }

    @Override
    public void setUnitTaxAmount(BigDecimal amount) {
        this.unitTaxAmount = amount;
    }

    @Override
    public void setAmountWithTax(BigDecimal amount) {
        this.amountWithTax = amount;
    }

    @Override
    public void setAmountWithoutTax(BigDecimal amount) {
        this.amountWithoutTax = amount;
    }

    @Override
    public void setDiscountAmount(BigDecimal amount) {
        this.discountAmount = amount;
    }

    @Override
    public void setTaxAmount(BigDecimal amount) {
        this.taxAmount = amount;
    }

    @Override
    public void setTaxPointDate(Date date) {
        this.taxPointDate = date;
    }

    @Override
    public List<GenericInvoice> getDocumentReferences() {
        return this.references;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setCreditOrDebit(CreditOrDebit creditOrDebit) {
        this.creditOrDebit = creditOrDebit;
    }

    @Override
    public void setShippingCostsAmount(BigDecimal amount) {
        this.shippingCostsAmount = amount;
    }

    @Override
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public void setExchangeRateToDocumentCurrency(BigDecimal rate) {
        this.exchangeRateToDocumentCurrency = rate;
    }

    @Override
    public List<Tax> getTaxes() {
        return this.taxes;
    }

    @Override
    public void setTaxExemptionReason(String exemptionReason) {
        this.taxExemptionReason = exemptionReason;
    }

    @Override
    public void setAmountType(AmountType type) {
        this.type = type;
    }

}
