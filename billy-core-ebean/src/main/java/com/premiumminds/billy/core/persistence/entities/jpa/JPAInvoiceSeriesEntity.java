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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.entities.Business;

@Entity
@Table(name = Config.TABLE_PREFIX + "INVOICE_SERIES",
        uniqueConstraints = @UniqueConstraint(columnNames = { "SERIES", "ID_BUSINESS" }))
public class JPAInvoiceSeriesEntity extends JPABaseEntity implements InvoiceSeriesEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "SERIES")
    protected String series;

    @ManyToOne(targetEntity = JPABusinessEntity.class)
    @JoinColumn(name = "ID_BUSINESS", referencedColumnName = "ID")
    protected JPABusinessEntity business;

    @Override
    public String getSeries() {
        return this.series;
    }

    @Override
    public Business getBusiness() {
        return this.business;
    }

    @Override
    public void setSeries(String series) {
        this.series = series;
    }

    @Override
    public <T extends Business> void setBusiness(T business) {
        this.business = (JPABusinessEntity) business;
    }
}
