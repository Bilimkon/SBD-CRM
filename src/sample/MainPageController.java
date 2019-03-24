package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.jetbrains.annotations.Nullable;
import sample.Core.Models.BasketItem;
import sample.Core.Models.CreditModel;
import sample.Core.Models.ReceiptCheck;
import sample.Core.User;
import sample.DAO.Database;
import sample.DAO.HistoryDao;
import sample.DAO.ProductDao;
import sample.DAO.printer;
import sample.Design_fxml.CustomItems.CustomBasketItem.ShopItemListItem;
import sample.MaxsulotTableView.MaxsulotTable;
import sample.MaxsulotTableView.ProductTable;
import sample.Utils.PrinterService;
import sample.Utils.Utils;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class MainPageController extends Parent implements Initializable {
    private ProductDao productDao;
    private MaxsulotTable maxsulotTable;

    private float cardCost = 0;

    @FXML
    private Button btnDiscount;


    @FXML
    private TextField textSampleIzlash;

    private Connection myConn;

    @FXML
    private TableView<ProductTable> tableSampleManual;

    @FXML
    private VBox addedItemsList;

    @FXML
    private Label totalCost;

    @FXML
    private TextField scanCodeField;

    @FXML
    private Label idUserName;

    @FXML
    private Label idStartDate;

    @FXML
    private Button idTotalSum;

    @FXML
    private GridPane topLeftGrid;

    @FXML
    private ScrollPane scrollView;

    @FXML
    private Label ClockText;
    @FXML
    private Label DateText;
    @FXML private  Button BtnSell;
    public static String TotalCost2;
    public static List<BasketItem> basket = new ArrayList<>();
    private CreditModel credit = null;

    private void initializeTable() {
        TableColumn<ProductTable, String> barcode = new TableColumn<>("Barcode");
        TableColumn<ProductTable, String> name = new TableColumn<>("Nomi");
        TableColumn<ProductTable, String> type = new TableColumn<>("Turi");
        TableColumn<ProductTable, Integer> quantity = new TableColumn<>("Miqdori");
        TableColumn<ProductTable, Double> cost = new TableColumn<>("Narxi");

        tableSampleManual.getColumns().addAll(barcode, name, type, quantity, cost);

        barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        addedItemsList.getChildren().addListener((ListChangeListener<Node>) c -> {
//            List<Node> n = addedItemsList.getChildren();
            float sum = calculateCurrentTotalSum();
//            for (int i = 0; i < n.size(); i++) {
//                BasketItem basketItem = (BasketItem) (n.get(i)).getUserData();
//                sum += (basketItem.getCost() * basketItem.getAmount());
//            }
            totalCost.setText(new BigDecimal(sum).toPlainString() +" sum");
        });
        tableSampleManual.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
            try {
                if (tableSampleManual.getSelectionModel().getSelectedItem() != null) {
                    ProductTable productTable = tableSampleManual.getSelectionModel().getSelectedItem();
                    addProductTableToList(productTable);
                    tableSampleManual.getSelectionModel().clearSelection();
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        scanCodeField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    onCodeScanClicked();
                }
            }
        });
        scanCodeField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F6) {
                    actionSell();
                }
            }
        });

        scanCodeField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F7) {
                    isCardCheck.setSelected(true);
                    cardSummField.setText(String.valueOf(calculateCurrentTotalSum()));
                    scanCodeField.requestFocus();

                }
            }
        });

        tableSampleManual.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.F6){
                    actionSell();
                }
            }
        });
        tableSampleManual.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()==KeyCode.F7){
                    PrintReport();
                }
            }
        });
        tableSampleManual.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()==KeyCode.F5){
                    btnActionIzlash();
                    textSampleIzlash.requestFocus();
                }
            }
        });
        textSampleIzlash.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.F6){
                    actionSell();
                }
            }
        });
        scrollView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.F6){
                    actionSell();
                }
            }
        });

    }

    /*
    * Sell action with Enter
    */

    private float calculateCurrentTotalSum() {
        float sum = 0;
        for (BasketItem aBasket : basket) {
            sum += (aBasket.getCost() * aBasket.getAmount());
        }
        return sum;
    }

    public boolean updateListItemAmount(BasketItem basketItem) {
        List<Node> items = addedItemsList.getChildren();
        for (Node item : items) {
            BasketItem i = (BasketItem) item.getUserData();
            if (i.isEqual(basketItem.getBarcode())) {
                TextField field = (TextField) item.lookup("#amountField");
                field.setText((Integer.valueOf(field.getText()) + 1) + "");
                changeBasketItemAmount(i.getBarcode(), i.getAmount() + 1);
            }
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeTable();
        User u = LoginController.currentUser;
        cardSummField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Utils.isNumberValid(newValue, Utils.Number.FLOAT)) {
                if (newValue.length() > 1 & newValue.charAt(0) == '0') {
                    cardSummField.setText(newValue.replaceFirst("0", ""));
                }
                cardCost = Float.parseFloat(cardSummField.getText());
                cardSum.setText(new BigDecimal(cardCost).toPlainString()+ " sum");
            } else {
                Platform.runLater(() -> {
                    cardSummField.setText("0");
                    cardSummField.selectAll();
                    cardCost = 0;
                });
            }
        });

        if (u == null) {
            u = new User();
            u.setFirstName("Muhammadjo");
            u.setLastName("Toxirov");
            u.setId(1);
            LoginController.currentUser = u;
            u.setDate(Calendar.getInstance().getTime());
        }
        setUserData(u);
        Clock();
        Thread.currentThread();
        System.out.println(Thread.currentThread().getName());
        scanCodeField.requestFocus();
    }

    private void setUserData(User u) {
        idUserName.setText(u.getFirstName() + " " + u.getLastName());
        idStartDate.setText(Utils.getCurrnetDateInStandardFormat());
        idTotalSum.setText("0.0 sum");
    }
    private  void inserToHistoryTable(User u){

        String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = myConn.prepareStatement("INSERT  INTO Table_history1 VALUES (?,'maxsulot sotildi' ,? )");
            preparedStatement.setString(1,u.getFirstName()+" " +u.getLastName());
            preparedStatement.setString(2,apple);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private int counter = 0;

    private void addProductTableToList(ProductTable productTable) {
        FXMLLoader loader = createCustomItemLoader("AddedItemListItem", "CustomBasketItem/");
        assert loader != null;
        try {
            BasketItem basketItem = BasketItem.getInstance();
            basketItem.setAll(productTable.getBarcode(), productTable.getName(), (float) productTable.getCost(), 1, true);
            Pane p = loader.load();
            p.setUserData(basketItem);
            ShopItemListItem item = loader.getController();

            item.setDetails(productTable, basketItem.isAccepted());

            for (BasketItem b : basket) {
                if (b.isEqual(basketItem.getBarcode())) {
                    updateListItemAmount(b);
                    return;
                }
            }
            counter++;
            item.id = counter;

            basket.add(basketItem);
            addedItemsList.getChildren().add(p);

            item.setPane(p);
           TextField field = (TextField) p.lookup("#amountField");
            field.requestFocus();
            field.selectAll();

            field.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    Label l = (Label) p.lookup("#itemPrice");
                    BasketItem i = (BasketItem) p.getUserData();
                    int quantity = productDao.getQuantity(i.getBarcode());

                    if (!Utils.isNumberValid(newValue, Utils.Number.INT) || newValue.equals("")) {

                        field.setText("0");
                        return;
                    }

                    if (!Utils.isNumberValid(newValue, Utils.Number.INT) || oldValue.equals("")) {
                        field.setText("0");
                        return;
                    }

                    if (newValue.length() > 1 & newValue.charAt(0) == '0') {
                        field.setText(newValue.replaceFirst("0", ""));
                        return;
                    }

                    int number = Utils.isNumberInRange(Integer.valueOf(newValue), 0, quantity);
                    if (number != Integer.valueOf(newValue)) {
                        field.setText(number + "");
                        field.selectAll();
                    } else {
                        int finalNewValue = Integer.parseInt(newValue);
                        Platform.runLater(() -> {
                            try {
                                changeBasketItemAmount(i.getBarcode(), finalNewValue);
                                currentTotalCost = calculateCurrentTotalSum();
                                totalCost.setText(new BigDecimal(currentTotalCost).toPlainString() + " sum");
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        });
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //
    private void changeBasketItemAmount(String barcode, int amount) {
        for (BasketItem item : basket) {
            if (item.getBarcode().equals(barcode)) {
                item.setAmount(amount);
                return;
            }
        }
    }

    public MainPageController() {
        try {
            myConn = Database.getConnection();
            productDao = new ProductDao();
        } catch (Exception exc) {
            Utils.ErrorAlert("Error","Xatolik"+exc, "Xatolik bor shu yerda");
        }
    }

    @FXML
    private Label cardSum;
    @FXML
    private Label creditSum;

    public void onBtnDiscountClicked() {
        try {

            FXMLLoader loader = createCustomItemLoader("DiscountWindow", "CustomDiscountWindow/");
            assert loader != null;
            VBox vBox = loader.load();
            PopOver popOver = new PopOver(vBox);

            Button btnCancel = (Button) vBox.lookup("#btnCancel");
            Button btnOK = (Button) vBox.lookup("#discountOK");
            TextField summFiled = (TextField) vBox.lookup("#summField");
            TextArea descrField = (TextArea) vBox.lookup("#descrField");

            if (credit != null) {
                summFiled.setText(credit.getSumma() + "");
                descrField.setText(credit.getDescription());
            }

            btnOK.setOnMouseClicked(event -> {
                if (Utils.isNumberValid(summFiled.getText(), Utils.Number.FLOAT) && !descrField.getText().trim().equals("")) {
                    Float credit_sum = Float.valueOf(summFiled.getText());
                    currentTotalCost = calculateCurrentTotalSum();
                    if (credit_sum <= currentTotalCost - cardCost) {
                        credit = new CreditModel(credit_sum, descrField.getText().trim());
                        popOver.hide();
                        creditSum.setText(new BigDecimal(credit.getSumma()).toPlainString() + " sum");
                    } else {
                        Utils.ErrorAlert("Xatolik mavjud", "summada " + credit_sum, "siz " + (currentTotalCost - cardCost) + " dan kamroq summa kiritishingiz kerak");
                    }
                    popOver.hide();
                } else {
                    Utils.ErrorAlert("Xatolik mavjud", "Summa da yoki Izohda", "Siz izoh ni yozishingiz shart va summa kiritayotganizda faqat raqamlardan foydalanaing");
                }
            });

            popOver.setDetachedTitle("Kreditga berish");

            popOver.detach();

            btnCancel.setOnMouseClicked(event -> popOver.hide());

            popOver.show(btnDiscount);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onCodeScanClicked() {
        try {
            ProductDao dao = new ProductDao();
            ProductTable pt = dao.convertProductToProductTable(dao.getProduct(scanCodeField.getText().trim()));
            addProductTableToList(pt);
            scanCodeField.setText("");
            scanCodeField.requestFocus();
        } catch (Exception e) {
            System.out.println("no such item: " + scanCodeField.getText());
        }
    }

    @FXML
    private CheckBox checkDiscount;

    public void onSetDiscountChecked() {
        if (checkDiscount.isSelected()) {
            btnDiscount.setDisable(false);
        } else {
            credit = null;
            creditSum.setText("0.0 sum");
            btnDiscount.setDisable(true);
        }
    }

    @FXML
    private TextField cardSummField;

    @FXML
    private CheckBox isCardCheck;

    public void onIsFromCardSelected() {
        if (isCardCheck.isSelected()) {
            cardSummField.setDisable(false);
            cardSummField.setText(String.valueOf(calculateCurrentTotalSum()));
        } else {
            cardSummField.setDisable(true);
            cardSummField.setText("");
            cardCost = 0;
            cardSum.setText("0.0 sum");
        }
    }

    @FXML
    public void btnActionIzlash() {
        String SearchItemName = textSampleIzlash.getText();
        buildData(SearchItemName);
    }

    private ObservableList<ProductTable> productTables;

    private void buildData(String name) {
        productTables = FXCollections.observableArrayList();
        ResultSet myRs;
        try {
            myRs = ProductDao.getResultSet(name, textSampleIzlash, myConn);
            ProductDao.productTableGenerator(myRs, productTables);
            tableSampleManual.setItems(productTables);
            System.out.println("number of items: " + tableSampleManual.getItems().size());
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * on cancelShop button clicked
     */
    public void cancelShop() {
        reset();
    }

    private FXMLLoader createCustomItemLoader(String title, @Nullable String folder) {
        try {
            if (folder == null) folder = "";
            String path = folder + title + ".fxml";
            return new FXMLLoader(getClass().getResource("Design_fxml/CustomItems/" + path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * on action sell
     */
    public void actionSell() {

        User u =LoginController.currentUser;
        for (BasketItem item : basket) {
            System.out.println(item.getBarcode() + " " + item.getAmount());
        }

        float sum1 = calculateCurrentTotalSum();

        if(sum1>0) {

                    PreparedStatement preparedStatement;

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Savdoni amalga oshirish");
                    alert.setHeaderText(null);
                    alert.setContentText("Savdoni amalga oshirasizmi?");

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent())
                        if (result.get() == ButtonType.OK) {
                            HistoryDao historyDao = new HistoryDao(myConn);

                            try {
                                currentTotalCost = calculateCurrentTotalSum();
                                historyDao.insertBasketToHistory(basket, LoginController.currentUser, credit, cardCost);

                                idTotalSum.setText(new BigDecimal(currentTotalCost + Float.parseFloat(idTotalSum.getText().split(" ")[0])).toPlainString() + " sum");
                                reset();
                                inserToHistoryTable(u);
                                btnActionIzlash();
                                printAction();
                                scanCodeField.requestFocus();
                            } catch (Exception e) {
                                Utils.ErrorAlert("Xatolik", "Savdo amalga oshmadi", e.getMessage());

                                System.out.println(e.getMessage());
                            }
                        }
                }
        else{
            JOptionPane.showMessageDialog(null, "Sotish uchun hech narsa tanlanmagan!" , "Eslatma", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private float currentTotalCost = 0;

    private void reset() {
        creditSum.setText("0.0 sum");
        cardSum.setText("0.0 sum");
        credit = null;
        basket = new ArrayList<>();
        addedItemsList.getChildren().removeAll(addedItemsList.getChildren());
        totalCost.setText("0.0 sum");
        currentTotalCost = 0;
        counter = 0;
        checkDiscount.setSelected(false);
        isCardCheck.setSelected(false);
        cardSummField.setText("");
    }

    public void Clock() {
        Thread clock = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());

            while (Main.is_clock_alive) {
                Calendar cal = Calendar.getInstance();
                int second = cal.get(Calendar.SECOND);
                int minute = cal.get(Calendar.MINUTE);
                int hour = cal.get(Calendar.HOUR);

                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Date date  =new Date();

                try {
                    Platform.runLater(() -> {
                        String second_str = "" + second;
                        String min_str = "" + minute;
                        String hour_str = "" + hour;

                        if (minute < 10) {
                            min_str = "0" + minute;
                        }
                        if (hour == 0) {
                            hour_str = "12";
                        }
                        if (second < 10) {
                            second_str = "0" + second;
                        }
                        DateText.setText(dateFormat.format(date));
                        ClockText.setText(hour_str + ":"
                                + min_str + ":"
                                + second_str);
                    });

                    Thread.sleep(1000);
                }   catch (Exception exe) {
                    JOptionPane.showMessageDialog(null, exe);
                }
            }
        });

        clock.start();
    }
    public void CloseAction(){

    }
    public void BtnUpdateAction(){
        btnActionIzlash();
    }

    public void printAction(){
        String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());

        PrinterService printerService = new PrinterService();

        System.out.println(printerService.getPrinters());

        ArrayList<ReceiptCheck> receiptChecks =null;
        receiptChecks =PerProduct();

        StringBuilder storage = new StringBuilder();

        for (ReceiptCheck item : receiptChecks) {
          storage.append(item.getName()+ "    Miqdori: " + item.getQuantity()+"   Umumiy narxi: " +item.getPrice()+"\n"+"----------------------------------------------\n");
        }
        System.out.println(storage);

        System.out.println(TotalSum());

        printer  printer = new printer();


        //print some stuff
        printerService.printString(printer.printerName(), "\n" +
                "*********Software business development**********\n\n\n"+
                "*********    Egamberdi ota do'koni    **********\n\n"+
                storage+"\n"+
                "Umumiy summa              "+TotalSum()+" sum\n\n\n" +
                "***************"+apple+"***************\n"+
                "***********Xaridingiz uchun raxmat!***********\n\n\n\n\n\n\n\n");

        // cut that paper!
        byte[] cutP = new byte[] { 0x1d, 'V', 1 };

        printerService.printBytes(printer.printerName(), cutP);

    }

    public int  TotalSum(){

        Statement statement=null;
        ResultSet resultSet = null;

        try {
            statement=myConn.createStatement();

            resultSet =statement.executeQuery("select total_cost from collapsedCreditHistoryAll where id = (select max(id) as 'last_item_id' from sbd_market.savdoAction)");

            if(resultSet.next()){
                return resultSet.getInt("total_cost");
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return -1;

    }

    public  ArrayList<ReceiptCheck> PerProduct(){

        Statement statement=null;
        ResultSet resultSet = null;
        ArrayList<ReceiptCheck> receiptChecksList = new ArrayList<>();

        try {

            statement=myConn.createStatement();

            resultSet =statement.executeQuery("select * from history where savdo_action_id = (select max(id) as 'last_item_id' from sbd_market.savdoAction)");

            while (resultSet.next()){
                ReceiptCheck receiptCheck = new ReceiptCheck();
                receiptCheck.setId(resultSet.getInt("item_id"));
                receiptCheck.setName(resultSet.getString("item_name"));
                receiptCheck.setQuantity(resultSet.getInt("item_quantity"));
                receiptCheck.setPrice(resultSet.getDouble("total_cost"));
                receiptChecksList.add(receiptCheck);
            }
            return receiptChecksList;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }
    public void PrintReport(){

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Components/PrintReport.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Report");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.isAlwaysOnTop();

            stage.show();
            // Hide this current window (if this is what you want)

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
