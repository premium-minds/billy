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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.Config;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.entities.Context;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "CONTEXT")
public class JPAContextEntity extends JPABaseEntity<Context> implements ContextEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "DESCRIPTION")
    protected String description;

    @ManyToOne(targetEntity = JPAContextEntity.class)
    @JoinColumn(name = "ID_CONTEXT_PARENT", referencedColumnName = "ID")
    protected Context parent;

    public JPAContextEntity() {
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Context getParentContext() {
        return this.parent;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public <T extends ContextEntity> void setParentContext(T parent) {
        this.parent = parent;
    }

}
