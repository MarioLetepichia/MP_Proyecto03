package app.funcionality;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.InvalidAlgorithmParameterException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import java.security.NoSuchAlgorithmException;

public class AESFileEncryption {

	/**
	 * Método para generar una kay apartir de un string 
	 * @param password
	 * @return secret
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */
    public static SecretKey getSecretKey(String password)throws InvalidKeySpecException,InvalidKeyException,
	NoSuchAlgorithmException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] salt = new byte[8];
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
        256);
        SecretKey secretKey = factory.generateSecret(keySpec);
        SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        return secret;

    }
	/**
	 * Método para encriptar un archivo aprtir de una key
	 * @param in nombre del archivo
	 * @param secret de tipo SecretKey
	 * @throws InvalidKeyException
	 * @throws InvalidParameterSpecException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchAlgorithmException
	 * @throws FileNotFoundException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IOException
	 */

    public static void encript(String in, SecretKey secret) throws InvalidKeyException,InvalidParameterSpecException,IllegalBlockSizeException,
	NoSuchAlgorithmException,FileNotFoundException,BadPaddingException, NoSuchPaddingException,IOException{
    //Creacion del archivo extra que contiene el nombre del archivo original
    String[] fileName = in.split("/");
    FileProcessor.saveFileName(fileName[fileName.length - 1]);
		FileInputStream inFile = new FileInputStream(in);
		FileOutputStream outFile = new FileOutputStream("resources/encrypted/encryptedFile.enc");
		byte[] salt = new byte[8];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
		FileOutputStream saltOutFile = new FileOutputStream("resources/encrypted/aux/salt.enc");
		saltOutFile.write(salt);
		saltOutFile.close();

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();

		FileOutputStream ivOutFile = new FileOutputStream("resources/encrypted/aux/iv.enc");
		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		ivOutFile.write(iv);
		ivOutFile.close();
		byte[] input = new byte[64];
		int bytesRead;

		while ((bytesRead = inFile.read(input)) != -1) {
			byte[] output = cipher.update(input, 0, bytesRead);
			if (output != null)
				outFile.write(output);
		}

		byte[] output = cipher.doFinal();
		if (output != null)
			outFile.write(output);

		inFile.close();
		outFile.flush();
		outFile.close();

		System.out.println("File Encrypted.");
		
		}

        /**
		 * Método para descencriptar un archivo apartit de la key
		 * @param secret de tipo SecretKey 
		 * @throws BadPaddingException
		 * @throws InvalidKeyException
		 * @throws NoSuchPaddingException
		 * @throws InvalidAlgorithmParameterException
		 * @throws IllegalBlockSizeException
		 * @throws FileNotFoundException
		 * @throws NoSuchAlgorithmException
		 * @throws IOException
		 */
        public static void descript(SecretKey secret, String EncryptedFile) throws BadPaddingException,
		InvalidKeyException,NoSuchPaddingException, InvalidAlgorithmParameterException,
		IllegalBlockSizeException,FileNotFoundException,NoSuchAlgorithmException,IOException{
      
		FileInputStream saltFis = new FileInputStream("resources/encrypted/aux/salt.enc");
		byte[] salt = new byte[8];
		saltFis.read(salt);
		saltFis.close();

		FileInputStream ivFis = new FileInputStream("resources/encrypted/aux/iv.enc");
		byte[] iv = new byte[16];
		ivFis.read(iv);
		ivFis.close();

    String fileName = FileProcessor.getFileName("resources/encrypted/aux/fileName.txt");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
		FileInputStream fis = new FileInputStream(EncryptedFile);
		FileOutputStream fos = new FileOutputStream(String.format("resources/%s", fileName));
		byte[] in = new byte[64];
		int read;
		while ((read = fis.read(in)) != -1) {
			byte[] output = cipher.update(in, 0, read);
			if (output != null)
				fos.write(output);
		}

		byte[] output = cipher.doFinal();
		if (output != null)
			fos.write(output);
		fis.close();
		fos.flush();
		fos.close();
		System.out.println("File Decrypted.");
	
        }

}