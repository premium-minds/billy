package com.premiumminds.billy.portugal.services.builders;

import com.premiumminds.billy.core.services.builders.AddressBuilder;
import com.premiumminds.billy.portugal.services.entities.PTAddress;


public interface PTAddressBuilder<TBuilder extends PTAddressBuilder<TBuilder, TAddress>, TAddress extends PTAddress> extends AddressBuilder<TBuilder, TAddress> {

}
