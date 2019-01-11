package koro.sensei.tester;

/**
 * The TestFunction interface supports the calling of a singel function to test.
 * 
 * @author Planters
 *
 */
public interface TestFunction {

	/**
	 * Calls the function to test supplying the specified arguments. The arguments can be 
	 * supplied to use as an anonymous inner class.
	 * 
	 * @param functionArguments - the arguments for the function implementation to use
	 */
	public void callFunction() throws Exception;
	
}
