package com.example.demo;

import com.example.demo.activeactor.ActiveActorDestructible;

import java.util.List;

public class CollisionHandler {

    /**
     * Handles enemy breaches by damaging the player and destroying the enemy.
     *
     * @param user the player-controlled actor who takes damage if defenses are breached
     * @param enemyUnits the list of enemy units to check for defensive breaches
     */
    public static void handleEnemyPenetration(ActiveActorDestructible user, List<ActiveActorDestructible> enemyUnits) {
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemyHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy();
            }
        }
    }

    /**
     * Checks if an enemy has penetrated the defensive boundary.
     *
     * @param enemy the enemy actor to check
     * @return true if the enemy's position is beyond the defensive boundary, false otherwise
     */
    private static boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return enemy.getTranslateX() + enemy.getLayoutX() < 0;
    }

    /**
     * Handles collisions between two actor lists, damaging both actors on collision.
     *
     * @param actors1 the first list of destructible actors
     * @param actors2 the second list of destructible actors
     */
    public static void handleCollisions(List<ActiveActorDestructible> actors1,
                                        List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                }
            }
        }
    }
}
