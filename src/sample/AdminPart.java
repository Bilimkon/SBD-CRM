package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.DAO.*;
import sample.productTableView.*;
import sample.Utils.BarCodeService;
import sample.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

import static sample.Utils.BarCodeService.numbGen;


public class AdminPart implements Initializable {

    // String name12  =  LoginController.currentUser.getFirstName();
    @FXML
    private TextField AdminTextSearch;
    @FXML
    private TableView<product> AdminTable;
    //O'zgarmalar Maydoni textFiledlari
    @FXML
    private TextField AdminTextBarcode;
    @FXML
    private TextField AdminTextNomi;
    @FXML
    private TextField AdminTextId;
    @FXML
    private ComboBox<String> ComboTypeList;
    @FXML
    private ComboBox<String> ComboBoxUnit;
    @FXML
    private TextField AdminTextMiqdori;
    @FXML
    private TextField AdminTextNarxi;
    @FXML
    private TextField AdminTextSale;
    @FXML
    private DatePicker AdminTextDan;
    @FXML
    private DatePicker AdminTextGacha;
    @FXML
    private ComboBox<String> comboBoxSuplier;
    //Savdo table -------------------------------
    @FXML
    private TableView<HistoryTable> TarixTable;
    @FXML
    private TableView<Sotuvchi> SotuvchiTable;
    //SavdoRateTable------------------------------
    @FXML
    private TableView<SoldRate> SavdoRateTable1;
    // Hisoblar oynasi uchun qilinadigan barcha variablar shu yerda initialize qilina
    @FXML
    private TableView<CollapsedHistory> Xtable;
    @FXML
    Label LabelTotalCost;
    @FXML
    Label LabelTotalCredit;
    @FXML
    Label LabelTotalCard;
    @FXML
    private DatePicker XDateValue1;
    @FXML
    private DatePicker XDateValue2;
    @FXML
    private ComboBox<String> Xcombo1;
    @FXML
    private CheckBox Xcheckbox1;
    @FXML
    private CheckBox CheckQarzbox;

    @FXML
    private AreaChart XLineChart;
    // @FXML
    //private BarChart<String, Double> XisobTabBarChart;
    // -----------------------------------------------
    //AllTotal1
    //---------------------------------------------------------------------------------------------

    // Sotuvchi page
    @FXML
    TextField firstNameSo;
    @FXML
    TextField lastNameS;
    @FXML
    TextField salaryS;
    @FXML
    TextField text_id;
    @FXML
    TextField passwordS;
    @FXML
    TextField text_username;
    @FXML
    DatePicker birthDateS;
    @FXML
    TextField jobTieleS;
    @FXML
    Label LabalTotalNaqtCost;

    /*
     *  Tarix table
     */
    @FXML
    DatePicker TarixDate1;
    @FXML
    DatePicker TarixDate2;

    /**
     * btnchiqish()
     */

