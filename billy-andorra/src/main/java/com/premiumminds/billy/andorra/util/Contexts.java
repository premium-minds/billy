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
package com.premiumminds.billy.andorra.util;

import com.google.inject.Injector;
import com.premiumminds.billy.andorra.Config;
import com.premiumminds.billy.andorra.persistence.dao.DAOADRegionContext;
import com.premiumminds.billy.andorra.services.entities.ADRegionContext;
import com.premiumminds.billy.andorra.services.persistence.ADRegionContextPersistenceService;

/**
 * Encapsulates all Context information for Spain.
 */
public class Contexts {

    Config configuration = new Config();

    private final Spain spain;
    private final Continent continent;
    private final CanaryIslands canaryIslands;
    private final Injector injector;
    private final ADRegionContextPersistenceService persistenceService;

    public class Spain {

        public ADRegionContext allRegions() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao.get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.UUID));
        }
    }

    public class Continent {

        public ADRegionContext allContinentRegions() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.UUID));
        }

        public ADRegionContext alava() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Alava.UUID));
        }

        public ADRegionContext albacete() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Albacete.UUID));
        }

        public ADRegionContext alicante() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Alicante.UUID));
        }

        public ADRegionContext almeria() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Almeria.UUID));
        }

        public ADRegionContext asturias() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Asturias.UUID));
        }

        public ADRegionContext avila() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Avila.UUID));
        }

        public ADRegionContext badajoz() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Badajoz.UUID));
        }

        public ADRegionContext baleares() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Baleares.UUID));
        }

        public ADRegionContext barcelona() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Barcelona.UUID));
        }

        public ADRegionContext bizkaia() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Bizkaia.UUID));
        }

        public ADRegionContext burgos() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Burgos.UUID));
        }

        public ADRegionContext caceres() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Caceres.UUID));
        }

        public ADRegionContext cadiz() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Cadiz.UUID));
        }

        public ADRegionContext cantabria() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Cantabria.UUID));
        }

        public ADRegionContext castellon() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Castellon.UUID));
        }

        public ADRegionContext ciudadReal() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.CiudadReal.UUID));
        }

        public ADRegionContext cordoba() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Cordoba.UUID));
        }

        public ADRegionContext cuenca() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Cuenca.UUID));
        }

        public ADRegionContext gerona() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Gerona.UUID));
        }

        public ADRegionContext gipuzkoa() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Gipuzkoa.UUID));
        }

        public ADRegionContext granada() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Granada.UUID));
        }

        public ADRegionContext guadalajara() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Guadalajara.UUID));
        }

        public ADRegionContext huelva() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Huelva.UUID));
        }

        public ADRegionContext huesca() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Huesca.UUID));
        }

        public ADRegionContext jaen() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Jaen.UUID));
        }

        public ADRegionContext laCoruna() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.LaCoruna.UUID));
        }

        public ADRegionContext laRioja() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.LaRioja.UUID));
        }

        public ADRegionContext leon() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Leon.UUID));
        }

        public ADRegionContext lerida() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Lerida.UUID));
        }

        public ADRegionContext lugo() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Lugo.UUID));
        }

        public ADRegionContext madrid() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Madrid.UUID));
        }

        public ADRegionContext malaga() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Malaga.UUID));
        }

        public ADRegionContext murcia() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Murcia.UUID));
        }

        public ADRegionContext navarra() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Navarra.UUID));
        }

        public ADRegionContext orense() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Orense.UUID));
        }

        public ADRegionContext palencia() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Palencia.UUID));
        }

        public ADRegionContext pontevedra() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Pontevedra.UUID));
        }

        public ADRegionContext salamanca() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Salamanca.UUID));
        }

        public ADRegionContext segovia() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Segovia.UUID));
        }

        public ADRegionContext sevilla() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Sevilla.UUID));
        }

        public ADRegionContext soria() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Soria.UUID));
        }

        public ADRegionContext tarragona() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Tarragona.UUID));
        }

        public ADRegionContext teruel() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Teruel.UUID));
        }

        public ADRegionContext toledo() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Toledo.UUID));
        }

        public ADRegionContext valencia() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Valencia.UUID));
        }

        public ADRegionContext valladolid() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Valladolid.UUID));
        }

        public ADRegionContext zamora() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Zamora.UUID));
        }

        public ADRegionContext zaragoza() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.Continental.Zaragoza.UUID));
        }

    }

    public class CanaryIslands {

        public ADRegionContext allCanaryRegions() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.CanaryIslands.UUID));
        }

        public ADRegionContext staCruzDeTenerife() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao.get(
                    Contexts.this.configuration.getUID(Config.Key.Context.Spain.CanaryIslands.StaCruzDeTenerife.UUID));
        }

        public ADRegionContext lasPalmas() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Spain.CanaryIslands.LasPalmas.UUID));
        }
    }

    public Contexts(Injector injector) {
        this.spain = new Spain();
        this.continent = new Continent();
        this.canaryIslands = new CanaryIslands();
        this.injector = injector;
        this.persistenceService = this.getInstance(ADRegionContextPersistenceService.class);
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

    public ADRegionContextPersistenceService persistence() {
        return this.persistenceService;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
