package GUI;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import system.MyPoint;
import system.RMIData;

import com.rmiinterface.Constants;
import com.rmiinterface.TestRemote;

/**Klasa odpowiedzialna za panel roboczy*/
public class MyFrame extends JFrame {

	/**
	 Panel roboczy.
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JCheckBox chckbxOkregi;
	private JCheckBox chckbxWypenijKomorkiKolorami;
	private JTextField tfLiczbaWatkow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame frame = new MyFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyFrame() {
		setTitle("Diagram Voroni - SRiR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 927, 665);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		chckbxOkregi = new JCheckBox("Rysuj okr\u0119gi");
		chckbxOkregi.setSelected(true);

		final MyPanel panel = new MyPanel();
		panel.setBounds(10, 122, panel.X, panel.Y);
		contentPane.add(panel);

		chckbxOkregi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.setIfDrawCircles(chckbxOkregi.isSelected());
			}
		});
		chckbxOkregi.setBounds(10, 10, 155, 23);
		contentPane.add(chckbxOkregi);

		chckbxWypenijKomorkiKolorami = new JCheckBox(
				"Wype\u0142nij komórki kolorami");
		chckbxWypenijKomorkiKolorami.setSelected(false);
		chckbxWypenijKomorkiKolorami.setEnabled(true);
		chckbxWypenijKomorkiKolorami.setBounds(10, 40, 204, 23);
		contentPane.add(chckbxWypenijKomorkiKolorami);

		JButton btnCzy = new JButton("CZY\u015A\u0106");
		btnCzy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.clear();
			}
		});
		btnCzy.setBounds(220, 40, 119, 23);
		contentPane.add(btnCzy);

		final JCheckBox chckbxKolorowieGranice = new JCheckBox(
				"Kolorowie granice");
		chckbxKolorowieGranice.setBounds(221, 10, 155, 23);
		contentPane.add(chckbxKolorowieGranice);

		chckbxKolorowieGranice.setSelected(false);
		chckbxKolorowieGranice.setEnabled(true);

		final JCheckBox chckbxZdjcie = new JCheckBox("Zapisz zdj\u0119cie");
		chckbxZdjcie.setSelected(panel.isIfSavePhoto());
		chckbxZdjcie.setEnabled(true);
		chckbxZdjcie.setBounds(411, 10, 155, 23);
		chckbxZdjcie.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});

		contentPane.add(chckbxZdjcie);

		JButton btnPobierzPunktyZ = new JButton("Pobierz losowe punkty z serwera");
		btnPobierzPunktyZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// panel.addPoint(new MyPoint(3701, 354));
				List<Point> points;

				Registry registry = null;
				try {
					registry = LocateRegistry.getRegistry(RMIData.address,
							Constants.RMI_PORT);
					TestRemote remote = (TestRemote) registry
							.lookup(Constants.RMI_ID);

					points = remote.getRandomPoints();

					for (Point point : points) {
						panel.addPoint(new MyPoint(point.x, point.y));
					}
				} catch (RemoteException | NotBoundException e) {
					// custom title, warning icon
					JOptionPane.showMessageDialog(null,
							"B³¹d po³¹czenia z serwerem RMI.",
							"B³¹d po³¹czenia.", JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		btnPobierzPunktyZ.setBounds(10, 88, 246, 23);
		contentPane.add(btnPobierzPunktyZ);

		tfLiczbaWatkow = new JTextField();
		tfLiczbaWatkow.setText("3");
		tfLiczbaWatkow.setBounds(389, 89, 86, 20);
		contentPane.add(tfLiczbaWatkow);
		tfLiczbaWatkow.setColumns(10);

		JLabel lblLiczbaWtkw = new JLabel("Liczba w\u0105tk\u00F3w:");
		lblLiczbaWtkw.setBounds(293, 92, 109, 14);
		contentPane.add(lblLiczbaWtkw);

		JButton btnLicz = new JButton("LICZ");
		btnLicz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					panel.setThreadNumber(Integer.parseInt(tfLiczbaWatkow
							.getText()));
				} catch (NumberFormatException e) {
					panel.setThreadNumber(1);
					tfLiczbaWatkow.setText("1");
				}
				panel.calc();
			}
		});
		btnLicz.setBounds(674, 10, 137, 101);
		contentPane.add(btnLicz);
		
		JButton btnPobierzLokalizacjMcdonalds = new JButton("Pobierz lokalizacj\u0119 McDonalds");
		btnPobierzLokalizacjMcdonalds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List<Point> points;

				Registry registry = null;
				try {
					registry = LocateRegistry.getRegistry(RMIData.address,
							Constants.RMI_PORT);//pobieranie lokalizacji 
					TestRemote remote = (TestRemote) registry
							.lookup(Constants.RMI_ID);

					points = remote.getRealPoints();

					for (Point point : points) {
						panel.addPoint(new MyPoint(point.x, point.y));
					}
				} catch (RemoteException | NotBoundException e2) {
					// custom title, warning icon
					JOptionPane.showMessageDialog(null,
							"B³¹d po³¹czenia z serwerem RMI.",
							"B³¹d po³¹czenia.", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnPobierzLokalizacjMcdonalds.setBounds(372, 40, 246, 23);
		contentPane.add(btnPobierzLokalizacjMcdonalds);

		chckbxKolorowieGranice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.setIfColorLines(chckbxKolorowieGranice.isSelected());
			}
		});

		chckbxWypenijKomorkiKolorami.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.setIfFillWithColors(chckbxWypenijKomorkiKolorami
						.isSelected());
			}
		});

		panel.clear();
	}
}
