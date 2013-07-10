package com.premiumminds.billy.core.jpa;

import org.junit.BeforeClass;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.CoreJPADependencyModule;
import com.premiumminds.billy.core.CoreJPAPersistenceDependencyModule;

public class AbstractTest {

	private static Injector injector;
	
	@BeforeClass 
	public static void setUpClass() {      
		injector = Guice.createInjector(new CoreJPADependencyModule(), new CoreJPAPersistenceDependencyModule());
		injector.getInstance(CoreJPAPersistenceDependencyModule.Initializer.class);
	}
	
	public <T> T getInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}
	
}
