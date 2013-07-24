package com.premiumminds.billy.portugal.util;

import com.google.inject.Injector;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTBusiness;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTProduct;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;
import com.premiumminds.billy.portugal.services.entities.PTShippingPoint;
import com.premiumminds.billy.portugal.services.entities.PTSupplier;
import com.premiumminds.billy.portugal.services.entities.PTTax;

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
	private final Injector injector;
	
	public Builders(Injector injector) {
		configurationBuilders = new Configuration();
		invoiceBuilders = new Invoices();
		this.injector = injector;
	}
	
	public Configuration configuration() {
		return configurationBuilders;
	}
	
	public Invoices invoices() {
		return invoiceBuilders;
	}
	
	private <T> T getInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}
	
}
