package com.example.demo.activeactor;

public class EnemyMissiles extends Missiles {
	
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 27;
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * Creates an {@code EnemyMissiles} instance at the specified position with predefined image properties.
	 *
	 * @param initialXPos The missile's initial x-coordinate.
	 * @param initialYPos The missile's initial y-coordinate.
	 */

	public EnemyMissiles(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the {@link Missiles} position by moving it horizontally to the left based on predefined velocity.
	 * This method overrides the superclass method to define specific movement for {@link EnemyMissiles}.
	 */

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the actor's position by invoking {@code updatePosition()}, moving it horizontally based on predefined velocity.
	 * This method overrides {@code updateActor} to define the actor's behavior during each game loop cycle.
	 */

	@Override
	public void updateActor() {
		updatePosition();
	}


}
