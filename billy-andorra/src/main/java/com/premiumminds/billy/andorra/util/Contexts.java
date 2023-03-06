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
 * Encapsulates all Context information for Andorra.
 */
public class Contexts {

    Config configuration = new Config();

    private final Contexts.Andorra Andorra;
    private final Injector injector;
    private final ADRegionContextPersistenceService persistenceService;

    public class Andorra {

        public ADRegionContext allRegions() {
            DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
            return (ADRegionContext) dao.get(Contexts.this.configuration.getUID(Config.Key.Context.Andorra.UUID));
        }

		public ADRegionContext andorraLaVieja() {
			DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
			return (ADRegionContext) dao
				.get(Contexts.this.configuration.getUID(Config.Key.Context.Andorra.AndorraLaVieja.UUID));
		}

		public ADRegionContext canillo() {
			DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
			return (ADRegionContext) dao
				.get(Contexts.this.configuration.getUID(Config.Key.Context.Andorra.Canillo.UUID));
		}

		public ADRegionContext encamp() {
			DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
			return (ADRegionContext) dao
				.get(Contexts.this.configuration.getUID(Config.Key.Context.Andorra.Encamp.UUID));
		}

		public ADRegionContext lasEscaldasEngordany() {
			DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
			return (ADRegionContext) dao
				.get(Contexts.this.configuration.getUID(Config.Key.Context.Andorra.LasEscaldasEngordany.UUID));
		}

		public ADRegionContext laMassana() {
			DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
			return (ADRegionContext) dao
				.get(Contexts.this.configuration.getUID(Config.Key.Context.Andorra.LaMassana.UUID));
		}

		public ADRegionContext ordino() {
			DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
			return (ADRegionContext) dao
				.get(Contexts.this.configuration.getUID(Config.Key.Context.Andorra.Ordino.UUID));
		}

		public ADRegionContext sanJulianDeLoria() {
			DAOADRegionContext dao = Contexts.this.getInstance(DAOADRegionContext.class);
			return (ADRegionContext) dao
				.get(Contexts.this.configuration.getUID(Config.Key.Context.Andorra.SanJulianDeLoria.UUID));
		}
    }

    public Contexts(Injector injector) {
        this.Andorra = new Andorra();
        this.injector = injector;
        this.persistenceService = this.getInstance(ADRegionContextPersistenceService.class);
    }

    public Andorra andorra() {
        return this.Andorra;
    }

    public ADRegionContextPersistenceService persistence() {
        return this.persistenceService;
    }

    private <T> T getInstance(Class<T> clazz) {
        return this.injector.getInstance(clazz);
    }
}
