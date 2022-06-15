import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESFileEncryption {

    /*
     * Método para obtener la contraseña segura apartir el string que 
     * se le pide al usuario
     */
    public static SecretKey getSecretKey(String password){

        SecretKeyFactory factory = SecretKeyFactory
        .getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
        256);
        SecretKey secretKey = factory.generateSecret(keySpec);
        SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        return secret;

    }

    public static void encript(FileInputStream inFile, SecretKey secret){
        // file to be encrypted
		inFile = new FileInputStream("prueba2.txt");

		// encrypted file
		FileOutputStream outFile = new FileOutputStream("encryptedfile.des");
        	// password, iv and salt should be transferred to the other end
		// in a secure manner

		// salt is used for encoding
		// writing it to a file
		// salt should be transferred to the recipient securely
		// for decryption
		byte[] salt = new byte[8];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
		FileOutputStream saltOutFile = new FileOutputStream("salt.enc");
		saltOutFile.write(salt);
		saltOutFile.close();
        	//
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();

		// iv adds randomness to the text and just makes the mechanism more
		// secure
		// used while initializing the cipher
		// file to store the iv
		FileOutputStream ivOutFile = new FileOutputStream("iv.enc");
		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		ivOutFile.write(iv);
		ivOutFile.close();

		//file encryption
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
         * Método para descencrptar apartir de la key
         * @param secret
         */
        public static void descript(SecretKey secret){
        // reading the salt
		// user should have secure mechanism to transfer the
		// salt, iv and password to the recipient
		FileInputStream saltFis = new FileInputStream("salt.enc");
		byte[] salt = new byte[8];
		saltFis.read(salt);
		saltFis.close();

		// reading the iv
		FileInputStream ivFis = new FileInputStream("iv.enc");
		byte[] iv = new byte[16];
		ivFis.read(iv);
		ivFis.close();

		// file decryption
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
		FileInputStream fis = new FileInputStream("encryptedfile.des");
		FileOutputStream fos = new FileOutputStream("plainfile_decrypted.txt");
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
	public static void main(String[] args) throws Exception {

        SecretKey secret= getSecretKey("password");
        descript(secret);

    }

}