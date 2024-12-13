package com.example.demo.activeactor;

public class Meteor extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "meteor.png"; // Replace with your obstacle image
    private int speedY;

    /**
     * Constructs a {@link Meteor} with the specified initial position and vertical speed.
     * The meteor moves downward with the provided speed.
     *
     * @param startX The initial horizontal position.
     * @param startY The initial vertical position.
     * @param speedY The vertical speed of the meteor.
     */
    public Meteor(double startX, double startY, int speedY) {
        super(IMAGE_NAME, 70, startX, startY);

        this.speedY = speedY;
    }

    /**
     * Updates the vertical position of the {@link Meteor} by adding {@code speedY} to its current y-coordinate,
     * creating downward movement. This is typically called each game loop iteration.
     */
    @Override
    public void updatePosition() {
        setTranslateY(getTranslateY() + speedY);
    }

    /**
     * Updates the actor's state and position by invoking {@code updatePosition} to adjust its vertical position.
     * This method is typically called each game loop iteration and can be overridden by subclasses for additional behavior.
     */

    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Handles the {@link Meteor} behavior when damaged by immediately invoking {@link #destroy()}
     * to mark it as destroyed and trigger related destruction effects.
     * Overrides {@link ActiveActorDestructible#takeDamage()}.
     */
    @Override
    public void takeDamage() {
        destroy();
    }
}