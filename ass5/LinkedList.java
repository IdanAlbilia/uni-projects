/**
 * This class is List of links.
 *  @author Itai Dagan
 */
public class LinkedList implements List{
	// Fields
	private Link head, tail;
	private int size;


	// Constructors.
	/**
	 * Set the first and last Link of the List to be null
	 */
	public LinkedList (){
		head = null;
		tail = null;
	}
	//copy constructor
	/**
	 * Copy Constructor of the linked list 
	 * @param list A list to copy from
	 */
	public LinkedList (LinkedList list){
		if(list.head != null){
			this.head = new Link(list.head);
			Link pointer = this.head;
			while(pointer.getNext() != null){
				pointer = pointer.getNext();
			}
			this.tail = pointer;
		}
		this.size = list.size;
	}
	/**
	 * This functions get an object and it adds this object to the end of the list
	 */
	// Methods
	public void add(Object element){
		if(element == null)
		{
			throw new NullPointerException();
		}
		if(isEmpty())
		{
			Link newLink = new Link(element, null);
			head = newLink;
			tail = newLink;
		}
		else
		{

			Link newLink = new Link(element, null);
			tail.setNext(newLink);
			tail = newLink;
		}
		size++;
	}
	/**
	 * This functions get an index and object and it adds this object to specific index
	 */
	public void add(int index, Object element){
		if(element == null)
		{
			throw new NullPointerException();
		}
		if(index<0 || index>=size)
			throw new IndexOutOfBoundsException();
		if(index == 0){
			Link newLink = new Link(element, null);
			newLink.setNext(head);
			head = newLink;
		}
		else{
			int listIndex = 0;
			Link curr = head;
			while(listIndex+1 < index){
				curr = curr.getNext();
				listIndex++;
			}
			Link newLink = new Link(element, null);
			newLink.setNext(curr.getNext());
			curr.setNext(newLink);
		}
		size++;
	}
	/**
	 * This function check if the list is empty if does it returns true else returns false.
	 */
	public boolean isEmpty()
	{
		if(head == null)
		{
			return true;
		}
		return false;
	}
	/**
	 * Getter for specific link in the list
	 */
	public Object get(int index)
	{
		if(index<0 || index>=size)
			throw new IndexOutOfBoundsException();
		int listIndex = 0;
		Link curr = head;
		while(listIndex<index)
		{
			//goes through the list until the desired index has been found
			curr = curr.getNext();
			listIndex++;
		}
		return curr.getData();
	}
	/**
	 * returns the list size
	 */
	public int size()
	{
		return size;
	}
	/**
	 * This function goes through the list and checks if it already have this data in one of the link if does returns true else returns false.
	 */
	public boolean contains(Object element)
	{
		if(element == null)
		{
			throw new NullPointerException();
		}
		int listIndex = 0;
		Link curr = head;
		while(listIndex<size && curr!= null)
		{
			//checks each link if his data is the same as the given Object
			if(curr.getData().equals(element))
			{
				return true;
			}
			curr = curr.getNext();
			listIndex++;
		}
		return false;
	}
	/**
	 * Receives an index and an object and it sets the object to be in this index and returns the data that has been there before.
	 */
	public Object set(int index, Object element)
	{
		if(element == null)
		{
			throw new NullPointerException();
		}
		if(index<0 || index>=size)
			throw new IndexOutOfBoundsException();
		if(index == 0){
			return(this.head.setData(element));
		}
		else{
			int listIndex = 0;
			Link curr = head;
			//goes through the list until the desired index has been found
			while(listIndex < index){
				curr = curr.getNext();
				listIndex++;
			}
			//replace the data
			return(curr.setData(element));
		}
	}
	/**
	 * Returns a String of the links strings in the list by defined order
	 */
	@Override
	public String toString()
	{
		String ans = "";
		int listIndex = 0;
		Link curr = head;
		while(listIndex < size){
			if(listIndex < size-1)
			{
				ans = ans + curr.toString() + ", \n";
			}
			else
			{
				ans = ans + curr.toString();
			}
			curr = curr.getNext();
			listIndex++;
		}
		return ans;
	}
	/**
	 * This functions check if both LinkedList contains the exact same links if does returns true else false.
	 */
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof LinkedList)
		{
			//casting for the object received
			LinkedList compare = (LinkedList)other;
			//checks if they are in the same size first
			if(compare.size != this.size)
			{
				return false;
			}
			int listIndex = 1;
			Link currThis = this.head;
			Link currCompare = compare.head;
			if(!currThis.equals(currCompare))
			{
				return false;
			}
			//goes through the list and checks that each link in the list are equals
			while(listIndex < size){
				if(!currThis.equals(currCompare))
				{
					return false;
				}
				currCompare = currCompare.getNext();
				currThis = currThis.getNext();
				listIndex++;
			}
			return true;
		}
		return false;
	}
	/**
	 * This function gets a comparator and sort the list by the comparator settings.
	 * @param comp Comparator to sort the list by.
	 */
	public void sortBy(Comparator comp)
	{
		if(comp == null)
		{
			throw new NullPointerException();
		}
		// goes through the list like selection sort - finds the minimum value and the list and put it in the index place.
		Link minLink = this.head;
		for (int i = 0; i < size-1; i++){
			int minIndex = i;
			Link curr2 = minLink.getNext();
			for (int j = i+1; j < size; j++)
			{
				if (comp.compare(minLink.getData(),curr2.getData())>0)
				{
					//if minimum value has been found it keeps the index of him
					minIndex = j;
				}
				curr2 = curr2.getNext();
			}
			//replace the data of the current index to the data to min value index and swap between them
			Object temp = get(minIndex);
			this.set(minIndex, get(i));
			this.set(i, temp);
			minLink = minLink.getNext();
		}
	}

}
