package Interface;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import Interface.BackendConnector;

public class Payment extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtStatus;
    private JTextField txtTotal;
    private int customerId;

    public Payment(double totalAmount, List<String> cartItems, int orderId, int customerId) {
        this.customerId = customerId;

        System.out.println("Payment constructor loaded. customerId = " + customerId + ", orderId = " + orderId);

        setTitle("Payment");
        setSize(800, 700);
        setLocationRelativeTo(null); // ✅ Center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true); // ✅ Bring window to front

        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 253, 208));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Title
        JLabel lblTitle = new JLabel("Payment");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        lblTitle.setBounds(10, 10, 776, 78);
        contentPane.add(lblTitle);

        // Total Amount
        JLabel lblAmount = new JLabel("Total Amount      : RM");
        lblAmount.setFont(new Font("Arial Black", Font.PLAIN, 15));
        lblAmount.setBounds(214, 141, 184, 42);
        contentPane.add(lblAmount);

        txtTotal = new JTextField(String.format("%.2f", totalAmount));
        txtTotal.setFont(new Font("Arial Black", Font.PLAIN, 14));
        txtTotal.setEditable(false);
        txtTotal.setBounds(397, 152, 117, 25);
        contentPane.add(txtTotal);

        // Status
        JLabel lblStatus = new JLabel("Status                 : ");
        lblStatus.setFont(new Font("Arial Black", Font.PLAIN, 15));
        lblStatus.setBounds(214, 200, 171, 42);
        contentPane.add(lblStatus);

        txtStatus = new JTextField("Pending");
        txtStatus.setFont(new Font("Arial Black", Font.PLAIN, 14));
        txtStatus.setEditable(false);
        txtStatus.setBounds(397, 211, 117, 25);
        contentPane.add(txtStatus);

        // Payment Method
        JLabel lblPaymentMethod = new JLabel("Payment Method:");
        lblPaymentMethod.setFont(new Font("Arial Black", Font.PLAIN, 15));
        lblPaymentMethod.setBounds(214, 260, 171, 42);
        contentPane.add(lblPaymentMethod);

        JList<String> methodList = new JList<>(new String[]{"TnG e-Wallet", "FPX Online Banking", "Visa Card"});
        methodList.setFont(new Font("Arial Black", Font.PLAIN, 12));
        methodList.setBounds(397, 272, 140, 60);
        methodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contentPane.add(methodList);

        // Confirm Button
        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        btnConfirm.setBounds(438, 381, 97, 30);
        contentPane.add(btnConfirm);

        btnConfirm.addActionListener(e -> {
            String selectedMethod = methodList.getSelectedValue();
            if (selectedMethod == null || selectedMethod.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a payment method.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String jsonData = "{\"order_id\":" + orderId + "}";
            String url = "http://localhost/FoodDelivery_Backend/update_payment_status.php";
            String response = BackendConnector.sendPost(url, jsonData);

            if (response.contains("\"payment_status\":\"success\"")) {
                txtStatus.setText("Paid");
                JOptionPane.showMessageDialog(this, "Payment successful via " + selectedMethod + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new Receipt(totalAmount, cartItems, customerId).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Payment succeeded but failed to update status.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Back Button
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        btnBack.setBounds(199, 381, 97, 30);
        contentPane.add(btnBack);

        btnBack.addActionListener(e -> dispose());

        // ✅ Final: Make it visible
        setVisible(true);
        System.out.println("Payment window shown.");
    }
}
