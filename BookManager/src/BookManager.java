import java.io.IOException;
import java.util.Scanner;

public class BookManager
{
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(System.in);
		System.out.println("1 - Change path\n2 - Manage books\n3 - Exit");
		int input = in.nextInt();
		if (input == 1)
		{
			System.out.print("New path: ");
			String path = in.next();
			PathReplacer.replace(path);
		}
		in.close();
	}
}