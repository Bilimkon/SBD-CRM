package sample.Components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Components.models.AddTypeModel;
import sample.DAO.Database;
import sample.productTableView.Sotuvchi;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddType implements Initializable {

    @FXML
    TextField TypeName;
    @FXML
    TextArea TypeDescription;
    @FXML
    CheckBox Unit;
    @FXML
    Button SaveBtn;
    @FXML
    TextField TypeId;
    @FXML
    TableView TypeTable;

    Connection connection;
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TableColumn id = new TableColumn("Tartib raqami");
        TableColumn name = new TableColumn("Nomi");
        TableColumn measurement = new TableColumn("O'lchov birligi");
        TableColumn description = new TableColumn("Tarif");

        TypeTable.getColumns().addAll(id, name, measurement, description);

        id.setCellValueFactory(new PropertyValueFactory<Sotuvchi, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("name"));
        measurement.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("measurement"));
        description.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("description"));

        try {
            typeTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setUpdate();


    }

    public void typeTable() throws SQLException {

        //List to add items
        ObservableList<AddTypeModel> Sotuvchi_S = FXCollections.observableArrayList();
        try {
            statement = Database.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM type ORDER BY id");
            while (resultSet.next()) {
                AddTypeModel addTypeModel = new AddTypeModel();
                addTypeModel.setId(resultSet.getInt("id"));
                addTypeModel.setName(resultSet.getString("name"));
                addTypeModel.setMeasurement(resultSet.getString("unit"));
                addTypeModel.setDescription(resultSet.getString("Description"));
                Sotuvchi_S.add(addTypeModel);
            }
            TypeTable.setItems(Sotuvchi_S);


        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            statement.close();
            resultSet.close();
        }

    }

    public void setUpdate() {
        try {
            TypeTable.setOnMouseClicked(event -> {
                AddTypeModel apple2 = (AddTypeModel) TypeTable.getItems().get(TypeTable.getSelectionModel().getSelectedIndex());
                try {
                    TypeId.setText(String.valueOf(apple2.getId()));
                    TypeName.setText(apple2.getName());
                    TypeDescription.setText(apple2.getDescription());
                } catch (Exception exc) {
                }
            });
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void AddTypeSaveAction() throws Exception {
        String name = TypeName.getText();
        String description = TypeDescription.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tur qo'shish");
        alert.setHeaderText(null);
        alert.setContentText("Yangi tur qo'shasizmi");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                connection = Database.getConnection();
                preparedStatement = connection.prepareStatement("INSERT INTO type(Name,unit,Description) VALUES (?,?,?)");
                if (!name.isEmpty() && !description.isEmpty()) {
                    if (Unit.isSelected()) {
                        String apple = "1";
                        preparedStatement.setString(1, name);
                        preparedStatement.setString(2, apple);
                        preparedStatement.setString(3, description);
                        preparedStatement.executeUpdate();
                    } else {
                        String apple = "2";
                        preparedStatement.setString(1, name);
                        preparedStatement.setString(2, apple);
                        preparedStatement.setString(3, description);
                        preparedStatement.executeUpdate();
                    }
                    JOptionPane.showMessageDialog(null, "Yangi tur qo'shildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);
                    typeTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Iltimos hamma malumotlarni kiriting", "Xatolik", JOptionPane.ERROR_MESSAGE);

                }
            }
    }

    public void updateType() throws Exception {
        String id = TypeId.getText();
        String name = TypeName.getText();
        String Description = TypeDescription.getText();
        String unit = Unit.getText();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText("Tur malumotlarini o'zgartirasizmi");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                if (name != null && Description != null && !id.isEmpty()) {
                    preparedStatement = Database.getConnection().prepareStatement("UPDATE type set name=?, unit=? ,Description=? where id=" + id);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, unit);
                    preparedStatement.setString(3, Description);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, " Ma'lumotlar o'zgartirildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Iltimos hamma malumotlarni kiriting", "Xatolik", JOptionPane.ERROR_MESSAGE);
                }
            }
        typeTable();
    }

    public void deleteType() throws Exception {
        String id = TypeId.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'chirish");
        alert.setHeaderText(null);
        alert.setContentText("Tanlangan turni o'zgartirasizmi");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                if (!id.isEmpty()) {
                    PreparedStatement preparedStatement1 = null;
                    preparedStatement1 = Database.getConnection().prepareStatement("DELETE FROM type WHERE id=" + id);
                    preparedStatement1.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Tur o'chirildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "O'chirish uchun tur tanlanmadi", "Xatolik", JOptionPane.ERROR_MESSAGE);
                }
            }
        typeTable();
    }
}
