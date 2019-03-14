package sample.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
Humoyun Qo'rg'onov  SBD(Software Business Development)
 */


public class DaoUtils {
    public static String tableName = "sbd_market?autoReconnect=true&useSSL=false";
    public static void close(Connection myConn, Statement myStmt, ResultSet myRs)
            throws SQLException {

        if (myRs != null) {
            myRs.close();
        }

        if (myStmt != null) {

        }

        if (myConn != null) {
            myConn.close();
        }
    }

    public static void close(Statement myStmt, ResultSet myRs) throws SQLException {
        close(null, myStmt, myRs);
    }

    public static void close(Statement myStmt) throws SQLException {
        close(null, myStmt, null);
    }




}
