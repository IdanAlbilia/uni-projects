// Author: Itai Dagan 

public class Warmup {

	public static void main(String[] args) {

		//System.out.println(countNumberOfEvenDigits(3254));
		// Add code to test your functions
		/*for(int i=0; i<10; i++)
		{
			System.out.println(i + " " + doesDigitAppearInNumber(1, i));
		}*/
		/*System.out.println(countNumberOfEvenDigits(22556677));
		System.out.println(countTheAmountOfCharInString("sisi", 's'));
		System.out.println(checkIfAllLettersAreCapitalOrSmall(""));*/
		//System.out.println(checkIfAllLettersAreCapitalOrSmall(""));
	}

	// **************   Task1   **************
	public static boolean doesDigitAppearInNumber(int number, int digit) {
		//this funstion gets number and digit.
		// the function returns true if the digit appears in the number and false if not.
		if(number%10 == digit){
			//checking if the right digit is equal to the given digit, if does returns true.
			return true;
		}
		else if(number < 10)
		{
			//if the number is smaller than 10 it means there were no digits in the number like the given digit.
			return false;
		}
		else
		{
			// the function calls the same number divided by 10 and the given digit.
			return doesDigitAppearInNumber(number/10, digit);
		}
	}

	// ************** Task2 **************
	public static int countNumberOfEvenDigits(int number) {
		//this function calls another function with the right digit and the number divided by 10.
		//the function returns the answer from the other function
		int ans = 0;
		ans = ans + countNumberOfEvenDigits(number/10,number%10);
		return ans;
	}
	public static int countNumberOfEvenDigits(int number,int digit) {
		//this funstion gets number and digit.
		// the function sums all the digits that are even and returns the sum of them.
		int ans =(digit+1)%2; // if the number is even the result will be 1 else it will be 0
		if(number>0)
		{
			// as long as the number is bigger than 0 it calls the function again with the right digit and the number divided by 10.
			ans = ans + countNumberOfEvenDigits(number/10,number%10);
		}
		return ans;
	}


	// ************** Task3 **************
	public static int countTheAmountOfCharInString(String str, char c) {
		//the function gets string and char and returns how many times the char exists in the string.
		int ans=0;
		if(str.length() == 0)
		{
			//this is the breaking point of the function when the length is 0
			return ans;
		}
		else if(str.charAt(0) == c)
		{
			//adds 1 to the sum and call the function again without the left char of the string.
			ans = 1;
			ans = ans + countTheAmountOfCharInString(str.substring(1), c);
		}
		else
		{
			//call the function again without the left char of the string.
			ans = ans + countTheAmountOfCharInString(str.substring(1), c);
		}
		return ans;
	}

	// ************** Task4 **************
	public static boolean checkIfAllLettersAreCapitalOrSmall(String str) {
		//the function gets string and returns true if all the chars are captial or small letter. false if not.
		if(str.length() > 1)
		{
			//as long the string length is bigger than 1 it checks 2 chars in location '0' and '1' to see if both of them are between the capital or the small letters and if does it calls the function again without the left char of the string.
			System.out.println(str);
			if((str.charAt(0)>= 'A' && str.charAt(0)<= 'Z') && (str.charAt(1)>= 'A' && str.charAt(1)<= 'Z') || (str.charAt(0)>= 'a' && str.charAt(0)<= 'z') && (str.charAt(1)>= 'a' && str.charAt(1)<= 'z'))
			{
				return checkIfAllLettersAreCapitalOrSmall(str.substring(1));
			}
			else
			{
				return false;
			}
		}
		else
		{
			return true;
		}
	}

}
