package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.DAO.Database;
import sample.DAO.ProductDao;
import sample.DAO.printer;
import sample.MaxsulotTableView.*;
import sample.Utils.BarCodeService;
import sample.Utils.Utils;

import javax.swing.*;
import java.io.*;
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
    private TextField AdminTextMiqdori;
    @FXML
    private TextField AdminTextNarxi;
    @FXML
    private TextField AdminTextSale;
    @FXML
    private DatePicker AdminTextDan;
    @FXML
    private DatePicker AdminTextGacha;
    //Savdo table -------------------------------
    @FXML
    private TableView SavdoTable;
    @FXML
    private TableView SavdoTable1;
    //--------------------------------------------
    @FXML
    private TableView QarzlarTable;
    @FXML
    private TableView TarixTable;
    @FXML
    private TableView SotuvchiTable;
    @FXML
    private TableView MuhimChangeTable;
    @FXML
    private TableView OmborHisobiTable;
    @FXML
    private ImageView ImageSotuvchi;
    //SavdoRateTable------------------------------
    @FXML
    private TableView SoldRateTable;
    @FXML
    private TableView SavdoRateTable1;
    //-------------------------------------------
    @FXML
    private TextField Qarzid;
    //Savdo oynasi   oynasidan hamma variablelar  initialize qilsih kerak
    @FXML
    private DatePicker SavdoDateP1;
    @FXML
    private DatePicker SavdoDateP2;
    //--------------------------------------------
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
    private JFXComboBox Xcombo1;
    @FXML
    private ComboBox Xcombo2;
    @FXML
    private ComboBox Xcombo3;
    @FXML
    private CheckBox Xcheckbox1;
    @FXML
    private CheckBox CheckQarzbox;
    @FXML
    private CheckBox CheckboxToday;
    @FXML
    private JFXButton XbuttonSaralash;
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
    //----------------------------------------------

    /*
    *  Tarix table
    */
    @FXML
    JFXDatePicker TarixDate1;
    @FXML
    JFXDatePicker TarixDate2;
    @FXML TextField TarixId;
    @FXML TextField TarixName;
    @FXML TextField TarixType;
    @FXML TextField TarixMiqdori;
    @FXML TextField TarixSana;
    @FXML TextField TarixSumma;
    @FXML TextField TarixProductId;

    /*
    *  DDL change tab
    *
    */
    @FXML JFXDatePicker DDL_date1;
    @FXML JFXDatePicker DDL_date2;


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
    private ResultSet resultSet = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeProductTab();
        setOzgaartirishMaxsulot();
        ozgartirishStuvchiAction();
        collapsedHistoryTab();
        intializeQarzTab();
        initializeHistoryTab();
        SotuvchiTab();
        SoldRateTab();
        TableHistoryTab();
        OmborHisobiTab();
        Xisoblartab();
        setTarixAction();
        // AllTotal1Tab();
        try {
            collapsedHistoryAll();
            qarzTable();
            tarixtable();
            SotuvchiTable();
            setQarztablerowselect();
            SavdoRateTable();
            TableDDLChange();
            OmborHisobiTable();
            HisoblarTable();
            SotuvchiCombobox();
            AddTypeComboboxAction();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeProductTab() {
        //Maxsulotlar table
//        TableColumn id = new TableColumn("Id");

        TableColumn id = new TableColumn("Id");
        TableColumn barcode1 = new TableColumn("Barcode");
        TableColumn name1 = new TableColumn("Nomi");
        TableColumn type1 = new TableColumn("Turi");
        TableColumn quantity1 = new TableColumn("Miqdori");
        TableColumn cost1 = new TableColumn("Narxi");
        TableColumn itemSaleCost = new TableColumn("Chegirma narxi");
        TableColumn date1 = new TableColumn("...dan");
        TableColumn expireDate1 = new TableColumn("...gacha");


        AdminTable.getColumns().addAll(id,barcode1, name1, type1, quantity1, cost1, itemSaleCost, date1, expireDate1);

        barcode1.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("barcode"));
        name1.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("name"));
        type1.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("type"));
        quantity1.setCellValueFactory(new PropertyValueFactory<ProductTable, Integer>("quantity"));
        cost1.setCellValueFactory(new PropertyValueFactory<ProductTable, Double>("cost"));
        itemSaleCost.setCellValueFactory(new PropertyValueFactory<ProductTable, Double>("itemsalecost"));
        date1.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("date"));
        expireDate1.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("expireDate"));
        id.setCellValueFactory(new PropertyValueFactory<ProductTable, Integer>("id"));
    }

    private void initializeHistoryTab() {
        TableColumn TarixH = new TableColumn(" Id");
        TableColumn Tarix_M_Id = new TableColumn("Maxsulot raqami");

        TableColumn NameH = new TableColumn("Nomi");
        TableColumn TypeH = new TableColumn("Turi");
        TableColumn QuantityH = new TableColumn("Miqdori");
        TableColumn PaidDateH = new TableColumn("To'langan sana");
        TableColumn TotalCostH = new TableColumn("Umumiy summa");


        TarixTable.getColumns().addAll(TarixH,Tarix_M_Id, NameH, TypeH, QuantityH, PaidDateH, TotalCostH);
        TarixH.setCellValueFactory(new PropertyValueFactory<HistoryTable, Integer>("tarixid"));
        Tarix_M_Id.setCellValueFactory(new PropertyValueFactory<HistoryTable, Integer>("MaxsulotId"));
        NameH.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("itemname"));
        TypeH.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("itemtype"));
        QuantityH.setCellValueFactory(new PropertyValueFactory<HistoryTable, Integer>("itemquantity"));
        PaidDateH.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("paiddata"));
        TotalCostH.setCellValueFactory(new PropertyValueFactory<HistoryTable, Double>("totalcost"));

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
        TableColumn Hpaid_in_cash_S = new TableColumn("Naqtpul summa");

        Xtable.getColumns().addAll(Hfirst_name_S, Hcard_amount_S, Hcredit_S, Hpaid_date_S, Htotal_cost_S, Hpaid_in_cash_S);

        Hfirst_name_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, String>("first_name_C"));
        Hcard_amount_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, Float>("card_amount_C"));
        Hcredit_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, Float>("credit_C"));
        Hpaid_date_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, String>("paid_date_C"));
        Htotal_cost_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, Double>("total_cost_C"));
        Hpaid_in_cash_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, Double>("paid_in_cash_C"));
    }

    private void collapsedHistoryTab() {

        TableColumn id_S = new TableColumn("Id");
        TableColumn first_name_S = new TableColumn("Ism");
        TableColumn last_name_S = new TableColumn("Familiya");
        TableColumn card_amount_S = new TableColumn("Karta summa");
        TableColumn credit_S = new TableColumn("Qarz summa");
        TableColumn credit_description_S = new TableColumn("Izox");
        TableColumn paid_date_S = new TableColumn("to'langan sana");
        TableColumn total_cost_S = new TableColumn("Umumiy summa");
        TableColumn paid_in_cash_S = new TableColumn("Naqtpul summa");

        SavdoTable1.getColumns().addAll(id_S, first_name_S, last_name_S, card_amount_S, credit_S, credit_description_S, paid_date_S, total_cost_S, paid_in_cash_S);


        id_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, Integer>("id_C"));
        first_name_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, String>("first_name_C"));
        last_name_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, String>("last_name_C"));
        card_amount_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, Float>("card_amount_C"));
        credit_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, Float>("credit_C"));
        credit_description_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, Float>("credit_description_C"));
        paid_date_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, String>("paid_date_C"));
        total_cost_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, String>("total_cost_C"));
        paid_in_cash_S.setCellValueFactory(new PropertyValueFactory<CollapsedHistory, String>("paid_in_cash_C"));
    }

    private void intializeQarzTab() {
        TableColumn idQ = new TableColumn("Id");
        TableColumn firstnameQ = new TableColumn("Ismi");
        TableColumn surnameQ = new TableColumn("Familiya");
        TableColumn cardAmountQ = new TableColumn("Karta summa");
        TableColumn creditQ = new TableColumn("Qarz summa");
        TableColumn creditDescriptionQ = new TableColumn("Izoh");
        TableColumn paid_dateQ = new TableColumn("To'langan sana");
        TableColumn total_costQ = new TableColumn("Umumiy summa");
        TableColumn paid_in_cash = new TableColumn("Naqt pul summa");

        QarzlarTable.getColumns().addAll(idQ, firstnameQ, surnameQ, cardAmountQ, creditQ, creditDescriptionQ, paid_dateQ, total_costQ, paid_in_cash);
        idQ.setCellValueFactory(new PropertyValueFactory<QarzTable, Integer>("idQ"));
        firstnameQ.setCellValueFactory(new PropertyValueFactory<QarzTable, String>("FirstName_Q"));
        surnameQ.setCellValueFactory(new PropertyValueFactory<QarzTable, String>("Lastname_Q"));
        cardAmountQ.setCellValueFactory(new PropertyValueFactory<QarzTable, Float>("Card_amount_Q"));
        creditQ.setCellValueFactory(new PropertyValueFactory<QarzTable, Float>("Credit_Q"));
        creditDescriptionQ.setCellValueFactory(new PropertyValueFactory<QarzTable, Float>("CreditDescription_Q"));
        paid_dateQ.setCellValueFactory(new PropertyValueFactory<QarzTable, String>("paid_date_Q"));
        total_costQ.setCellValueFactory(new PropertyValueFactory<QarzTable, Double>("total_cost_Q"));
        paid_in_cash.setCellValueFactory(new PropertyValueFactory<QarzTable, Double>("paid_in_cash"));
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

    private void TableHistoryTab() {
        TableColumn ownerTH = new TableColumn("O'zgartiruvchi");
        TableColumn descriptionTH = new TableColumn("Tarif");
        TableColumn dateTh = new TableColumn("Sana");
        MuhimChangeTable.getColumns().addAll(ownerTH, descriptionTH, dateTh);
        ownerTH.setCellValueFactory(new PropertyValueFactory<TableHistory, String>("owener"));
        descriptionTH.setCellValueFactory(new PropertyValueFactory<TableHistory, String>("description"));
        dateTh.setCellValueFactory(new PropertyValueFactory<TableHistory, String>("changeTime"));

    }

    private void OmborHisobiTab() {

        TableColumn barcodeOH = new TableColumn("Barcode");
        TableColumn nameOH = new TableColumn("Nomi");
        TableColumn typeOH = new TableColumn("Turi");
        TableColumn quantityOH = new TableColumn("Miqdori");
        TableColumn costOH = new TableColumn("Sotuv Narxi");
        TableColumn saleCostOH = new TableColumn(" Tannarxi");
        TableColumn totalCostOH = new TableColumn("Umumiy narxi");
        TableColumn tatalSaleOH = new TableColumn("Umumiy tannarxi");

        OmborHisobiTable.getColumns().addAll(barcodeOH, nameOH, typeOH, quantityOH, costOH, saleCostOH, totalCostOH, tatalSaleOH);

        barcodeOH.setCellValueFactory(new PropertyValueFactory<OmborHisobi, String>("barcodeOH"));
        nameOH.setCellValueFactory(new PropertyValueFactory<OmborHisobi, String>("nameOH"));
        typeOH.setCellValueFactory(new PropertyValueFactory<OmborHisobi, String>("typeOH"));
        saleCostOH.setCellValueFactory(new PropertyValueFactory<OmborHisobi, Double>("salaCostOH"));
        quantityOH.setCellValueFactory(new PropertyValueFactory<OmborHisobi, Integer>("quantityOH"));
        costOH.setCellValueFactory(new PropertyValueFactory<OmborHisobi, Double>("costOH"));
        totalCostOH.setCellValueFactory(new PropertyValueFactory<OmborHisobi, Double>("item_total_cost"));
        tatalSaleOH.setCellValueFactory(new PropertyValueFactory<OmborHisobi, Double>("item_total_sale_cost"));


    }

    public AdminPart() throws Exception {


        // connect to database
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
            AdminTable.setItems(apple);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Xatolik" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void maxsulotQoshish() throws SQLException {

        String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText("Yangi maxsulot qo'shasizmi?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent())
            if (result.get() == ButtonType.OK) {


                PreparedStatement preparedStatement = null;
                try {
                    String list = ComboTypeList.getSelectionModel().getSelectedItem().toString();
                    System.out.println(list);
                    String barcode1 = AdminTextBarcode.getText();
                    String nomi1 = AdminTextNomi.getText();
                    Integer Miqdori1 = Integer.parseInt(AdminTextMiqdori.getText());
                    Double narxi1 = Double.parseDouble(AdminTextNarxi.getText());
                    Double saleNarxi = Double.parseDouble(AdminTextSale.getText());
                    String dan1 = AdminTextDan.getValue().toString();
                    String gacha1 = AdminTextGacha.getValue().toString();
                    if(list !=null) {

                        preparedStatement = myConn.prepareStatement("INSERT INTO maxsulotlar "
                                + "(item_barcode, item_name, item_type, item_quantity," +
                                " item_cost,item_sale_cost, item_date, item_expire_date " +
                                " ) "
                                + "VALUES (?,?,?,?,?,?,?,?)");
                        preparedStatement.setString(1, barcode1);
                        preparedStatement.setString(2, nomi1);
                        preparedStatement.setString(3, list);
                        preparedStatement.setInt(4, Miqdori1);
                        preparedStatement.setDouble(5, narxi1);
                        preparedStatement.setDouble(6, saleNarxi);
                        preparedStatement.setString(7, dan1);
                        preparedStatement.setString(8, gacha1);


                        preparedStatement.executeUpdate();
                        btnIzlashAction();
                        preparedStatement = myConn.prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Maxsulot qoshildi' ,?)");
                        preparedStatement.setString(1, apple);
                        preparedStatement.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Yangi maxsulot qo'shildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);

                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Iltimos hamma malumotlarni kiriting", "Xatolik", JOptionPane.ERROR_MESSAGE);

                    }
                } catch (Exception exc) {
                    exc.printStackTrace();

                    JOptionPane.showMessageDialog(null, "Xatolik" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
                } finally {
                    //  DaoUtils.close(preparedStatement);

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
                            "WHERE cardAmount>0 and substr(paid_date,7,10)\n" +
                            "BETWEEN ? AND ?");
                    preparedStatement.setString(1, sdate1);
                    preparedStatement.setString(2, sdate2);

                    myRs = preparedStatement.executeQuery();

                    preparedStatement = myConn.prepareStatement("SELECT sum(c.total_cost) umumiy_summa,sum(paid_in_cash) umumiyCashAmount, sum(c.cardAmount) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM collapsedCreditHistoryAll c\n" +
                            "WHERE cardAmount >0 and substr(paid_date,7,10)\n" +
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
                            "WHERE credit>0 and substr(paid_date,7,10)\n" +
                            "BETWEEN ? AND ? ");
                    preparedStatement.setString(1, sdate1);
                    preparedStatement.setString(2, sdate2);

                    myRs = preparedStatement.executeQuery();

                    preparedStatement = myConn.prepareStatement("SELECT sum(c.total_cost) umumiy_summa,sum(paid_in_cash) umumiyCashAmount, sum(c.cardAmount) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM collapsedCreditHistoryAll c\n" +
                            "WHERE credit >0 and substr(paid_date,7,10)\n" +
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

                /*
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


            }

            else {
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
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
            exc.printStackTrace();
        }

    }

    private void setOzgaartirishMaxsulot() {
        try {
            AdminTable.setOnMouseClicked(event -> {
                ProductTable apple2 = (ProductTable) AdminTable.getItems().get(AdminTable.getSelectionModel().getSelectedIndex());
                try {
                    AdminTextBarcode.setText(apple2.getBarcode());
                    AdminTextNomi.setText(apple2.getName());
                    String narx1 = String.valueOf(apple2.getCost());
                    AdminTextNarxi.setText(narx1);
                    String saleNarxi1 = String.valueOf(apple2.getItemsalecost());
                    AdminTextSale.setText(saleNarxi1);
                    AdminTextDan.setValue(LocalDate.parse(apple2.getDate()));
                    AdminTextGacha.setValue(LocalDate.parse(apple2.getExpireDate()));
                    String miqdor = String.valueOf(apple2.getQuantity());
                    AdminTextMiqdori.setText(miqdor);
                    AdminTextId.setText(String.valueOf(apple2.getId()));


                } catch (Exception exc) {
                }
            });
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void btnOzgartirishAction() throws SQLException {
        String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText("Maxsulot malumotlarini o'zgartirasizmi ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent())
            if (result.get() == ButtonType.OK) {

                try {
                    int id = Integer.parseInt(AdminTextId.getText());
                    String barcode1 = AdminTextBarcode.getText();
                    String nomi1 = AdminTextNomi.getText();
                    Integer Miqdori1 = Integer.parseInt(AdminTextMiqdori.getText());
                    Double narxi1 = Double.parseDouble(AdminTextNarxi.getText());
                    Double saleNarxi1 = Double.parseDouble(AdminTextSale.getText());
                    String dan1 = AdminTextDan.getValue().toString();
                    String gacha1 = AdminTextDan.getValue().toString();
                    preparedStatement = myConn.prepareStatement("UPDATE maxsulotlar "
                            + "SET item_name=?,item_quantity=?,item_cost=?,item_sale_cost=?,item_date=?,item_expire_date=? ,item_barcode=?" + " WHERE id=? ");


                    preparedStatement.setString(1, nomi1);
                    preparedStatement.setInt(2, Miqdori1);
                    preparedStatement.setDouble(3, narxi1);
                    preparedStatement.setDouble(4, saleNarxi1);
                    preparedStatement.setString(5, dan1);
                    preparedStatement.setString(6, gacha1);
                    preparedStatement.setString(7, barcode1);
                    preparedStatement.setInt(8, id);
                    preparedStatement.executeUpdate();

                    btnIzlashAction();

                    preparedStatement = myConn.prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Maxsulot Ozgartirildi' ,?)");
                    preparedStatement.setString(1, apple);
                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(null, " Maxsulot  o'zgartirildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);


                } catch (Exception exc) {
                    exc.printStackTrace();
                    JOptionPane.showMessageDialog(null, exc, "Bildirgi", JOptionPane.ERROR_MESSAGE);

                } finally {


                }

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
                    // DaoUtils.close(preparedStatement);

                }
            }
    }

    private void collapsedHistoryAll() throws Exception {

/*
        //Getting input values  from dataPicker values

       String dataValue1 = SavdoDateP1.getValue().toString();
        String dataValue2 = SavdoDateP2.getValue().toString();


        PreparedStatement myPrep=null;
        Statement myStmt = null;
        ResultSet myRs = null;
        //List to add items
        ObservableList<CollapsedHistory> appleC = FXCollections.observableArrayList();

        try {
            if (dataValue1 != null || dataValue2 != null) {

                String sqlQuery = "SELECT * FROM collapsedCreditHistoryAll WHERE  paid_date BETWEEN ? and ?";
                //  myStmt = myConn.createStatement();
                // myRs = myStmt.executeQuery("SELECT * FROM collapsedCreditHistoryAll WHERE to_char(paid_date,'MM,dd,yyyy')  BETWEEN ? and ?");


            myPrep = myConn.prepareStatement(sqlQuery);
            myPrep.setString(1, dataValue1);
            myPrep.setString(2, dataValue2);
            myRs = myPrep.executeQuery();
        }
            else{
                  myStmt = myConn.createStatement();
                  myRs = myStmt.executeQuery("SELECT * FROM collapsedCreditHistoryAll ");
            }

            while (myRs.next()) {
                CollapsedHistory collapsedHistory = new CollapsedHistory();

                collapsedHistory.setId_C(myRs.getInt("id"));
                collapsedHistory.setFirst_name_C(myRs.getString("first_name"));
                collapsedHistory.setLast_name_C(myRs.getString("last_name"));
                collapsedHistory.setCard_amount_C(myRs.getFloat("cardAmount"));
                collapsedHistory.setCredit_C(myRs.getFloat("credit"));
                collapsedHistory.setCredit_description_C(myRs.getString("creditDescription"));
                collapsedHistory.setPaid_date_C(myRs.getString("paid_date"));
                collapsedHistory.setTotal_cost_C(myRs.getDouble("total_cost"));
                collapsedHistory.setPaid_in_cash_C(myRs.getDouble("paid_in_cash"));
                appleC.add(collapsedHistory);
            }
            SavdoTable.setItems(appleC);

        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            DaoUtils.close(myPrep);
        }
        */

        Statement myStmt = null;
        ResultSet myRs = null;
        //List to add items
        ObservableList<CollapsedHistory> appleC = FXCollections.observableArrayList();
        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT * FROM (\n" +
                    "                SELECT * FROM collapsedCreditHistoryAll ORDER BY id DESC LIMIT 200\n" +
                    "              ) sub\n" +
                    "ORDER BY id ASC");

            while (myRs.next()) {
                CollapsedHistory collapsedHistory = new CollapsedHistory();

                collapsedHistory.setId_C(myRs.getInt("id"));
                collapsedHistory.setFirst_name_C(myRs.getString("first_name"));
                collapsedHistory.setLast_name_C(myRs.getString("last_name"));
                collapsedHistory.setCard_amount_C(new BigDecimal(myRs.getFloat("cardAmount")).toPlainString());
                collapsedHistory.setCredit_C(new BigDecimal(myRs.getFloat("credit")).toPlainString());
                collapsedHistory.setCredit_description_C(myRs.getString("creditDescription"));
                collapsedHistory.setPaid_date_C(myRs.getString("paid_date"));
                collapsedHistory.setTotal_cost_C(new BigDecimal(myRs.getDouble("total_cost")).toPlainString());
                collapsedHistory.setPaid_in_cash_C(new BigDecimal(myRs.getDouble("paid_in_cash")).toPlainString());
                appleC.add(collapsedHistory);
            }
            SavdoTable1.setItems(appleC);

        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        }


    }

    public void QarzSaralash() throws SQLException {
        qarzTable();
    }

    private void qarzTable() throws SQLException {


        LocalDate DeptData1 = QarzDate1.getValue();
        LocalDate DeptData2 = QarzDate2.getValue();


        Statement myStmt = null;
        ResultSet myRs = null;
        //List to add items
        ObservableList<QarzTable> appleQ = FXCollections.observableArrayList();
        try {

            if (DeptData1 != null && DeptData2 != null) {

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String Qdate1 = df.format(Date.from(DeptData1.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                String Qdate2 = df.format(Date.from(DeptData2.atStartOfDay(ZoneId.systemDefault()).toInstant()));

                preparedStatement = myConn.prepareStatement("SELECT * FROM collapsedCreditHistory\n" +
                        "WHERE substr(paid_date,7,10)\n" +
                        "BETWEEN ? AND ?");
                preparedStatement.setString(1, Qdate1);
                preparedStatement.setString(2, Qdate2);
                myRs = preparedStatement.executeQuery();
            } else {

                myStmt = myConn.createStatement();

                myRs = myStmt.executeQuery("SELECT * FROM (\n" +
                        "    SELECT * FROM collapsedCreditHistory ORDER BY id DESC LIMIT 300\n" +
                        ") sub\n" +
                        "ORDER BY id ASC");
            }

            while (myRs.next()) {
                QarzTable qarzTable = new QarzTable();

                qarzTable.setIdQ(myRs.getInt("id"));
                qarzTable.setFirstName_Q(myRs.getString("first_name"));
                qarzTable.setLastname_Q(myRs.getString("last_name"));
                qarzTable.setCard_amount_Q(new BigDecimal(myRs.getFloat("cardAmount")).toPlainString());
                qarzTable.setCredit_Q(new BigDecimal(myRs.getFloat("credit")).toPlainString());
                qarzTable.setCreditDescription_Q(myRs.getString("creditDescription"));
                qarzTable.setPaid_date_Q(myRs.getString("paid_date"));
                qarzTable.setTotal_cost_Q(new BigDecimal(myRs.getDouble("total_cost")).toPlainString());
                qarzTable.setPaid_in_cash(new BigDecimal(myRs.getDouble("paid_in_cash")).toPlainString());
                appleQ.add(qarzTable);
            }
            QarzlarTable.setItems(appleQ);


        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        } finally {
            //DaoUtils.close(myStmt);
        }
    }

    @FXML
    private void tarixtable() throws SQLException {

        LocalDate DeptData1 = TarixDate1.getValue();
        LocalDate DeptData2 = TarixDate2.getValue();


        Statement myStmt = null;
        ResultSet myRs = null;
        //List to add items
        ObservableList<HistoryTable> appleQ = FXCollections.observableArrayList();
        try {

            if (DeptData1 != null && DeptData2 != null) {

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String Qdate1 = df.format(Date.from(DeptData1.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                String Qdate2 = df.format(Date.from(DeptData2.atStartOfDay(ZoneId.systemDefault()).toInstant()));

                preparedStatement = myConn.prepareStatement("SELECT * FROM history_v\n" +
                        "WHERE substr(paid_date,7,10)\n" +
                        "BETWEEN ? AND ?");
                preparedStatement.setString(1, Qdate1);
                preparedStatement.setString(2, Qdate2);
                myRs = preparedStatement.executeQuery();
            } else {

                myStmt = myConn.createStatement();

                myRs = myStmt.executeQuery("SELECT * FROM (\n" +
                        "    SELECT * FROM history_v ORDER BY Tarix_id DESC LIMIT 300\n" +
                        ") sub\n" +
                        "ORDER BY Tarix_id ASC");
            }

            while (myRs.next()) {
                HistoryTable qarzTable = new HistoryTable();

                qarzTable.setTarixid(myRs.getInt("tarix_id"));
                qarzTable.setMaxsulotId(myRs.getInt("item_id"));
                qarzTable.setItemname(myRs.getString("item_name"));
                qarzTable.setItemtype(myRs.getString("item_type"));
                qarzTable.setItemquantity(myRs.getInt("item_quantity"));
                qarzTable.setPaiddata(myRs.getString("paid_date"));
                qarzTable.setTotalcost(new BigDecimal(myRs.getDouble("total_cost")).toPlainString());
                appleQ.add(qarzTable);
            }
            TarixTable.setItems(appleQ);


        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        } finally {
            //DaoUtils.close(myStmt);
        }
    }
    public  void  setTarixAction(){
        try {
            TarixTable.setOnMouseClicked(event -> {
                HistoryTable sotuvchi = (HistoryTable) TarixTable.getItems().get(TarixTable.getSelectionModel().getSelectedIndex());
                try {
                    TarixId.setText(String.valueOf(sotuvchi.getTarixid()));
                    TarixName.setText(sotuvchi.getItemname());
                    TarixType.setText(sotuvchi.getItemtype());
                    TarixMiqdori.setText(String.valueOf(sotuvchi.getItemquantity()));
                    TarixSana.setText(sotuvchi.getPaiddata());
                    TarixSumma.setText(sotuvchi.getTotalcost());
                    TarixProductId.setText(String.valueOf(sotuvchi.getMaxsulotId()));
                } catch (Exception exc) {
                }
            });
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        }
    }
    @FXML
    public void BtnRevertAction(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText(" Tanlangan savdoni qaytarasimi?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent())
            if (result.get() == ButtonType.OK) {

                String quantity = TarixMiqdori.getText();
                String id = TarixProductId.getText();
                String Tarix_Id = TarixId.getText();


                PreparedStatement preparedStatement = null;


                if (quantity != null && id != null) {


                    String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());

                    try {
                        preparedStatement = myConn.prepareStatement("UPDATE maxsulotlar set item_quantity = item_quantity+? Where id="+id);
                        preparedStatement.setString(1, quantity);
                        preparedStatement.executeUpdate();

                        preparedStatement = myConn.prepareStatement("DELETE FROM history  where tarix_id=?");
                        preparedStatement.setString(1, Tarix_Id);
                        preparedStatement.executeUpdate();

                        preparedStatement = myConn.prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Savdo qaytarildi' ,?)");
                        preparedStatement.setString(1, apple);
                        preparedStatement.executeUpdate();

                        JOptionPane.showMessageDialog(null, " Tanlangan savdo qaytarildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);


                        tarixtable();


                    } catch (Exception exc) {
                        exc.printStackTrace();
                    } finally {
                        // DaoUtils.close(preparedStatement);

                    }
                }
            }else
            {
                JOptionPane.showMessageDialog(null,"  Qaytarishga savdo tanlanmadi ! ", "Xatolik", JOptionPane.ERROR_MESSAGE);

            }

    }

    private void SotuvchiTable() throws Exception {


        Statement myStmt = null;
        ResultSet myRs = null;
        //List to add items
        ObservableList<Sotuvchi> Sotuvchi_S = FXCollections.observableArrayList();
        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT * FROM sotuvchi ORDER BY sotuvchi_id");

            while (myRs.next()) {
                Sotuvchi sotuvchi = new Sotuvchi();

                sotuvchi.setSotuvchi_id(myRs.getInt("sotuvchi_id"));
                sotuvchi.setFirstName_sotuvchi(myRs.getString("first_name"));
                sotuvchi.setLastName_sotuvchi(myRs.getString("last_name"));
                sotuvchi.setTelNumber_sotuvchi(myRs.getString("tel_number"));
                sotuvchi.setSalary_sotuvchi(myRs.getFloat("salary"));
                sotuvchi.setDate_sotuvchi(myRs.getString("date"));
                sotuvchi.setLavozim_sotuvchi(myRs.getString("lavozim"));
                sotuvchi.setPassword_sotuvchi(myRs.getString("password"));
                sotuvchi.setEmail_sotuvchi(myRs.getString("email"));
                Sotuvchi_S.add(sotuvchi);
            }
            SotuvchiTable.setItems(Sotuvchi_S);


        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        } finally {
            //  DaoUtils.close(myStmt);
        }


    }

    private void product_sold_rate() throws ClassNotFoundException, SQLException {

        //   JOptionPane.showMessageDialog(null,"Connected");
        Statement myStmt = null;
        ResultSet myRs = null;
        piechartdata = FXCollections.observableArrayList();

        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT * FROM product_sold_rate");
            while (myRs.next()) {
                piechartdata.add(new PieChart.Data(myRs.getString("item_name"), myRs.getInt("sold_quantity")));
                cell.add(myRs.getInt("sold_quantity"));
                name.add(myRs.getString("item_name"));


            }

        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);

        } finally {
            //DaoUtils.close(myStmt);
        }

    }

    private void KunlikReytingChart() throws Exception {

        Statement myStmt = null;
        ResultSet myRs = null;
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        try {

            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT * FROM (\n" +
                    "    SELECT * FROM collapsedCreditHistoryAll ORDER BY id DESC LIMIT 200\n" +
                    ") sub\n" +
                    "ORDER BY id ASC ");

            while (myRs.next()) {
                series.getData().add(new XYChart.Data<>(myRs.getString("paid_date"), myRs.getInt("total_cost")));
            }
            KunlikAreaChart.getData().add(series);


        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);

        } finally {

        }


    }

    public void Update() throws Exception {

        product_sold_rate();
        SavdoReytingPieChart.setData(piechartdata);
        collapsedHistoryAll();
        qarzTable();
        tarixtable();
        SotuvchiTable();
        TableDDLChange();
        OmborHisobiTable();
        SavdoRateTable();


    }

    public void UpdateBarChart() throws Exception {
        KunlikReytingChart();
        product_sold_rate();
        SavdoReytingPieChart.setData(piechartdata);
        collapsedHistoryAll();
        qarzTable();
        tarixtable();
        SotuvchiTable();
        TableDDLChange();
        OmborHisobiTable();
        HisoblarTable();
        SavdoRateTable();
    }

    public void ActionChiqish() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Chiqish");
        alert.setHeaderText(null);
        alert.setContentText(" Dasturdan chiqmoqchimisiz  ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent())
            if (result.get() == ButtonType.OK) {


                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("Design_fxml/Login.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("SBD boshqaruv tizimi");
                    stage.setScene(new Scene(root, 1080, 720));
                    stage.setResizable(true);
                    stage.show();
                    // Hide this current window (if this is what you want)

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }

    private void SavdoRateTable() throws SQLException {

        Statement myStmt = null;
        ResultSet myRs = null;
        //List to add items
        ObservableList<SoldRate> Soldrate = FXCollections.observableArrayList();
        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT * FROM product_sold_rate ORDER BY sold_quantity");

            while (myRs.next()) {
                SoldRate soldRate = new SoldRate();


                soldRate.setNameSR(myRs.getString("item_name"));
                soldRate.setBarcodeSR(myRs.getString("item_barcode"));
                soldRate.setTotalSR(new BigDecimal(myRs.getFloat("total cost")).toPlainString());
                soldRate.setSoldQuantitySR(myRs.getInt("sold_quantity"));
                Soldrate.add(soldRate);
            }
            SavdoRateTable1.setItems(Soldrate);


        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        } finally {
            //   DaoUtils.close(myStmt);
        }


    }

    public void deleteQarzlar() throws Exception {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText(" Qazni rostdan yopmoqchimisiz ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent())
            if (result.get() == ButtonType.OK) {


                PreparedStatement preparedStatement = null;
                String barcode1 = Qarzid.getText();

                if (barcode1 != null) {


                    String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());

                    try {
                        preparedStatement = myConn.prepareStatement("UPDATE savdoAction set creditDescription = CONCAT(creditDescription,' berildi ') " + " WHERE id=?");
                        preparedStatement.setString(1, barcode1);
                        preparedStatement.executeUpdate();

                        preparedStatement = myConn.prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Qarz Tolandi' ,?)");
                        preparedStatement.setString(1, apple);
                        preparedStatement.executeUpdate();

                        JOptionPane.showMessageDialog(null, barcode1, "Qarz olingan maxsulotni puli to'landi", JOptionPane.INFORMATION_MESSAGE);


                        qarzTable();


                    } catch (Exception exc) {
                    } finally {
                        // DaoUtils.close(preparedStatement);

                    }
                }
            }else
            {
                JOptionPane.showMessageDialog(null,"Olib tashlashga hech narsa tanlamagan", "Xatolik", JOptionPane.ERROR_MESSAGE);

            }


    }

    private void setQarztablerowselect() throws Exception {
        try {

            QarzlarTable.setOnMouseClicked(event -> {
                QarzTable qarzTable = (QarzTable) QarzlarTable.getItems().get(QarzlarTable.getSelectionModel().getSelectedIndex());

                try {
                    String idQarzlar = String.valueOf(qarzTable.getIdQ());

                    Qarzid.setText(idQarzlar);

                } catch (Exception exc) {

                }


            });
        } catch (Exception exe) {

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


                PreparedStatement preparedStatement = null;


                try {
                    preparedStatement = myConn.prepareStatement("DELETE FROM savdoAction ");

                    int delete = preparedStatement.executeUpdate();
                    preparedStatement = myConn.prepareStatement("DELETE FROM history");
                    preparedStatement.executeUpdate();
                    preparedStatement = myConn.prepareStatement("DELETE FROM maxsulotlar");
                    preparedStatement.executeUpdate();
                    preparedStatement = myConn.prepareStatement("DELETE FROM product_type");
                    preparedStatement.executeUpdate();
                    preparedStatement = myConn.prepareStatement("DELETE FROM sotuvchi");
                    preparedStatement.executeUpdate();
                    preparedStatement = myConn.prepareStatement("DELETE FROM printer");
                    preparedStatement.executeUpdate();

                    preparedStatement = myConn.prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Barcha malumotlar ochirildi' ,current_date)");
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, " Barcha malumotlar tozalandi", "Barcha malumotlar o'chirib tashlandi", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception exc) {

                    JOptionPane.showMessageDialog(null, exc, "Bildirgi", JOptionPane.ERROR_MESSAGE);

                } finally {
                    //DaoUtils.close(preparedStatement);

                }
            }
    }
    @FXML
    private void TableDDLChange() throws SQLException {

        LocalDate DeptData1 = DDL_date1.getValue();
        LocalDate DeptData2 = DDL_date2.getValue();


        Statement myStmt = null;
        ResultSet myRs = null;
        //List to add items
        ObservableList<TableHistory> appleQ = FXCollections.observableArrayList();
        try {

            if (DeptData1 != null && DeptData2 != null) {

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String Qdate1 = df.format(Date.from(DeptData1.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                String Qdate2 = df.format(Date.from(DeptData2.atStartOfDay(ZoneId.systemDefault()).toInstant()));

                preparedStatement = myConn.prepareStatement("SELECT * FROM Table_history1\n" +
                        "WHERE substr(change_time,7,10)\n" +
                        "BETWEEN ? AND ?");
                preparedStatement.setString(1, Qdate1);
                preparedStatement.setString(2, Qdate2);
                myRs = preparedStatement.executeQuery();
            } else {

                myStmt = myConn.createStatement();

                myRs = myStmt.executeQuery("SELECT * FROM (\n" +
                        "    SELECT * FROM Table_history1 ORDER BY change_time DESC LIMIT 300\n" +
                        ") sub\n" +
                        "ORDER BY change_time ASC");
            }

            while (myRs.next()) {
                TableHistory tableHistory = new TableHistory();

                tableHistory.setOwener(myRs.getString("owner"));
                tableHistory.setDescription(myRs.getString("description"));
                tableHistory.setChangeTime(myRs.getString("change_time"));

                appleQ.add(tableHistory);
            }
            MuhimChangeTable.setItems(appleQ);


        } catch (Exception exc) {
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        } finally {
            // DaoUtils.close(myStmt);
        }


    }

    private void OmborHisobiTable() throws SQLException {

        Statement myStmt = null;
        ResultSet myRs = null;
        ResultSet ObRS = null;
        //List to add items
        ObservableList<OmborHisobi> tableHistories = FXCollections.observableArrayList();
        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT * FROM OmborHisoblari ORDER BY item_quantity");

            preparedStatement = myConn.prepareStatement("SELECT  sum(item_total_cost) obTotal_cost,sum(item_total_sale_cost) itsCost FROM OmborHisoblari ");
            ObRS = preparedStatement.executeQuery();

            while (myRs.next()) {
                OmborHisobi omborHisobi = new OmborHisobi();

                omborHisobi.setBarcodeOH(myRs.getString("item_barcode"));
                omborHisobi.setNameOH(myRs.getString("item_name"));
                omborHisobi.setTypeOH(myRs.getString("item_type"));
                omborHisobi.setQuantityOH(myRs.getInt("item_quantity"));
                omborHisobi.setCostOH(new BigDecimal(myRs.getDouble("item_cost")).toPlainString());
                omborHisobi.setSalaCostOH(new BigDecimal(myRs.getDouble("item_sale_cost")).toPlainString());
                omborHisobi.setItem_total_cost(new BigDecimal(myRs.getDouble("item_total_cost")).toPlainString());
                omborHisobi.setItem_total_sale_cost(new BigDecimal(myRs.getDouble("item_total_sale_cost")).toPlainString());

                tableHistories.add(omborHisobi);
            }
            OmborHisobiTable.setItems(tableHistories);

            if (ObRS.next()) {
                OUmumiySumma.setText(new BigDecimal(ObRS.getDouble("obTotal_cost")).toPlainString() + ".00 sum");
                OmborChegirmaLabel.setText(new BigDecimal(ObRS.getDouble("itsCost")).toPlainString() + ".00 sum");
            }


        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
            exc.printStackTrace();
        } finally {
            //DaoUtils.close(myStmt);
        }


    }

    public void excelToDB() throws FileNotFoundException {


        FileChooser fileChooser = new FileChooser();
        Stage stage = null;

        File selectFile = fileChooser.showOpenDialog(stage);

        FileInputStream file2 = new FileInputStream("E:\\OmborXisobi.xls");


        try {

            String Q1 = "INSERT INTO maxsulotlar(item_barcode, item_name, item_type, item_quantity,item_cost,item_sale_cost,item_date, item_expire_date,item_suplier,item_turlari) VALUES(?, ?, ?, ? , ? , ? , ? ,? ,? , ?)";
            preparedStatement = myConn.prepareStatement(Q1);

            XSSFWorkbook wb = new XSSFWorkbook(file2);
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
                preparedStatement.setString(9, row.getCell(8).getStringCellValue());
                preparedStatement.setString(10, row.getCell(9).getStringCellValue());
                // preparedStatement.setString(, row.getCell(10).getStringCellValue());
                preparedStatement.execute();

                preparedStatement.close();
                wb.close();
                myConn.close();


                JOptionPane.showMessageDialog(null, "All records are imported to database", "Ijobiy", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception exe) {
            JOptionPane.showMessageDialog(null, "Exel fileni qo'shishda muammo kuzatildi" + exe, " Xatolik", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void ADDSeller() throws Exception {


        sotuvchiQoshish();
        Update();


    }

    private void sotuvchiQoshish() {
        String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText(" Yangi sotuvchini qo'shasizmi ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent())
            if (result.get() == ButtonType.OK) {


                PreparedStatement preparedStatement = null;
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

                    JOptionPane.showMessageDialog(null, "Xatolik" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
                } finally {


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
                }
            });
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ozgartirishSotuvchi() {
        String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText("Sotuvchi malumotlarini o'zgartirasizmi ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent())
            if (result.get() == ButtonType.OK) {

                PreparedStatement preparedStatement = null;
                try {
                    String nameSotuvchi = firstNameSo.getText().toString();
                    String lastnameSotuvchi = lastNameS.getText().toString();
                    String jobTitleSotuvchi = jobTieleS.getText().toString();
                    Double salarySotuvchi = Double.parseDouble(salaryS.getText());
                    String emailSotuvchi = emailS.getText().toString();
                    String passwordSotuvchi = passwordS.getText().toString();
                    String telephoneSotuchi = telephoneS.getText().toString();
                    String birthDateSotuvchi = birthDateS.getValue().toString();

                    preparedStatement = myConn.prepareStatement("UPDATE  sotuvchi set  first_name=?, last_name=?, tel_number=?,salary=?, date=?, lavozim=?, password=? WHERE email=?");

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

                    JOptionPane.showMessageDialog(null, "Sotuvchi  malumotlari ozgartirildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception exc) {

                    JOptionPane.showMessageDialog(null, "Xatolik" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);
                } finally {
                    //  DaoUtils.close(preparedStatement);

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

    private void sotuvchiDelete() {
        String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O'zgartirish");
        alert.setHeaderText(null);
        alert.setContentText(" Sotuvchini rostdan ham o'chirasizmi ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent())
            if (result.get() == ButtonType.OK) {

                PreparedStatement preparedStatement = null;
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
                } finally {
                    // DaoUtils.close(preparedStatement);

                }


            }
    }

    private void SotuvchiCombobox() throws SQLException {
        Statement statement = null;
        ResultSet resultSet;

        statement = myConn.createStatement();

        resultSet = statement.executeQuery("SELECT first_name FROM sbd_market.sotuvchi");

        while (resultSet.next()) {  // loop

            // Now add the comboBox addAll statement
            Xcombo1.getItems().addAll(resultSet.getString("first_name"));

        }


    }

    public void AddTypeComboboxAction() throws SQLException {
        Statement statement = null;
        ResultSet resultSet;

        statement = myConn.createStatement();

        resultSet = statement.executeQuery("SELECT Name FROM sbd_market.product_type");

        while (resultSet.next()) {  // loop

            // Now add the comboBox addAll statement
            ComboTypeList.getItems().addAll(resultSet.getString("name"));

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

    public void XisoblarToExcel() throws SQLException {

        LocalDate date1 = XDateValue1.getValue();
        LocalDate date2 = XDateValue2.getValue();
        if (date1 != null && date2 != null) {

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String sdate1 = df.format(Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            String sdate2 = df.format(Date.from(date2.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            PreparedStatement preparedStatement = null;
            ResultSet rs = null;
//
//            preparedStatement = myConn.prepareStatement("SELECT * FROM collapsedCreditHistoryAll\n" +
//                    "WHERE substr(paid_date,7,10)\n" +
//                    "BETWEEN ? AND ?");
//            preparedStatement.setString(1, sdate1);
//            preparedStatement.setString(2, sdate2);
//
//            rs = preparedStatement.executeQuery();


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

    public void PrinterAddAction() {


        TextInputDialog dialog = new TextInputDialog("Printer");
        dialog.setTitle("Printer nomini qo'shish");
        dialog.setHeaderText("Iltimos printer nomini qo'shing");
        dialog.setContentText("Printer nomi");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println(result.get());

            String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());

            PreparedStatement preparedStatement = null;


            try {
                preparedStatement = myConn.prepareStatement("UPDATE printer set name=?");
                preparedStatement.setString(1, result.get());
                preparedStatement.executeUpdate();

                preparedStatement = myConn.prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Printer nomi qoshildi' ,?)");
                preparedStatement.setString(1, apple);
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Yangi printer nomi qo'shildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void SetExcelFolder() {

        DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage stage = null;

        File dir = directoryChooser.showDialog(stage);

        System.out.println(dir.getAbsolutePath() + "\\");
        String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());

        String Path = dir.getAbsolutePath() + "\\";

        try {
            preparedStatement = myConn.prepareStatement("UPDATE printer set Path=?");
            preparedStatement.setString(1, Path);
            preparedStatement.executeUpdate();

            preparedStatement = myConn.prepareStatement("INSERT  INTO Table_history1 VALUES ('Admin','Excel joylashuvi qoshildi' ,?)");
            preparedStatement.setString(1, apple);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Excel yanaratiladigan joy qo'shildi", "Bildirgi", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*
     *
     * AddType function
     *
     */
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
            // Hide this current window (if this is what you want)

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
            // Hide this current window (if this is what you want)

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void GenerateBarcode(){


        if (AdminTextBarcode.getText().isEmpty()){
            String testCode = String.valueOf(numbGen());

            BarCodeService serv = new BarCodeService();
            String parsedString = serv.parseInput(testCode);
            AdminTextBarcode.setText(parsedString);
            //System.out.println("Input: " + testCode + ", parsed string: " + parsedString);
            String barCodeString = serv.generateCode(parsedString);
           // System.out.println("Result: " + barCodeString);
        }else {
            String barcode= AdminTextBarcode.getText();
            int lenght =barcode.length();
            if(lenght==13) {
                BarCodeService serv = new BarCodeService();
                String parsedString = serv.parseInput(barcode);
                AdminTextBarcode.setText(parsedString);
              //  System.out.println("Input: " + barcode + ", parsed string: " + parsedString);
                String barCodeString = serv.generateCode(parsedString);
              //  System.out.println("Result: " + barCodeString);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Barcodega 13 honali son kiritilishi shart", "Bildirgi", JOptionPane.ERROR_MESSAGE);

            }
        }


    }



}





