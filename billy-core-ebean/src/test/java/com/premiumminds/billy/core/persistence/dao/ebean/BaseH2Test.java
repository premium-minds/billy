/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core Ebean.
 *
 * billy core Ebean is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core Ebean is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core Ebean. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.dao.ebean;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;

import io.ebean.Ebean;

public abstract class BaseH2Test {

    /**
     * Resets all H2 data (and sequences) after each test. Guarantees that each test is independent from one another.
     */
    @After
    public void cleanup() throws Exception {
        try (Statement statement = Ebean.beginTransaction().getConnection().createStatement()) {
            statement.execute("set REFERENTIAL_INTEGRITY false");

            // Find all tables and truncate them
            Set<String> tables = new HashSet<>();
            try (ResultSet result = statement
                    .executeQuery("select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='PUBLIC'")) {
                while (result.next()) {
                    tables.add(result.getString(1));
                }
            }
            for (String table : tables) {
                statement.executeUpdate("TRUNCATE TABLE " + table);
            }

            // Find all sequences and reset them
            Set<String> sequences = new HashSet<>();
            try (ResultSet result = statement.executeQuery(
                    "select SEQUENCE_NAME from INFORMATION_SCHEMA.SEQUENCES where SEQUENCE_SCHEMA='PUBLIC'")) {
                while (result.next()) {
                    sequences.add(result.getString(1));
                }
            }
            for (String seq : sequences) {
                statement.executeUpdate("alter sequence " + seq + " restart with 1");
            }

            statement.execute("set REFERENTIAL_INTEGRITY true");
        }
    }
}
