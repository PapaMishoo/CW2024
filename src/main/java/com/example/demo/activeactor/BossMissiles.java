package com.example.demo.activeactor;

public class BossMissiles extends Missiles {
	
	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 75;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructs a {@link BossMissiles} object with the specified initial vertical position.
	 * The missile moves horizontally from a fixed position.
	 *
	 * @param initialYPos The missile's initial vertical position.
	 */

	public BossMissiles(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}
	/**
	 * Updates the {@link BossMissiles} position by moving it horizontally using its predefined velocity.
	 * This overrides the superclass method to implement specific movement behavior, moving left with a negative velocity.
	 */

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the {@link BossMissiles} state each game loop iteration by calling {@link #updatePosition()}
	 * to adjust its horizontal position based on predefined movement.
	 */

	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
