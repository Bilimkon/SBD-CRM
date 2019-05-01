package sample;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import sample.DAO.Database;
import sample.DAO.ProductDao;
import sample.DAO.printer;
import sample.MaxsulotTableView.*;
import sample.Utils.BarCodeService;
import sample.Utils.Utils;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static sample.Utils.BarCodeService.numbGen;


public class AdminPart implements Initializable {

    // String name12  =  LoginController.currentUser.getFirstName();
    @FXML
    private TextField AdminTextSearch;
    @FXML
    private TableView AdminTable;
    //O'zgarmalar Maydoni textFiledlari
    @FXML
    private TextField AdminTextBarcode;
    @FXML
    private TextField AdminTextNomi;
    @FXML
    private TextField AdminTextId;
    @FXML
    private ComboBox ComboTypeList;
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
    private JFXButton btnLogOut;
    @FXML
    private Button btnAddProduct;
    @FXML
    ComboBox comboBoxSuplier;
    //Savdo table -------------------------------
    @FXML
    private TableView TarixTable;
    @FXML
    private TableView SotuvchiTable;
    //SavdoRateTable------------------------------
    @FXML
    private TableView SoldRateTable;
    @FXML
    private TableView SavdoRateTable1;
    // Hisoblar oynasi uchun qilinadigan barcha variablar shu yerda initialize qilina
    @FXML
    private TableView Xtable;
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
    private ComboBox Xcombo1;
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
    @FXML
    private TableView AllTotal1;
    //---------------------------------------------------------------------------------------------

    // Sotuvchi page
    @FXML
    TextField firstNameSo;
    @FXML
    TextField lastNameS;
    @FXML
    TextField salaryS;
    @FXML
    TextField emailS;
    @FXML
    TextField passwordS;
    @FXML
    TextField telephoneS;
    @FXML
    DatePicker birthDateS;
    @FXML
    TextField jobTieleS;
    //--------------------------------------------
    //Ombor hisobi  panel
    @FXML
    Label OUmumiySumma;
    @FXML
    Label OmborChegirmaLabel;
    @FXML
    Label LabalTotalNaqtCost;
    //---------------------------------------------
    //Qarzlar tab
    @FXML
    DatePicker QarzDate1;
    @FXML
    DatePicker QarzDate2;
    @FXML
    TextField CreditSumma;
    //----------------------------------------------

    /*
     *  Tarix table
     */
    @FXML
    DatePicker TarixDate1;
    @FXML
    DatePicker TarixDate2;
    @FXML
    TextField TarixId;
    @FXML
    TextField TarixName;
    @FXML
    TextField TarixType;
    @FXML
    TextField TarixMiqdori;
    @FXML
    TextField TarixSana;
    @FXML
    TextField TarixSumma;
    @FXML
    TextField TarixProductId;

    /*
     *  DDL change tab
     *
     */
    @FXML
    DatePicker DDL_date1;
    @FXML
    DatePicker DDL_date2;

    /**
     * btnchiqish()
     */
    @FXML
    Menu btnChiqish;

