package com.premiumminds.billy.portugal.services.builders;

import com.premiumminds.billy.core.services.builders.ProductBuilder;
import com.premiumminds.billy.portugal.services.entities.PTProduct;

public interface PTProductBuilder<TBuilder extends PTProductBuilder<TBuilder, TProduct>, TProduct extends PTProduct>
		extends ProductBuilder<TBuilder, TProduct> {

}
