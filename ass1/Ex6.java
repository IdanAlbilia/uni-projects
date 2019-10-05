/* Author: Idan Albilia 311375612!
*  This program gets a number and prints geometric sequences of 4 numbers and their ratio
*/
import java.util.Scanner;


public class Ex6 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter an integer larger than 0 (n > 0):");
	    
		int number;
		number=sc.nextInt();
		int ratio;
		int a2=1,a3,=1,a4=1;
		
		for(ratio=2;ratio*ratio*ratio<number;ratio++)
		{
			for(int a1=1;a1*ratio*ratio*ratio<number;a1++)
			{
				a2=a1*ratio;
				a3=a2*ratio;
				a4=a3*ratio;
				
				System.out.print(a1+","+a2+","+a3+","+a4+";"+ratio+" ");
				System.out.print(a1+" "+a2+"="+a1+"*"+ratio+" "+a3+"="+a2+"*"+ratio+" "+a4+"="+a3+"*"+ratio)
	        }
			
		}
		sc.close();

	}
}
