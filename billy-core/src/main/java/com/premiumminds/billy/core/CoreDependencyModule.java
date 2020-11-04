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
package com.premiumminds.billy.core;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import javax.inject.Inject;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.documents.impl.DocumentIssuingServiceImpl;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.core.util.NotImplementedInterceptor;
import com.premiumminds.billy.core.util.NotOnUpdate;
import com.premiumminds.billy.core.util.NotOnUpdateInterceptor;

public class CoreDependencyModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(DocumentIssuingService.class).to(DocumentIssuingServiceImpl.class);

		Matcher<Object> allClassesMatcher = Matchers.any();
		AbstractMatcher<Method> nonSyntheticMethods = new AbstractMatcher<Method>() {
			@Override
			public boolean matches(Method method) {
				return !method.isSynthetic();
			}
		};

		Matcher<AnnotatedElement> notImplementedMatcher = Matchers.annotatedWith(NotImplemented.class);
		Matcher<Method> combinedMethodMatcher1 = nonSyntheticMethods.and(notImplementedMatcher);
		NotImplementedInterceptor notImplementedInterceptor = new NotImplementedInterceptor();
		this.bindInterceptor(allClassesMatcher, combinedMethodMatcher1, notImplementedInterceptor);


		Matcher<AnnotatedElement> annotatedElementMatcher = Matchers.annotatedWith(NotOnUpdate.class);
		Matcher<Method> combinedMethodMatcher2 = nonSyntheticMethods.and(annotatedElementMatcher);
		NotOnUpdateInterceptor notOnUpdateInterceptor = new NotOnUpdateInterceptor();
		this.bindInterceptor(allClassesMatcher, combinedMethodMatcher2, notOnUpdateInterceptor);
    }

    public static class Initializer {

        @Inject
        public Initializer() {
        }
    }

}
