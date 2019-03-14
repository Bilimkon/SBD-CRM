package sample.MaxsulotTableView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OmborHisobi {
    private SimpleStringProperty barcodeOH = new SimpleStringProperty();
    private SimpleStringProperty nameOH = new SimpleStringProperty();
    private SimpleStringProperty typeOH = new SimpleStringProperty();
    private SimpleIntegerProperty quantityOH = new SimpleIntegerProperty();
    private SimpleStringProperty costOH = new SimpleStringProperty();
    private SimpleStringProperty salaCostOH = new SimpleStringProperty();
    private SimpleStringProperty item_total_cost = new SimpleStringProperty();
    private SimpleStringProperty item_total_sale_cost = new SimpleStringProperty();

    public String getBarcodeOH() {
        return barcodeOH.get();
    }

    public SimpleStringProperty barcodeOHProperty() {
        return barcodeOH;
    }

    public void setBarcodeOH(String barcodeOH) {
        this.barcodeOH.set(barcodeOH);
    }

    public String getNameOH() {
        return nameOH.get();
    }

    public SimpleStringProperty nameOHProperty() {
        return nameOH;
    }

    public void setNameOH(String nameOH) {
        this.nameOH.set(nameOH);
    }

    public String getTypeOH() {
        return typeOH.get();
    }

    public SimpleStringProperty typeOHProperty() {
        return typeOH;
    }

    public void setTypeOH(String typeOH) {
        this.typeOH.set(typeOH);
    }

    public int getQuantityOH() {
        return quantityOH.get();
    }

    public SimpleIntegerProperty quantityOHProperty() {
        return quantityOH;
    }

    public void setQuantityOH(int quantityOH) {
        this.quantityOH.set(quantityOH);
    }

    public String getCostOH() {
        return costOH.get();
    }

    public SimpleStringProperty costOHProperty() {
        return costOH;
    }

    public void setCostOH(String costOH) {
        this.costOH.set(costOH);
    }

    public String getItem_total_cost() {
        return item_total_cost.get();
    }

    public SimpleStringProperty item_total_costProperty() {
        return item_total_cost;
    }

    public void setItem_total_cost(String item_total_cost) {
        this.item_total_cost.set(item_total_cost);
    }

    public String getItem_total_sale_cost() {
        return item_total_sale_cost.get();
    }

    public SimpleStringProperty item_total_sale_costProperty() {
        return item_total_sale_cost;
    }

    public void setItem_total_sale_cost(String item_total_sale_cost) {
        this.item_total_sale_cost.set(item_total_sale_cost);
    }

    public String getSalaCostOH() {
        return salaCostOH.get();
    }

    public SimpleStringProperty salaCostOHProperty() {
        return salaCostOH;
    }

    public void setSalaCostOH(String salaCostOH) {
        this.salaCostOH.set(salaCostOH);
    }
}
