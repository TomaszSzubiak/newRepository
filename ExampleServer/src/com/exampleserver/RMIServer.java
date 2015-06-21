package com.exampleserver;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.rmiinterface.Constants;

/**Klasa Serwera, implementuj¹ca remote, pobiera info RMI_PORT, RMI_ID
 * */
public class RMIServer {
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		RemoteImpl impl = new RemoteImpl();
		Registry registry = LocateRegistry.createRegistry(Constants.RMI_PORT);
		registry.bind(Constants.RMI_ID, impl);
		
		System.out.println("starded");
	}

}
