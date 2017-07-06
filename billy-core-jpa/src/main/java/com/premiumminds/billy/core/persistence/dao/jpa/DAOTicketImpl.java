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
package com.premiumminds.billy.core.persistence.dao.jpa;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.management.remote.NotificationResult;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.mysema.query.jpa.impl.JPAQuery;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.DAOTicket;
import com.premiumminds.billy.core.persistence.entities.TicketEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPATicketEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.QJPATicketEntity;
import com.premiumminds.billy.core.services.UID;

public class DAOTicketImpl extends AbstractDAO<TicketEntity, JPATicketEntity> implements DAOTicket {

  @Inject
  public DAOTicketImpl(Provider<EntityManager> emProvider) {
    super(emProvider);
  }

  @Override
  protected Class<? extends JPATicketEntity> getEntityClass() {
    return JPATicketEntity.class;
  }

  @Override
  public TicketEntity getEntityInstance() {
    return new JPATicketEntity();
  }

  @Override
  public UID getObjectEntityUID(String ticketUID) throws NoResultException {
    QJPATicketEntity ticket = QJPATicketEntity.jPATicketEntity;

    JPAQuery query = new JPAQuery(this.getEntityManager());

    TicketEntity ticketEntity = query.from(ticket).where(ticket.uid.eq(ticketUID))
        .uniqueResult(ticket);

    if (ticketEntity == null) {
      throw new NoResultException();
    }

    return ticketEntity.getObjectUID();
  }
}
