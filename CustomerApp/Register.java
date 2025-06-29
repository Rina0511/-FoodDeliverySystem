package Interface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Register {

	private JFrame frame;
	private JTextField textField_1, textField_2, textField_3, textField_4, textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register window = new Register();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void showWindow() {
	    frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public Register() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Register");
		frame.setBounds(100, 100, 800, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 253, 208));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		textField_1 = new JTextField();
		textField_1.setBounds(332, 207, 203, 31);
		contentPane.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setBounds(332, 260, 203, 31);
		contentPane.add(textField_2);

		textField_3 = new JTextField();
		textField_3.setBounds(332, 311, 203, 31);
		contentPane.add(textField_3);

		textField_4 = new JTextField();
		textField_4.setBounds(332, 359, 203, 31);
		contentPane.add(textField_4);

		textField_5 = new JTextField();
		textField_5.setBounds(332, 407, 203, 31);
		contentPane.add(textField_5);

		JLabel lblNewLabel = new JLabel("Sign Up");
		lblNewLabel.setFont(new Font("Rockwell", Font.BOLD, 50));
		lblNewLabel.setBounds(332, 33, 197, 109);
		contentPane.add(lblNewLabel);

		JLabel lblUsername = new JLabel("Email:");
		lblUsername.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 18));
		lblUsername.setBounds(269, 202, 74, 38);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Username:");
		lblPassword.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 18));
		lblPassword.setBounds(231, 255, 102, 38);
		contentPane.add(lblPassword);

		JLabel lblConfirmPassword = new JLabel("Password:");
		lblConfirmPassword.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 18));
		lblConfirmPassword.setBounds(231, 306, 102, 38);
		contentPane.add(lblConfirmPassword);

		JLabel lblPhoneNo = new JLabel("Confirm Password:");
		lblPhoneNo.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 18));
		lblPhoneNo.setBounds(161, 354, 161, 38);
		contentPane.add(lblPhoneNo);

		JLabel lblEmail = new JLabel("No.Phone:");
		lblEmail.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 18));
		lblEmail.setBounds(231, 402, 102, 38);
		contentPane.add(lblEmail);

		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setFont(new Font("Rockwell", Font.BOLD, 16));
		btnConfirm.setBounds(295, 543, 113, 43);
		contentPane.add(btnConfirm);

		JButton btnReturn = new JButton("Return");
		btnReturn.setFont(new Font("Rockwell", Font.BOLD, 16));
		btnReturn.setBounds(466, 543, 113, 43);
		contentPane.add(btnReturn);

		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnReturn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        frame.dispose();          // Close Register window
		        Login login = new Login(); // Create Login window instance
		        login.showWindow();        // Show Login window
		    }
		});

	
			}
	}

