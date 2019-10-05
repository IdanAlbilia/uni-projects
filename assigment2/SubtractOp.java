
public class SubtractOp extends BinaryOp {

	public double operate(double left, double right) {
		
		return left - right;

	}


	public double getPrecedence() {

		return 1.0;

	}

	public  String toString(){

		return "-";
	}
}
