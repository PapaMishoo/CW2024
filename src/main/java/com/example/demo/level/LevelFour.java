package com.example.demo.level;

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

    private final List<ActiveActorDestructible> meteors;
    private final Set<Double> usedYPositions = new HashSet<>();

    public LevelFour(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

        this.meteors = new ArrayList<>();
    }

    @Override
    protected void updateScene() {
        super.updateScene();
        handleMeteorCollisions();
        handleMeteorOutOfBounds();
    }

    private void handleMeteorCollisions() {
        handleCollisions(friendlyUnits, meteors);
    }

    private void handleMeteorOutOfBounds() {
        for (ActiveActorDestructible meteor : meteors) {
            if (meteor.getTranslateY() > 750)
                meteor.destroy();
        }
    }

    @Override
    protected void updateActors() {
        super.updateActors();
        meteors.forEach(meteor -> meteor.updateActor());
    }

    @Override
    protected void removeAllDestroyedActors() {
        super.removeAllDestroyedActors();
        removeDestroyedActors(meteors);
    }


    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }

        else if (userHasReachedKillTarget()) {
            winGame();
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

    private void addMeteor(Meteor meteor) {
        meteors.add(meteor);
        getRoot().getChildren().add(meteor);
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
            return Math.random();  // If no predefined Y positions are available, randomize
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
        // Optional: Add any additional miscellaneous behavior for the level
    }
}

