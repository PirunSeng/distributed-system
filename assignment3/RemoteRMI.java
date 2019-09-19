import java.rmi.*;

public interface RemoteRMI extends Remote {
	public WordFrequency CountWordFrequency(String []arrStr, int start, int end) throws RemoteException;
}
