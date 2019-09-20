package me.graphs;

import java.util.Scanner;

public class GraphFromLinks {
    public static void main(String[] args) {
	    System.out.println("Type 'q' to quit.");
	    System.out.printf("Example of a link: 1-2\n\n");

	    Graph graph = new Graph();
	    try (Scanner in = new Scanner(System.in)) {
		String input;
		do {
			System.out.print("Enter link: ");
	    		input = in.nextLine();
			if (input.equalsIgnoreCase("q")) {
				break;
			}
			graph.addEdge(input);
		} while (true);
	    }
	    System.out.println(graph);
    }
}
