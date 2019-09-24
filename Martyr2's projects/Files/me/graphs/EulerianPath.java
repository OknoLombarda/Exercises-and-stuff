package me.graphs;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class EulerianPath {
    public static void main(String[] args) {
        Graph graph = new Graph();
        if (args.length > 0) {
            try (Scanner sc = new Scanner(Paths.get(args[0]), "UTF-8")) {
                while (sc.hasNextLine()) {
                    graph.addEdges(sc.nextLine().split("\\s+"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fillGraph(graph);
        }

        ArrayList<LinkedHashMap<Integer, String>> paths = new ArrayList<>();
        for (Map.Entry<String, LinkedList<String>> entry : graph.getVertices().entrySet()) {
            walkPath(entry.getKey(), graph, new LinkedHashMap<Integer, String>(), paths);
        }

        if (paths.size() == 0) {
            System.out.println("Eulerian path is not possible for this graph");
            System.exit(0);
        }

        LinkedHashMap<Integer, String> shortest = null;
        for (LinkedHashMap<Integer, String> map : paths) {
            if (shortest == null || map.size() > shortest.size()) {
                shortest = map;
            }
        }

        System.out.println("The shortest path:");
        for (String s : shortest.values()) {
            System.out.println(s);
        }
    }

    public static void walkPath(String vertex, Graph graph, LinkedHashMap<Integer, String> moves, ArrayList<LinkedHashMap<Integer, String>> paths) {
        LinkedHashMap<Integer, String> save = new LinkedHashMap<>(moves);
        boolean moved = false;
        int hash = 0;

        for (String v : graph.getVertices().get(vertex)) {
            if (moves.size() != save.size()) {
                moves = new LinkedHashMap<Integer, String>();
                moves.putAll(save);
            }
            hash = vertex.hashCode() + v.hashCode();
            if (!moves.containsKey(hash)) {
                moved = true;
                moves.put(hash, vertex.concat("-").concat(v));
                walkPath(v, graph, moves, paths);
            }
        }

        if (!moved && moves.size() == graph.getAmountOfEdges()) {
            paths.add(moves);
        }
    }

    public static void fillGraph(Graph graph) {
        try (Scanner in = new Scanner(System.in)) {
            String line;
            boolean done = false;
            while (!done) {
                System.out.print("Enter link ('Q' to quit): ");
                line = in.nextLine();
                if (line.equalsIgnoreCase("q")) {
                    done = true;
                } else {
                    graph.addEdge(line);
                }
            }
        }
    }
}
