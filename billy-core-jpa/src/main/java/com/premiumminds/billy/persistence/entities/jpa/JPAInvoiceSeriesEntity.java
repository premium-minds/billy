/*
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
package com.premiumminds.billy.persistence.entities.jpa;

import java.util.Optional;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.InvoiceSeries;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "INVOICE_SERIES",
        uniqueConstraints = @UniqueConstraint(columnNames = { "SERIES", "ID_BUSINESS" }))
public class JPAInvoiceSeriesEntity extends JPABaseEntity<InvoiceSeries> implements InvoiceSeriesEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "SERIES")
    protected String series;

    @ManyToOne(targetEntity = JPABusinessEntity.class)
    @JoinColumn(name = "ID_BUSINESS", referencedColumnName = "ID")
    protected Business business;

    @Column(name = "SERIES_UNIQUE_CODE")
    protected String seriesUniqueCode;

    @Override
    public String getSeries() {
        return this.series;
    }

    @Override
    public Optional<String> getSeriesUniqueCode() {
        return Optional.ofNullable(seriesUniqueCode);
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
        this.business = business;

    }

    @Override
    public void setSeriesUniqueCode(final String seriesUniqueCode) {
        this.seriesUniqueCode = seriesUniqueCode;
    }
}
