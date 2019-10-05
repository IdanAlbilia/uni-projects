
public class TreeNode {
	//fields
	TreeNode left;
	TreeNode right;
	Object data;
	
	/** constructor
	 * @param data2 The object data of the node.
	 */
	public TreeNode(Object data2){
		this.data = data2;
		left = null;
		right = null;
	}
	
	/** constructor that receives also left and right children
	 * @param data2 The object data of the node.
	 * @param left2 The left child of the node
	 * @param right2 The right child of the node
	 */
	public TreeNode(Object data2 , TreeNode left2, TreeNode right2){
		this.data = data2;
		this.left = left2;
		this.right = right2;
	}
	
	/** returns the data of the node
	 * @return The object data of the node.
	 */
	public Object getData(){
		return this.data;
	}
	
	/** returns the left child of the node
	 * @return The TreeNode left child of the node.
	 */
	public TreeNode getLeft(){
		return this.left;
	}
	
	/** returns the right child of the node
	 * @return The TreeNode right child of the node.
	 */
	public TreeNode getRight(){
		return this.right;
	}
	
	/** returns if this node is a leaf or not
	 * @return true or false.
	 */
	public boolean isLeaf(){
		return (this.left == null) && (this.right == null);
	}
	
	


}
