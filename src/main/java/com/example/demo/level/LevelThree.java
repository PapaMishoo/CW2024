package com.example.demo.level;

import com.example.demo.CollisionHandler;
import com.example.demo.activeactor.ActiveActorDestructible;
import com.example.demo.activeactor.EnemyJet;
import com.example.demo.activeactor.Meteor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LevelThree extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/space.jpg";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 10;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.01;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final double[] PREDEFINED_Y_POSITIONS = {0.0, 0.25, 0.5, 0.75, 1.0};
    private static final int MAX_METEORS_ON_SCREEN = 2;
    private static final String NEXT_LEVEL = "com.example.demo.level.LevelFour";

    private final List<ActiveActorDestructible> meteors;
    private final Set<Double> usedYPositions = new HashSet<>();

    private boolean isLevelFinished = false;

    /**
     * Constructs a {@link LevelThree} instance, initializing meteors and setting up the game scene using parent class functionality.
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

        this.meteors = new ArrayList<>();
    }

    /**
     * Extends the parent `updateScene` method in {@link LevelThree} to handle meteor-related mechanics, including
     * collisions and removing out-of-bounds meteors.
     */
    @Override
    protected void updateScene() {
        super.updateScene();
        handleMeteorCollisions();
        handleMeteorOutOfBounds();
    }

    /**
     * Manages collisions between friendly units and meteors during the update cycle, applying damage using the
     * `handleCollisions` helper.
     */
    private void handleMeteorCollisions() {
        CollisionHandler.handleCollisions(friendlyUnits, meteors);
    }

    /**
     * Removes off-screen {@link Meteor} by destroying those exceeding the vertical limit of 750 to optimize resources
     * and maintain gameplay performance.
     */
    private void handleMeteorOutOfBounds() {
        for (ActiveActorDestructible meteor : meteors) {
            if (meteor.getTranslateY() > 750)
                meteor.destroy();
        }
    }

    /**
     * Extends the parent `updateActors` method in {@link LevelThree} to update meteors by invoking their
     * `updateActor` method.
     */
    @Override
    protected void updateActors() {
        super.updateActors();
        meteors.forEach(meteor -> meteor.updateActor());
    }

    /**
     * Extends `LevelParent#removeAllDestroyedActors` to remove destroyed {@link Meteor} objects from the
     * {@code meteors} collection, maintaining game state and performance.
     */
    @Override
    protected void removeAllDestroyedActors() {
        super.removeAllDestroyedActors();
        removeDestroyedActors(meteors);
    }

    /**
     * Checks if the game ends with {@code loseGame()} if the player is destroyed or transitions to the next level
     * upon reaching the kill target.
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
     * Overrides the parent method to initialize friendly units by adding the
     * {@link com.example.demo.activeactor.PlayerJet} to the game scene's root group.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Overrides the parent method to spawn enemy units and {@link Meteor} in {@link LevelThree}, ensuring dynamic
     * gameplay by maintaining target counts and managing positions.
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

        if (meteors.size() < MAX_METEORS_ON_SCREEN) {
            Meteor meteor = new Meteor(Math.random() * 1200, 0, 10);
            addMeteor(meteor);
        }
    }

    /**
     * Adds a {@link Meteor} to the game by updating the meteors collection and including it in the root node for
     * visibility and interaction.     */
    private void addMeteor(Meteor meteor) {
        meteors.add(meteor);
        getRoot().getChildren().add(meteor);
    }

    /**
     * Frees a Y position by removing it from the list of used positions.
     */
    private void freeYPosition(Double yPosition) {
        usedYPositions.remove(yPosition);
    }

    /**
     * Calculates and returns an available Y position, selecting from unused predefined positions or generating a
     * random value if none are available.     */
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
     * Returns a {@link LevelView} configured for the level using the game root and player initial health.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    /**
     * @return true if the user's kill count meets or exceeds the required target, false otherwise.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    /**
     * Provides an extension point for level-specific behavior in {@link LevelThree}, allowing customization of
     * unique gameplay mechanics.
     */
    @Override
    protected void misc(){
        // Optional: Add any additional miscellaneous behavior for the level
    }
}

