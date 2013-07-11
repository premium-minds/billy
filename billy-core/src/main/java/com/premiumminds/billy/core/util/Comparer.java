/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy-core.
 * 
 * billy-core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * billy-core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-core. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class Comparer {

	public static <T extends Object> boolean areEqual(T o1, T o2)
			throws IOException {
		ByteArrayOutputStream bos1 = new ByteArrayOutputStream(), bos2 = new ByteArrayOutputStream();
		ObjectOutputStream oos1 = new ObjectOutputStream(bos1);
		ObjectOutputStream oos2 = new ObjectOutputStream(bos2);
		oos1.writeObject(o1);
		oos2.writeObject(o2);
		byte[] o1Bytes = bos1.toByteArray();
		byte[] o2Bytes = bos2.toByteArray();

		if (o1Bytes.length != o2Bytes.length) {
			return false;
		}

		for (int i = 0; i < o1Bytes.length; ++i) {
			if (o1Bytes[i] != o2Bytes[i]) {
				return false;
			}
		}
		return true;
		// if(o1.getClass().isAssignableFrom(IBaseEntity.class)) {
		// return ((IBaseEntity) o1).getUUID().equals(((IBaseEntity)
		// o2).getUUID());
		// } else {
		// return o1.equals(o2);
		// }
		// for(Method m : o1.getClass().getMethods()) {
		// if(m.getName().startsWith("get")) {
		// Object r1 = m.invoke(o1, (Object[])null);
		// Object r2 = m.invoke(o2, (Object[])null);
		// if(null == r1 && null == r2) {
		// continue;
		// }else {
		// if(null == r1 || !r1.equals(r2)) {
		// throw new Exception("NOT EQUAL: " + m.getName());
		// //return false;
		// }
		// }
		// }
		// }
		// return true;
	}

}
