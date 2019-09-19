import java.rmi.*;

public interface RemoteRMI extends Remote {
	public void CountWordFrequency(String []arrStr, int start, int end) throws RemoteException;
}
