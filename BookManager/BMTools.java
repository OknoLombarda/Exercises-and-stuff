import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;

class BMTools
{
	private static File ExpBooks = new File("/home/oknolombarda/eclipse-workspace/BookManager/BookExport");
	private static File[] list = ExpBooks.listFiles();
	private static File backup = new File("/home/oknolombarda/eclipse-workspace/BookManager/backup");
	private static File[] bpFiles = backup.listFiles();
	private static int startFrom;
	
	public static void loadSave() throws IOException
	{
		Scanner sF = new Scanner(Paths.get("/home/oknolombarda/eclipse-workspace/BookManager/save.txt"), "UTF-8");
		String temp = sF.next();
		startFrom = Integer.valueOf(temp);
		sF.close();
	}
	
	public static int getSaveValue()
	{
		return startFrom;
	}
	
	public static void backupFiles() throws IOException
	{
		for (File f : bpFiles)
			f.delete();
		for (File f : list)
			Files.copy(Paths.get(f.getAbsolutePath()), Paths.get("/home/oknolombarda/eclipse-workspace/BookManager/backup/" + f.getName()));
	}
	
	public static void loadFromBackup() throws IOException
	{
		for (File f : list)
			f.delete();
		for (File f : bpFiles)
			Files.copy(Paths.get(f.getAbsolutePath()), Paths.get("/home/oknolombarda/eclipse-workspace/BookManager/BookExport/" + f.getName()));
	}
	
	public static void replace(String oldString, String newString) throws IOException
	{
		for(int i = 0; i < list.length; i++) {
			Scanner in = new Scanner(Paths.get(list[i].getAbsolutePath()), "UTF-8");
			ArrayList<String> tempBook = new ArrayList<>();
			String check;
			while(in.hasNextLine())
			{
				check = in.nextLine();
				if (check.contains(oldString))
					check = check.replace(oldString, newString);
				tempBook.add(check);
			}
			String[] book = new String[tempBook.size()];
			tempBook.toArray(book);
			PrintWriter out = new PrintWriter(new FileWriter("/home/oknolombarda/eclipse-workspace/BookManager/BookExport/" + list[i].getName(), false));
			for (String s : book)
				out.println(s);
			in.close();
			out.close();
		}
	}
	
	public static int amount()
	{
		return list.length;
	}
	
	static void getText(int num) throws IOException
	{
		Scanner text = new Scanner(Paths.get(list[num].getAbsolutePath()), "UTF-8");
		String line;
		while(text.hasNextLine())
		{
			line = text.nextLine();
			System.out.println(line);
		}
		text.close();
	}
	
	public static void delete(int num)
	{
		list[num].delete();
	}
}