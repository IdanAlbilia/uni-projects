/* Author: Idan Albilia 311375612!
*  This program recieve 2 integers, height and width of a rectangle,
*  and prints the rectangle's area and perimeter.
*/
import java.util.Scanner;

public class Ex2 {
	public static void main(String[] args){
		// This line helps us getting input from the user
		Scanner sc = new Scanner(System.in);
		
		// Get height input from the user (integer)
		System.out.print("Enter height: ");
		int height = sc.nextInt();
		
		// Get width input from the user (integer)
		System.out.print("Enter width: ");
		int width = sc.nextInt();
		
		int area = height*width; // To complete
		int perimeter = (height+width)*2; // To complete
		
		
		System.out.println(area); // Complete printing the area (number only!) 
		System.out.println(perimeter); // Complete printing the perimeter (number only!)
	}
}
