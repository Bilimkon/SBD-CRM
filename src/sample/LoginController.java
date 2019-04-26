package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.Core.User;
import sample.DAO.UserDao;
import sample.Utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    public Button btnKirish;
    public TextField textIsm;
    public PasswordField textPassword;
    @FXML private ProgressBar bar;

    static double ii=0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void btnActionKirish() throws IOException {

        // set progress to different level of progressbar
        ii += 0.2;
        bar.setProgress(ii);

        String ismText = textIsm.getText().toString();//getting string  values
        String passText = textPassword.getText().toString();//getting string  values
        Parent root = null;
        Stage stage = new Stage();

        if (isUserExists(ismText, passText)) {

            try {
                root = FXMLLoader.load(getClass().getResource("Design_fxml/MainPage.fxml"));

                stage.setTitle("SBD boshqaruv tizimi");
                stage.setResizable(true);
               // stage.setOnCloseRequest(event -> Main.is_clock_alive = false);

                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();
                stage.setScene(new Scene(root));
                stage.show();
                stage.setMinHeight(720);
                stage.setMinWidth(1080);
                stage.setResizable(true);


            // Hide this current window (if this is what you want)
                this.btnKirish.getScene().getWindow().hide();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(ismText.equals("Admin")&&passText.equals("7559")){

            try {
                root = FXMLLoader.load(getClass().getResource("Design_fxml/AdminPart.fxml"));

                stage.setTitle("SBD admin tizimi");
                stage.setResizable(true);

                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();
                stage.setScene(new Scene(root));
                stage.show();
                stage.setMinWidth(1080);
                stage.setMinHeight(720);
                stage.setResizable(true);
                // Hide this current window (if this is what you want)
                this.btnKirish.getScene().getWindow().hide();

             } catch (IOException e) {
                e.printStackTrace();

                Utils.ErrorAlert("Error","Xatolik"+e, "Xatolik bor shu yerda");
            }
        }
       else {
            Utils.ErrorAlert("Login", "Xatolik bor", "E-mail yoki parol xato");
        }
    }
    public static User currentUser;

    private boolean isUserExists(String name, String password) {
        try {
            UserDao userDao = new UserDao();
            currentUser = userDao.getUser(name, password);

            if (currentUser == null) return false;
            userDao.closeConnection();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
