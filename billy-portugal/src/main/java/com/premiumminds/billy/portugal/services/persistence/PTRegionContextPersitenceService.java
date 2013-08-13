package com.premiumminds.billy.portugal.services.persistence;

import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.persistence.services.PersistenceServiceImpl;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;


public class PTRegionContextPersitenceService<T extends PTRegionContext> extends PersistenceServiceImpl<T>
		implements PersistenceService<T> {

	public PTRegionContextPersitenceService(Injector injector) {
		super(injector);
	}

	@NotImplemented
	@Override
	public T createEntity(final Builder<T> builder) {
		return null;
	}

}
