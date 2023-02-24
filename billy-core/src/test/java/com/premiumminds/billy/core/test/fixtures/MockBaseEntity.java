/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.test.fixtures;

import java.util.Date;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Entity;

public class MockBaseEntity<T> implements Entity<T> {

    private static final long serialVersionUID = 1L;

    public Long id;
    public StringID<T> uid;
    public Date createTimestamp;
    public Date updateTimestamp;
    public boolean isNew;

    public MockBaseEntity() {
        this.isNew = true;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Override
    public Long getID() {
        return this.id;
    }

    @Override
    public StringID<T> getUID() {
        return this.uid;
    }

    @Override
    public void setUID(StringID<T> uid) {
        this.uid = uid;
    }

    @Override
    public Date getCreateTimestamp() {
        return this.createTimestamp;
    }

    @Override
    public Date getUpdateTimestamp() {
        return this.updateTimestamp;
    }

    @Override
    public void initializeEntityDates() {
        this.createTimestamp = this.updateTimestamp = new Date();
    }

}
