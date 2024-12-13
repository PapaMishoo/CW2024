package com.example.demo.activeactor;

import com.example.demo.controller.Controller;
import com.example.demo.level.LevelParent;

public class EnemyJet extends FighterJet {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 57;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -80.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;

	private final LevelParent level;

	/**
	 * Constructs an {@link EnemyJet} object that represents an enemy aircraft in the game.
	 * The enemy jet is placed at the specified initial x and y position and is associated
	 * with a given level.
	 *
	 * @param level the level in which this enemy jet exists
	 * @param initialXPos the initial x-coordinate
	 * @param initialYPos the initial y-coordinate
	 */
	public EnemyJet(LevelParent level, double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);

		this.level = level;
	}
	/**
	 * Updates the {@link EnemyJet} position by moving it horizontally based on {@code HORIZONTAL_VELOCITY}.
	 * This method simulates movement across the screen during the game loop.
	 */

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires an {@link EnemyMissiles} if the random probability meets the firing rate.
	 * The missile is created at a position determined by {@code PROJECTILE_X_POSITION_OFFSET} and {@code PROJECTILE_Y_POSITION_OFFSET}.
	 * Returns {@code null} if no missile is fired.
	 *
	 * @return An {@code ActiveActorDestructible} representing the fired missile, or {@code null}.
	 */

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyMissiles(projectileXPosition, projectileYPostion);
		}
		return null;
	}
	/**
	 * Updates the {@link EnemyJet} state each game loop iteration by:
	 * 1. Moving the jet horizontally with {@code updatePosition()}.
	 * 2. Firing a projectile with {@code fireProjectile()} and spawning it if created.
	 */

	@Override
	public void updateActor() {
		updatePosition();
		level.spawnEnemyProjectile(fireProjectile());
	}
}
