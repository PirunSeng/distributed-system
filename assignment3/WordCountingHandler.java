import java.io.*;
import java.net.*;
import java.rmi.Naming;

public class WordCountingHandler extends Thread{
  private Socket socket = null;
  private RemoteRMI []rds;
  private WordFrequency dictionary;
  private BufferedReader in = null;
  private PrintWriter out = null;
  private String sentences = null;

  public WordCountingHandler(Socket socket, RemoteRMI []rds){
    this.socket = socket;
    this.rds = rds;
    this.dictionary = new WordFrequency();
  }

  public void run() {
    try{
      System.out.println("Got connection from " + socket.getInetAddress());
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);

      sentences = in.readLine();
      String []arrayWords = sentences.split(" ", 0);
      int len =  arrayWords.length;

      // split array by number of rds
      // use async dictionary to avoid
      //
      WordCounting wc = new WordCounting(this.rds[0], dictionary, arrayWords, 0, len);
      wc.start();

      try {
        wc.join();
      } catch(InterruptedException ie){}

      out.println(dictionary.getDictionary());

    } catch(Exception e) {
      System.out.println(e);
    }
  }
}

class WordCounting extends Thread {
  private RemoteRMI rd;
  private WordFrequency dictionary;
  private String []arrayWords;
  private int start;
  private int end;

  public WordCounting(RemoteRMI rd, WordFrequency dictionary, String []arrayWords, int start, int end) {
    this.rd = rd;
    this.dictionary = dictionary;
    this.arrayWords = arrayWords;
    this.start = start;
    this.end = end;
  }

  public void run() {
    try {
      this.dictionary.mergeWord(this.rd.wordFrequency(this.arrayWords, this.start, this.end));
    } catch(Exception e) {
      System.out.println(e);
    }
  }
}
