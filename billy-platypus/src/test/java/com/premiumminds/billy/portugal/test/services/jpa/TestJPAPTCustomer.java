package com.premiumminds.billy.portugal.test.services.jpa;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.portugal.PlatypusBootstrap;
import com.premiumminds.billy.portugal.PlatypusDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PlatypusTestPersistenceDependencyModule;
import com.premiumminds.billy.portugal.test.util.PTCustomerTestUtil;

public class TestJPAPTCustomer extends PTAbstractTest {

	@Test
	public void doTest() {
		Injector injector = Guice.createInjector(
				new PlatypusDependencyModule(),
				new PlatypusTestPersistenceDependencyModule());
		injector.getInstance(PlatypusDependencyModule.Initializer.class);
		injector.getInstance(PlatypusTestPersistenceDependencyModule.Initializer.class);
		execute(injector);
		// assert
	}

	public static void execute(final Injector injector) {
		PlatypusBootstrap.execute(injector);
		DAO<?> dao = injector.getInstance(DAOPTInvoice.class);
		final PTCustomerTestUtil customer = new PTCustomerTestUtil(injector);

		try {
			new TransactionWrapper<Void>(dao) {

				@Override
				public Void runTransaction() throws Exception {
					DAOPTCustomer daoPTCustomer = injector
							.getInstance(DAOPTCustomer.class);

					PTCustomerEntity newCustomer = customer.getCustomerEntity();

					daoPTCustomer.create(newCustomer);

					return null;
				}

			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
