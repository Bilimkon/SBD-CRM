package sample.Core;

public class History {

    private int id;
    private int userId;
    private int itemId;
    private String itemName;
    private String itemType;
    private int itemQuantity;
    private String itemBarcode;
    private float totalCost;
    private String paidDate;


    public History(int id, int userId, int itemId, String itemName, String itemType, int itemQuantity, String itemBarcode, String paidDate, float totalCost) {

        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemQuantity = itemQuantity;
        this.itemBarcode = itemBarcode;
        this.paidDate = paidDate;
        this.totalCost = totalCost;
    }

    public History(int tarix_id, String item_name, String item_type, int item_quantity, String paid_date, float total_cost) {


    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {

        this.itemType = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Double getItemQuantity() {
        return Double.valueOf(itemQuantity);
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getBarcode() {
        return itemBarcode;
    }

    public void setBarcode(String ItemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
