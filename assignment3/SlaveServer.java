import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.rmi.*;

public class SlaveServer extends UnicastRemoteObject implements RemoteRMI {

  private static final long serialVersionUID = 1L;

  public SlaveServer() throws RemoteException {
		super();
	}

	public WordFrequency wordFrequency(String []arrStr, int start, int end) {
		try{
			System.out.println("A client is called!!! from ::" + getClientHost());
			System.out.println("Local addr : " + InetAddress.getLocalHost());
		  WordFrequency dictionary = new WordFrequency();
      String word = "";
      for(int i = start; i < end; i++) {
        word = arrStr[i];
        word = word.replaceAll("[^\\p{Alpha}]+", " ");
        word = word.trim().toLowerCase();
        if(word != "null" && !word.isEmpty()) {
          dictionary.addWord(word);
        }
      }

			return dictionary;
		} catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public static void main(String args[]) {
		try {
			LocateRegistry.createRegistry(1099);
			SlaveServer ds = new SlaveServer();
			Naming.rebind("SlaveServer", ds);
			System.out.println("SlaveServer is created!!!");
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
