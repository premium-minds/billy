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

import com.premiumminds.billy.core.persistence.dao.DAOAddress;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.platypus.PlatypusBaseTest;

public class AddressTestEntities
		extends
		PlatypusBaseTest {

	public static List<AddressEntity> getEntities() {
		List<AddressEntity> result = new ArrayList<AddressEntity>();
		result.add(getInstance1());
		return result;
	}

	public static AddressEntity getInstance1() {
		DAOAddress daoAddress = getInstance(DAOAddress.class);

		return daoAddress.getAddressInstance(
				"Portugal", 
				"linha1", 
				"linha2", 
				"linha3", 
				"Lisboa", 
				"Estremadura", 
				"1234-567");
	}
	
	public static AddressEntity getInstance2() {
		DAOAddress daoAddress = getInstance(DAOAddress.class);

		return daoAddress.getAddressInstance(
				"Espanha", 
				"linha123", 
				"linha2434", 
				"linha3564", 
				"Madrid", 
				"sei la", 
				"3215-467");
	}
	
	public static AddressEntity getInstance3() {
		DAOAddress daoAddress = getInstance(DAOAddress.class);

		return daoAddress.getAddressInstance(
				"França", 
				"dgsf", 
				"gsdfgfd", 
				"dfgdfg", 
				"Paris", 
				"sdasgd", 
				"9874-561");
	}

}
