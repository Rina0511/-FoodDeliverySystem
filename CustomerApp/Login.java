package Interface;

import java.awt.*;
import javax.swing.*;
import Interface.BackendConnector;

public class Login {

    private JFrame frame;
    private JTextField textField;
    private JPasswordField textField_1;

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

    public Login() {
        initialize();
    }

    public void showWindow() {
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Cake Shop Login");
        frame.setBounds(100, 100, 800, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 100, 0)); // Dark Green background
        contentPane.setLayout(null);
        frame.setContentPane(contentPane);

        JLabel lblTitle = new JLabel("Oriental Coffee");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Showcard Gothic", Font.BOLD, 70));
        lblTitle.setBounds(74, 95, 631, 105);
        contentPane.add(lblTitle);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Cambria Math", Font.PLAIN, 24));
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setBounds(206, 210, 177, 55);
        contentPane.add(lblUsername);

        textField = new JTextField();
        textField.setBounds(332, 222, 211, 33);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Cambria Math", Font.PLAIN, 24));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(206, 275, 177, 55);
        contentPane.add(lblPassword);

        textField_1 = new JPasswordField();
        textField_1.setBounds(332, 281, 211, 33);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setFont(new Font("Rockwell", Font.PLAIN, 20));
        btnLogin.setBounds(365, 348, 159, 39);
        contentPane.add(btnLogin);

        JButton btnSignUp = new JButton("SIGN UP");
        btnSignUp.setFont(new Font("Rockwell", Font.PLAIN, 20));
        btnSignUp.setBounds(475, 429, 159, 45);
        contentPane.add(btnSignUp);

        JButton btnExit = new JButton("EXIT");
        btnExit.setFont(new Font("Rockwell", Font.PLAIN, 20));
        btnExit.setBounds(259, 429, 159, 45);
        contentPane.add(btnExit);

        // Exit action
        btnExit.addActionListener(e -> System.exit(0));

        // Login action
        btnLogin.addActionListener(e -> {
            String username = textField.getText().trim();
            String password = new String(textField_1.getPassword());

            String jsonData = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\", \"role\":\"customer\"}";
            String url = "http://localhost/FoodDelivery_Backend/login.php";
            String response = BackendConnector.sendPost(url, jsonData);

            try {
                if (response != null && response.contains("\"status\":\"success\"")) {
                    int customerId = -1;

                    if (response.contains("\"id\":")) {
                        String[] parts = response.split("\"id\":");
                        String idStr = parts[1].split(",")[0].replaceAll("[^0-9]", "");
                        customerId = Integer.parseInt(idStr);
                    }

                    JOptionPane.showMessageDialog(frame, "Login successful!");

                    MenuGUI menu = new MenuGUI(customerId);  // ✅ Pass to Menu
                    menu.showWindow();
                    frame.dispose();

                } else {
                    String message = "Invalid login.";
                    if (response != null && response.contains("\"message\":\"")) {
                        message = response.split("\"message\":\"")[1].split("\"")[0];
                    }
                    JOptionPane.showMessageDialog(frame, "Login failed: " + message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Something went wrong.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Register action
        btnSignUp.addActionListener(e -> {
            Register register = new Register();
            register.showWindow();
            frame.dispose();
        });
    }
}