    @FXML
    private PieChart SavdoReytingPieChart;
    ObservableList<PieChart.Data> piechartdata;
    @FXML
    private BarChart<String, Integer> KunlikReytingBarChart;
    ArrayList<Integer> cell = new ArrayList<Integer>();
    ArrayList<String> name = new ArrayList<String>();
    @FXML
    private AreaChart<String, Integer> KunlikAreaChart;
    @FXML
    private BarChart<String, Integer> HisobBarchart;
    ArrayList<Integer> cell1 = new ArrayList<Integer>();
    ArrayList<String> name1 = new ArrayList<String>();
    private Connection myConn;
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    ProductDao productDao = new ProductDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeProductTab();
        initializeHistoryTab();
        setOzgaartirishMaxsulot();
        ComboBoxUnit.getItems().addAll("Dona", "Kg");
        try {
            AddTypeComboboxAction();
            productTable();
            product_sold_rate();
            SavdoRateTable();
            SoldRateTab();
            tarixtable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeProductTab() {
        TableColumn id = new TableColumn("Id");
        TableColumn unit = new TableColumn("Birlik");
        TableColumn barcode = new TableColumn("Barcode");
        TableColumn name = new TableColumn("Nomi");
        TableColumn type = new TableColumn("Turi");
        TableColumn cost_o = new TableColumn("Tannarx");
        TableColumn cost = new TableColumn("Narx");
        TableColumn quantity = new TableColumn("Miqdori");
        TableColumn date_c = new TableColumn("...dan");
        TableColumn date_o = new TableColumn("...gacha");
        TableColumn suplier = new TableColumn("Taminotchi");
        TableColumn date = new TableColumn("Sana");
        AdminTable.getColumns().addAll(id, unit, barcode, name, type, cost_o, cost, quantity, date_c, date_o, suplier, date);
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
    }

    private void initializeHistoryTab() {
        TableColumn id = new TableColumn(" Id");
        TableColumn username = new TableColumn(" Ism");
        TableColumn customer = new TableColumn(" Mijoz");
        TableColumn barcode = new TableColumn(" Barcode");
        TableColumn product_name = new TableColumn(" Nomi");
        TableColumn type_name = new TableColumn(" Turi");
        TableColumn total_cost = new TableColumn(" Umumiy narxi");
        TableColumn cost = new TableColumn(" Narxi");
        TableColumn quantity = new TableColumn(" Miqdori");
        TableColumn date = new TableColumn(" Sana");
        TableColumn sell_action_id = new TableColumn("S_id");


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
        TableColumn sotuvchi_id = new TableColumn("Id");
        TableColumn first_name_sotuvchi = new TableColumn("Ism");
        TableColumn last_name_sotuvchi = new TableColumn("Familiya");
        TableColumn tel_number_sotuvchi = new TableColumn("Tel number");
        TableColumn salary_sotuvchi = new TableColumn("Oylik");
        TableColumn date_sotuvchi = new TableColumn("Sana");
        TableColumn lavozim_sotuvchi = new TableColumn("Lavozim");
        TableColumn password_sotuvchi = new TableColumn("Parol");
        TableColumn email_sotuvchi = new TableColumn("Email");

        SotuvchiTable.getColumns().addAll(sotuvchi_id, first_name_sotuvchi, last_name_sotuvchi, tel_number_sotuvchi, salary_sotuvchi, date_sotuvchi, lavozim_sotuvchi, password_sotuvchi, email_sotuvchi);

        sotuvchi_id.setCellValueFactory(new PropertyValueFactory<Sotuvchi, Integer>("sotuvchi_id"));
        first_name_sotuvchi.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("firstName_sotuvchi"));
        last_name_sotuvchi.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("lastName_sotuvchi"));
        tel_number_sotuvchi.setCellValueFactory(new PropertyValueFactory<Sotuvchi, Float>("telNumber_sotuvchi"));
        salary_sotuvchi.setCellValueFactory(new PropertyValueFactory<Sotuvchi, Float>("salary_sotuvchi"));
        date_sotuvchi.setCellValueFactory(new PropertyValueFactory<Sotuvchi, Float>("date_sotuvchi"));
        lavozim_sotuvchi.setCellValueFactory(new PropertyValueFactory<Sotuvchi, String>("lavozim_sotuvchi"));
        password_sotuvchi.setCellValueFactory(new PropertyValueFactory<Sotuvchi, Double>("password_sotuvchi"));
        email_sotuvchi.setCellValueFactory(new PropertyValueFactory<Sotuvchi, Double>("email_sotuvchi"));
    }

    private void Xisoblartab() {

        TableColumn Hfirst_name_S = new TableColumn("Ism");
        TableColumn Hcard_amount_S = new TableColumn("Karta summa");
        TableColumn Hcredit_S = new TableColumn("Qarz summa");
        TableColumn Hpaid_date_S = new TableColumn("to'langan sana");
        TableColumn Htotal_cost_S = new TableColumn("Umumiy summa");
        TableColumn Hpaid_in_cash_S = new TableColumn("Naqd pul summa");

        Xtable.getColumns().addAll(Hfirst_name_S, Hcard_amount_S, Hcredit_S, Hpaid_date_S, Htotal_cost_S, Hpaid_in_cash_S);

        Hfirst_name_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, String>("first_name_C"));
        Hcard_amount_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, Float>("card_amount_C"));
        Hcredit_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, Float>("credit_C"));
        Hpaid_date_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, String>("paid_date_C"));
        Htotal_cost_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, Double>("total_cost_C"));
        Hpaid_in_cash_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, Double>("paid_in_cash_C"));
    }


    private void SoldRateTab() {
        TableColumn nameSR = new TableColumn("Nomi");
        TableColumn barcodeSR = new TableColumn("Barcode");
        TableColumn tCostSR = new TableColumn("Umumiy summa");
        TableColumn soldQuantirySR = new TableColumn("Sotilgan soni");

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


    public void btnIzlashAction() throws SQLException {
        String textIzlash2 = AdminTextSearch.getText();
        ObservableList<ProductTable> apple = FXCollections.observableArrayList();
        ResultSet myRs = null;
        try {
            myRs = ProductDao.getResultSet(textIzlash2, AdminTextSearch, myConn);
            ProductDao.productTableGeneratorAdmin(myRs, apple);
            // AdminTable.setItems(apple);
        } catch (Exception exc) {
            exc.printStackTrace();
        }

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
    public void maxsulotQoshish() throws Exception {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText("Yangi maxsulot qo'shasizmi?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                try {
                    String list = ComboTypeList.getSelectionModel().getSelectedItem().toString();
                    String barcode1 = AdminTextBarcode.getText();
                    String nomi1 = AdminTextNomi.getText();
                    String unit = ComboBoxUnit.getSelectionModel().getSelectedItem().toString();
                    String Miqdori1 = AdminTextMiqdori.getText();
                    String narxi1 = AdminTextNarxi.getText();
                    String saleNarxi = AdminTextSale.getText();
                    String dan1 = AdminTextDan.getValue().toString();
                    String gacha1 = AdminTextGacha.getValue().toString();
                    productDao.addProduct(barcode1, nomi1, list, saleNarxi, narxi1, Miqdori1, dan1, gacha1, "1", unit);
                    btnIzlashAction();
                    AdminTextBarcode.setText("");
                    AdminTextNomi.setText("");
                    AdminTextMiqdori.setText("");
                    AdminTextNarxi.setText("");
                    AdminTextSale.setText("");
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
    }


    private void setOzgaartirishMaxsulot() {
        try {
            AdminTable.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
                product apple2 = (product) AdminTable.getSelectionModel().getSelectedItem();
                try {
                    AdminTextBarcode.setText(apple2.getBarcode());
                    AdminTextNomi.setText(apple2.getName());
                    AdminTextNarxi.setText(apple2.getCost());
                    AdminTextSale.setText(apple2.getCost_o());
                    AdminTextDan.setValue(LocalDate.parse(apple2.getDate_c()));
                    AdminTextGacha.setValue(LocalDate.parse(apple2.getDate_o()));
                    AdminTextMiqdori.setText(apple2.getQuantity());
                    AdminTextId.setText(String.valueOf(apple2.getId()));
                } catch (Exception exc) {
                }
            });

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }


    /**
     * Product update
     */
    public void btnOzgartirishAction() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText("Maxsulot malumotlarini o'zgartirasizmi ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                try {
                    String list = ComboTypeList.getSelectionModel().getSelectedItem().toString();
                    String id = AdminTextId.getText();
                    String barcode1 = AdminTextBarcode.getText();
                    String nomi1 = AdminTextNomi.getText();
                    String Miqdori1 = AdminTextMiqdori.getText();
                    String narxi1 = AdminTextNarxi.getText();
                    String saleNarxi1 = AdminTextSale.getText();
                    String dan1 = AdminTextDan.getValue().toString();
                    String gacha1 = AdminTextDan.getValue().toString();
                    productDao.updateProduct(barcode1, nomi1, list, saleNarxi1, narxi1, Miqdori1, dan1, gacha1, "1", id);
                    btnIzlashAction();
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
        Statement myStmt;
        //List to add items
        ObservableList<CollapsedHistory> HappleC = FXCollections.observableArrayList();
        try {
            if (Xcheckbox1.isSelected()) {

                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery("SELECT * FROM collapsedCreditHistoryAll WHERE cardAmount > 0");
                preparedStatement = myConn.prepareStatement("SELECT sum(c.total_cost) umumiy_summa,sum(paid_in_cash) umumiyCashAmount, sum(c.cardAmount) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM collapsedCreditHistoryAll c\n" +
                        "WHERE cardAmount > 0");

                if (date1 != null && date2 != null) {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    String sdate1 = df.format(Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    String sdate2 = df.format(Date.from(date2.atStartOfDay(ZoneId.systemDefault()).toInstant()));


                    preparedStatement = myConn.prepareStatement("SELECT * FROM collapsedCreditHistoryAll\n" +
                            "WHERE cardAmount>0 AND substr(paid_date,7,10)\n" +
                            "BETWEEN ? AND ?");
                    preparedStatement.setString(1, sdate1);
                    preparedStatement.setString(2, sdate2);

                    myRs = preparedStatement.executeQuery();

                    preparedStatement = myConn.prepareStatement("SELECT sum(c.total_cost) umumiy_summa,sum(paid_in_cash) umumiyCashAmount, sum(c.cardAmount) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM collapsedCreditHistoryAll c\n" +
                            "WHERE cardAmount >0 AND substr(paid_date,7,10)\n" +
                            "BETWEEN ? AND ?");
                    preparedStatement.setString(1, sdate1);
                    preparedStatement.setString(2, sdate2);
                    myRsTotal = preparedStatement.executeQuery();
                }


                myRsTotal = preparedStatement.executeQuery();
            } else if (CheckQarzbox.isSelected()) {

                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery("SELECT * FROM collapsedCreditHistoryAll WHERE credit>0");

                preparedStatement = myConn.prepareStatement("SELECT sum(c.total_cost) umumiy_summa,sum(paid_in_cash) umumiyCashAmount, sum(c.cardAmount) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM collapsedCreditHistoryAll c\n" +
                        "WHERE credit>0");
                if (date1 != null && date2 != null) {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    String sdate1 = df.format(Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    String sdate2 = df.format(Date.from(date2.atStartOfDay(ZoneId.systemDefault()).toInstant()));


                    preparedStatement = myConn.prepareStatement("SELECT * FROM collapsedCreditHistoryAll\n" +
                            "WHERE credit>0 AND substr(paid_date,7,10)\n" +
                            "BETWEEN ? AND ? ");
                    preparedStatement.setString(1, sdate1);
                    preparedStatement.setString(2, sdate2);

                    myRs = preparedStatement.executeQuery();

                    preparedStatement = myConn.prepareStatement("SELECT sum(c.total_cost) umumiy_summa,sum(paid_in_cash) umumiyCashAmount, sum(c.cardAmount) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM collapsedCreditHistoryAll c\n" +
                            "WHERE credit >0 AND substr(paid_date,7,10)\n" +
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


                preparedStatement = myConn.prepareStatement("SELECT * FROM collapsedCreditHistoryAll\n" +
                        "WHERE substr(paid_date,7,10)\n" +
                        "BETWEEN ? AND ? ");
                preparedStatement.setString(1, sdate1);
                preparedStatement.setString(2, sdate2);

                myRs = preparedStatement.executeQuery();

                preparedStatement = myConn.prepareStatement("SELECT sum(c.total_cost) umumiy_summa, sum(paid_in_cash) umumiyCashAmount, sum(c.cardAmount) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM collapsedCreditHistoryAll c\n" +
                        "WHERE substr(paid_date,7,10)\n" +
                        "BETWEEN ? AND ?");
                preparedStatement.setString(1, sdate1);
                preparedStatement.setString(2, sdate2);
                myRsTotal = preparedStatement.executeQuery();

                /**
                 *   Getting data from database to XYChart
                 */
                ResultSet myGraphRestutSet = null;

                preparedStatement = myConn.prepareStatement("SELECT * FROM collapsedCreditHistoryAll\n" +
                        "WHERE substr(paid_date,7,10)\n" +
                        "BETWEEN ? AND ?");
                preparedStatement.setString(1, sdate1);
                preparedStatement.setString(2, sdate2);

                myGraphRestutSet = preparedStatement.executeQuery();


                XYChart.Series<String, Integer> series = new XYChart.Series<>();

                while (myGraphRestutSet.next()) {
                    series.getData().add(new XYChart.Data<>(myGraphRestutSet.getString("paid_date"), myGraphRestutSet.getInt("Total_cost")));
                }
                XLineChart.getData().add(series);

                /*
                 * End of XYChart
                 *
                 */


            } else {
                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery("SELECT * FROM (\n" +
                        "                SELECT * FROM collapsedCreditHistoryAll ORDER BY id DESC LIMIT 200\n" +
                        "              ) sub\n" +
                        "ORDER BY id ASC ");

                preparedStatement = myConn.prepareStatement("SELECT sum(c.total_cost) umumiy_summa, sum(paid_in_cash) umumiyCashAmount, sum(c.cardAmount) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM collapsedCreditHistoryAll c");

                myRsTotal = preparedStatement.executeQuery();
            }

            while (myRs.next()) {
                CollapsedHistory collapsedHistory = new CollapsedHistory();

                //collapsedHistory.setId_C(myRs.getInt("id"));
                collapsedHistory.setFirst_name_C(myRs.getString("first_name"));
                //collapsedHistory.setLast_name_C(myRs.getString("last_name"));
                collapsedHistory.setCard_amount_C(new BigDecimal(myRs.getFloat("cardAmount")).toPlainString());
                collapsedHistory.setCredit_C(new BigDecimal(myRs.getFloat("credit")).toPlainString());
                collapsedHistory.setPaid_date_C(myRs.getString("paid_date"));
                collapsedHistory.setTotal_cost_C(new BigDecimal(myRs.getDouble("total_cost")).toPlainString());
                collapsedHistory.setPaid_in_cash_C(new BigDecimal(myRs.getDouble("paid_in_cash")).toPlainString());
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


    public void btnOchirishAction() throws Exception {
        String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText("Maxsulotni rostdan ham o'chirasizmi ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent())
            if (result.get() == ButtonType.OK) {


                PreparedStatement preparedStatement = null;
                String barcode1 = AdminTextBarcode.getText();
                Integer id1 = null;
                try {
                    preparedStatement = myConn.prepareStatement("DELETE  FROM maxsulotlar" + " WHERE item_barcode=?");
                    preparedStatement.setString(1, barcode1);
                    int delete = preparedStatement.executeUpdate();
                    if (delete == 1) {
                        preparedStatement = myConn.prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Maxsulot ochirildi' ,?)");
                        preparedStatement.setString(1, apple);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "O'chirildi", "Maxsulot o'chirildi", JOptionPane.INFORMATION_MESSAGE);

                    }
                    btnIzlashAction();


                } catch (Exception exc) {

                } finally {
                    statement.close();
                    resultSet.close();

                }
            }
    }

    @FXML
    private void tarixtable() throws SQLException {

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
        } finally {
            statement.close();
            resultSet.close();
        }
    }


    private void SotuvchiTable() throws Exception {
        ObservableList<Sotuvchi> Sotuvchi_S = FXCollections.observableArrayList();
        try {
            statement = myConn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sotuvchi ORDER BY sotuvchi_id");

            while (resultSet.next()) {
                Sotuvchi sotuvchi = new Sotuvchi();

                sotuvchi.setSotuvchi_id(resultSet.getInt("sotuvchi_id"));
                sotuvchi.setFirstName_sotuvchi(resultSet.getString("first_name"));
                sotuvchi.setLastName_sotuvchi(resultSet.getString("last_name"));
                sotuvchi.setTelNumber_sotuvchi(resultSet.getString("tel_number"));
                sotuvchi.setSalary_sotuvchi(resultSet.getFloat("salary"));
                sotuvchi.setDate_sotuvchi(resultSet.getString("date"));
                sotuvchi.setLavozim_sotuvchi(resultSet.getString("lavozim"));
                sotuvchi.setPassword_sotuvchi(resultSet.getString("password"));
                sotuvchi.setEmail_sotuvchi(resultSet.getString("email"));
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

    public void UpdateBarChart() throws Exception {
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

    public void clearTables() throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText(" Rostdan ham barcha ma'lumotlarni o'chirmoqchimisiz ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                try {
                    preparedStatement = myConn.prepareStatement("DELETE FROM sellaction  ");
                    preparedStatement.executeUpdate();
                    preparedStatement = myConn.prepareStatement("DELETE FROM history");
                    preparedStatement.executeUpdate();
                    preparedStatement = myConn.prepareStatement("DELETE FROM product");
                    preparedStatement.executeUpdate();
                    preparedStatement = myConn.prepareStatement("DELETE FROM seller");
                    preparedStatement.executeUpdate();
                    preparedStatement = myConn.prepareStatement("DELETE FROM customer");
                    preparedStatement.executeUpdate();
                    preparedStatement = myConn.prepareStatement("DELETE FROM suplier");
                    preparedStatement.executeUpdate();
                    preparedStatement = myConn.prepareStatement("DELETE FROM type");
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, " Barcha malumotlar tozalandi", "Barcha malumotlar o'chirib tashlandi", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception exc) {
                    exc.printStackTrace();
                } finally {
                    preparedStatement.close();
                }
            }
    }

    public void excelToDB() throws IOException, SQLException {
        //  FileChooser fileChooser = new FileChooser();
        //  Stage stage = null;

        //  File selectFile = fileChooser.showOpenDialog(stage);
//
//        FileInputStream file2 = new FileInputStream("E:\\OmborXisobi.xls");

/*
        try {
            String Q1 = "INSERT INTO maxsulotlar(item_barcode, item_name, item_type, item_quantity,item_cost,item_sale_cost,item_date, item_expire_date) VALUES(?, ?, ?, ? , ? , ? , ? ,? )";
            preparedStatement = myConn.prepareStatement(Q1);
            XSSFWorkbook wb = new XSSFWorkbook(new File("E:\\OmborXisobi.xlsx"));
            XSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                preparedStatement.setString(1, row.getCell(0).getStringCellValue());
                preparedStatement.setString(2, row.getCell(1).getStringCellValue());
                preparedStatement.setInt(3, (int) row.getCell(2).getNumericCellValue());
                preparedStatement.setDouble(4, row.getCell(3).getNumericCellValue());
                preparedStatement.setDouble(5, row.getCell(4).getNumericCellValue());
                preparedStatement.setString(6, row.getCell(5).getStringCellValue());
                preparedStatement.setString(7, row.getCell(6).getStringCellValue());
                preparedStatement.setString(8, row.getCell(7).getStringCellValue());
            }
            preparedStatement.execute();
            preparedStatement.close();
            wb.close();

            JOptionPane.showMessageDialog(null, "All records are imported to database", "Ijobiy", JOptionPane.INFORMATION_MESSAGE);
        }
         catch (Exception exe) {
            exe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Exel fileni qo'shishda muammo kuzatildi" + exe, " Xatolik", JOptionPane.ERROR_MESSAGE);
        }*/
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
                    String nameSotuvchi = firstNameSo.getText().toString();
                    String lastnameSotuvchi = lastNameS.getText().toString();
                    String jobTitleSotuvchi = jobTieleS.getText().toString();
                    String oyliklar = salaryS.getText().toString();
                    Double salarySotuvchi = Double.parseDouble(oyliklar);
                    String emailSotuvchi = emailS.getText().toString();
                    String passwordSotuvchi = passwordS.getText().toString();
                    String telephoneSotuchi = telephoneS.getText().toString();
                    String birthDateSotuvchi = birthDateS.getValue().toString();
                    preparedStatement = myConn.prepareStatement("INSERT INTO sotuvchi "
                            + "( first_name, last_name, tel_number,salary, date, lavozim, password, email ) "
                            + "VALUES (?,?,?,?,?,?,?,?)");
                    preparedStatement.setString(1, nameSotuvchi);
                    preparedStatement.setString(2, lastnameSotuvchi);
                    preparedStatement.setString(3, telephoneSotuchi);
                    preparedStatement.setDouble(4, salarySotuvchi);
                    preparedStatement.setString(5, birthDateSotuvchi);
                    preparedStatement.setString(6, jobTitleSotuvchi);
                    preparedStatement.setString(7, passwordSotuvchi);
                    preparedStatement.setString(8, emailSotuvchi);
                    preparedStatement.executeUpdate();
                    preparedStatement = myConn.prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin',' Yangi sotuvchi qoshildi' ,?)");
                    preparedStatement.setString(1, apple);
                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Yangi sotuvchi qo'shildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);

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
                Sotuvchi sotuvchi = (Sotuvchi) SotuvchiTable.getItems().get(SotuvchiTable.getSelectionModel().getSelectedIndex());
                try {
                    firstNameSo.setText(sotuvchi.getFirstName_sotuvchi());
                    lastNameS.setText(sotuvchi.getLastName_sotuvchi());
                    String salarySot = String.valueOf(sotuvchi.getSalary_sotuvchi());
                    salaryS.setText(salarySot);
                    jobTieleS.setText(sotuvchi.getLavozim_sotuvchi());
                    emailS.setText(sotuvchi.getEmail_sotuvchi());
                    passwordS.setText(sotuvchi.getPassword_sotuvchi());
                    birthDateS.setValue(LocalDate.parse(sotuvchi.getDate_sotuvchi()));
                    telephoneS.setText(sotuvchi.getTelNumber_sotuvchi());
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
                    String nameSotuvchi = firstNameSo.getText().toString();
                    String lastnameSotuvchi = lastNameS.getText().toString();
                    String jobTitleSotuvchi = jobTieleS.getText().toString();
                    Double salarySotuvchi = Double.parseDouble(salaryS.getText());
                    String emailSotuvchi = emailS.getText().toString();
                    String passwordSotuvchi = passwordS.getText().toString();
                    String telephoneSotuchi = telephoneS.getText().toString();
                    String birthDateSotuvchi = birthDateS.getValue().toString();
                    preparedStatement = myConn.prepareStatement("UPDATE  sotuvchi SET  first_name=?, last_name=?, tel_number=?,salary=?, date=?, lavozim=?, password=? WHERE email=?");
                    preparedStatement.setString(1, nameSotuvchi);
                    preparedStatement.setString(2, lastnameSotuvchi);
                    preparedStatement.setString(3, telephoneSotuchi);
                    preparedStatement.setDouble(4, salarySotuvchi);
                    preparedStatement.setString(5, birthDateSotuvchi);
                    preparedStatement.setString(6, jobTitleSotuvchi);
                    preparedStatement.setString(7, passwordSotuvchi);
                    preparedStatement.setString(8, emailSotuvchi);
                    preparedStatement.executeUpdate();
                    preparedStatement = myConn.prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin',' Sotuvchi  malumotlari ozgartirildi' ,?)");
                    preparedStatement.setString(1, apple);
                    preparedStatement.executeUpdate();
                    Utils.InfoAlert("Bildirgi","Sotuvchi","Sotuvchi  malumotlari ozgartirildi");
                } catch (Exception exc) {
                    Utils.ErrorAlert("Xatolik", "Sotuvchi" ,""+exc);
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
        String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText(" Sotuvchini rostdan ham o'chirasizmi ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                String emailSotuvchi1 = emailS.getText();
                Integer id1 = null;
                try {
                    preparedStatement = myConn.prepareStatement("DELETE  FROM sotuvchi" + " WHERE sotuvchi.email=?");
                    preparedStatement.setString(1, emailSotuvchi1);
                    int delete = preparedStatement.executeUpdate();
                    if (delete == 1) {
                        preparedStatement = myConn.prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Sotuvchi ochirildi' ,?)");
                        preparedStatement.setString(1, apple);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "O'chirildi", "Sotuvchi o'chirildi", JOptionPane.INFORMATION_MESSAGE);
                    }
                    btnIzlashAction();
                } catch (Exception exc) {
                    exc.printStackTrace();
                } finally {
                    preparedStatement.close();
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

    public void listProduct() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Excel fayl yaratish ");
        alert.setHeaderText(null);
        alert.setContentText("Ombordagi maxsulotlarni excell faylga ko'chirasizmi?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                printer printer = new printer();
                String filename = printer.ExcelFilePath() + "List.xls";
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.createSheet("List");
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
                Sheet desSheet = writeWorkbook.createSheet("list");
                Statement stmt = null;
                ResultSet rs = null;
                try {
                    String query = "SELECT * FROM listProduct";

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
                        FileOutputStream fileOut = new FileOutputStream(printer.ExcelFilePath() + "list.xls");
                        writeWorkbook.write(fileOut);
                        fileOut.close();
                    }
                    Utils.InfoAlert("Bildirgi","Excell","Excell fayl yaratildi");
                } catch (SQLException e) {
                    System.out.println("Failed to get data from database");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }

    public void dbToExcell() throws IOException {


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
                    String query = "SELECT * FROM OmborHisoblari";
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
                        FileOutputStream fileOut = new FileOutputStream(printer.ExcelFilePath() + "OmborXisobi.xls");
                        writeWorkbook.write(fileOut);
                        fileOut.close();
                    }
                    Utils.InfoAlert("Bildirgi","Excell","Excell fayl yaratildi");
                    } catch (SQLException e) {
                    System.out.println("Failed to get data from database");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
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
                    System.out.println("Your excel file has been generated!");

                    Workbook writeWorkbook = new HSSFWorkbook();
                    Sheet desSheet = writeWorkbook.createSheet("new sheet");

                    try {

                        preparedStatement = myConn.prepareStatement("SELECT * FROM collapsedCreditHistoryAll\n" +
                                "WHERE substr(paid_date,7,10)\n" +
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
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }

    }

    public void PrinterAddAction() throws SQLException {

        TextInputDialog dialog = new TextInputDialog("Printer");
        dialog.setTitle("Printer nomini qo'shish");
        dialog.setHeaderText("Iltimos printer nomini qo'shing");
        dialog.setContentText("Printer nomi");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println(result.get());
            String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
            try {
                preparedStatement = myConn.prepareStatement("UPDATE printer SET name=?");
                preparedStatement.setString(1, result.get());
                preparedStatement.executeUpdate();

                preparedStatement = myConn.prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Printer nomi qoshildi' ,?)");
                preparedStatement.setString(1, apple);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Yangi printer nomi qo'shildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                preparedStatement.close();
            }
        }
    }

    public void SetExcelFolder() throws SQLException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = null;
        File dir = directoryChooser.showDialog(stage);
        System.out.println(dir.getAbsolutePath() + "\\");
        String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
        String Path = dir.getAbsolutePath() + "\\";
        try {
            preparedStatement = myConn.prepareStatement("UPDATE printer SET Path=?");
            preparedStatement.setString(1, Path);
            preparedStatement.executeUpdate();
            preparedStatement = myConn.prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Excel joylashuvi qoshildi' ,?)");
            preparedStatement.setString(1, apple);
            preparedStatement.executeUpdate();
            Utils.InfoAlert("Bildirgi", "Excell", "Excell joyi qo'shildi!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            preparedStatement.close();
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

    public void TypeOperation() {
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
    }

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





