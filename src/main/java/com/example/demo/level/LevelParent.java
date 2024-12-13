package com.example.demo.level;

import java.util.*;
import java.util.stream.Collectors;


import com.example.demo.CollisionHandler;
import com.example.demo.InputManager;
import com.example.demo.activeactor.ActiveActorDestructible;
import com.example.demo.activeactor.FighterJet;
import com.example.demo.activeactor.PlayerJet;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.util.Duration;

public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 40;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final PlayerJet user;
	private final Scene scene;
	private final ImageView background;


	protected final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	
	private int currentNumberOfEnemies;
	private LevelView levelView;


	/**
	 * Constructs a new {@link LevelParent} instance, initializing the game scene and its components
	 * including the player and enemy units, projectiles, background, and other level-specific attributes.
	 *
	 * @param backgroundImageName The file name of the background image that will be used for the game level.
	 * @param screenHeight The height of the game screen.
	 * @param screenWidth The width of the game screen.
	 * @param playerInitialHealth The initial health value for the player's character.
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new PlayerJet(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();


		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
	}

	/**
	 * Initializes all friendly units for the game, including setting their initial state and properties.
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Checks if the game is over based on the player's status and level conditions.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Spawns enemy units for the current level, adding them to the game world based on level-specific
	 * mechanics and spawning rules. Subclasses must implement this to define timing and placement.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Creates and returns a {@link LevelView} instance for the current level.
	 * Allows subclasses to customize initialization and configurations for level-specific elements
	 * like heart display, win screen, and game over screen.
	 *
	 * @return Configured {@link LevelView} for the current level.
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Allows subclasses to add level-specific logic during the {@code updateScene} process.
	 * Override to define custom behaviors beyond the standard {@code LevelParent} updates.
	 */
	protected abstract void misc();

	/**
	 * Sets up the game scene for the current level, including the background, friendly units,
	 * and heart indicator. Prepares all components for gameplay and returns the initialized {@link Scene}.
	 *
	 * @return The initialized {@link Scene} for the game level.
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	/**
	 * Starts gameplay by focusing on the background and initiating the game loop timeline.
	 * Ensures input handling and periodic updates are operational.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * Transitions the game to the next level by notifying observers about the level change.
	 * @param levelName The fully qualified name of the new level to transition to.
	 */
	public void goToNextLevel(String levelName) {
		setChanged();
		notifyObservers(levelName);

	}

	/**
	 invokes the necessary methods like updatingHandles and handlingCollisions
	 */
	protected void updateScene() {
		spawnEnemyUnits();
		updateActors();
		updateNumberOfEnemies();
		CollisionHandler.handleEnemyPenetration(friendlyUnits.get(0), enemyUnits);
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
		misc();
	}

	/**
	 * Sets up the {@link Timeline} with an indefinite cycle count and a {@link KeyFrame} to periodically invoke
	 * {@code updateScene()}.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Pauses the game by stopping the {@code timeline}, halting gameplay and state updates until resumed.
	 */
	public void pauseGame() {
		timeline.pause();
	}

	/**
	 * Resumes gameplay by restarting the {@code timeline}, allowing game updates and rendering to continue after a pause.
	 */
	public void resumeGame() {
		timeline.play();
	}

	/**
	 * Configures the background's dimensions, focus, input handling, and adds it with a dynamic level title label to the scene.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);


		InputManager inputManager = InputManager.getInstance();


		inputManager.initializeInputs(background, user, this::fireProjectile);

		root.getChildren().add(background);

		String levelName = this.getClass().getSimpleName();
		levelName = levelName.substring(0, 5) + " " + levelName.substring(5);

		Label label = new Label(levelName);
		label.setLayoutX(screenWidth / 2 - label.getLayoutBounds().getWidth() / 2);
		label.setScaleX(2);
		label.setScaleY(2);
		root.getChildren().add(label);
	}


	/**
	 * Fires a {@link com.example.demo.activeactor.PlayerMissiles}, adding it to the scene graph and tracking it in the user projectiles list.
	 */
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	/**
	 * Spawns an {@link com.example.demo.activeactor.EnemyMissiles} by adding it to the scene graph and tracking it in the enemy projectiles list.
	 */
	public void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * Updates all game actors, including units and missiles, by invoking their respective {@code updateActor} methods.
	 */
	protected void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	/**
	 * Removes destroyed actors from game entity lists to maintain consistent game states.
	 */
	protected void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	/**
	 * Removes destroyed {@link ActiveActorDestructible} actors from the provided list and the scene graph.
	 */
	protected void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	/**
	 * Handles collisions between friendly and enemy planes by delegating to the handleCollisions method.
	 */
	private void handlePlaneCollisions() {
		CollisionHandler.handleCollisions(friendlyUnits, enemyUnits);
	}

	/**
	 * Delegates collisions between user projectiles and enemy units to {@link CollisionHandler}, managing damage and entity removal.
	 */
	private void handleUserProjectileCollisions() {
		CollisionHandler.handleCollisions(userProjectiles, enemyUnits);
	}

	/**
	 * Manages collisions between {@link com.example.demo.activeactor.EnemyMissiles} and friendly units, handling damage and projectile removal.
	 */
	private void handleEnemyProjectileCollisions() {
		CollisionHandler.handleCollisions(enemyProjectiles, friendlyUnits);
	}

	/**
	 * Updates the player's health UI by adjusting the levelView to reflect the current health status.
	 */
	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * Updates the user's kill count by incrementing it for each eliminated enemy based on changes in enemy units.
	 */
	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	/**
	 * Stops the game timeline and displays a win image on the level view when the game is won.
	 */
	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}

	/**
	 * Stops the game timeline and displays a "Game Over" image when the player loses.
	 */
	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
	}

	/**
	 * @return the {@link PlayerJet} instance representing the user.
	 */
	protected PlayerJet getUser() {
		return user;
	}

	/**
	 * @return the root {@code Group} object.
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * @return the current count of enemy units.
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Adds an enemy unit to the enemy list and scene graph.
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}


	/**
	 * @return the maximum Y-coordinate position for the enemy.
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * @return the screen width as a double.
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * @return true if the user is destroyed; false otherwise.
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Updates the enemy count to match the current size of the enemy units list.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	/**
	 * @return the background {@code ImageView} instance.
	 */
	public ImageView getBackground() {
		return background;
	}
}
