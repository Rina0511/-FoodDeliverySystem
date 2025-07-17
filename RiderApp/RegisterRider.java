package rider;

import javax.swing.*;
import org.json.JSONObject;
import java.awt.*;

public class RegisterRider {

    public RegisterRider() {
        JFrame frame = new JFrame("Rider Registration");
        frame.setSize(450, 500); // Increased height for extra field
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(0, 100, 0));

        JLabel title = new JLabel("Register as Rider");
        title.setFont(new Font("Showcard Gothic", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(90, 20, 300, 30);
        frame.add(title);

        // Labels and fields
        String[] labels = {"Name", "Email", "Password", "Confirm Password", "Phone", "Address"};
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i] + ":");
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Rockwell", Font.PLAIN, 16));
            label.setBounds(50, 70 + i * 45, 150, 25);
            frame.add(label);

            if (labels[i].toLowerCase().contains("password")) {
                fields[i] = new JPasswordField();
            } else {
                fields[i] = new JTextField();
            }
            fields[i].setBounds(210, 70 + i * 45, 170, 25);
            frame.add(fields[i]);
        }

        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Rockwell", Font.BOLD, 16));
        btnRegister.setBackground(new Color(220, 184, 144));
        btnRegister.setBounds(150, 70 + labels.length * 45 + 10, 130, 35); // Position below last field
        frame.add(btnRegister);

        btnRegister.addActionListener(e -> {
            String username = fields[0].getText();
            String email = fields[1].getText();
            String password = new String(((JPasswordField) fields[2]).getPassword());
            String confirm = new String(((JPasswordField) fields[3]).getPassword());
            String phone = fields[4].getText();
            String address = fields[5].getText(); // ✅ New field

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.");
                return;
            }

            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("email", email);
            json.put("password", password);
            json.put("confirmPassword", confirm);
            json.put("phone", phone);
            json.put("address", address); // ✅ Added to JSON
            json.put("role", "rider");

            String response = BackendConnectorRider.sendPost("http://localhost/FoodDelivery_Backend/register.php", json.toString());

            if (response != null && response.contains("\"status\":\"success\"")) {
                JOptionPane.showMessageDialog(frame, "Registered successfully.");
                frame.dispose();
                new LoginRider();
            } else {
                JOptionPane.showMessageDialog(frame, "Registration failed: " + response);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
