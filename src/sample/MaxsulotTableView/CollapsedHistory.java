package sample.MaxsulotTableView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CollapsedHistory {

    SimpleIntegerProperty id_C = new SimpleIntegerProperty();
    SimpleIntegerProperty sotuvchi_id_C = new SimpleIntegerProperty();
    SimpleStringProperty first_name_C = new SimpleStringProperty();
    SimpleStringProperty last_name_C = new SimpleStringProperty();
    SimpleStringProperty  card_amount_C = new SimpleStringProperty();
    SimpleStringProperty credit_C  = new SimpleStringProperty();
    SimpleStringProperty credit_description_C = new SimpleStringProperty();
    SimpleStringProperty paid_date_C = new SimpleStringProperty();
    SimpleStringProperty total_cost_C = new SimpleStringProperty();
    SimpleStringProperty paid_in_cash_C = new SimpleStringProperty();

    public int getId_C() {
        return id_C.get();
    }

    public SimpleIntegerProperty id_CProperty() {
        return id_C;
    }

    public void setId_C(int id_C) {
        this.id_C.set(id_C);
    }

    public int getSotuvchi_id_C() {
        return sotuvchi_id_C.get();
    }

    public SimpleIntegerProperty sotuvchi_id_CProperty() {
        return sotuvchi_id_C;
    }

    public void setSotuvchi_id_C(int sotuvchi_id_C) {
        this.sotuvchi_id_C.set(sotuvchi_id_C);
    }

    public String getFirst_name_C() {
        return first_name_C.get();
    }

    public SimpleStringProperty first_name_CProperty() {
        return first_name_C;
    }

    public void setFirst_name_C(String first_name_C) {
        this.first_name_C.set(first_name_C);
    }

    public String getLast_name_C() {
        return last_name_C.get();
    }

    public SimpleStringProperty last_name_CProperty() {
        return last_name_C;
    }

    public void setLast_name_C(String last_name_C) {
        this.last_name_C.set(last_name_C);
    }

    public String getCard_amount_C() {
        return card_amount_C.get();
    }

    public SimpleStringProperty card_amount_CProperty() {
        return card_amount_C;
    }

    public void setCard_amount_C(String card_amount_C) {
        this.card_amount_C.set(card_amount_C);
    }

    public String getCredit_C() {
        return credit_C.get();
    }

    public SimpleStringProperty credit_CProperty() {
        return credit_C;
    }

    public void setCredit_C(String credit_C) {
        this.credit_C.set(credit_C);
    }

    public String  getCredit_description_C() {
        return credit_description_C.get();
    }

    public SimpleStringProperty credit_description_CProperty() {
        return credit_description_C;
    }

    public void setCredit_description_C(String credit_description_C) {
        this.credit_description_C.set(credit_description_C);
    }

    public String getPaid_date_C() {
        return paid_date_C.get();
    }

    public SimpleStringProperty paid_date_CProperty() {
        return paid_date_C;
    }

    public void setPaid_date_C(String paid_date_C) {
        this.paid_date_C.set(paid_date_C);
    }

    public String getTotal_cost_C() {
        return total_cost_C.get();
    }

    public SimpleStringProperty total_cost_CProperty() {
        return total_cost_C;
    }

    public void setTotal_cost_C(String total_cost_C) {
        this.total_cost_C.set(total_cost_C);
    }

    public String getPaid_in_cash_C() {
        return paid_in_cash_C.get();
    }

    public SimpleStringProperty paid_in_cash_CProperty() {
        return paid_in_cash_C;
    }

    public void setPaid_in_cash_C(String paid_in_cash_C) {
        this.paid_in_cash_C.set(paid_in_cash_C);
    }
}
