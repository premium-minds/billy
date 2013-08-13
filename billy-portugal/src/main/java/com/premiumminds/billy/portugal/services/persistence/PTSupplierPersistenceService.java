package com.premiumminds.billy.portugal.services.persistence;

import com.google.inject.Injector;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.persistence.services.PersistenceServiceImpl;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTSupplierEntity;
import com.premiumminds.billy.portugal.services.entities.PTSupplier;


public class PTSupplierPersistenceService<T extends PTSupplier> extends PersistenceServiceImpl<T>
		implements PersistenceService<T> {

	public PTSupplierPersistenceService(Injector injector) {
		super(injector);
	}

	@Override
	public T createEntity(final Builder<T> builder) {
		final DAOPTSupplier dao = this.injector
				.getInstance(DAOPTSupplier.class);

		try {
			return new TransactionWrapper<T>(dao) {

				@Override
				public T runTransaction() throws Exception {
					PTSupplierEntity supplierEntity = (PTSupplierEntity) builder
							.build();
					return (T) dao.create(supplierEntity);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

}
