/**
 * This class stands for a Link that will be used in LinkedList and holds an Object and pointer to the next Link.
 *  @author Itai Dagan
 */
public class Link{
	// Fields
	private Object data;
	private Link next;


	// Constructors
	/**
	 * Constructor that get an object and create a new link that will point to null link.
	 * @param data
	 */
	public Link(Object data){
		this(data, null);
	}
	/**
	 * Constructor that get an object and create a new link that will point to the Link that he gets.
	 * @param data an object that the link will hold
	 * @param next pointer for the next Link
	 */
	public Link(Object data, Link next)	{
		this.data = data;
		this.next = next;
	}
	/**
	 * Copy Constructor for Link, gets a Link and create a new link with the same data and pointer.
	 * @param link Link to copy from
	 */
	public Link(Link link)	{
		//checks the Instance of this link's data and create a new instance of this data
		if(link.getData() instanceof VIPClient)
			this.data = new VIPClient(link.getData());
		else if(link.getData() instanceof Client)
			this.data = new Client(link.getData());
		else if(link.getData() instanceof ProductInStore)
			this.data = new ProductInStore(link.getData());
		else if(link.getData() instanceof ProductInStorageSmall)
			this.data = new ProductInStorageSmall(link.getData());
		else if(link.getData() instanceof ProductInStorageMedium)
			this.data = new ProductInStorageMedium(link.getData());
		else this.data = new ProductInStorageLarge(link.getData());
		if(link.getNext() != null){
			this.next = new Link (link.getNext());
		}
		else this.next = null;
	}


	// Methods
	/**
	 * Getter for the data that this Link holds
	 * @return the data that this Link holds
	 */
	public Object getData(){
		return data;
	}
	/**
	 * Getter for the pointer to the next link that this Link holds
	 * @return the pointer to the next link that this Link holds
	 */
	public Link getNext() {
		return next;
	}
	/**
	 * Setter for the pointer to the next link that this Link will hold
	 * @param next the pointer to the next link that this Link will hold
	 */
	public void setNext(Link next) {
		this.next = next;
	}
	/**
	 * Setter for the data that this Link will hold, it returns the previous data that he had
	 * @param data new data for this link
	 * @return the previous data that he had
	 */
	public Object setData(Object data) {
		Object res = this.data;
		this.data = data;
		return res;
	}
	/**
	 * Returns a String of the data
	 */
	@Override
	public String toString(){
		return data.toString();
	}
	/**
	 * Checks if 2 Links have the same data if does returns true else false.
	 */
	@Override
	public boolean equals(Object other) {
		if(other instanceof Link)
		{
			if(this.data.equals(((Link)other).data))
			{
				return true;
			}
		}
		// default return value
		return false;
	}
}
