package com.premiumminds.billy.portugal.services.builders;

import com.premiumminds.billy.core.services.builders.TaxBuilder;
import com.premiumminds.billy.portugal.services.entities.PTTax;

public interface PTTaxBuilder<TBuilder extends PTTaxBuilder<TBuilder, TTax>, TTax extends PTTax>
		extends TaxBuilder<TBuilder, TTax> {

}
