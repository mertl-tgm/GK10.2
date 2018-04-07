package main.webapp;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class Security {
	private static String password = "NBsfdqQj4eE1HE3noX2LWHxcy5C1zXbU";
	private SecretKeySpec key;
	
	public Security() {
		byte[] salt = new String("xXNmXtDa7n6Rn1gx7a425lTciV8LY9Uu").getBytes();
		int iterationCount = 40000;
		int keyLength = 128;
		try {
			this.key = createSecretKey(password.toCharArray(), salt, iterationCount, keyLength);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
	
	private static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
		SecretKey key = factory.generateSecret(keySpec);
		return new SecretKeySpec(key.getEncoded(), "AES");
	}

	public String encrypt(String text) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, this.key);
			AlgorithmParameters param = cipher.getParameters();
			IvParameterSpec paramSpec = param.getParameterSpec(IvParameterSpec.class);
			byte[] cryptoText = cipher.doFinal(text.getBytes("UTF-8"));
			byte[] iv = paramSpec.getIV();
			return base64Encode(iv) + ":" + base64Encode(cryptoText);
		} catch (GeneralSecurityException|IOException e) {
			e.printStackTrace();
		}
		return "error";
	}

	public String decrypt(String text) {
		try {
			String iv = text.split(":")[0];
			String property = text.split(":")[1];
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, this.key, new IvParameterSpec(base64Decode(iv)));
			return new String(cipher.doFinal(base64Decode(property)), "UTF-8");
		} catch (GeneralSecurityException|IOException e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	private static String base64Encode(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	private static byte[] base64Decode(String text) throws IOException {
		return Base64.getDecoder().decode(text);
	}

}
