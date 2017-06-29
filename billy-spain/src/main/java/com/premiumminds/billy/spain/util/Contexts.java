/**
 * Copyright (C) 2017 Premium Minds.
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
package com.premiumminds.billy.spain.util;

import com.google.inject.Injector;
import com.premiumminds.billy.spain.Config;
import com.premiumminds.billy.spain.persistence.dao.DAOESRegionContext;
import com.premiumminds.billy.spain.services.entities.ESRegionContext;
import com.premiumminds.billy.spain.services.persistence.ESRegionContextPersistenceService;

/**
 * Encapsulates all Context information for Spain.
 */
public class Contexts {

    Config configuration = new Config();

    private final Spain spain;
    private final Continent continent;
    private final CanaryIslands canaryIslands;
    private final Injector injector;
    private final ESRegionContextPersistenceService persistenceService;

    public class Spain {

        public ESRegionContext allRegions() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao.get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.UUID));
        }
    }

    public class Continent {

        public ESRegionContext allContinentRegions() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.UUID));
        }

        public ESRegionContext alava() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Alava.UUID));
        }

        public ESRegionContext albacete() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Albacete.UUID));
        }

        public ESRegionContext alicante() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Alicante.UUID));
        }

        public ESRegionContext almeria() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Almeria.UUID));
        }

        public ESRegionContext asturias() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Asturias.UUID));
        }

        public ESRegionContext avila() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Avila.UUID));
        }

        public ESRegionContext badajoz() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Badajoz.UUID));
        }

        public ESRegionContext baleares() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Baleares.UUID));
        }

        public ESRegionContext barcelona() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Barcelona.UUID));
        }

        public ESRegionContext bizkaia() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Bizkaia.UUID));
        }

        public ESRegionContext burgos() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Burgos.UUID));
        }

        public ESRegionContext caceres() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Caceres.UUID));
        }

        public ESRegionContext cadiz() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Cadiz.UUID));
        }

        public ESRegionContext cantabria() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Cantabria.UUID));
        }

        public ESRegionContext castellon() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Castellon.UUID));
        }

        public ESRegionContext ciudadReal() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.CiudadReal.UUID));
        }

        public ESRegionContext cordoba() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Cordoba.UUID));
        }

        public ESRegionContext cuenca() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Cuenca.UUID));
        }

        public ESRegionContext gerona() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Gerona.UUID));
        }

        public ESRegionContext gipuzkoa() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Gipuzkoa.UUID));
        }

        public ESRegionContext granada() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Granada.UUID));
        }

        public ESRegionContext guadalajara() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Guadalajara.UUID));
        }

        public ESRegionContext huelva() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Huelva.UUID));
        }

        public ESRegionContext huesca() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Huesca.UUID));
        }

        public ESRegionContext jaen() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Jaen.UUID));
        }

        public ESRegionContext laCoruna() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.LaCoruna.UUID));
        }

        public ESRegionContext laRioja() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.LaRioja.UUID));
        }

        public ESRegionContext leon() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Leon.UUID));
        }

        public ESRegionContext lerida() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Lerida.UUID));
        }

        public ESRegionContext lugo() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Lugo.UUID));
        }

        public ESRegionContext madrid() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Madrid.UUID));
        }

        public ESRegionContext malaga() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Malaga.UUID));
        }

        public ESRegionContext murcia() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Murcia.UUID));
        }

        public ESRegionContext navarra() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Navarra.UUID));
        }

        public ESRegionContext orense() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Orense.UUID));
        }

        public ESRegionContext palencia() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Palencia.UUID));
        }

        public ESRegionContext pontevedra() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Pontevedra.UUID));
        }

        public ESRegionContext salamanca() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Salamanca.UUID));
        }

        public ESRegionContext segovia() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Segovia.UUID));
        }

        public ESRegionContext sevilla() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Sevilla.UUID));
        }

        public ESRegionContext soria() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Soria.UUID));
        }

        public ESRegionContext tarragona() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Tarragona.UUID));
        }

        public ESRegionContext teruel() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Teruel.UUID));
        }

        public ESRegionContext toledo() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Toledo.UUID));
        }

        public ESRegionContext valencia() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Valencia.UUID));
        }

        public ESRegionContext valladolid() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Valladolid.UUID));
        }

        public ESRegionContext zamora() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Zamora.UUID));
        }

        public ESRegionContext zaragoza() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Zaragoza.UUID));
        }

    }

    public class CanaryIslands {

        public ESRegionContext allCanaryRegions() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.CanaryIslands.UUID));
        }

        public ESRegionContext staCruzDeTenerife() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao.get(
                    Contexts.this.configuration.getUID(Config.Key.Context.Spain.CanaryIslands.StaCruzDeTenerife.UUID));
        }

        public ESRegionContext lasPalmas() {
            DAOESRegionContext dao = Contexts.this.getInstance(DAOESRegionContext.class);
            return (ESRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.CanaryIslands.LasPalmas.UUID));
        }
    }

    public Contexts(Injector injector) {
        this.spain = new Spain();
        this.continent = new Continent();
        this.canaryIslands = new CanaryIslands();
        this.injector = injector;
        this.persistenceService = this.getInstance(ESRegionContextPersistenceService.class);
    }

    public Spain spain() {
        return this.spain;
    }

    public Continent continent() {
        return this.continent;
    }

    public CanaryIslands canaryIslands() {
        return this.canaryIslands;
    }

    public ESRegionContextPersistenceService persistence() {
        return this.persistenceService;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
