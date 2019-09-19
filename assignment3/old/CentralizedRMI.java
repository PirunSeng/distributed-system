import java.rmi.*;

public interface CentralizedRMI extends Remote {
  public void MergeDictionary(WordFrequency other) throws RemoteException;
  public WordFrequency GetDictionary() throws RemoteException;
  public boolean CanUpdateData(long id) throws RemoteException;
}
