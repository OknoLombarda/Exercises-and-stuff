package me.graphs.index;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class Graph implements Serializable {
    private static final long serialVersionUID = 453862187345908743L;

    private LinkedList<Vertex> vertices;

    public Graph() {
        vertices = new LinkedList<>();
    }

    public void addEdge(File first, File second, Map<String, Integer> index) {
        if (first == null && second == null) {
            return;
        }

        if (first == null) {
            addVertex(second, index);
        } else if (second == null) {
            addVertex(first);
        } else {
            addNode(first, second, index);
        }
    }

    private void addNode(File first, File second, Map<String, Integer> index) {
        Vertex v1 = getVertex(first);
        Vertex v2 = getVertex(second);

        if (v1 != null) {
            if (v2 != null) {
                if (!v1.nodes.contains(v2)) {
                    connectVertices(v1, v2);
                }
            } else {
                v2 = new Vertex(second);
                connectVertices(v1, v2);
                vertices.add(v2);
            }
        } else if (v2 != null) {
            v1 = new Vertex(first);
            connectVertices(v1, v2);
            vertices.add(v1);
        } else {
            v1 = new Vertex(first);
            v2 = new Vertex(second);
            connectVertices(v1, v2);
            vertices.add(v1);
            vertices.add(v2);
        }

        v2.index = index;
    }

    private void addVertex(File value, Map<String, Integer> index) {
        Vertex v = getVertex(value);
        if (v == null) {
            v = new Vertex(value);
            vertices.add(v);
        }
        v.index = index;
    }

    private void addVertex(File value) {
        addVertex(value, null);
    }

    private Vertex getVertex(File value) {
        for (Vertex v : vertices) {
            if (v.value == value) {
                return v;
            }

            for (Vertex v1 : v.nodes) {
                if (v1.value == value) {
                    return v1;
                }
            }
        }

        return null;
    }

    public int hashCode() {
        return vertices.hashCode() + 1337;
    }

    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null) {
            return false;
        }
        if (getClass() != otherObject.getClass()) {
            return false;
        }

        Graph other = (Graph) otherObject;
        return vertices.equals(other.vertices);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Graph[");
        Iterator<Vertex> iter = vertices.iterator();
        Vertex next;
        while (iter.hasNext()) {
            next = iter.next();
            sb.append(next.value);
            sb.append(":{ ");

            Iterator<Vertex> iter1 = next.nodes.iterator();
            while (iter1.hasNext()) {
                sb.append(iter1.next().value);
                if (iter1.hasNext()) {
                    sb.append(", ");
                }
            }
            sb.append(" }");

            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public LinkedList<Vertex> getVertices() {
        return vertices;
    }

    private void connectVertices(Vertex first, Vertex second) {
        first.nodes.add(second);
        second.nodes.add(first);
    }

    static class Vertex implements Serializable {
        private static final long serialVersionUID = 253462156315928733L;

        private File value;
        private LinkedList<Vertex> nodes;
        private Map<String, Integer> index;
        private long lastModified;

        Vertex(File value) {
            this.value = value;
            nodes = new LinkedList<>();
            lastModified = value.lastModified();
        }

        public Map<String, Integer> getIndex() { // TODO del
            return index;
        }

        public File getValue() { // TODO del
            return value;
        }

        @Override
        public int hashCode() {
            return value.hashCode() * 31 + 2;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }

            Vertex other = (Vertex) obj;
            return value.equals(other.value);
        }
    }
}