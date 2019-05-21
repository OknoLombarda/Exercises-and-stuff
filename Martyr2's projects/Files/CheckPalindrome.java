import java.util.Scanner;

public class CheckPalindrome
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a word: ");
		String input = in.next();
		StringBuilder sb = new StringBuilder();
                for (int i = input.length() - 1; i >= 0; i--)
                        sb.append(input.charAt(i));
		if (sb.toString().equals(input))
			System.out.println("This word is a palindrome");
		else
			System.out.println("This word is not a palindrome");
		in.close();	
	}
}
