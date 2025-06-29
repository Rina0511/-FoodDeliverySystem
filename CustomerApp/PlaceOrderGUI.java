package Interface;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlaceOrderGUI {

    private JFrame frame;

    public PlaceOrderGUI() {
        initialize(List.of());
    }

    public PlaceOrderGUI(List<String> cartList) {
        initialize(cartList);
    }

    private void initialize(List<String> items) {
        frame = new JFrame("Your Cart");
        frame.setBounds(100, 100, 450, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(255, 253, 208));
        frame.setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Order Summary", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Rockwell", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Display cart items
        JTextArea itemArea = new JTextArea();
        itemArea.setFont(new Font("Serif", Font.PLAIN, 18));
        itemArea.setEditable(false);

        if (items.isEmpty()) {
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

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 253, 208));
        buttonPanel.setLayout(new FlowLayout());

        // Confirm button
        JButton confirmBtn = new JButton("Confirm Order");
        confirmBtn.setFont(new Font("Rockwell", Font.PLAIN, 18));
        confirmBtn.setBackground(new Color(200, 180, 140));
        confirmBtn.setFocusPainted(false);

        confirmBtn.addActionListener(e -> {
            double totalAmount = 0.0;

            for (String item : items) {
                if (item.contains("Nasi Lemak")) totalAmount += 7.50;
                else if (item.contains("Malay Rendang Chicken")) totalAmount += 10.90;
                else if (item.contains("Mee Siam")) totalAmount += 8.00;
                else if (item.contains("Curry Laksa Mee")) totalAmount += 10.80;
                else if (item.contains("Kopi")) totalAmount += 3.50;
                else if (item.contains("Cendol")) totalAmount += 6.60;
            }

            frame.dispose(); // Close the cart window

            // Open Payment window with calculated total
            new Payment(totalAmount, items).setVisible(true);
        });

        // Back to Menu button
        JButton backBtn = new JButton("Back to Menu");
        backBtn.setFont(new Font("Rockwell", Font.PLAIN, 18));
        backBtn.setBackground(new Color(220, 184, 144));
        backBtn.setFocusPainted(false);

        backBtn.addActionListener(e -> {
            frame.dispose();
            new MenuGUI(); // Return to Menu GUI
        });

        buttonPanel.add(confirmBtn);
        buttonPanel.add(backBtn);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
