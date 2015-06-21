package com.mcdonald.client;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.rmiinterface.Constants;
import com.rmiinterface.TestRemote;

/**Klasa zawieraj¹ca podstawowe informacje o kliencie */
public class Client extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfX;
	private JTextField tfY;
	private JTextField tfAdres;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
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
	public Client() {
		setTitle("McDonald Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 339, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblX = new JLabel("X:");
		lblX.setBounds(10, 145, 46, 14);
		contentPane.add(lblX);

		tfX = new JTextField();
		tfX.setBounds(43, 142, 86, 20);
		contentPane.add(tfX);
		tfX.setColumns(10);

		final JLabel labely = new JLabel("Y:");
		labely.setBounds(175, 148, 46, 14);
		contentPane.add(labely);

		tfY = new JTextField();
		tfY.setColumns(10);
		tfY.setBounds(208, 145, 86, 21);
		contentPane.add(tfY);

		JButton btnWylij = new JButton("WY\u015ALIJ");
		btnWylij.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Registry registry;
				try {
					registry = LocateRegistry.getRegistry(tfAdres.getText(),
							Constants.RMI_PORT);
					
					TestRemote test = (TestRemote) registry
							.lookup(Constants.RMI_ID);
					int x;
					int y;
					try {
						x = Integer.parseInt(tfX.getText());
						System.out.println(x);
						y = Integer.parseInt(tfY.getText());
						System.out.println(y);
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(Client.this,
								"Error: niepoprwne liczby.", "Z³e dane",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (x < 0 || x > 8000 || y < 0 || y > 5000) {
						JOptionPane.showMessageDialog(Client.this,
								"Niepoprawne liczby, zakres wynosi 0<x<8000, 0<y<5000", "Z³e dane",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					test.addPoints(new Point(x, y));
				} catch (RemoteException | NotBoundException e) {
					JOptionPane.showMessageDialog(Client.this,
							"RMI server not found!", "Server error",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		btnWylij.setBounds(110, 247, 89, 23);
		contentPane.add(btnWylij);

		JLabel lblPodajWsprzdneLokalizaji = new JLabel(
				"Podaj wsp\u00F3\u0142rz\u0119dne lokalizaji!");
		lblPodajWsprzdneLokalizaji.setBounds(76, 47, 175, 84);
		contentPane.add(lblPodajWsprzdneLokalizaji);
		
		tfAdres = new JTextField();
		tfAdres.setBounds(175, 294, 86, 20);
		contentPane.add(tfAdres);
		tfAdres.setColumns(10);
		
		JLabel lblAdres = new JLabel("Adres:");
		lblAdres.setBounds(110, 297, 46, 14);
		contentPane.add(lblAdres);
	}
}
