import java.util.Scanner;

public class FindPI
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.print("Amount of numbers: ");
		int n = in.nextInt();
		if (n > 15)
			n = 15;
		if (n < 0)
			n = 0;
		String format = "%." + n + "f%n";
		System.out.printf(format, Math.PI);
	}
}