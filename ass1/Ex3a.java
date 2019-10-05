/* Author: ADD YOUR NAME!
*  This program (Fill...)
*/
import java.util.Scanner;


public class Ex3a {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a serie of integers (terminate the serie by entering 0):");

		int numberInTheSerie=1;
		int counter=0, finalCount=0;
		while(numberInTheSerie!=0){
			numberInTheSerie = sc.nextInt();
			if (numberInTheSerie%2==0){
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
