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

import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.Validate;

import com.premiumminds.billy.core.persistence.entities.BaseEntity;
import com.premiumminds.billy.core.services.UID;

import io.ebean.Ebean;

@MappedSuperclass
public abstract class JPABaseEntity implements BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    protected Integer id;

    @Basic(optional = false)
    @Column(name = "UID", nullable = false, insertable = true, updatable = false, unique = true)
    protected String uid;

    @Basic(optional = false)
    @Column(name = "UID_ROW", nullable = false, insertable = true, updatable = false, unique = true)
    protected String uidRow;

    @Basic(optional = false)
    @Column(name = "ENTITY_VERSION", nullable = false, insertable = true, updatable = false, unique = false)
    protected int entityVersion;

    @Column(name = "CREATE_TIMESTAMP", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createTimestamp;

    @Column(name = "UPDATE_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updateTimestamp;

    @Column(name = "ACTIVE")
    protected Boolean active;

    public JPABaseEntity() {
        this.uid = this.generateUUID().toString();
    }

    @Override
    public boolean isNew() {
        return Ebean.getBeanState(this).isNew();
    }

    @PrePersist
    public void onPersist() {
        if (this.isNew()) {
            this.uidRow = this.generateUUID().toString();
            this.entityVersion = 1;
            this.active = true;
            if (this.createTimestamp == null) {
                this.initializeEntityDates();
            }
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updateTimestamp = new Date();
    }

    @Override
    public Integer getID() {
        return this.id;
    }

    @Override
    public UID getUID() {
        return new UID(this.uid);
    }

    @Override
    public void setUID(UID uid) {
        Validate.notNull(uid);
        this.uid = uid.toString();
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
        if (!this.isNew()) {
            throw new RuntimeException("Cannot redefine the creation date for a persisted entity");
        }
        this.updateTimestamp = this.createTimestamp = new Date();
    }

    protected UUID generateUUID() {
        return UUID.randomUUID();
    }

}
