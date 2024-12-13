package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import com.example.demo.userinterface.Pause;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.level.LevelParent;

public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.level.LevelOne";
	private final Stage stage;
	private final Pause pause;

	/**
	 * Constructs a {@link Controller} to initialize the game stage and pause functionality.
	 */
	public Controller(Stage stage) {
		this.stage = stage;
		this.pause = new Pause(stage); // Pass the stage and the active game scene
	}

	/**
	 * Initiates the game by displaying the primary stage and dynamically loading the first level using reflection.
	 *
	 * @throws ClassNotFoundException if the level class {@code LEVEL_ONE_CLASS_NAME} cannot be located
	 * @throws NoSuchMethodException if the required constructor for the level class is not found
	 * @throws SecurityException if access to the constructor is restricted
	 * @throws InstantiationException if an instance of the level class cannot be created
	 * @throws IllegalAccessException if the constructor or its class is inaccessible
	 * @throws IllegalArgumentException if the provided arguments for the constructor are invalid
	 * @throws InvocationTargetException if the invoked constructor throws an exception
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	/**
	 * Transitions the game to the specified level by dynamically loading its class, creating an instance, and
	 * initializing its scene using reflection.
	 *
	 * @param className the fully qualified name of the level class to load and start
	 * @throws ClassNotFoundException if the specified level class cannot be located
	 * @throws NoSuchMethodException if the required constructor for the level class is not found
	 * @throws SecurityException if the application is not allowed to access the constructor
	 * @throws InstantiationException if an instance of the level class cannot be created
	 * @throws IllegalAccessException if the constructor or its class is inaccessible
	 * @throws IllegalArgumentException if the provided arguments are invalid for the constructor
	 * @throws InvocationTargetException if the invoked constructor throws an exception
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
			myLevel.addObserver(this);
			Scene scene = myLevel.initializeScene();
			stage.setScene(scene);
			myLevel.startGame();
			pause.setCurrentScene(scene);
			pause.setCurrentLevel(myLevel);
	}

	/**
	 * Handles updates from the observable object by attempting to transition to the specified level, catching
	 * exceptions, and displaying error alerts if needed.
	 *
	 * @param arg0 the observable object being observed, typically the source of the update
	 * @param arg1 the argument passed by the observable object, in this case, expected to be
	 *             the name of the level class as a String
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			goToLevel((String) arg1);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
	}

}
