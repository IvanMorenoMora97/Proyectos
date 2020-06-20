package lib;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptoKeys {

	private CryptoKeys() {}
	
	// secret
    
    public static SecretKey generateSecretKey(String algorithm, int size) 
            throws NoSuchAlgorithmException {
     
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        keyGen.init(size); // for example
        SecretKey secretKey = keyGen.generateKey();
        return secretKey;
    }
    
    public static SecretKey importSecretKey(String algorithm, byte[] encodedKey) {
    	
        return new SecretKeySpec(encodedKey, algorithm);
    }
    
    // public
    
    public static KeyPair generateKeyPair(String algorithm, int size) 
            throws NoSuchAlgorithmException {
        
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm);
        kpg.initialize(size);
        KeyPair kp = kpg.generateKeyPair();
        return kp;
    }    
    
    public static PrivateKey importPrivateKey(KeyFactory kf, byte[] encodedKey) 
            throws InvalidKeySpecException {
        
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(encodedKey);
        PrivateKey privateKey = kf.generatePrivate(keySpecPKCS8);
        return privateKey;
    }
    
    public static PublicKey importPublicKey(KeyFactory kf, byte[] encodedKey) 
            throws InvalidKeySpecException {
        
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(encodedKey);
        PublicKey publicKey = kf.generatePublic(keySpecX509);
        return publicKey;
    }    
    
    public static KeyPair importKeyPair(String algorithm, byte[] encPublicKey, byte[] encPrivateKey) 
            throws NoSuchAlgorithmException, InvalidKeySpecException              {
        
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        
        PublicKey publicKey = importPublicKey(kf, encPublicKey);
        PrivateKey privateKey = importPrivateKey(kf, encPrivateKey);
        
        KeyPair kp = new KeyPair(publicKey, privateKey);
        return kp;
    }
    
    // conversion
    
    public static byte[] base64ToBytes(String base64Str) {
        return Base64.getDecoder().decode(base64Str);
    }
    
    public static String bytesToBase64(byte[] hash) {        
        return Base64.getEncoder().encodeToString(hash);
    }
    
    // only for pretty printing
    
    public static String pretty(byte[] bytes) {
    	return pretty(bytes, 0, 0);
    }
    
    public static String pretty(byte[] bytes, int perBlock, int perLine) {
        
        StringBuilder hexString = new StringBuilder();
        
        for (int i = 0; i < bytes.length; i++) {
        	
        	if (perLine > 0 && i % perLine == 0) {
        		hexString.append(System.lineSeparator());
        	}
        	else if (i > 0 && perBlock > 0 && i % perBlock == 0) {
        		hexString.append(" ");
        	}
        	
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
