package com.example.demo.activeactor;

import com.example.demo.SoundManager;

public abstract class FighterJet extends ActiveActorDestructible {

	private int health;

	/**
	 * Constructs a {@link FighterJet} with specified image, dimensions, position, and health.
	 *
	 * @param imageName The image file name for the FighterJet.
	 * @param imageHeight The image height of the FighterJet.
	 * @param initialXPos The initial horizontal position.
	 * @param initialYPos The initial vertical position.
	 * @param health The initial health value.
	 */

	public FighterJet(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Fires a {@link Missiles} from the current actor and returns its reference.
	 * Subclasses should implement this method to specify the projectile's properties, such as direction and position.
	 *
	 * @return An {@link ActiveActorDestructible} representing the fired projectile, or null if none is fired.
	 */

	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Applies damage to the {@link FighterJet}, reducing its health by one.
	 * If health reaches zero, it calls the `destroy` method and plays the "impactsound.wav" using SoundManager.
	 */

	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
			SoundManager.playSound("impactsound.wav");
		}
	}

	/**
	 * Calculates the X-coordinate for a {@link Missiles} starting position based on the object's position and a horizontal offset.
	 *
	 * @param xPositionOffset The offset to be added to the object's current position.
	 * @return The calculated X-coordinate for the missile's starting position.
	 */

	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y-coordinate for a {@link Missiles} initial position based on the object's position and a vertical offset.
	 *
	 * @param yPositionOffset The vertical offset to be added to the object's current position.
	 * @return The calculated Y-coordinate for the missile's starting position.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}
	/**
	 * Checks if the {@link FighterJet} health is zero, indicating it is destroyed.
	 *
	 * @return true if health is zero, otherwise false.
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Retrieves the current health of the {@link FighterJet}.
	 *
	 * @return the health value of the FighterJet, representing its remaining durability.
	 */
	public int getHealth() {
		return health;
	}
		
}

