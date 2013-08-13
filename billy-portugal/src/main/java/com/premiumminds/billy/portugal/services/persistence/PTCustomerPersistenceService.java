package com.premiumminds.billy.portugal.services.persistence;

import com.google.inject.Injector;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.persistence.services.PersistenceServiceImpl;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;


public class PTCustomerPersistenceService<T extends PTCustomer> extends PersistenceServiceImpl<T>
		implements PersistenceService<T> {

	public PTCustomerPersistenceService(Injector injector) {
		super(injector);
	}

	@Override
	public T createEntity(final Builder<T> builder) {
		final DAOPTCustomer dao = this.injector
				.getInstance(DAOPTCustomer.class);

		try {
			return new TransactionWrapper<T>(dao) {

				@Override
				public T runTransaction() throws Exception {
					PTCustomerEntity customerEntity = (PTCustomerEntity) builder
							.build();
					return (T) dao.create(customerEntity);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

}
