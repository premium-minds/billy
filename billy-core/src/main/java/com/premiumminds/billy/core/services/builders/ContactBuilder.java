/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.services.builders;

import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.entities.Contact;

public interface ContactBuilder<TBuilder extends ContactBuilder<TBuilder, TContact>, TContact extends Contact>
		extends Builder<TContact> {

	public TBuilder setName(String name);

	public TBuilder setTelephone(String telephone);

	public TBuilder setMobile(String mobile);

	public TBuilder setFax(String fax);

	public TBuilder setEmail(String email);

	public TBuilder setWebsite(String website);

}
