/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.premiumminds.billy.core.services.UID;

public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    public static final String TABLE_PREFIX = "BILLY_FR_";

    private static final String CONFIGURATIONS_FILE_NAME = "france.properties";

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

    // CONFIG
    /**
     * Property access keys
     */
    public static class Key {

        public static class Context {

            public static class France {

                public static final String UUID = "context.france.uuid";
                public static final String TAX_EXEMPT_UUID = "context.france.tax.exempt.uuid";
                public static final String TAX_EXEMPT_VALUE = "context.france.tax.exempt.value";

                public static class Continental {

                    public static final String UUID = "context.france.continental.uuid";

                    public static class VAT {

                        public static final String NORMAL_UUID = "context.france.continental.tax.vat.normal.uuid";
                        public static final String NORMAL_PERCENT = "context.france.continental.tax.vat.normal.percent";

                        public static final String INTERMEDIATE_UUID =
                                "context.france.continental.tax.vat.intermediate.uuid";
                        public static final String INTERMEDIATE_PERCENT =
                                "context.france.continental.tax.vat.intermediate.percent";

                        public static final String REDUCED_UUID = "context.france.continental.tax.vat.reduced.uuid";
                        public static final String REDUCED_PERCENT =
                                "context.france.continental.tax.vat.reduced.percent";

                        public static final String SUPER_REDUCED_UUID = "context.france.continental.tax.vat.super_reduced.uuid";
                        public static final String SUPER_REDUCED_PERCENT =
                                "context.france.continental.tax.vat.super_reduced.percent";
                    }
                    
                    public static class Alsace {

                    	public static final String UUID = "context.france.alsace.uuid";
                    }

                    public static class Aquitaine {

                    	public static final String UUID = "context.france.aquitaine.uuid";
                    }

                    public static class Auvergne {

                    	public static final String UUID = "context.france.auvergne.uuid";
                    }

                    public static class BasseNormandie {

                    	public static final String UUID = "context.france.bassenormandie.uuid";
                    }

                    public static class Bourgogne {

                    	public static final String UUID = "context.france.bourgogne.uuid";
                    }

                    public static class Bretagne {

                    	public static final String UUID = "context.france.bretagne.uuid";
                    }

                    public static class Centre {

                    	public static final String UUID = "context.france.centre.uuid";
                    }

                    public static class Champagne_Ardenne {

                    	public static final String UUID = "context.france.champagne_ardenne.uuid";
                    }

                    public static class Corse {

                    	public static final String UUID = "context.france.corse.uuid";
                    }

                    public static class Franche_Comte {

                    	public static final String UUID = "context.france.franche_comte.uuid";
                    }

                    public static class HauteNormandie {

                    	public static final String UUID = "context.france.hautenormandie.uuid";
                    }

                    public static class Ile_de_France {

                    	public static final String UUID = "context.france.ile_de_france.uuid";
                    }

                    public static class Languedoc_Roussillon {

                    	public static final String UUID = "context.france.languedoc_roussillon.uuid";
                    }

                    public static class Limousin {

                    	public static final String UUID = "context.france.limousin.uuid";
                    }

                    public static class Lorraine {

                    	public static final String UUID = "context.france.lorraine.uuid";
                    }

                    public static class MidiPyrenees {

                    	public static final String UUID = "context.france.midipyrenees.uuid";
                    }

                    public static class Nord_Pas_de_Calais {

                    	public static final String UUID = "context.france.nord_pas_de_calais.uuid";
                    }

                    public static class Pays_de_la_Loire {

                    	public static final String UUID = "context.france.pays_de_la_loire.uuid";
                    }

                    public static class Picardie {

                    	public static final String UUID = "context.france.picardie.uuid";
                    }

                    public static class Poitou_Charentes {

                    	public static final String UUID = "context.france.poitou_charentes.uuid";
                    }

                    public static class Provence_Alpes_Cote_d_Azur {

                    	public static final String UUID = "context.france.provence_alpes_cote_d_azur.uuid";
                    }

                    public static class RhoneAlpes {

                    	public static final String UUID = "context.france.rhonealpes.uuid";
                    }
                    
                }

            }
        }
    }

}
