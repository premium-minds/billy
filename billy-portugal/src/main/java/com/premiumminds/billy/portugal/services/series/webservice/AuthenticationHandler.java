/*
 * Copyright (C) 2017 Premium Minds.
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
package com.premiumminds.billy.portugal.services.series.webservice;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

class AuthenticationHandler implements SOAPHandler<SOAPMessageContext>  {

    private static final String AUTH_NS = "http://schemas.xmlsoap.org/ws/2002/12/secext";
    private static final String AUTH_PREFIX = "wss";
    private static final SimpleDateFormat TIMESTAMP_FORMATER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
    static {
        TIMESTAMP_FORMATER.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private final WebserviceCredentials webserviceCredentials;
    private final WebserviceKeys webserviceKeys;

    public AuthenticationHandler(WebserviceCredentials webserviceCredentials, WebserviceKeys webserviceKeys) {
        this.webserviceCredentials = webserviceCredentials;
        this.webserviceKeys = webserviceKeys;
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(final SOAPMessageContext smc) {
        boolean direction = (Boolean) smc.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (direction){
            try {
                final byte[] simetricKey = generateRequestKey();

                final byte[] encryptedPassword = cypherCredential(simetricKey, webserviceCredentials.getPassword());
                final String b64EncryptedPassword = Base64.getEncoder().encodeToString(encryptedPassword);

                final String timestamp = getTimestamp();
                final byte[] encryptedTimestamp = cypherCredential(simetricKey, timestamp);
                final String b64EncryptedTimestamp = Base64.getEncoder().encodeToString(encryptedTimestamp);

                final Key publicKey = getPublicKeyFromKeystore();
                final byte[] encriptedSimetricKey = cypherRequestKey((PublicKey) publicKey, simetricKey);
                final String b64EncryptedSimetricKey = Base64.getEncoder().encodeToString(encriptedSimetricKey);

                createSoapSecurityHeader(smc, b64EncryptedPassword, b64EncryptedTimestamp, b64EncryptedSimetricKey,
                        webserviceCredentials.getUsername());

            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean handleFault(final SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(final MessageContext context) {

    }

    private void createSoapSecurityHeader(SOAPMessageContext smc, String b64EncryptedPassword,
            String b64EncryptedTimestamp, String b64EncryptedSimetricKey, String username) throws SOAPException {
        SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
        SOAPFactory soapFactory = SOAPFactory.newInstance();

        SOAPElement wsSecHeaderElm = soapFactory.createElement("Security", AUTH_PREFIX, AUTH_NS);
        SOAPElement userNameTokenElm = soapFactory.createElement("UsernameToken", AUTH_PREFIX, AUTH_NS);
        SOAPElement userNameElm = soapFactory.createElement("Username", AUTH_PREFIX, AUTH_NS);
        userNameElm.addTextNode(username);
        SOAPElement passwdElm = soapFactory.createElement("Password", AUTH_PREFIX, AUTH_NS);
        passwdElm.addTextNode(b64EncryptedPassword);
        SOAPElement nonceElm = soapFactory.createElement("Nonce", AUTH_PREFIX, AUTH_NS);
        nonceElm.addTextNode(b64EncryptedSimetricKey);
        SOAPElement createdElm = soapFactory.createElement("Created", AUTH_PREFIX, AUTH_NS);
        createdElm.addTextNode(b64EncryptedTimestamp);

        userNameTokenElm.addChildElement(userNameElm);
        userNameTokenElm.addChildElement(passwdElm);
        userNameTokenElm.addChildElement(nonceElm);
        userNameTokenElm.addChildElement(createdElm);

        wsSecHeaderElm.addChildElement(userNameTokenElm);

        final SOAPHeader sh = envelope.getHeader();

        sh.addChildElement(wsSecHeaderElm);
    }

    private byte[] cypherRequestKey(PublicKey publicKey, byte[] requestKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(requestKey);
    }

    public byte[] generateRequestKey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);

        Key keyToBeWrapped = generator.generateKey();

        return keyToBeWrapped.getEncoded();
    }

    private byte[] cypherCredential(byte[] requestKey, String credential)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(requestKey, "AES"));

        return cipher.doFinal(credential.getBytes(StandardCharsets.UTF_8));
    }

    private Key getPublicKeyFromKeystore()
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

        KeyStore ks = KeyStore.getInstance("JKS");

        try (InputStream fis = webserviceKeys.getKeystore().toURL().openStream()) {
            ks.load(fis, webserviceKeys.getKeystorePassword().toCharArray());
            return ks.getCertificate(webserviceKeys.getKeyAlias()).getPublicKey();
        }
    }

    private static String getTimestamp() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return TIMESTAMP_FORMATER.format(c.getTime());
    }
}
