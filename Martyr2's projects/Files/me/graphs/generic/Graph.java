package me.graphs.generic;

import java.util.*;

public class Graph<T> {
    private LinkedList<Vertex<T>> vertices;

    public Graph() {
        vertices = new LinkedList<>();
    }

    public void addEdge(T first, T second) {
        if (first == null && second == null) {
            return;
        }

        if (first == null) {
            addVertex(second);
        } else if (second == null) {
            addVertex(first);
        } else {
            addNode(first, second);
        }
    }

    private void addNode(T first, T second) {
        Vertex<T> v1 = getVertex(first);
        Vertex<T> v2 = getVertex(second);

        if (v1 != null) {
            if (v2 != null) {
                if (!v1.nodes.contains(v2)) {
                    connectVertices(v1, v2);
                }
            } else {
                v2 = new Vertex<>(second);
                connectVertices(v1, v2);
                vertices.add(v2);
            }
        } else if (v2 != null) {
            v1 = new Vertex<>(first);
            connectVertices(v1, v2);
            vertices.add(v1);
        } else {
            v1 = new Vertex<>(first);
            v2 = new Vertex<>(second);
            connectVertices(v1, v2);
            vertices.add(v1);
            vertices.add(v2);
        }
    }

    private void addVertex(T value) {
        Vertex<T> v = getVertex(value);
        if (v != null) {
            return;
        }
        v = new Vertex<>(value);
        vertices.add(v);
    }

    public Vertex<T> getVertex(T value) {
        for (Vertex<T> v : vertices) {
            if (v.value == value) {
                return v;
            }

            for (Vertex<T> v1 : v.nodes) {
                if (v1.value == value) {
                    return v1;
                }
            }
        }

        return null;
    }

    public void resetVertices() {
        for (Vertex<T> v : vertices) {
            v.reset();
            for (Vertex<T> v1 : v.nodes) {
                v1.reset();
            }
        }
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

        Graph<?> other = (Graph<?>) otherObject;
        return vertices.equals(other.vertices);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Graph[");
        Iterator<Vertex<T>> iter = vertices.iterator();
        Vertex<T> next;
        while (iter.hasNext()) {
            next = iter.next();
            sb.append(next.value);
            sb.append(":{ ");

            Iterator<Vertex<T>> iter1 = next.nodes.iterator();
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

    public LinkedList<Vertex<T>> getVertices() {
        return vertices;
    }

    private void connectVertices(Vertex<T> first, Vertex<T> second) {
        first.nodes.add(second);
        second.nodes.add(first);
    }

    static class Vertex<T> {
        private T value;
        private int weight;
        private boolean visited;
        private LinkedList<Vertex<T>> nodes;
        private Vertex<T> previous;

        public Vertex(T value) {
            this.value = value;
            this.weight = Integer.MAX_VALUE;
            visited = false;
            nodes = new LinkedList<>();
        }

        public T getValue() {
            return value;
        }

        public LinkedList<Vertex<T>> getNodes() {
            return nodes;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public Vertex<T> getPrevious() {
            return previous;
        }

        public void setPrevious(Vertex<T> previous) {
            this.previous = previous;
        }

        public boolean hasPrevious() {
            return previous != null;
        }

        public void reset() {
            weight = Integer.MAX_VALUE;
            visited = false;
            previous = null;
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

            Vertex<?> other = (Vertex<?>) obj;
            return value.equals(other.value);
        }
    }
}