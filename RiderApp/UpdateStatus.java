package rider;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UpdateStatus {

    public UpdateStatus() {
        JFrame frame = new JFrame("Update Order Status");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(0, 100, 0));

        JLabel lblOrder = new JLabel("Order ID:");
        lblOrder.setForeground(Color.WHITE);
        lblOrder.setFont(new Font("Rockwell", Font.PLAIN, 16));
        lblOrder.setBounds(50, 40, 100, 25);
        frame.add(lblOrder);

        JTextField txtOrderID = new JTextField();
        txtOrderID.setBounds(160, 40, 150, 25);
        frame.add(txtOrderID);

        JLabel lblStatus = new JLabel("New Status:");
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setFont(new Font("Rockwell", Font.PLAIN, 16));
        lblStatus.setBounds(50, 90, 100, 25);
        frame.add(lblStatus);

        String[] statuses = {"Pending", "In Transit", "Delivered"};
        JComboBox<String> statusBox = new JComboBox<>(statuses);
        statusBox.setBounds(160, 90, 150, 25);
        frame.add(statusBox);

        JButton btnUpdate = new JButton("Update Status");
        btnUpdate.setFont(new Font("Rockwell", Font.BOLD, 14));
        btnUpdate.setBackground(new Color(220, 184, 144));
        btnUpdate.setBounds(120, 140, 160, 35);
        frame.add(btnUpdate);

        btnUpdate.addActionListener(e -> {
            String orderId = txtOrderID.getText().trim();
            String newStatus = (String) statusBox.getSelectedItem();

            if (orderId.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Order ID cannot be empty.");
                return;
            }

            try {
                URL url = new URL("http://localhost/backend-api/update_status.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                String data = "order_id=" + URLEncoder.encode(orderId, "UTF-8") +
                              "&status=" + URLEncoder.encode(newStatus, "UTF-8");

                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == 200) {
                    JOptionPane.showMessageDialog(frame, "Order status updated successfully.");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to update status.");
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
        new UpdateStatus(); // For testing
    }
}
