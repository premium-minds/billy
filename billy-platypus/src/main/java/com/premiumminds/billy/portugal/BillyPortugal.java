/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTProduct;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.entities.PTShippingPoint;
import com.premiumminds.billy.portugal.services.entities.PTSupplier;
import com.premiumminds.billy.portugal.services.entities.PTTax;

public class BillyPortugal {

	public class Builders {
		
		public class Invoices {
			
			//TODO invoice, credit note, simple invoice, manual invoice, etc...
			
		}
		
		public PTAddress.Builder createAddressBuilder() {
			return getInstance(PTAddress.Builder.class);
		}

		public PTContact.Builder createContactBuilder() {
			return getInstance(PTContact.Builder.class);
		}

		public PTProduct.Builder createProductBuilder() {
			return getInstance(PTProduct.Builder.class);
		}

		public PTShippingPoint.Builder createShippingPointBuilder() {
			return getInstance(PTShippingPoint.Builder.class);
		}

		public PTSupplier.Builder createSupplierBuilder() {
			return getInstance(PTSupplier.Builder.class);
		}

		public class Configuration {

			public PTApplication.Builder createApplicationBuilder() {
				return getInstance(PTApplication.Builder.class);
			}

			public PTRegionContext.Builder createRegionContextBuilder() {
				return getInstance(PTRegionContext.Builder.class);
			}

			public PTTax.Builder createTaxBuilder() {
				return getInstance(PTTax.Builder.class);
			}

			public PTBusiness.Builder createBusinessBuilder() {
				return getInstance(PTBusiness.Builder.class);
			}

		}

		private final Configuration configurationBuilders;
		private final Invoices invoiceBuilders;
		
		private Builders() {
			configurationBuilders = new Configuration();
			invoiceBuilders = new Invoices();
		}
		
		public Configuration configuration() {
			return configurationBuilders;
		}
		
		public Invoices invoices() {
			return invoiceBuilders;
		}
	}
	
	public class Taxes { //TODO
	
		public class Continent {
			
			public PTTax normal() {
				//TODO
				return null;
			}
			
			public PTTax intermediate() {
				//TODO
				return null;
			}
			
			public PTTax reduced() {
				//TODO
				return null;
			}
			
		}
		
		public class Madeira {
			
			public PTTax normal() {
				//TODO
				return null;
			}
			
			public PTTax intermediate() {
				//TODO
				return null;
			}
			
			public PTTax reduced() {
				//TODO
				return null;
			}
			
		}
		
		public class Azores {
			
			public PTTax normal() {
				//TODO
				return null;
			}
			
			public PTTax intermediate() {
				//TODO
				return null;
			}
			
			public PTTax reduced() {
				//TODO
				return null;
			}
			
		}

		public PTTax exempt() {
			//TODO
			return null;
		}
		
		private final Continent continent;
		private final Madeira madeira;
		private final Azores azores;
		
		public Taxes() {
			continent = new Continent();
			madeira = new Madeira();
			azores = new Azores();
		}
		
		public Continent continent() {
			return continent;
		}
		
		public Madeira madeira() {
			return madeira;
		}

		public Azores azores() {
			return azores;
		}
		
	}


	private static final String DEFAULT_PERSISTENCE_UNIT = "BillyPlatypusPersistenceUnit";

	private final Injector injector;
	private final Builders builders;
	private final Taxes taxes;

	public BillyPortugal() {
		this(new JpaPersistModule(DEFAULT_PERSISTENCE_UNIT));
	}

	public BillyPortugal(PersistModule persistModule) {
		injector = Guice.createInjector(new PlatypusDependencyModule(), persistModule);
		builders = new Builders();
		taxes = new Taxes();
	}

	public Builders builders() {
		return this.builders;
	}
	
	public Taxes taxes() {
		return taxes;
	}

	private <T> T getInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}

}
