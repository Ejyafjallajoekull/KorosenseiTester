package koro.sensei.tester;


/**
 * The TestSubject interface streamlines the access of testing functions.
 * 
 * @author Planters
 *
 */
public interface TestSubject {

	/**
	 * Run all predefined tests on this test subject.
	 * 
	 * @throws TestFailureException this will be thrown, if a test did not meet the
	 * specified requirements
	 */
	public void runAllTests() throws TestFailureException;
	
	/**
	 * Assert that the test condition is true, otherwise an exception with the 
	 * specified failure message is raised.
	 * 
	 * @param assertion - a condition, which must be true
	 * @param failureMessage - the message the thrown exception will carry
	 *  if the assertion is false
	 * @throws TestFailureException this will be thrown if the assertion if false
	 */
	public static void assertTestCondition(boolean assertion, String failureMessage) throws TestFailureException {
		if (!assertion) {
			throw new TestFailureException(failureMessage);
		}
	}
	
	/**
	 * Assert that the test condition is true, otherwise an exception with the 
	 * specified failure message is raised.
	 * The failure message will be formated with the specified arguments.
	 * 
	 * @param assertion - a condition, which must be true
	 * @param formattedFailureMessage - the message the thrown exception will carry
	 *  if the assertion is false
	 * @param formatParameters the arguments used to format the failure message
	 * @throws TestFailureException this will be thrown if the assertion if false
	 */
	public static void assertTestCondition(boolean assertion, String formattedFailureMessage, Object... formatParameters) throws TestFailureException {
		if (!assertion) {
			throw new TestFailureException(String.format(formattedFailureMessage, formatParameters));
		}
	}
	
//	public static void assertException(TestFunction functionToTest, Class<? extends Exception> exceptionType, String failureMessage, Object ... testFunctionArguments) throws TestFailureException {
//		if (functionToTest != null) {
//			
//		} else {
//			throw new NullPointerException("");
//		}
//	}
	
}
