/* Author: Idan Albilia 311375612!
*  This program gets from the user a serie of numbers and returns the third largest number of the serie and the number of times of it in the serie 
*/
import java.util.Scanner;


public class Ex2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a serie of integers (terminate the serie by entering 0):");
		
		int numberInTheSerie;
		while(numberInTheSerie!=0){
				numberInTheSerie = sc.nextInt();
			System.out.println(numberInTheSerie)
		}
		
		
	    
	

		sc.close();
	}
}
