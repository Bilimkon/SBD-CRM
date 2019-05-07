package sample.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static sample.DAO.Database.myConn;

public class printer {



    public String printerName() throws SQLException {

        Statement statement=null;
        ResultSet resultSet = null;

        try {
            statement=myConn.createStatement();

            resultSet =statement.executeQuery("SELECT * FROM utils");

            if(resultSet.next()){
                return String.valueOf(resultSet.getString("printerName"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            statement.close();
            resultSet.close();
        }
        return null;
    }
    public String ExcelFilePath() throws SQLException {

        Statement statement1=null;
        ResultSet resultSet1 = null;

        try {
            statement1=myConn.createStatement();

            resultSet1 =statement1.executeQuery("SELECT * FROM utils");

            if(resultSet1.next()){
                return String.valueOf(resultSet1.getString("filePath"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            statement1.close();
            resultSet1.close();
        }
        return null;

    }
}
