

/**
 * Abstract class describing binary arithmetic operations.
 */
public abstract class BinaryOp extends CalcToken {
		char op;
	/**
	 * Return the precedence value of this operation
	 * (note that actual values do not matter, only their
	 * relative values to each other).
	 * @return the precedence of this operation.
	 */
	public double getPrecedence() {
		return 0.0;
	}

	/**
	 * Return the result of this operation on its operands.
	 * @param left the left operand.
	 * @param right the right operand.
	 * @return the result of the operation.
	 */
	public abstract double operate(double left, double right);  
}