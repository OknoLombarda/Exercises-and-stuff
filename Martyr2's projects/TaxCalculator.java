import java.util.Scanner;

public class TaxCalculator {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a cost and either a country or state tax: ");
		
		String[] input = in.nextLine().trim().split("\\s+");
		double cost = -1;
		double percent = -1;
		
		if (input.length > 2)
			System.err.println("Wrong input");
		
		for (String s : input) {
			if (cost == -1 && s.matches("^[0-9]+.?[0-9]+$"))
				cost = Double.parseDouble(s);
			if (percent == -1) {
				if (s.equalsIgnoreCase("state"))
					percent = 0.05;
				else if (s.equalsIgnoreCase("country"))
					percent = 0.025;
			}
		}
		
		System.out.printf("Tax: %s\nTotal cost: %.2f", (percent == 0.05 ? "5%" : "2.5%"), cost * (1 + percent));
	}
}
