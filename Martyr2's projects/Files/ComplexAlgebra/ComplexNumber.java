package me.numbers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexNumber {
	private double first = 0;
	private double second = 0;
	
	
	public ComplexNumber(double first, double second) {
		this.first = first;
		this.second = second;
	}
	
	public static ComplexNumber add(ComplexNumber firstNumber, ComplexNumber secondNumber) {
		return new ComplexNumber(firstNumber.getFirst() + secondNumber.getFirst(),
								 firstNumber.getSecond() + secondNumber.getSecond());
	}
	
	public static ComplexNumber subtract(ComplexNumber firstNumber, ComplexNumber secondNumber) {
		return new ComplexNumber(firstNumber.getFirst() - secondNumber.getFirst(),
								 firstNumber.getSecond() - secondNumber.getSecond());
	}
	
	public static ComplexNumber multiply(ComplexNumber firstNumber, ComplexNumber secondNumber) {
		double[] firsts = { firstNumber.getFirst(), secondNumber.getFirst() };
		double[] seconds = { firstNumber.getSecond(), secondNumber.getSecond() };
		return new ComplexNumber(firsts[0] * firsts[1] - seconds[0] * seconds[1],
								 firsts[0] * seconds[1] + firsts[1] * seconds[0]);
	}
	
	public static ComplexNumber divide(ComplexNumber firstNumber, ComplexNumber secondNumber) {
		ComplexNumber temp = multiply(firstNumber,
						new ComplexNumber(secondNumber.getFirst(), -(secondNumber.getSecond())));
		double coeff = 1 / (Math.pow(secondNumber.getFirst(), 2) + Math.pow(secondNumber.getSecond(), 2));
		return new ComplexNumber(temp.getFirst() * coeff, temp.getSecond() * coeff);
	}
	
	public static ComplexNumber negate(ComplexNumber number) {
		return new ComplexNumber(-(number.getFirst()), -(number.getSecond()));
	}
	
	public static ComplexNumber inverse(ComplexNumber number) {
		double sum = Math.pow(number.getFirst(), 2) + Math.pow(number.getSecond(), 2);
		return new ComplexNumber(number.getFirst() / sum, -(number.getSecond() / sum));
	}
	
	public static ComplexNumber parse(String line) {
		if (!line.matches("^[-+]?\\(?[-+]?\\d+\\.?\\d*\\)?[+-]{1}\\(?[+-]?\\d+\\.?\\d*i\\)?$"))
			return null;
		
		double[] param = new double[2];
		
		line = line.replaceAll("\\(|\\)", "");
		Matcher mr = Pattern.compile("\\-?\\d+\\.?\\d*").matcher(line);
		for (int i = 0; i < 2; i++) {
			if (mr.find())
				param[i] = Double.parseDouble(mr.group());
		}
		
		return new ComplexNumber(param[0], param[1]);
	}

	public boolean equals(Object otherObject) {
		if (this == otherObject)
			return true;
		if (otherObject == null)
			return false;
		if (getClass() != otherObject.getClass())
			return false;
		
		ComplexNumber otherNumber = (ComplexNumber) otherObject;
		if (Double.compare(this.first, otherNumber.getFirst()) == 0
				&& Double.compare(this.second, otherNumber.getSecond()) == 0)
			return true;
		return false;
	}
	
	public double getFirst() {
		return first;
	}
	
	public double getSecond() {
		return second;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String firstV = String.valueOf(first);
		firstV = firstV.endsWith(".0") ? firstV.replaceFirst("\\.0$", "") : firstV;
		String secondV = String.valueOf(second);
		secondV = secondV.endsWith(".0") ? secondV.replaceFirst("\\.0$", "") : secondV;
		
		if (first < 0)
			sb.append("(").append(firstV).append(")");
		else
			sb.append(firstV);
		
		sb.append("+");
		
		if (second < 0)
			sb.append("(").append(secondV).append("i").append(")");
		else
			sb.append(secondV).append("i");
		
		return sb.toString();
	}
}
