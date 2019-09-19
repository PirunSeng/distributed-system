import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.rmi.*;

public class SlaveServer extends UnicastRemoteObject implements RemoteRMI {

  private static final long serialVersionUID = 1L;
  private CentralizedRMI centerRD;

  public SlaveServer() throws RemoteException {
		super();
    try {
      this.centerRD =  (CentralizedRMI)Naming.lookup("rmi://192.168.0.101/CentralizedServer");
     } catch(Exception e) {
      System.out.println(e);
    }
	}

	public void CountWordFrequency(String []arrStr, int start, int end) {
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

      while(true) {
        // check turn if my turn?
        // canShareUpdateData
        if(this.centerRD.CanUpdateData(this.serialVersionUID)) {
          // UpdateShareData
          this.centerRD.MergeDictionary(dictionary);
          break;
        }
      }
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
