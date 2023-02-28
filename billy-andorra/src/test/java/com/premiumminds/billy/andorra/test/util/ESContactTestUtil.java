/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.test.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.andorra.persistence.entities.ADContactEntity;
import com.premiumminds.billy.andorra.services.entities.ADContact;

public class ESContactTestUtil {

    private static final String NAME = "name";
    private static final String TELEPHONE = "998887999";
    private static final String MOBILE = "999999999";
    private static final String EMAIL = "email@email.em";
    private static final String FAX = "9999999122";
    private static final String WEBSITE = "website@website.web";

    private Injector injector;

    public ESContactTestUtil(Injector injector) {
        this.injector = injector;
    }

    public ADContact.Builder getContactBuilder(String name, String telephone, String mobile, String fax, String email,
											   String website) {
        ADContact.Builder contactBuilder = this.injector.getInstance(ADContact.Builder.class);

        contactBuilder.setName(name).setEmail(email).setMobile(mobile).setFax(fax).setTelephone(telephone)
                .setWebsite(website);

        return contactBuilder;

    }

    public ADContactEntity getContactEntity(String uid) {
        ADContactEntity entity = (ADContactEntity) this.getContactBuilder().build();
        entity.setUID(StringID.fromValue(uid));
        return entity;
    }

    public ADContactEntity getContactEntity() {
        return (ADContactEntity) this.getContactBuilder().build();
    }

    public ADContact.Builder getContactBuilder() {
        return this.getContactBuilder(ESContactTestUtil.NAME, ESContactTestUtil.TELEPHONE, ESContactTestUtil.MOBILE,
                ESContactTestUtil.FAX, ESContactTestUtil.EMAIL, ESContactTestUtil.WEBSITE);
    }
}
