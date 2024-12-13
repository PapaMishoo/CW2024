package com.example.demo.level;

import com.example.demo.CollisionHandler;
import com.example.demo.activeactor.ActiveActorDestructible;
import com.example.demo.activeactor.BossJet;
import com.example.demo.activeactor.EnemyJet;
import com.example.demo.activeactor.Meteor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LevelFour extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/space.jpg";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 10;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.01;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final double[] PREDEFINED_Y_POSITIONS = {0.0, 0.25, 0.5, 0.75, 1.0};
    private static final int MAX_METEORS_ON_SCREEN = 2;

    /**
     * The {@link Meteor} field tracks active destructible meteors, represented as {@link ActiveActorDestructible},
     * interacting with mechanics like collisions and destruction. It is a dynamic {@code List} used in methods such as
     * {@code handleMeteorCollisions} and {@code removeAllDestroyedActors}.
     */
    private final List<ActiveActorDestructible> meteors;
    private final Set<Double> usedYPositions = new HashSet<>();

    /**
     * Initializes the {@link LevelFour} game level with screen dimensions, background image, player health, and a
     * list for meteor actors.
     *
     * @param screenHeight The height of the screen where the level will be displayed.
     * @param screenWidth The width of the screen where the level will be displayed.
     */
    public LevelFour(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

        this.meteors = new ArrayList<>();
    }

    /**
     * Extends the parent {@code updateScene} method in {@link LevelFour} to include meteor-specific behaviors like
     * handling collisions and removing out-of-bounds meteors.
     */
    @Override
    protected void updateScene() {
        super.updateScene();
        handleMeteorCollisions();
        handleMeteorOutOfBounds();
    }

    /**
     * Handles collisions between meteors and friendly units during the game update cycle, applying damage using
     * {@link CollisionHandler#handleCollisions}.
     */
    private void handleMeteorCollisions() {
        CollisionHandler.handleCollisions(friendlyUnits, meteors);
    }

    /**
     * Destroys meteors exceeding the screen's lower bounds (Y > 750) during the game update cycle using
     * {@link ActiveActorDestructible#destroy()}.
     */
    private void handleMeteorOutOfBounds() {
        for (ActiveActorDestructible meteor : meteors) {
            if (meteor.getTranslateY() > 750)
                meteor.destroy();
        }
    }

    /**
     * Updates all actors in the level, including meteors, by invoking {@code updateActor()} on each meteor in the
     * {@code meteors} collection.
     */
    @Override
    protected void updateActors() {
        super.updateActors();
        meteors.forEach(meteor -> meteor.updateActor());
    }

    /**
     * Cleans up destroyed actors, including meteors, from the {@code meteors} collection.
     */
    @Override
    protected void removeAllDestroyedActors() {
        super.removeAllDestroyedActors();
        removeDestroyedActors(meteors);
    }

    /**
     * Ends the game with a loss if the user is destroyed or a win if the kill target is reached.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }

        else if (userHasReachedKillTarget()) {
            winGame();
        }
    }

    /**
     * Adds the player's jet to the game scene, ensuring it is visible and interactive in {@code LevelFour}.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Spawns enemy units and {@link Meteor} in {@code LevelFour} based on thresholds and probabilities.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                Double newEnemyInitialYPosition = getAvailableYPosition();

                ActiveActorDestructible newEnemy = new BossJet(this, getScreenWidth(), newEnemyInitialYPosition * getEnemyMaximumYPosition());
                addEnemyUnit(newEnemy);
                usedYPositions.add(newEnemyInitialYPosition);

                newEnemy.setOnDestroyed(() -> freeYPosition(newEnemyInitialYPosition));
            }
        }

        if (meteors.size() < MAX_METEORS_ON_SCREEN) {
            Meteor meteor = new Meteor(Math.random() * 1200, 0, 10);
            addMeteor(meteor);
        }
    }

    /**
     * Adds a {@link Meteor} to the game level by updating the `meteors` collection and the scene's root node.
     * @param meteor The meteor instance to be added
     */
    private void addMeteor(Meteor meteor) {
        meteors.add(meteor);
        getRoot().getChildren().add(meteor);
    }

    /**
     * Frees a Y-position by removing it from the {@code usedYPositions} collection.
     * @param yPosition The Y-position to be freed.
     */
    private void freeYPosition(Double yPosition) {
        usedYPositions.remove(yPosition);
    }

    /**
     * Retrieves an available Y position from a predefined set or generates a random value if none are available.
     * @return an available Y position from the predefined set or a random value if none are available.
     */
    private Double getAvailableYPosition() {
        List<Double> availablePositions = new ArrayList<>();
        for (double y : PREDEFINED_Y_POSITIONS) {
            if (!usedYPositions.contains(y)) {
                availablePositions.add(y);
            }
        }

        if (availablePositions.isEmpty()) {
            return Math.random();  // If no predefined Y positions are available, randomize
        }

        return availablePositions.get((int) (Math.random() * availablePositions.size()));
    }

    /**
     * @return a new {@link LevelView} initialized with the root node and player health settings.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    /**
     * @return true if the user’s kill count meets or exceeds the required kills to advance, false otherwise.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    /**
     * Allows subclasses to add or override miscellaneous level-specific behavior.
     */
    @Override
    protected void misc(){
        // Optional: Add any additional miscellaneous behavior for the level
    }
}

