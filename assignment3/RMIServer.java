import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.rmi.*;

public class RMIServer {
  public static void main(String args[]) {
    try {
      LocateRegistry.createRegistry(1099);
      CentralizedServer center = new CentralizedServer();
      Naming.rebind("CentralizedServer", center);
      System.out.println("CentralizedServer is created!!!");


      SlaveServer ds = new SlaveServer();
      Naming.rebind("SlaveServer", ds);
      System.out.println("SlaveServer is created!!!");
    } catch(Exception e) {
      System.out.println(e);
    }
  }
}
