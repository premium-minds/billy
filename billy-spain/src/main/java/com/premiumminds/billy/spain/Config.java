/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import com.premiumminds.billy.core.services.UID;

public class Config {

	public static final String	TABLE_PREFIX				= "BILLY_ES_";

	private static final String	CONFIGURATIONS_FILE_NAME	= "spain.properties";

	private Properties			properties;

	public Config() {
		this.properties = this.load();
		this.validateProperties(this.properties);
	}

	private Properties load() {
		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(Config.CONFIGURATIONS_FILE_NAME);
		Properties properties = new Properties();
		try {
			properties.load(stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
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

		public static class Context {

			public static class Spain {

				public static final String	UUID				= "context.spain.uuid";
				public static final String	TAX_EXEMPT_UUID		= "context.spain.tax.exempt.uuid";
				public static final String	TAX_EXEMPT_VALUE	= "context.spain.tax.exempt.value";

				public static class Continental {

					public static final String	UUID	= "context.spain.continental.uuid";

					public static class VAT {

						public static final String	NORMAL_UUID				= "context.spain.continental.tax.vat.normal.uuid";
						public static final String	NORMAL_PERCENT			= "context.spain.continental.tax.vat.normal.percent";

						public static final String	INTERMEDIATE_UUID		= "context.spain.continental.tax.vat.intermediate.uuid";
						public static final String	INTERMEDIATE_PERCENT	= "context.spain.continental.tax.vat.intermediate.percent";

						public static final String	REDUCED_UUID			= "context.spain.continental.tax.vat.reduced.uuid";
						public static final String	REDUCED_PERCENT			= "context.spain.continental.tax.vat.reduced.percent";
					}
					
					public static class Alava {
						public static final String	UUID	= "context.spain.alava.uuid";
					}
					
					public static class Albacete {
						public static final String	UUID	= "context.spain.albacete.uuid";
					}
					
					public static class Alicante {
						public static final String	UUID	= "context.spain.alicante.uuid";
					}
					
					public static class Almeria {
						public static final String	UUID	= "context.spain.almeria.uuid";
					}
					
					public static class Asturias {
						public static final String	UUID	= "context.spain.asturias.uuid";
					}
					
					public static class Avila {
						public static final String	UUID	= "context.spain.avila.uuid";
					}
					
					public static class Badajoz {
						public static final String	UUID	= "context.spain.badajoz.uuid";
					}
					
					public static class Baleares {
						public static final String	UUID	= "context.spain.baleares.uuid";
					}
					
					public static class Barcelona {
						public static final String	UUID	= "context.spain.barcelona.uuid";
					}
					
					public static class Bizkaia {
						public static final String	UUID	= "context.spain.bizkaia.uuid";
					}
					
					public static class Burgos {
						public static final String	UUID	= "context.spain.burgos.uuid";
					}
					
					public static class Caceres {
						public static final String	UUID	= "context.spain.caceres.uuid";
					}
					
					public static class Cadiz {
						public static final String	UUID	= "context.spain.cadiz.uuid";
					}
					
					public static class Cantabria {
						public static final String	UUID	= "context.spain.cantabria.uuid";
					}
					
					public static class Castellon {
						public static final String	UUID	= "context.spain.castellon.uuid";
					}
					
					public static class CiudadReal {
						public static final String	UUID	= "context.spain.ciudadreal.uuid";
					}
					
					public static class Cordoba {
						public static final String	UUID	= "context.spain.cordoba.uuid";
					}

					public static class Cuenca {
						public static final String	UUID	= "context.spain.cuenca.uuid";
					}
					
					public static class Gerona {
						public static final String	UUID	= "context.spain.gerona.uuid";
					}
					
					public static class Gipuzkoa {
						public static final String	UUID	= "context.spain.gipuzkoa.uuid";
					}
					
					public static class Granada {
						public static final String	UUID	= "context.spain.granada.uuid";
					}
					
					public static class Guadalajara {
						public static final String	UUID	= "context.spain.guadalajara.uuid";
					}
					
					public static class Huelva {
						public static final String	UUID	= "context.spain.huelva.uuid";
					}
					
					public static class Huesca {
						public static final String	UUID	= "context.spain.huesca.uuid";
					}
					
					public static class Jaen {
						public static final String	UUID	= "context.spain.jaen.uuid";
					}
					
					public static class LaCoruna {
						public static final String	UUID	= "context.spain.lacoruna.uuid";
					}
					
					public static class LaRioja {
						public static final String	UUID	= "context.spain.larioja.uuid";
					}
					
					public static class Leon {
						public static final String	UUID	= "context.spain.leon.uuid";
					}
					
					public static class Lerida {
						public static final String	UUID	= "context.spain.lerida.uuid";
					}
					
					public static class Lugo {
						public static final String	UUID	= "context.spain.lugo.uuid";
					}
					
					public static class Madrid {
						public static final String	UUID	= "context.spain.madrid.uuid";
					}
					
					public static class Malaga {
						public static final String	UUID	= "context.spain.malaga.uuid";
					}
					
					public static class Murcia {
						public static final String	UUID	= "context.spain.murcia.uuid";
					}
					
					public static class Navarra {
						public static final String	UUID	= "context.spain.navarra.uuid";
					}
					
					public static class Orense {
						public static final String	UUID	= "context.spain.orense.uuid";
					}
					
					public static class Palencia {
						public static final String	UUID	= "context.spain.palencia.uuid";
					}
					
					public static class Pontevedra {
						public static final String	UUID	= "context.spain.pontevedra.uuid";
					}
					
					public static class Salamanca {
						public static final String	UUID	= "context.spain.salamanca.uuid";
					}
					
					public static class Segovia {
						public static final String	UUID	= "context.spain.segovia.uuid";
					}
					
					public static class Sevilla {
						public static final String	UUID	= "context.spain.sevilla.uuid";
					}
					
					public static class Soria {
						public static final String	UUID	= "context.spain.soria.uuid";
					}
					
					public static class Tarragona {
						public static final String	UUID	= "context.spain.tarragona.uuid";
					}
					
					public static class Teruel {
						public static final String	UUID	= "context.spain.teruel.uuid";
					}
					
					public static class Toledo {
						public static final String	UUID	= "context.spain.toledo.uuid";
					}
					
					public static class Valencia {
						public static final String	UUID	= "context.spain.valencia.uuid";
					}
					
					public static class Valladolid {
						public static final String	UUID	= "context.spain.valladolid.uuid";
					}
					
					public static class Zamora {
						public static final String	UUID	= "context.spain.zamora.uuid";
					}
					
					public static class Zaragoza {
						public static final String	UUID	= "context.spain.zaragoza.uuid";
					}
					
				}
			
				public static class CanaryIslands {
					
					public static final String	UUID	= "context.spain.canaryislands.uuid";

					public static class IGIC {
						public static final String	NORMAL_UUID				= "context.spain.canaryislands.tax.igic.normal.uuid";
						public static final String	NORMAL_PERCENT			= "context.spain.canaryislands.tax.igic.normal.percent";

						public static final String	INTERMEDIATE_UUID		= "context.spain.canaryislands.tax.igic.intermediate.uuid";
						public static final String	INTERMEDIATE_PERCENT	= "context.spain.canaryislands.tax.igic.intermediate.percent";

						public static final String	REDUCED_UUID			= "context.spain.canaryislands.tax.igic.reduced.uuid";
						public static final String	REDUCED_PERCENT			= "context.spain.canaryislands.tax.igic.reduced.percent";
					}
					
					public static class LasPalmas {
						public static final String	UUID	= "context.spain.canaryislands.laspalmas.uuid";
					}
					
					public static class StaCruzDeTenerife {
						public static final String	UUID	= "context.spain.canaryislands.stacruzdetenerife.uuid";
					}
					
				}
			}
		}
	}

}
