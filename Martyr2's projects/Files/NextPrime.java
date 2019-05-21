import java.util.Scanner;

public class NextPrime
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		String ans = "";
		int k = 1;
		int l = 0;
		int i = 1;
		int nextPrime = 0;
		boolean isPrime = false;
		do
		{
			isPrime = false;
			do
			{
				k = 1;
				l = 0;
				while (k <= i)
				{
					if (i % k == 0)
						l++;
					k++;	
				}
				if (l == 2)
				{
					nextPrime = i;
					isPrime = true;
				}
				i++;
			} while (isPrime == false);
			System.out.print(nextPrime + "\n\nPrint next prime? ");
			ans = in.next();
		} while (ans.toLowerCase().equals("y"));
		in.close();
	}
}
