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
package com.premiumminds.billy.andorra.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generates {@link PrivateKey} and {@link PublicKey}.
 */
public class KeyGenerator {

    private static final Logger log = LoggerFactory.getLogger(KeyGenerator.class);

    private final URL privateKey;

    /**
     * Generates the {@link PrivateKey} and {@link PublicKey} based on the
     * {@link PrivateKey} location.
     *
     * @param privateKey path to private key
     */
    public KeyGenerator(URL privateKey) {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        this.privateKey = privateKey;
    }

    private String getKeyFromFile() {
        InputStream inputStream = null;
        String key = "";

        try {
            inputStream = this.privateKey.openStream();
            key = IOUtils.toString(inputStream);
        } catch (IOException e) {
            KeyGenerator.log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        return key;
    }

    private KeyPair getKeyPair() {
        PEMParser pemReader = new PEMParser(new StringReader(this.getKeyFromFile()));
        KeyPair pair = null;

        try {
            PEMKeyPair pemKeyPair = (PEMKeyPair) pemReader.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            pair = converter.getKeyPair(pemKeyPair);
        } catch (IOException e) {
            KeyGenerator.log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(pemReader);
        }

        return pair;
    }

    /**
     * @return {@link PrivateKey}
     */
    public PrivateKey getPrivateKey() {
        return this.getKeyPair().getPrivate();
    }

    /**
     * @return {@link PublicKey}
     */
    public PublicKey getPublicKey() {
        return this.getKeyPair().getPublic();
    }

}
