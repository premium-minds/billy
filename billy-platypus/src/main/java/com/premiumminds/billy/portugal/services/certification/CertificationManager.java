/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.certification;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.codec.binary.Base64;

public class CertificationManager {

	private static final int EXPECTED_HASH_LENGTH = 172;

	private PrivateKey privateKey;
	private PublicKey publicKey;
	private Signature signature;
	private boolean autoVerifyHash;

	public CertificationManager() {
		this.privateKey = null;
		this.publicKey = null;
		this.autoVerifyHash = false;

		try {
			this.signature = Signature.getInstance("SHA1withRSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	public void setAutoVerifyHash(boolean verify) {
		this.autoVerifyHash = verify;
	}

	public String getHashBase64(String source) throws InvalidHashException,
			InvalidKeyException {
		String hashBase64 = Base64.encodeBase64String(this
				.getHashBinary(source));
		if (this.autoVerifyHash) {
			if ((!this.verifyHashBase64(source, hashBase64))
					|| (hashBase64.length() != CertificationManager.EXPECTED_HASH_LENGTH)) {
				throw new InvalidHashException();
			}
		}
		return hashBase64;

	}

	public byte[] getHashBinary(String source) throws InvalidHashException,
			InvalidKeyException {

		byte[] hash;

		try {
			this.signature.initSign(this.privateKey);
			this.signature.update(source.getBytes());
			hash = this.signature.sign();
			if (this.autoVerifyHash) {
				if (!this.verifyHashBinary(source, hash)) {
					throw new InvalidHashException();
				}

			}
		} catch (SignatureException e) {
			throw new InvalidHashException(
					"Signature exception - should not happen");
		}

		return hash;
	}

	public boolean verifyHashBase64(String source, String hashBase64)
			throws InvalidKeyException {
		return (this.verifyHashBinary(source, Base64.decodeBase64(hashBase64)) && (hashBase64
				.length() == CertificationManager.EXPECTED_HASH_LENGTH));
	}

	public boolean verifyHashBinary(String source, byte[] hash)
			throws InvalidKeyException {
		try {
			this.signature.initVerify(this.publicKey);
			this.signature.update(source.getBytes());
			return this.signature.verify(hash);
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setPrivateKey(PrivateKey key) throws InvalidKeySpecException {
		this.privateKey = key;
	}

	public void setPublicKey(PublicKey key) throws InvalidKeySpecException {
		this.publicKey = key;
	}

}
