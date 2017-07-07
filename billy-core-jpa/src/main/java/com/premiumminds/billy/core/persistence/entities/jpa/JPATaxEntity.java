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
import java.util.Currency;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.persistence.entities.TaxEntity;
import com.premiumminds.billy.core.services.entities.Context;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "TAX")
public class JPATaxEntity extends JPABaseEntity implements TaxEntity {

	private static final long	serialVersionUID	= 1L;

	@Column(name = "CODE")
	protected String			code;

	@ManyToOne(targetEntity = JPAContextEntity.class)
	@JoinColumn(name = "ID_CONTEXT", referencedColumnName = "ID")
	protected Context			context;

	@Column(name = "CURRENCY")
	protected Currency			currency;

	@Column(name = "DESCRIPTION")
	protected String			description;

	@Column(name = "DESIGNATION")
	protected String			designation;

	@Column(name = "FLAT_RATE_AMOUNT", precision=19, scale = 7)
	protected BigDecimal		flatRateAmount;

	@Column(name = "PERCENT_RATE_VALUE", precision=19, scale = 7)
	protected BigDecimal		percentageRateValue;

	@Enumerated(EnumType.STRING)
	@Column(name = "RATE_TYPE")
	protected TaxRateType		taxRateType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VALID_FROM")
	protected Date				validFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VALID_TO")
	protected Date				validTo;

	@Column(name = "VALUE", precision=19, scale = 7)
	protected BigDecimal		value;

	public JPATaxEntity() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public Context getContext() {
		return this.context;
	}

	@Override
	public String getDesignation() {
		return this.designation;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public BigDecimal getValue() {
		return this.value;
	}

	@Override
	public Date getValidFrom() {
		return this.validFrom;
	}

	@Override
	public Date getValidTo() {
		return this.validTo;
	}

	@Override
	public TaxRateType getTaxRateType() {
		return this.taxRateType;
	}

	@Override
	public BigDecimal getPercentageRateValue() {
		return this.percentageRateValue;
	}

	@Override
	public BigDecimal getFlatRateAmount() {
		return this.flatRateAmount;
	}

	@Override
	public Currency getCurrency() {
		return this.currency;
	}

	@Override
	public <T extends ContextEntity> void setContext(T context) {
		this.context = context;
	}

	@Override
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public void setValidFrom(Date from) {
		this.validFrom = from;
	}

	@Override
	public void setValidTo(Date to) {
		this.validTo = to;
	}

	@Override
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public void setTaxRateType(TaxRateType type) {
		this.taxRateType = type;
	}

	@Override
	public void setPercentageRateValue(BigDecimal value) {
		this.percentageRateValue = value;
	}

	@Override
	public void setFlatRateAmount(BigDecimal amount) {
		this.flatRateAmount = amount;
	}

	@Override
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
