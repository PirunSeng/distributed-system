import java.rmi.*;
import java.util.Map;

public interface RemoteDate extends Remote {
	public Map<String, Integer> wordFrequency(String str) throws RemoteException;
}