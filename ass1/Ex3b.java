/* Author: Idan Albilia 311375612!
*  This program gets from the user a serie of numbers and returns the number of the largest serie of ascending even numbers in the whole serie
*/
import java.util.Scanner;


public class Ex3b {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a serie of integers (terminate the serie by entering 0):");
		
		int numberInTheSerie=1;
		int counter=0, finalCount=0;
		int oldNumber=0;
		while(numberInTheSerie!=0){
			oldNumber=numberInTheSerie;
			numberInTheSerie = sc.nextInt();
			if (numberInTheSerie==0)
				break;
			if (numberInTheSerie%2==0&&numberInTheSerie>oldNumber){
				counter++;
				if (counter>finalCount){
				finalCount=counter;
				}
			}
				else{	
					if (counter>finalCount){
						finalCount=counter;
					}
					counter=0;
				}
		}		
		
		System.out.println(finalCount);

		
		sc.close();
	}
}
