import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EnglishPracticing
{
	private static wordHolder wh = new wordHolder();
	
	public static void main(String[] args) throws IOException
	{
		while (true)
			mainMenu();
	}
	
	public static void mainMenu() throws IOException
	{
		clearScreen();
		System.out.printf("\n\nChoose an option:\n\n    1. Start practicing\n    2. Add a word\n"
				+ "    3. Search for a word\n    4. Exit\n\n:");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String input = in.readLine();
		if (input.equals("4"))
		{
			System.exit(0);
		}
		
		
		if (input.equals("1"))
		{
			clearScreen();
		}
		
		if (input.equals("2"))
		{
			boolean check = false;
			clearScreen();
			System.out.printf("\n\nType a word and its translation(s) or \"exit\" to go back to the main menu\n\n    Example:\n    word translation1 translation2\n\n:");
			do
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				ArrayList<String> inp = new ArrayList<>();
				String s = br.readLine();
				if (!s.toLowerCase().equals("exit"))
				{
					Scanner sc = new Scanner(s);
					while (sc.hasNext())
						inp.add(sc.next());
					String word = inp.get(0);
					inp.remove(0);
					String[] translations = new String[inp.size()];
					inp.toArray(translations);
					wh.addWord(word, translations);
					System.out.print("Add another word? (y/n): ");
					word = br.readLine();
					if (word.toLowerCase().equals("y"))
					{
						check = true;
						System.out.printf("\n:");
					}
					else
						check = false;
					sc.close();
				}
			} while (check);
			if (!wh.isEmpty())
				wh.printWords();
		}
		
		if (input.equals("3"))
		{
			clearScreen();
			System.out.printf("\nEnter the word or one of its translations\n\n:");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String word = br.readLine();
			int[] indexes = wh.findWord(word);
			clearScreen();
			if (indexes.length == 0)
			{
				System.out.printf("\nNo matches.\n\n");
				System.out.print("Press enter to return to the main menu.");
				br.readLine();
			}
			else
			{
				System.out.printf("\nSearch results:\n\n");
				for (int i = 0; i < indexes.length; i++)
				{
					String[] translations = wh.getTranslations(indexes[i]);
					String out = "      " + (i + 1) +". " + wh.getWord(indexes[i]) + " - ";
					for (int l = 0; l < translations.length; l++)
					{
						out += translations[l];
						if (l != translations.length - 1)
							out += ", ";
					}
					System.out.println(out);
				}
				System.out.printf("\nEnter a command and number of a word or \'exit\' to return to the main menu\n"
						+ "\n      Example: delete 1\n               editWord 1 pajama\n\nAvailable commands:\n\n      delete  -  delete word\n"
						+ "      editWord newWord  -  substitute word with newWord\n      "
						+ "editTranslations transl1 transl2 ...  -  substitute existing translations\n      "
						+ "addTranslations transl1 transl2 ...  -  add translations\n\n:");
				String line = br.readLine();
				Scanner cmdReader = new Scanner(line);
				ArrayList<String> tempcmd = new ArrayList<String>() {
					private static final long serialVersionUID = 1L;

					{
						while(cmdReader.hasNext()) { add(cmdReader.next()); } 
					}};
				
				cmdReader.close();
				String[] cmd = new String[tempcmd.size()];
				tempcmd.toArray(cmd);
				if (!cmd[0].equalsIgnoreCase("exit"))
				{
					int index = indexes[Integer.parseInt(cmd[1]) - 1];
					
					if (cmd[0].equalsIgnoreCase("delete"))
						wh.delWord(index);
					
					if (cmd.length >= 3)
					{
						if (cmd[0].equalsIgnoreCase("editWord"))
							wh.editWord(index, cmd[2]);
					
						ArrayList<String> transl = new ArrayList<>();
						for (int i = 2; i < cmd.length; i++)
							transl.add(cmd[i]);
						String[] translations = new String[transl.size()];
						transl.toArray(translations);
						
						if (cmd[0].equalsIgnoreCase("editTranslations"))
							wh.editTranslations(index, translations);
						
						if (cmd[0].equalsIgnoreCase("addTranslations"))
							wh.addTranslations(index, translations);
					}
					
					wh.printWords();
				}
			}
		}
	}
	
	public static void clearScreen()
	{
		System.out.printf("\033[H\033[2J");  
	    System.out.flush();  
	}
}