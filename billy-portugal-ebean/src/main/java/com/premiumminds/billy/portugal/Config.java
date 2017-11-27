/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.premiumminds.billy.core.services.UID;

public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    public static final String TABLE_PREFIX = "BILLY_PT_";

    private static final String CONFIGURATIONS_FILE_NAME = "portugal.properties";

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

    public UID getUID(String key) {
        return new UID(this.getUUID(key).toString());
    }

    public UUID getUUID(String key) {
        return UUID.fromString(this.get(key));
    }

    /**
     * Property access keys
     */
    public static class Key {

        public static class Customer {

            public static class Generic {

                public static final String UUID = "customer.generic.uuid";
            }
        }

        public static class Contact {

            public static class Generic {

                public static final String UUID = "contact.generic.uuid";
            }
        }

        public static class Address {

            public static class Generic {

                public static final String UUID = "address.generic.uuid";
            }
        }

        public static class Context {

            public static class Portugal {

                public static final String UUID = "context.portugal.uuid";
                public static final String TAX_EXEMPT_UUID = "context.portugal.tax.exempt.uuid";
                public static final String TAX_EXEMPT_VALUE = "context.portugal.tax.exempt.value";

                public static class Continental {

                    public static final String UUID = "context.portugal.continental.uuid";

                    public static class VAT {

                        public static final String NORMAL_UUID = "context.portugal.continental.tax.vat.normal.uuid";
                        public static final String NORMAL_PERCENT =
                                "context.portugal.continental.tax.vat.normal.percent";

                        public static final String INTERMEDIATE_UUID =
                                "context.portugal.continental.tax.vat.intermediate.uuid";
                        public static final String INTERMEDIATE_PERCENT =
                                "context.portugal.continental.tax.vat.intermediate.percent";

                        public static final String REDUCED_UUID = "context.portugal.continental.tax.vat.reduced.uuid";
                        public static final String REDUCED_PERCENT =
                                "context.portugal.continental.tax.vat.reduced.percent";
                    }

                    public static class Aveiro {

                        public static final String UUID = "context.portugal.aveiro.uuid";
                    }

                    public static class Beja {

                        public static final String UUID = "context.portugal.beja.uuid";
                    }

                    public static class Braga {

                        public static final String UUID = "context.portugal.braga.uuid";
                    }

                    public static class Braganca {

                        public static final String UUID = "context.portugal.braganca.uuid";
                    }

                    public static class CasteloBranco {

                        public static final String UUID = "context.portugal.castelobranco.uuid";
                    }

                    public static class Coimbra {

                        public static final String UUID = "context.portugal.coimbra.uuid";
                    }

                    public static class Evora {

                        public static final String UUID = "context.portugal.evora.uuid";
                    }

                    public static class Faro {

                        public static final String UUID = "context.portugal.faro.uuid";
                    }

                    public static class Guarda {

                        public static final String UUID = "context.portugal.guarda.uuid";
                    }

                    public static class Leiria {

                        public static final String UUID = "context.portugal.leiria.uuid";
                    }

                    public static class Lisboa {

                        public static final String UUID = "context.portugal.lisboa.uuid";
                    }

                    public static class Portalegre {

                        public static final String UUID = "context.portugal.portalegre.uuid";
                    }

                    public static class Porto {

                        public static final String UUID = "context.portugal.porto.uuid";
                    }

                    public static class Santarem {

                        public static final String UUID = "context.portugal.santarem.uuid";
                    }

                    public static class Setubal {

                        public static final String UUID = "context.portugal.setubal.uuid";
                    }

                    public static class Viana {

                        public static final String UUID = "context.portugal.viana.uuid";
                    }

                    public static class VilaReal {

                        public static final String UUID = "context.portugal.vilareal.uuid";
                    }

                    public static class Viseu {

                        public static final String UUID = "context.portugal.viseu.uuid";
                    }
                }

                public static class Madeira {

                    public static final String UUID = "context.portugal.madeira.uuid";

                    public static class VAT {

                        public static final String NORMAL_UUID = "context.portugal.madeira.tax.vat.normal.uuid";
                        public static final String NORMAL_PERCENT = "context.portugal.madeira.tax.vat.normal.percent";

                        public static final String INTERMEDIATE_UUID =
                                "context.portugal.madeira.tax.vat.intermediate.uuid";
                        public static final String INTERMEDIATE_PERCENT =
                                "context.portugal.madeira.tax.vat.intermediate.percent";

                        public static final String REDUCED_UUID = "context.portugal.madeira.tax.vat.reduced.uuid";
                        public static final String REDUCED_PERCENT = "context.portugal.madeira.tax.vat.reduced.percent";
                    }
                }

                public static class Azores {

                    public static final String UUID = "context.portugal.azores.uuid";

                    public static class VAT {

                        public static final String NORMAL_UUID = "context.portugal.azores.tax.vat.normal.uuid";
                        public static final String NORMAL_PERCENT = "context.portugal.azores.tax.vat.normal.percent";

                        public static final String INTERMEDIATE_UUID =
                                "context.portugal.azores.tax.vat.intermediate.uuid";
                        public static final String INTERMEDIATE_PERCENT =
                                "context.portugal.azores.tax.vat.intermediate.percent";

                        public static final String REDUCED_UUID = "context.portugal.azores.tax.vat.reduced.uuid";
                        public static final String REDUCED_PERCENT = "context.portugal.azores.tax.vat.reduced.percent";
                    }
                }
            }
        }
    }

}
