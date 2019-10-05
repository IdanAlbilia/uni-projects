/* Author: Idan Albilia 311375612!
*  This program gets a number from the user and prints true if the number is a power of 2 and false if it is not. 
*/
import java.util.Scanner;


public class Ex4 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter an integer larger than 0 (n > 0):");
		
		int numberToCheck = sc.nextInt();
		boolean num=false;
		
		while(numberToCheck!=1){
			numberToCheck/2=numberToCheck;
			
			if(numberToCheck==0){
				num=true;
				break;
			}
			
			
		}
		System.out.println(num);
		sc.close();
	}
}
