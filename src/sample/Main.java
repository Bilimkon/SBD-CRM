package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Main extends Application {
    public static boolean isStageAlive = false;
    public static boolean is_clock_alive = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Design_fxml/Login.fxml"));
        primaryStage.setTitle("SBD boshqaruv tizimi.");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1090);
        primaryStage.setMaximized(true);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("style/Images/SBD-logo.png")));
//      primaryStage.getIcons().add(new Image(String.valueOf(getClass().getClassLoader().getResource("style/Images/22.png"))));
//      primaryStage.setScene(new Scene(root));
        primaryStage.show();
        isStageAlive = true;
        primaryStage.setOnCloseRequest(event -> {
            isStageAlive = false;

            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

                    String filename = "crashlogs/"+sdf.format(cal.getTime())+".txt";

                    PrintStream writer;
                    try {
                        writer = new PrintStream(filename, "UTF-8");
                        writer.println(e.getClass() + ": " + e.getMessage());
                        for (int i = 0; i < e.getStackTrace().length; i++) {
                            writer.println(e.getStackTrace()[i].toString());
                        }

                    } catch (FileNotFoundException | UnsupportedEncodingException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }
            });



        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                is_clock_alive = false;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
