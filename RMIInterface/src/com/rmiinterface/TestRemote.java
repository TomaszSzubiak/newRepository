package com.rmiinterface;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**Remote interface
 * */
public interface TestRemote extends Remote {
	
	public boolean isLoginValid(String username, String pass) throws RemoteException;
	void addPoints(Point point) throws RemoteException;
	List<Point> getRandomPoints() throws RemoteException;
	List<Point> getRealPoints() throws RemoteException;
	

}
