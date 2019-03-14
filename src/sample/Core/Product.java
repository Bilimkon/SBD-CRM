package sample.Core;

/*
 Humoyun Qo'rg'onov  SBD(Software Business Development)
  */

public class Product {

    private int id;
    private String itemBarcode;
    private String itemName;
    private String itemType;
    private int itemQuantity;
    private Float itemCost;
    private String itemDate;
    private String itemExpireDate;
    private String itemSuplier;
    private String itemTurlari;

    private Product() {

    }

    public static Product getInstance() {
        return new Product();
    }

    public Product(String itemBarcode, String itemName, String itemType, int itemQuantity, Float itemCost, String itemDate, String itemExpireDate, String itemSuplier, String itemTurlari) {
        this(0, itemBarcode, itemName, itemType, itemQuantity, itemCost, itemDate, itemExpireDate, itemSuplier, itemTurlari);
    }

    public Product(int id, String itemBarcode, String itemName, String itemType, int itemQuantity, Float itemCost, String itemDate, String itemExpireDate, String itemSuplier, String itemTurlari) {

        super();
        this.id = id;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemQuantity = itemQuantity;
        this.itemCost = itemCost;
        this.itemSuplier = itemSuplier;
        this.itemDate = itemDate;
        this.itemExpireDate = itemExpireDate;
        this.itemBarcode = itemBarcode;
        this.itemTurlari = itemTurlari;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Float getItemCost() {
        return itemCost;
    }

    public void setItemCost(Float itemCost) {
        this.itemCost = itemCost;
    }

    public String getItemSuplier() {
        return itemSuplier;
    }

    public void setItemSuplier(String itemSuplier) {
        this.itemSuplier = itemSuplier;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public String getItemExpireDate() {
        return itemExpireDate;
    }

    public void setItemExpireDate(String itemExpireDate) {
        this.itemExpireDate = itemExpireDate;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public String getItemTurlari() {
        return itemTurlari;
    }

    public void setItemTurlari(String itemTurlari) {
        this.itemTurlari = itemTurlari;
    }

    @Override
    public String toString() {
        return String
                .format("Maxsulot [id=%s,itemBarcode=%s ,itemName=%s, itemType=%s,itemQuantity=%s, itemCost=%s, itemDate=%s,ItemExpireDate=%s,itemSuplier=%s,itemTurlari=%s]",
                        id, itemBarcode, itemName, itemType, itemQuantity, itemCost, itemDate, itemExpireDate, itemSuplier, itemTurlari);
    }


}
