/*
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
package com.premiumminds.billy.core.persistence.entities.ebean;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;

@Entity
@Inheritance
@DiscriminatorColumn(length = 255)
@DiscriminatorValue("JPAAddressEntity")
@Table(name = Config.TABLE_PREFIX + "ADDRESS")
public class JPAAddressEntity extends JPABaseEntity implements AddressEntity {

    private static final long serialVersionUID = 1L;

    // Private field without getters or setters that is needed to map the inverse @OneToMany relation
    // Avoids ownership limitation problem of Ebean http://ebean-orm.github.io/docs/mapping/jpa/oneToMany
    @ManyToOne(targetEntity = JPACustomerEntity.class)
    private JPACustomerEntity customer;

    // Private field without getters or setters that is needed to map the inverse @OneToMany relation
    // Avoids ownership limitation problem of Ebean http://ebean-orm.github.io/docs/mapping/jpa/oneToMany
    @ManyToOne(targetEntity = JPASupplierEntity.class)
    private JPASupplierEntity supplier;

    @Column(name = "NUMBER")
    protected String number;

    @Column(name = "DETAILS")
    protected String details;

    @Column(name = "BUILDING")
    protected String building;

    @Column(name = "CITY")
    protected String city;

    @Column(name = "POSTAL_CODE")
    protected String postalCode;

    @Column(name = "REGION")
    protected String region;

    @Column(name = "COUNTRY")
    protected String country;

    @Column(name = "STREET_NAME")
    protected String streetName;

    public JPAAddressEntity() {
    }

    @Override
    public String getNumber() {
        return this.number;
    }

    @Override
    public String getDetails() {
        return this.details;
    }

    @Override
    public String getBuilding() {
        return this.building;
    }

    @Override
    public String getCity() {
        return this.city;
    }

    @Override
    public String getPostalCode() {
        return this.postalCode;
    }

    @Override
    public String getRegion() {
        return this.region;
    }

    @Override
    public String getISOCountry() {
        return this.country;
    }

    @Override
    public String getStreetName() {
        return this.streetName;
    }

    @Override
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public void setISOCountry(String country) {
        this.country = country;
    }

    @Override
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

}
