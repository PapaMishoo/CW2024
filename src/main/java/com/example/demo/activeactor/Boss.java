package com.example.demo.activeactor;

import com.example.demo.level.LevelParent;

import java.util.*;

public class Boss extends FighterJet {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000;
	private static final double INITIAL_Y_POSITION = 250;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 0.00;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = 0.005;
	private static final int IMAGE_HEIGHT = 50;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 15;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = -250;
	private static final int Y_POSITION_LOWER_BOUND = 375;
	private static final int MAX_FRAMES_WITH_SHIELD = 150;
	private final LevelParent level;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	/**
	 * Constructs a new {@link Boss} instance, initializing its attributes and movement pattern.
	 *
	 * @param level The level in which the boss is being created. Represents an instance of {@code LevelParent},
	 *              providing contextual information such as screen dimensions and active actors in the level.
	 */
	public Boss(LevelParent level) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		this.level = level;
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
	}

	/**
	 * Retrieves the shielded state of the {@link Boss}.
	 *
	 * @return true if the Boss is currently shielded, false otherwise.
	 */
	public boolean getIsShielded() {
		return isShielded;
	}

	/**
	 * Updates the Boss's vertical position according to its movement pattern,
	 * ensuring it stays within defined vertical boundaries.
	 * Handles both upward and downward movement with position clamping.
	 */
	@Override
	public void updatePosition() {
		moveVertically(getNextMove());

		if (getTranslateY() < Y_POSITION_UPPER_BOUND)
			setTranslateY(Y_POSITION_UPPER_BOUND);
		else if (getTranslateY() > Y_POSITION_LOWER_BOUND)
			setTranslateY(Y_POSITION_LOWER_BOUND);
	}

	/**
	 * Updates the Boss actor's frame state, including position, shield management, and potential projectile firing.
	 * Handles vertical movement, shield dynamics, and projectile spawning during each game update.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
		level.spawnEnemyProjectile(fireProjectile());
	}

	/**
	 * Fires a Missile from the Boss if eligible in the current frame.
	 *
	 * @return Fired projectile or null if no firing occurs
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossMissiles(getProjectileInitialPosition()) : null;
	}

	/**
	 * Reduces Boss health if not shielded, delegating damage handling to superclass when applicable.
	 * Manages health reduction and associated effects based on shield state.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	/**
	 * Generates a randomized vertical movement pattern for the Boss.
	 * Creates a shuffled sequence of upward, downward, and stationary movements.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Manages Boss shield state during gameplay, handling activation, maintenance, and deactivation.
	 * Tracks shield duration and updates its status based on game conditions.
	 */
	private void updateShield() {
		if (isShielded) framesWithShieldActivated++;
		else if (shieldShouldBeActivated()) activateShield();	
		if (shieldExhausted()) deactivateShield();
	}

	/**
	 * Determines the next move in the {@link Boss} movement pattern.
	 * It tracks consecutive moves in the same direction, reshuffles the pattern when the limit is reached,
	 * and updates the move index for cycling through the pattern.
	 *
	 * @return The next vertical movement value: positive for downward, negative for upward, or zero for no movement.
	 */

	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines if the {@link Boss} should fire a {@link BossMissiles} in the current frame.
	 * It uses a random check against the {@code BOSS_FIRE_RATE} to decide if firing occurs.
	 *
	 * @return true if the Boss fires, false otherwise.
	 */

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Calculates the initial vertical position for a {@link BossMissiles} fired by the {@link Boss}.
	 * It uses the Boss's current layout and translation, offset by {@code PROJECTILE_Y_POSITION_OFFSET}.
	 *
	 * @return The initial vertical position of the projectile.
	 */

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}
	/**
	 * Determines if the {@link Boss} shield should be activated in the current frame.
	 * The decision is based on a random check against {@code BOSS_SHIELD_PROBABILITY}.
	 *
	 * @return true if the shield is activated, false otherwise.
	 */

	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}
	/**
	 * Checks if the {@link Boss} shield has been active for the maximum duration.
	 * It compares the active frame count to {@code MAX_FRAMES_WITH_SHIELD}.
	 *
	 * @return true if the shield has reached its duration, false otherwise.
	 */

	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}
	/**
	 * Activates the shield for the {@link Boss}, protecting it from damage.
	 * The shielded state is used by methods like {@code takeDamage()} and {@code updateShield()}.
	 * It remains active until deactivated, usually by {@code deactivateShield()}.
	 */

	private void activateShield() {
		isShielded = true;
	}
	/**
	 * Deactivates the {@link Boss} shield, allowing it to take damage.
	 * Resets the shield's active frame counter. Called when the shield is exhausted or conditions are no longer met.
	 */

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

}
