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
package com.premiumminds.billy.core.test.fixtures;

import java.time.LocalDate;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class JavaLocalDateConstruct extends AbstractConstruct {

    @Override
    public Object construct(final Node node) {
        ScalarNode snode = (ScalarNode) node;
        try {
            return LocalDate.parse(snode.getValue());
        } catch (IllegalAccessError ignored) {
        }

        throw new YAMLException(String.format(
            "Can't construct a java object for scalar %s (node value: %s)",
            node.getTag(),
            snode.getValue()));
    }
}
