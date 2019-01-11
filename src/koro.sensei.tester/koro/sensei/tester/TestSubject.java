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
	public static void assertTestCondition(boolean assertion, 
			String failureMessage) throws TestFailureException {
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
	public static void assertTestCondition(boolean assertion, String formattedFailureMessage, 
			Object... formatParameters) throws TestFailureException {
		if (!assertion) {
			throw new TestFailureException(String.format(formattedFailureMessage, formatParameters));
		}
	}
	
	/**
	 * Assert that the specified function raises the specified exception, otherwise an exception with the 
	 * specified failure message is raised.
	 * 
	 * @param functionToTest - the expression to test wrapped into a function block
	 * @param exceptionType - the type of exception expected
	 * @param failureMessage - the message to display upon failure
	 * @throws TestFailureException if any other exception than the expected one is thrown or no exception 
	 * has been thrown at all
	 */
	public static void assertException(TestFunction functionToTest, 
			Class<? extends Throwable> exceptionType, String failureMessage) throws TestFailureException {
		if (functionToTest != null && exceptionType != null) {
			try {
				functionToTest.callFunction();
				throw new TestFailureException(failureMessage);
			} catch (Exception testException) {
				if (exceptionType.isInstance(testException)) {
					// Do nothing as this is expected behaviour.
				} else {
					throw new TestFailureException(testException);
				}
			}
		} else {
			throw new NullPointerException("Null cannot be tested.");
		}
	}
	
	/**
	 * Assert that the specified function raises the specified exception, otherwise an exception with the 
	 * specified failure message is raised.
	 * The failure message will be formated with the specified arguments.
	 * 
	 * @param functionToTest - the expression to test wrapped into a function block
	 * @param exceptionType - the type of exception expected
	 * @param formattedFailureMessage - the message to display upon failure
	 * @param formatParameters the arguments used to format the failure message
	 * @throws TestFailureException if any other exception than the expected one is thrown or no exception 
	 * has been thrown at all
	 */
	public static void assertException(TestFunction functionToTest, 
			Class<? extends Throwable> exceptionType, 
			String formattedFailureMessage, Object... formatParameters) throws TestFailureException {
		if (functionToTest != null && exceptionType != null) {
			try {
				functionToTest.callFunction();
				throw new TestFailureException(String.format(formattedFailureMessage, formatParameters));
			} catch (Exception testException) {
				if (exceptionType.isInstance(testException)) {
					// Do nothing as this is expected behaviour.
				} else {
					throw new TestFailureException(testException);
				}
			}
		} else {
			throw new NullPointerException("Null cannot be tested.");
		}
	}
	
}
