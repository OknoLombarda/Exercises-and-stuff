package me.graphs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Graph {
    private HashMap<String, LinkedList<String>> vertices;

    public Graph() {
        vertices = new HashMap<>();
    }

    public Graph(String... edges) {
        this();
        addEdges(edges);
    }

    public void addEdge(String edge) {
        String[] vert = edge.split("-");
        addNode(vert[0], vert[1]);
        addNode(vert[1], vert[0]);
    }

    public void addEdges(String... edges) {
        for (String edge : edges) {
            addEdge(edge);
        }
    }

    private void addNode(String v1, String v2) {
        LinkedList<String> nodes;
        if (vertices.containsKey(v1)) {
            nodes = vertices.get(v1);
            if (!nodes.contains(v2)) {
                nodes.add(v2);
            }
        } else {
            nodes = new LinkedList<>();
            nodes.add(v2);
            vertices.put(v1, nodes);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        LinkedList<String> nodes;
        Iterator<String> iter;
        boolean first = true;
        for (Map.Entry<String, LinkedList<String>> entry : vertices.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(entry.getKey()).append(":[");
            nodes = entry.getValue();
            iter = nodes.iterator();
            while (iter.hasNext()) {
                sb.append(iter.next());
                if (iter.hasNext()) {
                    sb.append(";");
                }
            }
            sb.append("]");
        }
        sb.append(" }");
        return sb.toString();
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null)
            return false;
        if (getClass() != other.getClass())
            return false;

        Graph og = (Graph) other;
        return vertices.equals(og.vertices);
    }

    public int hashCode() {
        return vertices.hashCode() * 33;
    }
}
