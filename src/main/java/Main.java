import app.funcionality.*;

import java.io.Console;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.javatuples.Pair;

/**
 * Clase principal del programa
 */

public class Main{
    public static void main(String[] args) {
        if(args.length == 0){
            usoCorrecto();
            System.exit(0);
        }
        String opcion = args[0];
        if(opcion.compareTo("c") == 0){
            if(args.length == 5)
                encriptar(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), args[4]); 
            else
                usoCorrecto();
        }
        else if(opcion.compareTo("d") == 0){
            if(args.length == 3)
                desencriptar(args[1], args[2]);
            else
                usoCorrecto();
        }
        else{
            System.out.println("No se detecto ninguna opcion valida. Intentalo de nuevo\n");
            usoCorrecto();
            System.exit(0);
        }

    }
        
    private static void usoCorrecto(){
        System.out.println("USO DEL PROGRAMA \n" +
        " - Cifrar \n     c <Nombre del archivo que contendra n evaluaciones> <n = evaluaciones requeridas> "+
        "<t = minimo de puntos necesarios para descifrar> <Direccion del archivo a cifrar>" +
        "\n\n - Descifrar \n     d <Direccion del archivo que contiene minimo t evaluaciones> < Direccion del archivo cifrado>");
    }

    public static void encriptar(String evaluations, int requiredEvaluations, int minimunEvaluations, String file){
        //Pedir contrasena al usuario
        Console cnsl= System.console();
  
        if (cnsl == null) {
            System.out.println(
                "No console available");
            return;
        }
        char[] ch = cnsl.readPassword(
            "Teclea la constrasena: ");
        String password = new String(ch);

        SecretKey key = null;
        BigInteger keyNumber = BigInteger.ZERO;
        try {
            key = AESFileEncryption.getSecretKey(password);
            keyNumber = new BigInteger(key.getEncoded());
            RandomPolynomial poly = new RandomPolynomial(minimunEvaluations, keyNumber.toString());
            ArrayList<Pair<BigInteger, BigInteger>> evaluationsList = poly.getNPoints(requiredEvaluations);
            FileProcessor.writeFileByLine(String.format("resources/encrypted/%s.frg", evaluations), evaluationsList);
            AESFileEncryption.encript(file, key);
        } catch (InvalidKeyException|InvalidKeySpecException|NoSuchAlgorithmException e) {
            System.err.println("Hubo un error con la llave");
            System.exit(1);
        } catch (IOException n){
            System.err.println("Ocurrio un error inesperado");
            System.exit(1);
        } catch(InvalidParameterSpecException|IllegalBlockSizeException|BadPaddingException|NoSuchPaddingException m){
            System.err.println("Ocurrio un error con la encriptacion");
            System.exit(1);
        }
    }

    

    public static void desencriptar(String evaluations, String encryptedFile){
        ArrayList<Pair<BigInteger, BigInteger>> evaluationsList;
        
        try {
            evaluationsList = FileProcessor.readEvaluations(evaluations);
            //for(Pair<BigInteger, BigInteger> item : evaluationsList){
            //    System.out.println(String.format("(%s,%s)", item.getValue0().toString(), item.getValue1().toString()));
            //}
            //Recuperamos La llave 
            PolynomialInterpolation poly = new PolynomialInterpolation(evaluationsList);
            BigInteger keyNumber = poly.calculateIndependent();
            //BigInteger --> bytes[] --> SecretKey
            byte[] encodedKey = keyNumber.toByteArray();
            SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
            //Solo falta desencriptar
            AESFileEncryption.descript(originalKey, encryptedFile);
        } catch (IOException e) {
            System.err.println("Ocurrio un error inesperado");
            System.out.print(e);
            System.exit(1);
        } catch (BadPaddingException|InvalidKeyException|NoSuchAlgorithmException|InvalidAlgorithmParameterException|IllegalBlockSizeException|NoSuchPaddingException e){
            System.err.println("Hubo un error con el descifrado");
            System.out.println(e);
            System.exit(1);
        }
    }
}
