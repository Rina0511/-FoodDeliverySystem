package rider;

import javax.swing.*;
import org.json.JSONObject;
import java.awt.*;

public class LoginRider {

    public static void main(String[] args) {
        new LoginRider();
    }

    public LoginRider() {
        JFrame frame = new JFrame("Rider Login");
        frame.setSize(450, 330);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0, 100, 0));
        frame.setLayout(null);

        JLabel title = new JLabel("Oriental Coffee - Rider");
        title.setFont(new Font("Showcard Gothic", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBounds(40, 20, 400, 40);
        frame.add(title);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Rockwell", Font.PLAIN, 18));
        lblUser.setBounds(50, 80, 120, 25);
        frame.add(lblUser);

        JTextField txtUser = new JTextField();
        txtUser.setBounds(180, 80, 180, 25);
        frame.add(txtUser);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setForeground(Color.WHITE);
        lblPass.setFont(new Font("Rockwell", Font.PLAIN, 18));
        lblPass.setBounds(50, 120, 120, 25);
        frame.add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(180, 120, 180, 25);
        frame.add(txtPass);

        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Rockwell", Font.BOLD, 16));
        btnLogin.setBackground(new Color(220, 184, 144));
        btnLogin.setBounds(150, 170, 130, 35);
        frame.add(btnLogin);

        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Rockwell", Font.BOLD, 16));
        btnRegister.setBackground(new Color(200, 180, 140));
        btnRegister.setBounds(150, 220, 130, 30);
        frame.add(btnRegister);

        btnRegister.addActionListener(e -> new RegisterRider());

        btnLogin.addActionListener(e -> {
            String username = txtUser.getText();
            String password = new String(txtPass.getPassword());

            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", password);
            json.put("role", "rider");

            String response = BackendConnectorRider.sendPost("http://localhost/FoodDelivery_Backend/login.php", json.toString());

            if (response != null && response.contains("\"status\":\"success\"")) {
                JOptionPane.showMessageDialog(frame, "Login successful.");
                JSONObject jsonResponse = new JSONObject(response);
                int riderId = jsonResponse.getInt("id");

                frame.dispose(); 
                new ViewTasks(riderId); 
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials or not registered.");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
