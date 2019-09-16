import java.io.*;
import java.net.*;

public class Server {
  public static void main(String[] args){
    ServerSocket server = null;
    Socket socket = null;
    BufferedReader in = null;
    PrintWriter out = null;
    try{
      server = new ServerSocket(9999);

      while(true){
        System.out.println("-------------------------");
        System.out.println("Wait for client to connect....");
        socket = server.accept();
        System.out.println("Got connection from " + socket.getInetAddress());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        String sentences = in.readLine();
        System.out.println(sentences);
      }

    }catch(IOException ioe){
      System.out.println(ioe);
    }
  }
}
