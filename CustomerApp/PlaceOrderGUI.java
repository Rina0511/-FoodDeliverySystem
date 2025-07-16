package Interface;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class PlaceOrderGUI {

    private JFrame frame;
    private int customerId;
    private HashMap<String, Double> priceMap = new HashMap<>();
    private List<String> items;

    public PlaceOrderGUI(int customerId, List<String> cartList) {
        this.customerId = customerId;
        this.items = cartList;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Your Cart");
        frame.setBounds(100, 100, 450, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(255, 253, 208));
        frame.setLayout(new BorderLayout());

        priceMap.put("Nasi Lemak", 7.50);
        priceMap.put("Malay Rendang Chicken", 10.90);
        priceMap.put("Mee Siam", 8.00);
        priceMap.put("Curry Laksa Mee", 10.80);
        priceMap.put("Kopi", 3.50);
        priceMap.put("Cendol", 6.60);

        JLabel titleLabel = new JLabel("Order Summary", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Rockwell", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        frame.add(titleLabel, BorderLayout.NORTH);

        JTextArea itemArea = new JTextArea();
        itemArea.setFont(new Font("Serif", Font.PLAIN, 18));
        itemArea.setEditable(false);

        if (items == null || items.isEmpty()) {
            itemArea.setText("Your cart is empty.");
        } else {
            int i = 1;
            for (String item : items) {
                itemArea.append(i++ + ". " + item + "\n");
            }
        }

        JScrollPane scrollPane = new JScrollPane(itemArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 253, 208));
        buttonPanel.setLayout(new FlowLayout());

        JButton confirmBtn = new JButton("Confirm Order");
        confirmBtn.setFont(new Font("Rockwell", Font.PLAIN, 18));
        confirmBtn.setBackground(new Color(200, 180, 140));

        confirmBtn.addActionListener(e -> {
            double totalAmount = 0.0;
            List<String> itemList = new ArrayList<>();

            for (String item : items) {
                for (String key : priceMap.keySet()) {
                    if (item.contains(key)) {
                        totalAmount += priceMap.get(key);
                        itemList.add(key);
                        break;
                    }
                }
            }

            // Prepare JSON
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("\"customer_id\":").append(customerId).append(",");
            json.append("\"items\":").append("[");
            for (int i = 0; i < itemList.size(); i++) {
                json.append("\"").append(itemList.get(i)).append("\"");
                if (i < itemList.size() - 1) json.append(",");
            }
            json.append("],");
            json.append("\"total\":").append(String.format("%.2f", totalAmount));
            json.append("}");

            // Send to backend
            String url = "http://localhost/FoodDelivery_Backend/place_order.php";
            String response = BackendConnector.sendPost(url, json.toString());

            System.out.println("Server response: " + response);

            if (response.contains("\"payment_status\":\"success\"")) {
                int orderId = -1;

                try {
                    // Extract order ID safely
                    int startIndex = response.indexOf("\"order_id\":") + 11;
                    int endIndex = response.indexOf("}", startIndex);
                    String orderIdStr = response.substring(startIndex, endIndex).replaceAll("[^0-9]", "");
                    orderId = Integer.parseInt(orderIdStr);

                    JOptionPane.showMessageDialog(frame, "Order placed successfully!\nTotal: RM " + String.format("%.2f", totalAmount));
                    frame.dispose();

                    // ✅ Attempt to open Payment window
                    Payment paymentWindow = new Payment(totalAmount, items, orderId, customerId);
                    paymentWindow.setVisible(true);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Order placed, but failed to open Payment page.\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Order placement failed. Server response:\n" + response, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backBtn = new JButton("Back to Menu");
        backBtn.setFont(new Font("Rockwell", Font.PLAIN, 18));
        backBtn.setBackground(new Color(220, 184, 144));
        backBtn.addActionListener(e -> {
            frame.dispose();
            new MenuGUI(customerId).showWindow();
        });

        buttonPanel.add(confirmBtn);
        buttonPanel.add(backBtn);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
