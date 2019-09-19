import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.rmi.*;

public class CentralizedServer extends UnicastRemoteObject implements CentralizedRMI {

  private static final long serialVersionUID = 1L;
  private WordFrequency dictionary;
  // private boolean dictionary;

  public CentralizedServer() throws RemoteException {
    super();
    this.dictionary = new WordFrequency();
  }

  public void MergeDictionary(WordFrequency other) {
    dictionary.mergeWord(other);
  }

  public WordFrequency GetDictionary() {
    return this.dictionary;
  }

  public boolean CanUpdateData(long id) {
    System.out.println(id);
    return true;
  };
}
