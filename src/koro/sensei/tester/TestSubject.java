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
	 * 
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
	
}
