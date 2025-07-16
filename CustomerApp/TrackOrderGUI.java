package Interface;

import javax.swing.*;
import java.awt.*;
import Interface.BackendConnector;
import Interface.NotificationHelper;


public class TrackOrderGUI extends JFrame {

    private int customerId;
    private JPanel contentPane;
    private JTextArea statusArea;

    public TrackOrderGUI(int customerId) {
        this.customerId = customerId;

        setTitle("Track My Orders");
        setBounds(100, 100, 700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 253, 208));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Your Order Status", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        statusArea = new JTextArea();
        statusArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        statusArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statusArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFont(new Font("Rockwell", Font.BOLD, 16));
        refreshBtn.addActionListener(e -> fetchOrderStatus());
        contentPane.add(refreshBtn, BorderLayout.SOUTH);

        fetchOrderStatus(); // Load once on open
        setVisible(true);
    }
    private void fetchOrderStatus() {
        String url = "http://localhost/FoodDelivery_Backend/get_orders.php?customer_id=" + customerId;
        String response = BackendConnector.sendGet(url);

        if (response == null || response.isEmpty()) {
            statusArea.setText("No order history found.");
            return;
        }

        statusArea.setText("");
        String[] orderBlocks = response.split("\\{");
        for (String block : orderBlocks) {
            if (block.contains("customer_id")) {
                String id = extractValue(block, "\"id\":");
                String rawItems = extractValue(block, "\"items\":");
                String cleanedItems = rawItems.replaceAll("[\\[\\]\"]", "").replace("\\", "").trim();

                String[] itemArray = cleanedItems.split(",");
                StringBuilder itemsBuilder = new StringBuilder();
                for (int i = 0; i < itemArray.length; i++) {
                    itemsBuilder.append(itemArray[i].trim());
                    if (itemArray.length > 1 && i != itemArray.length - 1) {
                        itemsBuilder.append(", ");
                    }
                }
                String items = itemsBuilder.toString();

                String total = extractValue(block, "\"total\":");
                String paymentStatus = extractValue(block, "\"payment_status\":");
                String deliveryStatus = extractValue(block, "\"delivery_status\":");

                String rider = extractValue(block, "\"assigned_rider\":");

                // ✅ Show notification based on delivery status
                if (deliveryStatus.equalsIgnoreCase("assigned")) {
                    NotificationHelper.showNotification("Order ID " + id + " is on the way!", "Order Assigned");
                } else if (deliveryStatus.equalsIgnoreCase("delivered")) {
                    NotificationHelper.showNotification("Order ID " + id + " has been delivered!", "Order Delivered");
                }

                statusArea.append("Order ID        : " + id + "\n");
                statusArea.append("Items           : " + items + "\n");
                statusArea.append("Total           : RM " + total + "\n");
                statusArea.append("Payment Status  : " + paymentStatus + "\n");
                statusArea.append("Delivery Status : " + deliveryStatus + "\n");

                if (!rider.equals("null")) {
                    statusArea.append("Assigned Rider  : " + rider + "\n");
                }
                statusArea.append("------------------------------------------\n");
            }
        }
    }


    private String extractValue(String block, String key) {
        int start = block.indexOf(key);
        if (start == -1) return "";
        start += key.length();
        int end = block.indexOf(",", start);
        if (end == -1) end = block.indexOf("}", start);
        if (end == -1) end = block.length();
        return block.substring(start, end).replaceAll("[\":{}]", "").trim();
    }
}
