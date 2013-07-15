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

import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.platypus.PlatypusBaseTest;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;

public class PTRegionContextTestEntities
		extends
		PlatypusBaseTest {

	public static List<PTRegionContextEntity> getEntities() {
		List<PTRegionContextEntity> result = new ArrayList<PTRegionContextEntity>();
		result.add(getInstance1());
		return result;
	}

	public static PTRegionContextEntity getInstance1() {
		DAOPTRegionContext dao = getInstance(DAOPTRegionContext.class);
		DAOContext daoContext = getInstance(DAOContext.class);

		PTRegionContextEntity result = dao.getPTRegionContextInstance(
				"Aveiro", 
				"blablabla", 
				getInstance3(), 
				"PT");
		
		daoContext.create(result.getParent());
		return result;
	}

	public static PTRegionContextEntity getInstance2() {
		DAOPTRegionContext dao = getInstance(DAOPTRegionContext.class);
		return dao.getPTRegionContextInstance(
				"Lisboa", 
				"dfdgffs", 
				null, 
				"PT-20");
	}
	
	public static PTRegionContextEntity getInstance3() {
		DAOPTRegionContext dao = getInstance(DAOPTRegionContext.class);
		return dao.getPTRegionContextInstance(
				"Faro", 
				"asfagt", 
				null, 
				"PT-12");
	}

}
