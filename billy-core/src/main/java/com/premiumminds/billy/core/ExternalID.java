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
package com.premiumminds.billy.core;

import java.io.Serializable;

public final class ExternalID<I> implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String value;

    private ExternalID(String value) {
        this.value = value;
    }

    public String getIdentifier() {
        return this.value;
    }

    public static <I> ExternalID<I> fromValue(String value) {
        return new ExternalID<>(value);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof ExternalID)) {
            return false;
        } else {
            ExternalID<?> other = (ExternalID)obj;
            if (this.value == null) {
                return other.value == null;
            } else {
                return this.value.equals(other.value);
            }
        }
    }

    public int hashCode() {
        int result = 1;
        result = 31 * result + (this.value == null ? 0 : this.value.hashCode());
        return result;
    }

    public String toString() {
        return this.value;
    }
}
