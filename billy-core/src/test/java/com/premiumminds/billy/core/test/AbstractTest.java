package com.premiumminds.billy.core.test;

import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.BeforeClass;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.core.CoreDependencyModule;
import com.premiumminds.billy.core.test.fixtures.MockBaseEntity;

public class AbstractTest {

	private static Injector injector;

	@BeforeClass
	public static void setUpClass() {
		AbstractTest.injector = Guice.createInjector(Modules.override(
				new CoreDependencyModule()).with(new MockDependencyModule()));
	}

	public <T> T getInstance(Class<T> clazz) {
		return AbstractTest.injector.getInstance(clazz);
	}

	public <T> T getMock(Class<T> clazz) {
		return mock(clazz);
	}

	@SuppressWarnings("unchecked")
	public <T extends MockBaseEntity> MockBaseEntity createMockEntityFromYaml(
			Class<T> clazz, String path) {
		Constructor constructor = new Constructor(clazz);
		TypeDescription typeDescription = new TypeDescription(clazz);
		constructor.addTypeDescription(typeDescription);
		Yaml yaml = new Yaml(constructor);

		try {
			return (T) yaml.load(new BufferedReader(new FileReader(path)));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
