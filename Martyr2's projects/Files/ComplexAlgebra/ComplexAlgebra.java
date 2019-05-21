package me.numbers;

import java.util.Scanner;
import java.util.function.Function;

public class ComplexAlgebra {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.printf("\n\n1. Addition\n2. Subtraction\n3. Multiplication\n4. Division\n5. Negation\n6. Inversion\n7. Exit\n\n");
		int ans = in.nextInt();
		Function<ComplexNumber[], ComplexNumber> func = cn -> null;
		int NUMBERS_AMOUNT = 2;
		
		if (ans == 7)
			System.exit(0);
		else if (ans == 1)
			func = cn -> ComplexNumber.add(cn[0], cn[1]);
		else if (ans == 2)
			func = cn -> ComplexNumber.subtract(cn[0], cn[1]);
		else if (ans == 3)
			func = cn -> ComplexNumber.multiply(cn[0], cn[1]);
		else if (ans == 4)
			func = cn -> ComplexNumber.divide(cn[0], cn[1]);
		else if(ans == 5) {
			NUMBERS_AMOUNT = 1;
			func = cn -> ComplexNumber.negate(cn[0]);
		}
		else if (ans == 6) {
			NUMBERS_AMOUNT = 1;
			func = cn -> ComplexNumber.inverse(cn[0]);
		}
		
		ComplexNumber[] input = readNumbers(NUMBERS_AMOUNT);
		System.out.println("Result: " + func.apply(input));
		
		in.close();
	}
	
	public static ComplexNumber[] readNumbers(int flag) {
		ComplexNumber[] input = new ComplexNumber[2];
		Scanner in = new Scanner(System.in);
		
		System.out.print("Enter a complex number: ");
		String temp = in.nextLine();
		input[0] = ComplexNumber.parse(temp);
		
		if (flag == 1)
			return input;
		
		System.out.print("Enter second complex number: ");
		temp = in.nextLine();
		input[1] = ComplexNumber.parse(temp);

		return input;
	}
}
