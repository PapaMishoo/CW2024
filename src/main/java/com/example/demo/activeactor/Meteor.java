package com.example.demo.activeactor;

public class Meteor extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "meteor.png"; // Replace with your obstacle image
    private int speedY;

    public Meteor(double startX, double startY, int speedY) {
        super(IMAGE_NAME, 70, startX, startY);

        this.speedY = speedY;
    }

    @Override
    public void updatePosition() {
        setTranslateY(getTranslateY() + speedY);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public void takeDamage() {
        destroy();
    }
}