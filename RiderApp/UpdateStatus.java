package rider;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateStatus {

    public UpdateStatus(int riderId) {
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

        String[] statuses = {"pending", "assigned", "delivered"}; // match lowercase used in DB
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
            	String endpoint = "";
            	String json;

            	if (newStatus.equalsIgnoreCase("assigned")) {
            	    endpoint = "accept_order.php";
            	    json = "{\"order_id\":" + orderId + ", \"rider_id\":" + riderId + "}";
            	} else if (newStatus.equalsIgnoreCase("delivered")) {
            	    endpoint = "mark_delivered.php";
            	    json = "{\"order_id\":" + orderId + ", \"status\":\"delivered\"}";
            	} else {
            	    JOptionPane.showMessageDialog(frame, "Invalid status selected.");
            	    return;
            	}

            	URL url = new URL("http://localhost/FoodDelivery_Backend/" + endpoint);


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");

                //String json = "{\"order_id\":" + orderId + ", \"status\":\"" + newStatus + "\"}";

                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                if (responseCode == 200 && response.toString().contains("success")) {
                    JOptionPane.showMessageDialog(frame, "Order status updated.");
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
        new UpdateStatus(1); // for standalone testing
    }
}
