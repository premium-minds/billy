package com.premiumminds.billy.portugal.test.services.jpa;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.portugal.PlatypusBootstrap;
import com.premiumminds.billy.portugal.PlatypusDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PlatypusTestPersistenceDependencyModule;
import com.premiumminds.billy.portugal.test.util.PTBusinessTestUtil;

public class TestJPAPTBusiness extends PTAbstractTest {

	private Injector injector;

	@Before
	public void initialize() {
		injector = Guice.createInjector(new PlatypusDependencyModule(),
				new PlatypusTestPersistenceDependencyModule());
		injector.getInstance(PlatypusDependencyModule.Initializer.class);
		injector.getInstance(PlatypusTestPersistenceDependencyModule.Initializer.class);
		PlatypusBootstrap.execute(injector);
	}

	@Test
	public void doTest() {
		execute(injector);
		// assert
	}

	public static void execute(final Injector injector) {
		DAO<?> dao = injector.getInstance(DAOPTInvoice.class);
		final PTBusinessTestUtil business = new PTBusinessTestUtil(injector);

		try {
			new TransactionWrapper<Void>(dao) {

				@Override
				public Void runTransaction() throws Exception {
					DAOPTBusiness daoPTBusiness = injector
							.getInstance(DAOPTBusiness.class);

					PTBusinessEntity newBusiness = business.getBusinessEntity();

					daoPTBusiness.create(newBusiness);

					return null;
				}

			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
