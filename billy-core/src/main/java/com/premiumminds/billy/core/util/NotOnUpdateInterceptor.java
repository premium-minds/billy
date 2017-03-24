/**
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
package com.premiumminds.billy.core.util;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.premiumminds.billy.core.exceptions.BillyUpdateException;
import com.premiumminds.billy.core.persistence.entities.BaseEntity;
import com.premiumminds.billy.core.services.builders.impl.AbstractBuilder;

/**
 * Intercepts methods calls to annotated with {@link NotOnUpdate}.
 * 
 * Only runs if it building an entity marked as new. Otherwise @throws
 * {@link BillyUpdateException}
 * 
 * @author Hugo Correia
 * 
 */
public class NotOnUpdateInterceptor implements MethodInterceptor {

	public static final String	METHOD_NAME	= "getTypeInstance";

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		String exceptionMessage = invocation.getMethod()
				.getAnnotation(NotOnUpdate.class).message();

		Method method = getMethod(invocation.getThis().getClass());
		BaseEntity entity = (BaseEntity) method.invoke(invocation.getThis(),
				new Object[] {});

		if (entity.isNew()) {
			return invocation.proceed();

		} else
			throw new BillyUpdateException(exceptionMessage);
	}

	private Method getMethod(Class<? extends Object> clazz)
		throws NoSuchMethodException {
		if (clazz.getCanonicalName().equals(Object.class.getCanonicalName()))
			throw new NoSuchMethodException(METHOD_NAME);
		if (clazz.getCanonicalName().equals(
				AbstractBuilder.class.getCanonicalName())) {

			Method foundMethod = clazz.getDeclaredMethod(METHOD_NAME,
					new Class[] {});
			foundMethod.setAccessible(true);
			return foundMethod;
		} else {
			return getMethod(clazz.getSuperclass());
		}

	}
}
