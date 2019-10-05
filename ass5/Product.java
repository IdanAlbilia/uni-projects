/**
 * This is an abstract class the represents a product that have a name,serialNum and price
 * @author Itai Dagan 
 *
 */
public abstract class Product {

	// Fields
	private String name;
	private int serialNum;
	private double price;


	// Constructors
	/**
	 * constructor for product
	 * @param name String of letters that is longer than 0
	 * @param serialNum Serial number bigger than 0
	 * @param price Price that is bigger than 0
	 */
	public Product(String name, int serialNum, double price){
		if(name == null || name.length() <= 0 || serialNum <= 0|| price<= 0)
		{
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.serialNum = serialNum;
		this.price = price;
	}
	/**
	 * Copy constructor for product that gets an Object and if his instance is product it copy him
	 * @param data
	 */
	public Product(Object data)
	{
		if(data instanceof Product)
		{
			Product other = (Product)data;
			this.name = other.name;
			this.serialNum = other.serialNum;
			this.price = other.price;
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}
	// Methods
	/**
	 * An abstract function to be implemented in the extend classes of this class.
	 * This function calculate the final price of the product by his specific type
	 * @return 2D array that holds in the first index the price and in the second the shipping rate
	 */
	public abstract double[] computeFinalPrice();
	/**
	 * Getter for the product name
	 * @return the product name
	 */
	public String getProductName()
	{
		return this.name;
	}
	/**
	 * 
	 * @return product serialNum
	 */
	public int getProductSerialNumber()
	{
		return this.serialNum;
	}
	/**
	 * Getter for the product price
	 * @return the product price
	 */
	public double getProductPrice()
	{
		return this.price;
	}
	/**
	 * Returns a string with the product name,serialNum and price
	 */
	@Override
	public String toString()
	{
		String ans = "Product: ";
		ans = ans + this.name + ", " + this.serialNum + ", "+ this.price; 
		return ans;
	}
	/**
	 * 	If 2 products have the same serial number it returns true else returns false
	 */
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Product)
		{
			if(this.serialNum == ((Product)other).serialNum)
			{
				return true;
			}
		}
		return false;
	}
}
