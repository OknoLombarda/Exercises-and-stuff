import java.util.Scanner;

public class CollatzConjecture {
	public static void main(String[] args) {
		System.out.print("Enter a number greater than one: ");
		int input = 0;
		try (Scanner in = new Scanner(System.in)) {
			input = in.nextInt();
		}
		if (input <= 1) {
			System.err.println("The entered number is equal or less than 1");
			System.exit(0);
		}
		
		int result = input;
		int count = 0;
		while (result != 1) {
			if (result % 2 == 0)
				result /= 2;
			else
				result = result * 3 + 1;
			
			count++;
		}
		
		System.out.println("It took " + count + " steps to get to 1 from " + input);
	}
}
