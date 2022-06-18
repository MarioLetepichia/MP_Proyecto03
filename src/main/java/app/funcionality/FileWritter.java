import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.io.BufferedReader;


public class FileWritter{

public static void writeFileByLine(String filePath, ArrayList<String> linesToWrite) throws IOException {
    
    Path output = Paths.get(filePath);
    try {
        Files.write(output,linesToWrite);
        System.out.println(output.toFile().getAbsolutePath());
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void writeArray(ArrayList<String> linesToWrite,String filePath) throws IOException {
BufferedReader bufReader = new BufferedReader(new FileReader(filePath));
ArrayList<String> listOfLines = new ArrayList<>();

String line = bufReader.readLine();
while (line != null) {
  listOfLines.add(line);
  line = bufReader.readLine();
}

bufReader.close();

}


  public static void main(String[] args) {

    ArrayList<String> listOfInts = new ArrayList<String>();

    try {
        writeArray(listOfInts,"prueba.txt");
        System.out.print(listOfInts);
    } catch (Exception e) {
        //TODO: handle exception
    }
    
  }
}  