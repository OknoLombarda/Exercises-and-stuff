import java.util.Scanner;

public class PrimeFact
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a number: ");
		int n = in.nextInt();
		int k = 1;
		int l = 0;
		System.out.println();
		for (int i = 1; i <= n; i++)
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
				System.out.println(i);
		}
		in.close();
	}
}
