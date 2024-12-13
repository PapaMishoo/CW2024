package com.example.demo.userinterface;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ShieldImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
	private static final int SHIELD_SIZE = 200;

	/**
	 * Constructs a {@link ShieldImage} instance and initializes its position, size, and visibility.
	 *
	 * @param xPosition the x-coordinate position of the shield image
	 * @param yPosition the y-coordinate position of the shield image
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Displays the {@link ShieldImage} and brings it to the front of the scene.
	 */
	public void showShield() {
		this.setVisible(true);
		toFront();
	}

	/**
	 * Hides the {@link ShieldImage} making it invisible.
	 */
	public void hideShield() {
		this.setVisible(false);
	}
}
