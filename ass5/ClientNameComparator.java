/**
 * Implements Comparator.
 * @author Itai Dagan
 *
 */
public class ClientNameComparator implements Comparator {
	/**
	 * This comparator is defined by String lexigraphic compare and first compare between the last name and if they are equal it compare between the last names
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
		// if the first last name is bigger the second retuns positive number
		if(firstClient.getLastName().compareTo(secondClient.getLastName())>0)
		{
			return 1;
		}
		// if the first last name is lower the second retuns positive number
		else if(firstClient.getLastName().compareTo(secondClient.getLastName())<0)
		{
			return -1;
		}
		// if the first client first name is bigger the second retuns positive number
		else if(firstClient.getFirstName().compareTo(secondClient.getFirstName())>0)
		{
			return 1;
		}
		// if the first client first name is lower the second retuns positive number
		else if (firstClient.getFirstName().compareTo(secondClient.getFirstName())<0)
		{
			return -1;
		}
		//both of them equal return 0
		else
		{
			return 0;
		}
	}

}
