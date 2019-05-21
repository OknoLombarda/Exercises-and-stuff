package me.calculator;

import java.util.ArrayList;
import java.util.Stack;

class CalcTools {
	public static String[] toPolishNotation(String expression) {
		Stack<String> st = new Stack<>();
		ArrayList<String> out = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < expression.length(); i++) {
			if (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '!'
					|| (expression.charAt(i) == '-' && (i == 0 || (!Character.isDigit(expression.charAt(i-1)) && expression.charAt(i-1) != ')')))) {
				while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '!'
						|| (expression.charAt(i) == '-' && (i == 0 || (!Character.isDigit(expression.charAt(i-1)) && expression.charAt(i-1) != ')'))))) {
					sb.append(expression.charAt(i));
					i++;
				} // while loop
				out.add(sb.toString());
				sb.delete(0, sb.length());
				
				if (i >= expression.length())
					break;
			} // if operator
			
			if (Character.isLetter(expression.charAt(i))) {
				while (i < expression.length() && Character.isLetter(expression.charAt(i))) {
					sb.append(expression.charAt(i));
					i++;
				}
				String func = sb.toString();
				
				if (isPrefixFunction(func))
					st.push(func);
				else
					return null;  // expression error
				
				sb.delete(0, sb.length());
				
				if (i >= expression.length())
					break;
			}
			
			if (expression.charAt(i) == '(')
				st.push("(");
			
			if (expression.charAt(i) == ')') {
				while (!st.empty() && !st.peek().equals("(")) {
					out.add(st.pop());
				}
				if (!st.empty() && st.peek().equals("("))
					st.pop();
				else
					return null;  // expression error
			}
			
			if (isBinaryOperation(expression.charAt(i))) {
				while (!st.empty() && (isPrefixFunction(st.peek()) ||
							(isBinaryOperation(st.peek().charAt(0)) &&
							 getPriority(st.peek().charAt(0)) <= getPriority(expression.charAt(i))))) {
					out.add(st.pop());
				}
				st.push(String.valueOf(expression.charAt(i)));
			}
		} // for loop
		
		while (!st.empty())
			out.add(st.pop());
		
		String[] result = new String[out.size()];
		out.toArray(result);
		return result;
	}
	
	public static String calculate(String input) {
		Stack<String> st = new Stack<>();
		String[] expr = toPolishNotation(input);
		
		for (String s : expr) {
			if (s.matches("-?\\d+(\\.\\d+)?"))
				st.push(s);
			else if (isBinaryOperation(s.charAt(0)))
				st.push(String.valueOf(applyBinaryOperation(st.pop(), st.pop(), s)));
			else if (isPrefixFunction(s))
				st.push(String.valueOf(applyPrefixFunction(st.pop(), s)));
		}
		
		return st.pop();
	}
	
	public static double applyBinaryOperation(String firstNum, String secondNum, String operation) {
		double first = Double.parseDouble(firstNum);
		double second = Double.parseDouble(secondNum);
		
		if (operation.equals("^"))
			return Math.pow(second, first);
		if (operation.equals("*"))
			return first * second;
		if (operation.equals("/"))
			return second / first;
		if (operation.equals("+"))
			return first + second;
		if (operation.equals("-"))
			return second - first;
		
		return 0;
	}
	
	public static double applyPrefixFunction(String number, String function) {
		double num = Double.parseDouble(number);
		if (function.equals("log"))
			return Math.log10(num);
		if (function.equals("ln"))
			return Math.log(num);
		if (function.equals("sin"))
			return Math.asin(num);
		if (function.equals("cos"))
			return Math.acos(num);
		if (function.equals("tan"))
			return Math.tan(num);
		if (function.equals("cot"))
			return Math.cos(num) / Math.sin(num);
		
		return 0;
	}

	public static boolean isPrefixFunction(String s) {
		for (String f : functions)
			if (s.equals(f))
				return true;
		return false;
	}
	
	public static boolean isBinaryOperation(char c) {
		for (int i = 0; i < operations.length - 2; i++)
			if (c == operations[i])
				return true;
		return false;
	}
	
	public static int getPriority(char c) {
		int temp = 0;
		for (int i = 0; i < operations.length; i++) {
			if (c == operations[i]) {
				if (i == 2)
					i = 1;
				else if (i == 3 || i == 4)
					i = 2;
				else if (i == 5 || i == 6)
					i = 3;
				
				temp = i;
				break;
			}
		}
		return temp;
	}

	private static char[] operations = { '^', '*', '/', '+', '-', '(', ')' };
	private static String[] functions = { "log", "ln", "sin", "cos", "tan", "cot" };
}
