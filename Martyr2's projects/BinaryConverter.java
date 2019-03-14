import java.util.Scanner;

public class BinaryConverter
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.println("1 - Binary to decimal\n2 - Decimal to binary");
		int ans = in.nextInt();
		
		if (ans == 1)
		{
			int result = 0;
			System.out.print("\nEnter a binary number: ");
			int bin = in.nextInt();
			String bin1 = String.valueOf(bin);
			int len = bin1.length() - 1;
			for (int i = 0; i < bin1.length(); i++)
			{
				result += Character.getNumericValue(bin1.charAt(len)) * Math.pow(2, i);
				len--;
			}
			System.out.println("Result: " + result);
		}

		if (ans == 2)
		{
			String result = "";
			System.out.print("Enter a decimal number: ");
			int dec = in.nextInt();
			while (dec > 1)
			{
				result = dec % 2 + result;
				dec = dec / 2;
			}
			System.out.println("Result: 1" + result);
		}
		in.close();
	}
}
