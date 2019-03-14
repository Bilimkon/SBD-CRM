package sample.MaxsulotTableView;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Sotuvchi {

    SimpleIntegerProperty sotuvchi_id = new SimpleIntegerProperty();
    SimpleStringProperty  firstName_sotuvchi = new SimpleStringProperty();
    SimpleStringProperty  lastName_sotuvchi = new SimpleStringProperty();
    SimpleStringProperty  telNumber_sotuvchi = new SimpleStringProperty();
    SimpleDoubleProperty  salary_sotuvchi = new SimpleDoubleProperty();
    SimpleStringProperty  date_sotuvchi = new SimpleStringProperty();
    SimpleStringProperty  lavozim_sotuvchi = new SimpleStringProperty();
    SimpleStringProperty  password_sotuvchi = new SimpleStringProperty();
    SimpleStringProperty  email_sotuvchi = new SimpleStringProperty();

    public int getSotuvchi_id() {
        return sotuvchi_id.get();
    }

    public SimpleIntegerProperty sotuvchi_idProperty() {
        return sotuvchi_id;
    }

    public void setSotuvchi_id(int sotuvchi_id) {
        this.sotuvchi_id.set(sotuvchi_id);
    }

    public String getFirstName_sotuvchi() {
        return firstName_sotuvchi.get();
    }

    public SimpleStringProperty firstName_sotuvchiProperty() {
        return firstName_sotuvchi;
    }

    public void setFirstName_sotuvchi(String firstName_sotuvchi) {
        this.firstName_sotuvchi.set(firstName_sotuvchi);
    }

    public String getLastName_sotuvchi() {
        return lastName_sotuvchi.get();
    }

    public SimpleStringProperty lastName_sotuvchiProperty() {
        return lastName_sotuvchi;
    }

    public void setLastName_sotuvchi(String lastName_sotuvchi) {
        this.lastName_sotuvchi.set(lastName_sotuvchi);
    }

    public String getTelNumber_sotuvchi() {
        return telNumber_sotuvchi.get();
    }

    public SimpleStringProperty telNumber_sotuvchiProperty() {
        return telNumber_sotuvchi;
    }

    public void setTelNumber_sotuvchi(String telNumber_sotuvchi) {
        this.telNumber_sotuvchi.set(telNumber_sotuvchi);
    }

    public double getSalary_sotuvchi() {
        return salary_sotuvchi.get();
    }

    public SimpleDoubleProperty salary_sotuvchiProperty() {
        return salary_sotuvchi;
    }

    public void setSalary_sotuvchi(double salary_sotuvchi) {
        this.salary_sotuvchi.set(salary_sotuvchi);
    }

    public String getDate_sotuvchi() {
        return date_sotuvchi.get();
    }

    public SimpleStringProperty date_sotuvchiProperty() {
        return date_sotuvchi;
    }

    public void setDate_sotuvchi(String date_sotuvchi) {
        this.date_sotuvchi.set(date_sotuvchi);
    }

    public String getLavozim_sotuvchi() {
        return lavozim_sotuvchi.get();
    }

    public SimpleStringProperty lavozim_sotuvchiProperty() {
        return lavozim_sotuvchi;
    }

    public void setLavozim_sotuvchi(String lavozim_sotuvchi) {
        this.lavozim_sotuvchi.set(lavozim_sotuvchi);
    }

    public String getPassword_sotuvchi() {
        return password_sotuvchi.get();
    }

    public SimpleStringProperty password_sotuvchiProperty() {
        return password_sotuvchi;
    }

    public void setPassword_sotuvchi(String password_sotuvchi) {
        this.password_sotuvchi.set(password_sotuvchi);
    }

    public String getEmail_sotuvchi() {
        return email_sotuvchi.get();
    }

    public SimpleStringProperty email_sotuvchiProperty() {
        return email_sotuvchi;
    }

    public void setEmail_sotuvchi(String email_sotuvchi) {
        this.email_sotuvchi.set(email_sotuvchi);
    }
}
