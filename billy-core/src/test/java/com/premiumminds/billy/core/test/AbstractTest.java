package com.premiumminds.billy.core.test;

import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.premiumminds.billy.core.CoreDependencyModule;
import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.persistence.dao.DAOApplication;
import com.premiumminds.billy.core.persistence.dao.DAOBankAccount;
import com.premiumminds.billy.core.persistence.dao.DAOBusiness;
import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.DAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.dao.DAOShippingPoint;
import com.premiumminds.billy.core.persistence.dao.DAOSupplier;
import com.premiumminds.billy.core.persistence.dao.DAOTax;

public class AbstractTest {

	public static class TestModule extends AbstractModule {

		@Override
		protected void configure() {
			bind(DAOAddress.class).toInstance(mock(DAOAddress.class));
			bind(DAOApplication.class).toInstance(mock(DAOApplication.class));
			bind(DAOBankAccount.class).toInstance(mock(DAOBankAccount.class));
			bind(DAOBusiness.class).toInstance(mock(DAOBusiness.class));
			bind(DAOContact.class).toInstance(mock(DAOContact.class));
			bind(DAOContext.class).toInstance(mock(DAOContext.class));
			bind(DAOCustomer.class).toInstance(mock(DAOCustomer.class));
			bind(DAOGenericInvoice.class).toInstance(mock(DAOGenericInvoice.class));
			bind(DAOGenericInvoiceEntry.class).toInstance(mock(DAOGenericInvoiceEntry.class));
			bind(DAOProduct.class).toInstance(mock(DAOProduct.class));
			bind(DAOShippingPoint.class).toInstance(mock(DAOShippingPoint.class));
			bind(DAOSupplier.class).toInstance(mock(DAOSupplier.class));
			bind(DAOTax.class).toInstance(mock(DAOTax.class));
		}
		
	}
	
	
	private static Injector injector;

	@BeforeClass
	public static void setUpClass() {
		AbstractTest.injector = Guice
				.createInjector(Modules.override(new CoreDependencyModule()).with(new TestModule()));
	}

	public <T> T getInstance(Class<T> clazz) {
		return AbstractTest.injector.getInstance(clazz);
	}

	public <T> T getMock(Class<T> clazz) {
		return mock(clazz);
	}

}
