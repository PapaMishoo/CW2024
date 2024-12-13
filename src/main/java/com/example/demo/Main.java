package com.example.demo;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.controller.Controller;
import com.example.demo.userinterface.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private Controller myController;

	/**
	 * Initializes and displays the primary stage with title, size, and non-resizable window, then shows the main menu.
	 *
	 * @param stage the primary stage to be displayed
	 * @throws ClassNotFoundException if a class cannot be found during initialization
	 * @throws NoSuchMethodException if a required method is missing
	 * @throws SecurityException if access to a method or resource is denied
	 * @throws InstantiationException if an object cannot be instantiated
	 * @throws IllegalAccessException if access to a class or method is restricted
	 * @throws IllegalArgumentException if an invalid argument is passed
	 * @throws InvocationTargetException if an exception occurs during method invocation
	 */
	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);

		MainMenu mainMenu = new MainMenu();
		mainMenu.show(stage);
	}

	/**
	 *  Main entry point for the JavaFX application, launching the JavaFX runtime.
	 *
	 * @param args the command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		launch();
	}
}
