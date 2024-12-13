package com.example.demo.activeactor;

import com.example.demo.SoundManager;


/**
 * Abstract base class for active, destructible game entities.
 * Extends {@link ActiveActor} and implements {@link Destructible}, enabling destruction logic.
 * Designed for entities with position updates, state changes, and customizable damage behavior.
 */

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private Runnable onDestroyedCallback;
	private boolean isDestroyed;

	/**
	 * Constructs a destructible {@link ActiveActor} with the specified image, size, and position.
	 *
	 * @param imageName   the image file name for the actor.
	 * @param imageHeight the height of the actor's image.
	 * @param initialXPos the initial X-coordinate of the actor.
	 * @param initialYPos the initial Y-coordinate of the actor.
	 */

	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Updates the object's position in the game world. Subclasses define specific movement logic.
	 * Called during each game loop iteration to adjust the object's coordinates.
	 */

	@Override
	public abstract void updatePosition();

	/**
	 * Updates the state and behavior of the {@link ActiveActorDestructible}.
	 * Subclasses implement specific logic beyond position changes, such as interactions and animations.
	 */

	public abstract void updateActor();

	/**
	 * Handles damage received by the object.
	 * Subclasses implement specific damage logic, health reduction,
	 * and related effects or state changes.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Marks the actor as destroyed, updating its state and triggering any defined destruction callback.
	 * Handles removal, effects, and system notifications when the actor is no longer functional.
	 */
	@Override
	public void destroy() {
		setDestroyed(true);

		if (onDestroyedCallback != null)
			onDestroyedCallback.run();
	}

	/**
	 * Sets the actor's destruction state, indicating whether it's active or destroyed.
	 *
	 * @param isDestroyed Boolean representing the actor's current state
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks whether the actor is currently in a destroyed state.
	 *
	 * @return true if the actor is destroyed, false otherwise.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * Sets a callback to be invoked when the actor is destroyed.
	 *
	 * @param callback Runnable to execute on destruction, or null
	 */
	public void setOnDestroyed(Runnable callback) {
		this.onDestroyedCallback = callback;
	}
	
}
