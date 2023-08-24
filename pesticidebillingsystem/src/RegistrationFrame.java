//Registrationframe.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegistrationFrame extends JFrame {
    private JTextField nameField;
    private JTextField aadharField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JLabel errorLabel;

    public RegistrationFrame() {
        // Initialize components and layout
        nameField = new JTextField(20);
        aadharField = new JTextField(20);
        passwordField = new JPasswordField(20);
        registerButton = new JButton("Register");
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        registerButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                registerFarmer();
            }
        });

        // Add components to the frame's content pane
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Aadhar Card:"));
        panel.add(aadharField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("")); // Empty space for layout
        panel.add(registerButton);
        panel.add(errorLabel);

        getContentPane().add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Farmer Registration");
        pack();
        setLocationRelativeTo(null);
    }

    private void registerFarmer() {
        String name = nameField.getText();
        String aadhar = aadharField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        // Data Validation
        if (name.isEmpty() || aadhar.isEmpty() || passwordChars.length == 0) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        // Validate Aadhar card (you can implement a more robust check)
        if (!isValidAadhar(aadhar)) {
            errorLabel.setText("Invalid Aadhar card number.");
            return;
        }

        // Password strength validation (you can add more checks)
        if (password.length() < 8) {
            errorLabel.setText("Password must be at least 8 characters long.");
            return;
        }

        // Securely hash the password
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        Farmer farmer = new Farmer(name, aadhar, passwordHash);

        try {
            // Insert the farmer into the database
            DatabaseManager.insertFarmer(farmer);
            JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.");
            this.dispose(); // Close the registration frame after successful registration
        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("Registration failed due to a database error. Please try again later.");
        }
    }

    private boolean isValidAadhar(String aadhar) {
        // Implement Aadhar card validation logic here (e.g., check length, format)
        return aadhar.matches("\\d{12}");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistrationFrame registrationFrame = new RegistrationFrame();
            registrationFrame.setVisible(true);
        });
    }
}
