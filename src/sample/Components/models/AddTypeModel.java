package sample.Components.models;

public class AddTypeModel {
    private int id;
    private String name;
    private String measurement;
    private String description;

    public AddTypeModel(int id, String name, String measurament, String description) {
        this.id = id;
        this.name = name;
        this.measurement = measurament;
        this.description = description;
    }

    public AddTypeModel() {

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

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
