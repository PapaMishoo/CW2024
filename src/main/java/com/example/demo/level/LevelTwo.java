package com.example.demo.level;

import com.example.demo.activeactor.Boss;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.level.LevelThree";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;

	private boolean isLevelFinished = false;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

		boss = new Boss(this);
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}

		else if (boss.isDestroyed() && !isLevelFinished) {
			goToNextLevel(NEXT_LEVEL);
			isLevelFinished = true;
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

	private void updateShieldImage(){
		if (boss.getIsShielded()) {
			levelView.showShield();
		}else {
			levelView.hideShield();
		}

	}

 	@Override
	protected void misc() {
			updateShieldImage();
	}
}
