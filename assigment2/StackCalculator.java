
public class StackCalculator extends Calculator {

	public StackCalculator(){
	}
	
	public double evaluate(String expr) {

		StackAsArray stack =  new StackAsArray();
		ExpTokenizer Tokenizer = new ExpTokenizer(expr);

		for (int i =0; i <expr.length() && Tokenizer.hasMoreElements(); i++) {

			CalcToken token = (CalcToken) Tokenizer.nextElement();

			if(token instanceof BinaryOp) {
				CalcToken op1 = (CalcToken)stack.pop();
				CalcToken op2 = (CalcToken)stack.pop();

				ValueToken op1val = (ValueToken)op1;
				ValueToken op2val = (ValueToken)op2;

				double result = ((BinaryOp)token).operate(op2val.getValue(), op1val.getValue());
				ValueToken tok = new ValueToken(result);
				stack.push(tok);

			}else {

				stack.push(token);

			}

		}

		CalcToken last = (CalcToken) stack.pop();
		ValueToken val = ( ValueToken) last;

		return val.getValue();

	}



	public String infixToPostfix(String infix) {

		int count=0;
		String Postfix = new String("");
		StackAsArray stack =  new StackAsArray();
		ExpTokenizer Tokenizer = new ExpTokenizer(infix);

		for(int i=0; i< infix.length()&& Tokenizer.hasMoreElements(); i++) {
			CalcToken token = (CalcToken) Tokenizer.nextElement();

			if(token instanceof LeftBracket) {
				stack.push(token);
				count=0;	
			}

			else if (token instanceof RightBracket) {
				count=0;	
				boolean OpenBracket = false;

				while(!stack.isEmpty()&&OpenBracket == false) {

					Object valuetoken = stack.pop();

					if(valuetoken instanceof LeftBracket) {
						OpenBracket= true;
						
					}else {

						Postfix = Postfix +" " + valuetoken.toString();
					}
					
				}

			}else if(token instanceof BinaryOp){
				count=0;
				boolean precedance = false;

				double precedanceNum = ((BinaryOp)token).getPrecedence();

				if(!stack.isEmpty()) {

					while(!stack.isEmpty()&&precedance == false ) {

						Object Opertortoken = stack.pop();

						if (!(Opertortoken instanceof LeftBracket)){

							if(Opertortoken instanceof BinaryOp) {

								if((( BinaryOp)token).getPrecedence()<= precedanceNum){
									precedance = true;
									stack.push(Opertortoken);
									stack.push(token);

								}else {
									Postfix = Postfix + " " + Opertortoken.toString();

								}

							}else {

								Postfix = Postfix + " " + Opertortoken.toString();

							}

						}else {

							precedance = true;
							stack.push(Opertortoken);
							stack.push(token);	
						}

					}

				}else {
					stack.push(token);

				}


			}

			else {

				if(count !=0) {
					Postfix = Postfix + token.toString();

				}else {

					if(Postfix.length() ==0) {

						Postfix = Postfix + token.toString();

					}else {
						Postfix = Postfix + " " + token.toString();

					}

				}

				count++;
			}
		}

		while (!stack.isEmpty()) {

			Object element = stack.pop();

			Postfix = Postfix + " " +  element.toString();
		}

		return Postfix;


	}

}

