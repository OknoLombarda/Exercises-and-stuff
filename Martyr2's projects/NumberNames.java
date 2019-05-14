import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class NumberNames {
	public static void main(String[] args) {
		int input = 0;
		System.out.print("Enter a number: ");
		try (Scanner in = new Scanner(System.in)) {
			input = in.nextInt();
		}
		
		String number = String.valueOf(input);
		StringBuilder sb = new StringBuilder();
		if (number.startsWith("-")) {
			sb.append("МинусS");
			number = number.substring(1);
			
			if (number.length() > 9) {
				System.err.println("The number is too long");
				return;
			}
		}
		
		ArrayList<String> num = new ArrayList<>();
		if (number.length() > 3) {
			while (number.length() > 3) {
				num.add(number.substring(number.length() - 3));
				number = number.substring(0, number.length() - 3);
			}
			if (!number.isBlank())
				num.add(number);
		}
		else
			if (!number.isBlank())
				num.add(number);
		
		Collections.reverse(num);
			
		int i = num.size();
		for (String s : num) {
			for (int j = 0; j < 3; j++) {
				if (s.length() == 3 && j == 0)
					sb.append(getHundredsName(s.charAt(j)));
				if (j == 1) {
					if (checkSpecial(s)) {
						sb.append(getSpecialName(s));
						j += 2;
					}
					else
						if (s.length() >= 2)
							sb.append(getDecimalsName(s.charAt((s.length() == 2) ? 0 : j)));
				}
				if (j == 2) {
					boolean isThousand = false;
					if (i == 2)
						isThousand = true;
					int k = j;
					if (s.length() == 2)
						k = 1;
					if (s.length() == 1)
						k = 0;
					sb.append(getDigitName(s.charAt(k), isThousand));
				}
			}
			sb.append(getAdditionalWord(s, i));
			i--;
		}
		
		String output = sb.toString();
		output = output.replaceAll("S+", " ").trim();
		if (Character.isLowerCase(output.charAt(0))) {
			output = Character.toUpperCase(output.charAt(0)) + output.substring(1);
		}
		
		System.out.println(output);
	}
	
	public static String getDigitName(char c, boolean isThousand) {
		if (c == '1') {
			if (isThousand)
				return "SоднаS";
			else
				return "SодинS";
		}
		if (c == '2')
			return "SдваS";
		if (c == '3')
			return "SтриS";
		if (c == '4')
			return "SчетыреS";
		if (c == '5')
			return "SпятьS";
		if (c == '6')
			return "SшестьS";
		if (c == '7')
			return "SсемьS";
		if (c == '8')
			return "SвосемьS";
		if (c == '9')
			return "SдевятьS";
		
		return "";
	}
	
	public static String getAdditionalWord(String s, int flag) {
		if (flag == 3 || flag == 2)
			flag -= 2;
		else
			return "";
		String[][] words = {
				{ "SтысячаS", "SтысячиS", "SтысячS" },
				{"SмиллионS", "SмиллионаS", "SмиллионовS" }
		};
		if (checkSpecial(s))
			return words[flag][2];
		char d = s.charAt(s.length() - 1);
		if (d == '0' || d == '5' || d == '6' || d == '7' || d == '8' || d == '9')
			return words[flag][2];
		if (d == '1')
			return words[flag][0];
		if (d == '2' || d == '3' || d == '4')
			return words[flag][1];
		
		return "";
	}
	
	public static String getHundredsName(char c) {
		if (c == '1')
			return "SстоS";
		if (c == '2')
			return "SдвестиS";
		if (c == '3')
			return "SтристаS";
		if (c == '4')
			return "SчетырестаS";
		if (c == '5')
			return "SпятьсотS";
		if (c == '6')
			return "SшестьсотS";
		if (c == '7')
			return "SсемьсотS";
		if (c == '8')
			return "SвосемьсотS";
		if (c == '9')
			return "SдевятьсотS";
		
		return "";
	}
	
	public static String getDecimalsName(char c) {
		if (c == '2')
			return "SдвадцатьS";
		if (c == '3')
			return "SтридцатьS";
		if (c == '4')
			return "SсорокS";
		if (c == '5')
			return "SпятьдесятS";
		if (c == '6')
			return "SшестьдесятS";
		if (c == '7')
			return "SсемьдесятS";
		if (c == '8')
			return "SвосемьдесятS";
		if (c == '9')
			return "SдевяностоS";
		
		return "";
	}
	
	public static boolean checkSpecial(String s) {
		if (s.endsWith("10") || s.endsWith("11") || s.endsWith("12") || s.endsWith("13")
							 || s.endsWith("14") || s.endsWith("15") || s.endsWith("16")
							 || s.endsWith("17") || s.endsWith("18") || s.endsWith("19"))
			return true;
		
		return false;
	}
	
	public static String getSpecialName(String s) {
		if (s.endsWith("10"))
			return "SдесятьS";
		if (s.endsWith("11"))
			return "SодиннадцатьS";
		if (s.endsWith("12"))
			return "SдвенадцатьS";
		if (s.endsWith("13"))
			return "SтринадцатьS";
		if (s.endsWith("14"))
			return "SчетырнадцатьS";
		if (s.endsWith("15"))
			return "SпятнадцатьS";
		if (s.endsWith("16"))
			return "SшестнадцатьS";
		if (s.endsWith("17"))
			return "SсемнадцатьS";
		if (s.endsWith("18"))
			return "SвосемнадцатьS";
		if (s.endsWith("19"))
			return "SдевятнадцатьS";
		
		return "";
	}
}
