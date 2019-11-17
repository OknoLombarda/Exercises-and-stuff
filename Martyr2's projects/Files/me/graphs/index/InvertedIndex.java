package me.graphs.index;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static me.graphs.index.Graph.Vertex;

public class InvertedIndex implements Serializable {
    private static final long serialVersionUID = 546863289045815743L;

    private Graph index;
    private File root;

    InvertedIndex(File root, Graph index) {
        this.root = root;
        this.index = index;
    }

    public List<File> search(String s) {
        Set<SearchResult<File>> results = new HashSet<>();
        String[] keys = s.toLowerCase().replaceAll("\\W+", " ").split("\\s+");

        Map<String, Integer> ind;
        int sim;
        int value;
        for (Vertex v : index.getVertices()) {
            sim = 0;
            ind = v.getIndex();

            for (String key : keys) {
                if (ind != null && ind.containsKey(key)) {
                    value = ind.get(key);
                    sim += 1 + value / Math.pow(10, countDigits(value));
                }
            }

            if (sim > 0) {
                results.add(new SearchResult<>(v.getValue(), sim));
            }
        }

        return results.stream().sorted().map(SearchResult::getResult).collect(Collectors.toList());
    }

    private int countDigits(int num) {
        int count = 1;
        while ((num /= 10) != 0) {
            count++;
        }
        return count;
    }

    public File getRoot() {
        return root;
    }

    public Graph getAsGraph() {
        return index;
    }

    public String toString() {
        return index.toString();
    }
}
