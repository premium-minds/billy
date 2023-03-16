/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.premiumminds.billy.core.services.StringID;

public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    public static final String TABLE_PREFIX = "BILLY_AD_";

    private static final String CONFIGURATIONS_FILE_NAME = "andorra.properties";

    private Properties properties;

    public Config() {
        this.properties = this.load();
        this.validateProperties(this.properties);
    }

    private Properties load() {
        InputStream stream =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(Config.CONFIGURATIONS_FILE_NAME);
        Properties properties = new Properties();
        try {
            properties.load(stream);
            stream.close();
        } catch (IOException e) {
            Config.log.error(e.getMessage(), e);
            throw new RuntimeException();
        }
        return properties;
    };

    private void validateProperties(Properties properties) {
        return; // TODO
    }

    public String get(String key) {
        return this.properties.getProperty(key);
    }

    public <T> StringID<T> getUID(String key) {
        return StringID.fromValue(this.get(key));
    }

    /**
     * Property access keys
     */
    public static class Key {

        public static class Context {

            public static class Andorra {

                public static final String UUID = "context.andorra.uuid";
                public static final String TAX_EXEMPT_UUID = "context.andorra.tax.exempt.uuid";
                public static final String TAX_EXEMPT_VALUE = "context.andorra.tax.exempt.value";

                public static class VAT {

                    public static final String INCREASED_UUID = "context.andorra.tax.vat.increased.uuid";
                    public static final String INCREASED_PERCENT = "context.andorra.tax.vat.increased.percent";
                    public static final String NORMAL_UUID = "context.andorra.tax.vat.normal.uuid";
                    public static final String NORMAL_PERCENT = "context.andorra.tax.vat.normal.percent";
                    public static final String SPECIAL_UUID = "context.andorra.tax.vat.special.uuid";
                    public static final String SPECIAL_PERCENT = "context.andorra.tax.vat.special.percent";
                    public static final String INTERMEDIATE_UUID = "context.andorra.tax.vat.intermediate.uuid";
                    public static final String INTERMEDIATE_PERCENT = "context.andorra.tax.vat.intermediate.percent";
                    public static final String REDUCED_UUID = "context.andorra.tax.vat.reduced.uuid";
                    public static final String REDUCED_PERCENT = "context.andorra.tax.vat.reduced.percent";
                }

                public static class AndorraLaVieja {

                    public static final String UUID = "context.andorra.andorralavieja.uuid";
                }

                public static class Canillo {

                    public static final String UUID = "context.andorra.canillo.uuid";
                }

                public static class Encamp {

                    public static final String UUID = "context.andorra.encamp.uuid";
                }

                public static class LasEscaldasEngordany {

                    public static final String UUID = "context.andorra.lasescaldasengordany.uuid";
                }

                public static class LaMassana {

                    public static final String UUID = "context.andorra.lamassana.uuid";
                }

                public static class Ordino {

                    public static final String UUID = "context.andorra.ordino.uuid";
                }

                public static class SanJulianDeLoria {

                    public static final String UUID = "context.andorra.sanjuliandeloria.uuid";
                }
            }
        }
    }

}
