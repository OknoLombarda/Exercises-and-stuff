import java.util.Scanner;

public class ReverseString
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a string: ");
		String input = in.nextLine();
		StringBuilder sb = new StringBuilder();
		for (int i = input.length() - 1; i >= 0; i--)
			sb.append(input.charAt(i));
		System.out.println("Reversed string: " + sb.toString());
		in.close();
	}
}
