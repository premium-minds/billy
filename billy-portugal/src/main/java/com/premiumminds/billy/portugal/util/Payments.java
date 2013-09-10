package com.premiumminds.billy.portugal.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.builders.impl.BuilderManager;
import com.premiumminds.billy.portugal.services.entities.PTPayment;


public class Payments {

	private final Injector	injector;

	public Payments(Injector injector) {
		this.injector = injector;
	}

	public PTPayment.Builder builder() {
		return getInstance(PTPayment.Builder.class);
	}
	
	public PTPayment.Builder builder(PTPayment payment) {
		PTPayment.Builder builder = getInstance(PTPayment.Builder.class);
		BuilderManager.setTypeInstance(builder, payment);
		return builder;
	}
	
	private <T> T getInstance(Class<T> clazz) {
		return this.injector.getInstance(clazz);
	}
}
