package Interface;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Login window = new Login();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}
	
	public void showWindow() {
	    frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unused")
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Cake Shop Login");
		frame.setBounds(100, 100, 800, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 100, 0)); // Dark Green background
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);

		// Cake Shop title
		JLabel lblTitle = new JLabel("Oriental Coffee");
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setFont(new Font("Showcard Gothic", Font.BOLD, 70));
		lblTitle.setBounds(74, 95, 631, 105);
		contentPane.add(lblTitle);

		// Username label and field
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Cambria Math", Font.PLAIN, 24));
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setBounds(206, 210, 177, 55);
		contentPane.add(lblUsername);

		textField = new JTextField();
		textField.setBounds(332, 222, 211, 33);
		contentPane.add(textField);
		textField.setColumns(10);

		// Password label and field
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Cambria Math", Font.PLAIN, 24));
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setBounds(206, 275, 177, 55);
		contentPane.add(lblPassword);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(332, 281, 211, 33);
		contentPane.add(textField_1);

		// Login button
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setFont(new Font("Rockwell", Font.PLAIN, 20));
		btnLogin.setBounds(365, 348, 159, 39);
		contentPane.add(btnLogin);

		btnLogin.addActionListener(e -> {
			String username = textField.getText();
			String password = textField_1.getText();

			// You can replace this with controller call
			System.out.println("Login clicked. Username: " + username + ", Password: " + password);
			// Example:
			// Login_Controller loginController = new Login_Controller();
			// loginController.handleLogin(username, password);
		});

		// Sign Up button
		JButton btnSignUp = new JButton("SIGN UP");
		btnSignUp.setFont(new Font("Rockwell", Font.PLAIN, 20));
		btnSignUp.setBounds(475, 429, 159, 45);
		contentPane.add(btnSignUp);

		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		// Exit button
		JButton btnExit = new JButton("EXIT");
		btnExit.setFont(new Font("Rockwell", Font.PLAIN, 20));
		btnExit.setBounds(259, 429, 159, 45);
		contentPane.add(btnExit);

		btnExit.addActionListener(e -> System.exit(0));
		
		// Inside your btnLogin ActionListener:
		btnLogin.addActionListener(e -> {
		    String username = textField.getText();
		    String password = textField_1.getText();

		    // For now, just print. Later you can add actual validation here.
		    System.out.println("Login clicked. Username: " + username + ", Password: " + password);

		    
		    // Close login window
		    frame.dispose();
		});

		btnSignUp.addActionListener(e -> {
		    Register register = new Register();
		    register.showWindow();
		    frame.dispose();
		});

		btnLogin.addActionListener(e -> {
		    String username = textField.getText();
		    String password = textField_1.getText();

		    MenuGUI menu = new MenuGUI();
		    menu.showWindow();
		    frame.dispose();
		});

		btnSignUp.addActionListener(e -> {
		    Register register = new Register();
		    register.showWindow();
		    frame.dispose();
		});
		


	}
}
