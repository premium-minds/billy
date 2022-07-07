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
package com.premiumminds.billy.core.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UIDTest {

    @Test
    public void test() {

        UID uid1 = new UID("1583e00b-7698-4dec-82db-4f812ec273b8");
        UID uid2 = new UID("1583e00b-7698-4dec-82db-4f812ec273b8");

        // equals consistency: objects that are equal to each other must return the same hashCode
        assertEquals(uid1, uid2);
        assertEquals(uid1.hashCode(), uid2.hashCode());

    }
}