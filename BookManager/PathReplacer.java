import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

class PathReplacer
{
	private static File ExpBooks = new File("/home/oknolombarda/eclipse-workspace/BookManager/BookExport");
	private static File[] list = ExpBooks.listFiles();
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
	
	public static void replace(String oldString, String newString) throws IOException
	{
		for(int i = 0; i < list.length; i++) {
			Scanner in = new Scanner(Paths.get(list[i].getAbsolutePath()), "UTF-8");
			String check;
			PrintWriter out = new PrintWriter("/home/oknolombarda/eclipse-workspace/BookManager/replaced/" + list[i].getName(), "UTF-8");
			while(in.hasNextLine())
			{
				check = in.nextLine();
				if (check.contains(oldString))
					check = check.replace(oldString, newString);
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