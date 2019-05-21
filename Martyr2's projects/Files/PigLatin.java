import java.util.Scanner;

public class PigLatin
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a word: ");
		String input = in.next();
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < input.length() && isConsonant(input.charAt(i)))
			i++;
		if (i > 0)
			System.out.println(input.substring(i) + input.substring(0, i) + "ay");
		else
			System.out.println(input + "way");
		in.close();
	}

	public static boolean isConsonant(char c)
	{
		char[] consonants = { 'b', 'c', 'd', 'f', 'g', 'h', 'j','k','l','m','n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z' };
		for (char cons : consonants)
			if (Character.toLowerCase(c) == cons)
				return true;
		return false;
	}
}
