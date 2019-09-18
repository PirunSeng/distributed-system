import java.rmi.*;

public interface RemoteRMI extends Remote {
	public WordFrequency wordFrequency(String []arrStr, int start, int end) throws RemoteException;
}
