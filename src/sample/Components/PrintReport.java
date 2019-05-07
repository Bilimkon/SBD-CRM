package sample.Components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import sample.DAO.Database;
import sample.DAO.printer;
import sample.Utils.PrinterService;
import sample.Utils.Utils;

import javax.swing.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class PrintReport implements Initializable {


    @FXML
    public Label Nsumma;
    @FXML
    public Label Psumma;
    @FXML
    public Label Usumma;
    @FXML
    public DatePicker RDate1;
    @FXML
    public DatePicker RDate2;

    Statement myStmt = null;
    ResultSet myRs = null;
    PreparedStatement preparedStatement = null;
    Connection connection;
    private final int noOfDaysToAdd = 1;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        print();
    }

    public static final LocalDate NOW_LOCAL_DATE() {
        String date = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }


    public void print() {
        try {
            RDate1.setValue(NOW_LOCAL_DATE());


            LocalDate date1 = RDate1.getValue();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String sdate1 = df.format(Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            RDate2.setValue(RDate1.getValue().plusDays(noOfDaysToAdd));
            LocalDate date2 = RDate2.getValue();
            String sdate2 = df.format(Date.from(date2.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            connection = Database.getConnection();
            preparedStatement = connection.prepareStatement("SELECT sum(c.total_cost) umumiy_summa, sum(paid_in_cash) umumiyCashAmount, " +
                    "sum(c.cardAmount) umumiyCard_amount, sum(c.credit) umumiyCredit_amount FROM collapsedCreditHistoryAll c " +
                    "where substr(paid_date,7,10) between ? and ? ");
            preparedStatement.setString(1, sdate1);
            preparedStatement.setString(2, sdate2);

            myRs = preparedStatement.executeQuery();
            if (myRs.next()) {

                Nsumma.setText(new BigDecimal(myRs.getDouble("umumiyCashAmount")).toPlainString() + ".00 sum");
                Psumma.setText(new BigDecimal(myRs.getDouble("umumiyCard_amount")).toPlainString() + ".00 sum");
                Usumma.setText(new BigDecimal(myRs.getDouble("umumiy_summa")).toPlainString() + ".00 sum");

            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc, "Xatolik", JOptionPane.ERROR_MESSAGE);

        }

    }
    public void printReceipt() throws SQLException {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kunlik hisoblarni yaratish ");
        alert.setHeaderText(null);
        alert.setContentText(" Kunlik hisoblarni chop etasizmi?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent())
            if (result.get() == ButtonType.OK) {


                String apple = Utils.convertDateToStandardFormat(Utils.getCurrentDate());

                PrinterService printerService = new PrinterService();

                System.out.println(printerService.getPrinters());


                String NaqtSumma = Nsumma.getText();
                String PlastikSumma = Psumma.getText();
                String TotalSumma = Usumma.getText();


                printer printer = new printer();


                //print some stuff
                printerService.printString(printer.printerName(), "\n" +
                        "*********Software business development**********\n\n\n" +
                        "*********    Kunlik savdo hisoboti    **********\n\n" +
                        "Naqd pul summa            " + NaqtSumma + " sum\n\n\n" +
                        "Plastik summa             " + PlastikSumma + " sum\n\n\n" +
                        "Umumiy summa              " + TotalSumma + " sum\n\n\n" +
                        "***************" + apple + "***************\n" +
                        "***********Sizning kunlik hisobingiz!***********\n\n\n\n\n\n\n\n");

                // cut that paper!
                byte[] cutP = new byte[]{0x1d, 'V', 1};

                printerService.printBytes(printer.printerName(), cutP);
            }

    }

}
