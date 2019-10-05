/* Author: Idan Albilia 311375612!
*  This program gets an integer k>0 and prints the k prime number 
*/
import java.util.Scanner;


public class Ex1 {

	public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
		System.out.println("Enter an integer number:");

		int k = sc.nextInt();
		int counter = 1; // counts how many primes have been 
		int nextNumber=2;
		
		while (counter<=k){
			boolean isPrime=true;
			int divisor = 2;
			while (divisor*divisor<=nextNumber&isPrime){ // a loop that checks numbers ( if they are prime or not )
				if(nextNumber%divisor==0){
					isPrime=false; // if false the checked number isnt prime
					break; // stop the loop and exit it
			}
			divisor=divisor+1;
			}
			if(isPrime){ // if the number is prime add one to the counter
				counter=counter+1;
			}
			nextNumber=nextNumber+1;
		}
		System.out.println(nextNumber-1);
		sc.close();
			}
			
		
	}

