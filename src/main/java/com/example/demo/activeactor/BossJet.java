package com.example.demo.activeactor;

import com.example.demo.level.LevelParent;

public class BossJet extends FighterJet {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final int IMAGE_HEIGHT = 62;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -80.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 3;
	private static final double FIRE_RATE = .01;

	private final LevelParent level;

	public BossJet(LevelParent level, double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
		this.level = level;
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyMissiles(projectileXPosition, projectileYPostion);
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
		level.spawnEnemyProjectile(fireProjectile());
	}

}
