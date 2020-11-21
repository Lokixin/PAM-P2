/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmd;

/**
 *
 * @author lokix
 * 
 * This file is an executable. It can be used to generate a SecretKey and
 * store it into the KeyStore. It uses the CryptoTools class.
*/

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.crypto.SecretKey;
import utilities.CryptoTools;


public class CreateAndStoreKey {
    
    public static void main(String[] args) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        String filepath = "keystore.keystore";
        String password = "pimpampum";
        CryptoTools ct = new CryptoTools(filepath, password);
        SecretKey key = ct.loadFromKeyStore();
        System.out.print(key);
    }
}
