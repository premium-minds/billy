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
package com.premiumminds.billy.core.util;

import java.util.Collection;

import org.apache.commons.lang3.Validate;

public class BillyValidator extends Validate {

    private static BillyValidator instance = new BillyValidator();

    private final Localizer localizer;

    private BillyValidator() {
        this.localizer = new Localizer("com/premiumminds/billy/core/i18n/Validation");
    }

    public static <T> T mandatory(T o, String fieldName) {
        Validate.notNull(o, BillyValidator.instance.localizer.getString("invalid.mandatory", fieldName), o);
        return o;
    }

    public static <T extends CharSequence> T mandatory(T o, String fieldName) {
        Validate.notNull(o, BillyValidator.instance.localizer.getString("invalid.mandatory", fieldName), o);
        Validate.notBlank(o, BillyValidator.instance.localizer.getString("invalid.mandatory", fieldName), o);
        return o;
    }

    public static <T> T notNull(T object, String fieldName) {
        Validate.notNull(object, BillyValidator.instance.localizer.getString("invalid.null", fieldName), fieldName);
        return object;
    }

    public static <T extends CharSequence> T notBlank(T object, String fieldName) {
        Validate.notBlank(object, BillyValidator.instance.localizer.getString("invalid.blank", fieldName), fieldName);
        return object;
    }

    public static <T extends CharSequence> T notBlankButNull(T object, String fieldName) {
        if (null != object) {
            Validate.notBlank(object, BillyValidator.instance.localizer.getString("invalid.blank", fieldName),
                    fieldName);
        }
        return object;
    }

    public static <T extends Collection<?>> T notEmpty(T object, String fieldName) {
        Validate.notEmpty(object, BillyValidator.instance.localizer.getString("invalid.empty", fieldName), fieldName);
        return object;
    }

    public static <T> T found(T o, String fieldName) {
        Validate.notNull(o, BillyValidator.instance.localizer.getString("invalid.not_found", fieldName), o);
        return o;
    }

}
