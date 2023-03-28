/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.persistence.entities.jpa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.andorra.Config;
import com.premiumminds.billy.andorra.persistence.entities.ADSimpleInvoiceEntity;
import com.premiumminds.billy.andorra.services.entities.ADInvoiceEntry;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "SIMPLE_INVOICE")
public class JPAADSimpleInvoiceEntity extends JPAADInvoiceEntity implements ADSimpleInvoiceEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "CLIENT_TYPE")
    protected CLIENTTYPE clientType;

    @Override
    public List<ADInvoiceEntry> getEntries() {
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
