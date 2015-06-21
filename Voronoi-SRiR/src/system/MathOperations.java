package system;

import java.awt.Point;

/**Klasa zawieraj¹ca operacje matematycznyw wykorzysstywane w algorytmie*/
public class MathOperations {

	/**Funkcja licz¹ca œrodek odcinka*
	 * @return œrodek odcinka
	 */
	public static Point middlePoint(Point p1, Point p2) {
		return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
	}

	/**Funkcja licz¹ca d³ugoœæ odcinka*
	 * @return d³ugoœæ odcinka
	 */
	public static double abstand(Point p1, Point p2) {
		return  Math.sqrt( (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y)
				* (p2.y - p1.y) );
	}

	/**Funkcja sprawdzaj¹ca czy punkt znajduje siê w okrêgu*
	 * @return true/false
	 */
	public static boolean inCircle(Point p, Circle c) {
		return (((p.x - c.getCenterPoint().x) * (p.x - c.getCenterPoint().x))
				+ ((p.y - c.getCenterPoint().y) * (p.y - c.getCenterPoint().y))) < (c
				.getRadius() * c.getRadius());
	}
	
	/**Funkcja licz¹ca k¹t pomiêdzy punktami/odcinkami*
	 * @return k¹t
	 */
	public static float getAngle(Point a, Point b) {
	    return (float) Math.toDegrees(Math.atan2(a.x - b.x, a.y - b.y));
	}
}
