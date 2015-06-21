package system;

import java.awt.Point;

/**Klasa zawieraj�ca operacje matematycznyw wykorzysstywane w algorytmie*/
public class MathOperations {

	/**Funkcja licz�ca �rodek odcinka*
	 * @return �rodek odcinka
	 */
	public static Point middlePoint(Point p1, Point p2) {
		return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
	}

	/**Funkcja licz�ca d�ugo�� odcinka*
	 * @return d�ugo�� odcinka
	 */
	public static double abstand(Point p1, Point p2) {
		return  Math.sqrt( (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y)
				* (p2.y - p1.y) );
	}

	/**Funkcja sprawdzaj�ca czy punkt znajduje si� w okr�gu*
	 * @return true/false
	 */
	public static boolean inCircle(Point p, Circle c) {
		return (((p.x - c.getCenterPoint().x) * (p.x - c.getCenterPoint().x))
				+ ((p.y - c.getCenterPoint().y) * (p.y - c.getCenterPoint().y))) < (c
				.getRadius() * c.getRadius());
	}
	
	/**Funkcja licz�ca k�t pomi�dzy punktami/odcinkami*
	 * @return k�t
	 */
	public static float getAngle(Point a, Point b) {
	    return (float) Math.toDegrees(Math.atan2(a.x - b.x, a.y - b.y));
	}
}
