package com.premiumminds.billy.platypus.entities;
public class MockDependencyModule extends AbstractModule {

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
		bind(DAOGenericInvoiceEntry.class).toInstance(
				mock(DAOGenericInvoiceEntry.class));
		bind(DAOProduct.class).toInstance(mock(DAOProduct.class));
		bind(DAOShippingPoint.class).toInstance(mock(DAOShippingPoint.class));
		bind(DAOSupplier.class).toInstance(mock(DAOSupplier.class));
		bind(DAOTax.class).toInstance(mock(DAOTax.class));
	}

}