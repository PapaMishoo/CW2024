package com.example.demo.activeactor;

public class EnemyMissiles extends Missiles {
	
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 27;
	private static final int HORIZONTAL_VELOCITY = -10;

	public EnemyMissiles(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public void updateActor() {
		updatePosition();
	}


}
