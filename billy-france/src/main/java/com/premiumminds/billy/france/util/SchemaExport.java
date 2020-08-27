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

import java.io.File;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.ParsedPersistenceXmlDescriptor;
import org.hibernate.jpa.boot.internal.PersistenceXmlParser;
import org.hibernate.jpa.boot.spi.PersistenceUnitDescriptor;
import org.hibernate.jpa.boot.spi.ProviderChecker;
import org.hibernate.tool.schema.TargetType;

public class SchemaExport {

	public static void main(String[] args) {
		SchemaExport
			.exportSchema(args[0], args[1], args[2], args[3], args[4], args[5]);
	}

	private static void exportSchema(String persistenceUnit, String url, String username, String password,
		String outputDir, String delimiter) {

		File file = new File(outputDir);
		new File(file.getParent()).mkdirs();

		Map<String, Object> properties = setupProperties(url, username, password);
		EntityManagerFactoryBuilderImpl entityManagerFactoryBuilder =
			getEntityManagerFactoryBuilderOrNull(persistenceUnit, properties);

		EntityManagerFactory factory = entityManagerFactoryBuilder.build();
		MetadataImplementor metaData = entityManagerFactoryBuilder.getMetadata();

		org.hibernate.tool.hbm2ddl.SchemaExport se = new org.hibernate.tool.hbm2ddl.SchemaExport();
		se.setOutputFile(outputDir);
		se.setFormat(true);
		se.setDelimiter(delimiter);
		se.execute(EnumSet.of(TargetType.SCRIPT), org.hibernate.tool.hbm2ddl.SchemaExport.Action.CREATE, metaData);

		factory.close();
	}

	private static Map<String, Object> setupProperties(String url, String username, String password) {
		Map<String, Object>  properties = new HashMap<>();
		properties.put("javax.persistence.jdbc.url", url);
		properties.put("javax.persistence.jdbc.user", username);
		properties.put("javax.persistence.jdbc.password", password);

		return Collections.unmodifiableMap(properties);
	}

	private static EntityManagerFactoryBuilderImpl getEntityManagerFactoryBuilderOrNull(String persistenceUnitName,
		Map<String, Object> properties) {

		final List<ParsedPersistenceXmlDescriptor> units;
		try {
			units = PersistenceXmlParser.locatePersistenceUnits(properties);
		} catch (Exception e) {
			throw new PersistenceException("Unable to locate persistence units", e);
		}

		if (persistenceUnitName == null && units.size() > 1) {
			throw new PersistenceException("No name provided and multiple persistence units found");
		}

		for (ParsedPersistenceXmlDescriptor persistenceUnit : units) {
			final boolean matches = persistenceUnitName == null || persistenceUnit.getName().equals(persistenceUnitName);
			if (!matches) {
				continue;
			}

			if (!ProviderChecker.isProvider(persistenceUnit, properties)) {
				continue;
			}

			return getEntityManagerFactoryBuilder(persistenceUnit, properties, null);
		}

		return null;
	}

	private static EntityManagerFactoryBuilderImpl getEntityManagerFactoryBuilder(
		PersistenceUnitDescriptor persistenceUnitDescriptor, Map<String, Object> integration,
		ClassLoader providedClassLoader) {
		return new EntityManagerFactoryBuilderImpl(persistenceUnitDescriptor, integration, providedClassLoader);
	}
}
