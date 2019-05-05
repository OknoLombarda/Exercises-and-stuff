import java.util.Scanner;

public class UnitConverter {
	public static void main(String[] args) {
		System.out.printf("\n1. Temperature\n2. Currency\n3. Volume\n4. Mass\n5. Exit\n\n");
		Scanner in = new Scanner(System.in);
		int ans = in.nextInt();
		
		if (ans == 5)
			System.exit(0);
		
		else if (ans == 1) {
			int[] ft = fromTo(temperature);
			System.out.print("Enter the value: ");
			double value = in.nextDouble();
			if (ft[0] == 0)
				value += 273.15;
			if (ft[0] == 2)
				value = (value - 32.0) * (5.0 / 9.0) + 273.15;
			
			if (ft[1] == 0)
				value -= 273.15;
			if (ft[1] == 2)
				value = (value - 273.15) * (9.0 / 5.0) + 32.0;
			
			System.out.printf("Result: %.2f", value);
				
		}
		
		else if (ans == 2) {
			int[] ft = fromTo(currency);
			System.out.print("Enter the value: ");
			double value = in.nextDouble();
			if (ft[0] == 1)
				value = value * 1.12;
			if (ft[0] == 2)
				value = value * 0.015;
			if (ft[0] == 3)
				value = value * 1.32;
			
			if (ft[1] == 1)
				value = value / 1.12;
			if (ft[1] == 2)
				value = value / 0.015;
			if (ft[1] == 3)
				value = value / 1.32;
			
			System.out.printf("Result: %.2f", value);
		}
		
		else if (ans == 3) {
			int[] ft = fromTo(volume);
			System.out.print("Enter the value: ");
			double value = in.nextDouble();
			if (ft[0] == 0)
				value = value / 1000.0;
			if (ft[0] == 2)
				value = value * 1000000.0;
			
			if (ft[1] == 1)
				value = value * 1000.0;
			if (ft[1] == 2)
				value = value / 1000000.0;
			
			System.out.printf("Result: %.2f", value);
		}
		
		else if (ans == 4) {
			int[] ft = fromTo(mass);
			System.out.print("Enter the value: ");
			double value = in.nextDouble();
			if (ft[0] == 1)
				value = value * 0.453592;
			
			if (ft[1] == 1)
				value = value / 0.453592;
			
			System.out.printf("Result: %.2f", value);
		}
		
		in.close();
	}
	
	public static int[] fromTo(String[] options) {
		StringBuilder sb = new StringBuilder();
		System.out.println("From:");
		for (int i = 0; i < options.length; i++) {
			System.out.println(sb.append(i + 1).append(". ").append(options[i]).toString());
			sb.delete(0, sb.length());
		}
		Scanner in = new Scanner(System.in);
		int[] result = new int[2];
		result[0] = in.nextInt() - 1;
		System.out.println("To:");
		int count = 1;
		for (int i = 0; i < options.length; i++) {
			if (i != result[0]) {
				System.out.println(sb.append(count).append(". ").append(options[i]).toString());
				count++;
			}
			sb.delete(0, sb.length());
		}
		result[1] = in.nextInt() - 1;
		if (result[1] >= result[0])
			result[1]++;
		return result;
	}
	
	public static final String[] temperature = { "Celcius", "Kelvin", "Fahrenheit" };
	public static final String[] currency = { "Dollar", "Euro", "Ruble", "Pound" };
	public static final String[] volume = { "mm^3", "cm^3", "m^3" };
	public static final String[] mass = { "kg", "lb" };
}
