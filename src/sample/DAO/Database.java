package sample.DAO;

import sample.Utils.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    static Connection myConn;


    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        try {
            //Class.forName("org.sqlite.JDBC");
            myConn = DriverManager.getConnection("jdbc:sqlite:"+DaoUtils.tableName);
            ProductDao productDao = new ProductDao(myConn);
        } catch (Exception exc) {
            Utils.ErrorAlert("Xatolik", "Xatolik", "Xatolik" + exc);
        }finally {

        }
        return myConn;
    }
}
