package sample.DAO;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import sample.Core.Product;
import sample.MaxsulotTableView.ProductTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductDao {

    private Connection myConn;

    public ProductDao(Connection conn) {
        this.myConn = conn;
    }

    public ProductDao() throws Exception {
        //Class.forName("org.sqlite.JDBC");
        // connect to database
        myConn =Database.getConnection();// DriverManager.getConnection("jdbc:sqlite:" + DaoUtils.tableName);
       // System.out.println("Employee DAO - DB connection successful to jdbc:sqlite:" + DaoUtils.tableName);
    }

    public int getQuantity(String barcode) {
        try {
            Product p = getProduct(barcode);
            if (p != null) {
                return p.getItemQuantity();
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Search product with barcode
     *
     * @param barcode unique id of the product
     */
    public Product getProduct(String barcode) throws Exception {
        Product product;

        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("select * from maxsulotlar where item_quantity > 0 and maxsulotlar.item_barcode = '" + barcode + "' order by item_name ");
            if (myRs.next()) {
                product = convertRowToProduct(myRs);
                return product;
            }
        } finally {
            DaoUtils.close(myStmt, myRs);
        }
        return null;
    }

    public List<Product> getAllProduct() throws Exception {
        List<Product> list = new ArrayList<Product>();

        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("select * from maxsulotlar where item_quantity > 0 order by item_name ");

            while (myRs.next()) {
                Product tempEmployee = convertRowToProduct(myRs);
                list.add(tempEmployee);
            }

            return list;
        } finally {
            DaoUtils.close(myStmt, myRs);
        }
    }

    public static void productTableGenerator(ResultSet myRs, ObservableList<ProductTable> productTables) throws SQLException {
        while (myRs.next()) {
            ProductTable app = ProductTable.getInstance();
            app.setBarcode(myRs.getString("item_barcode"));
            app.setName(myRs.getString("item_name"));
            app.setType(myRs.getString("item_type"));
            app.setQuantity(myRs.getInt("item_quantity"));
            app.setCost(myRs.getDouble("item_cost"));
            //app.setDate(myRs.getString("item_date"));
            //app.setExpireDate(myRs.getString("item_expire_date"));
            //app.setSuplier(myRs.getString("item_suplier"));
            app.setTurlari(myRs.getString("item_turlari"));
            productTables.add(app);
        }
    }
    public static void productTableGeneratorAdmin(ResultSet myRs, ObservableList<ProductTable> productTables) throws SQLException {
        while (myRs.next()) {
            ProductTable app = ProductTable.getInstance();
            app.setId(myRs.getInt("id"));
            app.setBarcode(myRs.getString("item_barcode"));
            app.setName(myRs.getString("item_name"));
            app.setType(myRs.getString("item_type"));
            app.setQuantity(myRs.getInt("item_quantity"));
            app.setItemsalecost(myRs.getDouble("item_sale_cost"));
            app.setCost(myRs.getDouble("item_cost"));
            app.setDate(myRs.getString("item_date"));
            app.setExpireDate(myRs.getString("item_expire_date"));
            app.setSuplier(myRs.getString("item_suplier"));
            app.setTurlari(myRs.getString("item_turlari"));
            productTables.add(app);
        }
    }


    public static ResultSet getResultSet(String name, TextField textSampleIzlash, Connection myConn) throws SQLException {
        Statement myStmt;
        ResultSet myRs;
        PreparedStatement myStmt1;
        if (textSampleIzlash.getText().trim().isEmpty()) {

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery("select * from maxsulotlar where item_quantity > 0 order by item_name ");

        } else {
            name += "%";
            myStmt1 = myConn.prepareStatement("select * from maxsulotlar where item_quantity > 0 and maxsulotlar.item_name like ?  order by maxsulotlar.item_name");

            myStmt1.setString(1, name);

            myRs = myStmt1.executeQuery();
        }
        return myRs;
    }

    public List<Product> searchProduct(String SearchItemName) throws Exception {

        List<Product> list = new ArrayList<Product>();
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            SearchItemName += "%";
            myStmt = myConn.prepareStatement("select * from maxsulotlar where item_quantity > 0 and item_name like ?  order by item_name");

            myStmt.setString(1, SearchItemName);

            myRs = myStmt.executeQuery();

            while (myRs.next()) {
                Product product = convertRowToProduct(myRs);
                list.add(product);
            }

            return list;
        } finally {
            DaoUtils.close(myStmt, myRs);
        }
    }

    public ProductTable convertProductToProductTable(Product product) {
        ProductTable pt = ProductTable.getInstance();
        pt.setName(product.getItemName());
        pt.setBarcode(product.getItemBarcode());
        pt.setCost(product.getItemCost());
        pt.setDate(product.getItemDate());
        pt.setExpireDate(product.getItemExpireDate());
        pt.setQuantity(product.getItemQuantity());
        pt.setSuplier(product.getItemSuplier());
        pt.setTurlari(product.getItemTurlari());
        pt.setType(product.getItemType());
        return pt;
    }

    private Product convertRowToProduct(ResultSet myRs) throws SQLException {

        int id = myRs.getInt("id");
        String itemBarcode = myRs.getString("item_barcode");
        String itemName = myRs.getString("item_name");
        String itemType = myRs.getString("item_type");
        int itemQuantity = myRs.getInt("item_quantity");
        Float itemCost = myRs.getFloat("item_cost");
        String itemDate = myRs.getString("item_date");
        String itemExpireDate = myRs.getString("item_expire_date");
        String itemSuplier = myRs.getString("item_suplier");
        String itemTurlari = myRs.getString("item_turlari");

        Product tempMaxsulot = new Product(id, itemBarcode, itemName, itemType, itemQuantity, itemCost, itemDate, itemExpireDate, itemSuplier, itemTurlari);

        return tempMaxsulot;
    }
}
