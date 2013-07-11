package com.premiumminds.billy.core.test.services.builders;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOBusiness;
import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.BusinessEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockBusinessEntity;

public class TestBusinessBuilder extends AbstractTest {

	@Test
	public void doTest() {
		MockBusinessEntity mockBusiness = this
				.loadFixture(BusinessEntity.class);

		DAOBusiness mockDaoBusiness = this.getMock(DAOBusiness.class);
		DAOContext mockDaoContext = this.getMock(DAOContext.class);

		Mockito.when(mockDaoBusiness.getEntityInstance()).thenReturn(
				new MockBusinessEntity());
		Mockito.when(mockDaoContext.get((UID) Matchers.anyObject()))
				.thenReturn(this.getMock(ContextEntity.class));

		Business.Builder builder = new Business.Builder(mockDaoBusiness,
				mockDaoContext);

		Contact.Builder mockContactBuilder = this
				.getMock(Contact.Builder.class);
		Mockito.when(mockContactBuilder.build()).thenReturn(
				Mockito.mock(ContactEntity.class));

		Address.Builder mockAddressBuilder = this
				.getMock(Address.Builder.class);
		Mockito.when(mockAddressBuilder.build()).thenReturn(
				Mockito.mock(AddressEntity.class));

		Application.Builder mockApplicationBuilder = this
				.getMock(Application.Builder.class);
		Mockito.when(mockApplicationBuilder.build()).thenReturn(
				Mockito.mock(Application.class));

		builder.setOperationalContextUID(Mockito.mock(UID.class))
				.setName(mockBusiness.getName())
				.setCommercialName(mockBusiness.getCommercialName())
				.addContact(mockContactBuilder).addContact(mockContactBuilder)
				.addContact(mockContactBuilder).setAddress(mockAddressBuilder)
				.setBillingAddress(mockAddressBuilder)
				.addApplication(mockApplicationBuilder)
				.addApplication(mockApplicationBuilder)
				.addApplication(mockApplicationBuilder)
				.setFinancialID(mockBusiness.getFinancialID());

		Business business = builder.build();

		assert (business != null); // TODO verify result
	}

	// TODO load from YAML
	public MockBusinessEntity loadFixture(Class<BusinessEntity> clazz) {
		MockBusinessEntity result = new MockBusinessEntity();

		result.taxId = "tax_id";
		result.name = "name";
		result.commercialName = "commercial_name";
		result.website = "website";

		return result;
	}

}
