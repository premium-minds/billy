package com.premiumminds.billy.core.test;

import org.junit.BeforeClass;
import org.mockito.Mockito;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.CoreDependencyModule;

public class AbstractTest {

	private static Injector injector;
	
	@BeforeClass 
	public static void setUpClass() {      
		injector = Guice.createInjector(new CoreDependencyModule());
	}
	
	public <T> T getInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}
	
	public <T> T getMock(Class<T> clazz) {
		return Mockito.mock(clazz);
	}
	
}
