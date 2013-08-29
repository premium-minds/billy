/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.entities.jpa;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.premiumminds.billy.core.persistence.entities.jpa.JPAApplicationEntity;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;

@Entity
@Audited
@Table(name = Config.TABLE_PREFIX + "APPLICATION")
public class JPAPTApplicationEntity extends JPAApplicationEntity implements
	PTApplicationEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Basic(optional = false)
	@Column(name = "NUMBER")
	protected Integer			number;

	@Basic(optional = true)
	@Column(name = "KEYS_PATH")
	protected String			path;

	public JPAPTApplicationEntity() {

	}

	@Override
	public List<Contact> getContacts() {
		return super.getContacts();
	}

	@Override
	public Integer getSoftwareCertificationNumber() {
		return this.number;
	}

	@Override
	public void setSoftwareCertificateNum(Integer number) {
		this.number = number;
	}

	@Override
	public URL getApplicationKeysPath() throws MalformedURLException {
		return new URL(this.path);
	}

	public void setApplicationKeysPath(URL path) {
		this.path = path.toExternalForm();
	}

}
