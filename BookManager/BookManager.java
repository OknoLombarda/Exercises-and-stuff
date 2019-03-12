import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;

public class BookManager
{
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(System.in);
		System.out.println("1 - Change path\n2 - Manage books\n3 - Exit");
		int input = in.nextInt();
		if (input == 3)
			System.exit(0);
		
		if (input == 1)
		{
			System.out.print("New path: ");
			String path = in.next();
			PathReplacer.replace(path);
		}
		if (input == 2)
		{
			System.out.println("\n");
			for (int i = PathReplacer.getSaveValue(); i < PathReplacer.amount(); i++)
			{
				PathReplacer.getText(i);
				System.out.print("\nDelete this book? ");
				String ans = in.next();
				if (ans.toLowerCase().equals("y"))
				{
					//Возможно, работает неправильно
					PathReplacer.delete(i);
					i--;
				}
				if (ans.toLowerCase().equals("q"))
				{
					PrintWriter save = new PrintWriter("/home/oknolombarda/git/Exercises-and-stuff/BookManager/bin/save.txt\n");
					//Не работает, надо чем-то заменить
					save.print(i);
					save.close();
					System.exit(0);
				}
				System.out.println("\n\n\n\n");
			}
		}
		in.close();
	}
}