package sample.DAO;

import sample.Core.User;

import java.sql.*;

public class UserDao {
    Connection myConn;



    public UserDao(Connection myConn) {
        this.myConn = myConn;
    }

    public UserDao() throws ClassNotFoundException, SQLException {

        myConn = Database.getConnection();

        /*Class.forName("org.sqlite.JDBC");
        // connect to database
        myConn = DriverManager.getConnection("jdbc:sqlite:" + DaoUtils.tableName);
        System.out.println("Employee DAO - DB connection successful to jdbc:sqlite:" + DaoUtils.tableName);*/
    }

    public User getUser(String name, String password) throws SQLException {
        Statement statement=null;
        ResultSet  res = null;
        try {
            statement  = myConn.createStatement();
             res = statement.executeQuery("select * from main.seller where main.seller.username = '" + name + "' and main.seller.password = '" + password + "';");
            while (res.next()) {
                User u = new User(
                        res.getInt("id"),
                        res.getString("firstname"),
                        res.getString("lastname"),
                        res.getString("username"), null, res.getFloat("salary"), null, res.getString("admin"));
                u.setAdmin(false);
                if (res.getString("admin").equals("owner")) {
                    u.setAdmin(true);
                }
               // myConn.close();
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            statement.close();
            res.close();
        }

        return null;
    }

    public void closeConnection() throws SQLException {
        myConn.close();
    }
}
