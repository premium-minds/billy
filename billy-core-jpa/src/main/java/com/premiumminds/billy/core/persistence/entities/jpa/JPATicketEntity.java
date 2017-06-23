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

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.TicketEntity;
import com.premiumminds.billy.core.services.UID;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "TICKET")
public class JPATicketEntity extends JPABaseEntity implements TicketEntity {

  private static final long serialVersionUID = 1L;

  @Basic(optional = true)
  @Column(name = "OBJECT_UID", updatable = true, insertable = true)
  protected String objectUID;

  @Basic(optional = true)
  @Column(name = "CREATION_DATE", updatable = true, insertable = true)
  @Temporal(TemporalType.TIMESTAMP)
  protected Date creationDate;

  @Basic(optional = true)
  @Column(name = "PROCESS_DATE", updatable = true, insertable = true)
  @Temporal(TemporalType.TIMESTAMP)
  protected Date processDate;

  @Override
  public UID getObjectUID() {
    return new UID(this.objectUID);
  }

  @Override
  public Date getCreationDate() {
    return this.creationDate;
  }

  @Override
  public Date getProcessDate() {
    return this.processDate;
  }

  @Override
  public void setObjectUID(UID objectUID) {
    this.objectUID = objectUID.getValue();
  }

  @Override
  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  @Override
  public void setProcessDate(Date processDate) {
    this.processDate = processDate;
  }

}
