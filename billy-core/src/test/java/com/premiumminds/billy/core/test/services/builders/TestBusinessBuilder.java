package com.premiumminds.billy.core.test.services.builders;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Matchers;

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
		MockBusinessEntity mockBusiness = loadFixture(BusinessEntity.class);
		
		DAOBusiness mockDaoBusiness = getMock(DAOBusiness.class);
		DAOContext mockDaoContext = getMock(DAOContext.class);
		
		when(mockDaoBusiness.getEntityInstance()).thenReturn(new MockBusinessEntity());
		when(mockDaoContext.get((UID) Matchers.anyObject())).thenReturn(getMock(ContextEntity.class));
		
		Business.Builder builder = new Business.Builder(
				mockDaoBusiness, 
				mockDaoContext);
		
		Contact.Builder mockContactBuilder = getMock(Contact.Builder.class);
		when(mockContactBuilder.build()).thenReturn(mock(ContactEntity.class));
		
		Address.Builder mockAddressBuilder = getMock(Address.Builder.class);
		when(mockAddressBuilder.build()).thenReturn(mock(AddressEntity.class));
		
		Application.Builder mockApplicationBuilder = getMock(Application.Builder.class);
		when(mockApplicationBuilder.build()).thenReturn(mock(Application.class));

		builder
			.setOperationalContextUID(mock(UID.class))
			.setName(mockBusiness.getName())
			.setCommercialName(mockBusiness.getCommercialName())
			.addContact(mockContactBuilder)
			.addContact(mockContactBuilder)
			.addContact(mockContactBuilder)
			.setAddress(mockAddressBuilder)
			.setBillingAddress(mockAddressBuilder)
			.addApplication(mockApplicationBuilder)
			.addApplication(mockApplicationBuilder)
			.addApplication(mockApplicationBuilder)
			.setFinancialID(mockBusiness.getFinancialID());
		
		Business business = builder.build();
		
		assert(business != null); //TODO verify result
	}
	
	//TODO load from YAML
	public MockBusinessEntity loadFixture(Class<BusinessEntity> clazz) {
		MockBusinessEntity result = new MockBusinessEntity();
		
		result.taxId = "tax_id";
		result.name = "name";
		result.commercialName = "commercial_name";
		result.website = "website";
		
		return result;
	}
	
}
