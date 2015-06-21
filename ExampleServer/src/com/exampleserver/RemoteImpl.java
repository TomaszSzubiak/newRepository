package com.exampleserver;

import java.awt.Point;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.rmiinterface.TestRemote;

/**Klasa odpowiadaj�ca za us�ug� serwerow� RMI, rozszerzaj�ca UnicastRemoteObject*/
public class RemoteImpl extends UnicastRemoteObject implements TestRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**Hashmapa przechowuj�ca klient�w*/
	HashMap<String, String> users;
	/**Kolejka przechowuj�ca informacje o punktach*/
	List<Point> points;

	protected RemoteImpl() throws RemoteException {
		super();
		users = new HashMap<>();
		points = new LinkedList<Point>();
		users.put("admin", "1234");
	}

	/**Funkcja sprawdzaj�ca poprawno�� logowania - walidacja*/
	@Override
	public boolean isLoginValid(String username, String pass)
			throws RemoteException {
		System.out.println("logowanie jako: " + username + " " + pass);
		if (pass.equals(users.get(username)))
			return true;
		return false;
	}

	/**Funkcja generuj�ca losowe punkty na mapie
	 * @return lista punkt�w
	 * */
	@Override
	public List<Point> getRandomPoints() throws RemoteException {
		List<Point> list = new LinkedList<Point>();
		Random rand = new Random();

		//for (int i = 0; i < 100; i++) {
		//	list.add(new Point(rand.nextInt(7500), rand.nextInt(4900)));
		//}
		
		for (int i = 0; i < 10; i++) {
			list.add(new Point(rand.nextInt(1000), rand.nextInt(1000)));/** losowanie punktow */
		}

		System.out.println("punkty wygenerowane");

		return list;
	}
	
	@Override
	public List<Point> getRealPoints() throws RemoteException {
		return points;
	}

	/**Funkcja wyswietlaj�ca informacje o dodanym punkcie przez klienta
	 * */
	@Override
	public void addPoints(Point point) throws RemoteException {
		System.out.println("adding: "+point.x+" "+point.y);
		points.add(point);
	}

}
