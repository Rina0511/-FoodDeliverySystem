package Interface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Interface.BackendConnector;
import javax.swing.*;

public class Register {

	private JFrame frame;
	private JTextField textField_1, textField_2, textField_5, textField_6;
	private JPasswordField textField_3, textField_4;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Register window = new Register();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public void showWindow() {
		frame.setVisible(true);
	}

	public Register() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Register");
		frame.setBounds(100, 100, 800, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 253, 208));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Sign Up");
		lblNewLabel.setFont(new Font("Rockwell", Font.BOLD, 50));
		lblNewLabel.setBounds(332, 33, 197, 109);
		contentPane.add(lblNewLabel);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 18));
		lblEmail.setBounds(269, 202, 74, 38);
		contentPane.add(lblEmail);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 18));
		lblUsername.setBounds(231, 255, 102, 38);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 18));
		lblPassword.setBounds(231, 306, 102, 38);
		contentPane.add(lblPassword);

		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 18));
		lblConfirmPassword.setBounds(161, 354, 161, 38);
		contentPane.add(lblConfirmPassword);

		JLabel lblPhone = new JLabel("No.Phone:");
		lblPhone.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 18));
		lblPhone.setBounds(231, 402, 102, 38);
		contentPane.add(lblPhone);

		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 18));
		lblAddress.setBounds(231, 450, 102, 38);
		contentPane.add(lblAddress);

		textField_1 = new JTextField();  // Email
		textField_1.setBounds(332, 207, 203, 31);
		contentPane.add(textField_1);

		textField_2 = new JTextField();  // Username
		textField_2.setBounds(332, 260, 203, 31);
		contentPane.add(textField_2);

		textField_3 = new JPasswordField();  // Password
		textField_3.setBounds(332, 311, 203, 31);
		contentPane.add(textField_3);

		textField_4 = new JPasswordField();  // Confirm Password
		textField_4.setBounds(332, 359, 203, 31);
		contentPane.add(textField_4);

		textField_5 = new JTextField();  // Phone
		textField_5.setBounds(332, 407, 203, 31);
		contentPane.add(textField_5);

		textField_6 = new JTextField();  // Address
		textField_6.setBounds(332, 455, 203, 31);
		contentPane.add(textField_6);

		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setFont(new Font("Rockwell", Font.BOLD, 16));
		btnConfirm.setBounds(295, 600, 113, 43);
		contentPane.add(btnConfirm);

		JButton btnReturn = new JButton("Return");
		btnReturn.setFont(new Font("Rockwell", Font.BOLD, 16));
		btnReturn.setBounds(466, 600, 113, 43);
		contentPane.add(btnReturn);

		btnReturn.addActionListener(e -> {
			frame.dispose();
			Login login = new Login();
			login.showWindow();
		});

		btnConfirm.addActionListener(e -> {
			String email = textField_1.getText();
			String username = textField_2.getText();
			String password = new String(textField_3.getPassword());
			String confirmPassword = new String(textField_4.getPassword());
			String phone = textField_5.getText();
			String address = textField_6.getText();

			String role = "customer";

			String jsonData = "{"
					+ "\"email\":\"" + email + "\","
					+ "\"username\":\"" + username + "\","
					+ "\"password\":\"" + password + "\","
					+ "\"confirmPassword\":\"" + confirmPassword + "\","
					+ "\"phone\":\"" + phone + "\","
					+ "\"address\":\"" + address + "\","
					+ "\"role\":\"" + role + "\""
					+ "}";

			String url = "http://localhost/FoodDelivery_Backend/register.php";
			String response = BackendConnector.sendPost(url, jsonData);

			try {
				if (response.contains("\"status\":\"success\"")) {
					String message = response.split("\"message\":\"")[1].split("\"")[0];
					JOptionPane.showMessageDialog(frame, "Registration successful!\n" + message, "Success", JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
					Login login = new Login();
					login.showWindow();
				} else {
					String message = "Registration failed.";
					if (response.contains("\"message\":\"")) {
						message = response.split("\"message\":\"")[1].split("\"")[0];
					}
					JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Something went wrong.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}