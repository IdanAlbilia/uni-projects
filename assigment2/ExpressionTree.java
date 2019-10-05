
public class ExpressionTree {
	
	TreeNode root;
	
	public ExpressionTree (String postfixExp) {
				
		BuildExpressionTree(postfixExp);
	}	
	
	public Object getroot() {
		
		return root;
	}

	
	public void BuildExpressionTree (String postfixExp) {
		
		
		StackAsArray stack =  new StackAsArray();
		ExpTokenizer Tokenizer = new ExpTokenizer(postfixExp);

		
		for ( int i=0 ; i<postfixExp.length()&& Tokenizer.hasMoreElements();i++) {
			
			CalcToken token = (CalcToken) Tokenizer.nextElement();
			
			if (token instanceof ValueToken) {
				
				TreeNode nodeVal = new TreeNode(token);
						
				stack.push(nodeVal);
				
			}else if (token instanceof BinaryOp) {
				
				Object right = stack.pop();
				Object left = stack.pop();
				
				TreeNode nodeOp = new TreeNode(token,(TreeNode)left,(TreeNode)right);
				
				stack.push(nodeOp);
				
			}
			
			
		}
		
		this.root = (TreeNode) stack.pop();
		
	}
}

	
