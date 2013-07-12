package com.premiumminds.billy.core.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.BeforeClass;
import org.mockito.Mockito;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.CoreDependencyModule;
import com.premiumminds.billy.core.test.fixtures.MockBaseEntity;
import com.premiumminds.billy.core.test.fixtures.MockBusinessEntity;

public class AbstractTest {

	private static Injector injector;

	@BeforeClass
	public static void setUpClass() {
		AbstractTest.injector = Guice
				.createInjector(new CoreDependencyModule());
	}

	public <T> T getInstance(Class<T> clazz) {
		return AbstractTest.injector.getInstance(clazz);
	}

	public <T> T getMock(Class<T> clazz) {
		return Mockito.mock(clazz);
	}

	public <T extends MockBaseEntity> MockBaseEntity createMockEntityFromYaml(
			Class<T> clazz, String path) {
		Constructor constructor = new Constructor(clazz);
		TypeDescription typeDescription = new TypeDescription(clazz);
		constructor.addTypeDescription(typeDescription);
		Yaml yaml = new Yaml(constructor);

		try {
			return (MockBusinessEntity) yaml.load(new BufferedReader(
					new FileReader(path)));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
