import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseManager {
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pesticide_billing";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Rupak@soa123";

    // Establish a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    // Insert a new farmer into the database
    public static void insertFarmer(Farmer farmer) throws SQLException {
        int randomFarmerId = generateRandomFarmerId();
        String insertQuery = "INSERT INTO farmer (farmer_id, name, aadhar, passwordHash) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, randomFarmerId);
            statement.setString(2, farmer.getName());
            statement.setString(3, farmer.getAadhar());
            statement.setString(4, farmer.getPasswordHash());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating farmer failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int farmerId = generatedKeys.getInt(1);
                    farmer.setFarmer_id(farmerId); // Set the generated farmerId in the Farmer object
                } else {
                    throw new SQLException("Creating farmer failed, no ID obtained.");
                }
            }
        }
    }

    // Generate a random farmer_id (you can adjust the range as needed)
    private static int generateRandomFarmerId() {
        Random random = new Random();
        // Generate a random integer within a specific range
        int minId = 1000; // Adjust as needed
        int maxId = 9999; // Adjust as needed
        return random.nextInt(maxId - minId + 1) + minId;
    }

    // Implement other database-related methods here

    public static Farmer getFarmerById(int farmerId) throws SQLException {
        Farmer farmer = null;

        String selectQuery = "SELECT name, aadhar, passwordHash FROM farmer WHERE farmer_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {

            statement.setInt(1, farmerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String aadharCard = resultSet.getString("aadhar");
                    String passwordHash = resultSet.getString("passwordHash");

                    farmer = new Farmer(farmerId, name, aadharCard, passwordHash);
                }
            }
        }

        return farmer;
    }

    public static List<BillingInfo> getBillingInfoByFarmerId(int farmerId) throws SQLException {
        List<BillingInfo> billingInfoList = new ArrayList<>();
    
        String selectQuery = "SELECT BillingID, Date, PesticideName, Quantity, TotalCost " +
                             "FROM pesticidebillinginfo " +
                             "WHERE farmer_id = ?";
    
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
    
            statement.setInt(1, farmerId);
    
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int billingId = resultSet.getInt("BillingID");
                    String date = resultSet.getString("Date");
                    String pesticideName = resultSet.getString("PesticideName");
                    int quantity = resultSet.getInt("Quantity");
                    double totalCost = resultSet.getDouble("TotalCost");
    
                    BillingInfo billingInfo = new BillingInfo(billingId, quantity, date, pesticideName, quantity, totalCost);
                    billingInfoList.add(billingInfo);
                }
            }
        }
    
        return billingInfoList;
    }
    

    public static List<Farmer> getAllFarmers() throws SQLException {
        List<Farmer> farmers = new ArrayList<>();

        String selectQuery = "SELECT farmer_id, name, aadhar, passwordHash FROM farmer";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int farmerId = resultSet.getInt("farmer_id");
                String name = resultSet.getString("name");
                String aadharCard = resultSet.getString("aadhar");
                String passwordHash = resultSet.getString("passwordHash");

                Farmer farmer = new Farmer(farmerId, name, aadharCard, passwordHash);
                farmers.add(farmer);
            }
        }

        return farmers;
    }

    // Retrieve a farmer by their Aadhar card number
    public static Farmer getFarmerByAadhar(String aadharCard) throws SQLException {
        String selectQuery = "SELECT farmer_id, name, aadhar, passwordHash FROM farmer WHERE aadhar = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, aadharCard);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int farmerId = resultSet.getInt("farmer_id");
                    String name = resultSet.getString("name");
                    String passwordHash = resultSet.getString("passwordHash");

                    Farmer farmer = new Farmer(farmerId, name, aadharCard, passwordHash);
                    return farmer;
                }
            }
        }

        return null; // Return null if no farmer with the given Aadhar card number is found
    }

    // Delete a farmer from the database by their ID
    public static void deleteFarmerById(int farmerId) throws SQLException {
        String deleteQuery = "DELETE FROM farmer WHERE farmer_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, farmerId);
            statement.executeUpdate();
        }
    }

    // Update farmer details in the database
    public static void updateFarmer(Farmer farmer) throws SQLException {
        String updateQuery = "UPDATE farmer SET name = ?, aadhar = ?, passwordHash = ? WHERE farmer_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, farmer.getName());
            statement.setString(2, farmer.getAadhar());
            statement.setString(3, farmer.getPasswordHash());
            statement.setInt(4, farmer.getFarmer_id());

            statement.executeUpdate();
        }
    }
}
