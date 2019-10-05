
import java.util.EmptyStackException;

/**
 * This is a testing framework. Use it extensively to verify that your code is working
 * properly.
 */
public class Tester {

	private static boolean testPassed = true;
	private static int testNum = 0;

	/**
	 * This entry function will test all classes created in this assignment.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		//		StackCalculator test1 = new StackCalculator();
		//		String str = test1.infixToPostfix("( 7 + 3 ) * 5");
		//		System.out.println(str);
		//		System.out.println(test1.evaluate(str));
		/* TODO - write a function for each class */
		// Each function here should test a different class.

		//BinaryOp
		testAddOp();
		testSubtractOp();
		testMultiplyOp();
		testDivideOp();
		testPowOp();
		//Calculators
		testStackCalculator();
		testTreeCalculator();

		// Notifying the user that the code have passed all tests. 
		if (testPassed) {
			System.out.println("All " + testNum + " tests passed!");
		}
	}

	/**
	 * This utility function will count the number of times it was invoked. 
	 * In addition, if a test fails the function will print the error message.  
	 * @param exp The actual test condition
	 * @param msg An error message, will be printed to the screen in case the test fails.
	 */
	private static void test(boolean exp, String msg) {
		testNum++;

		if (!exp) {
			testPassed = false;
			System.out.println("Test " + testNum + " failed: "  + msg);
		}
	}

	/**
	 * Checks the SubtractOp class.
	 */
	private static void testSubtractOp() {
		SubtractOp Operator = new SubtractOp();
		test((Operator.getPrecedence() == 1.0), "The answer should be 1.0 .");
		test((Operator.toString().equals("-")), "The string - should be printed.");
		test((Operator.operate(-18.0 , -4.0) == -14.0), "The answer should be -14.0 .");
		test((Operator.operate(18.0 , -4.0) == 22.0), "The answer should be 22.0 .");
		test((Operator.operate(18.0 , 4.0) == 14.0), "The answer should be 14.0 .");
	}

	/**
	 * Checks the AddOp class.
	 */
	private static void testAddOp() {
		AddOp Operator = new AddOp();
		test((Operator.getPrecedence() == 1.0), "The answer should be 1.0 .");
		test((Operator.toString().equals("+")), "The string + should be printed.");
		test((Operator.operate(-10.0 , -2.5) == -12.5), "The answer should be -12.5 .");
		test((Operator.operate(10.0 , -2.5) == 7.5), "The answer should be 7.5 .");
		test((Operator.operate(2.0 , 2.5) == 4.5), "The answer should be 4.5 .");

	}

	/**
	 * Checks the MultiplyOp class.
	 */
	private static void testMultiplyOp() {
		MultiplyOp Operator = new MultiplyOp();
		test((Operator.getPrecedence() == 2.0), "The answer should be 2.0 .");
		test((Operator.toString().equals("*")), "The string * should be printed.");
		test((Operator.operate(-7.0 , -5.0) == 35.0), "The answer should be 35.0 .");
		test((Operator.operate(7.0 , -5.0) == -35.0), "The answer should be -35.0 .");
		test((Operator.operate(7.0 , 5.0) == 35.0), "The answer should be 35.0 .");
	}

	/**
	 * Checks the DivideOp class.
	 */
	private static void testDivideOp() {
		DivideOp Operator = new DivideOp();
		test((Operator.getPrecedence() == 2.0), "The answer should be 2.0 .");
		test((Operator.toString().equals("/")), "The string / should be printed.");
		test((Operator.operate(-21.0 , -3.0) == 7.0), "The answer should be 7.0 .");
		test((Operator.operate(-21.0 , 3.0) == -7.0), "The answer should be -7.0 .");
		test((Operator.operate(15.0 , 3.0) == 5.0), "The answer should be 5.0 .");
	}

	/**
	 * Checks the PowOp class.
	 */
	private static void testPowOp() {
		PowOp Operator = new PowOp();
		test((Operator.getPrecedence() == 3.0), "The answer should be 3.0 .");
		test((Operator.toString().equals("^")), "The string ^ should be printed.");
		test((Operator.operate(2.0 , -1.0) == 0.5), "The answer should be 0.5 .");
		test((Operator.operate(2.0 , 0) == 1), "The answer should be 1 .");
		test((Operator.operate(2.0 , 4.0) == 16), "The answer should be 3.5 .");

	}

	/**
	 * Checks the StackCalculator class.
	 */
	private static void testStackCalculator() {
		StackCalculator stack = new StackCalculator();
		String postExp = stack.infixToPostfix("( 7 + 3 ) * ( 18 - 2 )");
		test(postExp.equals("7.0 3.0 + 18.0 2.0 - *") , "The output should be  7.0 3.0 + 18.0 2.0 - *");
		double result = stack.evaluate(postExp);
		test(result ==  160.0, "The output of should be  160.0");

		postExp = stack.infixToPostfix("( 3.0 + 4.0 )");
		test(postExp.equals("3.0 4.0 +") , "The output of should be 3.0 4.0 +");
		result = stack.evaluate(postExp);
		test(result ==  7.0, "The output of should be 7.0");


		postExp = stack.infixToPostfix("( 7.0 * 4.0 )");
		test(postExp.equals("7.0 4.0 *") , "The output of should be 7.0 4.0 *");
		result = stack.evaluate(postExp);
		test(result ==  28.0, "The output of should be 28.0");

		postExp = stack.infixToPostfix("( 3.0 - 8.0 )");
		test(postExp.equals("3.0 8.0 -") , "The output of should be 3.0 8.0 -");
		result = stack.evaluate(postExp);
		test(result ==  -5.0, "The output of should be -5.0");

		postExp = stack.infixToPostfix("( ( ( 3.0 - 8.0 ) * 3.0 ) + 16.0 )");
		test(postExp.equals("3.0 8.0 - 3.0 * 16.0 +") , "The output of should be  3.0 8.0 - 3.0 * 16.0 +");
		result = stack.evaluate(postExp);
		test(result ==  1.0, "The output of should be  1.0");


		postExp = stack.infixToPostfix("( ( ( 11.0 - 8.0 ) * 3.0 ) + 16.0 )");
		test(postExp.equals("11.0 8.0 - 3.0 * 16.0 +") , "The output of should be  11.0 8.0 - 3.0 * 16.0 +");
		result = stack.evaluate(postExp);
		test(result ==  25.0, "The output of should be  25.0");

		postExp = stack.infixToPostfix("( ( ( 4.0 ^ 2.0 ) / 2.0 ) + 2.0 )");
		test(postExp.equals("4.0 2.0 ^ 2.0 / 2.0 +") , "The output of should be  4.0 2.0 ^ 2.0 / 2.0 +");
		result = stack.evaluate(postExp);
		test(result ==  10.0, "The output of should be  10.0");

		postExp = stack.infixToPostfix("( ( 2.0 * ( 4.0 + 6.0 ) ) - ( 3.0 * 1.0 ) )");
		test(postExp.equals("2.0 4.0 6.0 + * 3.0 1.0 * -") , "The output of should be  2.0 4.0 6.0 + * 3.0 1.0 * -");
		result = stack.evaluate(postExp);
		test(result ==  17.0, "The output of should be  17.0");

		postExp = stack.infixToPostfix("( 2.0 ^ ( 1.0 + 1.0 ) ) * ( 6.0 * ( 3.0 + 1.0 ) )");
		test(postExp.equals("2.0 1.0 1.0 + ^ 6.0 3.0 1.0 + * *") , "The output of should be  2.0 1.0 1.0 + ^ 6.0 3.0 1.0 + * *");
		result = stack.evaluate(postExp);
		test(result ==  96.0, "The output of should be  96.0");

	}

	/**
	 * * Checks the TreeCalculator class.
	 */
	private static void testTreeCalculator() {
		TreeCalculator Tree  = new TreeCalculator();
		double Treetest = Tree.evaluate("3 4 +");

		Treetest = Tree.evaluate("7 8 10 + + 11 -");
		test(Treetest == 14, "The output of 7 8 10 + 11 - + is 14");
		test(Tree.getPrefix().equals("- + 7.0 + 8.0 10.0 11.0"),"The output of should be - + 7 + 8 10 11");

		Treetest = Tree.evaluate("2 8 10 * / 40 *");
		test(Treetest == 1, "The output of 2 8 10 * 40 * / is 14");
		test(Tree.getPrefix().equals("* / 2.0 * 8.0 10.0 40.0"),"The output of should be - + 7 + 8 10 11");

		Treetest = Tree.evaluate("2 4 6 + * 3 1 * -");
		test(Treetest == 17,"The output of 2 4 6 + * 3 1 * - should be 17");
		test(Tree.getPrefix().equals("- * 2.0 + 4.0 6.0 * 3.0 1.0"), "The output of should be - * 2.0 + 4.0 6.0 * 3.0 1.0");


		Treetest = Tree.evaluate("2.0 1.0 1.0 + ^ 6.0 3.0 1.0 + * *");
		test(Treetest == 96,"The output of 2.0 1.0 1.0 + ^ 6.0 3.0 1.0 + * * should be 96");
		test(Tree.getPrefix().equals("* ^ 2.0 + 1.0 1.0 * 6.0 + 3.0 1.0"), "The output of should be * ^ 2.0 + 1.0 1.0 * 6.0 + 3.0 1.0");



	}

}