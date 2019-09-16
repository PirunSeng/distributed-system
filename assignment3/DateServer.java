import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.rmi.*;
import java.util.HashMap;
import java.util.Map;

public class DateServer extends UnicastRemoteObject
	implements RemoteDate{

	//private static final long serialVersionUID = 1L;

	/**
   *
   */
  private static final long serialVersionUID = 1L;

  public DateServer() throws RemoteException {
		super();
	}
	
	public Map<String, Integer> wordFrequency(String str){
		try{
			System.out.println("A client is called!!! from ::" + getClientHost());
			System.out.println("Local addr : " + InetAddress.getLocalHost());
		  Map<String, Integer> dictionary = new HashMap<String, Integer>();
      // String line = "Hello World. It has two world.";
      str = str.replaceAll("[^\\p{Alpha}]+", " ");
      // loop on all words
      for (String word : str.split(" ", 0)) {
        word = word.trim().toLowerCase();
        if(!word.isEmpty()) {
          // check if word already have in dictionary			
          if(dictionary.containsKey(word)) {
            dictionary.put(word, dictionary.get(word) + 1);
          } else {
            dictionary.put(word, 1);
          }
        }
      }
			return dictionary;
		}catch(Exception e){
			System.out.println(e);
		}
		return null;
	}
	
	public static void main(String args[]){
		try{
			LocateRegistry.createRegistry(1099);
			DateServer ds = new DateServer();
			Naming.rebind("DateServer", ds);
			//registry.rebind("DateServer", ds);
			System.out.println("DateServer is created!!!");
		}catch(Exception e){
			System.out.println(e);
		}
	}
}