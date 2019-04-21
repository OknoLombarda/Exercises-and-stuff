import java.util.Scanner;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;

public class CountWords
{
	public static void main(String[] args) throws IOException
	{
		Scanner sc;
		if (args.length != 0)
			sc = new Scanner(Paths.get(args[0]), "UTF-8");
		else
		{
			Scanner in = new Scanner(System.in);
			System.out.print("Enter the path to a file or file name: ");
			String fname = in.next();
			sc = new Scanner(Paths.get(fname), "UTF-8");
			in.close();
		}

		String summary = "";
		StringBuilder sb = new StringBuilder();
		String line = "";
		ArrayList<Integer> sentences = new ArrayList<>();
		int words = 0;

		while (sc.hasNextLine())
		{
			line = sc.nextLine();
			sb.append(line);
		}
		sc.close();

		summary = sb.toString();
		for (int i = 0; i < summary.length(); i++)
                {
        		if (Character.isLetter(summary.charAt(i)))
  			{
        	        	while (i < summary.length() && (Character.isLetter(summary.charAt(i)) || summary.charAt(i) == '-'))
                	        	i++;
                                words++;
                                i--;
                        }

			if (i < summary.length() && isPoint(summary.charAt(i)))
			{
				sentences.add(i);
				while (i < summary.length() && isPoint(summary.charAt(i)))
					i++;
				i--;
			}
                }
		int[] sen = sentences.stream().mapToInt(Integer::intValue).toArray();
		if (sen.length > 3)
			summary = summary.substring(0, sen[0] + 1).trim() + "\n" + summary.substring(sen[(sen.length / 2) - 1] + 1, sen[sen.length / 2] + 1).trim()
				+ "\n" + summary.substring(sen[sen.length - 2] + 1).trim();
		System.out.println("Total amount of words: " + words);
		System.out.printf("\nSummary:\n\n%s\n", summary);
	}

	public static boolean isPoint(char c)
	{
		char[] points = { '.', '!', '?' };
		for (char p : points)
			if (c == p)
				return true;
		return false;
	}
}
