/**
 * This class extend the Product class and represents product in store
 * @author Itai Dagan 
 *
 */
public class ProductInStore extends Product {
	/**
	 * Construcor for ProductInStore
	 * @param name
	 * @param serialNum
	 * @param price
	 */
	public ProductInStore(String name, int serialNum, double price) {
		//use the super construcor with the same arguments
		super(name, serialNum, price);
	}
	/**
	 * Copy Construcor for ProductInStore
	 * @param data
	 */
	public ProductInStore(Object data)
	{
		//use the super construcor with the same arguments
		super(data);
	}
	/**
	 * This function overrides the super method and his shipping rate is 0 because he his already in the store
	 */
	@Override
	public double[] computeFinalPrice() {
		double [] array = {getProductPrice(),0};
		return array;
	}

}
