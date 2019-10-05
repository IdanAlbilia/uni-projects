/**
 * This class extend the Product class and represents small product in storage
 * @author Itai Dagan
 *
 */
public class ProductInStorageSmall extends Product {
	/**
	 * Construcor for ProductInStorageSmall
	 * @param name
	 * @param serialNum
	 * @param price
	 */
	public ProductInStorageSmall(String name, int serialNum, double price) {
		//use the super construcor with the same arguments
		super(name, serialNum, price);
	}
	/**
	 * Copy Construcor for ProductInStorageSmall
	 * @param data
	 */
	public ProductInStorageSmall(Object data)
	{
		//use the super construcor with the same arguments
		super(data);
	}
	/**
	 * This function overrides the super method and calculate the shipping rate by 5% of the product price
	 */
	@Override
	public double[] computeFinalPrice() {
		double [] array = {getProductPrice(),Math.floor(getProductPrice()*0.05)};
		return array;
	}

}
