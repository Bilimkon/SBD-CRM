package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sample.Core.Product;
import sample.MaxsulotTableView.ProductTable;
import sample.Utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductDao {

    private Connection myConn;
    PreparedStatement pr=null;
    Statement st=null;
    ResultSet rs=null;
    String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());

    public ProductDao(Connection conn) {
        this.myConn = conn;
    }
    public ProductDao() throws Exception {
        myConn =Database.getConnection();
    }

    public int getQuantity(String barcode) {
        try {
            Product p = getProduct(barcode);
            if (p != null) {
                return p.getQuantity();
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
            myRs = myStmt.executeQuery("select * from main.product where quantity > 0 and main.product.barcode = '" + barcode + "' order by name ");
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
            myRs = myStmt.executeQuery("select * from main.product where quantity > 0 order by name ");
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
            ProductTable app = new ProductTable();
            app.setBarcode(myRs.getString("barcode"));
            app.setName(myRs.getString("name"));
            app.setType(myRs.getString("type"));
            app.setQuantity(myRs.getInt("quantity"));
            app.setCost(myRs.getString("cost"));
            productTables.add(app);
        }
    }
    public static void productTableGeneratorAdmin(ResultSet myRs, ObservableList<ProductTable> productTables) throws SQLException {
        while (myRs.next()) {
            ProductTable app = new ProductTable();
            app.setId(myRs.getInt("id"));
            app.setBarcode(myRs.getString("barcode"));
            app.setName(myRs.getString("name"));
            app.setType(myRs.getString("type"));
            app.setQuantity(myRs.getInt("quantity"));
            app.setCost_o(myRs.getString("cost_o"));
            app.setCost(myRs.getString("cost"));
            productTables.add(app);
        }
    }

    public static ResultSet getResultSet(String name, TextField textSampleIzlash, Connection myConn) throws SQLException {
        Statement myStmt;
        ResultSet myRs;
        PreparedStatement myStmt1;
        if (textSampleIzlash.getText().trim().isEmpty()) {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("select * from main.product where quantity > 0 order by name ");
        } else {
            name += "%";
            myStmt1 = myConn.prepareStatement("select * from main.product where quantity > 0 and main.product.name like ?  order by main.product.name");
            myStmt1.setString(1, name);
            myRs = myStmt1.executeQuery();
        }
        return myRs;
    }

    public ResultSet searchProduct(String SearchItemName) throws Exception {
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try {
            SearchItemName += "%";
            myStmt = myConn.prepareStatement("select * from main.product where quantity > 0 and name like ?  order by name");
            myStmt.setString(1, SearchItemName);
            myRs = myStmt.executeQuery();
            return myRs;
        } finally {
            DaoUtils.close(myStmt, myRs);
        }
    }

    public ResultSet searchProductType(String Name) throws Exception {
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try {
            Name += "%";
            myStmt = myConn.prepareStatement("select * from main.product where quantity > 0 and type like ?  order by name");
            myStmt.setString(1, Name);
            myRs = myStmt.executeQuery();
            return myRs;
        }
        finally {
        }
    }

    public ListView<String> typeList(ListView<String> list) throws SQLException {
        ObservableList<String> items = FXCollections.observableArrayList();
        Statement st=null;
        ResultSet rs=null;
        try {
            st = myConn.createStatement();
            rs = st.executeQuery("SELECT name from main.type");
            while (rs.next()) {
                items.add(rs.getString("name"));
            }
            list.setItems(items);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            st.close();
            rs.close();
        }
        return list;
    }

    public ProductTable convertProductToProductTable(Product product) {
        ProductTable pt = new ProductTable();
        pt.setId(product.getId());
        pt.setName(product.getName());
        pt.setBarcode(product.getBarcode());
        pt.setType(product.getType());
        pt.setType_id(product.getType_id());
        pt.setCost(product.getCost());
        pt.setCost_o(product.getCost_o());
        pt.setQuantity(product.getQuantity());
        pt.setDate_c(product.getDate_c());
        pt.setDate_o(product.getDate_o());
        pt.setCr_by(product.getCr_by());
        pt.setDate_cr(product.getDate_cr());
        pt.setUp_by(product.getUp_by());
        pt.setDate_up(product.getDate_up());
        return pt;
    }

    private Product convertRowToProduct(ResultSet myRs) throws SQLException {

        int id = myRs.getInt("id");
        String barcode =myRs.getString("barcode");
        String name =myRs.getString("name");
        String type =myRs.getString("type");
        int type_id =myRs.getInt("type_id");
        String cost = myRs.getString("cost");
        int quantity =myRs.getInt("quantity");
        String cost_o =myRs.getString("cost_o");
        String date_c = myRs.getString("date_c");
        String date_o =myRs.getString("date_o");
        int cr_by = myRs.getInt("cr_by");
        String date_cr =myRs.getString("date_cr");
        int up_by =myRs.getInt("up_by");
        String date_up =myRs.getString("date_up");

        Product tempMaxsulot = new Product(id, barcode, name, type, type_id, cost, quantity, cost_o, date_c, date_o,cr_by,date_cr,up_by,date_up);

        return tempMaxsulot;
    }

    public void addProduct(String pBarcode, String pName ,String pType ,String pTannarx ,String pCost ,String unit ,String pQuantity ,String pDan ,String pGacha ,String pSuplier) throws SQLException {

        try {
            pr = myConn.prepareStatement("INSERT INTO product(barcode, name, " +
                    "type, type_id, cost,unit, quantity, cost_o, date_c," +
                    " date_o, cr_by, date_cr, up_by, date_up, suplier_id) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pr.setString(1, pBarcode);
            pr.setString(2, pName);
            pr.setString(3, pType);
            pr.setString(4, "1");
            pr.setString(5, pCost);
            pr.setString(6,unit);
            pr.setString(7, pQuantity);
            pr.setString(8, pTannarx);
            pr.setString(9, pDan);
            pr.setString(10, pGacha);
            pr.setString(11, "1");
            pr.setString(12, apple);
            pr.setString(13, "1");
            pr.setString(14, apple);
            pr.setString(15, pSuplier);
            pr.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            pr.close();
        }
    }
    public void updateProduct(String pBarcode, String pName ,String pType ,String pTannarx ,String pCost ,String pQuantity ,String pDan ,String pGacha ,String pSuplier,String id) throws SQLException {
        pr =myConn.prepareStatement("UPDATE product SET  barcode=?,name=?,type=?,type_id=?,cost=?,quantity=?,cost_o=?,date_c=?,date_o=?,cr_by=?,date_cr=?,suplier_id=? WHERE id="+id);
        pr.setString(1,pBarcode);
        pr.setString(2,pName);
        pr.setString(3,pType);
        pr.setString(4,"1");//type_id
        pr.setString(5,pCost);
        pr.setString(6,pQuantity);
        pr.setString(7,pTannarx);
        pr.setString(8,pDan);
        pr.setString(9,pGacha);
        pr.setString(10,"1");//cr_by
        pr.setString(11,apple);
        pr.setString(12,"1");//suplier id

    }


}

