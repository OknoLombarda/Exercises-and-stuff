package me.calculator;

import java.util.Scanner;

public class ScientificCalculator {
	public static void main(String[] args) {
		String input = "";

		if (args.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (String s : args)
				sb.append(s);
			input = sb.toString();
		}
		
		System.out.println("Enter \"q\" to quit the program");
		while (true)
			mainMenu(input);
	}

	private static void mainMenu(String s) {
		String input = s;

		if (input.isEmpty()) {
			Scanner in = new Scanner(System.in);
			System.out.println("Enter a mathematical expression:");
			input = in.nextLine();
			if (input.equalsIgnoreCase("q"))
				System.exit(0);
		}

		input = input.toLowerCase().replaceAll(" ", "");
		input = input.replaceAll("e", Double.toString(Math.E));
		input = input.replaceAll("pi", Double.toString(Math.PI));

		String[] expr = CalcTools.toPolishNotation(input);
		
		if (expr == null)
			System.out.println("Syntax error");
		else {
			String result = CalcTools.calculate(input);
			if (result.endsWith(".0"))
				result = result.substring(0, result.length() - 2);
			System.out.println("=".concat(result));
		}
	}
}
