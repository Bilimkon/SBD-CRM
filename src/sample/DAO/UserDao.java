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

    public User getUser(String name, String password) {
        try {
            Statement preparedStatement = myConn.createStatement();
            ResultSet res = preparedStatement.executeQuery("select * from sbd_market.sotuvchi where sbd_market.sotuvchi.email = '" + name + "' and sbd_market.sotuvchi.password = '" + password + "';");
            while (res.next()) {
                User u = new User(
                        res.getInt("sotuvchi_id"),
                        res.getString("first_name"),
                        res.getString("last_name"),
                        res.getString("tel_number"), null, res.getFloat("salary"), null, res.getString("lavozim"));
                u.setAdmin(false);
                if (res.getString("lavozim").equals("owner")) {
                    u.setAdmin(true);
                }
               // myConn.close();
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void closeConnection() throws SQLException {
        myConn.close();
    }
}
