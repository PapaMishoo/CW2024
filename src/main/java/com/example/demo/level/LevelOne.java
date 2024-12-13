package com.example.demo.level;

import com.example.demo.activeactor.ActiveActorDestructible;
import com.example.demo.activeactor.EnemyJet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LevelOne extends LevelParent {
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.level.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = 0.01;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final double[] PREDEFINED_Y_POSITIONS = {0.0, 0.25, 0.5, 0.75, 1.0};


	private final Set<Double> usedYPositions = new HashSet<>();

	private boolean isLevelFinished = false;

	/**
	 * Constructs a {@link LevelOne} instance, initializing the screen dimensions, background, player health, and
	 * level-specific configurations.
	 *
	 * @param screenHeight the height of the game screen
	 * @param screenWidth the width of the game screen
	 */
	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks if the game ends due to the user being destroyed or progresses to the next level if the kill target
	 * is reached.
	 *Actions:
	 * - Calls {@link #loseGame()} if the user is destroyed.
	 * - Calls {@link #goToNextLevel(String)} if the kill target is met.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget() && !isLevelFinished) {
			goToNextLevel(NEXT_LEVEL);
			isLevelFinished = true;
		}
	}


	/**
	 * Adds the {@link com.example.demo.activeactor.PlayerJet} to the scene graph's root group, visually instantiating
	 * the player in the game world.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Spawns enemy units dynamically by checking spawn probability, assigning available Y positions, and managing
	 * overlap via `usedYPositions`.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				Double newEnemyInitialYPosition = getAvailableYPosition();

                ActiveActorDestructible newEnemy = new EnemyJet(this, getScreenWidth(), newEnemyInitialYPosition * getEnemyMaximumYPosition());
                addEnemyUnit(newEnemy);
                usedYPositions.add(newEnemyInitialYPosition);

                newEnemy.setOnDestroyed(() -> freeYPosition(newEnemyInitialYPosition));
            }
		}
	}

	/**
	 * Frees a Y position, making it available for reuse.
	 *@param yPosition the Y position to be freed
	 */
	private void freeYPosition(Double yPosition) {
		usedYPositions.remove(yPosition);
	}

	/**
	 * Returns an available Y position from predefined ones or a random value if all are occupied.
	 * @return an available Y position as a Double
	 */
	private Double getAvailableYPosition() {
		List<Double> availablePositions = new ArrayList<>();
		for (double y : PREDEFINED_Y_POSITIONS) {
			if (!usedYPositions.contains(y)) {
				availablePositions.add(y);
			}
		}

		if (availablePositions.isEmpty()) {
			return Math.random();
		}

		return availablePositions.get((int) (Math.random() * availablePositions.size()));
	}

	/**
	 * Creates and returns a new {@link LevelView} initialized with the root group and player's health.
	 * @return a new {@link LevelView} instance
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks if the user has reached the required kills to advance to the next level.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

	/**
	 * Handles level-specific logic not covered by primary methods, providing additional functionality as needed.
	 */
	@Override
	protected void misc(){

	}

}
