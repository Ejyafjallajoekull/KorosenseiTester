package koro.sensei.tester;

import java.io.File;
import java.time.LocalDateTime;
import java.util.logging.Level;

/**
 * The MainParameters class parses command line arguments.
 * 
 * @author Planters
 *
 */
public class MainParameters {

	// command line parameters
	private static final String LOG_LEVEL = "-log_level";
	private static final String LOG_FOLDER = "-log_folder";
	
	// variables, which can be set via command line
	private Level logLevel = Level.FINEST;
	private String logFolder = null;
	
	/**
	 * Create a set of command line set parameters from the specified string array.
	 * 
	 * @param arguments - the command line arguments
	 */
	public MainParameters(String[] arguments) {
		// init standard log folder
		LocalDateTime dateTimeNow = LocalDateTime.now();
		this.logFolder = String.format("Test_Runner_Logs%s%d_%d_%d_Test_Runner_Logs", 
				File.separator, dateTimeNow.getYear(), 
				dateTimeNow.getMonthValue(), dateTimeNow.getDayOfMonth());
		// parse arguments if existent
		if (arguments != null) {
			for (int i = 0; i < arguments.length; i++) {
				switch (arguments[i]) {
				
				case LOG_LEVEL:
					if (i < arguments.length - 1) {
						int level = Integer.parseInt(arguments[i+1]);
						switch (level) {
						
						case -2:
							this.logLevel = null;
							break;
						
						case -1:
							this.logLevel = Level.CONFIG;
							break;
						
						case 0:
							this.logLevel = Level.OFF;
							break;
							
						case 1: 
							this.logLevel = Level.SEVERE;
							break;
							
						case 2: 
							this.logLevel = Level.WARNING;
							break;
							
						case 3:
							this.logLevel = Level.INFO;
							break;
							
						case 4:
							this.logLevel = Level.FINE;
							break;
							
						case 5:
							this.logLevel = Level.FINER;
							break;
							
						case 6:
							this.logLevel = Level.FINEST;
							break;
							
						case 7:
							this.logLevel = Level.ALL;
							break;
							
						default:
							break;
							
						}
					}
					break;
					
				case LOG_FOLDER:
					if (i < arguments.length - 1) {
						this.logFolder = arguments[i+1];
					}
					break;
				
				default:
					break;
				
				}
			}
		}
	}
	
	/**
	 * Get the logging folder path, which has been set via the command line.
	 * If none has been set, the default one will be returned.
	 * 
	 * @return - the logging folder path
	 */
	public String getLogFolderPath() {
		return this.logFolder;
	}

	/**
	 * Get the logging level, which has been set via the command line.
	 * If none has been set, the defaul one will be returned.
	 * 
	 * @return - the logging level
	 */
	public Level getLoggingLevel() {
		return this.logLevel;
	}
	
}
