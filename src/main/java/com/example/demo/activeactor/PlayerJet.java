package com.example.demo.activeactor;

public class PlayerJet extends FighterJet {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = 670.0;
	private static final double X_LEFT_BOUND = 0; // Horizontal left boundary
	private static final double X_RIGHT_BOUND = 1250.0; // Horizontal right boundary (adjust based on screen width)
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 45;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8; // Velocity for horizontal movement
	private static final int PROJECTILE_X_POSITION = 100;
	private static final int PROJECTILE_Y_POSITION_OFFSET = -20;
	private int verticalVelocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;

	/**
	 * Constructs a {@link PlayerJet} with the specified initial health, predefined image attributes,
	 * and zero velocity multipliers. Represents the main character capable of movement and firing projectiles.
	 *
	 * @param initialHealth The initial health of the PlayerJet.
	 */
	public PlayerJet(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Updates the {@link PlayerJet} position based on movement state and velocity multipliers.
	 * Ensures the jet stays within predefined bounds by resetting translation if it exceeds them.
	 * Overrides the base class method for position updates specific to PlayerJet.
	 */
	@Override
	public void updatePosition() {

		if (isMovingVertically()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * verticalVelocityMultiplier);
			double newPositionY = getLayoutY() + getTranslateY();
			if (newPositionY < Y_UPPER_BOUND || newPositionY > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}


		if (isMovingHorizontally()) {
			double initialTranslateX = getTranslateX();
			this.moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);
			double newPositionX = getLayoutX() + getTranslateX();
			if (newPositionX < X_LEFT_BOUND || newPositionX > X_RIGHT_BOUND) {
				this.setTranslateX(initialTranslateX);
			}
		}
	}

	/**
	 * Updates the {@link PlayerJet} state by recalculating its position using {@link #updatePosition()}.
	 * Ensures the jet remains within bounds and overrides the superclass method for position updates.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a {@link PlayerMissiles} from the {@code PlayerJet}'s position with a Y-offset.
	 * Overrides the base class to create a missile specific to the PlayerJet's location.
	 *
	 * @return a {@link PlayerMissiles} projectile fired from the PlayerJet.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new PlayerMissiles(getTranslateX() + 100, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	/**
	 * Checks if the {@link PlayerJet} is moving vertically.
	 *
	 * @return {@code true} if the vertical velocity multiplier is non-zero, {@code false} otherwise.
	 */
	private boolean isMovingVertically() {
		return verticalVelocityMultiplier != 0;
	}

	/**
	 * Checks if the {@link PlayerJet} is moving horizontally.
	 *
	 * @return {@code true} if the horizontal velocity multiplier is non-zero, {@code false} otherwise.
	 */
	private boolean isMovingHorizontally() {
		return horizontalVelocityMultiplier != 0;
	}

	/**
	 * Initiates upward movement by setting the {@code verticalVelocityMultiplier} to a negative value.
	 */
	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	/**
	 * Initiates downward movement by setting the {@code verticalVelocityMultiplier} to a positive value.
	 */
	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	/**
	 * Stops vertical movement by setting {@code verticalVelocityMultiplier} to zero.
	 */
	public void stopVertical() {
		verticalVelocityMultiplier = 0;
	}

	/**
	 * Initiates leftward movement by setting {@code horizontalVelocityMultiplier} to a negative value.
	 */
	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	/**
	 * Initiates rightward movement by setting {@code horizontalVelocityMultiplier} to a positive value.
	 */
	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	/**
	 * Stops horizontal movement by setting {@code horizontalVelocityMultiplier} to zero.
	 */
	public void stopHorizontal() {
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Retrieves the number of kills achieved by the {@link PlayerJet}.
	 *
	 * @return the total number of kills recorded for the {@code PlayerJet}.
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the {@code numberOfKills} by one, tracking an enemy defeat.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}


}

