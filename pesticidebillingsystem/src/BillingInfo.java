public class BillingInfo {
    private int BillingID;
    private int farmer_id;
    private String Date;
    private String PesticideName;
    private int Quantity;
    private double TotalCost;

    public BillingInfo(int BillingID, int farmer_id, String Date, String PesticideName, int Quantity, double TotalCost) {
        this.BillingID = BillingID;
        this.farmer_id = farmer_id;
        this.Date = Date;
        this.PesticideName = PesticideName;
        this.Quantity = Quantity;
        this.TotalCost = TotalCost;
    }

    public int getBillingID() {
        return BillingID;
    }

    public void setBillingID(int BillingID) {
        this.BillingID = BillingID;
    }

    public int getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(int farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getPesticideName() {
        return PesticideName;
    }

    public void setPesticideName(String PesticideName) {
        this.PesticideName = PesticideName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public double getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(double TotalCost) {
        this.TotalCost = TotalCost;
    }

    @Override
    public String toString() {
        return "BillingInfo{" +
                "BillingID=" + BillingID +
                ", farmer_id=" + farmer_id +
                ", Date='" + Date + '\'' +
                ", PesticideName='" + PesticideName + '\'' +
                ", Quantity=" + Quantity +
                ", TotalCost=" + TotalCost +
                '}';
    }
}
