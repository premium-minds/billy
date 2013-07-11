package com.premiumminds.billy.core.test.services.builders;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOApplication;
import com.premiumminds.billy.core.persistence.entities.ApplicationEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockApplicationEntity;

public class TestApplicationBuilder extends AbstractTest {

	private static final String NAME = "name";
	private static final String VERSION	= "version";
	private static final String DEVELOPER_COMPANY_NAME = "company_name";
	private static final String DEVELOPER_COMPANY_TAX_ID = "company_tax_id";
	private static final String WEBSITE = "website";
	
	@Test
	public void doTest() {
		MockApplicationEntity mockApplication = this.loadFixture(ApplicationEntity.class);
		
		
		DAOApplication mockDaoApplication = this.getMock(DAOApplication.class);
		
		Mockito.when(mockDaoApplication.getEntityInstance()).thenReturn(new MockApplicationEntity());
		
		Application.Builder builder = new Application.Builder(mockDaoApplication);
		
		Contact.Builder mockContactBuilder = this.getMock(Contact.Builder.class);
		Mockito.when(mockContactBuilder.build()).thenReturn(Mockito.mock(ContactEntity.class));
		
		Contact.Builder mockMainContactBuilder = this.getMock(Contact.Builder.class);
		Mockito.when(mockMainContactBuilder.build()).thenReturn(Mockito.mock(ContactEntity.class));
		
		
		builder.addContact(mockContactBuilder).addContact(mockMainContactBuilder).setDeveloperCompanyName(DEVELOPER_COMPANY_NAME).setDeveloperCompanyTaxIdentifier(DEVELOPER_COMPANY_TAX_ID).setMainContact(mockMainContactBuilder).setName(NAME).setVersion(VERSION).setWebsiteAddress(WEBSITE);
		
		Application application = builder.build();
		
		assert(application != null);
		assertEquals(NAME, application.getName());
		assertEquals(VERSION, application.getVersion());
		assertEquals(DEVELOPER_COMPANY_NAME, application.getDeveloperCompanyName());
		assertEquals(DEVELOPER_COMPANY_TAX_ID, application.getDeveloperCompanyTaxIdentifier());
		assertEquals(WEBSITE, application.getWebsiteAddress());
		assert(application.getContacts() != null);
		assert(application.getMainContact() != null);
	}
	
	public MockApplicationEntity loadFixture(Class<ApplicationEntity> clazz) {
		MockApplicationEntity result = new MockApplicationEntity();
		
		result.name = NAME;
		result.version = VERSION;
		result.developerCompanyName = DEVELOPER_COMPANY_NAME;
		result.developerCompanyTaxIdentifier = DEVELOPER_COMPANY_TAX_ID;
		result.websiteAddress = WEBSITE;
		
		return result;
	}
}
