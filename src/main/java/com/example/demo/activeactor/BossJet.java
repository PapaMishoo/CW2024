package com.example.demo.activeactor;

import com.example.demo.level.LevelParent;

public class BossJet extends FighterJet {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final int IMAGE_HEIGHT = 57;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -80.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 3;
	private static final double FIRE_RATE = .01;

	private final LevelParent level;

	/**
	 * Constructs a `BossJet` object, a high-level {@link EnemyJet} for the game.
	 *
	 * @param level The game level this `BossJet` belongs to.
	 * @param initialXPos The initial x-coordinate of the `BossJet`.
	 * @param initialYPos The initial y-coordinate of the `BossJet`.
	 */

	public BossJet(LevelParent level, double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
		this.level = level;
	}

	/**
	 * Updates the {@link BossJet} position by moving it horizontally based on its velocity.
	 * This shifts the `BossJet` left across the screen using the {@code moveHorizontally} method.
	 */

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a {@link BossMissiles} if the fire rate condition is met.
	 * The projectile is created at a specified offset and launched.
	 * Returns null if the firing condition is not satisfied.
	 *
	 * @return An {@link ActiveActorDestructible} for the fired projectile, or null.
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
	 * Updates the {@link BossJet} state each game tick by:
	 * - Updating its position with {@code updatePosition} based on its velocity.
	 * - Attempting to fire a projectile with {@code fireProjectile} and adding it to the level if created.
	 */

	@Override
	public void updateActor() {
		updatePosition();
		level.spawnEnemyProjectile(fireProjectile());
	}

}
