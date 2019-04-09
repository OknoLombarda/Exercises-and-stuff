import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.PrintWriter;

public class BookManager
{
	public static void main(String[] args) throws IOException
	{
		while (true)
			mainMenu();
	}
	
	public static void mainMenu() throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		clearScreen();
		System.out.println();
		System.out.println("1 - Change string\n2 - Manage books\n3 - Backup files\n4 - Load files from backup\n5 - Exit\n");
		String inp = in.readLine();
		int input = Integer.valueOf(inp);
		if (input == 5)
			System.exit(0);
		
		if (input == 3)
			BMTools.backupFiles();
		
		if (input == 4)
			BMTools.loadFromBackup();
		
		if (input == 1)
		{
			
			System.out.printf("\nString to be replaced: ");
			String oldString = in.readLine();
			System.out.print("New string: ");
			String newString = in.readLine();
			BMTools.replace(oldString, newString);
		}
		if (input == 2)
		{
			BMTools.loadSave();
			int saveIndex = BMTools.getSaveValue() - 1;
			for (int i = BMTools.getSaveValue(); i < BMTools.amount(); i++)
			{
				saveIndex++;
				clearScreen();
				BMTools.getText(i);
				System.out.print("\nDelete this book? ");
				String ans = in.readLine();
				if (ans.toLowerCase().equals("y"))
				{
					BMTools.delete(i);
					saveIndex--;
				}
				if (ans.toLowerCase().equals("n"))
				{
					continue;
				}
				if (ans.toLowerCase().equals("q"))
				{
					PrintWriter save = new PrintWriter("/home/oknolombarda/eclipse-workspace/BookManager/save.txt");
					save.print(saveIndex);
					save.close();
					break;
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