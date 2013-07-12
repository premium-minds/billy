package com.premiumminds.billy.core.test.services.builders;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

import com.premiumminds.billy.core.persistence.dao.DAOApplication;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.services.entities.Application;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.test.AbstractTest;
import com.premiumminds.billy.core.test.fixtures.MockApplicationEntity;

public class TestApplicationBuilder extends AbstractTest {

	private static final String APPLICATION_YML = "src/test/resources/Application.yml";

	@Test
	public void doTest() {
		MockApplicationEntity mockApplication = (MockApplicationEntity) createMockEntityFromYaml(
				MockApplicationEntity.class, APPLICATION_YML);

		Mockito.when(getInstance(DAOApplication.class).getEntityInstance()).thenReturn(
				new MockApplicationEntity());

		Application.Builder builder = getInstance(Application.Builder.class);

		Contact.Builder mockContactBuilder = this
				.getMock(Contact.Builder.class);
		Mockito.when(mockContactBuilder.build()).thenReturn(
				Mockito.mock(ContactEntity.class));

		Contact.Builder mockMainContactBuilder = this
				.getMock(Contact.Builder.class);
		Mockito.when(mockMainContactBuilder.build()).thenReturn(
				Mockito.mock(ContactEntity.class));

		builder.addContact(mockContactBuilder)
				.addContact(mockMainContactBuilder)
				.setDeveloperCompanyName(
						mockApplication.getDeveloperCompanyName())
				.setDeveloperCompanyTaxIdentifier(
						mockApplication.getDeveloperCompanyTaxIdentifier())
				.setMainContact(mockMainContactBuilder)
				.setName(mockApplication.getName())
				.setVersion(mockApplication.getVersion())
				.setWebsiteAddress(mockApplication.getWebsiteAddress());

		Application application = builder.build();

		assert (application != null);
		assertEquals(mockApplication.getName(), application.getName());
		assertEquals(mockApplication.getVersion(), application.getVersion());
		assertEquals(mockApplication.getDeveloperCompanyName(),
				application.getDeveloperCompanyName());
		assertEquals(mockApplication.getDeveloperCompanyTaxIdentifier(),
				application.getDeveloperCompanyTaxIdentifier());
		assertEquals(mockApplication.getWebsiteAddress(),
				application.getWebsiteAddress());
		assert (application.getContacts() != null);
		assert (application.getMainContact() != null);
	}
}
