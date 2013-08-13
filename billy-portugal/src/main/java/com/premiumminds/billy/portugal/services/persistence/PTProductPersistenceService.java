package com.premiumminds.billy.portugal.services.persistence;

import com.google.inject.Injector;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.persistence.services.PersistenceServiceImpl;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.services.entities.PTProduct;


public class PTProductPersistenceService<T extends PTProduct> extends PersistenceServiceImpl<T>
		implements PersistenceService<T> {

	public PTProductPersistenceService(Injector injector) {
		super(injector);
	}

	@Override
	public T createEntity(final Builder<T> builder) {
		final DAOPTProduct dao = this.injector
				.getInstance(DAOPTProduct.class);

		try {
			return new TransactionWrapper<T>(dao) {

				@Override
				public T runTransaction() throws Exception {
					PTProductEntity productEntity = (PTProductEntity) builder
							.build();
					return (T) dao.create(productEntity);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

}
