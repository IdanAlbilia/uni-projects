/**
 * Abstract "superclass" describing ALL tokens seen when evaluating
 * arithmetic expressions.
 */
public abstract class CalcToken {
 
	/**
	 * By making this method abstract, all subclasses MUST implement it.
	 * @return the String representation of this token.
	 */
	public abstract String toString();
  
}