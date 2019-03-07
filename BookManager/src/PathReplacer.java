import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

class PathReplacer
{
	public static void replace(String newPath) throws IOException
	{
		File ExpBooks = new File("/home/oknolombarda/git/Exercises-and-stuff/BookManager/bin/BookExport");
		File[] list = ExpBooks.listFiles();
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
}