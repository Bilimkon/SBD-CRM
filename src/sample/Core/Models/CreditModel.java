package sample.Core.Models;

public class CreditModel {
    private float summa;
    private int id;

    public CreditModel(float summa, int id) {
        this.summa = summa;
        this.id = id;
    }

    public CreditModel(Float credit_sum, String trim) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSumma() {
        return summa;
    }

    public void setSumma(float summa) {
        this.summa = summa;
    }
}
