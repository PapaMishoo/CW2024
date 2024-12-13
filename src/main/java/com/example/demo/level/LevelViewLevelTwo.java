package com.example.demo.level;

import com.example.demo.userinterface.ShieldImage;
import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final ShieldImage shieldImage;

	/**
	 * Constructs a {@link LevelViewLevelTwo}, initializing the shield image and heart display for Level Two.
	 *
	 * @param root the root group where graphical elements of the level view will be added
	 * @param heartsToDisplay the initial number of hearts to display for the player's health
	 */
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		addImagesToRoot();
	}

	/**
	 * Adds the {@link ShieldImage} to the root group in the scene graph.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}

	/**
	 * Displays the {@link ShieldImage} in the game scene, making it visible and bringing it to the front.
	 */
	public void showShield() {
		shieldImage.showShield();
	}

	/**
	 * Hides the {@link ShieldImage} from the game scene, making it invisible.
	 */
	public void hideShield() {
		shieldImage.hideShield();
	}
}
