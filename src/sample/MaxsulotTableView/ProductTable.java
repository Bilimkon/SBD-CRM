package sample.MaxsulotTableView;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class ProductTable {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleDoubleProperty itemsalecost = new SimpleDoubleProperty();
    private SimpleStringProperty barcode = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty type = new SimpleStringProperty();
    private SimpleIntegerProperty quantity = new SimpleIntegerProperty();
    private SimpleDoubleProperty cost = new SimpleDoubleProperty();
    private SimpleStringProperty date = new SimpleStringProperty();
    private SimpleStringProperty expireDate = new SimpleStringProperty();
    private SimpleStringProperty suplier = new SimpleStringProperty();
    private SimpleStringProperty turlari = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public double getItemsalecost() {
        return itemsalecost.get();
    }

    public SimpleDoubleProperty itemsalecostProperty() {
        return itemsalecost;
    }

    public void setItemsalecost(double itemsalecost) {
        this.itemsalecost.set(itemsalecost);
    }

    public static ProductTable getInstance() {
        return new ProductTable();
    }

    private ProductTable() {

    }

    public String getBarcode() {
        return barcode.get();
    }

    public SimpleStringProperty barcodeProperty() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode.set(barcode);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public double getCost() {
        return cost.get();
    }

    public SimpleDoubleProperty costProperty() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost.set(cost);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getExpireDate() {
        return expireDate.get();
    }

    public SimpleStringProperty expireDateProperty() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate.set(expireDate);
    }

    public String getSuplier() {
        return suplier.get();
    }

    public SimpleStringProperty suplierProperty() {
        return suplier;
    }

    public void setSuplier(String suplier) {
        this.suplier.set(suplier);
    }

    public String getTurlari() {
        return turlari.get();
    }

    public SimpleStringProperty turlariProperty() {
        return turlari;
    }

    public void setTurlari(String turlari) {
        this.turlari.set(turlari);
    }
}
