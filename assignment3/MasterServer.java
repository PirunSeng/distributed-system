import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.util.HashMap;
import java.util.Map;

public class MasterServer {
  public static void main(String[] args) {
    ServerSocket server = null;
    Socket socket = null;
    BufferedReader in = null;
    PrintWriter out = null;
    String sentences = null;
    try {
      RemoteRMI rd = (RemoteRMI) Naming.lookup("rmi://localhost/SlaveServer");

      // dictionary
      Map<String, Integer> dictionary = new HashMap<String, Integer>();

      try{
        server = new ServerSocket(8888);

        while(true){
          System.out.println("-------------------------");
          System.out.println("Wait for client to connect....");
          socket = server.accept();
          System.out.println("Got connection from " + socket.getInetAddress());
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          out = new PrintWriter(socket.getOutputStream(), true);

          sentences = in.readLine();

          dictionary = rd.wordFrequency(sentences);

          for(Map.Entry<String, Integer> word : dictionary.entrySet()) {
            System.out.println(word.getKey() + ":" + word.getValue());
          }
          out.println(dictionary);
        }


      }catch(IOException ioe){
        System.out.println(ioe);
      }



    } catch(Exception e) {
      System.out.println(e);
    }
  }
}
