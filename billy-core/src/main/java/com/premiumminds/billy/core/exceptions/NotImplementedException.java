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
package com.premiumminds.billy.core.exceptions;

import com.premiumminds.billy.core.util.NotImplemented;

/**
 * @author Francisco Vargas
 *
 *         An exception to be thrown when a functionality is not implemented
 */
public class NotImplementedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    @NotImplemented
    public NotImplementedException() {
        super();
    }

    /**
     * A Constructor which takes a message
     *
     * @param string
     *        The exception message.
     */
    public NotImplementedException(String string) {
        super(string);
    }

}
