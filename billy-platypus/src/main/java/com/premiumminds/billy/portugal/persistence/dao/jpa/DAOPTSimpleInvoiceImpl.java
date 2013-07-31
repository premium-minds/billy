package com.premiumminds.billy.portugal.persistence.dao.jpa;

import java.util.List;

import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTSimpleInvoiceEntity;

public class DAOPTSimpleInvoiceImpl extends DAOPTInvoiceImpl {

	public DAOPTSimpleInvoiceImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public PTSimpleInvoiceEntity getEntityInstance() {
		return new JPAPTSimpleInvoiceEntity();
	}

	@Override
	protected Class<JPAPTSimpleInvoiceEntity> getEntityClass() {
		return JPAPTSimpleInvoiceEntity.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PTSimpleInvoiceEntity getLatestInvoiceFromSeries(String series)
			throws BillyRuntimeException {

		List<Object[]> list = findLastestUID(this.getEntityClass(), series);

		if (list.size() != 0)
			return (PTSimpleInvoiceEntity) this.get(new UID((String) list
					.get(0)[0]));
		else
			throw new BillyRuntimeException();
	}

}
