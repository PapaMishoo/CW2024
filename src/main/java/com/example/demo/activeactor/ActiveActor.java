package com.example.demo.activeactor;

import javafx.scene.image.*;

import java.util.Objects;


public abstract class ActiveActor extends ImageView {
	
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructs an instance of the {@link ActiveActor} with the specified image file,
	 * image height, and initial position coordinates.
	 *
	 * @param imageName the name of the image file
	 * @param imageHeight the height of the image
	 * @param initialXPos the initial horizontal position
	 * @param initialYPos the initial vertical position
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {

		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Updates the actor's position based on its specific movement logic.
	 * Called during the game loop to simulate motion within the game environment.
	 */

	public abstract void updatePosition();

	/**
	 * Moves the object horizontally by adjusting its horizontal translation
	 * (translateX property) based on the provided movement value.
	 *
	 * @param horizontalMove the amount by which to move the object horizontally.
	 *                        Positive values move the object to the right, while
	 *                        negative values move it to the left.
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Adjusts the vertical position of the object by modifying its translateY property
	 * based on the provided vertical movement value.
	 *
	 * @param verticalMove the amount to modify the object's vertical position.
	 *                     Positive values move the object downward, while negative values
	 *                     move it upward.
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
