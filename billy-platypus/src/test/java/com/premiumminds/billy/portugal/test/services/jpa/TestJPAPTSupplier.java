package com.premiumminds.billy.portugal.test.services.jpa;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.DAO;
import com.premiumminds.billy.core.persistence.dao.TransactionWrapper;
import com.premiumminds.billy.portugal.PlatypusBootstrap;
import com.premiumminds.billy.portugal.PlatypusDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.entities.PTSupplierEntity;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
import com.premiumminds.billy.portugal.test.PlatypusTestPersistenceDependencyModule;
import com.premiumminds.billy.portugal.test.util.PTSupplierTestUtil;

public class TestJPAPTSupplier extends PTAbstractTest {

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
		final PTSupplierTestUtil supplier = new PTSupplierTestUtil(injector);

		try {
			new TransactionWrapper<Void>(dao) {

				@Override
				public Void runTransaction() throws Exception {
					DAOPTSupplier daoPTSupplier = injector
							.getInstance(DAOPTSupplier.class);

					PTSupplierEntity newSupplier = supplier.getSupplierEntity();

					daoPTSupplier.create(newSupplier);

					return null;
				}

			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
