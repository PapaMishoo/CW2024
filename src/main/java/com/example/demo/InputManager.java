package com.example.demo;

import com.example.demo.activeactor.PlayerJet;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class InputManager {

    private static InputManager instance;
    private final Set<KeyCode> pressedKeys;

    /**
     * Private constructor for the {@link InputManager} singleton. Initializes the set of pressed keys.
     */
    private InputManager() {
        pressedKeys = new HashSet<>();
    }

    /**
     * Returns the singleton instance of {@link InputManager}.
     *
     * @return the instance of InputManager.
     */
    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }

    /**
     * Sets up key event handlers for the specified node and binds them to player actions.
     *
     * @param node the UI node that listens for key events.
     * @param user the player character to control.
     * @param fireProjectile the action to trigger when the fire button (SPACE) is pressed.
     */
    public void initializeInputs(Node node, PlayerJet user, Runnable fireProjectile) {
        node.setOnKeyPressed(this::handleKeyPressed);
        node.setOnKeyReleased(this::handleKeyReleased);

        node.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            KeyCode kc = e.getCode();
            if (kc == KeyCode.UP) user.moveUp();
            if (kc == KeyCode.DOWN) user.moveDown();
            if (kc == KeyCode.LEFT) user.moveLeft();
            if (kc == KeyCode.RIGHT) user.moveRight();
            if (kc == KeyCode.SPACE) fireProjectile.run();
        });

        node.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            KeyCode kc = e.getCode();
            if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stopVertical();
            if (kc == KeyCode.LEFT || kc == KeyCode.RIGHT) user.stopHorizontal();
        });
    }

    /**
     * Adds the pressed key to the set of currently pressed keys.
     *
     * @param event the key event triggered when a key is pressed.
     */
    private void handleKeyPressed(KeyEvent event) {
        pressedKeys.add(event.getCode());
    }

    /**
     * Removes the released key from the set of currently pressed keys.
     *
     * @param event the key event triggered when a key is released.
     */
    private void handleKeyReleased(KeyEvent event) {
        pressedKeys.remove(event.getCode());
    }

    /**
     * Checks if a specific key is currently pressed.
     *
     * @param key the KeyCode of the key to check.
     * @return true if the key is pressed, false otherwise.
     */
    public boolean isKeyPressed(KeyCode key) {
        return pressedKeys.contains(key);
    }

    /**
     * Clears all keys from the set of pressed keys.
     */
    public void clearAllKeys() {
        pressedKeys.clear();
    }
}
