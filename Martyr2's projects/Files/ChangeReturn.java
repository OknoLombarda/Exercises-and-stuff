import java.util.Scanner;

public class ChangeReturn
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.print("Cost: ");
		double cost = in.nextDouble();
		System.out.print("Amount of money: ");
		double money = in.nextDouble();
		if (money < cost)
		{
			System.out.println("Not enough money");
			System.exit(0);
		}

		double change = money - cost;
		double coins = change - (int)change;
		
		int pennies = 0;
		int nickels = 0;
		int dimes = 0;
		int quarters = 0;

		while (coins >= 0.25)
		{
			coins -= 0.25;
			quarters++;
		}
		while (coins >= 0.10)
		{
			coins -= 0.10;
			dimes++;
		}
		while (coins >= 0.05)
		{
			coins -= 0.05;
			nickels++;
		}
		while (coins > 0)
		{
			coins -= 0.01;
			pennies++;
		}

		String result = "Change: ";
		if ((int)change > 0)
			result += (int)change + " dollars ";
		if (quarters > 0)
			result += quarters + " quarters ";
		if (dimes > 0)
			result += dimes + " dimes ";
		if (nickels > 0)
			result += nickels + " nickels ";
		if (pennies > 0)
			result += pennies + " pennies";
		
		System.out.println(result);
		in.close();
	}
}
