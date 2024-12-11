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

	public PlayerJet(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}

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

	@Override
	public void updateActor() {
		updatePosition();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return new PlayerMissiles(getTranslateX() + 100, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	private boolean isMovingVertically() {
		return verticalVelocityMultiplier != 0;
	}

	private boolean isMovingHorizontally() {
		return horizontalVelocityMultiplier != 0;
	}

	// Vertical movement controls
	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	public void stopVertical() {
		verticalVelocityMultiplier = 0;
	}

	// Horizontal movement controls
	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	public void stopHorizontal() {
		horizontalVelocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}


}

