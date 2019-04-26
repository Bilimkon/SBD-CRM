package sample.DAO;

import org.jetbrains.annotations.Nullable;
import sample.Core.History;
import sample.Core.Models.BasketItem;
import sample.Core.Models.CreditModel;
import sample.Core.Product;
import sample.Core.User;
import sample.LoginController;
import sample.Utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDao {
    private Connection myConn;
    public static String historyTableName = "history";
    PreparedStatement pt;
    ResultSet rs;
    Statement st;

    public HistoryDao() throws Exception {

        myConn =Database.getConnection();// DriverManager.getConnection("jdbc:sqlite:" + DaoUtils.tableName);
    }

    public HistoryDao(Connection c) {
        myConn = c;
    }

    public List<History> getHistoryList() throws SQLException {
        //sotuvchi_id, item_name, item_type, item_quantity, tarix_id, item_barcode, paid_cost, paid_date, total_cost, remaining_cost, karta, action, action_time, karta_cost
        String query = "SELECT * FROM (\n" +
                "    SELECT * FROM history ORDER BY id DESC LIMIT 200\n" +
                ") sub\n" +
                "ORDER BY id ASC";
        ResultSet set = generateResultSet(query);
        List<History> histories = new ArrayList<>();
        while (set.next()) {
            History h = new History(
                    set.getInt("id"),
                    set.getString("product_barcode"),
                    set.getInt("product_id"),
                    set.getString("product_name"),
                    set.getInt("product_type"),
                    set.getInt("product_quantity"),
                    set.getInt("seller_id"),
                    set.getString("cost"),
                    set.getString("date_cr"),
                    set.getInt("cr_by"),
                    set.getString("date_up"),
                    set.getInt("date_up"),
                    set.getInt("customer_id"),
                    set.getInt("sell_action_id"));
            histories.add(h);
        }
        return histories;
    }

    public void insertBasketToHistory(List<BasketItem> basket, User user, @Nullable CreditModel credit, String total_cost, String cash,  String  card, String sale, String cr_by, String cost_paid ) throws Exception {
        try {
            ProductDao productDao = new ProductDao(myConn);
            if (credit == null) {
                credit = new CreditModel(0, 0);
            }
            String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
            pt = myConn.prepareStatement("insert  into new_sbd.sellaction(total_cost, cash, card, sale, credit, date_cr, cr_by, cost_paid, customer_id) values (?,?,?,?,?,?,?,?,?)");
            pt.setString(1, total_cost);
            pt.setString(2, cash);
            pt.setString(3, card);
            pt.setString(4, sale);
            pt.setString(5, String.valueOf(credit.getSumma()));
            pt.setString(6, apple);
            pt.setString(7, cr_by);
            pt.setString(8, cost_paid);
            pt.setString(9, String.valueOf(credit.getId()));
            pt.executeUpdate();

        int actionId = createAction();
        for (BasketItem item : basket) {
            Product p = productDao.getProduct(item.getBarcode());
            float totalCost = item.getAmount() * item.getCost();
            pt=myConn.prepareStatement("INSERT INTO new_sbd.history(product_barcode," +
                    " product_id, product_name, product_type, product_quantity, seller_id," +
                    " cost, date_cr, cr_by, customer_id, sell_action_id)" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            pt.setString(1, item.getBarcode());
            pt.setInt(2,p.getId());
            pt.setString(3,item.getTitle());
            pt.setInt(4,p.getType_id());
            pt.setInt(5,item.getAmount());
            pt.setString(6, String.valueOf(LoginController.currentUser.getId()));
            pt.setFloat(7,totalCost);
            pt.setString(8,apple);
            pt.setString(9,String.valueOf(LoginController.currentUser.getId()));
            pt.setString(10, String.valueOf(credit.getId()));
            pt.setInt(11,actionId);
            pt.executeUpdate();
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            pt.close();
        }
    }

    private int createAction() {
        try {
            String q = "select max(id) as 'last_item_id' from new_sbd.sellaction";
            ResultSet resultSet = generateResultSet(q);
            if (resultSet.next()) {
                return resultSet.getInt("last_item_id");
            } else return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private ResultSet generateResultSet(String query) throws SQLException {
        Statement myStmt = myConn.createStatement();
        return myStmt.executeQuery(query);
    }

    private boolean executeQuery(String query) throws SQLException {
        Statement myStmt = myConn.createStatement();
        return myStmt.execute(query);
    }

    private void insertIntoHistory(History history, int action_id) throws SQLException {
        String query = createHistoryQuery(history, action_id);
        System.out.println("executing query insert History: " + executeQuery(query));
    }

    private String createHistoryQuery(History history, int action_id) {

        int itemId = history.getProduct_id();
        String barcode = history.getProduct_barcode();
        String name = history.getProduct_name();
        int type =history.getProduct_type();
        int quantity =history.getProduct_quantity();
        int seller_id = history.getSeller_id();
        String cost =history.getCost();
        String date_cr =history.getDate_cr();
        int cr_by = history.getCr_by();
        String date_up =history.getDate_up();
        int up_by =history.getUp_by();
        int customer_id =history.getCustomer_id();
       // int sell_action_id =history.getSell_action_id();

        return "INSERT INTO " + historyTableName + " (" +
                "product_barcode, " +
                "product_id, " +
                "product_name, " +
                "product_type, " +
                "product_quantity, " +
                "seller_id, " +
                "cost, " +
                "date_cr," +
                "cr_by," +
                "date_up," +
                "up_by, " +
                "customer_id," +
                "sell_action_id" + ") VALUES(" + barcode + ", " + itemId + ", '" + name
                + "', '" + type + "', " + quantity + ", '" + seller_id + "', '" + 1
                + "', " + date_cr + ", " + cr_by + ", " + date_up+ ", " +up_by+ ", " +customer_id
                + ", " +action_id+ ")";
    }
}
