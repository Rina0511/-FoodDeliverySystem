package Interface;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import Interface.BackendConnector;

public class Receipt extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public Receipt(double totalAmount, List<String> items, int customerId)
 {
        setTitle("Receipt");
        setBounds(100, 100, 500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 253, 208));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Title
        JLabel title = new JLabel("Receipt", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        contentPane.add(title, BorderLayout.NORTH);

        // Text area for receipt content
        JTextArea receiptArea = new JTextArea();
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        receiptArea.setEditable(false);

        // Format receipt details
        StringBuilder receiptText = new StringBuilder();

        // Generate receipt number and timestamp
        Random rand = new Random();
        int receiptNumber = 100000 + rand.nextInt(900000); // 6-digit number
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        receiptText.append("Receipt No   : ").append(receiptNumber).append("\n");
        receiptText.append("Date & Time  : ").append(timestamp).append("\n");
        receiptText.append("====================================\n");
        receiptText.append("Items Ordered:\n");

        int count = 1;
        for (String item : items) {
            receiptText.append(String.format("%2d. %s\n", count++, item));
        }

        receiptText.append("------------------------------------\n");
        receiptText.append(String.format("Total Amount : RM %.2f\n", totalAmount));
        receiptText.append("====================================\n");
        receiptText.append("Thank you for your order!\n");

        receiptArea.setText(receiptText.toString());

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with Close button
        JButton btnClose = new JButton("Close");
        btnClose.setFont(new Font("Rockwell", Font.BOLD, 16));
        btnClose.addActionListener(e -> dispose());
        JButton btnTrackOrder = new JButton("Track Order");
        btnTrackOrder.setFont(new Font("Rockwell", Font.BOLD, 16));
        btnTrackOrder.addActionListener(e -> {
            new TrackOrderGUI(customerId); // Open tracking window
            dispose(); // Optional: close receipt
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 253, 208));
        bottomPanel.add(btnClose);
        bottomPanel.add(btnTrackOrder);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
