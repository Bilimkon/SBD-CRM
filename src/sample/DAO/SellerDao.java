package sample.DAO;

import sample.Utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SellerDao {
    private Connection myConn;

    public SellerDao(){
        try {
            myConn =Database.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void deleteSeller(String id) throws SQLException {
        PreparedStatement pr = null;

        try {
            pr=myConn.prepareStatement("DELETE  FROM seller WHERE id=?");
            pr.setString(1,id);
            pr.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (pr != null) {
                pr.close();
            }
        }
    }

    public  void updateSeller(String firstName, String lastName, String admin, String salary, String password, String birthDate, String username, String id) throws SQLException {
        PreparedStatement pr = null;
        try {
            pr = myConn.prepareStatement("UPDATE  seller SET  firstname=?, lastname=?, admin=?,salary=?, password=?, birthdate=?, username=? WHERE id=?");
            pr.setString(1, firstName);
            pr.setString(2, lastName);
            pr.setString(3, admin);
            pr.setString(4, salary);
            pr.setString(5, password);
            pr.setString(6, birthDate);
            pr.setString(7, username);
            pr.setString(8, id);
            pr.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (pr != null) {
                pr.close();
            }
        }
    }

    public  void addSeller(String firstName, String lastName, String admin, String salary, String password, String birthDate, String username) throws SQLException {
        String date = Utils.convertDateToStandardFormat(Utils.getCurrentDate());
        PreparedStatement pr = null;
        try {
            pr = myConn.prepareStatement("INSERT INTO seller (username, firstname, lastname, admin, salary, password, birthdate, date_cr) "
                    + "VALUES (?,?,?,?,?,?,?,?)");
            pr.setString(1, username);
            pr.setString(2, firstName);
            pr.setString(3, lastName);
            pr.setString(4, admin);
            pr.setString(5, salary);
            pr.setString(6, password);
            pr.setString(7, birthDate);
            pr.setString(8, date );
            pr.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (pr != null) {
                pr.close();
            }
        }
    }
}
