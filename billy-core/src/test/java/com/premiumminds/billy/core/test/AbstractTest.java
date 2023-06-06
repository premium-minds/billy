/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.core.CoreDependencyModule;
import com.premiumminds.billy.core.test.fixtures.JavaLocalDateConstruct;
import com.premiumminds.billy.core.test.fixtures.JavaZoneIdConstruct;
import com.premiumminds.billy.core.test.fixtures.MockBaseEntity;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TrustedTagInspector;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.ZoneId;

public class AbstractTest {

    private static Injector injector;
    protected final static String YML_CONFIGS_DIR = "src/test/resources/yml/";

    @BeforeAll
    public static void setUpClass() {
        AbstractTest.injector =
                Guice.createInjector(Modules.override(new CoreDependencyModule()).with(new MockDependencyModule()));
    }

    public <T> T getInstance(Class<T> clazz) {
        return AbstractTest.injector.getInstance(clazz);
    }

    public <T> T getMock(Class<T> clazz) {
        return Mockito.mock(clazz);
    }

    public <T extends MockBaseEntity> T createMockEntity(Class<T> clazz, String path) {
        final LoaderOptions loadingConfig = new LoaderOptions();
        loadingConfig.setTagInspector(new TrustedTagInspector());

        final Representer representer = new Representer(new DumperOptions());
        final Tag zoneIdTag = new Tag("!ZoneId");
        representer.addClassTag(ZoneId.class, zoneIdTag);
        final Tag localDateTag = new Tag("!LocalDate");
        representer.addClassTag(LocalDate.class, localDateTag);

        final Constructor constructor = new Constructor(clazz, loadingConfig) {{
            this.addTypeDescription(new TypeDescription(ZoneId.class, "ZoneId"));
            this.addTypeDescription(new TypeDescription(LocalDate.class, "LocalDate"));

            this.yamlConstructors.put(zoneIdTag, new JavaZoneIdConstruct());
            this.yamlConstructors.put(localDateTag, new JavaLocalDateConstruct());
        }};

        Yaml yaml = new Yaml(constructor, representer);

        try {
            return yaml.load(new BufferedReader(new FileReader(path)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
