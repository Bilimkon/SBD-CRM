package sample.Core.Models;

public class CreditModel {
    private float summa;
    private String description;

    public CreditModel(float summa, String description) {
        this.summa = summa;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getSumma() {
        return summa;
    }

    public void setSumma(float summa) {
        this.summa = summa;
    }
}
