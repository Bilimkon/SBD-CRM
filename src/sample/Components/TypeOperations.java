package sample.Components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Components.models.Type_operation;
import sample.DAO.Database;
import sample.MaxsulotTableView.Sotuvchi;
import sample.Utils.Utils;

import javax.swing.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class TypeOperations implements Initializable {



    @FXML public TableView TypeOperationTable;
    @FXML public TextField TNarx;
    @FXML public TextField TMiqdor;
    @FXML public TextField TId;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn id = new TableColumn("Tartib raqami");
        TableColumn name = new TableColumn("Nomi");
        TableColumn measurement = new TableColumn("O'lchov birligi");
        TableColumn description = new TableColumn("Mahsulotlar soni");

        TypeOperationTable.getColumns().addAll(id,name,measurement,description);

        id.setCellValueFactory(new PropertyValueFactory<Sotuvchi, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("name"));
        measurement.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("unit"));
        description.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("total_products"));

        typeOperationTable();
        setUpdate();
    }


    public void typeOperationTable(){

        Statement myStmt = null;
        ResultSet myRs = null;
        //List to add items
        ObservableList<Type_operation> Sotuvchi_S = FXCollections.observableArrayList();
        try {
            myStmt = Database.getConnection().createStatement();
            myRs = myStmt.executeQuery("SELECT * FROM type_operation ORDER BY id");

            while (myRs.next()) {
                Type_operation addTypeModel = new Type_operation();

                addTypeModel.setId(myRs.getInt("id"));
                addTypeModel.setName(myRs.getString("name"));
                addTypeModel.setUnit(myRs.getString("Measurament"));
                addTypeModel.setTotal_products(myRs.getInt("Total_products"));

                Sotuvchi_S.add(addTypeModel);
            }
            TypeOperationTable.setItems(Sotuvchi_S);


        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        } finally {
            //  DaoUtils.close(myStmt);
        }

    }
    public void setUpdate(){
        try {
            TypeOperationTable.setOnMouseClicked(event -> {
                Type_operation apple2 = (Type_operation) TypeOperationTable.getItems().get(TypeOperationTable.getSelectionModel().getSelectedIndex());
                try {
                    TId.setText(apple2.getName());


                } catch (Exception exc) {
                }
            });
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        }
    }
    public  void SaveTypeOperationTable() throws Exception {

        try {


            String narx = TNarx.getText();
            String miqdor = TMiqdor.getText();
            String id = TId.getText().trim();

            if(!id.isEmpty()) {

                if (miqdor.isEmpty()) {


                    String apple1 = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("O'zgartirish");
                    alert.setHeaderText(null);
                    alert.setContentText("Tur malumotlarini o'zgartirasizmi");

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent())
                        if (result.get() == ButtonType.OK) {

                            PreparedStatement preparedStatement = null;

                            preparedStatement = Database.getConnection().prepareStatement("UPDATE maxsulotlar set item_cost=item_sale_cost+(item_sale_cost*(?/100)) where item_type='" + id + "' ");
                            preparedStatement.setString(1, narx);
                            preparedStatement.executeUpdate();

                            preparedStatement = Database.getConnection().prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Turdagi maxsulotlar malumotlari ozgartirildi' ,?)");
                            preparedStatement.setString(1, apple1);

                            preparedStatement.executeUpdate();
                            JOptionPane.showMessageDialog(null, " Ma'lumotlar o'zgartirildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);

                        }
                } else if (narx.isEmpty()) {
                    String apple1 = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("O'zgartirish");
                    alert.setHeaderText(null);
                    alert.setContentText("Tur malumotlarini o'zgartirasizmi");

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent())
                        if (result.get() == ButtonType.OK) {

                            PreparedStatement preparedStatement = null;

                            preparedStatement = Database.getConnection().prepareStatement("UPDATE maxsulotlar set item_quantity=? where item_type='" + id + "' ");
                            preparedStatement.setString(1, miqdor);
                            preparedStatement.executeUpdate();

                            preparedStatement = Database.getConnection().prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Turdagi maxsulotlar malumotlari ozgartirildi' ,?)");
                            preparedStatement.setString(1, apple1);

                            preparedStatement.executeUpdate();
                            JOptionPane.showMessageDialog(null, " Ma'lumotlar o'zgartirildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);

                        }

                }
                else {
                    String apple1 = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("O'zgartirish");
                    alert.setHeaderText(null);
                    alert.setContentText("Tur malumotlarini o'zgartirasizmi");

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent())
                        if (result.get() == ButtonType.OK) {

                            PreparedStatement preparedStatement = null;

                            preparedStatement = Database.getConnection().prepareStatement("UPDATE maxsulotlar set item_cost=item_sale_cost+(item_sale_cost*(?/100)),item_quantity=?  where item_type='" + id + "' ");
                            preparedStatement.setString(1, narx);
                            preparedStatement.setString(2, miqdor);
                            preparedStatement.executeUpdate();

                            preparedStatement = Database.getConnection().prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Turdagi maxsulotlar malumotlari ozgartirildi' ,?)");
                            preparedStatement.setString(1, apple1);

                            preparedStatement.executeUpdate();
                            JOptionPane.showMessageDialog(null, " Ma'lumotlar o'zgartirildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);

                        }

                }
            }

        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, " Hamma malumotlarni kiriting"+e, "Xatolik", JOptionPane.ERROR_MESSAGE);

        }

    }
}
