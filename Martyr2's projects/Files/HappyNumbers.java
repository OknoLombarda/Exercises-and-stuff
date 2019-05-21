import java.util.ArrayList;
import java.util.Scanner;

public class HappyNumbers {
    public static void main(String args[]) {
    	System.out.print("Enter a number greater than 1: ");
    	int input = 0;
    	try (Scanner in = new Scanner(System.in)) {
    		input = in.nextInt();
    		if (input <= 1) {
    			System.err.println("Entered number is equal or less than 1");
    			return;
    		}
    	}
    	
    	ArrayList<Integer> happyNumbers = new ArrayList<>();
    	String temp;
    	int tempNum = 0;
    	while (happyNumbers.size() < 8) {
    		tempNum = input;
    		while (tempNum != 1) {
    			temp = String.valueOf(tempNum);
    			tempNum = 0;
    			for (char c : temp.toCharArray()) {
    				tempNum += (int) Math.pow(Character.getNumericValue(c), 2);
    			}
    		
    			if (tempNum == 4)
    				break;
    		}
    		if (tempNum != 4)
    			happyNumbers.add(input);
    		input++;
    	}
    	
    	System.out.println("Your happy numbers:");
    	happyNumbers.forEach(System.out::println);
    }
}