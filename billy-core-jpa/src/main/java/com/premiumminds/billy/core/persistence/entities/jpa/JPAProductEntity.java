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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.ProductEntity;
import com.premiumminds.billy.core.services.entities.Tax;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "PRODUCT")
public class JPAProductEntity extends JPABaseEntity implements ProductEntity {

  private static final long serialVersionUID = 1L;

  @Column(name = "PRODUCT_CODE")
  protected String productCode;

  @Column(name = "PRODUCT_GROUP")
  protected String group;

  @Column(name = "DESCRIPTION")
  protected String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "TYPE")
  protected ProductType type;

  @Column(name = "COMMODITY_CODE")
  protected String commodityCode;

  @Column(name = "NUMBER_CODE")
  protected String numberCode;

  @Column(name = "VALUATION_METHOD")
  protected String valuationMethod;

  @Column(name = "UNIT_OF_MEASURE")
  protected String unitOfMeasure;

  @ManyToMany(targetEntity = JPATaxEntity.class)
  @JoinTable(name = Config.TABLE_PREFIX + "PRODUCT_TAX", joinColumns = {
      @JoinColumn(name = "ID_PRODUCT", referencedColumnName = "ID") }, inverseJoinColumns = {
          @JoinColumn(name = "ID_TAX", referencedColumnName = "ID") })
  protected List<Tax> taxes;

  public JPAProductEntity() {
    this.taxes = new ArrayList<Tax>();
  }

  @Override
  public String getProductCode() {
    return this.productCode;
  }

  @Override
  public String getProductGroup() {
    return this.group;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  @Override
  public ProductType getType() {
    return this.type;
  }

  @Override
  public String getCommodityCode() {
    return this.commodityCode;
  }

  @Override
  public String getNumberCode() {
    return this.numberCode;
  }

  @Override
  public String getValuationMethod() {
    return this.valuationMethod;
  }

  @Override
  public String getUnitOfMeasure() {
    return this.unitOfMeasure;
  }

  @Override
  public void setProductCode(String code) {
    this.productCode = code;
  }

  @Override
  public void setProductGroup(String group) {
    this.group = group;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public void setType(ProductType type) {
    this.type = type;
  }

  @Override
  public void setCommodityCode(String code) {
    this.commodityCode = code;
  }

  @Override
  public void setNumberCode(String code) {
    this.numberCode = code;
  }

  @Override
  public void setValuationMethod(String method) {
    this.valuationMethod = method;
  }

  @Override
  public void setUnitOfMeasure(String unit) {
    this.unitOfMeasure = unit;
  }

  @Override
  public List<Tax> getTaxes() {
    return this.taxes;
  }

}
