package com.company.controllers;

import com.company.AnimatedZoomOperator;
import com.company.GameOfLife;
import com.company.MatrixUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class ViewController implements Initializable {
    @FXML
    public Canvas canvas;
    @FXML
    public Button randomizeButton;
    @FXML
    public Button startAndStopButton;
    @FXML
    public Button clearButton;
    @FXML
    public Label generationLabel;


    public GameOfLife gameOfLife;
    boolean[][] specialStructure;
    private boolean isRunning = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gameOfLife = new GameOfLife((int) canvas.getWidth(), (int) canvas.getHeight());


        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        new Thread(() -> {
            //Platform.runLater(() -> print(gameOfLife));
            for (; ; ) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isRunning) {
                    gameOfLife.generateNextStep();
                    Platform.runLater(() -> {
                        generationLabel.setText("Generation: " + gameOfLife.getGeneration());
                        print(gameOfLife);
                    });
                    gameOfLife.setGeneration(gameOfLife.getGeneration() + 1);
                }
            }
        }).start();


        AnimatedZoomOperator zoomOperator = new AnimatedZoomOperator();
        canvas.setOnScroll(event -> {
            double zoomFactor = 1.5;
            if (event.getDeltaY() <= 0) {
                // zoom out
                zoomFactor = 1 / zoomFactor;
            }
            zoomOperator.zoom(canvas, zoomFactor, event.getSceneX(), event.getSceneY());
        });
    }

    @FXML
    public void aboutLabelOnMouseClicked(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Made by WIMiIPek (Tomasz Białek)", ButtonType.OK);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("../assets/Hedgehog.jpg")));
        alert.setTitle("Tuptający jeż");
        alert.setHeaderText("Tup, tup, tup");
        alert.show();
    }

    public void canvasOnMouseClicked(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();
        if (specialStructure != null) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setFill(Color.FUCHSIA);
            for (int i = 0; i < specialStructure.length; i++) {
                for (int j = 0; j < specialStructure[0].length; j++) {
                    if (y + i < gameOfLife.getNumberOfColumns() && x + j < gameOfLife.getNumberOfRows()) {
                        System.out.print(specialStructure[i][j] + " ");
                        //gameOfLife.setCellValue(x + i, y + j, specialStructure[i][j]);
                        gameOfLife.setCellValue(x+j, y+i, specialStructure[i][j]);
                        if (specialStructure[i][j])
                            //gc.fillRect(x + i, y + j, 1, 1);
                            gc.fillRect(x+j, y+i, 1, 1);
                    }
                }
                System.out.println();
            }
            System.out.println("----------------------");
        }
        specialStructure = null;
    }

    public void canvasOnMouseDragged(MouseEvent mouseEvent) {
        //System.out.println("X: " + mouseEvent.getX() + "\tY: " + mouseEvent.getY());
        if (specialStructure == null) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            int x = (int) mouseEvent.getX();
            int y = (int) mouseEvent.getY();
            if (x >= 0 && y >= 0 && x < gameOfLife.getNumberOfRows() && y < gameOfLife.getNumberOfColumns()) {
                gameOfLife.setCellValue(x, y, true);
                gc.setFill(Color.FUCHSIA);
                gc.fillRect(x, y, 1, 1);
            }
        }
    }

    public void print(GameOfLife gameOfLife) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < gameOfLife.getNumberOfRows(); i++) {
            for (int j = 0; j < gameOfLife.getNumberOfColumns(); j++) {
                if (gameOfLife.getCellValue(i, j)) {
                    gc.setFill(Color.RED);
                    gc.fillRect(i, j, 1, 1);
                } else {
                    gc.setFill(Color.BLUE);
                    gc.fillRect(i, j, 1, 1);
                }
            }
        }
    }

    public void startAndStopButtonOnAction(ActionEvent actionEvent) {
        if (isRunning) {
            isRunning = false;
            startAndStopButton.setText("Start");
        } else {
            isRunning = true;
            startAndStopButton.setText("Stop");
        }
    }

    public void randomizeButtonOnAction(ActionEvent actionEvent) {
        MatrixUtils.randomizeMatrix(gameOfLife.getData());
        print(gameOfLife);
    }

    public void clearButtonOnAction(ActionEvent actionEvent) {
        gameOfLife.setData(new boolean[gameOfLife.getNumberOfRows()][gameOfLife.getNumberOfColumns()]);
        canvas.getGraphicsContext2D().setFill(Color.MAROON);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gameOfLife.setGeneration(0);
        generationLabel.setText(null);
    }

    public void blockStillLifesOnAction(ActionEvent actionEvent) {
        specialStructure = new boolean[2][2];
        for (int i = 0; i < specialStructure.length; i++) {
            for (int j = 0; j < specialStructure[0].length; j++)
                specialStructure[i][j] = true;
        }
    }

    public void boatStillLifesOnAction(ActionEvent actionEvent) {
        specialStructure = new boolean[3][3];
        specialStructure[0][0] = specialStructure[0][1] = true;
        specialStructure[0][2] = false;

        specialStructure[1][0] = true;
        specialStructure[1][1] = false;
        specialStructure[1][2] = true;

        specialStructure[2][0] = false;
        specialStructure[2][1] = true;
        specialStructure[2][2] = false;
    }

    public void loafStillLifesOnAction(ActionEvent actionEvent) {
        specialStructure = new boolean[4][4];
        specialStructure[0][0] = false;
        specialStructure[0][1] = specialStructure[0][2] = true;
        specialStructure[0][3] = false;

        specialStructure[1][0] = true;
        specialStructure[1][1] = specialStructure[1][2] = false;
        specialStructure[1][3] = true;

        specialStructure[2][0] = true;
        specialStructure[2][1] = false;
        specialStructure[2][2] = true;
        specialStructure[2][3] = false;

        specialStructure[3][0] = false;
        specialStructure[3][1] = true;
        specialStructure[3][2] = specialStructure[3][3] = false;
    }

    public void beehiveStillLifesOnAction(ActionEvent actionEvent) {
        specialStructure = new boolean[3][4];
        specialStructure[0][0] = false;
        specialStructure[0][1] = specialStructure[0][2] = true;
        specialStructure[0][3] = false;

        specialStructure[1][0] = true;
        specialStructure[1][1] = specialStructure[1][2] = false;
        specialStructure[1][3] = true;

        specialStructure[2][0] = false;
        specialStructure[2][1] = specialStructure[2][2] = true;
        specialStructure[2][3] = false;
    }

    public void tubStillLifesOnAction(ActionEvent actionEvent) {
        specialStructure = new boolean[3][3];
        specialStructure[0][0] = false;
        specialStructure[0][1] = true;
        specialStructure[0][2] = false;

        specialStructure[1][0] = true;
        specialStructure[1][1] = false;
        specialStructure[1][2] = true;

        specialStructure[2][0] = false;
        specialStructure[2][1] = true;
        specialStructure[2][2] = false;
    }

    public void pondStillLifesOnAction(ActionEvent actionEvent) {
        specialStructure = new boolean[4][4];
        specialStructure[0][0] = false;
        specialStructure[0][1] = specialStructure[0][2] = true;
        specialStructure[0][3] = false;

        specialStructure[1][0] = true;
        specialStructure[1][1] = specialStructure[1][2] = false;
        specialStructure[1][3] = true;

        specialStructure[2][0] = true;
        specialStructure[2][1] = specialStructure[2][2] = false;
        specialStructure[2][3] = true;

        specialStructure[3][0] = false;
        specialStructure[3][1] = specialStructure[3][2] = true;
        specialStructure[3][3] = false;
    }

    public void blinkerOscillatorsOnAction(ActionEvent actionEvent) {
        specialStructure = new boolean[3][3];
        for (int j = 0; j < specialStructure[0].length; j++)
            specialStructure[0][j] = false;

        for (int j = 0; j < specialStructure[0].length; j++)
            specialStructure[1][j] = true;

        for (int j = 0; j < specialStructure[0].length; j++)
            specialStructure[2][j] = false;
    }

    public void toadOscillatorsOnAction(ActionEvent actionEvent) {
        specialStructure = new boolean[2][4];
        specialStructure[0][0] = false;

        for (int j = 1; j < specialStructure[0].length; j++)
            specialStructure[0][j] = true;

        for (int j = 0; j < specialStructure[0].length - 1; j++)
            specialStructure[1][j] = true;

        specialStructure[1][3] = false;
    }

    public void gliderSpaceshipsOnAction(ActionEvent actionEvent) {
        specialStructure = new boolean[3][3];
        specialStructure[0][0] = false;
        specialStructure[0][1] = true;
        specialStructure[0][2] = false;

        specialStructure[1][0] = specialStructure[1][1] = false;
        specialStructure[1][2] = true;

        specialStructure[2][0] = specialStructure[2][1] = specialStructure[2][2] = true;
    }

    public void gospelGliderGunGunsOnAction(ActionEvent actionEvent) {
        specialStructure = new boolean[11][38];

        specialStructure[1][25] = true;

        specialStructure[2][23] = specialStructure[2][25] = true;

        specialStructure[3][13] = specialStructure[3][14] =
                specialStructure[3][21] = specialStructure[3][22] =
                        specialStructure[3][35] = specialStructure[3][36] = true;

        specialStructure[4][12] = specialStructure[4][16] =
                specialStructure[4][21] = specialStructure[4][22] =
                        specialStructure[4][35] = specialStructure[4][36] = true;

        specialStructure[5][1] = specialStructure[5][2] =
                specialStructure[5][11] = specialStructure[5][17] =
                        specialStructure[5][21] = specialStructure[5][22] = true;

        specialStructure[6][1] = specialStructure[6][2] =
                specialStructure[6][11] = specialStructure[6][15] = specialStructure[6][17] = specialStructure[6][18] =
                        specialStructure[6][23] = specialStructure[6][25] = true;

        specialStructure[7][11] = specialStructure[7][17] =
                specialStructure[7][25] = true;

        specialStructure[8][12] = specialStructure[8][16] = true;

        specialStructure[9][13] = specialStructure[9][14] = true;
    }
}
