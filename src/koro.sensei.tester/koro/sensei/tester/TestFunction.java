package koro.sensei.tester;

/**
 * The TestFunction interface supports the calling of a single function to test.
 * 
 * @author Planters
 *
 */
public interface TestFunction {

	/**
	 * Calls the function to test.
	 * 
	 * <p> The interface is used to execute a function or group of code to test it 
	 * for specific behaviour, e.g. if a specific exception is thrown.</p>
	 * 
	 * @throws Exception if an exception is thrown by the called function
	 */
	public void callFunction() throws Exception;
	
}
