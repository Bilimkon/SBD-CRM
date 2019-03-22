package sample.MaxsulotTableView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HistoryTable {
    private SimpleIntegerProperty savdoactionId = new SimpleIntegerProperty();
    private SimpleIntegerProperty tarixid = new SimpleIntegerProperty();
    private SimpleIntegerProperty MaxsulotId = new SimpleIntegerProperty();
    private SimpleStringProperty itemname = new SimpleStringProperty();
    private SimpleStringProperty itemtype = new SimpleStringProperty();
    private SimpleIntegerProperty itemquantity = new SimpleIntegerProperty();
    private SimpleStringProperty itembarcode = new SimpleStringProperty();
    private SimpleStringProperty paiddata = new SimpleStringProperty();
    private SimpleStringProperty totalcost = new SimpleStringProperty();

    public int getSavdoactionId() {
        return savdoactionId.get();
    }

    public SimpleIntegerProperty savdoactionIdProperty() {
        return savdoactionId;
    }

    public void setSavdoactionId(int savdoactionId) {
        this.savdoactionId.set(savdoactionId);
    }

    public int getTarixid() {
        return tarixid.get();
    }

    public SimpleIntegerProperty tarixidProperty() {
        return tarixid;
    }

    public void setTarixid(int tarixid) {
        this.tarixid.set(tarixid);
    }

    public String getItemname() {
        return itemname.get();
    }

    public SimpleStringProperty itemnameProperty() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname.set(itemname);
    }

    public String getItemtype() {
        return itemtype.get();
    }

    public SimpleStringProperty itemtypeProperty() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype.set(itemtype);
    }

    public int getItemquantity() {
        return itemquantity.get();
    }

    public SimpleIntegerProperty itemquantityProperty() {
        return itemquantity;
    }

    public void setItemquantity(int itemquantity) {
        this.itemquantity.set(itemquantity);
    }

    public String getItembarcode() {
        return itembarcode.get();
    }

    public SimpleStringProperty itembarcodeProperty() {
        return itembarcode;
    }

    public void setItembarcode(String itembarcode) {
        this.itembarcode.set(itembarcode);
    }

    public String getPaiddata() {
        return paiddata.get();
    }

    public SimpleStringProperty paiddataProperty() {
        return paiddata;
    }

    public void setPaiddata(String paiddata) {
        this.paiddata.set(paiddata);
    }

    public String getTotalcost() {
        return totalcost.get();
    }

    public SimpleStringProperty totalcostProperty() {
        return totalcost;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost.set(totalcost);
    }

    public int getMaxsulotId() {
        return MaxsulotId.get();
    }

    public SimpleIntegerProperty maxsulotIdProperty() {
        return MaxsulotId;
    }

    public void setMaxsulotId(int maxsulotId) {
        this.MaxsulotId.set(maxsulotId);
    }
}


