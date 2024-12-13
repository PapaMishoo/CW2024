package com.example.demo;

import com.example.demo.activeactor.PlayerJet;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class InputManagerTest {

    private InputManager inputManager;
    private PlayerJet mockPlayerJet;
    private Runnable mockFireProjectile;

    @BeforeEach
    public void setUp() {
        inputManager = InputManager.getInstance(); // Get the singleton instance
        mockPlayerJet = mock(PlayerJet.class);  // Mock the PlayerJet
        mockFireProjectile = mock(Runnable.class);  // Mock the fire action
    }

    @Test
    public void testSingletonInstance() {
        InputManager instance1 = InputManager.getInstance();
        InputManager instance2 = InputManager.getInstance();
        assertSame(instance1, instance2, "The InputManager should be a singleton.");
    }

    @Test
    public void testInitializeInputs() {
        // Setup: Create a mock node
        Node mockNode = mock(Node.class);

        // Call the method to initialize inputs
        inputManager.initializeInputs(mockNode, mockPlayerJet, mockFireProjectile);

        // Verify that the appropriate key events are registered
        verify(mockNode).setOnKeyPressed(any());
        verify(mockNode).setOnKeyReleased(any());
    }

    @Test
    public void testKeyPressedAndReleased() {
        // Setup
        KeyEvent upKeyPressed = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false);
        KeyEvent upKeyReleased = new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.UP, false, false, false, false);

        // Simulate pressing UP key
        inputManager.handleKeyPressed(upKeyPressed);
        assertTrue(inputManager.isKeyPressed(KeyCode.UP), "UP key should be registered as pressed.");

        // Simulate releasing UP key
        inputManager.handleKeyReleased(upKeyReleased);
        assertFalse(inputManager.isKeyPressed(KeyCode.UP), "UP key should be removed from pressed keys.");
    }

    @Test
    public void testPlayerJetMovementOnKeyPress() {
        // Setup
        KeyEvent upKeyPressed = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false);
        KeyEvent downKeyPressed = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, false, false, false);

        // Call the handler for key presses
        inputManager.initializeInputs(mock(PlayerJet.class), mockPlayerJet, mockFireProjectile);
        inputManager.handleKeyPressed(upKeyPressed);
        inputManager.handleKeyPressed(downKeyPressed);

        // Verify that the player's move methods were triggered for up and down keys
        verify(mockPlayerJet).moveUp();
        verify(mockPlayerJet).moveDown();
    }

    @Test
    public void testPlayerJetStopOnKeyRelease() {
        // Setup
        KeyEvent upKeyReleased = new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.UP, false, false, false, false);
        KeyEvent downKeyReleased = new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.DOWN, false, false, false, false);

        // Simulate releasing UP and DOWN keys
        inputManager.handleKeyReleased(upKeyReleased);
        inputManager.handleKeyReleased(downKeyReleased);

        // Verify that the stop methods were called on the player
        verify(mockPlayerJet).stopVertical();
    }

    @Test
    public void testFireProjectileOnSpacePress() {
        // Setup
        KeyEvent spaceKeyPressed = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SPACE, false, false, false, false);

        // Simulate pressing the SPACE key
        inputManager.initializeInputs(mock(Node.class), mockPlayerJet, mockFireProjectile);
        inputManager.handleKeyPressed(spaceKeyPressed);

        // Verify that the fireProjectile action was triggered
        verify(mockFireProjectile).run();
    }

    @Test
    public void testClearAllKeys() {
        // Setup
        KeyEvent leftKeyPressed = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false);
        inputManager.handleKeyPressed(leftKeyPressed);

        // Assert that LEFT key is pressed
        assertTrue(inputManager.isKeyPressed(KeyCode.LEFT), "LEFT key should be registered as pressed.");

        // Call clearAllKeys
        inputManager.clearAllKeys();

        // Assert that all keys are cleared
        assertFalse(inputManager.isKeyPressed(KeyCode.LEFT), "LEFT key should not be pressed after clear.");
    }
}
