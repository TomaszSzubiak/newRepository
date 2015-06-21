package system;

import java.awt.Point;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**Klasa przechowuj¹ca informacje o punktach*/
public class MyPoint extends Point {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**Kolejka zawieraj¹ca okrêgi*/
	List<Circle> circles;

	public MyPoint(int x, int y) {
		super(x, y);
		circles = new CopyOnWriteArrayList<Circle>();
	}

	/**Funkcja dodaj¹ca okr¹g*/
	public void addCircle(Circle c) {
		if (!circles.contains(c))
			circles.add(c);

	}

	/**Funkcja sortuj¹ca okrêgi*/
	public void sortCircles() {

		for (int i = 0; i < circles.size(); i++) {
			for (int j = 1; j < circles.size(); j++) {
				if (MathOperations.getAngle(this, circles.get(j).getCenterPoint()) < MathOperations.getAngle(this,
						circles.get(j - 1).getCenterPoint()))
					Collections.swap(circles, j, j - 1);
			}
		}

	}

	/**Funkcja zwracaj¹ca listê okrêgów*/
	public List<Circle> getCircles() {
		return circles;
	}

	/**Funkcja czyszcz¹ca listê okrêgów*/
	public void clearCircles() {
		circles.clear();
	}
}
