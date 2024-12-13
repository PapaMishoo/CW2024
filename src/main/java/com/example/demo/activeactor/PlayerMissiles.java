package com.example.demo.activeactor;

public class PlayerMissiles extends Missiles {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 125;
	private static final int HORIZONTAL_VELOCITY = 15;

	/**
	 * Constructs a {@code PlayerMissiles} object with an initial position and image.
	 */
	public PlayerMissiles(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the horizontal position of the {@link PlayerMissiles} based on its velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the {@code PlayerMissiles} by delegating to {@link #updatePosition()} for movement.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
