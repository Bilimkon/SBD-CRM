package sample.DAO;

import com.mysql.jdbc.Util;
import sample.Utils.Utils;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    static Connection myConn;


    public static Connection getConnection() throws ClassNotFoundException, SQLException {
       // Class.forName("com.mysql.jdbc.Driver");
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DaoUtils.tableName,"java","Bilimkon");
        try {
            ProductDao productDao = new ProductDao(myConn);
        } catch (Exception exc) {
            Utils.ErrorAlert("Xatolik","Xatolik","Xatolik"+exc);
        }
        return myConn;
    }
}
