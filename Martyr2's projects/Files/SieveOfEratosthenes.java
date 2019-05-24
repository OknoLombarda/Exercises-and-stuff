import java.util.Arrays;
import java.util.Scanner;

public class SieveOfEratosthenes {
	public static void main(String[] args) {
		int limit = 0;
		try(Scanner in = new Scanner(System.in)) {
			System.out.print("Enter the limit: ");
			limit = in.nextInt();
		}
		
		boolean[] primes = new boolean[limit];
		Arrays.fill(primes, true);
		for (int i = 1; i < (int) Math.sqrt(limit); i++)
			if (primes[i])
				for (int j = (int) Math.pow(i + 1, 2) - 1; j < limit; j = j + i + 1)
					primes[j] = false;
		
		System.out.println("All prime numbers up to entered limit:");
		for (int i = 1; i < limit; i++)
			if (primes[i])
				System.out.println(i + 1);
	}
}
