//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sample;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    public static boolean isStageAlive = false;
    public static boolean is_clock_alive = true;

    public Main() {
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("views/MainPage.fxml"));
        primaryStage.setTitle("SBD boshqaruv tizimi.");
        primaryStage.setScene(new Scene(root, 1080.0D, 720.0D));
        primaryStage.setMinHeight(720.0D);
        primaryStage.setMinWidth(1090.0D);
        primaryStage.show();
        isStageAlive = true;
        primaryStage.setOnCloseRequest((event) -> {
            isStageAlive = false;
            Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                public void uncaughtException(Thread t, Throwable e) {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String filename = "crashlogs/" + sdf.format(cal.getTime()) + ".txt";

                    try {
                        PrintStream writer = new PrintStream(filename, "UTF-8");
                        writer.println(e.getClass() + ": " + e.getMessage());

                        for(int i = 0; i < e.getStackTrace().length; ++i) {
                            writer.println(e.getStackTrace()[i].toString());
                        }
                    } catch (UnsupportedEncodingException | FileNotFoundException var8) {
                        var8.printStackTrace();
                    }

                }
            });
        });
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                Main.is_clock_alive = false;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
