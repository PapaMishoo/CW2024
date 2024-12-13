package com.example.demo.level;

import com.example.demo.activeactor.Boss;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.level.LevelThree";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;

	private boolean isLevelFinished = false;

	/**
	 * Initializes {@link LevelTwo} with a background, screen dimensions, and player health.
	 * @param screenHeight The height of the game screen.
	 * @param screenWidth The width of the game screen.
	 */
	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss(this);
	}

	/**
	 * Adds the player-controlled jet to the scene graph.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Checks game-over conditions and transitions to the next level if the {@link Boss} is defeated.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (boss.isDestroyed() && !isLevelFinished) {
			goToNextLevel(NEXT_LEVEL);
			isLevelFinished = true;
		}
	}

	/**
	 * Spawns the {@link Boss} if no active enemy units are present.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	/**
	 * Creates and returns a {@link LevelView} for LevelTwo with shield and health display.
	 * @return A {@link LevelViewLevelTwo} instance.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

	/**
	 * Updates the {@link com.example.demo.userinterface.ShieldImage} based on the boss's shielded state.
	 */
	private void updateShieldImage() {
		if (boss.getIsShielded()) {
			levelView.showShield();
		} else {
			levelView.hideShield();
		}
	}

	/**
	 * Handles {@link LevelTwo}-specific updates, including boss shield visibility.
	 */
	@Override
	protected void misc() {
		updateShieldImage();
	}
}
