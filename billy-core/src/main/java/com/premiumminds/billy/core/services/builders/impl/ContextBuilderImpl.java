/*
 * Copyright (C) 2017 Premium Minds.
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
package com.premiumminds.billy.core.services.builders.impl;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.builders.ContextBuilder;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.Localizer;
import javax.inject.Inject;

public class ContextBuilderImpl<TBuilder extends ContextBuilderImpl<TBuilder, TContext>, TContext extends Context>
        extends AbstractBuilder<TBuilder, TContext> implements ContextBuilder<TBuilder, TContext> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    protected DAOContext daoContext;

    @Inject
    public ContextBuilderImpl(DAOContext daoContext) {
        super(daoContext);
        this.daoContext = daoContext;
    }

    @Override
    public TBuilder setName(String name) {
        BillyValidator.notBlank(name, ContextBuilderImpl.LOCALIZER.getString("field.context_name"));
        this.getTypeInstance().setName(name);
        return this.getBuilder();
    }

    @Override
    public TBuilder setDescription(String description) {
        BillyValidator.notBlank(description, ContextBuilderImpl.LOCALIZER.getString("field.description"));
        this.getTypeInstance().setDescription(description);
        return this.getBuilder();
    }

    @Override
    public TBuilder setParentContextUID(StringID<Context> parentUID) {
        if (parentUID == null) {
            this.getTypeInstance().setParentContext(null);
        } else {
            ContextEntity c = this.daoContext.get(parentUID);
            BillyValidator.found(c, ContextBuilderImpl.LOCALIZER.getString("field.parent_context"));
            if (!this.getTypeInstance().isNew() && this.daoContext.isSameOrSubContext(c, this.getTypeInstance())) {
                throw new BillyRuntimeException("Can't set Parent Context on old instance, itself or sub context");
            }
            this.getTypeInstance().setParentContext(c);
        }
        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        ContextEntity c = this.getTypeInstance();
        BillyValidator.mandatory(c.getName(), ContextBuilderImpl.LOCALIZER.getString("field.context_name"));
        BillyValidator.mandatory(c.getDescription(), ContextBuilderImpl.LOCALIZER.getString("field.description"));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ContextEntity getTypeInstance() {
        return (ContextEntity) super.getTypeInstance();
    }

}
