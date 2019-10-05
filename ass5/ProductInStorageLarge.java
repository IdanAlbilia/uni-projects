/**
 * This class extend the Product class and represents large product in storage
 * @author Itai Dagan
 *
 */
public class ProductInStorageLarge extends Product {
	/**
	 *  Construcor for ProductInStorageLarge
	 * @param name
	 * @param serialNum
	 * @param price
	 */
	public ProductInStorageLarge(String name, int serialNum, double price) {
		//use the super construcor with the same arguments
		super(name, serialNum, price);
	}
	/**
	 * Copy Construcor for ProductInStorageLarge
	 * @param data
	 */
	public ProductInStorageLarge(Object data)
	{
		//use the super construcor with the same arguments
		super(data);
	}
	/**
	 * This function overrides the super method and calculate the shipping rate by 10% of the product price
	 */
	@Override
	public double[] computeFinalPrice() {
		double [] array = {getProductPrice(),Math.floor(getProductPrice()*0.1)};
		return array;
	}

}
