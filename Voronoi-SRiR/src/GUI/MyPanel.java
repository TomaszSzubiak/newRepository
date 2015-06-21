package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import system.CalcCircleThread;
import system.Circle;
import system.Line;
import system.MathOperations;
import system.MyPoint;

public class MyPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**szeroko�� pola*/
	final int X = 8000;
	/**wysoko�� pola*/
	final int Y = 5000;
	/**liczba w�tk�w*/
	public static int threadNumber = 1;
	/**Kolekcja zawieraj�ca wszystkie punkty*/
	private ArrayList<MyPoint> points;
	/**Kolekcja zawieraj�ca wszystkie kraw�dzie*/
	private ArrayList<MyPoint> edgePoints;
	/**Kolekcja zawieraj�ca wszystkie okr�gi*/
	private ArrayList<Circle> circles;
	/**Kolekcja zawieraj�ca wszystkie kraw�dzie linii*/
	private ArrayList<Line> edgeLines;
	/**narysowane okr�gi*/
	private boolean ifDrawCircles = true;
	/**wypelnienie kolorem*/
	private boolean ifFillWithColors = false;
	/**kolorowe kraw�dzie*/
	private boolean ifColorLines = false;
	/**zapisane zdj�cie*/
	private boolean ifSavePhoto = false;
	/** Kolekcja wszystkich kolor�w reprezentuj�cych punkty. */
	public static ArrayList<Color> colors;
	/** Liczba wszytkich kolor�w reprezentuj�cych punkty. */
	public static int colorCounter = 0;

	public MyPanel() {
		setBorder(BorderFactory.createLineBorder(Color.PINK));
		edgePoints = new ArrayList<MyPoint>();
		points = new ArrayList<MyPoint>();
		circles = new ArrayList<Circle>();
		edgeLines = new ArrayList<Line>();
		colors = new ArrayList<Color>();

		// dodanie punktu przez klikniecie:
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

				addPoint(new MyPoint(e.getX(), e.getY()));
				
			}
		});
	}
	
	/**Funkcja wyszukuj�ca punkty na kraw�dzi*/
	protected void findEdgePoints() {
		edgePoints.clear();
		edgeLines.clear();
		for (MyPoint point : points) {
			if (point.getCircles().size() <= 3)
				edgePoints.add(point);

		}
	}

	int delta;
	int ID;

	/**Funkcja odpowiedzialna za wyszukanie okr�g�w, rozdzielaj�ca zadanie pomi�dzy odpowiedni� ilo�� w�tk�w*/
	protected void calcCircles() {

		long start_time = System.nanoTime();

		// wyczyszczenie kolekcji okr�g�w z punkt�w:
		for (MyPoint point : points) {
			point.clearCircles();
		}

		circles.clear();

		// podzia� na w�tki:
		delta = points.size() / threadNumber;
		System.out.println("Watki podzielone");
		CalcCircleThread[] threads = new CalcCircleThread[threadNumber];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new CalcCircleThread();
			threads[i].delta = delta;
			threads[i].ID = i;
			threads[i].points = new ArrayList<>(points);
		}

		// start i ��czenie w�tk�w:
		for (ID = 0; ID < threadNumber; ID++) {
			threads[ID].start();
		}
		for (ID = 0; ID < threadNumber; ID++) {
			try {
				threads[ID].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		long end_time = System.nanoTime();
		double difference = (end_time - start_time) / 1e6;

		System.out.println("Czas wyszukiwania okregow: " + difference);
	}

	public Dimension getPreferredSize() {
		return new Dimension(250, 200);
	}

	/**Tworzenie i rysowanie obszaru*/
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, X, Y);

		if (ifFillWithColors)
			drawField(g);

		// wyczyszczenie z NAN:
		for (MyPoint point : points) {
			for (Circle circle : point.getCircles()) {
				if (Double.isNaN(circle.getRadius())) {
					point.getCircles().remove(circle);
					// System.out.println("NaN znaleziony");
				}
			}
		}

		// rysowanie okr�g�w:
		if (ifDrawCircles)
			for (MyPoint point : points) {
				for (Circle circle : point.getCircles()) {
					drawCircle(circle, g);
				}
			}
			

		// rysowanie punkt�w nie kraw�dziowych:
		for (MyPoint point : points) {
			drawPoint(point, g);
		}

		// rysowanie linii dla niegranicznych:
		g.setColor(Color.WHITE);
		for (int p = 0; p < points.size() - 1; p++) {
			// // je�eli jest punktem kraw�dziowym to pomi�:
			// if (edgePoints.contains(points.get(p)))
			// continue;
			MyPoint point = points.get(p);
			if (ifColorLines)
				g.setColor(colors.get(p));
			point.sortCircles();
			for (int i = 0; i < point.getCircles().size() - 1; i++) {
				g.drawLine(point.getCircles().get(i).getCenterPoint().x, point
						.getCircles().get(i).getCenterPoint().y, point
						.getCircles().get(i + 1).getCenterPoint().x, point
						.getCircles().get(i + 1).getCenterPoint().y);
			}

			if (point.getCircles().size() >= 2)
				g.drawLine(point.getCircles().get(0).getCenterPoint().x, point
						.getCircles().get(0).getCenterPoint().y, point
						.getCircles().get(point.getCircles().size() - 1)
						.getCenterPoint().x,
						point.getCircles().get(point.getCircles().size() - 1)
								.getCenterPoint().y);

		}

		// rysowanie linii dla granicznych:
		for (MyPoint point1 : edgePoints) {
			for (MyPoint point2 : edgePoints) {
				if (point1 == point2)
					continue;
				for (Circle circle1 : point1.getCircles()) {
					for (Circle circle2 : point2.getCircles()) {
						if (circle1 == circle2)
							;
						// edgeLines.add(new Line(circle1.getCenterPoint(),
						// MathOperations.middlePoint(point1, point2)));
					}
				}
			}

		}

		for (Line line : edgeLines) {
			g.setColor(Color.WHITE);
			g.drawLine(line.A.x, line.A.y, line.B.x, line.B.y);
			// System.out.println("rysowanie linii granicznej");
		}

	}

	/**Funkcja odpowiedzialna za narysowanie ca�ego obszaru*/
	private void drawField(Graphics g) {
		if (points.size() == 0)
			return;
		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {
				int winner = 0;
				double winnerDistance = 999999;
				MyPoint badanypunkt = new MyPoint(x, y);

				for (int p = 0; p < points.size(); p++) {

					if (MathOperations.abstand(points.get(p), badanypunkt) <= winnerDistance) {
						winner = p;
						winnerDistance = MathOperations.abstand(points.get(p),
								badanypunkt);
					}

				}
				// System.out.print(winner);
				g.setColor(colors.get(winner));
				g.fillRect(x, y, 1, 1);
			}

		}
	}

	/**Funkcja wywo�ywana przez przycisk "Licz" wykonuj�ca po kolei wszystkie operacje wymagane do obliczenia wyniku ko�cowego*/
	void calc() {
		calcCircles();
		findEdgePoints();
		takePicture();
		repaint();
	}

	/**Funkcja rysuj�ca punkt na obszarze*/
	private void drawPoint(MyPoint point, Graphics g) {
		int width = 10;
		g.setColor(Color.BLACK);
		g.fillOval(point.x - width / 2, point.y - width / 2, width, width);
		width = 8;
		g.setColor(Color.WHITE);
		g.fillOval(point.x - width / 2, point.y - width / 2, width, width);
		g.drawString(Integer.toString(point.getCircles().size()), point.x + 4,
				point.y);

	}

	/**Funkcja rysuj�ca okr�gi na obszarze*/
	private void drawCircle(Circle circle, Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.drawOval(circle.getCenterPoint().x - (int) circle.getRadius(),
				circle.getCenterPoint().y - (int) circle.getRadius(),
				(int) circle.getRadius() * 2, (int) circle.getRadius() * 2);

		g.setColor(Color.GREEN);
		g.fillOval(circle.getCenterPoint().x - 2,
				circle.getCenterPoint().y - 2, 4, 4);
	}

	/**Funkcja dodaj�ca pojedynczy punkt do kolejki okr�g�w*/
	public void addPoint(MyPoint p) {
		points.add(p);
		final Random rand = new Random();
		colorCounter++;
		colors.add(new Color(rand.nextInt(255), rand.nextInt(255), rand
				.nextInt(255)));
		repaint();
	}

	/**Funkcja dodaj�ca okr�g do kolejki okr�g�w*/
	public void addCircle(Circle c) {
		circles.add(c);
		repaint();
	}

	/**Funkcja sprawdzaj�ca czy okr�gi sa narysowane*/
	public void setIfDrawCircles(boolean ifDrawCircles) {
		this.ifDrawCircles = ifDrawCircles;
		repaint();
	}

	/**Funkcja sprawdzaj�ca czy obszar jest wype�niony kolorami*/
	public void setIfFillWithColors(boolean ifFillWithColors) {
		this.ifFillWithColors = ifFillWithColors;
		repaint();
	}

	/**Funkcja sprawdzaj�ca czy linie s� wype�nione kolorami*/
	public void setIfColorLines(boolean ifColorLines) {
		this.ifColorLines = ifColorLines;
		repaint();
	}

	/**Funkcja sprawdzaj�ca zapis zdj�cia do JPG*/
	public void setifSavePhoto(boolean ifPhoto) {
		this.ifSavePhoto = ifPhoto;
	}

	/**Funcka sprawdzaj�ca czy zdj�cie jest zapisne*/
	public boolean isIfSavePhoto() {
		return ifSavePhoto;
	}

	/**Funkcja czyszcz�ca*/
	public void clear() {
		edgeLines.clear();
		edgePoints.clear();
		points.clear();
		circles.clear();
		colors.clear();

		addPoint(new MyPoint(-8000, -8000));
		addPoint(new MyPoint(-7000, 2000));
		addPoint(new MyPoint(10000, -2220));
		addPoint(new MyPoint(-7000, Y + 1000));
		addPoint(new MyPoint(3000, Y + 1000));
		addPoint(new MyPoint(X + 1800, -8000));
		addPoint(new MyPoint(X + 1700, Y + 1000));
		addPoint(new MyPoint(1025, Y + 1000));

		repaint();
	}

	/**
	 * Zapisuje JPanel do JPEGa.
	 */
	void takePicture() {
		if (!ifSavePhoto)
			return;
		System.out.println("saving graphic file");
		BufferedImage img = new BufferedImage(X, Y, BufferedImage.TYPE_INT_RGB);
		this.print(img.getGraphics()); // or: panel.printAll(...);
		try {
			File file = new File("panel.jpg");
			System.out.println(file.getAbsolutePath());
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, "Zdj�cie zapisano.", "Sukces!",
				JOptionPane.INFORMATION_MESSAGE);
		System.out.println("saved");
	}

	/**Funkcja pobieraj�ca i ustalaj�ca ilo�� w�tk�w*/
	public void setThreadNumber(int threadNumber) {
		MyPanel.threadNumber = threadNumber;
	}
}
