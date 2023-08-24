import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainApplicationFrame extends JFrame {
    private JComboBox<Farmer> farmerComboBox;
    private JButton viewDetailsButton;

    public MainApplicationFrame() {
        // Initialize components and layout
        farmerComboBox = new JComboBox<>();
        viewDetailsButton = new JButton("View Details");

        farmerComboBox.setToolTipText("Select a farmer to view details");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(farmerComboBox);
        panel.add(viewDetailsButton);

        add(panel);

        setTitle("Pesticide Billing System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        // Populate the farmerComboBox with registered farmers
        List<Farmer> farmers= new ArrayList<>();
        try {
            farmers = DatabaseManager.getAllFarmers();
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        for (Farmer farmer : farmers) {
            farmerComboBox.addItem(farmer);
        }

        // Add an action listener to the viewDetailsButton
        viewDetailsButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                viewSelectedFarmerDetails();
            }
        });
    }

    private void viewSelectedFarmerDetails() {
        Farmer selectedFarmer = (Farmer) farmerComboBox.getSelectedItem();
        if (selectedFarmer != null) {
            // Create a new dialog to display farmer details
            JDialog detailsDialog = new JDialog(this, "Farmer Details", Dialog.ModalityType.APPLICATION_MODAL);
            detailsDialog.setLayout(new BorderLayout());
    
            // Create a JTextArea to show the details
            JTextArea detailsTextArea = new JTextArea();
            detailsTextArea.setEditable(false);
              // Retrieve the farmer's billing information
        String billingInfo = "";
        try {
            List<BillingInfo> billingInfoList = DatabaseManager.getBillingInfoByFarmerId(selectedFarmer.getFarmer_id());
            for (BillingInfo billing : billingInfoList) {
                billingInfo += "BillingID: " + billing.getBillingID() + "\n";
                billingInfo += "Date: " + billing.getDate() + "\n";
                billingInfo += "Pesticide Name: " + billing.getPesticideName() + "\n";
                billingInfo += "Quantity: " + billing.getQuantity() + "\n";
                billingInfo += "Total Cost: " + billing.getTotalCost() + "\n\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            billingInfo = "Error retrieving billing information.";
        }
    
            // Populate the detailsTextArea with farmer information
            String details = "Name: " + selectedFarmer.getName() + "\n";
            details += "Aadhar Card: " + selectedFarmer.getAadhar() + "\n";
            details += "Pesticide Billing Information:\n" + billingInfo; // Add billing information here
            // Add more information if needed
    
            detailsTextArea.setText(details);
    
            detailsDialog.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);
    
            // Add a close button
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(new ActionListener() {
                
                public void actionPerformed(ActionEvent e) {
                    detailsDialog.dispose(); // Close the dialog
                }
            });
            detailsDialog.add(closeButton, BorderLayout.SOUTH);
    
            // Set dialog properties
            detailsDialog.pack();
            detailsDialog.setLocationRelativeTo(this);
            detailsDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a farmer.");
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame mainAppFrame = new MainApplicationFrame();
            mainAppFrame.setVisible(true);
        });
    }
}
