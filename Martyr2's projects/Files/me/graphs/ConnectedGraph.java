package me.graphs;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class ConnectedGraph {
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

        HashMap<String, LinkedList<String>> vertices = graph.getVertices();
        HashSet<String> connections = new HashSet<>();

        connectVertices(vertices.keySet().iterator().next(), vertices, connections);

        boolean connected;
        do {
            connected = false;
            start: for (Map.Entry<String, LinkedList<String>> entry : vertices.entrySet()) {
                for (String v : entry.getValue()) {
                    if (connections.contains(v)) {
                        connectVertices(entry.getKey(), vertices, connections);
                        connected = true;
                        break start;
                    }
                }
            }
        } while (vertices.size() != 0 && connected);

        System.out.println("This graph is" + (connected ? "" : " not") + " connected");
    }

    public static void connectVertices(String vertex, HashMap<String, LinkedList<String>> vertices,
                                          HashSet<String> connections) {
        connections.add(vertex);
        LinkedList<String> v = vertices.get(vertex);
        connections.addAll(v);
        vertices.remove(vertex);
        v.forEach(vertices::remove);
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
