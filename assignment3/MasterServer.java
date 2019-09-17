import java.io.*;
import java.net.*;
import java.rmi.Naming;

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
      WordFrequency dictionary = new WordFrequency();

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
          //break sentences by number of PC
          // add each into thread
          // join thead
          // response result
          String []arrayWords = sentences.split(" ", 0);
          int len =  arrayWords.length;
          dictionary = rd.wordFrequency(arrayWords, 0, len/2);
          dictionary.mergeWord(rd.wordFrequency(arrayWords, len/2, len));

          out.println(dictionary.getDictionary());
        }


      }catch(IOException ioe){
        System.out.println(ioe);
      }



    } catch(Exception e) {
      System.out.println(e);
    }
  }
}
