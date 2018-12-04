package koro.sensei.tester;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;

import central.logging.functionality.LoggingFailureException;
import central.logging.functionality.LoggingHandler;


/**
 * The TestRunner class searches the current Java project for classes implementing 
 * the TestSubject interface and runs their testing method.
 * 
 * @author Planters
 *
 */
public class TestRunner {
	
	/**
	 * The extension of Java class files.
	 */
	private static final String CLASS_EXTENSION = ".class";
	/**
	 * The name of Java module declaration files.
	 */
	private static final String MODULE_IDENTIFIER = "module-info";
	
	/**
	 * The version number of the testing program.
	 */
	public static final String VERSION = "1.0.1.7";
	
	/**
	 * Run the test runner.<br>
	 * <br>
	 * <b>Command line parameters:</b><br>
	 * <br>
	 * <b>-test</b>: set the tests to be run<br>
	 * <b>-log_folder</b>: set the logging folder to the specified path<br>
	 * <b>-log_level</b>: set the logging level of the test runner with the following 
	 * integer arguments:<br>
	 * -2: completely disable all logging capabilities<br>
	 * -1: set the logging level to CONFIG<br>
	 * 0: set the logging level to OFF<br>
	 * 1: set the logging level to SEVERE<br>
	 * 2: set the logging level to WARNING<br>
	 * 3: set the logging level to INFO<br>
	 * 4: set the logging level to FINE<br>
	 * 5: set the logging level to FINER<br>
	 * 6: set the logging level to FINEST<br>
	 * 7: set the logging level to ALL<br>
	 * 
	 * @param args - the supplied command line arguments
	 */
	public static void main(String[] args) {
		// parse the command line arguments
		MainParameters mainParams = new MainParameters(args);
		Level logLevel = mainParams.getLoggingLevel();
		LoggingHandler.setLoggingFolder(mainParams.getLogFolderPath());
		LoggingHandler.setNumberLogFiles(Integer.MAX_VALUE);
		ArrayList<String> testsToRun = mainParams.getTests();

		// only start log writing if the level has not been set to a null object
		if (logLevel != null) {
			LoggingHandler.getLog().setLevel(logLevel);
			try {
				LoggingHandler.startLogWriting();
			} catch (LoggingFailureException e1) {
				LoggingHandler.logAndPrint(Level.WARNING, "Logging could not be started.", e1);
				e1.printStackTrace();
			}
		} else { // disable logging completely
			LoggingHandler.getLog().setLevel(Level.OFF);
		}
		LoggingHandler.getLog().info("Start running tests.");
		try {
			// create a list of all class files in the current project, excluding module declarations
			ArrayList<Path> list = new ArrayList<Path>();
			LoggingHandler.getLog().fine("Lookup path: " + (new File("")).toPath());
			Files.find((new File("")).toPath(), Integer.MAX_VALUE, (filePath, fileAttr) -> 
			filePath.toString().toLowerCase().endsWith(TestRunner.CLASS_EXTENSION) 
					&& !filePath.endsWith(MODULE_IDENTIFIER + CLASS_EXTENSION)).forEach(list::add);
			LoggingHandler.getLog().fine("Found the following classes: " + list.toString());

			// track statistics
			int successfullyTested = 0;
			int allTested = 0;
			
			// test all classes for the TestSubject interface
			for (Path classPath : list) {
				for (int i = 0; i < classPath.getNameCount(); i++) {
					try {
						String classQualifier = classPath.subpath(i, classPath.getNameCount()).toString().replace(File.separatorChar, '.');
						Class<?> testingClass = Class.forName(classQualifier.substring(0, classQualifier.length()-TestRunner.CLASS_EXTENSION.length()));
						if (!testingClass.isInterface() && TestRunner.isTestSubject(testingClass) 
								&& TestRunner.shouldBeTested(testingClass, testsToRun)) {
							// run all tests for all testing classes present
							LoggingHandler.logAndPrint(Level.INFO, "Testing class " + testingClass);
							allTested++;
							try {
								testingClass.getMethod("runAllTests").invoke(testingClass.getConstructor().newInstance());
								successfullyTested++;
							} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException 
									| InvocationTargetException | InstantiationException e) {
								if (e.getCause() instanceof TestFailureException) {
									LoggingHandler.logAndPrint(Level.SEVERE, "Some tests of " + testingClass
											+ " failed.", e);
								} else {
									LoggingHandler.logAndPrint(Level.SEVERE, "Test class " + testingClass
											+ " could not be tested.", e);	
								}
							}
						}
						break;
					} catch (ClassNotFoundException e) {
						/*
						 * Do nothing here as this is to be expected.
						 * The actual path is shortened from beginning to end in order
						 * to get the fully qualified class name.
						 */
					}
				}
			}
			// print statistics
			LoggingHandler.logAndPrint(Level.INFO, String.format("%d/%d tests successful.",
					successfullyTested, allTested));
			LoggingHandler.logAndPrint(Level.INFO, "Finished testing.");
		} catch (IOException e) {
			LoggingHandler.logAndPrint(Level.SEVERE, "The search for the class "
					+ "files did fail.", e);
			LoggingHandler.logAndPrint(Level.WARNING, "Testing aborted.");
		}
		try {
			LoggingHandler.stopLogWriting();
		} catch (LoggingFailureException e) {
			LoggingHandler.logAndPrint(Level.WARNING, "Logging could not be stopped.", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns whether the specified class implements the TestSubject interface.
	 * 
	 * @param classToTest - the class to test
	 * @return true if the specified class implements the TestSubject interface
	 */
	private static boolean isTestSubject(Class<?> classToTest) {
		if (classToTest != null && !classToTest.isInterface()) {
			for (Class<?> c : classToTest.getInterfaces()) {
				if (c.equals(TestSubject.class)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns whether the specified class should be tested given a list of all 
	 * classes to test. An empty list will result in all classes being tested.
	 * 
	 * @param classToTest - the class to check
	 * @param testsToRun - a list of all classes to be tested
	 * @return true if the specified class should be tested, false if not
	 */
	private static boolean shouldBeTested(Class<?> classToTest, ArrayList<String> testsToRun) {
		if (classToTest != null && (testsToRun == null 
				|| testsToRun.contains(classToTest.getSimpleName())
				|| testsToRun.isEmpty())) {
			return true;
		}
		return false;
	}
	
}
