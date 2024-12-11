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

    private InputManager() {
        pressedKeys = new HashSet<>();
    }

    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }

    public void initializeInputs(Node node, PlayerJet user, Runnable fireProjectile) {
        node.setOnKeyPressed(this::handleKeyPressed);
        node.setOnKeyReleased(this::handleKeyReleased);

        // Specific game logic for user movements and actions
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

    private void handleKeyPressed(KeyEvent event) {
        pressedKeys.add(event.getCode());
    }

    private void handleKeyReleased(KeyEvent event) {
        pressedKeys.remove(event.getCode());
    }

    public boolean isKeyPressed(KeyCode key) {
        return pressedKeys.contains(key);
    }

    public void clearAllKeys() {
        pressedKeys.clear();
    }
}
