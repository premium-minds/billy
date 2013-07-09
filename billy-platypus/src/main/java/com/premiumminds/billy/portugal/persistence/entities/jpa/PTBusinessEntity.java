/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-platypus.
 * 
 * billy-platypus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-platypus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-platypus.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.portugal.persistence.entities.jpa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.entities.IPTBusinessEntity;

@Entity
@Table(name = Config.TABLE_PREFIX + "BUSINESS")
public class PTBusinessEntity extends BusinessEntity
implements IPTBusinessEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "CENTRAL_COMMERCIAL_REGISTRY_NAME", nullable = true, insertable = true, updatable = true)
	private String centralCommercialRegistryName;
	
	@Column(name = "COMMERCIAL_REGISTRY_NUMBER", nullable = true, insertable = true, updatable = true)
	private String commercialRegistryNumber;
	
	
	protected PTBusinessEntity(){}
	
	public PTBusinessEntity(
					ContextEntity operationalContext,
					String financialId,
					String name,
					String commercialName,
					AddressEntityImpl address,
					AddressEntityImpl billingAddress,
					AddressEntityImpl shippingAddress,
					List<ContactEntity> contacts,
					String website,
					List<ApplicationEntityImpl> applications,
					String centralCommercialRegistryName,
					String centralCommercialRegistryNumber){
		
		super(operationalContext, financialId, name, commercialName, address, billingAddress, shippingAddress, contacts, website, applications);
		
		setCentralCommercialRegistryName(centralCommercialRegistryName);
		setCommercialRegistryNumber(centralCommercialRegistryNumber);
	}
	
	
	//GETTERS

	@Override
	public String getCentralCommercialRegistryName() {
		return centralCommercialRegistryName;
	}

	@Override
	public String getCommercialRegistryNumber() {
		return commercialRegistryNumber;
	}

	
	//SETTERS
	
	public void setCentralCommercialRegistryName(
			String centralCommercialRegistryName) {
		this.centralCommercialRegistryName = centralCommercialRegistryName;
	}

	public void setCommercialRegistryNumber(String commercialRegistryNumber) {
		this.commercialRegistryNumber = commercialRegistryNumber;
	}
	
}
