/**
 * 
 * @author 
 * 
 */
public class ImprovedStringHashFunction implements StringHashFunction {

	@Override
	public int hash(String str) {
		int sum = 0;
		for(int i = 0; i<str.length(); i++){
			char chr = str.charAt(i);
			int value = (int) chr;
			value =  (value * (int)Math.pow(31,str.length()-i-1));
			sum = sum + value;
		}
		return sum;
	}
}
