import java.math.BigInteger;
import java.util.Scanner;

public class FactorialFinder {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a number: ");
		int num = in.nextInt();
		int result = 1;
		if (num > 0) {
			for (int i = 1; i <= num; i++) {
				result = result * i;
			}
		}
		System.out.println("The factorial of n is " + result);
		System.out.println("(via recursion: " + recursion(num) + ")");
		in.close();
	}

	public static int recursion(int num) {
		if (num == 0) {
			return 1;
		}
		if (num > 1) {
			return recursion(num - 1) * num;
		}
		return num;
	}
	
	public static BigInteger findFactorial(BigInteger num) {
		if (num.compareTo(BigInteger.ZERO) == 0)
			return BigInteger.ONE;
		if (num.compareTo(BigInteger.ONE) > 0) {
			return num.multiply(findFactorial(num.subtract(BigInteger.ONE)));
		}
		return num;
	}
}
