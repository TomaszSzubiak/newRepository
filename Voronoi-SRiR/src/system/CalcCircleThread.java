package system;

import java.util.ArrayList;

/**Klasa odpowiedzialna za obliczanie okr�g�w przez okre�lon� ilo�� w�tk�w*/
public class CalcCircleThread extends Thread {

	/**Kolejka zawieraj�ca punkty*/
	public ArrayList<MyPoint> points;
	/**Delta*/
	public int delta;
	/**ID w�tku*/
	public int ID;

	@Override
	public void run() {
		super.run();
		System.out.println("run");

		for (int i = ID * delta; i < (ID + 1) * delta + 1; i++) {
			for (int j = i + 1; j < points.size(); j++)
				for (int k = j + 1; k < points.size(); k++) {
					MyPoint p1 = (MyPoint) points.get(i).clone();
					MyPoint p2 = (MyPoint) points.get(j).clone();
					MyPoint p3 = (MyPoint) points.get(k).clone();
					
					

					Circle circle = new Circle(p1, p2, p3);
					
					p1.addCircle(circle);
			        p2.addCircle(circle);
			        p3.addCircle(circle);

					// addCircle(circle);

					for (int ii = 0; ii < points.size(); ii++) {
						if (ii == i || ii == j || ii == k)
							continue;
						MyPoint poi = (MyPoint) points.get(ii).clone();
						if (MathOperations.inCircle(poi, circle)) {
							for (MyPoint po : circle.points) {
								po.getCircles().remove(circle);
							}
							// circles.remove(circle);
						}
					}

				}
		}
	}

}
