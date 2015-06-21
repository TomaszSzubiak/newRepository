package system;

import java.awt.Point;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**Klasa przechowuj�ca informacje o punktach*/
public class MyPoint extends Point {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**Kolejka zawieraj�ca okr�gi*/
	List<Circle> circles;

	public MyPoint(int x, int y) {
		super(x, y);
		circles = new CopyOnWriteArrayList<Circle>();
	}

	/**Funkcja dodaj�ca okr�g*/
	public void addCircle(Circle c) {
		if (!circles.contains(c))
			circles.add(c);

	}

	/**Funkcja sortuj�ca okr�gi*/
	public void sortCircles() {

		for (int i = 0; i < circles.size(); i++) {
			for (int j = 1; j < circles.size(); j++) {
				if (MathOperations.getAngle(this, circles.get(j).getCenterPoint()) < MathOperations.getAngle(this,
						circles.get(j - 1).getCenterPoint()))
					Collections.swap(circles, j, j - 1);
			}
		}

	}

	/**Funkcja zwracaj�ca list� okr�g�w*/
	public List<Circle> getCircles() {
		return circles;
	}

	/**Funkcja czyszcz�ca list� okr�g�w*/
	public void clearCircles() {
		circles.clear();
	}
}
