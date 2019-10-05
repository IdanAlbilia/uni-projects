/**
 * 
 * @author 
 * 
 */
public class AccumulateStringHashFunction implements StringHashFunction {

	@Override
	public int hash(String str) {
		int sum = 0;
		for(int i = 0; i<str.length(); i++){
			char chr = str.charAt(i);
			sum = sum + (int) chr;
		}
		return sum;
	}
}
