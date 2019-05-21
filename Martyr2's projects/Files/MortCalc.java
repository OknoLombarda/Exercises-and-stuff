import java.util.Scanner;
import java.text.NumberFormat;

/*  
    I don't really know how that mortgage system works
    and google didn't help me with that so I just made this
    program basing on my insufficient knowledge
*/

public class MortCalc
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.print("Borrowed sum: ");
		int bsum = in.nextInt();
		System.out.print("Term: ");
		int term = in.nextInt();
		System.out.print("Interest rate: ");
		double interestRate = in.nextDouble();
		System.out.println("You will have to pay " + NumberFormat.getCurrencyInstance().format((double)bsum / (double)term * (100 + interestRate) / 100) + " a month");
		in.close();
	}
}
