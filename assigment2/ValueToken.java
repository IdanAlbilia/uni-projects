
public class ValueToken extends CalcToken {

	double valueToken;
	
	public ValueToken(double val) {

		valueToken = val;
	}


	public double getValue() {
		
		return valueToken;
		
	}

	public  String toString(){

		return Double.toString(valueToken);
	}


}
