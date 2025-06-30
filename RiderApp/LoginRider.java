package rider;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter username and password.");
            } else {
                try {
                    URL url = new URL("http://localhost/backend-api/login_rider.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);

                    String postData = "username=" + URLEncoder.encode(username, "UTF-8") +
                                      "&password=" + URLEncoder.encode(password, "UTF-8");

                    OutputStream os = conn.getOutputStream();
                    os.write(postData.getBytes());
                    os.flush();
                    os.close();

                    int responseCode = conn.getResponseCode();

                    if (responseCode == 200) {
                        new ViewTasks(); // Proceed to Rider screen
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid login. Please register first.");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error connecting to backend: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
