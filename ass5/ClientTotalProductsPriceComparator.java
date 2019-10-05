/**
 * Implements Comparator.
 * @author Itai Dagan 
 *
 */
public class ClientTotalProductsPriceComparator implements Comparator {
	/**
	 * This comparator is defined by if the final price of only the products the second client is bigger it will returns positive if the same 0 and if the second have less total price it returns negative number
	 */
	@Override
	public int compare(Object o1, Object o2) {
		//if one of them is not instance of Client returns ClassCastException
		if(!(o1 instanceof Client) || !(o2 instanceof Client))
		{
			throw new ClassCastException();
		}
		Client firstClient = (Client)o1;
		Client secondClient = (Client)o2;
		//returns int result by second - first client
		return (int) (secondClient.computeFinalProductsPrice()-firstClient.computeFinalProductsPrice());
	}

}
