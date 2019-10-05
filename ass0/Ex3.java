/* Author: Idan Albilia 311375612!
*  This program recieve one integer with two digits and prints "Equal digits"
*  if the two digits are equal and "Different digits" if not.
*/
import java.util.Scanner;

public class Ex3 {
	public static void main(String[] args){
		// This line helps us getting input from the user
		Scanner sc = new Scanner(System.in);
		
		// Get input from the user (integer)
		System.out.print("Enter a number, please: ");
		int inputNum = sc.nextInt();
		int singels = inputNum%10;
		int dozens = inputNum/10;
		// You might want to add few lines of commands here
		
		if(singels==dozens){ // To complete
			System.out.println("Equal digits");
	 	}
	 	else{
			System.out.println("Different digits");
	 	}
	}
}

