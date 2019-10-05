import java.util.Scanner;


public class E {

	public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
		System.out.println("Enter an integer number:");

		
		
		for (int m=2;m<=7;m++){
			boolean isPrime=true;
			
			for (int i=2; i*i<=m;i++){
				if(m%i==0){
					isPrime=false;
			}
			}
			if(isPrime){
				System.out.println(m);
			}
		}
		sc.close();
			}
			
		
	}
