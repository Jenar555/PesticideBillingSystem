public class Farmer {
    private int farmer_id;
    private String name;
    private String aadhar;
    private String passwordHash;

    public Farmer(int farmer_id, String name, String aadhar, String passwordHash) {
        this.farmer_id = farmer_id;
        this.name = name;
        this.aadhar = aadhar;
        this.passwordHash = passwordHash;
    }

    public Farmer(String name, String aadhar, String passwordHash) {
        this.name = name;
        this.aadhar = aadhar;
        this.passwordHash = passwordHash;
    }

    public int getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(int farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
