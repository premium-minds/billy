/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
 *
 * billy is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.platypus.entities.mocks;

import java.util.ArrayList;
import java.util.List;

import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.core.util.ContactType;
import com.premiumminds.billy.platypus.PlatypusBaseTest;

public class ContactTestEntities
		extends
		PlatypusBaseTest {

	public static List<ContactEntity> getEntities() {
		List<ContactEntity> result = new ArrayList<ContactEntity>();
		result.add(getInstance1());
		return result;
	}

	public static ContactEntity getInstance1() {
		DAOContact daoContact = getInstance(DAOContact.class);

		return daoContact.getContactInstance(
				ContactType.PHONE, 
				"123456789");
	}
	
	public static ContactEntity getInstance2() {
		DAOContact daoContact = getInstance(DAOContact.class);

		return daoContact.getContactInstance(
				ContactType.EMAIL, 
				"fasdf@dgs.com");
	}
	
	public static ContactEntity getInstance3() {
		DAOContact daoContact = getInstance(DAOContact.class);

		return daoContact.getContactInstance(
				ContactType.MOBILE, 
				"654987321");
	}

}
