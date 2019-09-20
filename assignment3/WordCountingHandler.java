import java.io.*;
import java.net.*;
import java.rmi.Naming;

public class WordCountingHandler extends Thread{
  private CentralizedRMI centerRD;
  private Socket socket = null;
  private RemoteRMI []rds;
  private BufferedReader in = null;
  private PrintWriter out = null;
  private String sentences = null;

  public WordCountingHandler(Socket socket, RemoteRMI []rds){
    this.socket = socket;
    this.rds = rds;
  }

  public void run() {
    try {
      System.out.println("Got connection from " + socket.getInetAddress());
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);

      sentences = in.readLine();
      String []arrayWords = sentences.split(" ", 0);
      int len =  arrayWords.length;

      WordCounting wc = new WordCounting(this.rds[0], arrayWords,  0, len);
      wc.start();
      try {
        wc.join();
      } catch(InterruptedException ie){}
      WordFrequency dictionary = new WordFrequency();
      dictionary.mergeData(wc.dictionary);
      out.println(dictionary.getData());

      // WordCounting wc1 = new WordCounting(this.rds[0], arrayWords,  0, len * (1 / 3));
      // WordCounting wc2 = new WordCounting(this.rds[1], arrayWords,  len * (1 / 3), len * (2 / 3));
      // WordCounting wc3 = new WordCounting(this.rds[2], arrayWords,  len * (2 / 3), len);
      // wc1.start();
      // wc2.start();
      // wc3.start();
      // try {
      //   wc1.join();
      //   wc2.join();
      //   wc3.join();
      // } catch(InterruptedException ie){}

      // WordFrequency dictionary = new WordFrequency();
      // dictionary.mergeData(wc1.dictionary);
      // dictionary.mergeData(wc2.dictionary);
      // dictionary.mergeData(wc3.dictionary);
      // // for(int i = 0; i < pc_count; i ++) {
      // // }
      // // get result
      // out.println(dictionary.getData());
    } catch(Exception e) {
      System.out.println(e);
    }
  }
}

class WordCounting extends Thread {
  private RemoteRMI rd;
  private String []arrayWords;
  private int start;
  private int end;
  public WordFrequency dictionary;

  public WordCounting(RemoteRMI rd, String []arrayWords, int start, int end) {
    this.rd = rd;
    this.arrayWords = arrayWords;
    this.start = start;
    this.end = end;
    this.dictionary = new WordFrequency();
  }

  public void run() {
    try {
      this.dictionary = this.rd.CountWordFrequency(this.arrayWords, this.start, this.end);
    } catch(Exception e) {
      System.out.println(e);
    }
  }
}
