/**
 * This class extend the Product class and represents medium product in storage
 * @author Itai Dagan 
 *
 */
public class ProductInStorageMedium extends Product {
	/**
	 *  Construcor for ProductInStorageMedium
	 * @param name
	 * @param serialNum
	 * @param price
	 */
	public ProductInStorageMedium(String name, int serialNum, double price) {
		//use the super construcor with the same arguments
		super(name, serialNum, price);
	}
	/**
	 * Copy Construcor for ProductInStorageMedium
	 * @param data
	 */
	public ProductInStorageMedium(Object data)
	{
		//use the super construcor with the same arguments
		super(data);
	}
	/**
	 * This function overrides the super method and calculate the shipping rate by 7% of the product price
	 */
	@Override
	public double[] computeFinalPrice() {
		double [] array = {getProductPrice(),Math.floor(getProductPrice()*0.07)};
		return array;
	}

}
