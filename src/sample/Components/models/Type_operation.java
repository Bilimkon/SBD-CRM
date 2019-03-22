package sample.Components.models;

public class Type_operation {

    private int id;
    private String name;
    private String  unit;
    private int total_products;

    public Type_operation(int id, String name, String unit, int total_products) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.total_products = total_products;
    }

    public Type_operation() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getTotal_products() {
        return total_products;
    }

    public void setTotal_products(int total_products) {
        this.total_products = total_products;
    }
}
