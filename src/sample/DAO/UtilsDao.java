package sample.DAO;

import sample.Core.Models.ReceiptCheck;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class UtilsDao {
    private Connection myConn;

    public UtilsDao(){
        try {
            myConn= Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clearAll() throws SQLException {
        PreparedStatement pt =null;
        try {
            pt = myConn.prepareStatement("DELETE FROM sellaction  ");
            pt.executeUpdate();
            pt = myConn.prepareStatement("DELETE FROM history");
            pt.executeUpdate();
            pt = myConn.prepareStatement("DELETE FROM product");
            pt.executeUpdate();
            pt = myConn.prepareStatement("DELETE FROM seller");
            pt.executeUpdate();
            pt = myConn.prepareStatement("DELETE FROM customer");
            pt.executeUpdate();
            pt = myConn.prepareStatement("DELETE FROM suplier");
            pt.executeUpdate();
            pt = myConn.prepareStatement("DELETE FROM type");
            pt.executeUpdate();
            JOptionPane.showMessageDialog(null, " Barcha malumotlar tozalandi", "Barcha malumotlar o'chirib tashlandi", JOptionPane.INFORMATION_MESSAGE);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (pt != null) {
                pt.close();
            }
        }
    }

    public void excellFolder(String path) throws SQLException {
        PreparedStatement pt =null;
        try {
            pt = myConn.prepareStatement("UPDATE utils SET filePath=?");
            pt.setString(1, path);
            pt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (pt != null) {
                pt.close();
            }
        }
    }
    public void setPrinterName(String name) throws SQLException {
        PreparedStatement pt =null;
        try {
            pt = myConn.prepareStatement("UPDATE utils SET printerName=?");
            pt.setString(1, name);
            pt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (pt != null) {
                pt.close();
            }
        }
    }
    public int TotalSum() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = myConn.createStatement();
            resultSet = statement.executeQuery("SELECT total_cost FROM sellaction WHERE id = (SELECT max(id) AS 'last_item_id' FROM main.sellaction)");
            if (resultSet.next()) {
                return resultSet.getInt("total_cost");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public ArrayList<ReceiptCheck> PerProduct() throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<ReceiptCheck> receiptChecksList = new ArrayList<>();
        try {
            statement = myConn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM history WHERE sell_action_id = (SELECT max(id) AS 'last_item_id' FROM main.sellaction)");
            while (resultSet.next()) {
                ReceiptCheck receiptCheck = new ReceiptCheck();
                receiptCheck.setId(resultSet.getInt("id"));
                receiptCheck.setName(resultSet.getString("product_name"));
                receiptCheck.setQuantity(resultSet.getInt("product_quantity"));
                receiptCheck.setPrice(resultSet.getDouble("cost"));
                receiptChecksList.add(receiptCheck);
            }
            return receiptChecksList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return receiptChecksList;
    }
    public void InsertRowInDB(String barcode, String name, String type, String type_id, String cost, String quantity, String cost_o, String date_c, String date_o, String cr_by, String date_cr, String suplier_id, String unit) throws SQLException {
        PreparedStatement pr =null;
        try {
            String sql = "INSERT INTO product (barcode, name, type, type_id, cost, quantity, cost_o, date_c, date_o, cr_by, date_cr, suplier_id, unit) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pr = myConn.prepareStatement(sql);
            pr.setString(1, barcode);
            pr.setString(2, name);
            pr.setString(3, type);
            pr.setString(4, type_id);
            pr.setString(5, cost);
            pr.setString(6, quantity);
            pr.setString(7, cost_o);
            pr.setString(8, date_c);
            pr.setString(9, date_o);
            pr.setString(10, cr_by);
            pr.setString(11, date_cr);
            pr.setString(12, suplier_id);
            pr.setString(13, unit);
            pr.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pr != null) {
                pr.close();
            }
        }
    }



}
