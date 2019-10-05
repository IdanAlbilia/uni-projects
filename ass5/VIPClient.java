/**
 * This class extend the client class.
 * @author Itai Dagan 
 *
 */
public class VIPClient extends Client{

	// Constructors
	/**
	 * Construcor for vip client
	 * @param firstName
	 * @param lastName
	 * @param id
	 */
	public VIPClient(String firstName, String lastName, int id) {
		//use the super construcor with the same arguments
		super(firstName, lastName, id);
	}
	/**
	 * Copy Construcor for vip client
	 * @param data
	 */
	public VIPClient(Object data) {
		//use the super construcor with the same arguments
		super(data);
	}

	// Methods
	/**
	 * This function overrides the super method and calculate the shipping rate with discount of 50%
	 */
	@Override
	public double computeFinalShippingPrice()
	{
		return Math.floor(super.computeFinalShippingPrice()*0.5);
	}
}
