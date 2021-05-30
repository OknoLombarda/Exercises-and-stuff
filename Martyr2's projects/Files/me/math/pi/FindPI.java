package me.math.pi;

import java.util.Scanner;

public class FindPI {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Required precision: ");
		int precision = in.nextInt();

		PiCalculationResult pi = Pi.calculate(precision);

		System.out.printf("Calculated value: %s. Iterations count: %d", pi.getPi(), pi.getIterationCount());
	}
}