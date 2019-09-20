package me.graphs;

public class GraphFromLinks {
    public static void main(String[] args) {
        Graph g1 = new Graph("1-2", "2-3", "4-5", "5-1");
        System.out.println(g1);
    }
}
