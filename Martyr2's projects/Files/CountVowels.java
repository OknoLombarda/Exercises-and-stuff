import java.util.Scanner;

public class CountVowels
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.println("Enter a string:");
		String input = in.nextLine();
		boolean start = false;
		int count = 0;
		for (int i = 0; i < input.length(); i++)
		{
			if (isVowel(input.charAt(i)))
			{
				if (!start)
				{
					System.out.println("Vowels found:");
					start = true;
				}
				count++;
				System.out.println(input.charAt(i) + " (" + count + ")");
			}
		}
		if (count != 0)
			System.out.println("Total amount of vowels: " + count);
		in.close();
	}

	public static boolean isVowel(char c)
	{
		char[] vowels = { 'a', 'e', 'i', 'o', 'u', 'y' };
		for (char v : vowels)
			if (Character.toLowerCase(c) == v)
				return true;
		return false;
	}
}
