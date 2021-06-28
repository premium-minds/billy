/*
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
package com.premiumminds.billy.france.util;

import com.google.inject.Injector;
import com.premiumminds.billy.france.Config;
import com.premiumminds.billy.france.persistence.dao.DAOFRRegionContext;
import com.premiumminds.billy.france.services.entities.FRRegionContext;
import com.premiumminds.billy.france.services.persistence.FRRegionContextPersistenceService;

/**
 * Encapsulates all Context information for France.
 */
public class Contexts {

    Config configuration = new Config();

    private final France france;
    private final Continent continent;
    private final Injector injector;
    private final FRRegionContextPersistenceService persistenceService;

    public class France {

        public FRRegionContext allRegions() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao.get(Contexts.this.configuration.getUID(Config.Key.Context.France.UUID));
        }
    }

    public class Continent {

        public FRRegionContext allContinentRegions() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.UUID));
        }

        public FRRegionContext alsace() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Alsace.UUID));
        }

        public FRRegionContext aquitaine() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Aquitaine.UUID));
        }

        public FRRegionContext auvergne() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Auvergne.UUID));
        }

        public FRRegionContext basseNormandie() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.BasseNormandie.UUID));
        }

        public FRRegionContext bourgogne() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Bourgogne.UUID));
        }

        public FRRegionContext bretagne() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Bretagne.UUID));
        }

        public FRRegionContext centre() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Centre.UUID));
        }

        public FRRegionContext champagneArdenne() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Champagne_Ardenne.UUID));
        }

        public FRRegionContext corse() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Corse.UUID));
        }

        public FRRegionContext francheComte() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Franche_Comte.UUID));
        }

        public FRRegionContext hauteNormandie() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.HauteNormandie.UUID));
        }

        public FRRegionContext ileDeFrance() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Ile_de_France.UUID));
        }

        public FRRegionContext languedocRoussillon() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Languedoc_Roussillon.UUID));
        }

        public FRRegionContext limousin() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Limousin.UUID));
        }

        public FRRegionContext lorraine() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Lorraine.UUID));
        }

        public FRRegionContext midiPyrenees() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.MidiPyrenees.UUID));
        }

        public FRRegionContext nordPasDeCalais() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Nord_Pas_de_Calais.UUID));
        }

        public FRRegionContext paysDeLaLoire() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Pays_de_la_Loire.UUID));
        }

        public FRRegionContext picardie() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Picardie.UUID));
        }

        public FRRegionContext poitouCharentes() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Poitou_Charentes.UUID));
        }

        public FRRegionContext provenceAlpesCoteDAzur() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.Provence_Alpes_Cote_d_Azur.UUID));
        }

        public FRRegionContext rhoneAlpes() {
            DAOFRRegionContext dao = Contexts.this.getInstance(DAOFRRegionContext.class);
            return (FRRegionContext) dao
                .get(Contexts.this.configuration.getUID(Config.Key.Context.France.Continental.RhoneAlpes.UUID));
        }
    }
    
    public Contexts(Injector injector) {
        this.france = new France();
        this.continent = new Continent();
        this.injector = injector;
        this.persistenceService = this.getInstance(FRRegionContextPersistenceService.class);
    }

    public France france() {
        return this.france;
    }

    public Continent continent() {
        return this.continent;
    }

    public FRRegionContextPersistenceService persistence() {
        return this.persistenceService;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
