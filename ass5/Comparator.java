/**
 *  @author Itai Dagan 
 *  This is an interface of compartor
 */
public interface Comparator {
	/**
	 * @param o1
	 *            - the first object to be compared.
	 * @param o2
	 *            - the second object to be compared.
	 * @return negative integer, zero, or a positive integer if the first
	 *         argument is less than, equal to, or greater than the second argument, respectivly.
	 * 
	 */
	int compare(Object o1, Object o2);
}

