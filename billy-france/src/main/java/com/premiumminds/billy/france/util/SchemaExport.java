/**
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

import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.tool.EnversSchemaGenerator;

@SuppressWarnings("deprecation")
public class SchemaExport {

    public static void main(String[] args) {

        SchemaExport.exportSchema(args[0], args[1], Boolean.parseBoolean(args[2]), Boolean.parseBoolean(args[3]),
                args[4]);
    }

    private static void exportSchema(String persistenceUnit, String outputDir, Boolean drop, Boolean create,
            String delemiter) {
        File file = new File(outputDir);
        new File(file.getParent()).mkdirs();

        Ejb3Configuration jpaConfiguration = new Ejb3Configuration().configure(persistenceUnit, null);
        jpaConfiguration.buildMappings();
        Configuration hibernateConfiguration = jpaConfiguration.getHibernateConfiguration();
        AuditConfiguration.getFor(hibernateConfiguration);
        EnversSchemaGenerator esg = new EnversSchemaGenerator(hibernateConfiguration);
        org.hibernate.tool.hbm2ddl.SchemaExport se = esg.export();
        se.setOutputFile(outputDir);
        se.setFormat(true);
        se.setDelimiter(delemiter);
        se.drop(drop, false);
        se.create(create, false);
    }
}
