package me.graphs.generic;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DijkstraAlgorithm {
    private static <T> List<T> toList(Graph.Vertex<T> v) {
        List<T> list = new LinkedList<>();

        if (v == null) {
            return list;
        }

        list.add(0, v.getValue());
        while (v.hasPrevious()) {
            list.add(0, (v = v.getPrevious()).getValue());
        }
        return list;
    }

    public static <T> List<T> findPath(Graph<T> gr, T source, T target, Set<T> except) {
        HashSet<Graph.Vertex<T>> unvisited = new HashSet<>();
        unvisited.addAll(gr.getVertices().stream()
                .flatMap(v -> v.getNodes().stream()).collect(Collectors.toSet()));

        if (except != null && !except.isEmpty()) {
            unvisited.removeIf(v -> except.contains(v.getValue()));
        }

        Graph.Vertex<T> targ = gr.getVertex(target);
        Graph.Vertex<T> v = gr.getVertex(source);
        v.setWeight(0);

        int alt;
        while (!unvisited.isEmpty()) {
            for (Graph.Vertex<T> vert : unvisited) {
                if (v == null || vert.getWeight() < v.getWeight()) {
                    v = vert;
                }
            }

            unvisited.remove(v);

            if (v.equals(targ)) break;

            for (Graph.Vertex<T> neighbor : v.getNodes()) {
                if (neighbor.isVisited()) continue;

                alt = v.getWeight() + 1;
                if (alt < neighbor.getWeight()) {
                    neighbor.setWeight(alt);
                    neighbor.setPrevious(v);
                }
            }

            v.setVisited(true);
            v = null;
        }

        return toList(v);
    }

    public static <T> List<T> findPath(Graph<T> gr, T source, T target) {
        return findPath(gr, source, target, null);
    }

    public static <T> Graph<T> constructGraph(T[][] arr) {
        Graph<T> gr = new Graph<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                // check upper, lower, left and right elements
                // if they exist, connect them to current element
                if (i > 0) gr.addEdge(arr[i][j], arr[i - 1][j]);
                if (i < arr.length - 1) gr.addEdge(arr[i][j], arr[i + 1][j]);
                if (j > 0) gr.addEdge(arr[i][j], arr[i][j - 1]);
                if (j < arr[i].length - 1) gr.addEdge(arr[i][j], arr[i][j + 1]);
            }
        }
        return gr;
    }
}