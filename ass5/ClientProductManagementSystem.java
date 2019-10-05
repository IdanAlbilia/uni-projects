/**
 * This class represents the manager system of the clients and products.
 * @author Itai Dagan
 *
 */
public class ClientProductManagementSystem {
	private LinkedList products;
	private LinkedList clients;
	/**
	 * default constructor of the system the creates new empty lists of products and clients
	 */
	public ClientProductManagementSystem()
	{
		this.products = new LinkedList();
		this.clients = new LinkedList();
	}
	/**
	 * This function adds a new client if he does not exist yet in the system if it managed to add returns true else returns false
	 * @param client To add to the system
	 * @return if it managed to add returns true else returns false
	 */
	public boolean addClient(Client client)
	{
		// checks if the client exists
		if(this.clients.contains(client))
		{
			return false;
		}
		this.clients.add(client);
		return true;
	}
	/**
	 * This function adds a new product if it does not exist yet in the system if it managed to add returns true else returns false
	 * @param product To add to the system
	 * @return if it managed to add returns true else returns false
	 */
	public boolean addProduct(Product product)
	{
		// checks if the product exists
		if(this.products.contains(product))
		{
			return false;
		}
		this.products.add(product);
		return true;
	}
	/**
	 * This function adds product to client if both of them exist in the system and if the client doesn't have this product yet.
	 * @param client add the product to this client 
	 * @param product to add to the the client 
	 * @return if it managed to add returns true else returns false
	 */
	public boolean addProductToClient(Client client, Product product)
	{
		if(this.products.contains(product) && this.clients.contains(client))
		{
			//if both of them exist it check if the client doesnt have this product yet and if not it adds the product to the client
			LinkedList clientProducts = client.getProducts();
			if(!clientProducts.contains(product))
			{
				//returns the result of the add function
				return client.addProduct(product);
			}
		}
		return false;
	}
	/**
	 * This functions returns a new list sorted by given comparator and number of elements in the list 
	 * @param comp to sort by the list
	 * @param k number of elements 
	 * @return The list in k size
	 */
	public LinkedList getFirstKClients(Comparator comp, int k)
	{
		if(comp == null || k<=0)
		{
			throw new IllegalArgumentException();
		}
		// create new list and sort it by the given order 
		LinkedList firstKClients = new LinkedList(this.clients);
		firstKClients.sortBy(comp);
		// new list to be returned 
		LinkedList firstKClientsNew = new LinkedList();
		for(int i=0; i<k; i++)
		{
			//adds all the clients from the sorted list to the returned list until the number of desired elemnts
			firstKClientsNew.add(firstKClients.get(i));
		}
		return firstKClientsNew;
	}
	/**
	 * Getter of the number of clients in the system 
	 * @return the number of clients
	 */
	public int getNumberOfClients()
	{
		return this.clients.size();
	}
	/**
	 *  Getter of the number of products in the system 
	 * @return the number of products in the system 
	 */
	public int getNumberOfProducts()
	{
		return this.products.size();
	}
	/**
	 * This function Comupte the order price of specific client if he exists in the system 
	 * @param client to compute his order price
	 * @return the order price of the client, default will return 0 even if the client doesnt exist
	 */
	public double computeFinalOrderPrice(Client client)
	{
		if(client == null)
		{
			throw new NullPointerException();
		}
		if(this.clients.contains(client))
		{
			//if the client exist it reutrn his order price
			return client.computeFinalOrderPrice();
		}
		return 0;
	}

}
