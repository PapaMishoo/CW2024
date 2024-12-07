package com.example.demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LevelOne extends LevelParent {
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = 0.01;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final double[] PREDEFINED_Y_POSITIONS = {0.0, 0.25, 0.5, 0.75, 1.0};

	private final Set<Double> usedYPositions = new HashSet<>();

	private boolean isLevelFinished = false;

	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	/**
	 * This method checks if the game is over.
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

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				Double newEnemyInitialYPosition = getAvailableYPosition();

                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition * getEnemyMaximumYPosition());
                addEnemyUnit(newEnemy);
                usedYPositions.add(newEnemyInitialYPosition);

                newEnemy.setOnDestroyed(() -> freeYPosition(newEnemyInitialYPosition));
            }
		}
	}

	private void freeYPosition(Double yPosition) {
		usedYPositions.remove(yPosition);
	}

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



	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

	@Override
	protected void misc(){

	}

}
