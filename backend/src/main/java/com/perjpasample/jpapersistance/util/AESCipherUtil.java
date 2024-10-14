package com.perjpasample.jpapersistance.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.perjpasample.jpapersistance.security.Exception.CustomException.InvalidRequestException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;

// AES Encryption - Decryption
@Component
public class AESCipherUtil {

    @Value("${custom.var.aeskey.path}")
    private String SECRETKEY_PATH;

    @Value("${custom.var.aeskey.type}")
    private String KEYSTORE_TYPE;

    @Value("${custom.var.aeskey.algorithm}")
    private String ALGORITHM;
    
    @Value("${custom.var.aeskey.password}")
    private String KEYSTORE_PASSWORD;

    @Value("${custom.var.aeskey.alias}")
    private String KEY_ALIAS;

    private KeyStore keyStore;

    // Load AES Key
    private void loadKeyStore() throws InvalidRequestException {
        try (FileInputStream fis = new FileInputStream(SECRETKEY_PATH)) {
            keyStore = KeyStore.getInstance(KEYSTORE_TYPE);

            keyStore.load(fis, KEYSTORE_PASSWORD.toCharArray());
        } catch (KeyStoreException e) {
            throw new InvalidRequestException("Specified type is not supported.", e);
        } catch (FileNotFoundException e) {
            throw new InvalidRequestException("["+SECRETKEY_PATH+"] File was not found.", e);
        } catch (IOException e) {
            throw new InvalidRequestException("["+SECRETKEY_PATH+"]Error occured during reading of file.", e);
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidRequestException("Invalid algorithm.", e);
        } catch (CertificateException e) {
            throw new InvalidRequestException("The certificate caused error when loaded.", e);
        }
    }

    // To encrypt password using AES
    public String encryptingPassword(String password) throws InvalidRequestException {
        try {
            loadKeyStore();
            SecretKey secretKey = (SecretKey) keyStore.getKey(KEY_ALIAS, KEYSTORE_PASSWORD.toCharArray());
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

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
        } catch (KeyStoreException e) {
            throw new InvalidRequestException("KeyStore was not initialized.", e);
        } catch (UnrecoverableKeyException e) {
            throw new InvalidRequestException("KEYSTORE_PASSWORD is invalid.", e);
        }
    }

    // To decrypt password using AES
    public String decryptingPassword(String encryptedPassword) throws InvalidRequestException {
        try {
            loadKeyStore();
            SecretKey secretKey = (SecretKey) keyStore.getKey(KEY_ALIAS, KEYSTORE_PASSWORD.toCharArray());
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

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
        } catch (KeyStoreException e) {
            throw new InvalidRequestException("KeyStore was not initialized.", e);
        } catch (UnrecoverableKeyException e) {
            throw new InvalidRequestException("KEYSTORE_PASSWORD is invalid.", e);
        }
    }
}
