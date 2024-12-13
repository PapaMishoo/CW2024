package com.example.demo.userinterface;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class WinImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;

	/**
	 * Creates a {@link WinImage} instance with initialized position, dimensions, visibility, and image.
	 *
	 * @param xPosition the x-coordinate position
	 * @param yPosition the y-coordinate position
	 */
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

	/**
	 * Makes the {@link WinImage} visible on the screen by setting its visibility to true.
	 */
	public void showWinImage() {
		this.setVisible(true);
	}

}