    @FXML
    private PieChart SavdoReytingPieChart;
    ObservableList<PieChart.Data> piechartdata;
    @FXML
    private BarChart<String, Integer> KunlikReytingBarChart;
    ArrayList<Integer> cell = new ArrayList<Integer>();
    ArrayList<String> name = new ArrayList<String>();
    private Connection myConn;
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    ProductDao productDao = new ProductDao();
    UtilsDao utilsDao = new UtilsDao();
    SellerDao sellerDao = new SellerDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeProductTab();
        initializeHistoryTab();
        Xisoblartab();
        SotuvchiTab();
        setOzgaartirishMaxsulot();
        ComboBoxUnit.getItems().addAll("Dona", "Kg");
        try {
            AddTypeComboboxAction();
            AddSuplierComboboxAction();
            productTable();
            product_sold_rate();
            SavdoRateTable();
            SoldRateTab();
            tarixtable();
            SotuvchiTable();
            HisoblarTable();
            ozgartirishStuvchiAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeProductTab() {
        TableColumn<product, String> id = new TableColumn<>("Id");
        TableColumn<product, String> unit = new TableColumn<>("Birlik");
        TableColumn<product, String> barcode = new TableColumn<>("Barcode");
        TableColumn<product, String> name = new TableColumn<>("Nomi");
        TableColumn<product, String> type = new TableColumn<>("Turi");
        TableColumn<product, String> cost_o = new TableColumn<>("Tannarx");
        TableColumn<product, String> cost = new TableColumn<>("Narx");
        TableColumn<product, String> quantity = new TableColumn<>("Miqdori");
        TableColumn<product, String> date_c = new TableColumn<>("...dan");
        TableColumn<product, String> date_o = new TableColumn<product, String>("...gacha");
        TableColumn<product, String> suplier = new TableColumn<product, String>("Taminotchi");
        TableColumn<product, String> date = new TableColumn<product, String>("Sana");
        TableColumn<product, String> total_cost_o = new TableColumn<product, String>("Umumiy Tannarx");
        TableColumn<product, String> total_cost = new TableColumn<product, String>("Umimy sotuv summa");
        AdminTable.getColumns().addAll(id, unit, barcode, name, type, cost_o, cost, quantity, date_c, date_o, suplier, total_cost_o, total_cost, date);
        id.setCellValueFactory(new PropertyValueFactory<product, String>("id"));
        unit.setCellValueFactory(new PropertyValueFactory<product, String>("unit"));
        barcode.setCellValueFactory(new PropertyValueFactory<product, String>("barcode"));
        name.setCellValueFactory(new PropertyValueFactory<product, String>("name"));
        type.setCellValueFactory(new PropertyValueFactory<product, String>("type"));
        cost_o.setCellValueFactory(new PropertyValueFactory<product, String>("cost_o"));
        cost.setCellValueFactory(new PropertyValueFactory<product, String>("cost"));
        quantity.setCellValueFactory(new PropertyValueFactory<product, String>("quantity"));
        date_c.setCellValueFactory(new PropertyValueFactory<product, String>("date_c"));
        date_o.setCellValueFactory(new PropertyValueFactory<product, String>("date_o"));
        suplier.setCellValueFactory(new PropertyValueFactory<product, String>("suplier"));
        date.setCellValueFactory(new PropertyValueFactory<product, String>("date"));
        total_cost_o.setCellValueFactory(new PropertyValueFactory<product, String>("total_cost_o"));
        total_cost.setCellValueFactory(new PropertyValueFactory<product, String>("total_cost"));
    }

    private void initializeHistoryTab() {
        TableColumn<HistoryTable, Integer> id = new TableColumn<HistoryTable, Integer>(" Id");
        TableColumn<HistoryTable, Integer> username = new TableColumn<>(" Ism");
        TableColumn<HistoryTable, String> customer = new TableColumn<>(" Mijoz");
        TableColumn<HistoryTable, String> barcode = new TableColumn<HistoryTable, String>(" Barcode");
        TableColumn<HistoryTable, Integer> product_name = new TableColumn<>(" Nomi");
        TableColumn<HistoryTable, String> type_name = new TableColumn<HistoryTable, String>(" Turi");
        TableColumn<HistoryTable, Double> total_cost = new TableColumn<HistoryTable, Double>(" Umumiy narxi");
        TableColumn<HistoryTable, Double> cost = new TableColumn<>(" Narxi");
        TableColumn<HistoryTable, Double> quantity = new TableColumn<>(" Miqdori");
        TableColumn<HistoryTable, Double> date = new TableColumn<HistoryTable, Double>(" Sana");
        TableColumn<HistoryTable, Double> sell_action_id = new TableColumn<>("S_id");


        TarixTable.getColumns().addAll(id, username, customer, barcode, product_name, type_name, total_cost, cost, quantity, date, sell_action_id);
        id.setCellValueFactory(new PropertyValueFactory<HistoryTable, Integer>("id"));
        username.setCellValueFactory(new PropertyValueFactory<HistoryTable, Integer>("username"));
        customer.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("customer"));
        barcode.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("barcode"));
        product_name.setCellValueFactory(new PropertyValueFactory<HistoryTable, Integer>("product_name"));
        type_name.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("type_name"));
        total_cost.setCellValueFactory(new PropertyValueFactory<HistoryTable, Double>("total_cost"));
        cost.setCellValueFactory(new PropertyValueFactory<HistoryTable, Double>("cost"));
        quantity.setCellValueFactory(new PropertyValueFactory<HistoryTable, Double>("quantity"));
        date.setCellValueFactory(new PropertyValueFactory<HistoryTable, Double>("date"));
        sell_action_id.setCellValueFactory(new PropertyValueFactory<HistoryTable, Double>("sell_action_id"));
    }

    private void SotuvchiTab() {
        TableColumn<Sotuvchi, String> id = new TableColumn<Sotuvchi, String>("Id");
        TableColumn<Sotuvchi, String> username = new TableColumn<Sotuvchi, String>("Username");
        TableColumn<Sotuvchi, String> firstname = new TableColumn<Sotuvchi, String>("Ism");
        TableColumn<Sotuvchi, String> lastname = new TableColumn<Sotuvchi, String>("Familiya");
        TableColumn<Sotuvchi, String> admin = new TableColumn<Sotuvchi, String>("Admin");
        TableColumn<Sotuvchi, String> salary = new TableColumn<Sotuvchi, String>("Maosh");
        TableColumn<Sotuvchi, String> password = new TableColumn<>("Password");
        TableColumn<Sotuvchi, String> birthdate = new TableColumn<Sotuvchi, String>("Tug'ulgan sana");
        TableColumn<Sotuvchi, String> date_cr = new TableColumn<Sotuvchi, String>("Sana");

        SotuvchiTable.getColumns().addAll(id, username, firstname, lastname, admin, salary, password, birthdate, date_cr);

        id.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("id"));
        username.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("username"));
        firstname.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("firstname"));
        lastname.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("lastname"));
        admin.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("admin"));
        salary.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("salary"));
        password.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("password"));
        birthdate.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("birthdate"));
        date_cr.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("date_cr"));

    }

    private void Xisoblartab() {

        TableColumn<CollapsedHistory, String> id = new TableColumn<>("id");
        TableColumn<CollapsedHistory, String> seller = new TableColumn<>("Sotuvchi");
        TableColumn<CollapsedHistory, String> customer = new TableColumn<>("Xaridor");
        TableColumn<CollapsedHistory, String> cost_paid = new TableColumn<>("to'langan summa");
        TableColumn<CollapsedHistory, String> total_cost = new TableColumn<>("Umumiy summa");
        TableColumn<CollapsedHistory, String> sale = new TableColumn<>("Chegirma");
        TableColumn<CollapsedHistory, String> credit = new TableColumn<>("Qarz");
        TableColumn<CollapsedHistory, String> card = new TableColumn<>("Karta summa");
        TableColumn<CollapsedHistory, String> cash = new TableColumn<>("Naqt pul");
        TableColumn<CollapsedHistory, String> comment = new TableColumn<>("Izoh");
        TableColumn<CollapsedHistory, String> date = new TableColumn<>("Sana");


        Xtable.getColumns().addAll(id, seller, customer, cost_paid, total_cost, sale, credit, card, cash, comment, date);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        seller.setCellValueFactory(new PropertyValueFactory<>("seller"));
        customer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        cost_paid.setCellValueFactory(new PropertyValueFactory<>("cost_paid"));
        total_cost.setCellValueFactory(new PropertyValueFactory<>("total_cost"));
        sale.setCellValueFactory(new PropertyValueFactory<>("sale"));
        credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        card.setCellValueFactory(new PropertyValueFactory<>("card"));
        cash.setCellValueFactory(new PropertyValueFactory<>("cash"));
        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
    }


    private void SoldRateTab() {
        TableColumn<SoldRate, Integer> nameSR = new TableColumn<>("Nomi");
        TableColumn<SoldRate, String> barcodeSR = new TableColumn<SoldRate, String>("Barcode");
        TableColumn<SoldRate, String> tCostSR = new TableColumn<>("Umumiy summa");
        TableColumn<SoldRate, Float> soldQuantirySR = new TableColumn<SoldRate, Float>("Sotilgan soni");

        SavdoRateTable1.getColumns().addAll(nameSR, barcodeSR, tCostSR, soldQuantirySR);
        nameSR.setCellValueFactory(new PropertyValueFactory<SoldRate, Integer>("nameSR"));
        barcodeSR.setCellValueFactory(new PropertyValueFactory<SoldRate, String>("barcodeSR"));
        tCostSR.setCellValueFactory(new PropertyValueFactory<SoldRate, String>("totalSR"));
        soldQuantirySR.setCellValueFactory(new PropertyValueFactory<SoldRate, Float>("soldQuantitySR"));
    }


    public AdminPart() throws Exception {
        try {
            myConn = Database.getConnection();
            ProductDao productDao = new ProductDao(myConn);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Xatolik" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void btnIzlashAction() {
        String textIzlash2 = AdminTextSearch.getText();

    }

    private void productTable() throws SQLException {
        ObservableList<product> products = FXCollections.observableArrayList();
        try {
            statement = myConn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM product_v ORDER BY id");
            while (resultSet.next()) {
                product product = new product();
                product.setId(resultSet.getString("id"));
                product.setUnit(resultSet.getString("unit"));
                product.setBarcode(resultSet.getString("barcode"));
                product.setName(resultSet.getString("name"));
                product.setType(resultSet.getString("type"));
                product.setCost_o(resultSet.getString("cost_o"));
                product.setCost(resultSet.getString("cost"));
                product.setQuantity(resultSet.getString("quantity"));
                product.setDate_c(resultSet.getString("date_c"));
                product.setDate_o(resultSet.getString("date_o"));
                product.setSuplier(resultSet.getString("suplier"));
                product.setTotal_cost_o(resultSet.getString("total_cost_o"));
                product.setTotal_cost(resultSet.getString("total_cost"));
                product.setDate(resultSet.getString("date_cr"));
                products.addAll(product);
            }
            AdminTable.setItems(products);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement.close();
            resultSet.close();
        }
    }

    @FXML
    public void maxsulotQoshish() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText("Yangi maxsulot qo'shasizmi?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                try {
                    String type = ComboTypeList.getSelectionModel().getSelectedItem().toString();
                    String Suplier = comboBoxSuplier.getSelectionModel().getSelectedItem().toString();
                    String barcode1 = AdminTextBarcode.getText();
                    String nomi1 = AdminTextNomi.getText();
                    String unit = ComboBoxUnit.getSelectionModel().getSelectedItem().toString();
                    String Miqdori1 = AdminTextMiqdori.getText();
                    String narxi1 = AdminTextNarxi.getText();
                    String saleNarxi = AdminTextSale.getText();
                    String dan1 = AdminTextDan.getValue().toString();
                    String gacha1 = AdminTextGacha.getValue().toString();
                    productDao.addProduct(barcode1, nomi1, type, saleNarxi, narxi1, unit, Miqdori1, dan1, gacha1, Suplier);
                    AdminTextBarcode.setText("");
                    AdminTextNomi.setText("");
                    AdminTextMiqdori.setText("");
                    AdminTextNarxi.setText("");
                    AdminTextSale.setText("");
                    productTable();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    Utils.ErrorAlert("Xatolik", "Eslatma", "Hamma joylarni to'ldiring" + exc);
                }
            }
    }


    private void setOzgaartirishMaxsulot() {
        try {
            AdminTable.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
                product apple2 = AdminTable.getSelectionModel().getSelectedItem();
                try {
                    AdminTextBarcode.setText(apple2.getBarcode());
                    AdminTextNomi.setText(apple2.getName());
                    AdminTextNarxi.setText(apple2.getCost());
                    AdminTextSale.setText(apple2.getCost_o());
                    //AdminTextDan.setValue(LocalDate.parse(apple2.getDate_c()));
                    //AdminTextGacha.setValue(LocalDate.parse(apple2.getDate_o()));
                    AdminTextMiqdori.setText(apple2.getQuantity());
                    AdminTextId.setText(String.valueOf(apple2.getId()));
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            });
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }


    /**
     * Product update
     */
    public void btnOzgartirishAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText("Maxsulot malumotlarini o'zgartirasizmi ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                try {
                    String id = AdminTextId.getText();
                    String barcode1 = AdminTextBarcode.getText();
                    String nomi1 = AdminTextNomi.getText();
                    String Miqdori1 = AdminTextMiqdori.getText();
                    String narxi1 = AdminTextNarxi.getText();
                    String saleNarxi1 = AdminTextSale.getText();
                    String dan1 = AdminTextDan.getValue().toString();
                    String gacha1 = AdminTextDan.getValue().toString();
                    productDao.updateProduct(barcode1, nomi1, saleNarxi1, narxi1, Miqdori1, dan1, gacha1, id);
                    Utils.InfoAlert("Bildirgi", "Muoffaqqiyatli", "Maxsulot malumotlari o'zgartirildi!");
                    productTable();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
    }

    public void btnOchirishAction() throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText("Maxsulotni rostdan ham o'chirasizmi ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                String id = AdminTextId.getText();
                try {
                    productDao.deleteProduct(id);
                    Utils.InfoAlert("Bildirgi", "Muoffaqqiyatli", "Maxsulot o'chirildi!");
                    productTable();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
    }


    public void XisoblarSaralashBtn() {
        HisoblarTable();
    }

    private void HisoblarTable() throws NullPointerException {

        LocalDate date1 = XDateValue1.getValue();
        LocalDate date2 = XDateValue2.getValue();


        PreparedStatement statement;
        ResultSet myRs = null;
        ResultSet myRsTotal = null;
        Statement myStmt = null;
        //List to add items
        ObservableList<CollapsedHistory> HappleC = FXCollections.observableArrayList();
        try {
            if (Xcheckbox1.isSelected()) {

                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery("SELECT * FROM actionHistory_v WHERE card > 0");
                preparedStatement = myConn.prepareStatement("SELECT sum(c.total_cost) umumiy_summa,sum(cash) umumiyCashAmount, sum(c.card) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM actionHistory_v c\n" +
                        "WHERE card > 0");

                if (date1 != null && date2 != null) {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    String sdate1 = df.format(Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    String sdate2 = df.format(Date.from(date2.atStartOfDay(ZoneId.systemDefault()).toInstant()));


                    preparedStatement = myConn.prepareStatement("SELECT * FROM actionHistory_v\n" +
                            "WHERE card>0 AND substr(date,7,10)\n" +
                            "BETWEEN ? AND ?");
                    preparedStatement.setString(1, sdate1);
                    preparedStatement.setString(2, sdate2);

                    myRs = preparedStatement.executeQuery();

                    preparedStatement = myConn.prepareStatement("SELECT sum(c.total_cost) umumiy_summa,sum(cash) umumiyCashAmount, sum(c.card) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM actionHistory_v c\n" +
                            "WHERE card >0 AND substr(date,7,10)\n" +
                            "BETWEEN ? AND ?");
                    preparedStatement.setString(1, sdate1);
                    preparedStatement.setString(2, sdate2);
                    myRsTotal = preparedStatement.executeQuery();
                }


                myRsTotal = preparedStatement.executeQuery();
            } else if (CheckQarzbox.isSelected()) {

                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery("SELECT * FROM actionHistory_v WHERE credit>0");

                preparedStatement = myConn.prepareStatement("SELECT sum(c.total_cost) umumiy_summa,sum(cash) umumiyCashAmount, sum(c.card) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM actionHistory_v c\n" +
                        "WHERE credit>0");
                if (date1 != null && date2 != null) {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    String sdate1 = df.format(Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    String sdate2 = df.format(Date.from(date2.atStartOfDay(ZoneId.systemDefault()).toInstant()));


                    preparedStatement = myConn.prepareStatement("SELECT * FROM actionHistory_v\n" +
                            "WHERE credit>0 AND substr(date,7,10)\n" +
                            "BETWEEN ? AND ? ");
                    preparedStatement.setString(1, sdate1);
                    preparedStatement.setString(2, sdate2);

                    myRs = preparedStatement.executeQuery();

                    preparedStatement = myConn.prepareStatement("SELECT sum(c.total_cost) umumiy_summa,sum(cash) umumiyCashAmount, sum(c.card) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM actionHistory_v c\n" +
                            "WHERE credit >0 AND substr(date,7,10)\n" +
                            "BETWEEN ? AND ?");
                    preparedStatement.setString(1, sdate1);
                    preparedStatement.setString(2, sdate2);
                    myRsTotal = preparedStatement.executeQuery();
                }
                myRsTotal = preparedStatement.executeQuery();


            } else if (date1 != null && date2 != null) {
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String sdate1 = df.format(Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                String sdate2 = df.format(Date.from(date2.atStartOfDay(ZoneId.systemDefault()).toInstant()));


                preparedStatement = myConn.prepareStatement("SELECT * FROM actionHistory_v\n" +
                        "WHERE substr(date,7,10)\n" +
                        "BETWEEN ? AND ? ");
                preparedStatement.setString(1, sdate1);
                preparedStatement.setString(2, sdate2);

                myRs = preparedStatement.executeQuery();

                preparedStatement = myConn.prepareStatement("SELECT sum(c.total_cost) umumiy_summa, sum(cash) umumiyCashAmount, sum(c.card) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM actionHistory_v c\n" +
                        "WHERE substr(date,7,10)\n" +
                        "BETWEEN ? AND ?");
                preparedStatement.setString(1, sdate1);
                preparedStatement.setString(2, sdate2);
                myRsTotal = preparedStatement.executeQuery();

                /**
                 *   Getting data from database to XYChart
                 */
                ResultSet myGraphRestutSet = null;

                preparedStatement = myConn.prepareStatement("SELECT * FROM actionHistory_v\n" +
                        "WHERE substr(date,7,10)\n" +
                        "BETWEEN ? AND ?");
                preparedStatement.setString(1, sdate1);
                preparedStatement.setString(2, sdate2);

                myGraphRestutSet = preparedStatement.executeQuery();


                XYChart.Series<String, Integer> series = new XYChart.Series<>();

                while (myGraphRestutSet.next()) {
                    series.getData().add(new XYChart.Data<>(myGraphRestutSet.getString("date"), myGraphRestutSet.getInt("Total_cost")));
                }
                XLineChart.getData().add(series);

                /*
                 * End of XYChart
                 *
                 */


            } else {
                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery("SELECT * FROM (\n" +
                        "                SELECT * FROM actionHistory_v ORDER BY id DESC LIMIT 300\n" +
                        "              ) sub\n" +
                        "ORDER BY id ASC ");

                preparedStatement = myConn.prepareStatement("SELECT sum(c.total_cost) umumiy_summa, sum(cash) umumiyCashAmount, sum(c.card) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM actionHistory_v c");

                myRsTotal = preparedStatement.executeQuery();
            }

            while (myRs.next()) {
                CollapsedHistory collapsedHistory = new CollapsedHistory();
                collapsedHistory.setId(myRs.getString("id"));
                collapsedHistory.setSeller(myRs.getString("seller"));
                collapsedHistory.setCustomer(myRs.getString("customer"));
                collapsedHistory.setCost_paid(myRs.getString("cost_paid"));
                collapsedHistory.setTotal_cost(myRs.getString("total_cost"));
                collapsedHistory.setSale(myRs.getString("sale"));
                collapsedHistory.setCredit(myRs.getString("credit"));
                collapsedHistory.setCard(myRs.getString("card"));
                collapsedHistory.setCash(myRs.getString("cash"));
                collapsedHistory.setComment(myRs.getString("comment"));
                collapsedHistory.setDate(myRs.getString("date"));
                HappleC.add(collapsedHistory);
            }
            if (myRsTotal.next()) {
                LabelTotalCost.setText(new BigDecimal(myRsTotal.getDouble("umumiy_summa")).toPlainString() + ".00 sum");
                LabelTotalCredit.setText(new BigDecimal(myRsTotal.getDouble("umumiyCredit_amount")).toPlainString() + ".00 sum");
                LabelTotalCard.setText(new BigDecimal(myRsTotal.getDouble("umumiyCard_amount")).toPlainString() + ".00 sum");
                LabalTotalNaqtCost.setText(new BigDecimal(myRsTotal.getDouble("umumiyCashAmount")).toPlainString() + ".00 sum");

            }
            Xtable.setItems(HappleC);

        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }


    @FXML
    private void tarixtable() {

        LocalDate DeptData1 = TarixDate1.getValue();
        LocalDate DeptData2 = TarixDate2.getValue();

        ObservableList<HistoryTable> appleQ = FXCollections.observableArrayList();
        try {
            if (DeptData1 != null && DeptData2 != null) {
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String Qdate1 = df.format(Date.from(DeptData1.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                String Qdate2 = df.format(Date.from(DeptData2.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                preparedStatement = myConn.prepareStatement("SELECT * FROM history_v\n" +
                        "WHERE substr(date,7,10)\n" +
                        "BETWEEN ? AND ?");
                preparedStatement.setString(1, Qdate1);
                preparedStatement.setString(2, Qdate2);
                resultSet = preparedStatement.executeQuery();
            } else {
                statement = myConn.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM (\n" +
                        "    SELECT * FROM history_v ORDER BY product_id DESC LIMIT 300\n" +
                        ") sub\n" +
                        "ORDER BY product_id ASC");
            }
            while (resultSet.next()) {
                HistoryTable qarzTable = new HistoryTable();

                qarzTable.setId(resultSet.getString("product_id"));
                qarzTable.setUsername(resultSet.getString("username"));
                qarzTable.setCustomer(resultSet.getString("customer"));
                qarzTable.setBarcode(resultSet.getString("barcode"));
                qarzTable.setProduct_name(resultSet.getString("product_name"));
                qarzTable.setType_name(resultSet.getString("type_name"));
                qarzTable.setTotal_cost(resultSet.getString("total_cost"));
                qarzTable.setCost(resultSet.getString("cost"));
                qarzTable.setQuantity(resultSet.getString("quantity"));
                qarzTable.setDate(resultSet.getString("date"));
                qarzTable.setSell_action_id(resultSet.getString("sell_action_id"));
                appleQ.add(qarzTable);
            }
            TarixTable.setItems(appleQ);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }


    private void SotuvchiTable() throws Exception {
        ObservableList<Sotuvchi> Sotuvchi_S = FXCollections.observableArrayList();
        try {
            statement = myConn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM seller ORDER BY id");

            while (resultSet.next()) {
                Sotuvchi sotuvchi = new Sotuvchi();

                sotuvchi.setId(resultSet.getString("id"));
                sotuvchi.setUsername(resultSet.getString("username"));
                sotuvchi.setFirstname(resultSet.getString("firstname"));
                sotuvchi.setLastname(resultSet.getString("lastname"));
                sotuvchi.setAdmin(resultSet.getString("admin"));
                sotuvchi.setSalary(resultSet.getString("salary"));
                sotuvchi.setPassword(resultSet.getString("password"));
                sotuvchi.setBirthdate(resultSet.getString("birthdate"));
                sotuvchi.setDate_cr(resultSet.getString("date_cr"));

                Sotuvchi_S.add(sotuvchi);
            }
            SotuvchiTable.setItems(Sotuvchi_S);
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            statement.close();
            resultSet.close();
        }


    }

    private void product_sold_rate() throws SQLException {
        piechartdata = FXCollections.observableArrayList();
        try {
            statement = myConn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM soldRate");
            while (resultSet.next()) {
                piechartdata.add(new PieChart.Data(resultSet.getString("name"), resultSet.getInt("quantity")));
                cell.add(resultSet.getInt("quantity"));
                name.add(resultSet.getString("name"));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            resultSet.close();
            statement.close();
        }
    }


    public void Update() throws Exception {
        product_sold_rate();
        SavdoReytingPieChart.setData(piechartdata);
    }

    private void SavdoRateTable() throws SQLException {
        ObservableList<SoldRate> Soldrate = FXCollections.observableArrayList();
        try {
            statement = myConn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM soldRate ORDER BY quantity");
            while (resultSet.next()) {
                SoldRate soldRate = new SoldRate();
                soldRate.setNameSR(resultSet.getString("name"));
                soldRate.setBarcodeSR(resultSet.getString("barcode"));
                soldRate.setTotalSR(new BigDecimal(resultSet.getFloat("total_cost")).toPlainString());
                soldRate.setSoldQuantitySR(resultSet.getInt("quantity"));
                Soldrate.add(soldRate);
            }
            SavdoRateTable1.setItems(Soldrate);
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            statement.close();
            resultSet.close();
        }
    }

    public void clearTables() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText(" Rostdan ham barcha ma'lumotlarni o'chirmoqchimisiz ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                try {
                    utilsDao.clearAll();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
    }

    public void excelToDB() throws SQLException {
        try {
            FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
            dialog.setMode(FileDialog.LOAD);
            dialog.setVisible(true);
            String file1 = dialog.getFile();
            FileInputStream file = new FileInputStream(new File(dialog.getDirectory() + file1));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    //This will change all Cell Types to String
                    cell.setCellType(CellType.STRING);
                    switch (cell.getCellType()) {
                        case BOOLEAN:
                            System.out.println("boolean===>>>" + cell.getBooleanCellValue() + "\t");
                            break;
                        case NUMERIC:
                            break;
                        case STRING:
                            cell.getStringCellValue();
                            break;
                    }
                }
                String barcode = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                String type = row.getCell(2).getStringCellValue();
                String type_id = row.getCell(3).getStringCellValue();
                String cost = row.getCell(4).getStringCellValue();
                String quantity = row.getCell(5).getStringCellValue();
                String cost_o = row.getCell(6).getStringCellValue();
                String date_c = row.getCell(7).getStringCellValue();
                String date_o = row.getCell(8).getStringCellValue();
                String cr_by = row.getCell(9).getStringCellValue();
                String date_cr = row.getCell(10).getStringCellValue();
                String suplier_id = row.getCell(11).getStringCellValue();
                String unit = row.getCell(12).getStringCellValue();

                utilsDao.InsertRowInDB(barcode, name, type, type_id, cost, quantity, cost_o, date_c, date_o, cr_by, date_cr, suplier_id, unit);
                productTable();
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ADDSeller() throws Exception {
        sotuvchiQoshish();
        Update();
    }

    private void sotuvchiQoshish() throws SQLException {
        String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText(" Yangi sotuvchini qo'shasizmi ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                try {
                    String firstname = firstNameSo.getText().toString();
                    String lastname = lastNameS.getText().toString();
                    String admin = jobTieleS.getText().toString();
                    String salary = salaryS.getText().toString();
                    String password = passwordS.getText().toString();
                    String username = text_username.getText().toString();
                    String birthDate = birthDateS.getValue().toString();

                    sellerDao.addSeller(firstname,lastname,admin,salary,password,birthDate,username);
                    Utils.InfoAlert("Sotuvchi", "Muvvoffaqiyatli", "Yangi sotuvchi qo'shildi");
                    SotuvchiTable();
                } catch (Exception exc) {
                    exc.printStackTrace();
                } finally {
                    preparedStatement.close();
                }
            }

    }

    private void ozgartirishStuvchiAction() {
        try {
            SotuvchiTable.setOnMouseClicked(event -> {
                Sotuvchi sotuvchi = SotuvchiTable.getItems().get(SotuvchiTable.getSelectionModel().getSelectedIndex());
                try {
                    firstNameSo.setText(sotuvchi.getFirstname());
                    lastNameS.setText(sotuvchi.getLastname());
                    salaryS.setText(sotuvchi.getSalary());
                    jobTieleS.setText(sotuvchi.getAdmin());
                    text_id.setText(sotuvchi.getId());
                    passwordS.setText(sotuvchi.getPassword());
                    birthDateS.setValue(LocalDate.parse(sotuvchi.getBirthdate()));
                    text_username.setText(sotuvchi.getUsername());
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            });
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void ozgartirishSotuvchi() throws SQLException {
        String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText("Sotuvchi malumotlarini o'zgartirasizmi ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                try {
                    String firstname = firstNameSo.getText().toString();
                    String lastname = lastNameS.getText().toString();
                    String admin = jobTieleS.getText().toString();
                    String salary = salaryS.getText().toString();
                    String id = text_id.getText().toString();
                    String password = passwordS.getText().toString();
                    String username = text_username.getText().toString();
                    String birthdate = birthDateS.getValue().toString();
                    sellerDao.updateSeller(firstname,lastname,admin,salary,password,birthdate,username,id);
                    Utils.InfoAlert("Bildirgi", "Sotuvchi", "Sotuvchi  malumotlari ozgartirildi");
                    SotuvchiTable();
                } catch (Exception exc) {
                    exc.printStackTrace();
                } finally {
                    preparedStatement.close();
                }
            }
    }

    public void UpdateSeller() throws Exception {
        ozgartirishSotuvchi();
        Update();
    }

    public void deleteSeller() throws Exception {
        sotuvchiDelete();
        Update();
    }

    private void sotuvchiDelete() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText(" Sotuvchini rostdan ham o'chirasizmi ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                String id = text_id.getText();
                try {
                    sellerDao.deleteSeller(id);
                    SotuvchiTable();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
    }

    private void SotuvchiCombobox() throws SQLException {
        try {
            statement = myConn.createStatement();
            resultSet = statement.executeQuery("SELECT first_name FROM sbd_market.sotuvchi");
            while (resultSet.next()) {  // loop
                // Now add the comboBox addAll statement
                Xcombo1.getItems().addAll(resultSet.getString("first_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement.close();
            resultSet.close();
        }
    }

    public void AddTypeComboboxAction() throws SQLException {
        try {
            statement = myConn.createStatement();
            resultSet = statement.executeQuery("SELECT Name FROM main.type");
            while (resultSet.next()) {  // loop
                // Now add the comboBox addAll statement
                ComboTypeList.getItems().addAll(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement.close();
            resultSet.close();
        }
    }

    public void AddSuplierComboboxAction() throws SQLException {
        try {
            statement = myConn.createStatement();
            resultSet = statement.executeQuery("SELECT firstName FROM main.suplier");
            while (resultSet.next()) {  // loop
                // Now add the comboBox addAll statement
                comboBoxSuplier.getItems().addAll(resultSet.getString("firstname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement.close();
            resultSet.close();
        }
    }


    public void dbToExcell() throws SQLException {


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Excel fayl yaratish ");
        alert.setHeaderText(null);
        alert.setContentText("Ombordagi maxsulotlarni excell faylga ko'chirasizmi?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                printer printer = new printer();
                String filename = printer.ExcelFilePath() + "OmborXisobi.xls";
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.createSheet("Ombor Xisobi");
                FileOutputStream fileOut1 = null;
                try {
                    fileOut1 = new FileOutputStream(filename);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    workbook.write(fileOut1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fileOut1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Workbook writeWorkbook = new HSSFWorkbook();
                Sheet desSheet = writeWorkbook.createSheet("new sheet");
                Statement stmt = null;
                ResultSet rs = null;
                try {
                    String query = "SELECT * FROM product_v";
                    stmt = myConn.createStatement();
                    rs = stmt.executeQuery(query);
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnsNumber = rsmd.getColumnCount();
                    Row desRow1 = desSheet.createRow(0);
                    for (int col = 0; col < columnsNumber; col++) {
                        org.apache.poi.ss.usermodel.Cell newpath = desRow1.createCell(col);
                        newpath.setCellValue(rsmd.getColumnLabel(col + 1));
                    }
                    while (rs.next()) {
                        System.out.println("Row number" + rs.getRow());
                        Row desRow = desSheet.createRow(rs.getRow());
                        for (int col = 0; col < columnsNumber; col++) {
                            Cell newpath = desRow.createCell(col);
                            newpath.setCellValue(rs.getString(col + 1));
                        }
                        FileOutputStream fileOut = new FileOutputStream(printer.ExcelFilePath() + "Ombor.xls");
                        writeWorkbook.write(fileOut);
                        fileOut.close();
                    }
                    Utils.InfoAlert("Bildirgi", "Excell", "Excell fayl yaratildi");
                } catch (SQLException e) {
                    System.out.println("Failed to get data from database");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            }
    }

    public void XisoblarToExcel() throws SQLException {

        LocalDate date1 = XDateValue1.getValue();
        LocalDate date2 = XDateValue2.getValue();
        if (date1 != null && date2 != null) {

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String sdate1 = df.format(Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            String sdate2 = df.format(Date.from(date2.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            ResultSet rs = null;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Excel fayl yaratish ");
            alert.setHeaderText(null);
            alert.setContentText("Xisoblarni excel faylga ko'chirasizmi?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent())
                if (result.get() == ButtonType.OK) {

                    printer printer = new printer();


                    String filename = printer.ExcelFilePath() + "Xisoblar.xls";
                    HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet sheet = workbook.createSheet("Xisoblar");
                    FileOutputStream fileOut1 = null;
                    try {
                        fileOut1 = new FileOutputStream(filename);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Xatolik" + e, "Xatolik", JOptionPane.ERROR_MESSAGE);
                    }
                    try {
                        workbook.write(fileOut1);
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Xatolik" + e, "Xatolik", JOptionPane.ERROR_MESSAGE);
                    }
                    try {
                        fileOut1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Xatolik" + e, "Xatolik", JOptionPane.ERROR_MESSAGE);
                    }
                    try {
                        workbook.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Xatolik" + e, "Xatolik", JOptionPane.ERROR_MESSAGE);
                    }

                    Workbook writeWorkbook = new HSSFWorkbook();
                    Sheet desSheet = writeWorkbook.createSheet("new sheet");

                    try {

                        preparedStatement = myConn.prepareStatement("SELECT * FROM actionHistory_v\n" +
                                "WHERE substr(date,7,10)\n" +
                                "BETWEEN ? AND ?");
                        preparedStatement.setString(1, sdate1);
                        preparedStatement.setString(2, sdate2);
                        rs = preparedStatement.executeQuery();

                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();

                        Row desRow1 = desSheet.createRow(0);
                        for (int col = 0; col < columnsNumber; col++) {
                            org.apache.poi.ss.usermodel.Cell newpath = desRow1.createCell(col);
                            newpath.setCellValue(rsmd.getColumnLabel(col + 1));
                        }
                        while (rs.next()) {
                            System.out.println("Row number" + rs.getRow());
                            Row desRow = desSheet.createRow(rs.getRow());
                            for (int col = 0; col < columnsNumber; col++) {
                                Cell newpath = desRow.createCell(col);
                                newpath.setCellValue(rs.getString(col + 1));
                            }

                            FileOutputStream fileOut = new FileOutputStream(printer.ExcelFilePath() + "Xisoblar.xls");
                            writeWorkbook.write(fileOut);
                            fileOut.close();
                        }
                        JOptionPane.showMessageDialog(null, "Excel fayl yaratildi", "Ma'lumot", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException e) {
                        System.out.println("Failed to get data from database");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        preparedStatement.close();
                        if (rs != null) {
                            rs.close();
                        }
                        myConn.close();
                    }
                }
        }

    }

    public void PrinterAddAction() throws SQLException {

        TextInputDialog dialog = new TextInputDialog("Printer");
        dialog.setTitle("Printer nomini qo'shish");
        dialog.setHeaderText("Iltimos printer nomini qo'shing");
        dialog.setContentText("Printer nomi");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                 utilsDao.setPrinterName(result.get());
                JOptionPane.showMessageDialog(null, "Yangi printer nomi qo'shildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void SetExcelFolder() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = null;
        File dir = directoryChooser.showDialog(stage);
        System.out.println(dir.getAbsolutePath() + "\\");
        String path = dir.getAbsolutePath() + "\\";
        try {
           utilsDao.excellFolder(path);
            Utils.InfoAlert("Bildirgi", "Excell", "Excell joyi qo'shildi!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AddTypeAction() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Components/AddType.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Tur qo'shish");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.isAlwaysOnTop();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


   /* public void TypeOperation() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Components/Type_operations.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Tur ustida amallar");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.isAlwaysOnTop();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void GenerateBarcode() {
        if (AdminTextBarcode.getText().isEmpty()) {
            String testCode = String.valueOf(numbGen());
            BarCodeService serv = new BarCodeService();
            String parsedString = serv.parseInput(testCode);
            AdminTextBarcode.setText(parsedString);
            String barCodeString = serv.generateCode(parsedString);
        } else {
            String barcode = AdminTextBarcode.getText();
            int lenght = barcode.length();
            if (lenght == 13) {
                BarCodeService serv = new BarCodeService();
                String parsedString = serv.parseInput(barcode);
                AdminTextBarcode.setText(parsedString);
                String barCodeString = serv.generateCode(parsedString);
            } else {
                Utils.ErrorAlert("Eslatma", "Barcode", "Barcodega 13 honali son kiritilishi shart!");
            }
        }
    }
}





