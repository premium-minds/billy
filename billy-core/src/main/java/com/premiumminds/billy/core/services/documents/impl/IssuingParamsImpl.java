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
package com.premiumminds.billy.core.services.documents.impl;

import java.util.HashMap;
import java.util.Map;

import com.premiumminds.billy.core.services.documents.IssuingParams;
import com.premiumminds.billy.core.services.exceptions.ParameterNotFoundException;

public class IssuingParamsImpl implements IssuingParams {

    Map<String, Object> parameters;

    public IssuingParamsImpl() {
        this.parameters = new HashMap<>();
    }

    @Override
    public Object getParameter(String key) throws ParameterNotFoundException {
        if (this.parameters.containsKey(key)) {
            return this.parameters.get(key);
        } else {
            return null;
        }
    }

    @Override
    public void setParameter(String key, Object obj) {
        this.parameters.put(key, obj);
    }

}
