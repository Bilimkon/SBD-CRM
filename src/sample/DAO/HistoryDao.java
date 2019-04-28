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

    public void insertBasketToHistory(List<BasketItem> basket, User user, @Nullable CreditModel credit, String total_cost, String cash,  String  card, String sale, String cr_by, String cost_paid, String commnet ) throws Exception {
        try {
            ProductDao productDao = new ProductDao(myConn);
            if (credit == null) {
                credit = new CreditModel(0, 0);
            }
            String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
            pt = myConn.prepareStatement("insert  into main.sellaction(total_cost, cash, card, sale, credit, date_cr, cr_by, cost_paid, customer_id,comment) values (?,?,?,?,?,?,?,?,?,?)");
            pt.setString(1, total_cost);
            pt.setString(2, cash);
            pt.setString(3, card);
            pt.setString(4, sale);
            pt.setString(5, String.valueOf(credit.getSumma()));
            pt.setString(6, apple);
            pt.setString(7, cr_by);
            pt.setString(8, cost_paid);
            pt.setString(9, String.valueOf(credit.getId()));
            pt.setString(10,commnet);
            pt.executeUpdate();

        int actionId = createAction();
        for (BasketItem item : basket) {
            Product p = productDao.getProduct(item.getBarcode());
            float totalCost = item.getAmount() * item.getCost();
            pt=myConn.prepareStatement("INSERT INTO main.history(product_barcode," +
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
            String q = "select max(id) as 'last_item_id' from main.sellaction";
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

    public void addCustomer(String firstname ,String lastname) throws SQLException {
        try {
            pt = myConn.prepareStatement("INSERT  INTO customer(firstName,lastName) values (?,?)");
            pt.setString(1,firstname);
            pt.setString(2,lastname);
            pt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            pt.close();
        }
    }
    public  ResultSet searchCustomers(String name) throws SQLException {
        if (name.isEmpty()) {
            st =  myConn.createStatement();
            rs = st.executeQuery("select * from main.customer order by firstname ");
        } else {
            name += "%";
            pt = myConn.prepareStatement("select * from main.customer where  main.customer.firstname like ?  order by main.customer.firstname");
            pt.setString(1, name);
            rs = pt.executeQuery();
        }
        return rs;
    }
}
