package sample.DAO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    static Connection myConn;


    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DaoUtils.tableName,"java","Bilimkon");
        try {
            ProductDao productDao = new ProductDao(myConn);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Xatolik" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);

        }
        return myConn;
    }
}
