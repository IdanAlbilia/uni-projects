/* Author: Idan Albilia 311375612!
*  This program gets a number from the user and checks if its a perfect number
*/
import java.util.Scanner;


public class Ex5 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a natural number:");

		int number;
		number=sc.nextInt();
		int checker=0;
		boolean perfect=true;
		for (int j=1; j<=number/2;j++)
		{
			if (number%j==0){
				checker = checker+j;
			}
		}
		if (checker==number)
			System.out.println(perfect);
		else {
			perfect=false;
			System.out.println(perfect);
		}
		sc.close();
	}
}
