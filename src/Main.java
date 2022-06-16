/**
 * Clase principal del programa
 */
import java.io.*;
public class Main{

    public static void prueba(String nombreArchivo,int n,int t,String documentoClaro,String contrasenia){
        System.out.println("cifrando");
    }

    public static void prueba2(String nombre1, String nombre2){
        System.out.println("decifrando");
    }
    public static void main(String[] args) {
  
        String opcion = args[0];
      
        
        if(opcion == "c"){
           String nombre1 =args[1];
           int int1 = Integer.parseInt(args[2]);
           int int2 = Integer.parseInt(args[3]);
           String nombre2 = args[4];
           String contrasenia= args[5];
           prueba(nombre1,int1,int2,nombre2,contrasenia);
          
        }
        if(opcion == "d"){
            String nombre1 = args[1];
            String nombre2= args[2];
            prueba2(nombre1,nombre2);
        }

        // Create the console object
        Console cnsl= System.console();
  
        if (cnsl == null) {
            System.out.println(
                "No console available");
            return;
        }
  
    
        // Read password
        // into character array
        char[] ch = cnsl.readPassword(
            "Teclea la llave : ");
        String password = new String(ch);
        // Print password
        /*  System.out.println(
             "llave : " + password); */

      

    }
}