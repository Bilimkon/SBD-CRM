package sample.DAO;

import java.sql.ResultSet;
import java.sql.Statement;

import static sample.DAO.Database.myConn;

public class printer {



    public String printerName(){

        Statement statement=null;
        ResultSet resultSet = null;

        try {
            statement=myConn.createStatement();

            resultSet =statement.executeQuery("SELECT * FROM printer");

            if(resultSet.next()){
                return String.valueOf(resultSet.getString("name"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String ExcelFilePath(){

        Statement statement1=null;
        ResultSet resultSet1 = null;

        try {
            statement1=myConn.createStatement();

            resultSet1 =statement1.executeQuery("SELECT * FROM printer");

            if(resultSet1.next()){
                return String.valueOf(resultSet1.getString("Path"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
}
