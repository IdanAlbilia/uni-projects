
public class TreeCalculator extends Calculator {

	TreeNode node;
	ExpressionTree root;


	public TreeCalculator( ExpressionTree root) {

		this.node = (TreeNode) root.getroot();

	}

	public TreeCalculator(){
	}

	public double evaluate(String expr) {

		
		
		
		ExpressionTree root =  new ExpressionTree(expr);
		this.node = (TreeNode)root.getroot();
		return evaluateExpression((TreeNode)root.getroot());


	}

	public double evaluateExpression(TreeNode node) {

		double result;

		if(node.data instanceof BinaryOp) {

			BinaryOp oper = (BinaryOp) node.getData();

			double left=  evaluateExpression(node.left);

			double right=  evaluateExpression(node.right);

			result = oper.operate(left, right);

		}else {

			ValueToken val = (ValueToken) node.data;
			result = val.getValue();

		}

		return result;

	}

	public String getInfix() {
		String str = Inorder(node,"");
		return str.substring(1, (str.length()-1));
	}

	public String getPostfix() {
		String str = postOrder(node,"");
		return str.substring(1);
	}

	public String getPrefix() {
		String str = preOrder(node,"");
		return str.substring(1, (str.length()));
	}

	public String Inorder(TreeNode node, String str){
		if(node!=null) {
			str = str+"(";
			str = Inorder(node.left,str);
			str = str + " " +node.getData().toString()+ " ";
			str = Inorder(node.right,str);
			str = str+")";
		}
		return str;
	}
	
	public String postOrder(TreeNode node, String str){
		if(node!=null) {
			str = postOrder(node.left,str);
			str = postOrder(node.right,str);
			str = str + " " +node.getData().toString();
		}
		return str;
	}
	
	public String preOrder(TreeNode node, String str){
		if(node!=null) {	
			str = str + " " +node.getData().toString();
			str = preOrder(node.left,str);					
			str = preOrder(node.right,str);		
		}
		return str;
	}
}
