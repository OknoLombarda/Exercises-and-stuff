import java.util.Scanner;

public class CardValidator
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a card number: ");
		String numbers = in.nextLine();
		numbers = numbers.replace(" ", "");
		int[] number = new int[numbers.length()];
		for (int i = 0; i < numbers.length(); i++)
		{
			number[i] = Character.getNumericValue(numbers.charAt(i));
		}
		int i = 0;
		if (number.length % 2 != 0)
			i = 1;
		while (i < number.length)
		{
			number[i] = 2 * number[i];
			if (number[i] > 9)
				number[i] -= 9;
			i += 2;	
		}
		int sum = 0;
		for (int j : number)
			sum += j;
		if (sum % 10 == 0)
			System.out.println("Card number is valid");
		else
			System.out.println("Card number is invalid");
		in.close();
	}
}
