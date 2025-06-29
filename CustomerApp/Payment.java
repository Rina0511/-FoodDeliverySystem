package Interface;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Payment extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtPending;
    private JTextArea textArea;

    public Payment(double totalAmount, List<String> cartItems) {
        setTitle("Payment");
        setBounds(100, 100, 800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 253, 208));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("Payment");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        lblTitle.setBounds(10, 10, 776, 78);
        contentPane.add(lblTitle);

        JLabel lblNewLabel_1 = new JLabel("Total Amount      : RM");
        lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(214, 141, 184, 42);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Payment Method:");
        lblNewLabel_1_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
        lblNewLabel_1_1.setBounds(214, 260, 171, 42);
        contentPane.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Status                 : ");
        lblNewLabel_1_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
        lblNewLabel_1_2.setBounds(214, 200, 171, 42);
        contentPane.add(lblNewLabel_1_2);

        txtPending = new JTextField("Pending");
        txtPending.setFont(new Font("Arial Black", Font.PLAIN, 14));
        txtPending.setColumns(10);
        txtPending.setBounds(374, 211, 140, 31);
        contentPane.add(txtPending);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBounds(397, 152, 117, 22);
        textArea.setText(String.format("%.2f", totalAmount));
        contentPane.add(textArea);

        JList<String> list = new JList<>(new String[]{"TnG e-Wallet", "FPX online banking", "Visa Card"});
        list.setFont(new Font("Arial Black", Font.PLAIN, 12));
        list.setBounds(374, 272, 140, 58);
        contentPane.add(list);

        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        btnConfirm.setBounds(438, 381, 97, 23);
        contentPane.add(btnConfirm);

        btnConfirm.addActionListener(e -> {
            String paymentType = list.getSelectedValue();
            if (paymentType == null || paymentType.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a payment method.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Payment successful via " + paymentType + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close Payment window
                new Receipt(totalAmount, cartItems); // `items` is your cart list

            }
        });

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        btnBack.setBounds(199, 381, 97, 23);
        contentPane.add(btnBack);

        btnBack.addActionListener(e -> dispose());
    }
}
