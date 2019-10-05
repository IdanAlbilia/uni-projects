public class Client {
	
	// Fields
	private String firstName;
	private String lastName;
	private int id;
	private LinkedList products;
	
	
	// Constructors
	/**
	 * Constructor of Client
	 * @param firstName String of letters have to be at least 1 letter
	 * @param lastName String of letters have to be at least 1 letter
	 * @param id Id of the client must be above 0
	 */
	public Client(String firstName, String lastName, int id){
		if(firstName == null || firstName.length() <= 0 ||lastName == null || lastName.length() <= 0 || id <= 0)
		{
			throw new IllegalArgumentException();
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.products = new LinkedList();
	}


	// Methods
	/**
	 * Copy constructor of Client 
	 * @param data The client to copy from, if the object is not client it will throw new exception
	 */
	public Client(Object data) {
		if(data instanceof Client)
		{
			Client other = (Client)data;
			this.firstName = other.firstName;
			this.lastName = other.lastName;
			this.id = other.id;
			this.products = new LinkedList(other.products);
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}

	public String getLastName()
	{
		return this.lastName;
	}
	public String getFirstName()
	{
		return this.firstName;
	}
	public int getId()
	{
		return this.id;
	}
	public LinkedList getProducts()
	{
		return new LinkedList(this.products);
	}
	public boolean isInterestedIn(Product product)
	{
		return this.products.contains(product);
	}
	public boolean addProduct(Product product)
	{
		if(this.products.contains(product))
		{
			return false;
		}
		this.products.add(product);
		return true;
	}
	private double calculateTotalPriceOfProducts()
	{
		if(this.products.isEmpty())
		{
			return 0;
		}
		double totalPrice = 0; 
		for(int i=0; i< this.products.size(); i++)
		{
			Product temp = (Product)this.products.get(i);
			totalPrice = totalPrice+temp.getProductPrice();
			//System.out.println(totalPrice);
		}
		return totalPrice;
	}
	public String toString()
	{
		String ans = "Client: ";
		ans = ans + this.firstName + " "+ this.lastName + ", " + this.id + ", Products\n" + this.products; 
		return ans;
	}
	public boolean equals(Object other)
	{
		if(other instanceof Client)
		{
			if(this.id == ((Client)other).getId())
			{
				return true;
			}
		}
		return false;
	}
	public double computeFinalProductsPrice()
	{
		return calculateTotalPriceOfProducts();
	}
	public double computeFinalShippingPrice()
	{
		if(this.products.isEmpty())
		{
			return 0;
		}
		double totalPrice = 0; 
		for(int i=0; i< this.products.size(); i++)
		{
			Product temp = (Product)this.products.get(i);
			totalPrice = totalPrice+temp.computeFinalPrice()[1];
		}
		return totalPrice;
	}
	public double computeFinalOrderPrice()
	{
		return (computeFinalProductsPrice() + computeFinalShippingPrice());
	}
}
