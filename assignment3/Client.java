import java.io.*;
import java.net.*;

public class Client {
  private Socket socket;
  private PrintWriter out;
  private BufferedReader in;

  public Client(String host, int port, String file) {
    try {
      socket = new Socket(host, port);
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      // String [] files = {
      //   "data/500kb.txt",
      //   "data/1000kb.txt",
      //   "data/1500kb.txt",
      //   "data/2000kb.txt",
      //   "data/2500kb.txt",
      //   "data/3000kb.txt",
      // };
      // for(String file : files) {
      // }
      sendFile(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void sendFile(String fileName) throws IOException {
    FileReader fileReader =
                new FileReader(fileName);
    BufferedReader bufferedReader =
                new BufferedReader(fileReader);
    String sentences = null;
    String line = null;
    while((line = bufferedReader.readLine()) != null) {
      sentences = sentences + " " + line;
    }
    fileReader.close();
    bufferedReader.close();

    long startTime = System.currentTimeMillis();
    out.println(sentences);

    in.readLine();
    // System.out.println(in.readLine());
    long endTime = System.currentTimeMillis();


    System.out.println("-----------------------------");
    System.out.println(fileName + " : " + (endTime - startTime));
  }

  public static void main(String[] args) {
    Client fc = new Client("localhost", 8888, args[0]);
  }
}
