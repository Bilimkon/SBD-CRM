package sample.MaxsulotTableView;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AllTotal1 {


    SimpleStringProperty  totalSum = new SimpleStringProperty();
    SimpleIntegerProperty Soni = new SimpleIntegerProperty();
    SimpleDoubleProperty Total_cardCost = new SimpleDoubleProperty();
    SimpleDoubleProperty TotalCreditCost = new SimpleDoubleProperty();
    SimpleStringProperty date = new SimpleStringProperty();


    public String getTotalSum() {
        return totalSum.get();
    }

    public SimpleStringProperty totalSumProperty() {
        return totalSum;
    }

    public void setTotalSum(String totalSum) {
        this.totalSum.set(totalSum);
    }

    public int getSoni() {
        return Soni.get();
    }

    public SimpleIntegerProperty soniProperty() {
        return Soni;
    }

    public void setSoni(int soni) {
        this.Soni.set(soni);
    }

    public double getTotal_cardCost() {
        return Total_cardCost.get();
    }

    public SimpleDoubleProperty total_cardCostProperty() {
        return Total_cardCost;
    }

    public void setTotal_cardCost(double total_cardCost) {
        this.Total_cardCost.set(total_cardCost);
    }

    public double getTotalCreditCost() {
        return TotalCreditCost.get();
    }

    public SimpleDoubleProperty totalCreditCostProperty() {
        return TotalCreditCost;
    }

    public void setTotalCreditCost(double totalCreditCost) {
        this.TotalCreditCost.set(totalCreditCost);
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
}
