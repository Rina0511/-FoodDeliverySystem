package rider;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class ViewTasks extends JFrame {

    private JTable ordersTable;
    private DefaultTableModel tableModel;

    public ViewTasks() {
        setTitle("Rider - View Assigned Orders");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"Order ID", "Customer", "Status", "Date/Time"};
        tableModel = new DefaultTableModel(columns, 0);
        ordersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        JButton refreshBtn = new JButton("Refresh Orders");
        refreshBtn.setBackground(new Color(200, 180, 140));
        refreshBtn.addActionListener(e -> fetchOrders());
        bottomPanel.add(refreshBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(220, 120, 120));
        logoutBtn.addActionListener(e -> {
            dispose(); // Close current window
            new LoginRider(); // Go back to login
        });
        bottomPanel.add(logoutBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);

        fetchOrders();
    }

    private void fetchOrders() {
        try {
            URL url = new URL("http://localhost/api/get_orders.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONArray jsonArray = new JSONArray(response.toString());
            tableModel.setRowCount(0); // Clear existing rows

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject order = jsonArray.getJSONObject(i);
                tableModel.addRow(new Object[]{
                    order.getInt("order_id"),
                    order.getString("customer_name"),
                    order.getString("status"),
                    order.getString("created_at")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to fetch orders: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ViewTasks(); // for testing only, real app starts from RiderLogin
    }
}
