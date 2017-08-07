/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core Ebean.
 *
 * billy core Ebean is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core Ebean is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core Ebean. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.entities.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.CustomerEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.ShippingPointEntity;
import com.premiumminds.billy.core.persistence.entities.SupplierEntity;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.Supplier;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;

@Entity
@Table(name = Config.TABLE_PREFIX + "GENERIC_INVOICE",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "NUMBER", "ID_BUSINESS" }),
                @UniqueConstraint(columnNames = { "SERIES", "SERIES_NUMBER", "ID_BUSINESS" }) })
public class JPAGenericInvoiceEntity extends JPABaseEntity implements GenericInvoiceEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "NUMBER")
    protected String number;

    @Column(name = "SERIES")
    protected String series;

    @Column(name = "SERIES_NUMBER")
    protected Integer seriesNumber;

    @ManyToOne(targetEntity = JPABusinessEntity.class)
    @JoinColumn(name = "ID_BUSINESS", referencedColumnName = "ID")
    protected Business business;

    @ManyToOne(targetEntity = JPACustomerEntity.class)
    @JoinColumn(name = "ID_CUSTOMER", referencedColumnName = "ID")
    protected Customer customer;

    @ManyToOne(targetEntity = JPASupplierEntity.class)
    @JoinColumn(name = "ID_SUPPLIER", referencedColumnName = "ID")
    protected Supplier supplier;

    @Column(name = "OFFICE_NUMBER")
    protected String officeNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE")
    protected Date date;

    @Column(name = "AMOUNT_WITH_TAX", precision = 19, scale = 7)
    protected BigDecimal amountWithTax;

    @Column(name = "TAX_AMOUNT", precision = 19, scale = 7)
    protected BigDecimal taxAmount;

    @Column(name = "AMOUNT_WITHOUT_TAX", precision = 19, scale = 7)
    protected BigDecimal amountWithoutTax;

    @Column(name = "DISCOUNTS_AMOUNT", precision = 19, scale = 7)
    protected BigDecimal discountsAmount;

    @OneToOne(targetEntity = JPAShippingPointEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_SHIPPING_POINT_ORIGIN", referencedColumnName = "ID")
    protected ShippingPoint shippingOrigin;

    @OneToOne(targetEntity = JPAShippingPointEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ID_SHIPPING_POINT_DESTINATION", referencedColumnName = "ID")
    protected ShippingPoint shippingDestination;

    @Column(name = "PAYMENT_TERMS")
    protected String paymentTerms;

    @Column(name = "SELF_BILLED")
    protected Boolean selfBilled;

    @Column(name = "CASH_VAT_ENDORSER")
    protected Boolean cashVATEndorser;

    @Column(name = "THIRD_PARTY_BILLED")
    protected Boolean thirdPartyBilled;

    @Column(name = "SOURCE_ID")
    protected String sourceId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "GENERAL_LEDGER_DATE")
    protected Date generalLedgerDate;

    @Column(name = "BATCH_ID")
    protected String batchId;

    @Column(name = "TRANSACTION_ID")
    protected String transactionId;

    @Column(name = "CURRENCY")
    protected Currency currency;

    @Column(name = "SETTLEMENT_DESCRIPTION")
    protected String settlementDescription;

    @Column(name = "SETTLEMENT_DISCOUNT", precision = 19, scale = 7)
    protected BigDecimal settlementDiscount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SETTLEMENT_DATE")
    protected Date settlementDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "CREDIT_OR_DEBIT")
    protected CreditOrDebit creditOrDebit;

    @Column(name = "SCALE")
    protected Integer scale;

    @OneToMany(targetEntity = JPAGenericInvoiceEntryEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    protected List<GenericInvoiceEntry> entries;

    @OneToMany(targetEntity = JPAPaymentEntity.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    protected List<Payment> payments;

    public JPAGenericInvoiceEntity() {
        this.entries = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    @Override
    public Integer getScale() {
        return this.getScale();
    }

    @Override
    public String getNumber() {
        return this.number;
    }

    @Override
    public String getSeries() {
        return this.series;
    }

    @Override
    public Business getBusiness() {
        return this.business;
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Supplier getSupplier() {
        return this.supplier;
    }

    @Override
    public String getOfficeNumber() {
        return this.officeNumber;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public BigDecimal getAmountWithTax() {
        return this.amountWithTax;
    }

    @Override
    public BigDecimal getTaxAmount() {
        return this.taxAmount;
    }

    @Override
    public BigDecimal getAmountWithoutTax() {
        return this.amountWithoutTax;
    }

    @Override
    public BigDecimal getDiscountsAmount() {
        return this.discountsAmount;
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

    @Override
    public String getPaymentTerms() {
        return this.paymentTerms;
    }

    @Override
    public Boolean isSelfBilled() {
        return this.selfBilled;
    }

    @Override
    public String getSourceId() {
        return this.sourceId;
    }

    @Override
    public Date getGeneralLedgerDate() {
        return this.generalLedgerDate;
    }

    @Override
    public String getBatchId() {
        return this.batchId;
    }

    @Override
    public String getTransactionId() {
        return this.transactionId;
    }

    @Override
    public Currency getCurrency() {
        return this.currency;
    }

    @Override
    public String getSettlementDescription() {
        return this.settlementDescription;
    }

    @Override
    public BigDecimal getSettlementDiscount() {
        return this.settlementDiscount;
    }

    @Override
    public Date getSettlementDate() {
        return this.settlementDate;
    }

    @Override
    public CreditOrDebit getCreditOrDebit() {
        return this.creditOrDebit;
    }

    @Override
    public Integer getSeriesNumber() {
        return this.seriesNumber;
    }

    @Override
    public void setSeriesNumber(Integer seriesNumber) {
        this.seriesNumber = seriesNumber;
    }

    @Override
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void setSeries(String series) {
        this.series = series;
    }

    @Override
    public <T extends Business> void setBusiness(T business) {
        this.business = business;
    }

    @Override
    public <T extends CustomerEntity> void setCustomer(T customer) {
        this.customer = customer;
    }

    @Override
    public <T extends SupplierEntity> void setSupplier(T supplier) {
        this.supplier = supplier;
    }

    @Override
    public void setOfficeNumber(String number) {
        this.officeNumber = number;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public void setAmountWithTax(BigDecimal amount) {
        this.amountWithTax = amount;
    }

    @Override
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    @Override
    public void setAmountWithoutTax(BigDecimal amount) {
        this.amountWithoutTax = amount;
    }

    @Override
    public void setDiscountsAmount(BigDecimal amount) {
        this.discountsAmount = amount;
    }

    @Override
    public <T extends ShippingPointEntity> void setShippingOrigin(T origin) {
        this.shippingOrigin = origin;
    }

    @Override
    public <T extends ShippingPointEntity> void setShippingDestination(T destination) {
        this.shippingDestination = destination;
    }

    @Override
    public void setPaymentTerms(String terms) {
        this.paymentTerms = terms;
    }

    @Override
    public void setSelfBilled(Boolean selfBilled) {
        this.selfBilled = selfBilled;
    }

    @Override
    public void setSourceId(String source) {
        this.sourceId = source;
    }

    @Override
    public void setGeneralLedgerDate(Date date) {
        this.generalLedgerDate = date;
    }

    @Override
    public void setBatchId(String id) {
        this.batchId = id;
    }

    @Override
    public void setTransactionId(String id) {
        this.transactionId = id;
    }

    @Override
    public List<String> getReceiptNumbers() {
        // Unused. Needed to Override because of billy-core.
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<? extends GenericInvoiceEntry> getEntries() {
        return this.entries;
    }

    @Override
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public void setSettlementDescription(String description) {
        this.settlementDescription = description;
    }

    @Override
    public void setSettlementDiscount(BigDecimal discount) {
        this.settlementDiscount = discount;
    }

    @Override
    public void setSettlementDate(Date date) {
        this.settlementDate = date;
    }

    @Override
    public void setCreditOrDebit(CreditOrDebit creditOrDebit) {
        this.creditOrDebit = creditOrDebit;
    }

    @Override
    public void setScale(Integer scale) {
        this.scale = scale;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<? extends Payment> getPayments() {
        return this.payments;
    }

    @Override
    public Boolean isCashVATEndorser() {
        return this.cashVATEndorser;
    }

    @Override
    public Boolean isThirdPartyBilled() {
        return this.thirdPartyBilled;
    }
}
