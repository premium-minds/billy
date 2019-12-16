/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.util;

import com.google.inject.Injector;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.persistence.PTRegionContextPersistenceService;

/**
 * Encapsulates all Context information for Portugal.
 */
public class Contexts {

    Config configuration = new Config();

    private final Portugal portugal;
    private final Continent continent;
    private final Madeira madeira;
    private final Azores azores;
    private final Injector injector;
    private final PTRegionContextPersistenceService persistenceService;

    public class Portugal {

        public PTRegionContext allRegions() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao.get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.UUID));
        }
    }

    public class Continent {

        public PTRegionContext allContinentRegions() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.UUID));
        }

        public PTRegionContext aveiro() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Aveiro.UUID));
        }

        public PTRegionContext beja() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Beja.UUID));
        }

        public PTRegionContext braga() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Braga.UUID));
        }

        public PTRegionContext braganca() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Braganca.UUID));
        }

        public PTRegionContext casteloBranco() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao.get(
                    Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.CasteloBranco.UUID));
        }

        public PTRegionContext coimbra() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Coimbra.UUID));
        }

        public PTRegionContext evora() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Evora.UUID));
        }

        public PTRegionContext faro() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Faro.UUID));
        }

        public PTRegionContext guarda() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Guarda.UUID));
        }

        public PTRegionContext leiria() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Leiria.UUID));
        }

        public PTRegionContext lisboa() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Lisboa.UUID));
        }

        public PTRegionContext portalegre() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Portalegre.UUID));
        }

        public PTRegionContext porto() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Porto.UUID));
        }

        public PTRegionContext santarem() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Santarem.UUID));
        }

        public PTRegionContext setubal() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Setubal.UUID));
        }

        public PTRegionContext viana() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Viana.UUID));
        }

        public PTRegionContext vilaReal() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.VilaReal.UUID));
        }

        public PTRegionContext viseu() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Continental.Viseu.UUID));
        }
    }

    public class Azores {

        public PTRegionContext azores() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Azores.UUID));
        }
    }

    public class Madeira {

        public PTRegionContext madeira() {
            DAOPTRegionContext dao = Contexts.this.getInstance(DAOPTRegionContext.class);
            return (PTRegionContext) dao
                    .get(Contexts.this.configuration.getUID(Config.Key.Context.Portugal.Madeira.UUID));
        }
    }

    public Contexts(Injector injector) {
        this.portugal = new Portugal();
        this.continent = new Continent();
        this.madeira = new Madeira();
        this.azores = new Azores();
        this.injector = injector;
        this.persistenceService = this.getInstance(PTRegionContextPersistenceService.class);
    }

    public Portugal portugal() {
        return this.portugal;
    }

    public Continent continent() {
        return this.continent;
    }

    public Madeira madeira() {
        return this.madeira;
    }

    public Azores azores() {
        return this.azores;
    }

    public PTRegionContextPersistenceService persistence() {
        return this.persistenceService;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
