/**
 * Copyright (C) 2013 Premium Minds.
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
import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.persistence.entities.BaseEntity;
import com.premiumminds.billy.core.services.UID;

/**
 * @author Francisco Vargas
 * 
 *         The Billy JPA implementation of {@link JPABaseEntity}
 */
@MappedSuperclass
@Audited
public abstract class JPABaseEntity implements BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
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

	/**
	 * Constructor
	 */
	public JPABaseEntity() {
		this.uid = this.generateUUID().toString();
		this.updateTimestamp = this.createTimestamp = new Date();
	}

	@Override
	public boolean isNew() {
		return this.id == null;
	}

	@PrePersist
	protected void onPersist() {
		if (this.isNew()) {
			this.uidRow = this.generateUUID().toString();
			this.entityVersion = 1;
			this.active = true;
		}
	}

	@PreUpdate
	protected void onUpdate() {
		this.updateTimestamp = new Date();
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

	protected UUID generateUUID() {
		return UUID.randomUUID();
	}

}
