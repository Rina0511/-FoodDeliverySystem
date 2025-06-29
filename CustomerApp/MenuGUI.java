package Interface;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MenuGUI {

	private JFrame frame;
	private HashMap<Integer, String> menuMap = new HashMap<>();
	private HashMap<Integer, Double> priceMap = new HashMap<>();
	private java.util.List<String> cartList = new ArrayList<>();

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				MenuGUI window = new MenuGUI();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public void showWindow() {
	    frame.setVisible(true);
	}


	public MenuGUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Menu");
		frame.setBounds(100, 100, 1000, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(0, 100, 0));
		frame.setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel("Menu", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Showcard Gothic", Font.BOLD, 48));
		titleLabel.setForeground(new Color(255, 255, 255));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
		frame.add(titleLabel, BorderLayout.NORTH);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 253, 208));
		contentPane.setLayout(new GridLayout(2, 3, 10, 10));
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		frame.add(contentPane, BorderLayout.CENTER);

		// Add food items with price
		addFoodItem(contentPane, "src/Assets/image1.png", 1, "Nasi Lemak", 7.50);
		addFoodItem(contentPane, "src/Assets/image2.png", 2, "Malay Rendang Chicken", 10.90);
		addFoodItem(contentPane, "src/Assets/image3.png", 3, "Mee Siam", 8.00);
		addFoodItem(contentPane, "src/Assets/image4.png", 4, "Curry Laksa Mee", 10.80);
		addFoodItem(contentPane, "src/Assets/image5.png", 5, "Kopi", 3.50);
		addFoodItem(contentPane, "src/Assets/image6.png", 6, "Cendol", 6.60);

		JPanel bottomPanel = new JPanel(new FlowLayout());
		bottomPanel.setBackground(new Color(255, 253, 208));

		JButton orderBtn = new JButton("Place Order");
		orderBtn.setFont(new Font("Rockwell", Font.PLAIN, 22));
		orderBtn.setBackground(new Color(220, 184, 144));
		orderBtn.setFocusPainted(false);

		orderBtn.addActionListener(e -> {
			String input = JOptionPane.showInputDialog(frame, "Enter item numbers (e.g., 1,3,5):");
			if (input != null && !input.isEmpty()) {
				String[] nums = input.split(",");
				StringBuilder ordered = new StringBuilder();
				for (String n : nums) {
					try {
						int itemNum = Integer.parseInt(n.trim());
						String food = menuMap.get(itemNum);
						Double price = priceMap.get(itemNum);
						if (food != null && price != null) {
							cartList.add(food + " - RM " + String.format("%.2f", price));
							ordered.append(food).append(" (RM ").append(String.format("%.2f", price)).append(")\n");
						}
					} catch (NumberFormatException ignored) {
					}
				}
				if (!ordered.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Items added to cart:\n" + ordered, "Order Success", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frame, "No valid items added.", "Order Failed", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton viewCartBtn = new JButton("View Cart");
		viewCartBtn.setFont(new Font("Rockwell", Font.PLAIN, 22));
		viewCartBtn.setBackground(new Color(200, 180, 140));

		viewCartBtn.addActionListener(e -> {
			new PlaceOrderGUI(cartList);
		});

		bottomPanel.add(orderBtn);
		bottomPanel.add(viewCartBtn);
		frame.add(bottomPanel, BorderLayout.SOUTH);
	}

	private void addFoodItem(JPanel panel, String imagePath, int itemNumber, String foodName, double price) {
		JPanel foodPanel = new JPanel();
		foodPanel.setBackground(new Color(255, 253, 208));
		foodPanel.setLayout(new BorderLayout());

		try {
			ImageIcon icon = new ImageIcon(imagePath);
			Image scaledImg = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
			JLabel imgLabel = new JLabel(new ImageIcon(scaledImg));
			imgLabel.setHorizontalAlignment(JLabel.CENTER);
			foodPanel.add(imgLabel, BorderLayout.CENTER);
		} catch (Exception e) {
			JLabel errorLabel = new JLabel("Image not found");
			errorLabel.setHorizontalAlignment(JLabel.CENTER);
			foodPanel.add(errorLabel, BorderLayout.CENTER);
		}

		JPanel labelPanel = new JPanel(new GridLayout(2, 1));
		labelPanel.setBackground(new Color(255, 253, 208));

		JLabel nameLabel = new JLabel(itemNumber + ". " + foodName, SwingConstants.CENTER);
		nameLabel.setFont(new Font("Rockwell", Font.BOLD, 16));
		labelPanel.add(nameLabel);

		JLabel priceLabel = new JLabel("RM " + String.format("%.2f", price), SwingConstants.CENTER);
		priceLabel.setFont(new Font("Rockwell", Font.PLAIN, 14));
		priceLabel.setForeground(Color.DARK_GRAY);
		labelPanel.add(priceLabel);

		foodPanel.add(labelPanel, BorderLayout.SOUTH);

		panel.add(foodPanel);
		menuMap.put(itemNumber, foodName);
		priceMap.put(itemNumber, price);
	}
}
