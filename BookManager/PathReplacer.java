import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

class PathReplacer
{
	private static File ExpBooks = new File("/home/oknolombarda/git/Exercises-and-stuff/BookManager/bin/BookExport");
	private static File[] list = ExpBooks.listFiles();
	private static int startFrom;
	
	static
	{
		Scanner sF = new Scanner("/home/oknolombarda/git/Exercises-and-stuff/BookManager/bin/save.txt");
		String temp = sF.next();
		//Работает неправильно
		startFrom = (int)temp.charAt(0);
		sF.close();
	}
	
	public static int getSaveValue()
	{
		return startFrom;
	}
	
	public static void replace(String newPath) throws IOException
	{
		for(int i = 0; i < list.length; i++) {
			Scanner in = new Scanner(Paths.get(list[i].getAbsolutePath()), "UTF-8");
			String check;
			PrintWriter out = new PrintWriter("/home/oknolombarda/git/Exercises-and-stuff/BookManager/bin/replaced/" + list[i].getName(), "UTF-8");
			while(in.hasNextLine())
			{
				check = in.nextLine();
				if (check.contains("img://Textures/Interface/Books/Illuminated_Letters/"))
					check = check.replace("img://Textures/Interface/Books/Illuminated_Letters/", newPath);
				out.println(check);
			}
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