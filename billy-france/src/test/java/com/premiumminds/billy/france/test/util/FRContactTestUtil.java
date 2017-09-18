/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.test.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.france.persistence.entities.FRContactEntity;
import com.premiumminds.billy.france.services.entities.FRContact;

public class FRContactTestUtil {

    private static final String NAME = "name";
    private static final String TELEPHONE = "998887999";
    private static final String MOBILE = "999999999";
    private static final String EMAIL = "email@email.em";
    private static final String FAX = "9999999122";
    private static final String WEBSITE = "website@website.web";

    private Injector injector;

    public FRContactTestUtil(Injector injector) {
        this.injector = injector;
    }

    public FRContact.Builder getContactBuilder(String name, String telephone, String mobile, String fax, String email,
            String website) {
        FRContact.Builder contactBuilder = this.injector.getInstance(FRContact.Builder.class);

        contactBuilder.setName(name).setEmail(email).setMobile(mobile).setFax(fax).setTelephone(telephone)
                .setWebsite(website);

        return contactBuilder;

    }

    public FRContactEntity getContactEntity(String uid) {
        FRContactEntity entity = (FRContactEntity) this.getContactBuilder().build();
        entity.setUID(new UID(uid));
        return entity;
    }

    public FRContactEntity getContactEntity() {
        return (FRContactEntity) this.getContactBuilder().build();
    }

    public FRContact.Builder getContactBuilder() {
        return this.getContactBuilder(FRContactTestUtil.NAME, FRContactTestUtil.TELEPHONE, FRContactTestUtil.MOBILE,
                FRContactTestUtil.FAX, FRContactTestUtil.EMAIL, FRContactTestUtil.WEBSITE);
    }
}
