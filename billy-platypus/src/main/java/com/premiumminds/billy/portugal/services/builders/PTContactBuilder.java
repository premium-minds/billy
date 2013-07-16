package com.premiumminds.billy.portugal.services.builders;

import com.premiumminds.billy.core.services.builders.ContactBuilder;
import com.premiumminds.billy.portugal.services.entities.PTContact;


public interface PTContactBuilder<TBuilder extends PTContactBuilder<TBuilder, TContact>, TContact extends PTContact> extends ContactBuilder<TBuilder, TContact> {

}
