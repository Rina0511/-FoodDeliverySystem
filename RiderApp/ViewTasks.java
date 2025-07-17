package rider;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class ViewTasks extends JFrame {
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    private int riderId;

    public ViewTasks(int riderId) {
        this.riderId = riderId;
        setTitle("Rider - View Assigned Orders");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"Order ID", "Customer", "Status", "Date/Time", "Assigned Rider", "Track"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 5; 
            }
        };

        ordersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        add(scrollPane, BorderLayout.CENTER);

        // Properly attach Track button renderer/editor to the correct column
        ordersTable.getColumnModel().getColumn(5).setCellRenderer(new TrackButtonRenderer());
        ordersTable.getColumnModel().getColumn(5).setCellEditor(new TrackButtonEditor(new JCheckBox()));

        JPanel bottomPanel = new JPanel(new FlowLayout());

        JButton refreshBtn = new JButton("Refresh Orders");
        refreshBtn.setBackground(new Color(144, 238, 144));
        refreshBtn.setForeground(Color.BLACK);
        refreshBtn.setFont(new Font("Rockwell", Font.BOLD, 14));
        refreshBtn.addActionListener(e -> fetchOrders());
        bottomPanel.add(refreshBtn);

        JButton updateStatusBtn = new JButton("Update Status");
        updateStatusBtn.setBackground(new Color(173, 216, 230));
        updateStatusBtn.setForeground(Color.BLACK);
        updateStatusBtn.setFont(new Font("Rockwell", Font.BOLD, 14));
        updateStatusBtn.addActionListener(e -> new UpdateStatus(riderId));
        bottomPanel.add(updateStatusBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(240, 128, 128));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Rockwell", Font.BOLD, 14));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginRider();
        });
        bottomPanel.add(logoutBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
        fetchOrders();
    }

    private void fetchOrders() {
        try {
            URL url = new URL("http://localhost/FoodDelivery_Backend/get_orders_rider.php?rider_id=" + riderId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            reader.close();

            JSONArray arr = new JSONArray(sb.toString());
            tableModel.setRowCount(0);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject order = arr.getJSONObject(i);
                int orderId = order.getInt("id");
                int customerId = order.getInt("customer_id");
                String status = order.getString("delivery_status");
                String createdAt = order.optString("created_at", "-");

                String assignedRider = order.isNull("assigned_rider") ? "N/A" : String.valueOf(order.getInt("assigned_rider"));
                String address = order.optString("address", "No address available");

                tableModel.addRow(new Object[]{orderId, customerId, status, createdAt, assignedRider, address});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching orders: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Renderer for Track button
    class TrackButtonRenderer extends JButton implements TableCellRenderer {
        public TrackButtonRenderer() {
            setText("Track");
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int col) {
            return this;
        }
    }

    // Editor for Track button
    class TrackButtonEditor extends DefaultCellEditor {
        private JButton button = new JButton("Track");
        private String address;

        public TrackButtonEditor(JCheckBox checkBox) {
            super(checkBox);

            button.addActionListener(e -> {
                if (address == null || address.trim().isEmpty() || address.equalsIgnoreCase("No address available")) {
                    JOptionPane.showMessageDialog(null, "No valid address found to track.");
                    return;
                }

                try {
                    String url = "https://www.google.com/maps/search/?api=1&query=" +
                                 URLEncoder.encode(address, "UTF-8");
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Couldn't open Google Maps: " + ex.getMessage());
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
            address = (String) value; // Capture the address for this row
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Track";
        }
    }
}
