package com.example.demo.userinterface;

import com.example.demo.controller.Controller;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.Objects;


public class MainMenu {
    private static final String backgroundImageName = "/com/example/demo/images/Skies of Mishal.png";
    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;

    /**
     * Displays the {@link MainMenu} on the given stage with options to start the game or exit the application.
     *
     * @param stage the stage on which the main menu will be displayed
     */
    public void show(Stage stage) {

        Group menuLayout = new Group();
        menuLayout.setStyle("-fx-alignment: center; -fx-padding: 50; -fx-background-color: #000;");

        VBox buttonLayout = new VBox(20);
        buttonLayout.setTranslateX(SCREEN_WIDTH * 0.75);
        buttonLayout.setTranslateY(SCREEN_HEIGHT / 2);


        Button startGameButton = new Button("Start Game");
        Button exitButton = new Button("Exit");


        startGameButton.setStyle("-fx-font-size: 20; -fx-background-color: #00A; -fx-text-fill: #FFF;");
        exitButton.setStyle("-fx-font-size: 20; -fx-background-color: #A00; -fx-text-fill: #FFF;");

        buttonLayout.getChildren().addAll(startGameButton, exitButton);
        menuLayout.getChildren().addAll(buttonLayout);


        Scene mainMenuScene = new Scene(menuLayout, SCREEN_WIDTH, SCREEN_HEIGHT);

        ImageView background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
        background.setViewOrder(1);
        background.setPreserveRatio(true);
        background.setFitWidth(SCREEN_WIDTH);
        menuLayout.getChildren().add(background);


        startGameButton.setOnAction(e -> {
            try {
                Controller controller = new Controller(stage);
                controller.launchGame();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        exitButton.setOnAction(e -> {
            stage.close();
        });


        stage.setScene(mainMenuScene);
        stage.show();
    }
}