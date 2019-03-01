import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

public class PathReplacer
{
	public static void main(String[] args) throws IOException
	{
		File ExpBooks = new File("C:\\Users\\acer\\Documents\\eclipse\\PathReplacer\\src\\BookExport");
		File[] list = ExpBooks.listFiles();
		for(int i = 0; i < list.length; i++) {
			Scanner in = new Scanner(Paths.get(list[i].getAbsolutePath()), "UTF-8");
			String check;
			PrintWriter out = new PrintWriter("C:\\Users\\acer\\Documents\\eclipse\\PathReplacer\\src\\Replaced\\" + list[i].getName(), "UTF-8");
			while(in.hasNextLine())
			{
				check = in.nextLine();
				if (check.contains("img://Textures/Interface/Books/Illuminated_Letters/"))
					check = check.replace("img://Textures/Interface/Books/Illuminated_Letters/", "folder/");
				out.println(check);
			}
			in.close();
			out.close();
		}
	}
}