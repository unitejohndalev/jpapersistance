package com.perjpasample.jpapersistance.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.perjpasample.jpapersistance.security.Exception.CustomException.InvalidRequestException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.UnrecoverableKeyException;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;

// RSA Encryption - Decryption
@Component
public class KeyPairUtil {

    @Value("${custom.var.rsa.path}")
    private String RSAKEY_PATH;
    
    @Value("${custom.var.rsa.type}")
    private String KEYSTORE_TYPE;

    @Value("${custom.var.rsa.algorithm}")
    private String ALGORITHM;

    @Value("${custom.var.rsa.password}")
    private String KEYSTORE_PASSWORD;

    @Value("${custom.var.rsa.alias}")
    private String KEY_ALIAS;

    private KeyStore keyStore;

    // Load RSA Key
    private void loadKeyStore() throws InvalidRequestException {
        try (FileInputStream fis = new FileInputStream(RSAKEY_PATH)) {
            keyStore = KeyStore.getInstance(KEYSTORE_TYPE);

            keyStore.load(fis, KEYSTORE_PASSWORD.toCharArray());
        } catch (KeyStoreException e) {
            throw new InvalidRequestException("Specified type is not supported.", e);
        } catch (FileNotFoundException e) {
            throw new InvalidRequestException("["+RSAKEY_PATH+"] File was not found.", e);
        } catch (IOException e) {
            throw new InvalidRequestException("["+RSAKEY_PATH+"]Error occured during reading of file.", e);
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidRequestException("Invalid algorithm.", e);
        } catch (CertificateException e) {
            throw new InvalidRequestException("The certificate caused error when loaded.", e);
        }
    }

    // To get Public Key from RSA Key
    public PublicKey getPublicKey() throws InvalidRequestException {
        try {
            loadKeyStore();
            Certificate certificate = keyStore.getCertificate(KEY_ALIAS);
            PublicKey publicKey = certificate.getPublicKey();
            
            return publicKey;
        } catch (KeyStoreException e) {
            throw new InvalidRequestException("KeyStore was not initilized.", e);
        }
    }

    // To get Public Key from RSA Key
    public PrivateKey getPrivateKey() throws InvalidRequestException {
        try {
            loadKeyStore();
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(KEY_ALIAS, KEYSTORE_PASSWORD.toCharArray());
            
            return privateKey;
        } catch (KeyStoreException e) {
            throw new InvalidRequestException("Private key file was not found.", e);
        } catch (UnrecoverableKeyException e) {
            throw new InvalidRequestException("Error occur during converting Bytes to String.", e);
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidRequestException("Invalid algorithm.", e);
        }
    }

    // To encrypt password using RSA
    public String encryptingPassword(String password, PublicKey publicKey) throws InvalidRequestException {
        try {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            String encodedString = Base64.getEncoder().encodeToString(encryptedBytes);

            if (encodedString == null || encodedString.isEmpty()) {
                throw new InvalidRequestException("Error occurs during converting Bytes to String.");
            }
            return encodedString;
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidRequestException("Invalid algorithm.", e);
        } catch (NoSuchPaddingException e) {
            throw new InvalidRequestException("Padding scheme is not supported.", e);
        } catch (InvalidKeyException e) {
            throw new InvalidRequestException("Invalid decryption key provided.", e);
        } catch (IllegalBlockSizeException e) {
            throw new InvalidRequestException("Invalid block size.", e);
        } catch (BadPaddingException e) {
            throw new InvalidRequestException("Input Data not padded properly.", e);
        }
    }

    // To decrypt password using RSA
    public String decryptingPassword(String encryptedPassword, PrivateKey privateKey) throws InvalidRequestException {
        try {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        
        System.out.println(new String(decryptedBytes));

        return new String(decryptedBytes);
        } catch (InvalidKeyException e) {
            throw new InvalidRequestException("Invalid decryption key provided.", e);
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidRequestException("Invalid decryption algorithm.", e);
        } catch (NoSuchPaddingException e) {
            throw new InvalidRequestException("Padding scheme is not supported.", e);
        } catch (IllegalBlockSizeException e) {
            throw new InvalidRequestException("Invalid block size.", e);
        } catch (BadPaddingException e) {
            throw new InvalidRequestException("Input Data not padded properly.", e);
        }
    }
}
