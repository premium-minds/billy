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
package com.premiumminds.billy.core.services.entities;

import java.io.Serializable;
import java.util.Date;

import com.premiumminds.billy.core.services.StringID;

/**
 * @author Francisco Vargas
 *
 * The Billy services basic entity definition. All service entity
 * definitions should extend or implement BaseEntity.
 */
public interface Entity<T> extends Serializable {

    Long getID();

    /**
     * Gets the entity unique identifier.
     *
     * @return The unique identifier.
     */
    StringID<T> getUID();

    /**
     * Sets the unique identifier for the entity. Should only be used in very
     * well controlled contexts.
     *
     * @param stringID The unique identifier to be set.
     */
    void setUID(StringID<T> stringID);

    /**
     * Gets the entity creation date.
     *
     * @return The creation date.
     */
    Date getCreateTimestamp();

    /**
     * Gets the date of the last update of the entity.
     *
     * @return The update date.
     */
    Date getUpdateTimestamp();

    /**
     * Tells whether or not the entity is a new one
     *
     * @return true if the entity is new
     */
    boolean isNew();

    void initializeEntityDates();

}
