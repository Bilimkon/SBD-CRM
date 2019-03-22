package sample.DAO;

import org.jetbrains.annotations.Nullable;
import sample.Core.History;
import sample.Core.Models.BasketItem;
import sample.Core.Models.CreditModel;
import sample.Core.Product;
import sample.Core.User;
import sample.Utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDao {
    private Connection myConn;
    public static String historyTableName = "history";

    public HistoryDao() throws Exception {
       // Class.forName("org.sqlite.JDBC");
        // connect to database
        myConn =Database.getConnection();// DriverManager.getConnection("jdbc:sqlite:" + DaoUtils.tableName);
        System.out.println("History DAO - DB connection successful to jdbc:sqlite:SBD.sqlite");
    }

    public HistoryDao(Connection c) {
        myConn = c;
    }

    public List<History> getHistoryList() throws SQLException {
        //sotuvchi_id, item_name, item_type, item_quantity, tarix_id, item_barcode, paid_cost, paid_date, total_cost, remaining_cost, karta, action, action_time, karta_cost
        String query = "SELECT * FROM (\n" +
                "    SELECT * FROM history_v ORDER BY tarix_id DESC LIMIT 200\n" +
                ") sub\n" +
                "ORDER BY tarix_id ASC";
        ResultSet set = generateResultSet(query);
        List<History> histories = new ArrayList<>();
        while (set.next()) {
            History h = new History(
                    set.getInt("tarix_id"),
                    set.getString("item_name"),
                    set.getString("item_type"),
                    set.getInt("item_quantity"),
                    set.getString("paid_date"),
                    set.getFloat("total_cost"));

            histories.add(h);
        }
        return histories;
    }

    public void insertBasketToHistory(List<BasketItem> basket, User user, @Nullable CreditModel credit, float card) throws Exception {
        ProductDao productDao = new ProductDao(myConn);
        if (credit == null) {
            credit = new CreditModel(0, "");
        }
        int actionId = createAction(generateActionQuery(card, credit, Utils.getCurrentDate()));

        for (BasketItem item : basket) {
            Product p = productDao.getProduct(item.getBarcode());

            float totalCost = item.getAmount() * item.getCost();
            History h = new History(
                    0,
                    user.getId(),
                    p.getId(),
                    p.getItemName(),
                    p.getItemType(),
                    item.getAmount(),
                    item.getBarcode(),
                    Utils.getCurrnetDateInStandardFormat(),
                    totalCost);
            insertIntoHistory(h, actionId);
        }
    }

    private String generateActionQuery(float cardAmount, CreditModel credit, java.util.Date date) {

        String apple = Utils.convertDateToStandardFormat(date);

        return "insert into savdoAction (cardAmount, creditAmount, creditDescription, savdoTime) VALUES ("
                + cardAmount + ", "
                + credit.getSumma() + ", '"
                + credit.getDescription() + "', '"
                + apple + "')";
    }

    private int createAction(String actionQuery) {
        try {
            executeQuery(actionQuery);
            String q = "select max(id) as 'last_item_id' from sbd_market.savdoAction";
            ResultSet resultSet = generateResultSet(q);
            if (resultSet.next()) {
                return resultSet.getInt("last_item_id");
            } else return -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
        int itemId = history.getItemId();
        String barcode = history.getBarcode();
        String itemName = history.getItemName();
        Double itemQuantity = history.getItemQuantity();
        String itemType = history.getItemType();

        String paidDate = history.getPaidDate();
        Double totalCost = history.getTotalCost();
        int userID = history.getUserId();
        return "INSERT INTO " + historyTableName + " (" +
                "sotuvchi_id, " +
                "item_id, " +
                "item_name, " +
                "item_type, " +
                "item_quantity, " +
                "item_barcode, " +
                "paid_date, " +
                "total_cost, savdo_action_id) VALUES(" + userID + ", " + itemId + ", '" + itemName + "', '" + itemType + "', " + itemQuantity + ", '" + barcode + "', '"
                + paidDate + "', " +
                totalCost + ", " + action_id + ")";
    }
}
