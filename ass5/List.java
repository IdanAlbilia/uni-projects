/**
 * Interface of list
 * @author Itai Dagan
 *
 */
public interface List {

	// Methods

	public void add(Object element);

	public void add(int index, Object element);

	public int size();

	public boolean contains(Object element);

	public boolean isEmpty();

	public Object get(int index);

	public Object set(int index, Object element);

}
