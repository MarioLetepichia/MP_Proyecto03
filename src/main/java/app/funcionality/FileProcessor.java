package app.funcionality;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.math.BigInteger;
import java.util.ArrayList;
import org.javatuples.Pair;

public class FileProcessor {

  /**
   * Método para guardar el nombre del archivo
   * @param name
   */
  public static void saveFileName(String name) {
    //agregar resources/
    Path path = Paths.get("resources/encrypted/aux/fileName.txt");

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

  public static String getFileName(String file){
    Path path = Paths.get(file);  
    String content= "";
    try {
        content = Files.readString(path);
    }catch (IOException e){
        
    }
    return content;
  } 

  public static void writeFileByLine(String filePath, ArrayList<Pair<BigInteger,BigInteger>> linesToWrite) throws IOException {
    Path output = Paths.get(filePath);
    ArrayList<String> redifinedLines = new ArrayList<>();
    for (Pair<BigInteger,BigInteger> tuple : linesToWrite) {
        String temp = String.format("(%s,%s)", tuple.getValue0().toString(), tuple.getValue1().toString());
        redifinedLines.add(temp);
    }

    try {
        Files.write(output,redifinedLines);
        System.out.println(output.toFile().getAbsolutePath());
    } catch (Exception e) {
        e.printStackTrace();
    }
  }

  public static ArrayList<Pair<BigInteger, BigInteger>> readEvaluations(String filePath) throws IOException {
    BufferedReader in = new BufferedReader(new FileReader(filePath));
    ArrayList<String> listOfLines = new ArrayList<>();
    
    String line = in.readLine();
    while (line != null) {
      listOfLines.add(line);
      line = in.readLine();
    }
    in.close();
    ArrayList<Pair<BigInteger,BigInteger>> evaluationList = new ArrayList<>();

    for (String temp : listOfLines) {
        String[] entries = temp.split(",");
        String x = entries[0].substring(1);
        String y = entries[1].substring(0,entries[1].length()-1);
        Pair<BigInteger, BigInteger> tuple = new Pair<BigInteger,BigInteger>(new BigInteger(x), new BigInteger(y));
        evaluationList.add(tuple);
    }
    return evaluationList;    
}


}
