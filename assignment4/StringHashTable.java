import java.util.LinkedList;

/**
 * 
 * @author 
 *
 */
public class StringHashTable {
	private int counter=0;
	private int tableSize;
	private StringHashFunction ash;
	private LinkedList<String>[] Linked_Arr;

	@SuppressWarnings("unchecked")
	public StringHashTable(int tableSize, StringHashFunction hashFunction) {
		this.Linked_Arr = new LinkedList[tableSize];
		this.tableSize = tableSize;
		this.ash = hashFunction;
	}

	/**
	 * 
	 * @return 
	 */
	public int numOfElementsInTable() {
		return counter;
	}

	public boolean insert(String str) {
		if(str == null)
			return false;
		int key = (Math.abs(ash.hash(str))%(tableSize));
		if(Linked_Arr[key] == null){
			LinkedList<String> exp = new LinkedList<String>();
			Linked_Arr[key] = exp;
		}
		if (search(str))
			return false;
		Linked_Arr[key].add(str);
		counter++;
		return true;
	}

	public boolean delete(String str) {
		if(str == null)
			return false;
		if (!search(str))
			return false;
		int key = Math.abs(ash.hash(str))%tableSize;
		Linked_Arr[key].remove(str);
		counter--;
		return true;
	}

	public boolean search(String str) {
		if(str == null)
			return false;
		int key = Math.abs(ash.hash(str))%tableSize;
		if(Linked_Arr[key]!=null && Linked_Arr[key].contains(str))
			return true;
		else 
			return false;
	}
}
