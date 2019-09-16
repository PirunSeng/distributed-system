import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.rmi.Naming;
import java.util.HashMap;
import java.util.Map;

public class DateClient {
  public static void main(String[] args) {
    RemoteDate rd;
    try {
      rd = (RemoteDate) Naming.lookup("rmi://" + args[0] + "/DateServer");
    
      // dictionary
      Map<String, Integer> dictionary = new HashMap<String, Integer>();
      
      // The name of the file to open.
      String fileName = "my_file.txt";
      // This will reference one line at a time
      String line = null;

      try {
        // FileReader reads text files in the default encoding.
        FileReader fileReader = 
            new FileReader(fileName);

        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = 
            new BufferedReader(fileReader);

        while((line = bufferedReader.readLine()) != null) {
          // remove no English words		
          line = line.replaceAll("[^\\p{Alpha}]+", " ");
          try {
            // RemoteDate rd = (RemoteDate)Naming.lookup("rmi://192.168.10.101/DateServer");
            dictionary = rd.wordFrequency(line);
            // for(Map.Entry<String, Integer> word : dictionary.entrySet()) {
            //   System.out.println(word.getKey() + ":" + word.getValue());
            // }
          }catch(Exception e){
            System.out.println(e);
          }
        }   

        // Always close files.
        bufferedReader.close();         
      }
      catch(FileNotFoundException ex) {
          System.out.println(
              "Unable to open file '" + 
              fileName + "'");                
      }
      catch(Exception ex) {
          System.out.println(
              "Error reading file '" 
              + fileName + "'");                  
          // Or we could just do this: 
          // ex.printStackTrace();
      }
      for(Map.Entry<String, Integer> word : dictionary.entrySet()) {
        System.out.println(word.getKey() + ":" + word.getValue());
      }
    }catch(Exception e){
      System.out.println(e);
    }
  }
}