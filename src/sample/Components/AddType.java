package sample.Components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.AdminPart;
import sample.Components.models.AddTypeModel;
import sample.DAO.Database;
import sample.MaxsulotTableView.Sotuvchi;
import sample.Utils.Utils;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
    @FXML TableView TypeTable;

    Connection connection;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TableColumn  id = new TableColumn("Tartib raqami");
        TableColumn name = new TableColumn("Nomi");
        TableColumn measurement = new TableColumn("O'lchov birligi");
        TableColumn description = new TableColumn("Tarif");

        TypeTable.getColumns().addAll(id,name,measurement,description);

        id.setCellValueFactory(new PropertyValueFactory<Sotuvchi, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("name"));
        measurement.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("measurement"));
        description.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("description"));

        typeTable();
        setUpdate();


    }

    public void typeTable(){

        Statement myStmt = null;
        ResultSet myRs = null;
        //List to add items
        ObservableList<AddTypeModel> Sotuvchi_S = FXCollections.observableArrayList();
        try {
            myStmt = Database.getConnection().createStatement();
            myRs = myStmt.executeQuery("SELECT * FROM product_type ORDER BY id");

            while (myRs.next()) {
                AddTypeModel addTypeModel = new AddTypeModel();

                addTypeModel.setId(myRs.getInt("id"));
                addTypeModel.setName(myRs.getString("name"));
                addTypeModel.setMeasurement(myRs.getString("Measurament"));
                addTypeModel.setDescription(myRs.getString("Description"));

                Sotuvchi_S.add(addTypeModel);
            }
            TypeTable.setItems(Sotuvchi_S);


        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        } finally {
            //  DaoUtils.close(myStmt);
        }

    }
    public void setUpdate(){
        try {
            TypeTable.setOnMouseClicked(event -> {
                AddTypeModel apple2 = (AddTypeModel) TypeTable.getItems().get(TypeTable.getSelectionModel().getSelectedIndex());
                try {
                    TypeId.setText(String.valueOf(apple2.getId()));
                    TypeName.setText(apple2.getName());
                    TypeDescription.setText(apple2.getDescription());
                    Unit.setText(apple2.getMeasurement());
                } catch (Exception exc) {
                }
            });
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void updateDelete(){
        String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText("Maxsulotni rostdan ham o'zgartirasizmi ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent())
            if (result.get() == ButtonType.OK) {


                PreparedStatement preparedStatement = null;
                String barcode1 = TypeId.getText();
                Integer id1 = null;
                try {
                    preparedStatement = Database.getConnection().prepareStatement("DELETE  FROM product_type" + " WHERE item_barcode=?");
                    preparedStatement.setString(1, barcode1);
                    int delete = preparedStatement.executeUpdate();
                    if (delete == 1) {
                        preparedStatement = Database.getConnection().prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Tur ozgartirildi' ,?)");
                        preparedStatement.setString(1, apple);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "O'zgartirish", "Maxsulot o'chirildi", JOptionPane.INFORMATION_MESSAGE);

                    }


                } catch (Exception exc) {

                } finally {
                    // DaoUtils.close(preparedStatement);

                }
            }
    }



    public void AddTypeSaveAction() throws Exception {
        String name = TypeName.getText();
        String description = TypeDescription.getText();
        PreparedStatement preparedStatement = null;


        connection = Database.getConnection();

        preparedStatement = connection.prepareStatement("INSERT INTO product_type(Name,Measurament,Description) VALUES (?,?,?)");

        if (name != null && description != null) {


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
            Stage stage = (Stage) SaveBtn.getScene().getWindow();
            // do what you have to do
            stage.close();
            JOptionPane.showMessageDialog(null, "Yangi tur qo'shildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);

        }
        else {
            JOptionPane.showMessageDialog(null, "Iltimos hamma malumotlarni kiriting", "Xatolik", JOptionPane.ERROR_MESSAGE);
            AdminPart adminPart = new AdminPart();
            adminPart.UpdateBarChart();
        }
    }

}
