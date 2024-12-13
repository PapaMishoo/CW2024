package com.example.demo.userinterface;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	/**
	 * Creates a new instance of {@link GameOverImage} and initializes its position and image.
	 *
	 * @param xPosition the x-coordinate position
	 * @param yPosition the y-coordinate position
	 */
	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));

		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

}
