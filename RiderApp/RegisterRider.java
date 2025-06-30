package rider;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterRider {

    public RegisterRider() {
        JFrame frame = new JFrame("Rider Registration");
        frame.setSize(450, 430);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(0, 100, 0));

        JLabel title = new JLabel("Register as Rider");
        title.setFont(new Font("Showcard Gothic", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(90, 20, 300, 30);
        frame.add(title);

        // Labels and text fields
        String[] labels = {"Name:", "Email:", "Password:", "Phone:"};
        JLabel[] lbls = new JLabel[labels.length];
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            lbls[i] = new JLabel(labels[i]);
            lbls[i].setForeground(Color.WHITE);
            lbls[i].setFont(new Font("Rockwell", Font.PLAIN, 16));
            lbls[i].setBounds(50, 80 + i * 50, 100, 25);
            frame.add(lbls[i]);

            if (labels[i].equals("Password:")) {
                fields[i] = new JPasswordField();
            } else {
                fields[i] = new JTextField();
            }
            fields[i].setBounds(160, 80 + i * 50, 200, 25);
            frame.add(fields[i]);
        }

        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Rockwell", Font.BOLD, 16));
        btnRegister.setBackground(new Color(220, 184, 144));
        btnRegister.setBounds(150, 300, 130, 35);
        frame.add(btnRegister);

        btnRegister.addActionListener(e -> {
            String name = fields[0].getText();
            String email = fields[1].getText();
            String password = new String(((JPasswordField) fields[2]).getPassword());
            String phone = fields[3].getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.");
                return;
            }

            try {
                URL url = new URL("http://localhost/backend-api/register.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                String data = "username=" + URLEncoder.encode(name, "UTF-8") +
                              "&email=" + URLEncoder.encode(email, "UTF-8") +
                              "&password=" + URLEncoder.encode(password, "UTF-8") +
                              "&phone=" + URLEncoder.encode(phone, "UTF-8") +
                              "&role=rider";

                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    JOptionPane.showMessageDialog(frame, "Rider registered successfully!");
                    frame.dispose();
                    new LoginRider(); // Go back to login screen
                } else if (responseCode == 409) {
                    JOptionPane.showMessageDialog(frame, "Username already exists.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Registration failed. Try again.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterRider();
    }
}
