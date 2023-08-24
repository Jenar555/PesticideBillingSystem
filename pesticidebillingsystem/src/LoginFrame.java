import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField farmerIdField;
    private JPasswordField passwordField;

    public LoginFrame() {
        // Initialize components and layout
        farmerIdField = new JTextField(10);
        passwordField = new JPasswordField(10);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                try {
                    login();
                } catch (SQLException e1) {
                    
                    e1.printStackTrace();
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Farmer ID:"));
        panel.add(farmerIdField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel(""));
        panel.add(loginButton);

        add(panel);

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void login() throws SQLException {
        int farmerId = Integer.parseInt(farmerIdField.getText());
        String password = new String(passwordField.getPassword());

        Farmer farmer = DatabaseManager.getFarmerById(farmerId);
        if (farmer != null && farmer.getPasswordHash().equals(password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            // Open the main application window or perform other actions
            // You can close the login frame here
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
