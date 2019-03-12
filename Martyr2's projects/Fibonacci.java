import java.util.Scanner;

public class Fibonacci
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.print("Type a number: ");
		int n = in.nextInt();
		System.out.println("\n0\n1");
		long[] num = new long[n];
		num[0] = 0;
		num[1] = 1;
		for (int i = 2; i < n; i++)
		{
			num[i] = num[i - 1] + num[i - 2];
			System.out.println(num[i]);
		}
		in.close();
	}
}
