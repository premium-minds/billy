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
package com.premiumminds.billy.core.services.builders;

import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.core.services.entities.Tax;

public interface ProductBuilder<TBuilder extends ProductBuilder<TBuilder, TProduct>, TProduct extends Product>
        extends Builder<TProduct> {

    public TBuilder setProductCode(String code);

    public TBuilder setProductGroup(String group);

    public TBuilder setDescription(String description);

    public TBuilder setType(ProductType type);

    public TBuilder setCommodityCode(String code);

    public TBuilder setNumberCode(String code);

    public TBuilder setValuationMethod(String method);

    public TBuilder setUnitOfMeasure(String unit);

    public TBuilder addTaxUID(StringID<Tax> tax);

}
