package system;

import java.awt.Point;

/**Klasa odpowiedzialna za przechowywanie infrmacji o odcinku, oraz przechowuj¹ca operacje matematyczne*/
public class Line {

	/**Punkt A*/
	public Point A;
	/**Punkt B*/
	public Point B;

	public Line(Point A, Point B) {
		this.A = A;
		this.B = B;
	}

	/**Funkcja obliczaj¹ca i zwracaj¹ca prostopad³y odcinek
	 * @return prostopadla linia
	 */
	public Line getPerpendicular(Line l, int x, int y) {
		double xdif = l.B.x - l.A.x;
		double ydif = l.B.y - l.A.y;
		int a1 = (int) (x - ydif / 2);
		int b1 = (int) (y + xdif / 2);
		int a2 = (int) (x + ydif / 2);
		int b2 = (int) (y - xdif / 2);
		
		return new Line(new Point(a1, b1), new Point(a2, b2));
	}
}
