package com.example.demo.activeactor;

public abstract class Missiles extends ActiveActorDestructible {

	/**
	 * Constructs a {@link Missiles} instance with the specified image parameters and initial position.
	 * Represents a missile entity in the game.
	 *
	 * @param imageName The image file name for the missile.
	 * @param imageHeight The height of the missile image.
	 * @param initialXPos The initial horizontal position.
	 * @param initialYPos The initial vertical position.
	 */
	public Missiles(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles damage by invoking {@link #destroy()} to immediately destroy the object and trigger related behaviors.
	 * This overrides the parent class's implementation with direct destruction.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Updates the position of the {@link Missiles} object based on its movement behavior.
	 * Subclasses must implement this to define specific movement logic, invoked during the game loop.
	 */
	@Override
	public abstract void updatePosition();

}
