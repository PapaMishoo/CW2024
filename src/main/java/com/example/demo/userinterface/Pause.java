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

    /**
     * Constructs a {@link Pause} instance with the specified stage.
     *
     * @param stage the primary {@link Stage} used to display the game and pause scenes
     */
    public Pause(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the current scene for the pause functionality and initializes the {@link Pause} button.
     *
     * @param currentScene the current {@link Scene} of the game
     */
    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
        initializePauseButton(currentScene);
    }

    /**
     * Sets the current level for the pause functionality.
     *
     * @param currentLevel the {@link LevelParent} instance representing the current game level
     */
    public void setCurrentLevel(LevelParent currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * Adds a {@link Pause} button to the root of the provided scene.
     *
     * @param currentScene the {@link Scene} to which the pause button will be added
     */
    private void initializePauseButton(Scene currentScene) {
        Group root = (Group) currentScene.getRoot();
        root.getChildren().add(createPauseButton());
    }

    /**
     * Creates and returns a {@link Pause} button configured to pause the game when clicked.
     *
     * @return a {@link Button} configured for pausing the game
     */
    public Button createPauseButton() {
        Button pauseButton = new Button("Pause");
        pauseButton.setViewOrder(-1);
        pauseButton.setStyle("-fx-font-size: 14; -fx-background-color: #FF4500; -fx-text-fill: #FFF; -fx-padding: 5;");
        pauseButton.setOnAction(e -> pauseGame());
        pauseButton.setLayoutX(1200); // Adjust X position (top-right of screen)
        pauseButton.setLayoutY(10);   // Adjust Y position (top-right of screen)
        return pauseButton;
    }

    /**
     * Pauses the current game session and displays the pause menu.
     * Temporarily halts gameplay and shows the pause interface.
     */
    private void pauseGame() {
        currentLevel.getBackground().requestFocus();
        currentLevel.pauseGame();
        showPauseMenu();
    }

    /**
     * Resumes the paused game and switches back to the active gameplay scene.
     */
    private void resumeGame() {
        currentLevel.resumeGame();
        stage.setScene(currentScene);
    }

    /**
     * Displays the {@link Pause} menu interface with a "Resume" button to continue the game.
     */
    private void showPauseMenu() {
        VBox pauseMenuLayout = new VBox(20);
        pauseMenuLayout.setStyle("-fx-alignment: center; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 50;");

        Button resumeButton = new Button("Resume");
        resumeButton.setStyle("-fx-font-size: 20; -fx-background-color: #00A; -fx-text-fill: #FFF;");
        resumeButton.setOnAction(e -> resumeGame());

        pauseMenuLayout.getChildren().addAll(resumeButton);

        Scene pauseMenuScene = new Scene(new StackPane(pauseMenuLayout), 1300, 750);
        stage.setScene(pauseMenuScene);
    }
}
