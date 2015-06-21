package GUI;

import java.awt.EventQueue;
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

import system.RMIData;

import com.rmiinterface.Constants;
import com.rmiinterface.TestRemote;

/**Klasa odpowiedzialna za okienko logowania do systemu*/
public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfLogin;
	private JTextField tfPassword;
	private JTextField tfAddress;

	/**
	 * Uruchomienie okienka odpowiedzialnego za logowanie do systemu.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 201, 294);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(24, 11, 46, 14);
		contentPane.add(lblLogin);
		
		tfLogin = new JTextField();
		tfLogin.setBounds(24, 36, 140, 20);
		contentPane.add(tfLogin);
		tfLogin.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(24, 67, 91, 14);
		contentPane.add(lblPassword);
		
		tfPassword = new JTextField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(24, 94, 140, 20);
		contentPane.add(tfPassword);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				RMIData.address = tfAddress.getText(); 
				
				Registry registry;
				try {
					registry = LocateRegistry.getRegistry(RMIData.address, Constants.RMI_PORT);
					TestRemote remote = (TestRemote) registry.lookup(Constants.RMI_ID);
					
					if(remote.isLoginValid(tfLogin.getText(), tfPassword.getText())){
						new MyFrame().setVisible(true);
					}else{
						JOptionPane.showMessageDialog(LoginFrame.this,
							    "Podano z³y login lub has³o.",
							    "B³¹d logowania.",
							    JOptionPane.INFORMATION_MESSAGE);
					}
					
				} catch (RemoteException | NotBoundException e) {
					JOptionPane.showMessageDialog(LoginFrame.this,
						    "B³¹d po³¹czenia z serwerem RMI.",
						    "B³¹d po³¹czenia",
						    JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		btnLogin.setBounds(42, 216, 89, 23);
		contentPane.add(btnLogin);
		
		JLabel lblAdresSerwera = new JLabel("Adres serwera:");
		lblAdresSerwera.setBounds(24, 135, 91, 14);
		contentPane.add(lblAdresSerwera);
		
		tfAddress = new JTextField();
		tfAddress.setText("localhost");
		tfAddress.setColumns(10);
		tfAddress.setBounds(24, 160, 140, 20);
		contentPane.add(tfAddress);
	}
}
