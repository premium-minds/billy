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
package com.premiumminds.billy.france.persistence.entities.jpa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.france.Config;
import com.premiumminds.billy.france.persistence.entities.FRSimpleInvoiceEntity;
import com.premiumminds.billy.france.services.entities.FRInvoiceEntry;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "SIMPLE_INVOICE")
public class JPAFRSimpleInvoiceEntity extends JPAFRInvoiceEntity implements FRSimpleInvoiceEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "CLIENT_TYPE")
    protected CLIENTTYPE clientType;

    @Override
    public List<FRInvoiceEntry> getEntries() {
        return super.getEntries();
    }

    @Override
    public CLIENTTYPE getClientType() {
        return this.clientType;
    }

    @Override
    public void setClientType(CLIENTTYPE type) {
        this.clientType = type;
    }

}
