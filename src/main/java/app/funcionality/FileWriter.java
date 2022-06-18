package app.funcionality;

import java.io.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriter {

  /**
   * Método para guardar el nombre del archivo
   * @param name
   */
  public static void getFileNameToTxt(String name) {
    //agregar resources/
    Path path = Paths.get("filename.txt");

    String str = name;

    try {
      Files.writeString(path, str, StandardCharsets.UTF_8);
    } 
    catch (IOException ex) {
      System.out.print("Invalid Path");
    }
  }

  /*
   * Método para obtener el nombre del archivo
   */

  public static String getFileNameToString(String fileText){
    
    File file = new File(fileText);
    Path path = Paths.get(fileText);  
    String content= "";
    try {
        content = Files.readString(path);
    }catch (IOException e){
        
    }
    return content;
  } 


}
