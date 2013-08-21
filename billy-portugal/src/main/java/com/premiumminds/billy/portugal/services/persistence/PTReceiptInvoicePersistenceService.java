package com.premiumminds.billy.portugal.services.persistence;

import com.google.inject.Injector;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.exceptions.NotImplementedException;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.core.persistence.services.PersistenceService;
import com.premiumminds.billy.core.persistence.services.PersistenceServiceImpl;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.services.entities.PTReceiptInvoice;


public class PTReceiptInvoicePersistenceService<T extends PTReceiptInvoice> extends PersistenceServiceImpl<T> implements PersistenceService<T> {

	public PTReceiptInvoicePersistenceService(Injector injector) {
		super(injector);
	}

	@NotImplemented
	@Override
	public T createEntity(Builder<T> builder) {
		throw new NotImplementedException();
	}
	
	@NotImplemented
	@Override
	public T updateEntity(final Builder<T> builder) {
		throw new NotImplementedException();
	}

	@Override
	public T getEntity(final UID uid) {
		final DAOPTReceiptInvoice dao = this.injector
				.getInstance(DAOPTReceiptInvoice.class);

		try {
			return new TransactionWrapper<T>(dao) {

				@SuppressWarnings("unchecked")
				@Override
				public T runTransaction() throws Exception {
					return (T) dao.get(uid);
				}

			}.execute();
		} catch (Exception e) {
			throw new BillyRuntimeException(e);
		}
	}

}
