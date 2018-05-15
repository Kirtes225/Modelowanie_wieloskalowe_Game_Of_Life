package com.company;

import com.company.controllers.ViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("fxml/view.fxml"));
        Pane root = fxmlloader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("( ͡° ͜ʖ ͡°)");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("assets/Game_of_life.png")));
        primaryStage.show();

        //primaryStage.setMaximized(true);

        ViewController viewController = fxmlloader.getController();

//        root.heightProperty().addListener((observable, oldValue, newValue) -> {
//            viewController.canvas.setHeight(newValue.intValue());
//            viewController.gameOfLife.setData(MatrixUtils.resize(viewController.gameOfLife.getData(), viewController.gameOfLife.getNumberOfRows(), newValue.intValue()-10));
//            System.out.println("Canvas height: " + viewController.canvas.getHeight() + "; Array: " + viewController.gameOfLife.getNumberOfRows() + ", " + viewController.gameOfLife.getNumberOfColumns());
//
//        });

        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            viewController.canvas.setWidth(newValue.intValue());
            viewController.gameOfLife.setData(MatrixUtils.resize(viewController.gameOfLife.getData(), newValue.intValue() - 10, viewController.gameOfLife.getNumberOfColumns()));
            viewController.print(viewController.gameOfLife);
            System.out.println("Canvas width: " + viewController.canvas.getWidth() + "; Array: " + viewController.gameOfLife.getNumberOfRows() + ", " + viewController.gameOfLife.getNumberOfColumns());

        });

//        AnimatedZoomOperator zoomOperator = new AnimatedZoomOperator();
//        root.setOnScroll(event -> {
//            double zoomFactor = 1.5;
//            if (event.getDeltaY() <= 0) {
//                // zoom out
//                zoomFactor = 1 / zoomFactor;
//            }
//            zoomOperator.zoom(root, zoomFactor, event.getSceneX(), event.getSceneY());
//        });

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(2);
        });
    }
}