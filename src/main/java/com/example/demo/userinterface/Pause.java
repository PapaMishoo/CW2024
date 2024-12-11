package com.example.demo.userinterface;


import com.example.demo.level.LevelParent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Pause {

    private final Stage stage;

    private Scene currentScene;
    private LevelParent currentLevel;

    public Pause(Stage stage) {
        this.stage = stage;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;

        initializePauseButton(currentScene);
    }

    public void setCurrentLevel(LevelParent currentLevel) {
        this.currentLevel = currentLevel;
    }

    private void initializePauseButton(Scene currentScene) {
        Group root = (Group) currentScene.getRoot();
        root.getChildren().add(createPauseButton());
    }

    // Add a pause button to the game
    public Button createPauseButton() {
        Button pauseButton = new Button("Pause");
        pauseButton.setViewOrder(-1);
        pauseButton.setStyle("-fx-font-size: 14; -fx-background-color: #FF4500; -fx-text-fill: #FFF; -fx-padding: 5;");
        pauseButton.setOnAction(e -> pauseGame());
        pauseButton.setLayoutX(1200); // Adjust X position (top-right of screen)
        pauseButton.setLayoutY(10);   // Adjust Y position (top-right of screen)
        return pauseButton;
    }

    private void pauseGame() {
        currentLevel.getBackground().requestFocus();
        currentLevel.pauseGame();
        showPauseMenu();
    }

    private void resumeGame() {
        currentLevel.resumeGame();
        stage.setScene(currentScene);
    }

    // Show the pause menu
    private void showPauseMenu() {
        // Pause menu layout
        VBox pauseMenuLayout = new VBox(20);
        pauseMenuLayout.setStyle("-fx-alignment: center; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 50;");

        Button resumeButton = new Button("Resume");
        resumeButton.setStyle("-fx-font-size: 20; -fx-background-color: #00A; -fx-text-fill: #FFF;");
        resumeButton.setOnAction(e -> resumeGame()); // Return to the game

        pauseMenuLayout.getChildren().addAll(resumeButton);


        Scene pauseMenuScene = new Scene(new StackPane(pauseMenuLayout), 1300, 750);
        stage.setScene(pauseMenuScene);
    }
}
